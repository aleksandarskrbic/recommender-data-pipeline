package service.domain.model

import com.github.aleksandarskrbic.events.BookClickAvro

final case class Click(itemId: Long)

object Click {
  def apply(clickEvent: BookClickAvro): Click = Click(clickEvent.item.itemId)
}
