package com.zap.hai.agoda.xml

import org.scalatest._
import zap.framework.fileutils._
import com.zap.hai.agoda.model._
import zap.framework.json._

class AvailabilityResponseUnitTest extends FunSuite {

  test("xml to availibility response") {
    val xml = fileio.readFileAsString("availability_response.xml").get
    val node = scala.xml.XML.loadString(xml)
    val ar = xml.toAvailabilityResponse
    println(ar.toJsonPretty)


  }

}
