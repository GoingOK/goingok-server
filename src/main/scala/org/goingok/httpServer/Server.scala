package org.goingok.httpServer

import akka.http.scaladsl.Http
import org.goingok.BuildInfotest
import org.goingok.data.persistence.db.DatabaseOps

import scala.util.{Failure, Success}



/** The akka-http server
  *
  * The server is started by [[org.goingok.Application]]
  * The generic API structure is described by [[org.goingok.httpServer.GenericApi]]
  * which is bound to the server through the ''routes'' value.
  * Specific endpoints are specified by [[org.goingok.httpServer.GoingOkAPI]] which
  * inherits the ''GenericApi''.
  *
  * The ActorSystem, Materializer and Context are imported with [[org.goingok.GoingOkContext]]
  *
  */
object Server extends GoingOkAPI {
  import org.goingok.GoingOkContext._

  var dbOk = false

  def startServer(address:String, port:Int) = {
    log.info("->> STARTING {} - version {} <<-",BuildInfotest.name,BuildInfotest.version)
    log.info("Connecting to DB server")
    connectDb
    log.info("Starting http server at {}:{}",address,port)
    Http().bindAndHandle(routes,address,port)
  }

  def connectDb: Unit = {
    DatabaseOps.version.onComplete {
      case Success(result:String) => {
        dbOk = true
        log.info("Current version is: "+result)
        // Create tables that don't exist
        DatabaseOps.checkAndCreateTables()
        // Get the number of rows for all tables
        val tableRows = DatabaseOps.tableSizes()
        if(tableRows.isLeft) tableRows.left.map(i => log.info("Database tables exist: {}",i))
        else log.error("There was a problem with accessing the database tables")
      }
      case Failure(e:Exception) => log.error("Could not get version from db: "+e.getMessage)
      case _ => log.error("There was a problem getting the version from the database")
    }
  }
}

