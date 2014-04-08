package Demo

import com.hazelcast.core._
import com.hazelcast.map._
import java.util.{Map => JUMap}

object Implicits { 
	class EntryF[K <: AnyRef,V <: AnyRef](val f: (K,V) => V) extends EntryProcessor[K,V] 
		with EntryBackupProcessor[K,V] {
		def process(entry: JUMap.Entry[K,V]): Object = 
			f(entry.getKey, entry.getValue)
		override def getBackupProcessor = this
		override def processBackup(entry: JUMap.Entry[K,V]): Unit = 
		  process(entry)
	}

	implicit def toEntryProcessor[K <: AnyRef, V <: AnyRef](f: (K,V) => V): EntryProcessor[K,V] =
	  new EntryF(f)

	def clustered[T](f: (HazelcastInstance) => T): T = {
		val cluster: HazelcastInstance = Hazelcast.newHazelcastInstance()
		try { 
			f(cluster)
		} finally { 
			cluster.shutdown()
		}
	}
}