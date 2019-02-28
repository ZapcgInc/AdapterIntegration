package com.zap.hai

import com.zap.hai.agoda.model.{AvailabilityRequest, AvailabilityResponse, Hotel}
import com.zap.hai.agoda.rac.{AgodaRestClient, AgodaRestClientDefault}
import com.zap.hai.controllers.{ShoppingController, ShoppingControllerDefault}
import com.zap.hai.eps.{EpsAvailabilityResponse, EpsPropertyAvailability}
import com.zap.hai.finagle.FinagleRoutes
import com.zap.hai.services.{ShoppingService, ShoppingServiceDefault}
import com.zap.hai.transformers.{AgodaToEpsXfmr, AvailabilityResponseConverter, HotelPropertyXfmr}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import zap.framework.httpclient.{ZapHttpApacheClient, ZapHttpClient, ZapHttpRequest, ZapHttpResponse}

trait HaiFactoryDefault extends HaiFactory { self =>

  override lazy val httpClient: ZapHttpClient = new ZapHttpApacheClient {
    override val apacheClient: CloseableHttpClient = HttpClients.createDefault()
  }

  override val agodaRac: AgodaRestClient = new AgodaRestClientDefault {
    override val httpClient: ZapHttpClient = self.httpClient
  }

  val hotelXfmr : AgodaToEpsXfmr[Hotel, EpsPropertyAvailability] = new HotelPropertyXfmr {}
  val availToShopResponseXfmr : AgodaToEpsXfmr[AvailabilityResponse,EpsAvailabilityResponse] = new AvailabilityResponseConverter {
    override val hotelPropTransformer: AgodaToEpsXfmr[Hotel, EpsPropertyAvailability] = hotelXfmr
  }

  override val shoppingService: ShoppingService = new ShoppingServiceDefault {
    override val agodaRac: AgodaRestClient = self.agodaRac
    override val availToShopResponseXfmr: AgodaToEpsXfmr[AvailabilityResponse, EpsAvailabilityResponse] = self.availToShopResponseXfmr
  }

  override val shoppingController : ShoppingController = new ShoppingControllerDefault {
    override val shoppingService: ShoppingService = self.shoppingService
  }

  override val finagleService: FinagleRoutes = new FinagleRoutes {
    override val shoppingController: ShoppingController = self.shoppingController

  }

}
