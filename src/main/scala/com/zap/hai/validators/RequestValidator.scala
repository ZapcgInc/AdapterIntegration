package com.zap.hai.validators

import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.ErrorResponse

trait RequestValidator {


  def validate(request : ControllerRequest) :  Option[(ErrorResponse)]


}
