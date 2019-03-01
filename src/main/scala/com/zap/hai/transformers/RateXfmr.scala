package com.zap.hai.transformers

import com.zap.hai.agoda.model.{Benefit, Room}
import com.zap.hai.eps._


/*
rooms.rates.id
rooms.rates.available_rooms
rooms.rates.refundable
rooms.rates.fenced_deal
rooms.rates.deposit_required
rooms.rates.merchant_of_record
 */
trait RateXfmr extends AgodaToEpsXfmr[Room, EpsRate]{

  val benefitXfmr : AgodaToEpsXfmr[Benefit, EpsAmenity]

  //do the rest here.
  override def transform(room: Room): EpsRate = {

    val builder = new EpsRateBuilder()
    builder.withId(room.rateCategoryId)
    builder.withAvailableRooms(room.remainingRooms)

    val ppDays = room.cancellation.policyParameters.headOption.map{pp => pp.days}
    ppDays.filter(_ == 365).map{_ => builder.withIsRefundable(false)}
    ppDays.filter(_ != 365).map{_ => builder.withIsRefundable(true)}

    builder.withFencedDeal(false)
    builder.withIsDepositRequired(false)

    room.rateInfo.promoType.map{pt =>
      builder.withPromoId(pt.id)
      builder.withPromoDesc(pt.text)
    }

    room.benefits.headOption.map{b => benefitXfmr.transform(b)}.map{a => builder.addAmenities(a)}

    builder.build()
  }

}
