import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._
import org.goingok.httpServer.{ResponseMessage, GenericApi, GoingOkAPI}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s._

class TapStreamRouteSpec extends WordSpec with Matchers with ScalatestRouteTest with GoingOkAPI with Json4sSupport {


  "The service" should {

    "return a greeting for GET requests to the root path" in {
      // tests:
      Get() ~> routes ~> check {
        responseAs[ResponseMessage].message shouldEqual "The current version of this API can be found at /v1"
      }
    }

    "return an 'ok' message for GET requests to /v1/health" in {
      // tests:
      Get("/v1/health") ~> routes ~> check {
        responseAs[ResponseMessage].message shouldEqual "ok"
      }
    }

    "leave GET requests to other paths unhandled" in {
      // tests:
      Get("/someOtherPath") ~> routes ~> check {
        handled shouldBe false
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      // tests:
      Put() ~> Route.seal(routes) ~> check {
        status === StatusCodes.MethodNotAllowed
        import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.stringUnmarshaller
        responseAs[String] shouldEqual "HTTP method not allowed, supported methods: GET"
      }
    }
  }
}