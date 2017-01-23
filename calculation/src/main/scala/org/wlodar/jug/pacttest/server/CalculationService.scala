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

case class APIDoc(path: String, params: Seq[String])
case class Add(a:Int,b:Int)

trait Services extends Directives {

  import akka.http.scaladsl.model.StatusCodes._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import spray.json.DefaultJsonProtocol._

  import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._

  implicit val APIDocFormat=jsonFormat2(APIDoc)
  implicit val AddFormat=jsonFormat2(Add)

  val route: Route =
    path("apiUslugi"){
      get{
        complete("Pact działa!!!")  //fix
      }
    }
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
      } ~
      post{
        entity(as[Add]){add=>
          complete(<sum>{add.a + add.b}</sum>)
        }
      }
    } ~
    path("doc") {
      get{
        complete(APIDoc("sum", Seq("a", "b")))
      }
    }
}
