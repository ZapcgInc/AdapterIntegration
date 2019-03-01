package com.zap.hai

import com.zap.hai.agoda.model.{AvailabilityResponse, Benefit, Hotel, Room}
import com.zap.hai.agoda.rac.AgodaRestClient
import com.zap.hai.controllers.ShoppingController
import com.zap.hai.eps.{EpsAmenity, EpsAvailabilityResponse, EpsPropertyAvailability, EpsRate}
import com.zap.hai.finagle.FinagleRoutes
import com.zap.hai.services.ShoppingService
import com.zap.hai.transformers.AgodaToEpsXfmr
import zap.framework.httpclient.ZapHttpClient

trait HaiFactory {

  val hotelXfmr : AgodaToEpsXfmr[Hotel, EpsPropertyAvailability]
  val availToShopResponseXfmr : AgodaToEpsXfmr[AvailabilityResponse,EpsAvailabilityResponse]
  val rateXfmr : AgodaToEpsXfmr[Room, EpsRate]
  val benefitXfmr: AgodaToEpsXfmr[Benefit, EpsAmenity]
  val finagleService : FinagleRoutes
  val shoppingController : ShoppingController
  val shoppingService: ShoppingService
  val agodaRac: AgodaRestClient
  val httpClient: ZapHttpClient

}
