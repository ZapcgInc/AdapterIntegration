package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

class PropertyAvailabilityRoomRates(
                                     @JsonProperty("nightly") var nightlyPrice: List[List[PropertyAvailabilityPrice]],
                                     @JsonProperty("stay") stayPrice: Array[PropertyAvailabilityPrice],
                                     @JsonProperty("fees") fees: Array[PropertyAvailabilityPrice],
                                     @JsonProperty("totals") totals: PropertyAvailabilityTotalPrice
                                   )
