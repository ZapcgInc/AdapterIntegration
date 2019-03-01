package com.zap.hai.eps

case class EpsAvailabilityRequest(checkinDate:String,
                                  checkoutDate:String,
                                  currency:String,
                                  language: String,
                                  countryCode:String,
                                  occupancy:String,
                                  propertyId:String,
                                  salesChannel:String,
                                  salesEnvironment:String,
                                  sortType:String,
                                  filter:String,
                                  include:String,
                                  rateOption:String,
                                  billingTerms:String,
                                  paymentTerms:String,
                                  partnerPos:String)


class EpsAvailabilityRequestBuilder{
  private var checkinDate:String = _
  private var checkoutDate:String = _
  private var currency:String = _
  private var language: String = _
  private var countryCode:String = _
  private var occupancy:String = _
  private var propertyId:String = _
  private var salesChannel:String = _
  private var salesEnvironment:String = _
  private var sortType:String = _
  private var filter:String = _
  private var include:String = _
  private var rateOption:String = _
  private var billingTerms:String = _
  private var paymentTerms:String = _
  private var partnerPos:String = _

  def withPartnerPos(partnerPos:String)={
    this.partnerPos = partnerPos
    this
  }

  def withPaymentTerms(paymentTerms:String)={
    this.paymentTerms = paymentTerms
    this
  }


  def withBillingTerms(billingTerms:String)={
    this.billingTerms = billingTerms
    this
  }

  def withRateOption(rateOption:String)={
    this.rateOption = rateOption
    this
  }

  def withInclude(include:String)={
    this.include = include
    this
  }

  def withFilter(filter:String)={
    this.filter = filter
    this
  }

  def withSortType(sortType:String)={
    this.sortType = sortType
    this
  }

  def withSalesEnvironment(salesEnvironment:String)={
    this.salesEnvironment = salesEnvironment
    this
  }

  def withSalesChannel(salesChannel:String)={
    this.salesChannel = salesChannel
    this
  }


  def withPropertyId(propertyId:String)={
    this.propertyId = propertyId
    this
  }

  def withOccupancy(occupancy:String)={
    this.occupancy = occupancy
    this
  }

  def withCountryCode(countryCode:String)={
    this.countryCode = countryCode
    this
  }

  def withLanguage(language:String)={
    this.language = language
    this
  }

  def withCurrency(currency:String)={
    this.currency = currency
    this
  }

  def withCheckoutDate(checkoutDate:String)={
    this.checkoutDate = checkoutDate
    this
  }

  def withCheckinDate(checkinDate:String)={
    this.checkinDate = checkinDate
    this
  }

  def build()={
    EpsAvailabilityRequest(checkinDate:String,
      checkoutDate:String,
      currency:String,
      language: String,
      countryCode:String,
      occupancy:String,
      propertyId,
      salesChannel,
      salesEnvironment,
      sortType,
      filter,
      include,
      rateOption,
      billingTerms,
      paymentTerms,
      partnerPos)
  }


}