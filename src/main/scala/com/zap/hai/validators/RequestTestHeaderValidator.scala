package com.zap.hai.validators

import com.sun.deploy.nativesandbox.comm.Request
import com.sun.xml.internal.ws.util.StringUtils
import com.zap.hai.eps.ErrorResponse

/**
  * Util class to handle Test Suite Requests.
  * Request of EPS format can send a Header Parameters with Key "Test" that contains the desired Response "Status"
  * The API shall return the status specified in the header value.
  */
object RequestTestHeaderValidator extends RequestValidator
{
    private val INVALID_CONTENT_TYPE_RESPONSE_ERROR_TYPE: String = "test.content_invalid"
    private val INVALID_CONTENT_TYPE_RESPONSE_ERROR_MSG: String = "Content of the test header is invalid. Please use one of the following valid values: forbidden, no_availability, service_unavailable, standard, unknown_internal_error"

    private val INVALID_CONTENT_TYPE_RESPONSE_ERROR: ErrorResponse = new ErrorResponse(INVALID_CONTENT_TYPE_RESPONSE_ERROR_TYPE, INVALID_CONTENT_TYPE_RESPONSE_ERROR_MSG,Nil)

    override def validate(request: Request): Option[(HttpResponseStatus, EPSErrorResponse)] =
    {
        val header: String = request("Test")

        if (header==null || header.isEmpty)
        {
            None
        }

        ValidTestResponse.withNameOpt(header) match
        {
            case Some(ValidTestResponse.NO_AVAILABILITY) => Some(Status.NotFound, new EPSErrorResponse(EPSResponseErrorType.NO_AVAILABILITY))
            case Some(ValidTestResponse.UNKNOWN_INTERNAL_ERROR) => Some(Status.InternalServerError, new EPSErrorResponse(EPSResponseErrorType.UNKNOWN_INTERNAL_ERROR))
            case Some(ValidTestResponse.SERVICE_UNAVAILABLE) => Some(Status.ServiceUnavailable, new EPSErrorResponse(EPSResponseErrorType.SERVICE_UNAVAILABLE))
            case Some(ValidTestResponse.REQUEST_FORBIDDEN) => Some(Status.Forbidden, new EPSErrorResponse(EPSResponseErrorType.REQUEST_FORBIDDEN))
            case Some(ValidTestResponse.INVALID) =>  Some(Status.BadRequest, new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT, INVALID_CONTENT_TYPE_RESPONSE_ERROR))
            case _ => None
        }

    }

    private object ValidTestResponse extends Enumeration
    {
        val NO_AVAILABILITY = Value("no_availability")
        val UNKNOWN_INTERNAL_ERROR = Value("unknown_internal_error")
        val SERVICE_UNAVAILABLE = Value("service_unavailable")
        val REQUEST_FORBIDDEN = Value("forbidden")
        val INVALID = Value("INVALID")

        def withNameOpt(s: String): Option[Value] = values.find(_.toString == s)

        def getValueSetAsString(): String = values.mkString(",")
    }

}
