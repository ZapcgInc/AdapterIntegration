package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class PromotionType(id: String, text: String)

object PromotionType extends XmlSupport[PromotionType] {

  override def fromXml(node: Node): PromotionType = {
    val builder = new PromotionTypeBuilder()
    (node \ "Id").map{n => builder.withId(n.text)}
    (node \ "Text").map{n => builder.withText(n.text)}
    builder.build()
  }

  override def toXml(any: PromotionType): Elem = ???

}

class PromotionTypeBuilder {

  private var id: String = _
  private var text: String = _

  def withId(id: String) = {
    this.id = id
    this
  }

  def withText(text: String) = {
    this.text = text
    this
  }

  def build() = PromotionType(id, text)
}
