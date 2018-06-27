package org.goingok.httpServer

import akka.http.scaladsl.model.{DateTime, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.util.ByteString
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.RawHeader
import com.softwaremill.session.{SessionConfig, SessionManager}
import org.goingok._
import org.goingok.data.models.{GokId, ReflectionEntry, Research, ServerInfo}
import org.goingok.handlers.{AuthorisationHandler, ProfileHandler}
import org.goingok.message.{HandlerMessage, JsonMessage}
import org.json4s.JsonAST.JString

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */
trait GoingOkAPI extends GenericApi {

  import GoingOkContext._
  import com.softwaremill.session.SessionDirectives._
  import com.softwaremill.session.SessionOptions._

  val sessionConfig = SessionConfig.default("some_1very2_long3_secret4_and3_random5_string7_some9_very2_long4_secret6_and8_random10_string100")
  implicit val sessionManager = new SessionManager[String](sessionConfig)

  val profileRoutes = pathPrefix("profile") {
    options { complete("")} ~
    get {
      requiredSession(oneOff, usingHeaders) { session =>
        log.info("Successfully requested profile using session: "+ session)
        complete(ProfileHandler.process(HandlerMessage.Profile(session)))
      }
    } ~
    pathPrefix("reflection") {
      post {
        requiredSession(oneOff, usingHeaders) { session =>
          log.info("Successfully saved reflection using session: "+ session)
          //import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.stringUnmarshaller
          entity(as[Option[ReflectionEntry]]) { ref =>
            if (ref.isEmpty) complete("A ReflectionMsg  Entry is required")
            else {
              val entry = ref.get
              log.info(s"ReflectionMsg entry for $session: ${entry.toString}")
              complete(ProfileHandler.process(HandlerMessage.ReflectionMsg(session,entry)))
            }
          }
        }
      }
    }~
    pathPrefix("research") {
      post {
        requiredSession(oneOff, usingHeaders) { session =>
          log.info("Successfully saved research using session: "+ session)
          //import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.stringUnmarshaller
          entity(as[Option[Research]]) { res =>
            if (res.isEmpty) complete("A Research  Entry is required")
            else {
              val entry = res.get
              log.info(s"ResearchMsg entry for $session: ${entry.toString}")
              complete(ProfileHandler.process(HandlerMessage.ResearchMsg(session,entry)))
            }
          }
        }
      }
    }
  }

  val authRoutes = pathPrefix("auth") {
    post {
      import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.stringUnmarshaller
      entity(as[String]) { str =>
        if (str.isEmpty) complete("An  ID Token is required")
        else {
          val authMsg = HandlerMessage.Authorisation(str)
          onComplete(AuthorisationHandler.process(authMsg)) {gokId =>
            val id = gokId.get
            //System.out.println("FINAL ID: ",id)
            setSession(oneOff, usingHeaders, id) { ctx =>
              ctx.complete(GokId(id))
            }
          }
        }
      }
    }
//    extractUnmatchedPath { param =>
//      if (!param.isEmpty) {
//        post {
//          import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.byteStringUnmarshaller
//          entity(as[ByteString]) { str =>
//            if (str.isEmpty) complete("Text required for analysis")
//            else {
//              val analysisType = param.dropChars(1).head.toString
//              println("analysisType - {}",analysisType)
//              val analysisMsg = JsonMessage.ByteStringAnalysis(str,analysisType)
//              complete(TextAnalysisHandler.analyse(analysisMsg))
//            }
//          }
//        }
//      }
//      else nothingHere
//    }
  }



  override val customRoutes = pathPrefix("client") {

    profileRoutes ~ authRoutes

  }

  lazy val serverInfo = ServerInfo(BuildInfotest.name,BuildInfotest.version,BuildInfotest.builtAtString)

  override val adminRoutes = pathPrefix("admin") {
    path("version") {
        get {
            log.info("Version request")
            complete(serverInfo)
        }
    } ~
    get { nothingHere }
  }


}
