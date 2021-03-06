package com.wlodar.pactexample.first

import com.wlodar.jug.pacttest.client.ServiceClient
import org.scalatest.{FunSpec, FunSuite, Matchers}

import scalaj.http.HttpResponse

class FirstPactExample extends FunSuite with Matchers {

  import com.itv.scalapact.ScalaPactForger._
  import com.itv.scalapact.circe09._
  import com.itv.scalapact.http4s18._

  test("Pierwszy przykład na demo") {
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
      println(result)
      result.code shouldBe 200
      result.body shouldBe "Pact działa!!!"
//      result.body shouldBe "Błąd"

    }
  }

}
