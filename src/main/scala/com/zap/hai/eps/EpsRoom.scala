package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

case class EpsRoom(id: String,
                   @JsonProperty("room_name") roomName: String,
                   rates: List[EpsRate]
                  )


class EpsRoomBuilder {
  private var id: String = _
  private var roomName: String = _
  private var rates: List[EpsRate] = _

  def withId(id: String) = {
    this.id = id
    this
  }

  def withRoomName(roomName: String) = {
    this.roomName = roomName
    this
  }

  def addRate(rate: EpsRate) = {
    if (rates == null) rates = List()
    rates = rates ++ List(rate)
  }

  def build() = EpsRoom(id, roomName, rates)

}