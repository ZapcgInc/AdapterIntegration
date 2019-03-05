package com.zap.hai.transformers

import com.twitter.finagle.http.Method
import com.zap.hai.agoda.model.{Benefit, Room}
import com.zap.hai.eps._


/*
rooms.rates.id
rooms.rates.available_rooms
rooms.rates.refundable
rooms.rates.fenced_deal
rooms.rates.deposit_required
rooms.rates.merchant_of_record
 */
trait RateXfmr extends AgodaToEpsXfmr[Room, EpsRate]{

  val benefitXfmr : AgodaToEpsXfmr[Benefit, EpsAmenity]
  var totalPriceInclusive : Double = 0
  var totalPriceExclusive : Double = 0

  //do the rest here.
  override def transform(room: Room): EpsRate = {

    val rateBuilder = new EpsRateBuilder()
    rateBuilder.withId(room.rateCategoryId)
    rateBuilder.withAvailableRooms(room.remainingRooms)

    val ppDays = room.cancellation.policyParameters.headOption.map{pp => pp.days}
    ppDays.filter(_ == 365).map{_ => rateBuilder.withIsRefundable(false)}
    ppDays.filter(_ != 365).map{_ => rateBuilder.withIsRefundable(true)}


    rateBuilder.withFencedDeal(false)
    rateBuilder.withIsDepositRequired(false)
    rateBuilder.withMerchantOfRecord("merchant")

    room.rateInfo.promoType.map{pt =>
      rateBuilder.withPromoId(pt.id)
      rateBuilder.withPromoDesc(pt.text)
    }

    //populate rate links
    rateBuilder.withLinks(null)

    // polulate Amenities
    room.benefits.headOption.map{b => benefitXfmr.transform(b)}.map{a => rateBuilder.addAmenities(a)}

    // populate CancelPolicies
    if (room.rateInfo.totalPayAmt.inclusive != 0 && room.cancellation.policyDates != null) room.cancellation.policyDates.map(policyDate => rateBuilder.addCancelPolicies(
      new EpsCancelPenalty(room.currency,
      Option(policyDate.after).getOrElse(""),Option(policyDate.before).getOrElse(""),policyDate.rate.inclusive.toString,null,null)))
    else Option.empty

    // populate Bed Groups
    val href = ""
    val bedGroup = new EpsBedGroup(Map("price_check" -> new EpsLink(Method.Get.toString, href)),Nil)
    rateBuilder.addBedgroups(bedGroup)

    // populate ratesByOccupancy
    populateRates(room)


    rateBuilder.build()
  }

  def _populateNightlyPrice(room: Room): List[List[EpsPrice]] = {
    //val lengthOfStay: Int = _calculateLengthOfStay(request)
    val lengthOfStay = 3
    val base_rate = room.rateInfo.rate.exclusive
    println("base rate"+base_rate)
    val base_rate_temp = base_rate*1.1111
    val new_base_rate = Math.round(base_rate_temp*100.0) / 100.0

    val tax_and_service_fee = room.rateInfo.rate.fees+room.rateInfo.rate.tax
    println("new base rate"+new_base_rate)
    val priceList = List(
      new EpsPrice("base_rate", new_base_rate.toString, room.currency),
      //            new PropertyAvailabilityPrice("sales_tax", r.rateInfo.rate.tax, r.currency),
      new EpsPrice("tax_and_service_fee", (room.rateInfo.rate.fees+room.rateInfo.rate.tax).toString, room.currency))

    totalPriceInclusive = (new_base_rate+tax_and_service_fee)*lengthOfStay

    totalPriceExclusive =new_base_rate * lengthOfStay

    List.fill(lengthOfStay)(priceList)
  }
  def _populateSurcharges(room: Room):Option[(Array[EpsPrice] /*stayPrice*/ , Array[EpsPrice]) /*fees*/ ] = {
    if (room.rateInfo.surcharges != null)
    {
      var stayPrices: Array[EpsPrice] = null
      var fees: Array[EpsPrice] = null

      for (surcharge <- room.rateInfo.surcharges)
      {
        if (surcharge.charge != null)
        {
          if (surcharge.charge.equals("Excluded"))
          {
            val taxAndServiceFee = surcharge.rate.tax +surcharge.rate.fees
            fees = Array(
              new EpsPrice("mandatory_fee", surcharge.rate.exclusive.toString, room.currency),
              new EpsPrice("tax_and_service_fee", taxAndServiceFee.toString, room.currency)
            )
          }
          else
          {
            val taxAndServiceFee = surcharge.rate.tax+surcharge.rate.fees
            val propertyFee = surcharge.rate.exclusive
            stayPrices = Array(
              new EpsPrice("Property_fee", propertyFee.toString, room.currency),
              new EpsPrice("tax_and_service_fee", taxAndServiceFee.toString, room.currency)
            )
            totalPriceInclusive = totalPriceInclusive + propertyFee + taxAndServiceFee
            println("totalsIfSurcharges"+totalPriceInclusive)
          }

        }
      }

      return Some(stayPrices, fees)
    }

    None
  }

  def _populateInclusivePriceWithCurrency(room: Room): EpsPriceWithCurrency = {
    val inclusiveRate: Double = Math.round(totalPriceInclusive * 100.0) / 100.0
   // println("totalPriceInclusive"+inclusiveRate)
    new EpsPriceWithCurrency(
      new EpsPrice(null,inclusiveRate.toString, room.currency),
      new EpsPrice(null,inclusiveRate.toString, room.currency)
    )
  }

  def _populateExclusivePriceWithCurrency(room: Room): EpsPriceWithCurrency = {
    val exclusiveRate: Double = Math.round(totalPriceExclusive * 100.0) / 100.0
    // println("totalPriceExclusive"+exclusiveRate)
    new EpsPriceWithCurrency(
      new EpsPrice(null,exclusiveRate.toString, room.currency),
      new EpsPrice(null,exclusiveRate.toString, room.currency)
    )
  }

  def _populateTotals(room: Room): EpsTotalPrice = {
    new EpsTotalPrice(_populateInclusivePriceWithCurrency(room),_populateExclusivePriceWithCurrency(room),null,null,null)
  }

  def populateRates(room: Room) = {
    var roomPrice: EpsRoomRateBuilder = new EpsRoomRateBuilder

    roomPrice.nightly= _populateNightlyPrice(room)


    _populateSurcharges(room).foreach(surcharge =>
    {
      roomPrice.stay = surcharge._1
      roomPrice.fees = surcharge._2
    })

    roomPrice.totals = _populateTotals(room)

    // return immutable Map.
    var occupancyMap: scala.collection.mutable.Map[String, EpsRoomRate] = scala.collection.mutable.Map[String, EpsRoomRate]()
    val occupancy = Array("2")
    roomPrice.build()
  }


}
