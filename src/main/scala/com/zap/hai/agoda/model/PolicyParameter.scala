package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class PolicyParameter(charge:String, days:Int, content:String)

object PolicyParameter extends XmlSupport[PolicyParameter] {

  override def fromXml(node: Node): PolicyParameter = {
    new PolicyParameterBuilder()
      .withCharge(node \@ "charge")
      .withContent(node.text)
      .withDays((node \@ "days").toInt)
      .build()
  }

  override def toXml(any: PolicyParameter): Elem = ???

}


class PolicyParameterBuilder {

  private var charge:String = _
  private var days:Int = _
  private var content:String = _

  def withCharge(charge:String) = {
    this.charge = charge
    this
  }

  def withDays(days:Int) = {
    this.days = days
    this
  }

  def withContent(charge:String) = {
    this.charge = charge
    this
  }

  def build() = PolicyParameter(charge, days, content)


}


