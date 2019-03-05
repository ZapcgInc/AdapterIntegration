package com.zap.hai.transformers

import com.zap.hai.agoda.model.{Hotel, Rate, Room}
import com.zap.hai.eps._

trait HotelPropertyXfmr extends AgodaToEpsXfmr[Hotel, EpsPropertyAvailability] {

  val rateXfmr: AgodaToEpsXfmr[Room, EpsRate]


  override def transform(hotel: Hotel): EpsPropertyAvailability = {
    val builder = new EpsPropertyAvailabilityBuilder()

    val grouped = hotel.rooms.groupBy(_.id)

    grouped.map { t =>
      val epsRoomBuilder = new EpsRoomBuilder()
      //xfmr room to room
      val r = t._2.head
      epsRoomBuilder.withRoomName(r.name)
      epsRoomBuilder.withId(r.id)
      builder.withPropertyId(hotel.id)
      builder.addRoom(epsRoomBuilder.build())


      t._2.map { room =>
        epsRoomBuilder.addRate(rateXfmr.transform(room))
      }
      builder.addRoom(epsRoomBuilder.build())

    }

    builder.build()
  }

}