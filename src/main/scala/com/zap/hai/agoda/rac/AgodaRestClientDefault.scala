package com.zap.hai.agoda.rac

import com.zap.hai.agoda.model._
import zap.framework.httpclient._
import zap.framework.props._
import zap.framework.xml._
import zap.framework.json._


trait AgodaRestClientDefault extends AgodaRestClient  {

  val httpClient : ZapHttpClient

  override def affliateLongSearch(ar: AvailabilityRequest): Either[ZapHttpResponse,AvailabilityResponse] = {

    val baseurl = zaperties.req[String]("agoda.url")
    val request = ZapHttpRequest(Post,ZapUrl(s"${baseurl}/xmlpartner/xmlservice/search_lrv3"),
      Some(ZapHttpStringEntity(ar.toXml.plain)),("Authorization",zaperties.req[String]("agoda.authorization")),("Content-Type","application/xml"))
    println(s"Request = ${request.toJsonPretty}")
    val response = httpClient.execute(request)
    println(s"Response = ${response.body.get.asInstanceOf[ZapHttpStringEntity].content}")
    if(response.statusCode == 200){
      Right(response.body.get.asString.toAvailabilityResponse)
    } else {
      Left(response)
    }
  }

}
