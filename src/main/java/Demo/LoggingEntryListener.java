package Demo;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;

public class LoggingEntryListener<K, V> implements EntryListener<K, V> {
	@Override
	public void entryAdded(EntryEvent<K, V> e) {
		System.out.println("Added " + 
				e.getKey() + ", " + e.getValue());
	}
	@Override
	public void entryEvicted(EntryEvent<K, V> e) {
		System.out.println("Evicted " + 
				e.getKey() + ", " + e.getValue());
	}
	@Override
	public void entryRemoved(EntryEvent<K, V> e) {
		System.out.println("Removed " + 
				e.getKey() + ", " + e.getValue());
	}
	@Override
	public void entryUpdated(EntryEvent<K, V> e) {
		System.out.println("Updated " + e.getKey() + 
				" from " + e.getOldValue()
				+ " to " + e.getValue());
	}
}