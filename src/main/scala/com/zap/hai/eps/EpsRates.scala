package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

case class EpsRates(
                     id: String,
                     @JsonProperty("available_rooms") availableRooms: Int,
                     refundable: Boolean,
                     @JsonProperty("deposit_required") depositRequired: Boolean,
                     @JsonProperty("fenced_deal") fencedDeal: Boolean,
                     @JsonProperty("merchant_of_record") merchantOfRecord: String,
                     links: Map[String, EpsLink],
                     @JsonProperty("bed_groups") bedGroups: List[EpsBedGroup],
                     @JsonProperty("cancel_penalties") cancelPolicies: List[EpsCancelPenalty],
                     amenities: List[EpsAmenities],
                     @JsonProperty("occupancies") roomPriceByOccupancy: Map[String, EpsRoomRate],
                     @JsonProperty("promo_id") promoId: String,
                     @JsonProperty("promo_description") promoDesc: String

                   )
