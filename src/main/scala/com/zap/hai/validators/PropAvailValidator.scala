package com.zap.hai.validators

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{Duration, LocalDate}
import java.util.Date

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException
import com.zap.hai.constant.AvailabilityRequestParams
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{EPSErrorField, EPSErrorMessage, EPSErrorResponse, EPSErrorResponseBuilder}
import com.zap.hai.validators.constant.EPSResponseErrorType
import org.apache.commons.lang.StringUtils

import scala.collection.mutable.ArrayBuffer

trait PropAvailValidator extends RequestValidator {
  override def validate(request: ControllerRequest): EPSErrorResponse = {

    var errors = new ArrayBuffer[Option[EPSErrorMessage]]()
    //new CurrencyCodeValidator().validate(request.params).map{e=>errors += e}

    val mandatoryParams = List(AvailabilityRequestParams.PROPERTY_ID, AvailabilityRequestParams.CURRENCY_CODE_KEY,
      AvailabilityRequestParams.LANGUAGE_CODE_KEY, AvailabilityRequestParams.OCCUPANCY_KEY, AvailabilityRequestParams.SALES_CHANNEL,
      AvailabilityRequestParams.SALES_ENVIRONMENT, AvailabilityRequestParams.CHECKIN_PARAM_KEY, AvailabilityRequestParams.CHECKOUT_PARAM_KEY,
      AvailabilityRequestParams.SORT_TYPE, AvailabilityRequestParams.COUNTRY_CODE_KEY)

    for (param <- mandatoryParams) {
      val errorMessage: Option[EPSErrorMessage] = _validateMissingOrBlank(request, param.toString)
      errors += errorMessage
    }

    var errorMessage: Option[EPSErrorMessage] = _validatePropertyID(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }

    errorMessage = _validateStayInfo(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }

    errorMessage = _validateOccupancy(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }


    errorMessage = CurrencyCodeValidator.validate(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }

    errorMessage = LanguageCodeValidator.validate(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }
    errorMessage = CountryCodeValidator.validate(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }
    errorMessage = SalesChannelValidator.validate(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }
    errorMessage = SalesEnvironmentValidator.validate(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }
    errorMessage = FilterValidator.validate(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }
    errorMessage = RateOptionValidator.validate(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }
    errorMessage = SortTypeValidator.validate(request)
    if (errorMessage.isDefined) {
      Some(errorMessage.get)
      errors += errorMessage
    }

    new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT.errorType,EPSResponseErrorType.INVALID_INPUT.errorMessage, errors.toList)
  }

  def _validateMissingOrBlank(request: ControllerRequest, header: String): Option[EPSErrorMessage] = {

    val headerValues: List[String] = request.params(header.toString)
    println("Headers" + headerValues)
    if (headerValues != null && headerValues.isEmpty) {
      Some(EPSErrorResponseBuilder.createForMissingInput(header.toString).get)

    }

    val countNonBlankValues: Int = headerValues.count(StringUtils.isNotBlank)
    if (countNonBlankValues == 0) {
      Some(EPSErrorResponseBuilder.createForMissingInput(header.toString).get)
    }

    None
  }

  def _validatePropertyID(request: ControllerRequest): Option[EPSErrorMessage] = {
    if (request.params(AvailabilityRequestParams.PROPERTY_ID.toString).size > 250) {
      val errorFields = List(new EPSErrorField("property_id", request.params("property_id").size.toString, null))
      val responseError: EPSErrorMessage = new EPSErrorMessage(
        "property_id.above_maximum",
        "The number of property_id's passed in must not be greater than 250.",
        errorFields)

      responseError
    }

    None
  }

  def _validateOccupancy(request: ControllerRequest): Option[EPSErrorMessage] = {

    val countNonBlankOccupancy: Int = request.params(AvailabilityRequestParams.OCCUPANCY_KEY.toString).count(StringUtils.isNotBlank)
    if (countNonBlankOccupancy == 0) {
      Some(EPSErrorResponseBuilder.createForMissingInput(AvailabilityRequestParams.OCCUPANCY_KEY.toString).get)
    }

    for (occupancy: String <- request.params(AvailabilityRequestParams.OCCUPANCY_KEY.toString)) {
      if (StringUtils.isNotBlank(occupancy)) {
        val split = occupancy.split("-")
        val adultsCount = split(0).toInt

        if (adultsCount > 8) {
          // TODO: Add value to error response
          val errorFields = List(new EPSErrorField("occupancy", "querystring", adultsCount.toString))
          val responseError: EPSErrorMessage = new EPSErrorMessage(
            "number_of_adults.invalid_above_maximum",
            "Number of occupancies must be less than 9.",
            errorFields
          )

          responseError
        }

        if (adultsCount == 0) {
          val errorField = List(new EPSErrorField("occupancy", "querystring", adultsCount.toString))
          val responseError: EPSErrorMessage = new EPSErrorMessage(
            "number_of_adults.invalid_below_minimum",
            "Number of adults must be greater than 0.",
            errorField)

          responseError
        }

        if (split.length == 2) {
          val invalidChildrenAge: Option[Int] = split(1).split(",").map(_.toInt).find(_ >= 18)
          if (invalidChildrenAge.isDefined) {
            val responseError: EPSErrorMessage = new EPSErrorMessage(
              "child_age.invalid_outside_accepted_range",
              "Child age must be between 0 and 17.", null
            )
            responseError
          }
        }
      }
    }

    None
  }

  def _validateDateFormat(value: String): Boolean = {
    var date: Date = null
    var sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      date = sdf.parse(value)

      if (!value.equals(sdf.format(date))) {
        date = null
      }
    } catch {
      case e: ParseException => e.printStackTrace()
    }
    if (date == null) false else true
  }

  def _validateStayInfo(request: ControllerRequest): Option[EPSErrorMessage] = {
    val checkinParam: String = request.params(AvailabilityRequestParams.CHECKIN_PARAM_KEY.toString).head
    val checkoutParam: String = request.params(AvailabilityRequestParams.CHECKOUT_PARAM_KEY.toString).head

    var isCheckinValid: Boolean = _validateDateFormat(checkinParam)
    var isCheckoutValid: Boolean = _validateDateFormat(checkoutParam)

    if (!isCheckinValid) {
      println("Valid: " + isCheckinValid)
      val errorFields = List(new EPSErrorField("checkin", checkinParam, null))
      val responseError: EPSErrorMessage = new EPSErrorMessage(
        "checkin.invalid_date_format",
        "Invalid checkin format. " +
          "It must be formatted in ISO 8601 (YYYY-mm-dd) http://www.iso.org/iso/catalogue_detail?csnumber=40874.", errorFields
      )

      responseError
    }
    if (!isCheckoutValid) {
      val errorFields = List(new EPSErrorField("checkout", checkinParam, null))
      val responseError: EPSErrorMessage = new EPSErrorMessage(
        "checkout.invalid_date_format",
        "Invalid checkout format. " +
          "It must be formatted in ISO 8601 (YYYY-mm-dd) http://www.iso.org/iso/catalogue_detail?csnumber=40874.", errorFields
      )

      responseError
    }

    val differenceInDays: Long = Duration.between(
      LocalDate.now().atStartOfDay(),
      LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkinParam)).atStartOfDay()
    ).toDays


    if (differenceInDays < 0) {
      val errorFields = List(new EPSErrorField("checkin", checkinParam, null))
      val responseError: EPSErrorMessage = new EPSErrorMessage(
        "checkin.invalid_date_in_the_past",
        "Checkin cannot be in the past.",
        errorFields)

      responseError
    }

    if (differenceInDays > 500) {
      val errorFields = List(new EPSErrorField("checkin", checkinParam, null))
      val responseError: EPSErrorMessage = new EPSErrorMessage(
        "checkin.invalid_date_too_far_out",
        "Checkin too far in the future.",
        errorFields)

      responseError
    }

    val differenceBetweenStayDates: Long = Duration.between(
      LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkinParam)).atStartOfDay(),
      LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkoutParam)).atStartOfDay()
    ).toDays

    if (differenceBetweenStayDates < 0) {
      val errorFields = List(new EPSErrorField("checkin", "querystring", null), new EPSErrorField("checkout", "querystring", null))
      val responseError: EPSErrorMessage = new EPSErrorMessage("checkout.invalid_checkout_before_checkin",
        "Checkout must be after checkin.", errorFields)

      responseError
    }

    None
  }
}

