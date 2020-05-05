/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.github.aleksandarskrbic.events

import scala.annotation.switch

final case class BookRatingkAvro(var id: String, var userId: String, var rating: Int, var item: BookAvro)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", 0, new BookAvro)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 => {
          id
        }.asInstanceOf[AnyRef]
      case 1 => {
          userId
        }.asInstanceOf[AnyRef]
      case 2 => {
          rating
        }.asInstanceOf[AnyRef]
      case 3 => {
          item
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
        this.userId = {
          value.toString
        }.asInstanceOf[String]
      case 2 =>
        this.rating = {
          value
        }.asInstanceOf[Int]
      case 3 =>
        this.item = {
          value
        }.asInstanceOf[BookAvro]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = BookRatingkAvro.SCHEMA$
}

object BookRatingkAvro {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"BookRatingkAvro\",\"namespace\":\"com.github.aleksandarskrbic.events\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"userId\",\"type\":\"string\"},{\"name\":\"rating\",\"type\":\"int\"},{\"name\":\"item\",\"type\":{\"type\":\"record\",\"name\":\"BookAvro\",\"fields\":[{\"name\":\"itemId\",\"type\":\"long\"},{\"name\":\"authors\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"categories\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"description\",\"type\":\"string\"},{\"name\":\"format\",\"type\":\"string\"},{\"name\":\"lang\",\"type\":\"string\"},{\"name\":\"date\",\"type\":\"string\"},{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"weight\",\"type\":\"double\"},{\"name\":\"price\",\"type\":\"double\"}]}}]}"
  )
}
