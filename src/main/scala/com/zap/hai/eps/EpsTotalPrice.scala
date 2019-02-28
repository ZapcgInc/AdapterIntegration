package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

case class EpsTotalPrice(

                          @JsonProperty("inclusive") var inclusive: EpsPriceWithCurrency,
                          @JsonProperty("exclusive") var exclusive: EpsPriceWithCurrency,
                          @JsonProperty("strikethrough") var strikeThrough: EpsPriceWithCurrency,
                          @JsonProperty("marketing_fee") var marketingFee: EpsPriceWithCurrency,
                          @JsonProperty("minimum_selling_price") var minSellingPrice: EpsPriceWithCurrency,

                        )
