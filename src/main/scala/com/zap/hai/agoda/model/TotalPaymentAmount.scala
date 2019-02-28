package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class TotalPaymentAmount(fees: Double, exclusive: Double, tax: Double)

object TotalPaymentAmount extends XmlSupport[TotalPaymentAmount] {

  override def fromXml(node: Node): TotalPaymentAmount = {
    new TotalPaymentAmountBuilder()
      .withExclusive((node \@ "exclusive").toDouble)
      .withFees((node \@ "fees").toDouble)
      .withTax((node \@ "tax").toDouble)
      .build()
  }

  override def toXml(any: TotalPaymentAmount): Elem = ???

}


class TotalPaymentAmountBuilder {

  private var fees: Double = _
  private var exclusive: Double = _
  private var tax: Double = _

  def withFees(fees: Double) = {
    this.fees = fees
    this
  }

  def withExclusive(exclusive: Double) = {
    this.exclusive = exclusive
    this
  }

  def withTax(tax: Double) = {
    this.tax = tax
    this
  }

  def build() = TotalPaymentAmount(fees, exclusive, tax)


}