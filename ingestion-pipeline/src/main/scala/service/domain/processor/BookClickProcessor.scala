package service.domain.processor

import akka.actor.ActorSystem
import akka.kafka.scaladsl.{ Committer, Consumer }
import akka.kafka.Subscriptions
import akka.kafka.scaladsl.Consumer.DrainingControl
import akka.stream.alpakka.cassandra.scaladsl.{ CassandraFlow, CassandraSession }
import akka.stream.alpakka.cassandra.CassandraWriteSettings
import akka.stream.scaladsl.Keep
import akka.Done
import com.github.aleksandarskrbic.events.BookClickAvro
import service.adapter.cassandra.ClickTableOps
import service.adapter.kafka.{ committerSettings, consumerSettingsFor }
import service.domain.model.Click

import scala.concurrent.ExecutionContext

class BookClickProcessor(implicit system: ActorSystem, ec: ExecutionContext, cs: CassandraSession) {

  val consumerSettings = consumerSettingsFor[BookClickAvro]("localhost:9092", "book-click-consumer")
  val topics           = Subscriptions.topics("book-click")

  //Restartable source
  def process: DrainingControl[Done] =
    Consumer
      .sourceWithOffsetContext(consumerSettings, topics)
      .map(_.value)
      .map(clickEvent => Click(clickEvent))
      .via(
        CassandraFlow.withContext(
          CassandraWriteSettings.defaults,
          ClickTableOps.UPDATE_CLICKS_CQL,
          ClickTableOps.statementBinder
        )
      )
      .toMat(Committer.sinkWithOffsetContext(committerSettings))(Keep.both)
      .mapMaterializedValue(DrainingControl.apply)
      .run()
}
