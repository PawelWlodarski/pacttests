package com.wlodar.jug.pacttest.client

import scalaj.http.{Http, HttpResponse}

/**
  * Created by pawel on 25.12.16.
  */
object ServiceClient {



  def simpleCall(baseUrl:String):HttpResponse[String] = Http(s"$baseUrl/simple").asString

}
