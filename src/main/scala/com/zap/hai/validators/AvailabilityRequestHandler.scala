package com.zap.hai.validators

class AvailabilityRequestHandler extends Service[Request, Response]
{
    private val RESPONSE_JSON_MAPPER = (new ObjectMapper).registerModule(DefaultScalaModule)
    private val END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_lrv3"

    override def apply(request: Request): Future[Response] =
    {

        var validationResponse: Option[(HttpResponseStatus, EPSErrorResponse)] = RequestTestHeaderValidator.validate(request)
        if (validationResponse.isDefined)
        {
            return Future.value(_convertToEPSResponse(validationResponse.get._1, validationResponse.get._2))
        }

        validationResponse = AvailabilityRequestDataValidator.validate(request)
        if (validationResponse.isDefined)
        {
            return Future.value(_convertToEPSResponse(validationResponse.get._1, validationResponse.get._2))
        }

        // Create Agoda Request from EAN HTTP Request.
        val agodaAvailabilityRequest: AvailabilityRequestV2 = new AvailabilityRequestV2(request)

        // Convert agoda POJO to XML.
        val agodaRequestXML: String = agodaAvailabilityRequest.convertToXML()

        // Post to Agoda API and get response
        val agodaResponseAsString: String = AgodaPOSTRequestUtil.postXMLRequestAndGetResponse(agodaRequestXML, END_POINT)

        println(agodaResponseAsString)

        val agodaResponse: AvailabilityLongResponseV2 = AvailabilityLongResponseV2.unmarshall(agodaResponseAsString)

        // Convert to EPS Response
        val epsResponse: EPSShoppingResponse = new AvailabilityResponseConverter(agodaAvailabilityRequest, agodaResponse).convertToEPSResponse()

        Future.value(_convertToEPSResponse(HttpResponseStatus.OK, epsResponse.properties))
    }

    def _convertToEPSResponse(httpResponseStatus: HttpResponseStatus, agodaResponse: AnyRef): Response =
    {
        // Convert EPS Response to JSON
        val jsonResponse = RESPONSE_JSON_MAPPER.writeValueAsString(agodaResponse)

        val response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, httpResponseStatus))
        response.setContentTypeJson()
        response.setContentString(jsonResponse)

        response
    }
}