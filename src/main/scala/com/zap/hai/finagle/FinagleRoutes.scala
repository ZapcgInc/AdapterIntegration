package com.zap.hai.finagle

import java.io
import cats.data.OptionT
import com.twitter.finagle.Service
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.http._
import com.twitter.util.Future
import com.zap.hai.controllers.{ControllerRequest, ShoppingController}
import zap.framework.httpclient.{ZapHttpResponse, ZapHttpStringEntity}

import scala.collection.JavaConverters._
import scala.collection.mutable

trait FinagleRoutes {


  val shoppingController : ShoppingController

  val routes = RoutingService.byMethodAndPathObject[Request] {
    case (Method.Get, Root / "property" / "availability") => {
      delegate(){(crequest:ControllerRequest) => shoppingController.getPropertyAvailability(crequest)}
    }
    case (Method.Get, Root / "properties" / "availability" / hotelId / "rooms" / roomId / "rates" / rateId / "price-check") => {
      delegate(Map("hotelId"->hotelId,"roomId"->roomId,"rateId"->rateId)){(crequest:ControllerRequest)=>
        shoppingController.getPropertyAvailability(crequest)
      }
    }
    case _ => new Service[Request, Response] {
      def apply(request: Request): Future[Response] = Future(Response(Status.NotFound))
    }
  }


  def delegate(pathParams : Map[String,String] = Map[String,String]())(f:ControllerRequest=>ZapHttpResponse)  = {
    new Service[Request, Response] {
      def apply(req: Request): Future[Response] = {
        Future {
          val x: Map[String, List[String]] = req.headerMap.groupBy(_._1).map{ t=> (t._1,t._2.getAll(t._1).toList)}.toMap
          val y: Map[String,List[String]] = pathParams.map{t => (t._1,List(t._2))}.toMap
          val z: Map[String,List[String]] =  req.getParams().asScala.map(t=>(t.getKey,List(t.getValue))).toMap
          val zresponse = f(ControllerRequest(x++y++z, Some(req.contentString)))
          val fresponse = Response(Status(zresponse.statusCode))
          zresponse.body.map{b => fresponse.contentString = b.asInstanceOf[ZapHttpStringEntity].content}
          zresponse.headers.map{t => fresponse.headerMap.set(t._1,t._2)}
          fresponse
        }
      }
    }
  }


}

