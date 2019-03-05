package com.zap.hai.validators

import com.zap.hai.constant.AvailabilityRequestParams
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{EPSErrorMessage, EPSErrorResponseBuilder}

object SalesEnvironmentValidator
{
  private val VALID_SALES_ENV: List[String] = List("hotel_only", "hotel_package", "loyalty")

  def validate(request: ControllerRequest): Option[EPSErrorMessage] =
  {
    val sales:String = request.params(AvailabilityRequestParams.SALES_ENVIRONMENT.toString).head

    if (!VALID_SALES_ENV.contains(sales))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.SALES_ENVIRONMENT.toString).get)
    }

    None
  }
}
