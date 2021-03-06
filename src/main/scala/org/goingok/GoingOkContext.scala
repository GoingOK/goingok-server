package org.goingok

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */

object GoingOkContext {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  val log = Logging(system.eventStream,"~")
  def shorten(text:String,num:Int=30) = text.replace("\n"," ").take(num).concat("\u2026")
}
