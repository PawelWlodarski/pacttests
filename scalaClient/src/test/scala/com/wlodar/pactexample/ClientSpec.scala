package com.wlodar.pactexample

import com.wlodar.jug.pacttest.client.ServiceClient
import org.scalatest.{FunSpec, Matchers}

import scalaj.http.HttpResponse

class ClientSpec extends FunSpec with Matchers {

  import com.itv.scalapact.ScalaPactForger._

  val provider = "calculation service"
  val client = "scala client"

  it("should create contract for simple get") {
    forgePact
      .between(client)
      .and(provider)
      .addInteraction(
        interaction
          .description("simple example")
          .uponReceiving("/simple")
          .willRespondWith(200, "simple result")
      ).runConsumerTest { config =>

      val result: HttpResponse[String] =ServiceClient.simpleCall(config.baseUrl)


      result.code shouldBe 200
      result.body shouldBe "simple result"
    }

  }

}
