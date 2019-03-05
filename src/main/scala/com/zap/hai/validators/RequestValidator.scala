package com.zap.hai.validators

import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.EPSErrorResponse

trait RequestValidator {

  def validate(request: ControllerRequest): EPSErrorResponse

}
