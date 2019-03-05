package com.zap.hai.eps

import com.zap.hai.constant.AvailabilityRequestParams

case class EPSErrorField(name:String, `type`:String, value:String)
case class EPSErrorMessage(`type`:String, message:String, fields: List[EPSErrorField])
case class EPSErrorResponse(`type`:String,message:String,errors: List[Option[EPSErrorMessage]] )


object EPSErrorResponseBuilder {
  def createForMissingInput(fieldName: String): Option[EPSErrorMessage] = {
    val responseErrorType: String = s"$fieldName.required"
    val responseErrorMessage: String = _createErrorMessageForMissingInput(fieldName)
    val errorFields = List(new EPSErrorField(fieldName,"querystring",null))
    Some(
      new EPSErrorMessage(responseErrorType, responseErrorMessage, errorFields)
    )
  }

  def _createErrorTypeForUnsupportedInput(fieldName: String): String = {
    AvailabilityRequestParams.withNameOpt(fieldName) match {
      case Some(AvailabilityRequestParams.CURRENCY_CODE_KEY) => {
        "currency.not_supported"
      }
      case Some(AvailabilityRequestParams.LANGUAGE_CODE_KEY) => {
        "language.not_supported"
      }
      case Some(AvailabilityRequestParams.SALES_CHANNEL) => {
        "sales_channel.invalid"
      }
      case Some(AvailabilityRequestParams.SALES_ENVIRONMENT) => {
        "sales_environment.invalid"
      }
      case Some(AvailabilityRequestParams.COUNTRY_CODE_KEY) => {
        "country_code.invalid"
      }
      case Some(AvailabilityRequestParams.RATE_OPTION) => {
        "rate_option.invalid"
      }
      case Some(AvailabilityRequestParams.SORT_TYPE) => {
        "sort_type.invalid"
      }
      case Some(AvailabilityRequestParams.FILTER) => {
        "filter.invalid"
      }
      case _ => {
        throw new RuntimeException("No message configured for field" + fieldName)
      }
    }
  }

  def createForUnsupportedInput(fieldName: String): Option[EPSErrorMessage] = {
    val responseErrorType: String = _createErrorTypeForUnsupportedInput(fieldName)
    val responseErrorMessage: String = _createErrorMessageForUnsupportedInput(fieldName)
    val errorFields = List(new EPSErrorField(fieldName,"querystring",null))
    Some(
      new EPSErrorMessage(responseErrorType, responseErrorMessage, errorFields)
    )
  }

  def _createErrorMessageForMissingInput(fieldName: String): String = {
    AvailabilityRequestParams.withNameOpt(fieldName) match {
      case Some(AvailabilityRequestParams.SALES_CHANNEL) => {
        "Sales Channel is required.  Accepted sales_channel values are: [website, agent_tool, mobile_app, mobile_web, cache, meta]."
      }
      case Some(AvailabilityRequestParams.LANGUAGE_CODE_KEY) => {
        "Language code is required."
      }
      case Some(AvailabilityRequestParams.COUNTRY_CODE_KEY) => {
        "Country code is required."
      }
      case Some(AvailabilityRequestParams.OCCUPANCY_KEY) => {
        "Occupancy is required."
      }
      case Some(AvailabilityRequestParams.SALES_ENVIRONMENT) => {
        "Sales Environment is required.  Accepted sales_environment values are: [hotel_only, hotel_package, loyalty]."
      }
      case Some(AvailabilityRequestParams.SORT_TYPE) => {
        "Sort Type is required.  Accepted sort_type values are: [preferred]."
      }

      case _ => {
        s"$fieldName is required."
      }
    }
  }

  def _createErrorMessageForUnsupportedInput(fieldName: String): String = {
    AvailabilityRequestParams.withNameOpt(fieldName) match {
      case Some(AvailabilityRequestParams.CURRENCY_CODE_KEY) => {
        "Currency is not supported. Supported currencies are: [AED, ARS, AUD, BRL, CAD, CHF, CNY, DKK, EGP, EUR, GBP, HKD, IDR, ILS, INR, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PLN, RUB, SAR, SEK, SGD, THB, TRY, TWD, USD, VND, ZAR]"
      }
      case Some(AvailabilityRequestParams.LANGUAGE_CODE_KEY) => {
        "Language is not supported. Supported languages are: [ar-SA, cs-CZ, da-DK, de-DE, el-GR, en-US, es-ES, es-MX, fi-FI, fr-CA, fr-FR, hr-HR, hu-HU, id-ID, is-IS, it-IT, ja-JP, ko-KR, lt-LT, ms-MY, nb-NO, nl-NL, pl-PL, pt-BR, pt-PT, ru-RU, sk-SK, sv-SE, th-TH, tr-TR, uk-UA, vi-VN, zh-CN, zh-TW]"
      }
      case Some(AvailabilityRequestParams.SALES_CHANNEL) => {
        "Sales Channel is invalid.  Accepted sales_channel values are: [website, agent_tool, mobile_app, mobile_web, cache, meta]."
      }
      case Some(AvailabilityRequestParams.SALES_ENVIRONMENT) => {
        "Sales Environment is invalid.  Accepted sales_environment values are: [hotel_only, hotel_package, loyalty]."
      }
      case Some(AvailabilityRequestParams.COUNTRY_CODE_KEY) => {
        "Country code is invalid."
      }
      case Some(AvailabilityRequestParams.RATE_OPTION) => {
        "Rate Option is invalid.  Accepted rate_option values are: [net_rates, closed_user_group]."
      }
      case Some(AvailabilityRequestParams.FILTER) => {
        "Filter is invalid. Accepted filter values are: [refundable, expedia_collect, property_collect]."
      }
      case Some(AvailabilityRequestParams.SORT_TYPE) => {
        "Sort Type is invalid.  Accepted sort_type values are: [preferred]."
      }

      case _ => {
        throw new RuntimeException("No message configured for field" + fieldName)
      }
    }
  }


}