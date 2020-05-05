package service

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import service.adapter.cassandra.CassandraAdapter
import service.domain.processor.{ BookClickProcessor, BookRatingProcessor }

import scala.util.{ Failure, Success }

object Main extends LazyLogging {

  implicit val system     = ActorSystem("actor-system")
  implicit val dispatcher = system.dispatcher // add external dispatcher

  val cassandraAdapter          = new CassandraAdapter()
  implicit val cassandraSession = cassandraAdapter.cassandraSession

  lazy val bookClickProcessor  = new BookClickProcessor()
  lazy val bookRatingProcessor = new BookRatingProcessor()

  def main(args: Array[String]): Unit = {
    val done = cassandraAdapter.prepareCassandra

    done.onComplete {
      case Success(_) =>
        startProcessingPipelines()
      case Failure(ex) =>
        logger.error("Unable to initialize Cassandra.", ex)
        system.terminate()
    }
  }

  def startProcessingPipelines(): Unit = {
    bookClickProcessor.process
    bookRatingProcessor.process
  }

}
