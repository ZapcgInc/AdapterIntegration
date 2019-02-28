package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

case class EpsRoomRate(
                        nightly: List[List[EpsPrice]],
                        stay: Array[EpsPrice],
                        fees: Array[EpsPrice],
                        totals: EpsTotalPrice
                      )
