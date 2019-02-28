package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, Node, NodeSeq}

case class Room(id:String, name : String, lineItemId: Int, ratePlan:String, rateType:String, currency:String, model:String,
                rateCategoryId:String, bockId:String, standardTranslation:String, maxRoomOccupancy:MaxRoomOccupancy,
                remainingRooms:Int, rateInfo:RateInfo, cancellation:Cancellation, benefits : List[Benefit])

object Room extends XmlSupport[Room] {


  override def fromXml(node: Node): Room = {
    val builder = new RoomBuilder()
    builder.withId((node \@ "id"))
    builder.withName((node \@ "name"))
    builder.withLineItemId((node \@ "lineitemid"))
    builder.withRatePlan(node \@ "rateplan")
    builder.withRateType(node \@ "ratetype")
    builder.withCurrency(node \@ "currency")
    builder.withModel(node \@ "model")
    builder.withRateCategoryId(node \@ "ratecategoryid")
    builder.withBockId(node \@ "blockid")
    builder.withStdTrans((node \ "StandardTranslation").text)
    (node \ "MaxRoomOccupancy").map{n => builder.withMaxRoomOccupancy(n.toMaxRoomOccupancy)}
    builder.withRemainingRooms((node \ "RemainingRooms").text)
    (node \ "RateInfo").map{n => builder.withRateInfo(n.toRateInfo)}
    (node \ "Cancellation").map{n => builder.withCancellation(n.toCancellation)}
    (node \ "Benefits" \\ "Benefit").map{n => builder.addBenefits(n.toBenefit)}
    builder.build()
  }

  override def toXml(any: Room): Elem = ???


}


class RoomBuilder {

  private var id:String = _
  private var name : String = _
  private var lineItemId: Int= _
  private var ratePlan:String= _
  private var rateType:String= _
  private var currency:String= _
  private var model:String= _
  private var rateCategoryId:String= _
  private var bockId:String= _
  private var standardTranslation:String= _
  private var maxRoomOccupancy:MaxRoomOccupancy= _
  private var remainingRooms:Int= _
  private var rateInfo:RateInfo= _
  private var cancellation:Cancellation= _
  private var benefits : List[Benefit]= _

  def withRateType(rateType : String)={
    this.rateType = rateType
    this
  }
  def addBenefits(benefit: Benefit)={
    if(benefits == null) benefits = List()
    benefits = benefits ++ List(benefit)
  }

  def withCancellation(cancellation: Cancellation)={
    this.cancellation = cancellation
    this
  }

  def withRateInfo(rateInfo:RateInfo)={
    this.rateInfo = rateInfo
    this
  }

  def withRemainingRooms(remainingRooms : String)={
    if(remainingRooms != ""){this.remainingRooms = remainingRooms.toInt}
    this
  }

  def withMaxRoomOccupancy(maxRoomOccupancy: MaxRoomOccupancy) = {
    this.maxRoomOccupancy= maxRoomOccupancy
    this
  }

  def withStdTrans(stdTrans : String)={
    this.standardTranslation = stdTrans
    this
  }

  def withId(id:String)={
    this.id = id
    this
  }

  def withName(name:String) = {
    this.name = name
    this
  }

  def withLineItemId(lineItemId : String) = {
    if(lineItemId != ""){
      this.lineItemId= lineItemId.toInt
    }
    this
  }

  def withRatePlan(ratePlan:String) = {
    this.ratePlan = ratePlan
    this
  }

  def withCurrency(currency:String)={
    this.currency =currency
    this
  }

  def withModel(model:String) = {
    this.model = model
    this
  }

  def withRateCategoryId(rateCategoryId : String) = {
    this.rateCategoryId = rateCategoryId
    this
  }

  def withBockId(bockId:String) = {
    this.bockId = bockId
    this
  }


  def build() = {
    Room(id, name, lineItemId, ratePlan, rateType, currency, model, rateCategoryId, bockId, standardTranslation, maxRoomOccupancy,
      remainingRooms, rateInfo, cancellation, benefits)
  }

}
