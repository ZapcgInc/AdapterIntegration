package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class Benefit(id:Int, name:String, translation:String)

object Benefit extends XmlSupport[Benefit] {

  override def fromXml(node: Node): Benefit = {
    new BenefitBuilder()
      .withId((node \@ "id").toInt)
      .withName((node \ "Name").text)
      .withTranslation((node \ "Translation").text)
      .build()
  }

  override def toXml(any: Benefit): Elem = ???

}

class BenefitBuilder {

  private var id:Int = _
  private var name:String = _
  private var translation:String = _

  def withId(id:Int)={
    this.id = id
    this
  }
  def withName(name:String)={
    this.name = name
    this
  }
  def withTranslation(translation:String)={
    this.translation = translation
    this
  }
  def build() = Benefit(id, name, translation)

}


