/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.github.aleksandarskrbic.events

import scala.annotation.switch

final case class BookAvro(
  var itemId: Long,
  var authors: Seq[String],
  var categories: Seq[String],
  var description: String,
  var format: String,
  var lang: String,
  var date: String,
  var title: String,
  var weight: Double,
  var price: Double
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(0L, Seq.empty, Seq.empty, "", "", "", "", "", 0.0, 0.0)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 => {
          itemId
        }.asInstanceOf[AnyRef]
      case 1 => {
          scala.collection.JavaConverters
            .bufferAsJavaListConverter({
              authors map { x => x }
            }.toBuffer)
            .asJava
        }.asInstanceOf[AnyRef]
      case 2 => {
          scala.collection.JavaConverters
            .bufferAsJavaListConverter({
              categories map { x => x }
            }.toBuffer)
            .asJava
        }.asInstanceOf[AnyRef]
      case 3 => {
          description
        }.asInstanceOf[AnyRef]
      case 4 => {
          format
        }.asInstanceOf[AnyRef]
      case 5 => {
          lang
        }.asInstanceOf[AnyRef]
      case 6 => {
          date
        }.asInstanceOf[AnyRef]
      case 7 => {
          title
        }.asInstanceOf[AnyRef]
      case 8 => {
          weight
        }.asInstanceOf[AnyRef]
      case 9 => {
          price
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.itemId = {
          value
        }.asInstanceOf[Long]
      case 1 =>
        this.authors = {
          value match {
            case (array: java.util.List[_]) => {
              Seq((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
                x.toString
              }: _*))
            }
          }
        }.asInstanceOf[Seq[String]]
      case 2 =>
        this.categories = {
          value match {
            case (array: java.util.List[_]) => {
              Seq((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
                x.toString
              }: _*))
            }
          }
        }.asInstanceOf[Seq[String]]
      case 3 =>
        this.description = {
          value.toString
        }.asInstanceOf[String]
      case 4 =>
        this.format = {
          value.toString
        }.asInstanceOf[String]
      case 5 =>
        this.lang = {
          value.toString
        }.asInstanceOf[String]
      case 6 =>
        this.date = {
          value.toString
        }.asInstanceOf[String]
      case 7 =>
        this.title = {
          value.toString
        }.asInstanceOf[String]
      case 8 =>
        this.weight = {
          value
        }.asInstanceOf[Double]
      case 9 =>
        this.price = {
          value
        }.asInstanceOf[Double]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = BookAvro.SCHEMA$
}

object BookAvro {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"BookAvro\",\"namespace\":\"com.github.aleksandarskrbic.events\",\"fields\":[{\"name\":\"itemId\",\"type\":\"long\"},{\"name\":\"authors\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"categories\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"description\",\"type\":\"string\"},{\"name\":\"format\",\"type\":\"string\"},{\"name\":\"lang\",\"type\":\"string\"},{\"name\":\"date\",\"type\":\"string\"},{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"weight\",\"type\":\"double\"},{\"name\":\"price\",\"type\":\"double\"}]}"
  )
}
