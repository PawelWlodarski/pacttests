package org.wlodar.jug.pacttest.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import org.slf4j.LoggerFactory

/**
  * Created by pawel on 24.12.16.
  */
object CalculationService extends App with Services {
  val log = LoggerFactory.getLogger(getClass)

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val host = "localhost"
  val port = 11200

  Http().bindAndHandle(route, host, port)
  log.info(s"initiated calculation service on http://$host/$port")
}

trait Services extends Directives {

  import akka.http.scaladsl.model.StatusCodes._

  val route: Route =
    path("simple") {
      get {
        complete("simple result")
      }
    } ~
    path("sum") {
      get {
        parameters('a.as[Int], 'b.as[Int]) { (a, b) =>
          headerValueByName("action"){header=>
            if(header=="computation")
              complete(s"sum=${a + b}")
            else
              complete((BadRequest,s"Wrong 'action' header value :  $header"))
          }
        }
      }
    }
}
