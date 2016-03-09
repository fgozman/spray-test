package spraytest

import spray.routing.HttpService
import spray.http.MediaType
import spray.http.MediaTypes
import akka.actor.Actor
import org.slf4j.LoggerFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

trait MyService extends HttpService {
  def logger = LoggerFactory.getLogger(this.getClass)
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  val myRoute =
    pathSingleSlash {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete {
          //  logger.info("GET ")
            mapper.writeValueAsBytes(new Customer("Nume1", 1234,null))
          }
        }

      } ~
      post {
          // decompresses the request with Gzip or Deflate when required

          // unmarshal with in-scope unmarshaller
          entity(as[Array[Byte]]) { json =>
            // transfer to newly spawned actor
           // detach() {
              respondWithMediaType(MediaTypes.`application/json`) {
                complete {
 //                 logger.info("POST ")
                  val c = mapper.readValue(json, classOf[Customer])
                //  logger.info(c.name)
                  // ... write order to DB
                  mapper.writeValueAsBytes(new Customer(c.name+" 2",c.age+1,null))
                }
              }
            //}
          }
        }
    }
}