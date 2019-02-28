package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

case class EpsPropertyAvailability(
                                    @JsonProperty("property_id") propertyId: String,
                                    rooms: List[EpsRoom],
                                    links: Map[String, EpsLink],
                               )
