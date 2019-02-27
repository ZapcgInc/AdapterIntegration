package com.zap.hai.agoda

import scala.xml._

package object model {

  implicit class AvailabilityRequestToXml(ar:AvailabilityRequest) {
    def toXml = AvailabilityRequest.toXml(ar)
  }
  implicit class AvailabilityResponseToXml(ar:AvailabilityResponse){
    def toXml = AvailabilityResponse.toXml(ar)
  }

  implicit class ChildrenToXml(c:Children){
    def toXml = Children.toXml(c)
  }
  implicit class XmlStringToAgodaObject(xmlString : String) {
    def toAvailabilityRequest = AvailabilityRequest.fromXml(XML.loadString(xmlString))
    def toChildren = Children.fromXml(XML.loadString(xmlString))
    def toAvailabilityResponse = AvailabilityResponse.fromXml(XML.loadString(xmlString))
  }

  implicit class XmlNodeToAgodaObject(ns: NodeSeq) {
    def toMaxRoomOccupancy = MaxRoomOccupancy.fromXml(ns.head)


  }




}
