package com.zap.hai.transformers

import com.zap.hai.agoda.model.{AvailabilityResponse, Hotel}
import com.zap.hai.eps.{EpsAvailabilityResponse, EpsPropertyAvailability}

trait AvailabilityResponseConverter extends AgodaToEpsXfmr[AvailabilityResponse,EpsAvailabilityResponse] {

  val hotelPropTransformer : AgodaToEpsXfmr[Hotel, EpsPropertyAvailability]

  override def transform(agoda: AvailabilityResponse): EpsAvailabilityResponse = {
    val list = agoda.hotels.map{hotel => hotelPropTransformer.transform(hotel)}
    new EpsAvailabilityResponse(list)
  }

}
