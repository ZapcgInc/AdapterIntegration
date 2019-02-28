package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class Surcharge(id: String, method: String, charge: String, margin: String, name: String, rate: Rate)

object Surcharge extends XmlSupport[Surcharge] {

  override def fromXml(node: Node): Surcharge = {
    val builder = new SurchargeBuilder()
    builder.withCharge(node \@ "charge")
    builder.withId(node \@ "id")
    builder.withMargin(node \@ "margin")
    builder.withMethod(node \@ "method")
    builder.withName((node \ "Name").text)
    (node \ "Rate").map{ns => builder.withRate(ns.toRate)}
    builder.build()
  }

  override def toXml(any: Surcharge): Elem = ???


}

class SurchargeBuilder {

  private var id: String = _
  private var method: String = _
  private var charge: String = _
  private var margin: String = _
  private var name: String = _
  private var rate: Rate = _

  def withRate(rate:Rate)={
    this.rate = rate
    this
  }

  def withName(name:String)={
    this.name = name
    this
  }

  def withMargin(margin:String)={
    this.margin = margin
    this
  }

  def withCharge(charge:String)={
    this.charge = charge
    this
  }

  def withMethod(method:String)={
    this.method = method
    this
  }

  def withId(id:String)={
    this.id = id
    this
  }

  def build() = Surcharge(id, method, charge, margin, name, rate)

}
