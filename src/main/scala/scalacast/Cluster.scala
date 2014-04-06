import com.hazelcast.core._
import com.hazelcast.map._
import java.util.{Map => JUMap}

object Demo { 
	abstract class EntryF[K,V] extends EntryProcessor[K,V] 
		with EntryBackupProcessor[K,V] {
		def process(entry: JUMap.Entry[K,V]): Object
		override def getBackupProcessor = this
		override def processBackup(entry: JUMap.Entry[K,V]): Unit = 
		  process(entry)
	}

	def clustered[T](f: (HazelcastInstance) => T): T = {
		val cluster: HazelcastInstance = Hazelcast.newHazelcastInstance()
		try { 
			f(cluster)
		} finally { 
			cluster.shutdown()
		}
	}
}