package com.zap.hai.controllers

import com.zap.hai.agoda.model.{AvailabilityRequest, AvailabilityRequestBuilder}
import com.zap.hai.services.ShoppingService
import zap.framework.httpclient._
import zap.framework.props.zaperties
import zap.framework.json._

trait ShoppingControllerDefault extends ShoppingController {

  val shoppingService : ShoppingService

  override  def getPropertyAvailability(request: ControllerRequest) : ZapHttpResponse = {
    //validate
    //convert to availability request
    //invoke service
    //common exception handling.

    val b = new AvailabilityRequestBuilder()
    //language=en-US&country_code=US&occupancy=1&sales_channel=website&sales_environment=hotel_package&sort_type=preferred"
    b.withCheckInDate(request.params.get("checkin").get.head)
    b.withCheckOutDate(request.params.get("checkout").get.head)
    b.withCurrency(request.params.get("currency").get.head)
    b.withLanguage(request.params.get("language").get.head)
    b.withAdultCount(request.params.get("occupancy").get.head.toInt)
    b.withRoomCount(1)
    b.withRequestType(4)
    b.withApiKey(zaperties.req[String]("agoda.apikey"))
    b.withSiteId(zaperties.req[String]("agoda.siteid"))
    b.withPropertyIds(request.params.get("occupancy").get.head)
    val availibilityResponse = shoppingService.getPropertyAvailability(b.build())

    //convert to zap http response

    ZapHttpResponse(200,"",Some(ZapHttpStringEntity(availibilityResponse.properties.toJsonPretty)),("",""))
  }

  override def priceCheck(request: ControllerRequest): ZapHttpResponse = {
    //extract fields to make message
    //validate request 400

    //create PriceCheckRequest
    //call priceService
    //

    ???
  }

}
