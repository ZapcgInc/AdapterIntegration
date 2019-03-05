package com.zap.hai.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

case class EpsRate(
                    id: String,
                    @JsonProperty("available_rooms") availableRooms: Int,
                    refundable: Boolean,
                    @JsonProperty("deposit_required") depositRequired: Boolean,
                    @JsonProperty("fenced_deal") fencedDeal: Boolean,
                    @JsonProperty("merchant_of_record") merchantOfRecord: String,
                    links: Map[String, EpsLink],
                    @JsonProperty("bed_groups") bedGroups: List[EpsBedGroup],
                    @JsonProperty("cancel_penalties") cancelPolicies: List[EpsCancelPenalty],
                    amenities: List[EpsAmenity],
                    @JsonProperty("occupancies") roomPriceByOccupancy: Map[String, EpsRoomRate],
                    @JsonProperty("promo_id") promoId: String,
                    @JsonProperty("promo_description") promoDesc: String

                  )


class EpsRateBuilder {
  private var id: String = _
  private var availableRooms: Int = _
  private var refundable: Boolean = _
  private var depositRequired: Boolean = _
  private var fencedDeal: Boolean = _
  private var merchantOfRecord: String = _
  private var promoId: String = _
  private var promoDesc: String = _
  private var links: Map[String, EpsLink] = _
  private var bedGroups: List[EpsBedGroup] = _
  private var cancelPolicies: List[EpsCancelPenalty] = _
  private var amenities: List[EpsAmenity] = _
  private var roomPriceByOccupancy: Map[String, EpsRoomRate] = _

  def addAmenities(amenity : EpsAmenity) = {
    if(amenities == null) amenities = List()
    amenities = amenities ++ List(amenity)
    this
  }

  def addLinks(linksMap:Map[String,EpsLink])={
    this.links = linksMap
    this
  }

  def addRate(roomRate: EpsRoomRate, occupancy: String): Map[String, EpsRoomRate] ={
    println("RoomRate----"+roomRate+"occupancy"+occupancy)
    if(roomPriceByOccupancy == null) roomPriceByOccupancy = Map()
    roomPriceByOccupancy = roomPriceByOccupancy ++ Map(occupancy->roomRate)
    roomPriceByOccupancy
  }


  def addBedgroups(epsBedGroup: EpsBedGroup) = {
    if(bedGroups == null) bedGroups = List()
    bedGroups = bedGroups ++ List(epsBedGroup)
    this
  }

  def addCancelPolicies(cancelPolicy: EpsCancelPenalty) = {
    if(cancelPolicies == null) cancelPolicies = List()
    cancelPolicies = cancelPolicies ++ List(cancelPolicy)
    this
  }

  def withPromoId(promoId:String)={
    this.promoId = promoId
    this
  }

  def withPromoDesc(promoDesc : String)= {
    this.promoDesc = promoDesc
    this
  }

  def withMerchantOfRecord(merchantOfRecord: String) = {
    this.merchantOfRecord = merchantOfRecord
    this
  }

  def withFencedDeal(isFencedDeal: Boolean) = {
    this.fencedDeal = isFencedDeal
    this
  }

  def withIsDepositRequired(isDepositRequird: Boolean) = {
    this.depositRequired = isDepositRequird
    this
  }

  def withIsRefundable(isRefundable: Boolean) = {
    this.refundable = isRefundable
    this
  }

  def withAvailableRooms(availableRooms: Int) = {
    this.availableRooms = availableRooms
    this
  }

  def withId(id: String) = {
    this.id = id
    this
  }

  def withLinks(linksMap:Map[String,EpsLink])={
    this.links = linksMap
    this
  }



  def build() = {
    EpsRate(id: String, availableRooms: Int, refundable: Boolean, depositRequired: Boolean, fencedDeal: Boolean,
      merchantOfRecord: String, links: Map[String, EpsLink], bedGroups: List[EpsBedGroup], cancelPolicies: List[EpsCancelPenalty],
      amenities: List[EpsAmenity], roomPriceByOccupancy: Map[String, EpsRoomRate], promoId: String, promoDesc: String
    )

  }


}