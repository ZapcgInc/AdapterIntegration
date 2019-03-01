package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class RateInfo(rate: Rate, included: String, surcharges: List[Surcharge], totalPayAmt: TotalPaymentAmount, excluded: String,
                    promoType: Option[PromotionType])

object RateInfo extends XmlSupport[RateInfo] {

  override def fromXml(node: Node): RateInfo = {
    val builder = new RateInfoBuilder()

    builder.withRate((node \ "Rate").toRate)
    builder.withIncluded((node \ "Included").text)
    (node \ "Surcharges" \\ "Surcharge").map{s => builder.addSurcharge(s.toSurcharge)}
    (node \ "TotalPaymentAmount").map{n => builder.withTotalPayAmt(n.toTotalPaymentAmount)}
    (node \ "Excluded").map{t => builder.withExcluded(t.text)}
    (node \ "PromotionType").map{n => builder.withPromoType(Some(n.toPromotionType))}

    builder.build()
  }

  override def toXml(any: RateInfo): Elem = ???


}


class RateInfoBuilder {

  private var rate: Rate = _
  private var included: String = _
  private var surcharges: List[Surcharge] = _
  private var totalPayAmt: TotalPaymentAmount = _
  private var excluded: String = _
  private var promoType: Option[PromotionType] = None

  def withPromoType(promoType: Option[PromotionType]) = {
    this.promoType = promoType
    this
  }

  def withExcluded(excluded: String) = {
    this.excluded = excluded
    this
  }

  def withTotalPayAmt(totalPayAmt: TotalPaymentAmount) = {
    this.totalPayAmt = totalPayAmt
    this
  }

  def addSurcharge(surcharge: Surcharge) = {
    if (surcharges == null) surcharges = Nil
    surcharges = surcharges ++ List(surcharge)
    this
  }

  def withIncluded(included: String) = {
    this.included = included
    this
  }

  def withRate(rate: Rate) = {
    this.rate = rate
    this
  }

  def build() = {
    RateInfo(rate, included, surcharges, totalPayAmt, excluded, promoType)
  }

}