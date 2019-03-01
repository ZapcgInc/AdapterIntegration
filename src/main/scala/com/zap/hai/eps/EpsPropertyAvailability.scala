package com.zap.hai.eps

import com.fasterxml.jackson.annotation.JsonProperty

case class EpsPropertyAvailability(
                                    @JsonProperty("property_id") propertyId: String,
                                    rooms: List[EpsRoom],
                                    links: Map[String, EpsLink],
                                  )


class EpsPropertyAvailabilityBuilder {

  private var propertyId: String = _
  private var rooms: List[EpsRoom] = _
  private var links: Map[String, EpsLink] = _

  def addRoom(room: EpsRoom) = {
    if (rooms == null) rooms = List()
    rooms = rooms ++ List(room)
    this
  }

  def withLink(name: String, method: String, url: String) = {
    if (links == null) links = Map()
    links = links ++ Map(name -> EpsLink(method, url))
    this
  }

  def withPropertyId(propertyId: String) = {
    this.propertyId = propertyId
    this
  }

  def build() = EpsPropertyAvailability(propertyId, rooms, links)
}