package service.domain.processor

import akka.actor.ActorSystem
import akka.kafka.Subscriptions
import akka.kafka.scaladsl.{ Committer, Consumer }
import akka.kafka.scaladsl.Consumer.DrainingControl
import akka.stream.alpakka.cassandra.scaladsl.{ CassandraFlow, CassandraSession }
import akka.Done
import akka.stream.alpakka.cassandra.CassandraWriteSettings
import akka.stream.scaladsl.Keep
import com.github.aleksandarskrbic.events.BookRatingkAvro
import service.adapter.cassandra.RatingTableOps
import service.adapter.kafka.{ committerSettings, consumerSettingsFor }
import service.domain.model.Rating

import scala.concurrent.ExecutionContext

class BookRatingProcessor(implicit system: ActorSystem, ec: ExecutionContext, cs: CassandraSession) {

  val consumerSettings = consumerSettingsFor[BookRatingkAvro]("localhost:9092", "book-rating-consumer")
  val topics           = Subscriptions.topics("book-rating")

  def process: DrainingControl[Done] =
    Consumer
      .sourceWithOffsetContext(consumerSettings, topics)
      .map(_.value)
      .map(ratingEvent => Rating(ratingEvent))
      .via(
        CassandraFlow.withContext(
          CassandraWriteSettings.defaults,
          RatingTableOps.INSERT_RATING_CQL,
          RatingTableOps.statementBinder
        )
      )
      .toMat(Committer.sinkWithOffsetContext(committerSettings))(Keep.both)
      .mapMaterializedValue(DrainingControl.apply)
      .run()
}
