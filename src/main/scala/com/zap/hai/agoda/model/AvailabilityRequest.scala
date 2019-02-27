package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, Node}


/*
@XmlAttribute(name = "siteid") val siteId: String = "1812488"
@XmlAttribute(name = "apikey") val apiKey: String = "6fae573e-b261-4c02-97b4-3dd20d1e74b2"
@XmlElement(name = "Type") var requestType: Int = _
@XmlElement(name = "Id") var propertyIds: String = _
@XmlElement(name = "CheckIn") var checkInDate: String = _
@XmlElement(name = "CheckOut") var checkOutDate: String = _
@XmlElement(name = "Rooms") var roomCount: Int = _
@XmlElement(name = "Adults") var adultsCount: Int = _
@XmlElement(name = "Children") var childrenCount: Int = _
@XmlElementWrapper(name = "ChildrenAges") @XmlElement(name = "Age")var childrenAges: Array[Int] = _
@XmlElement(name = "Language")var language: String = _
@XmlElement(name = "Currency")var currency: String = _
 */
case class AvailabilityRequest(siteId: String, apiKey: String, requestType: Int, id: String, propertyIds: String,
                               checkInDate: String, checkOutDate:String, roomCount : Int, adultCount:Int,
                               childrenAges:List[Int], language:String, currency:String)

object AvailabilityRequest extends XmlSupport[AvailabilityRequest] {

  override def fromXml(node: Node): AvailabilityRequest = {
    new AvailabilityRequestBuilder()
      .withSiteId(node \@  "siteid")
      .withApiKey(node \@ "apikey")
      .withRequestType((node \ "Type").text.toInt)
      .withId((node \ "Id").text)
      .withCheckInDate((node \ "CheckIn").text)
      .withCheckOutDate((node \ "CheckOut").text)
      .withRoomCount((node \ "Rooms").text.toInt)
      .withAdultCount((node \ "Adults").text.toInt)
      .build()

  }

  override def toXml(ar: AvailabilityRequest): Elem = {
    <AvailabilityRequestV2 siteid={ar.siteId} apikey={ar.apiKey} xmlns="http://xml.agoda.com" xmlns:tns="http://xml.agoda.com" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
      <Type>{ar.requestType}</Type>
      <Id>{ar.propertyIds}</Id>
      <CheckIn>{ar.checkInDate}</CheckIn>
      <CheckOut>{ar.checkOutDate}</CheckOut>
      <Rooms>{ar.roomCount}</Rooms>
      <Adults>{ar.adultCount}</Adults>
      {
        if(!ar.childrenAges.isEmpty){
          <Children>{ar.childrenAges.size}</Children>
          <ChildrenAges>
          {ar.childrenAges.map{age => <Age>{age}</Age>}}
          </ChildrenAges>
        } else {
          <Children>0</Children>
        }
      }
      <Language>{ar.language}</Language>
      <Currency>{ar.currency}</Currency>
    </AvailabilityRequestV2>
  }

}


class AvailabilityRequestBuilder{

  private var siteId: String = _
  private var apiKey: String = _
  private var requestType: Int = _
  private var id: String = _
  private var propertyIds: String = _
  private var checkInDate: String = _
  private var checkOutDate:String = _
  private var roomCount : Int = _
  private var adultCount:Int = _
  private var childrenAges:ArrayBuffer[Int] = ArrayBuffer[Int]()
  private var language:String = _
  private var currency:String = _
  private var occupancies : ArrayBuffer[String] = ArrayBuffer()

  def addOccupancy(occupancy : String)={
    occupancies += occupancy
    this
  }

  def withCurrency(currency:String)={
    this.currency = currency
    this
  }

  def withLanguage(language : String)={
    this.language = language
    this
  }

  def addChildAge(age:Int) = {
    childrenAges += age
    this
  }

  def withAdultCount(adultCount : Int)={
    this.adultCount = adultCount
    this
  }

  def withRoomCount(roomCount : Int) = {
    this.roomCount = roomCount
    this
  }

  def withCheckOutDate(checkOutDate : String)={
    this.checkOutDate = checkOutDate
    this
  }

  def withId(id:String)={
    this.id = id
    this
  }

  def withPropertyIds(propertyIds : String) = {
    this.propertyIds = propertyIds
    this
  }

  def withCheckInDate(checkInDate : String) = {
    this.checkInDate = checkInDate
    this
  }

  def withSiteId(siteId:String) = {
    this.siteId = siteId
    this
  }

  def withApiKey(apiKey:String) = {
    this.apiKey = apiKey
    this
  }
  def withRequestType(requestType : Int) = {
    this.requestType = requestType
    this
  }


  def build(): AvailabilityRequest = {
    AvailabilityRequest(siteId, apiKey, requestType, id, propertyIds, checkInDate, checkOutDate, roomCount, adultCount,
      childrenAges.toList, language, currency)
  }

}