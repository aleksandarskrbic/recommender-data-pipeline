package service.generator

import java.util.UUID
import scala.concurrent.ExecutionContext
import com.github.aleksandarskrbic.events.{ BookAvro, BookClickAvro, BookRatingkAvro, OrderEventAvro }
import service.generator.DataLoader.Book

class Generator(books: List[Book])(implicit ec: ExecutionContext) {
  import util._

  def generateBookClick: BookClickAvro =
    BookClickAvro(uuid, userId, ipAddress, pickBook)

  def generateBookRating: BookRatingkAvro =
    BookRatingkAvro(uuid, userId.get, rating, pickBook)

  def generateOrderEvent: OrderEventAvro = {
    val numberOfItems = Random.nextInt(10) + 1
    val books         = (1 to numberOfItems).map(_ => pickBook)
    val totalPrice    = books.map(_.price).sum
    OrderEventAvro(uuid, uuid, userId.get, books, totalPrice)
  }

  private def pickBook: BookAvro = {
    val book = books(Random.nextInt(books.length))
    BookAvro(
      book.id.get,
      book.authors,
      book.categories,
      book.description,
      book.format,
      book.lang,
      book.date,
      book.title,
      book.weight.getOrElse(1),
      book.price
    )
  }

  private def uuid: String =
    UUID.randomUUID().toString

  private def userId: Option[String] =
    Some(Random.nextInt(1000000).toString)

  private def ipAddress: String =
    Random.nextInt(256) + "." + Random.nextInt(256) + "." + Random.nextInt(256) + "." + Random.nextInt(256)

  private def rating: Int =
    Random.nextInt(6)
}
