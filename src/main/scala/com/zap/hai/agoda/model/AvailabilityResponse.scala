package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, Node}

case class AvailabilityResponse(status:Int, searchId:String, hotels : List[Hotel], errors:List[ErrorMessage])

object AvailabilityResponse extends XmlSupport[AvailabilityResponse] {

  override def fromXml(node: Node): AvailabilityResponse = {
    val builder = new AvailabilityResponseBuilder()
    builder.withStatus((node \@  "status").toInt)
    builder.withSearchId((node \@  "searchid"))
    (node \ "Hotels" \\ "Hotel").map{n => builder.addHotel(Hotel.fromXml(n))}
    (node \\ "ErrorMessages").map{n => builder.addError(ErrorMessages.fromXml(n))}
    builder.build()
  }

  override def toXml(any: AvailabilityResponse): Elem = ???

}


class AvailabilityResponseBuilder{

  var status:Int = _
  var searchId:String = _
  var hotels : List[Hotel] = _
  var errors: List[ErrorMessage] = _

  def withStatus(status:Int) = {
    this.status = status
    this
  }

  def withSearchId(searchId: String) = {
    this.searchId = searchId
    this
  }

  def addHotel(hotel : Hotel)={
    if(hotels == null) hotels = List()
    hotels = hotels ++ List(hotel)
    this
  }

  def addError(error: ErrorMessage)={
    if(errors == null) errors = List()
    this.errors = this.errors ++ List(error)
    this
  }

  def build():AvailabilityResponse = {
    AvailabilityResponse(status,searchId, hotels, errors)
  }

}

