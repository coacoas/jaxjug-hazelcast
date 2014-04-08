package Demo;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;

public class LoggingEntryListener<K, V> implements EntryListener<K, V>, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	String logMessage(String message, K key, V value) {
		return String.format("%s (%s, %s)", message, key.toString(), value.toString());
	}

	@Override
	public void entryAdded(EntryEvent<K, V> arg0) {
		System.out.println(logMessage("Added", arg0.getKey(),
				arg0.getValue()));
	}

	@Override
	public void entryEvicted(EntryEvent<K, V> arg0) {
		System.out.println(logMessage("Evicted", arg0.getKey(),
				arg0.getValue()));
	}

	@Override
	public void entryRemoved(EntryEvent<K, V> arg0) {
		System.out.println(logMessage("Removed", arg0.getKey(),
				arg0.getValue()));
	}

	@Override
	public void entryUpdated(EntryEvent<K, V> arg0) {
		System.out.println(logMessage("Updated", arg0.getKey(),
				arg0.getValue()));
	}
}