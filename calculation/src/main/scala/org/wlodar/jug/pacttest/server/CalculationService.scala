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
case class JSonWithArray(text:String,integerType:Int, collection: List[String])
trait Services extends Directives {

  import akka.http.scaladsl.model.StatusCodes._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import spray.json.DefaultJsonProtocol._

  import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._

  implicit val APIDocFormat=jsonFormat2(APIDoc)
  implicit val AddFormat=jsonFormat2(Add)
  implicit val MatchingBodyFormat=jsonFormat3(JSonWithArray)


  val route: Route =
    path("apiUslugi"){
      get{
        complete("dupa")  //fix
      }
    } ~
    path("simple") {
      get {
        complete("simple result")
      }
    } ~
    path("sum") {
      //http://localhost:11200/sum?a=5&b=7  -> missing header
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
    } ~
    path("bodyMatchers") {
      get {
        complete(JSonWithArray("somethingDifferent",69,List("four")))
      }
    } ~
    path("providerState") {
      get{
        complete("ok")
      }
    }

}
