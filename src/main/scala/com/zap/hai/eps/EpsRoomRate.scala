package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}
import zap.framework.xml.XmlSupport

import scala.xml.Node

case class EpsRoomRate(
                        nightly: List[List[EpsPrice]],
                        stay: Array[EpsPrice],
                        fees: Array[EpsPrice],
                        totals: EpsTotalPrice
                      )

class EpsRoomRateBuilder {
  def build()= EpsRoomRate(nightly,stay,fees,totals)

  var nightly: List[List[EpsPrice]] = _
   var stay: Array[EpsPrice] = _
   var fees: Array[EpsPrice] = _
   var totals: EpsTotalPrice = _

}


