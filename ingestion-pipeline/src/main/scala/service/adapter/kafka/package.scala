package service.adapter

import scala.jdk.CollectionConverters._
import akka.actor.ActorSystem
import akka.kafka.{ CommitterSettings, ConsumerSettings }
import io.confluent.kafka.serializers.{
  AbstractKafkaAvroSerDeConfig,
  KafkaAvroDeserializer,
  KafkaAvroDeserializerConfig
}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{ Deserializer, StringDeserializer }

package object kafka {

  def committerSettings(implicit system: ActorSystem) =
    CommitterSettings(system)

  def consumerSettingsFor[T](kafkaServer: String = "localhost:9092", consumerGroup: String)(
    implicit system: ActorSystem
  ): ConsumerSettings[String, T] = {
    val kafkaAvroDeserializer = new KafkaAvroDeserializer()
    kafkaAvroDeserializer.configure(kafkaAvroSerDeConfig.asJava, false)
    val deserializer = kafkaAvroDeserializer.asInstanceOf[Deserializer[T]]
    ConsumerSettings(system, new StringDeserializer, deserializer)
      .withBootstrapServers(kafkaServer)
      .withGroupId(consumerGroup)
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
  }

  val kafkaAvroSerDeConfig = Map[String, String](
    AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG -> "http://localhost:8081",
    KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG -> true.toString
  )
}
