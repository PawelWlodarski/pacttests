package com.wlodar.pactexample.first

import com.wlodar.jug.pacttest.client.ServiceClient
import org.scalatest.{FunSpec, Matchers}

import scalaj.http.HttpResponse

class FirstPactExample extends FunSpec with Matchers {

  import com.itv.scalapact.ScalaPactForger._

  describe("Pierwszy przykład na demo") {
    forgePact
      .between("tenTutajKonsument")
      .and("uslugaZewnetrzna")
      .addInteraction(
        interaction
          .description("Prosty get bez parametrów")
          .uponReceiving("/apiUslugi")
          .willRespondWith(200, "Pact działa!!!")
      ).runConsumerTest{config=>

      //pokazać kod
      val result: HttpResponse[String] =ServiceClient.call(config.baseUrl,"apiUslugi")

      result.code shouldBe 200
      result.body shouldBe "Pact działa!!!"
//      result.body shouldBe "Błąd"

    }
  }

}
