package com.zap.hai.validators

import com.zap.hai.constant.AvailabilityRequestParams
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{EPSErrorMessage, EPSErrorResponseBuilder}

object LanguageCodeValidator
{
    private val VALID_LANGUAGE_CODES: List[String] = List("ar-SA", "cs-CZ", "da-DK", "de-DE", "el-GR", "en-US", "es-ES",
        "es-MX", "fi-FI", "fr-CA", "fr-FR", "hr-HR", "hu-HU", "id-ID", "is-IS", "it-IT", "ja-JP", "ko-KR",
        "lt-LT", "ms-MY", "nb-NO", "nl-NL", "pl-PL", "pt-BR", "pt-PT", "ru-RU", "sk-SK", "sv-SE", "th-TH",
        "tr-TR", "uk-UA", "vi-VN", "zh-CN", "zh-TW")

    def validate(request: ControllerRequest): Option[EPSErrorMessage] =
    {
        val languageCode: String = request.params("AvailabilityRequestParams.LANGUAGE_CODE_KEY.toString").head

        if (!VALID_LANGUAGE_CODES.contains(languageCode))
        {
            Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.LANGUAGE_CODE_KEY.toString).get)
        }

        None
    }
}
