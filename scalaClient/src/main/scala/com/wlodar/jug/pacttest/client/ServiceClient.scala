package com.wlodar.jug.pacttest.client

import scalaj.http.{Http, HttpResponse}

/**
  * Created by pawel on 25.12.16.
  */
object ServiceClient {

  def simpleCall(baseUrl: String): HttpResponse[String] = Http(s"$baseUrl/simple").asString


  def add(baseUrl: String)(a: Int, b: Int): HttpResponse[String] =
    Http(s"$baseUrl/sum")
      .param("a", a.toString)
      .param("b", b.toString)
      .header("action", "computation")
      .asString


  def doc(baseUrl:String): HttpResponse[String] = Http(s"$baseUrl/doc").asString


}
