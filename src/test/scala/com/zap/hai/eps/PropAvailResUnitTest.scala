package com.zap.hai.eps

import zap.framework.json._
import zap.framework.fileutils._
import org.scalatest._

class PropAvailResUnitTest extends FunSuite {

  test("Json To Object"){

    val json = fileio.readFileAsString("eps_property_availability_response.json").get
    //println(json)

    json.parseJsonAsEither[List[EpsPropertyAvailability]] match {
      case Right(obj) => println(obj.toJsonPretty)
      case Left(t) => t.printStackTrace()
    }

  }


  test("Object to Json"){
    val eps = EpsPropertyAvailability("19248",List(EpsRoom("id",Some("room-rate"),null)),null)
    println(eps.toJsonPretty)
  }



}
