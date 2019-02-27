package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}


/*
    @XmlElement(name = "Rate") var rate:Rate = _

    @XmlElement(name = "Included") var included:String = _

    @XmlElementWrapper(name = "Surcharges") @XmlElement(name = "Surcharge")
    var surcharges:Array[Surcharge]= _

    @XmlElement(name = "TotalPaymentAmount") var totalPaymentAmount:TotalPaymentAmount = _

    @XmlElement(name = "Excluded") var excluded:String = _

    @XmlElement(name = "PromotionType") var promotionType:PromotionType = _


 */
case class RateInfo(rate:Rate, included:String, surcharges : List[Surcharge], totalPayAmt : TotalPaymentAmount, excluded:String,
                    promoType : PromotionType)

object RateInfo extends XmlSupport[RateInfo] {

  override def fromXml(node: Node): RateInfo = ???

  override def toXml(any: RateInfo): Elem = ???


}


class RateInfoBuilder {

  var rate:Rate
  var included:String
  var surcharges : List[Surcharge]
  var totalPayAmt : TotalPaymentAmount,
  var  excluded:String,
  promoType : PromotionType

}