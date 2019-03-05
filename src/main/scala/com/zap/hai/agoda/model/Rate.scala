package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class Rate(inclusive: Double, fees: Double, exclusive: Double, tax: Double)

object Rate extends XmlSupport[Rate] {

  override def fromXml(node: Node): Rate = {
    val builder = new RateBuilder()
    builder.withInclusive((node \@ "inclusive").toDouble)
    builder.withExclusive((node \@ "exclusive").toDouble)
    builder.withFees((node \@ "fees").toDouble)
    builder.withInclusive((node \@ "inclusive").toDouble)
    builder.withTax((node \@ "tax").toDouble)

    builder.build()
  }

  override def toXml(any: Rate): Elem = ???

}

class RateBuilder {

  private var inclusive: Double = _
  private var fees: Double = _
  private var exclusive: Double = _
  private var tax: Double = _

  def withTax(tax: Double) = {
    this.tax = tax
    this
  }

  def withExclusive(exclusive: Double) = {
    this.exclusive = exclusive
    this
  }

  def withFees(fees: Double) = {
    this.fees = fees
    this
  }

  def withInclusive(inclusive: Double) = {
    this.inclusive = inclusive
    this
  }

  def build() = Rate(inclusive, fees, exclusive, tax)


}