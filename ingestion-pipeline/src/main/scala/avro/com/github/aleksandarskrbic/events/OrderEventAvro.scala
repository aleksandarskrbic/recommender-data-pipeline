/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.github.aleksandarskrbic.events

import scala.annotation.switch

final case class OrderEventAvro(
  var id: String,
  var orderId: String,
  var userId: String,
  var books: Seq[BookAvro],
  var totalPrice: Double
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", Seq.empty, 0.0)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 => {
          id
        }.asInstanceOf[AnyRef]
      case 1 => {
          orderId
        }.asInstanceOf[AnyRef]
      case 2 => {
          userId
        }.asInstanceOf[AnyRef]
      case 3 => {
          scala.collection.JavaConverters
            .bufferAsJavaListConverter({
              books map { x => x }
            }.toBuffer)
            .asJava
        }.asInstanceOf[AnyRef]
      case 4 => {
          totalPrice
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.id = {
          value.toString
        }.asInstanceOf[String]
      case 1 =>
        this.orderId = {
          value.toString
        }.asInstanceOf[String]
      case 2 =>
        this.userId = {
          value.toString
        }.asInstanceOf[String]
      case 3 =>
        this.books = {
          value match {
            case (array: java.util.List[_]) => {
              Seq(
                (scala.collection.JavaConverters
                  .asScalaIteratorConverter(array.iterator)
                  .asScala
                  .toSeq map { x => x }: _*)
              )
            }
          }
        }.asInstanceOf[Seq[BookAvro]]
      case 4 =>
        this.totalPrice = {
          value
        }.asInstanceOf[Double]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = OrderEventAvro.SCHEMA$
}

object OrderEventAvro {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"OrderEventAvro\",\"namespace\":\"com.github.aleksandarskrbic.events\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"orderId\",\"type\":\"string\"},{\"name\":\"userId\",\"type\":\"string\"},{\"name\":\"books\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"BookAvro\",\"fields\":[{\"name\":\"itemId\",\"type\":\"long\"},{\"name\":\"authors\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"categories\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"description\",\"type\":\"string\"},{\"name\":\"format\",\"type\":\"string\"},{\"name\":\"lang\",\"type\":\"string\"},{\"name\":\"date\",\"type\":\"string\"},{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"weight\",\"type\":\"double\"},{\"name\":\"price\",\"type\":\"double\"}]}}},{\"name\":\"totalPrice\",\"type\":\"double\"}]}"
  )
}
