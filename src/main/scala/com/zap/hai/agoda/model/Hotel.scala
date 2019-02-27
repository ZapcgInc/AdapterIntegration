package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.collection.mutable.ArrayBuffer
import scala.xml._


case class Hotel(id: String, cheapestRoom: CheapestRoom, paxSettings: PaxSettings, rooms: List[Room])

object Hotel extends XmlSupport[Hotel] {

  override def fromXml(node: Node): Hotel = {
    val builder = new HotelBuilder()
    builder.withId((node \ "Id").text)
    (node \ "CheapestRoom").map{n => builder.withCheapestRoom(CheapestRoom.fromXml(n))}
    (node \ "PaxSettings").map{n => builder.withPaxSettings(PaxSettings.fromXml(n))}
    (node \ "Rooms" \\ "Room").map{n => builder.addRoom(Room.fromXml(n))}
    builder.build()
  }

  override def toXml(any: Hotel): Elem = ???

}

class HotelBuilder {

  var id: String = _
  var cheapestRoom: CheapestRoom = _
  var paxSettings: PaxSettings = _
  var rooms: ArrayBuffer[Room] = ArrayBuffer[Room]()

  def withId(id: String) = {
    this.id = id
    this
  }

  def withCheapestRoom(cheapestRoom: CheapestRoom) = {
    this.cheapestRoom = cheapestRoom
    this
  }

  def withPaxSettings(paxSettings: PaxSettings) = {
    this.paxSettings = paxSettings
    this
  }

  def addRoom(room: Room) = {
    rooms += room
    this
  }

  def build() = new Hotel(id, cheapestRoom, paxSettings, rooms.toList)

}