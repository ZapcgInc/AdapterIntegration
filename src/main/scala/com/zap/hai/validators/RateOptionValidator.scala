package com.zap.hai.validators

import com.zap.hai.constant.AvailabilityRequestParams
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{EPSErrorMessage, EPSErrorResponseBuilder}

object RateOptionValidator
{
  private val VALID_RATE_OPTION: List[String] = List("net_rates", "closed_user_group")

  def validate(request: ControllerRequest): Option[EPSErrorMessage] =
  {
    val rateOptions:String = request.params(AvailabilityRequestParams.RATE_OPTION.toString).head

    if (rateOptions!=null && !VALID_RATE_OPTION.contains(rateOptions))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.RATE_OPTION.toString).get)
    }

    None
  }
}
