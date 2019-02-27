package zap.framework

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Paths

import org.apache.commons.io.{FileUtils, IOUtils}


package object fileutils {

  var customFileIO : Option[FileIO] = None

  val fileio = customFileIO.getOrElse(createFileIO())

  def createFileIO()= {
    new NoopFileIO with LfsFileIO with ClasspathFileIO {}
  }

  trait FileIO {
    def readFileAsString(path: String): Option[String]

    def readFileAsByteArray(path: String): Option[Array[Byte]]
  }

  trait NoopFileIO extends FileIO {
    def readFileAsString(path: String): Option[String] = None

    def readFileAsByteArray(path: String): Option[Array[Byte]] = None
  }

  trait LfsFileIO extends FileIO {
    abstract override def readFileAsByteArray(path: String): Option[Array[Byte]] = {
      try {
        Some(FileUtils.readFileToByteArray(new File(path)))
      } catch {
        case _: Throwable => {
          super.readFileAsByteArray(path)
        }
      }
    }

    abstract override def readFileAsString(path: String): Option[String] = {
      try {
        Some(FileUtils.readFileToString(new File(path), "UTF-8"))
      } catch {
        case _: Throwable => {
          super.readFileAsString(path)
        }
      }
    }
  }

  trait ClasspathFileIO extends FileIO {
    abstract override def readFileAsByteArray(path: String): Option[Array[Byte]] = {
      try {
        Some(IOUtils.resourceToByteArray(path, Thread.currentThread().getContextClassLoader))
      } catch {
        case _: Throwable => {
          super.readFileAsByteArray(path)
        }
      }

    }

    abstract override def readFileAsString(path: String): Option[String] = {
      try {
        Some(IOUtils.resourceToString(path, Charset.defaultCharset(), Thread.currentThread().getContextClassLoader))
      } catch {
        case _: Throwable => {
          super.readFileAsString(path)
        }
      }
    }
  }


}
