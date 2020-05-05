package service.producer

import akka.actor.ActorSystem

import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, Future }
import scala.jdk.CollectionConverters._
import akka.Done
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import com.github.aleksandarskrbic.events.{ BookClickAvro, BookRatingkAvro, OrderEventAvro }
import io.confluent.kafka.serializers.{ AbstractKafkaAvroSerDeConfig, KafkaAvroDeserializerConfig, KafkaAvroSerializer }
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ Serializer, StringSerializer }
import service.generator.Generator

class Producer(generator: Generator)(implicit system: ActorSystem, ec: ExecutionContext) {

  def clicks: Future[Done] =
    Source
      .repeat()
      .map(_ => generator.generateBookClick)
      .map(event => new ProducerRecord("book-click", event.id, event))
      .throttle(300, 1.seconds)
      .runWith(Producer.plainSink(producerSettings[BookClickAvro]))

  def ratings: Future[Done] =
    Source
      .repeat()
      .map(_ => generator.generateBookRating)
      .map(event => new ProducerRecord("book-rating", event.id, event))
      .throttle(5, 1.seconds)
      .runWith(Producer.plainSink(producerSettings[BookRatingkAvro]))

  def orders: Future[Done] =
    Source
      .repeat()
      .map(_ => generator.generateOrderEvent)
      .map(event => new ProducerRecord("order-event", event.id, event))
      .throttle(5, 1.seconds)
      .runWith(Producer.plainSink(producerSettings[OrderEventAvro]))

  val kafkaAvroSerDeConfig = Map[String, String](
    AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG -> "http://localhost:8081",
    KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG -> true.toString
  )

  def producerSettings[T]: ProducerSettings[String, T] = {
    val kafkaAvroSerializer = new KafkaAvroSerializer()
    kafkaAvroSerializer.configure(kafkaAvroSerDeConfig.asJava, false)
    val serializer = kafkaAvroSerializer.asInstanceOf[Serializer[T]]
    ProducerSettings(system, new StringSerializer, serializer).withBootstrapServers("localhost:9092")
  }
}
