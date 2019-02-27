package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class MaxRoomOccupancy(normalBedding: String, extraBeds: String)

object MaxRoomOccupancy extends XmlSupport[MaxRoomOccupancy] {

  override def fromXml(node: Node): MaxRoomOccupancy = {
    val builder = new MaxRoomOccupanyBuilder()
    builder.withExtraBeds(node \@ "extrabeds")
    builder.withNormalBedding(node \@ "normalbedding")
    builder.build()
  }

  override def toXml(any: MaxRoomOccupancy): Elem = ???

}


class MaxRoomOccupanyBuilder {

  private var normalBedding: String = _
  private var extraBeds: String = _

  def withNormalBedding(normalBedding: String) = {
    this.normalBedding = normalBedding
    this
  }

  def withExtraBeds(extraBeds: String) = {
    this.extraBeds = extraBeds
    this
  }

  def build() = MaxRoomOccupancy(normalBedding, extraBeds)

}