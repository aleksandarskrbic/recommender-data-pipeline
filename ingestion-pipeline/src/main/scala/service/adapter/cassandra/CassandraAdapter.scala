package service.adapter.cassandra

import akka.actor.ActorSystem
import akka.stream.alpakka.cassandra.CassandraSessionSettings
import akka.stream.alpakka.cassandra.scaladsl.CassandraSessionRegistry
import akka.Done

import scala.concurrent.{ ExecutionContext, Future }

class CassandraAdapter(implicit system: ActorSystem, ec: ExecutionContext) {

  lazy val sessionSettings      = CassandraSessionSettings()
  implicit val cassandraSession = CassandraSessionRegistry.get(system).sessionFor(sessionSettings)

  def prepareCassandra: Future[Done] =
    for {
      _ <- cassandraSession.executeDDL(CREATE_RECOMMENDER_KEYSPACE_CQL)
      _ <- cassandraSession.executeDDL(RatingTableOps.CREATE_RATINGS_BY_ITEM_CQL)
      _ <- cassandraSession.executeDDL(ClickTableOps.CREATE_CLICK_BY_ITEM_CQL)
    } yield Done.done()
}
