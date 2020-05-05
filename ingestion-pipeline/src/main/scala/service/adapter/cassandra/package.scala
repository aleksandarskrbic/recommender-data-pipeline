package service.adapter

import com.datastax.oss.driver.api.core.cql.{ BoundStatement, PreparedStatement }
import service.domain.model.{ Click, Rating }

package object cassandra {

  val CREATE_RECOMMENDER_KEYSPACE_CQL =
    """
      |CREATE KEYSPACE IF NOT EXISTS recommender WITH REPLICATION = {
      |   'class' : 'SimpleStrategy',
      |   'replication_factor' : 1
      |  };
      |""".stripMargin

  object RatingTableOps {
    val statementBinder: (Rating, PreparedStatement) => BoundStatement =
      (rating, preparedStatement) => preparedStatement.bind(rating.itemId, rating.userId, rating.rating)

    val CREATE_RATINGS_BY_ITEM_CQL =
      """
        |CREATE TABLE IF NOT EXISTS recommender.ratings_by_item (
        |   item_id bigint,
        |   user_id bigint,
        |   rating int,
        |   PRIMARY KEY (item_id, user_id)
        |);
        |""".stripMargin

    val INSERT_RATING_CQL = "INSERT INTO recommender.ratings (item_id, user_id, rating) VALUES (?, ?, ?)"
  }

  object ClickTableOps {
    val statementBinder: (Click, PreparedStatement) => BoundStatement =
      (click, preparedStatement) => preparedStatement.bind(click.itemId)

    val CREATE_CLICK_BY_ITEM_CQL =
      """
        |CREATE TABLE IF NOT EXISTS recommender.clicks_by_item (
        |   item_id bigint,
        |   clicks counter,
        |   PRIMARY KEY (item_id)
        |);
        |""".stripMargin

    val UPDATE_CLICKS_CQL = "UPDATE recommender.clicks_by_item SET clicks = clicks + 1 WHERE item_id = ?"
  }
}
