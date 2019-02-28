package com.zap.hai.eps

import com.fasterxml.jackson.annotation.JsonProperty

case class PropertyAvailabilityPrice(
                                      @JsonProperty("type") priceType: String,
                                      value: Double,
                                      currency: String
                                    )
