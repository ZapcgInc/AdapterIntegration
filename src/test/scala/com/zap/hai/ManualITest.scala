package com.zap.hai

import com.zap.hai.agoda.model.{AvailabilityRequest, AvailabilityRequestBuilder}
import zap.framework.props._


// sbt -Dts.config.path="../configs/agoda.conf" "test:runMain com.zap.hai.ManualITest"
object ManualITest extends App {

  new ManualITest().executeOne()

}

class ManualITest {


  def executeOne()={

    val factory = new HaiFactoryDefault {}
    val builder = new AvailabilityRequestBuilder()
    builder.withApiKey(zaperties.req[String]("agoda.apikey"))
    builder.withSiteId(zaperties.req[String]("agoda.siteid"))
    builder.withRequestType(4)
    builder.withAdultCount(1)
    builder.withRoomCount(1)
    builder.withCheckInDate("2019-04-01")
    builder.withCheckOutDate("2019-04-03")
    builder.withCurrency("USD")
    builder.withLanguage("en-us")
    builder.withPropertyIds("482")

    val either = factory.agodaRac.affliateLongSearch(builder.build())
    println(either)
  }

}
