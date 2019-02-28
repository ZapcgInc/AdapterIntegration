package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

case class PropertyAvailabilityPriceWithCurrency(
                                                  @JsonProperty("billable_currency") billable: PropertyAvailabilityPrice,
                                                  @JsonProperty("request_currency") request: PropertyAvailabilityPrice
                                                )
