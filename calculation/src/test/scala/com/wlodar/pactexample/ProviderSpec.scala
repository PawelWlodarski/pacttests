package com.wlodar.pactexample

import org.scalatest.{BeforeAndAfterAll, FunSpec, MustMatchers}

class ProviderSpec extends FunSpec with MustMatchers with BeforeAndAfterAll {


  import com.itv.scalapact.ScalaPactVerify._

  it("should run provider tests") {
    verifyPact
      .withPactSource(loadFromLocal("pacts"))
      .noSetupRequired
        .runVerificationAgainst("localhost",11200)

  }

}
