package com.zap.hai.services

import com.zap.hai.agoda.model._
import com.zap.hai.eps.EpsAvailabilityResponse

trait ShoppingService {

//  //getPropertyRoomRatesAndAvailability
    def getPropertyAvailability(request : AvailabilityRequest) : EpsAvailabilityResponse
//
//    //getCurrentPriceForPreBooking()
//    def priceCheck(request : PriceCheckRequest) : PriceCheckResponse

}


