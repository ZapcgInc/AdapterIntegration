package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml._


case class PolicyDate(before: String, after: String, rate: Rate)

object PolicyDate extends XmlSupport[PolicyDate] {

  override def fromXml(node: Node): PolicyDate = {
    new PolicyDateBuilder()
      .withBefore(node \@ "before")
      .withAfter(node \@ "after")
      .withRate(node \ "Rate")
      .build()
  }

  override def toXml(any: PolicyDate): Elem = ???

}

class PolicyDateBuilder {

  private var before: String = _
  private var after: String = _
  private var rate: Rate = _

  def withBefore(before: String) = {
    this.before = before
    this
  }

  def withAfter(after: String) = {
    this.after = after
    this
  }

  def withRate(ns: NodeSeq) = {
    ns.map { n => this.rate = ns.toRate }
    this
  }

  def build() = PolicyDate(before, after, rate)
}
