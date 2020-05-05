package service.domain.model

import com.github.aleksandarskrbic.events.BookRatingkAvro

final case class Rating(itemId: Long, userId: Long, rating: Int)

object Rating {
  def apply(ratingEvent: BookRatingkAvro): Rating =
    Rating(ratingEvent.item.itemId, ratingEvent.userId.toLong, ratingEvent.rating)
}
