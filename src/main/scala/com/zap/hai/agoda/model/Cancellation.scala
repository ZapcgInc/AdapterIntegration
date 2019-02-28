package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}


case class Cancellation(policyText: String, policyTranslated: String, policyDates: List[PolicyDate], policyParameters: List[PolicyParameter])

object Cancellation extends XmlSupport[Cancellation] {

  override def fromXml(node: Node): Cancellation = {
    val builder = new CancellationBuilder()
      .withPolicyText((node \ "PolicyText").text)
      .withPolicyTranslated((node \ "PolicyTranslated").text)
    (node \ "PolicyDates" \\ "PolicyDate").map{n => builder.addPolicyDate(n.toPolicyDate)}
    (node \ "PolicyParameters" \\ "PolicyParameter").map{n => builder.addPolicyParameters(n.toPolicyParameter)}
    builder.build()
  }

  override def toXml(any: Cancellation): Elem = ???

}

class CancellationBuilder {

  private var policyText: String = _
  private var policyTranslated: String = _
  private var policyDates: List[PolicyDate] = _
  private var policyParameters: List[PolicyParameter] = _


  def withPolicyText(policyText: String) = {
    this.policyText = policyText
    this
  }

  def withPolicyTranslated(policyTranslated: String) = {
    this.policyTranslated = policyTranslated
    this
  }

  def addPolicyDate(pd: PolicyDate) = {
    if (policyDates == null) policyDates = Nil
    policyDates = policyDates ++ List(pd)
  }

  def addPolicyParameters(policyParameter: PolicyParameter) = {
    if (policyParameters == null) policyParameters = Nil
    policyParameters = policyParameters ++ List(policyParameter)
  }

  def build() = Cancellation(policyText, policyTranslated, policyDates, policyParameters)

}
