package com.zap.hai.validators

import com.zap.hai.constant.AvailabilityRequestParams
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{EPSErrorMessage, EPSErrorResponseBuilder}

object CurrencyCodeValidator
{
    private val VALID_CURRENCY_CODES: List[String] = List("AED", "ARS", "AUD", "BRL", "CAD", "CHF", "CNY", "DKK", "EGP", "EUR", "GBP",
        "HKD", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RUB", "SAR", "SEK", "SGD", "THB",
        "TRY", "TWD", "USD", "VND", "ZAR")

    def validate(request: ControllerRequest): Option[EPSErrorMessage] =
    {
        val currencyCode:String = request.params("AvailabilityRequestParams.CURRENCY_CODE_KEY.toString").head
        println(currencyCode)
        if (!VALID_CURRENCY_CODES.contains(currencyCode))
        {
            Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.CURRENCY_CODE_KEY.toString).get)
        }

        None
    }
}
