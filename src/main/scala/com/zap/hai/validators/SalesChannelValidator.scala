package com.zap.hai.validators

import com.zap.hai.constant.AvailabilityRequestParams
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{EPSErrorResponseBuilder, ErrorResponse}

object SalesChannelValidator
{
  private val VALID_SALES_CHANNEL: List[String] = List("website", "agent_tool", "mobile_app", "mobile_web", "cache", "meta")

  def validate(request: ControllerRequest): Option[ErrorResponse] =
  {
    val rateOptions:String = request.params(AvailabilityRequestParams.SALES_CHANNEL.toString).head

    if (!VALID_SALES_CHANNEL.contains(rateOptions))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.SALES_CHANNEL.toString).get)
    }

    None
  }
}
