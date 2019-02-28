package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

case class EpsRoom(id: String,
                   @JsonProperty("room_name") room_name: Option[String],
                   rates: List[EpsRates]
                  )
