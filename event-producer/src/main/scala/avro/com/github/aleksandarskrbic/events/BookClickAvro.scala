/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.github.aleksandarskrbic.events

import scala.annotation.switch

final case class BookClickAvro(var id: String, var userId: Option[String], var ipAddress: String, var item: BookAvro)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", None, "", new BookAvro)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 => {
          id
        }.asInstanceOf[AnyRef]
      case 1 => {
          userId match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 2 => {
          ipAddress
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
          value match {
            case null => None
            case _    => Some(value.toString)
          }
        }.asInstanceOf[Option[String]]
      case 2 =>
        this.ipAddress = {
          value.toString
        }.asInstanceOf[String]
      case 3 =>
        this.item = {
          value
        }.asInstanceOf[BookAvro]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = BookClickAvro.SCHEMA$
}

object BookClickAvro {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"BookClickAvro\",\"namespace\":\"com.github.aleksandarskrbic.events\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"userId\",\"type\":[\"null\",\"string\"]},{\"name\":\"ipAddress\",\"type\":\"string\"},{\"name\":\"item\",\"type\":{\"type\":\"record\",\"name\":\"BookAvro\",\"fields\":[{\"name\":\"itemId\",\"type\":\"long\"},{\"name\":\"authors\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"categories\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"description\",\"type\":\"string\"},{\"name\":\"format\",\"type\":\"string\"},{\"name\":\"lang\",\"type\":\"string\"},{\"name\":\"date\",\"type\":\"string\"},{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"weight\",\"type\":\"double\"},{\"name\":\"price\",\"type\":\"double\"}]}}]}"
  )
}
