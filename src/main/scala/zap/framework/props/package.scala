package zap.framework

import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._
import com.typesafe.config.ConfigFactory
import zap.framework.fileutils._

package object props {

  var customZloader: Option[ZapertyLoader] = None

  val zaperties: ZapProperties = {
    val zaperties = new ZapProperties {}
    customZloader.getOrElse(createDefaultZLoader()).load(zaperties)
    zaperties
  }

  def minprops: ZapProperties = {
    val zaperties = new ZapProperties {}
    new NoopZapertyLoader with SystemZapertyLoader with EnvVarZapertyLoader {}.load(zaperties)
    zaperties
  }

  trait ZapProperties {
    val cache = new ConcurrentHashMap[String, Any]()

    def req[T](key: String): T = cache.get(key).asInstanceOf[T]

    def opt[T](key: String): Option[T] = if (cache.containsKey(key)) Some(req(key)) else None

    def get[T](key: String, defaultVal: T): T = if (cache.containsKey(key)) req(key) else defaultVal

    def put(key: String, value: Any) = cache.put(key, value)

  }

  trait ZapertyLoader {
    def load(properties: ZapProperties)
  }

  trait NoopZapertyLoader extends ZapertyLoader {
    override def load(properties: ZapProperties): Unit = {}
  }

  trait SystemZapertyLoader extends ZapertyLoader {
    abstract override def load(properties: ZapProperties): Unit = {
      sys.props.map { t => properties.put(t._1, t._2) }
      super.load(properties)
    }
  }

  trait EnvVarZapertyLoader extends ZapertyLoader {
    abstract override def load(properties: ZapProperties): Unit = {
      sys.env.map { t =>
        properties.put(t._1, t._2)
        properties.put(t._1.toLowerCase.replaceAll("_", "."), t._2)
      }
      super.load(properties)
    }
  }

  trait TypesafeConfigLoader extends ZapertyLoader {
    abstract override def load(properties: ZapProperties): Unit = {
      println(s"ts.config.path = ${minprops}")
      minprops.opt[String]("ts.config.path").map { path =>
        fileio.readFileAsString(path).map { content =>
          ConfigFactory.parseString(content)
        }.getOrElse(ConfigFactory.load()).entrySet().asScala.map { e => properties.put(e.getKey, e.getValue.unwrapped()) }
      }
      super.load(properties)
    }
  }

  def createDefaultZLoader(): ZapertyLoader = {
    new NoopZapertyLoader with EnvVarZapertyLoader with SystemZapertyLoader with TypesafeConfigLoader {}
  }


}
