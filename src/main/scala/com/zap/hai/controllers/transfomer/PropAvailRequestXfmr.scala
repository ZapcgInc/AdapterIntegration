package com.zap.hai.controllers.transfomer

import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.EpsAvailabilityRequest

trait PropAvailRequestXfmr extends RequestToEpsTransformer[EpsAvailabilityRequest] {

  override def transform(request: ControllerRequest): EpsAvailabilityRequest = {

    ???

  }

}
