package spraytest

import org.slf4j.LoggerFactory

import akka.actor.ActorRef
import akka.actor.ActorRefFactory
import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import akka.util.Timeout
import spray.can.Http

object Main {
 def logger = LoggerFactory.getLogger(this.getClass)
  // https://github.com/spray/spray/tree/release/1.1/examples
  def main(args: Array[String]): Unit = {
      println("1234566")
      
      implicit val system = ActorSystem()
      
      logger.info("Info1")
      val myListener: ActorRef = system.actorOf(Props[MyServiceActor], name = "handler")

      logger.info("Info2")
      IO(Http) ! Http.Bind(myListener, 
          interface = "0.0.0.0", 
          port = if(args.length > 0)  args(0).toInt else 8080)
      
  }
  
}

