package com.zap.hai

import com.zap.hai.agoda.model.{AvailabilityRequest, AvailabilityResponse, Benefit, Hotel, Room}
import com.zap.hai.agoda.rac.{AgodaRestClient, AgodaRestClientDefault}
import com.zap.hai.controllers.{ShoppingController, ShoppingControllerDefault}
import com.zap.hai.eps.{EpsAmenity, EpsAvailabilityResponse, EpsPropertyAvailability, EpsRate}
import com.zap.hai.finagle.FinagleRoutes
import com.zap.hai.services.{ShoppingService, ShoppingServiceDefault}
import com.zap.hai.transformers._
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import zap.framework.httpclient.{ZapHttpApacheClient, ZapHttpClient, ZapHttpRequest, ZapHttpResponse}

trait HaiFactoryDefault extends HaiFactory { self =>

  override lazy val httpClient: ZapHttpClient = new ZapHttpApacheClient {
    override lazy val apacheClient: CloseableHttpClient = HttpClients.createDefault()
  }

  override lazy val agodaRac: AgodaRestClient = new AgodaRestClientDefault {
    override lazy  val httpClient: ZapHttpClient = self.httpClient
  }

  override lazy val benefitXfmr: AgodaToEpsXfmr[Benefit, EpsAmenity] = new BenefitXfmr {}
  override lazy val rateXfmr : AgodaToEpsXfmr[Room, EpsRate] = new RateXfmr {
    override lazy val benefitXfmr: AgodaToEpsXfmr[Benefit, EpsAmenity] = self.benefitXfmr
  }
  override lazy val hotelXfmr : AgodaToEpsXfmr[Hotel, EpsPropertyAvailability] = new HotelPropertyXfmr {
    override lazy val rateXfmr: AgodaToEpsXfmr[Room, EpsRate] = self.rateXfmr
  }
  override lazy val availToShopResponseXfmr : AgodaToEpsXfmr[AvailabilityResponse,EpsAvailabilityResponse] = new AvailabilityResponseConverter {
    override lazy  val hotelPropTransformer: AgodaToEpsXfmr[Hotel, EpsPropertyAvailability] = hotelXfmr
  }

  override lazy val shoppingService: ShoppingService = new ShoppingServiceDefault {
    override lazy val agodaRac: AgodaRestClient = self.agodaRac
    override lazy val availToShopResponseXfmr: AgodaToEpsXfmr[AvailabilityResponse, EpsAvailabilityResponse] = self.availToShopResponseXfmr
  }

  override lazy val shoppingController : ShoppingController = new ShoppingControllerDefault {
    override lazy val shoppingService: ShoppingService = self.shoppingService
  }

  override lazy val finagleService: FinagleRoutes = new FinagleRoutes {
    override lazy val shoppingController: ShoppingController = self.shoppingController

  }

}
