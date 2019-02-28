package com.zap.hai.eps

import com.fasterxml.jackson.annotation.JsonProperty

case class EpsPrice(
                     @JsonProperty("type") priceType: String,
                     value: String,
                     currency: String
                   )
