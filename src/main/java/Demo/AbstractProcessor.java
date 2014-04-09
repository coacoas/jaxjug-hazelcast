package Demo;

import java.util.Map.Entry;

import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;

public abstract class AbstractProcessor<K, V> implements EntryProcessor<K, V>, EntryBackupProcessor<K, V> { 
	private static final long serialVersionUID = 1L;
	@Override
	public EntryBackupProcessor<K, V> getBackupProcessor() {
		return this;
	}
	@Override
	public void processBackup(Entry<K, V> arg0) {
		process(arg0);
	}
}