package service.generator

import java.nio.file.{ Path, Paths }
import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Random, Try }
import akka.actor.ActorSystem
import akka.stream.alpakka.csv.scaladsl.CsvParsing
import akka.stream.scaladsl.{ FileIO, Sink }

object DataLoader {
  type Record = (Int, String)
  case class Dataset(categories: Map[Int, String], authors: Map[Int, String], formats: Map[Int, String])
  case class Book(
    id: Option[Long],
    authors: List[String],
    categories: List[String],
    description: String,
    format: String,
    lang: String,
    date: String,
    title: String,
    weight: Option[Double],
    price: Double
  )
}

class DataLoader(implicit system: ActorSystem, ec: ExecutionContext) {
  import DataLoader._

  def dataset: Future[Seq[Book]] =
    for {
      categories <- keyValueLoader("data/categories.csv")
      authors    <- keyValueLoader("data/authors.csv")
      formats    <- keyValueLoader("data/formats.csv")
      dataset    = Dataset(toMap(categories), toMap(authors), toMap(formats))
      books      <- bookLoader("data/dataset.csv", dataset)
    } yield books

  private def keyValueLoader(path: String): Future[Seq[Record]] =
    FileIO
      .fromPath(createPath(path))
      .via(CsvParsing.lineScanner())
      .map(_.map(_.utf8String))
      .map { case id :: name :: _ => Try(id.toInt) -> name }
      .filter(record => record._1.isSuccess && record._2.nonEmpty)
      .map(record => record._1.get -> record._2)
      .runWith(Sink.seq)

  private def bookLoader(path: String, dataset: Dataset): Future[Seq[Book]] =
    FileIO
      .fromPath(createPath(path))
      .via(CsvParsing.lineScanner(maximumLineLength = 100 * 1024))
      .drop(1)
      .take(35000)
      .map(_.map(_.utf8String.trim))
      .map(row => toBook(row, dataset))
      .filter(_.id.isDefined)
      .runWith(Sink.seq)

  private def toBook(list: List[String], dataset: Dataset): Book = {
    val authorIds   = toList(list.head)
    val categoryIds = toList(list(2))

    val authors     = authorIds.flatten.map(id => dataset.authors.getOrElse(id, ""))
    val categories  = categoryIds.flatten.map(id => dataset.categories.getOrElse(id, ""))
    val description = list(3)
    val format: String = Try(list(10).toInt).toOption match {
      case Some(value) => dataset.formats.getOrElse(value, "")
      case None        => ""
    }
    val id     = Try(list(11).toLong).toOption
    val lang   = list(17)
    val date   = list(18)
    val title  = list(22)
    val weight = Try(list(24).toDouble).toOption

    Book(id, authors, categories, description, format, lang, date, title, weight, randomPrice)
  }

  private def toList(line: String): List[Option[Int]] = {
    val ids = line.substring(1, line.length() - 1)
    if (ids.isEmpty) List(Some(-1))
    else {
      val parts = ids.split(",")
      if (parts.size == 1) List(Try(parts(0).toInt).toOption)
      else {
        parts.map(id => Try(id.toInt).toOption).toList
      }
    }
  }

  private def toMap(seq: Seq[Record]): Map[Int, String] =
    seq.toMap

  private def createPath(path: String): Path =
    Paths.get(getClass.getClassLoader.getResource(path).getPath)

  private def randomPrice: Double =
    (Random.nextDouble() * 110) - 10
}
