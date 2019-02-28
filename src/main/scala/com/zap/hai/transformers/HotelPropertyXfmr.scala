package com.zap.hai.transformers

import com.zap.hai.agoda.model.Hotel
import com.zap.hai.eps.EpsPropertyAvailability

trait HotelPropertyXfmr extends AgodaToEpsXfmr[Hotel, EpsPropertyAvailability] {

  override def transform(i: Hotel): EpsPropertyAvailability = {

    ???

  }

}
