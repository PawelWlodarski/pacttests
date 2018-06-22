package com.wlodar.pactexample

import org.scalatest.{BeforeAndAfterAll, FunSpec, MustMatchers}

class ProviderSpec extends FunSpec with MustMatchers with BeforeAndAfterAll {


  import com.itv.scalapact.ScalaPactVerify._
  import com.itv.scalapact.circe09._
  import com.itv.scalapact.http4s18._


  ignore("should run provider tests") {
    verifyPact
      .withPactSource(loadFromLocal("pacts"))
      .noSetupRequired
        .runVerificationAgainst("localhost",11200)

  }

}
