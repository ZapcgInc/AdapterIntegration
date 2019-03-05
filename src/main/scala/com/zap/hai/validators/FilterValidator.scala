package com.zap.hai.validators

import com.zap.hai.constant.AvailabilityRequestParams
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{EPSErrorMessage, EPSErrorResponseBuilder}

object FilterValidator {
  private val VALID_FILTER_OPTION: List[String] = List("refundable", "expedia_collect","property_collect")

  def validate(request:ControllerRequest): Option[EPSErrorMessage] =
  {
    val filter:String = request.params(AvailabilityRequestParams.FILTER.toString).head

    if (filter!=null && !VALID_FILTER_OPTION.contains(filter))
    {
      Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.FILTER.toString).get)
    }
    None
  }
}
