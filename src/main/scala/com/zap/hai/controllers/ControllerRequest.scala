package com.zap.hai.controllers

case class ControllerRequest(params : Map[String,List[String]], body : Option[String])