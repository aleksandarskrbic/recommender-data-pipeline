package service

import scala.util.{ Failure, Success }
import akka.actor.ActorSystem
import service.generator.{ DataLoader, Generator }
import service.producer.Producer

object Main extends App {

  implicit val system     = ActorSystem("actor-system")
  implicit val dispatcher = system.dispatcher

  val loader = new DataLoader()

  loader.dataset.onComplete {
    case Success(result) =>
      val generator = new Generator(result.toList)
      val producer  = new Producer(generator)
      val clicks    = producer.clicks
      val ratings   = producer.ratings
      val orders    = producer.orders
    case Failure(_) =>
      system.terminate()
  }
}
