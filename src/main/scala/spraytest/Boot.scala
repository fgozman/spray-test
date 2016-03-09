package spraytest

import akka.actor.ActorSystem
import spray.servlet.WebBoot
import akka.actor.Props
import org.slf4j.LoggerFactory

// this class is instantiated by the servlet initializer
// it needs to have a default constructor and implement
// the spray.servlet.WebBoot trait
class Boot extends WebBoot{
  def logger = LoggerFactory.getLogger(this.getClass)
  logger.info("Init Boot")
   // we need an ActorSystem to host our application in
  val system  = ActorSystem("example")
  
  val serviceActor = system.actorOf(Props[MyServiceActor])
}