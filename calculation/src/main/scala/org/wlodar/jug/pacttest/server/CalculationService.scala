package org.wlodar.jug.pacttest.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer

/**
  * Created by pawel on 24.12.16.
  */
object CalculationService extends App with Services{
  implicit val system=ActorSystem()
  implicit val materializer=ActorMaterializer()


  Http().bindAndHandle(route,"localhost", 11200)

}

trait Services extends Directives{
  val route=path("simple"){
    get{
      complete("simple result")
    }
  }
}
