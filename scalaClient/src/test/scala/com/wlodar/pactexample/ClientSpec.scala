package com.wlodar.pactexample

import com.wlodar.jug.pacttest.client.ServiceClient
import org.scalatest.{FunSpec, Matchers}

import scalaj.http.HttpResponse

class ClientSpec extends FunSpec with Matchers {

  import com.itv.scalapact.ScalaPactForger._

  val provider = "calculation service"
  val client = "scala client"

  //it
  ignore("should create contract for simple get without parameters") {
    forgePact
      .between(client)
      .and(provider)
      .addInteraction(
        interaction
          .description("simple example")
          .uponReceiving("/simple")
          .willRespondWith(200, "simple result")
      ).runConsumerTest { config =>

      val result: HttpResponse[String] = ServiceClient.simpleCall(config.baseUrl)


      result.code shouldBe 200
      result.body shouldBe "simple result"
    }

  }

  ignore("shows an example of a simple get with paremeters and headers") {
    CustomForger.forge(
      interaction
        .description("example with parameters and headers")
        .uponReceiving(
          method = GET,
          path = "/sum",
          query = "a=2&b=3",
          headers = Map("action" -> "computation"),
          body = None,
          matchingRules = None)
        .willRespondWith(200, "sum=5")
    ).runConsumerTest { config =>
      val result = ServiceClient.add(config.baseUrl)(2, 3)

      result.code shouldBe 200
      result.body shouldBe "sum=5"
    }

  }


  val doc = APIDoc("sum", Seq("a", "b"))

  import org.json4s.native.Serialization._
  import org.json4s.native.JsonParser
  import org.json4s.DefaultFormats

  private implicit val formats = DefaultFormats

  ignore("show an example of json result") {
    CustomForger.forge(
      interaction.description("example with json result")
        .uponReceiving("/doc")
        .willRespondWith(200, Map("Content-Type" -> "application/json"), write(doc), None)
    ).runConsumerTest{config =>
        val result=ServiceClient.doc(config.baseUrl)

      result.code shouldBe 200
      JsonParser.parse(result.body).extract[APIDoc] shouldBe doc
    }
  }


}

case class APIDoc(path: String, params: Seq[String])

object CustomForger {

  import com.itv.scalapact.ScalaPactForger._
  import com.itv.scalapact.ScalaPactForger.forgePact.ScalaPactDescription

  def forge(interaction: ScalaPactInteraction): ScalaPactDescription =
    forgePact
      .between("scala client").and("calculation service")
      .addInteraction(interaction)

}
