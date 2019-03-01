package com.zap.hai.validators

import com.zap.hai.constant.AvailabilityRequestParams
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{EPSErrorResponseBuilder, ErrorResponse}

object SortTypeValidator
{
  private val VALID_SORT_TYPE: List[String] = List("preferred")

  def validate(request: ControllerRequest): Option[ErrorResponse] =
  {
    val sortType:String = request.params(AvailabilityRequestParams.SORT_TYPE.toString).head

    if (!VALID_SORT_TYPE.contains(sortType))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.SORT_TYPE.toString).get)
    }

    None
  }
}
