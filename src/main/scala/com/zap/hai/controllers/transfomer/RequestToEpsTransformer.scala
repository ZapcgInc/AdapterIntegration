package com.zap.hai.controllers.transfomer

import com.zap.hai.controllers.ControllerRequest

trait RequestToEpsTransformer[T] {

  def transform(request: ControllerRequest) : T

}
