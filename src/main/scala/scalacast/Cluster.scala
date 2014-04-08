package Demo

import com.hazelcast.core._
import com.hazelcast.map._
import java.util.{ Map => JUMap }
import com.hazelcast.query.SqlPredicate

class Listener[K, V] extends EntryListener[K, V] {
  override def entryAdded(e: EntryEvent[K, V]) =
    println(s"Added (${e.getKey}, ${e.getValue}")
  override def entryEvicted(e: EntryEvent[K, V]) =
    println(s"Evicted (${e.getKey}, ${e.getValue}")
  override def entryRemoved(e: EntryEvent[K, V]) =
    println(s"Removed (${e.getKey}, ${e.getValue}")
  override def entryUpdated(e: EntryEvent[K, V]) =
    println(s"Updated ${e.getKey} from ${e.getOldValue} to ${e.getValue}")
}

class ItemsListener[A] extends ItemListener[A] { 
  override def itemAdded(e: ItemEvent[A]) = 
    println(s"Added ${e.getItem()}")
  override def itemRemoved(e: ItemEvent[A]) = 
    println(s"Removed ${e.getItem()}")
}

object Implicits {
  class EntryF[K <: AnyRef, V <: AnyRef](val f: (K, V) => V) extends EntryProcessor[K, V]
    with EntryBackupProcessor[K, V] {
    def process(entry: JUMap.Entry[K, V]): Object =
      f(entry.getKey, entry.getValue)
    override def getBackupProcessor = this
    override def processBackup(entry: JUMap.Entry[K, V]): Unit =
      process(entry)
  }

  implicit def toEntryProcessor[K <: AnyRef, V <: AnyRef](f: (K, V) => V): EntryProcessor[K, V] =
    new EntryF(f)

  implicit def toSqlPredicate(s: String) = new SqlPredicate(s)

  def clustered[T](f: (HazelcastInstance) => T): T = {
    val cluster: HazelcastInstance = Hazelcast.newHazelcastInstance()
    try {
      f(cluster)
    } finally {
      cluster.shutdown()
    }
  }
}