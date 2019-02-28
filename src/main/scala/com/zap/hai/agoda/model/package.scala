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
    def toRate = Rate.fromXml(ns.head)
    def toSurcharge = Surcharge.fromXml(ns.head)
    def toRateInfo = RateInfo.fromXml(ns.head)
    def toTotalPaymentAmount = TotalPaymentAmount.fromXml(ns.head)
    def toPromotionType = PromotionType.fromXml(ns.head)
    def toPolicyDate = PolicyDate.fromXml(ns.head)
    def toPolicyParameter = PolicyParameter.fromXml(ns.head)
    def toCancellation = Cancellation.fromXml(ns.head)
    def toBenefit = Benefit.fromXml(ns.head)
  }




}
