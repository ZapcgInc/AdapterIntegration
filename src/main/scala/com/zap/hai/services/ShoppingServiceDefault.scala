package com.zap.hai.services

import com.zap.hai.agoda.model.{AvailabilityRequest, AvailabilityResponse}
import com.zap.hai.agoda.rac.AgodaRestClient
import com.zap.hai.eps.EpsAvailabilityResponse
import com.zap.hai.transformers.AgodaToEpsXfmr

trait ShoppingServiceDefault extends ShoppingService {

  val agodaRac : AgodaRestClient
  val availToShopResponseXfmr : AgodaToEpsXfmr[AvailabilityResponse,EpsAvailabilityResponse]

  override def getPropertyAvailability(request : AvailabilityRequest) : EpsAvailabilityResponse = {

    agodaRac.affliateLongSearch(request) match {
      case Right(r) => {
        availToShopResponseXfmr.transform(r)
      }
      case Left(l) => {
        throw new RuntimeException()
      }
    }

  }

}
