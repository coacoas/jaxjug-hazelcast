package Demo;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;

public class LoggingListener<K, V> implements EntryListener<K, V> {

	@Override
	public void entryAdded(EntryEvent<K, V> e) {
		System.out.println("Add entry event: " + e.getEventType());
		System.out.println(e.getOldValue());
		System.out.println(e.getValue());
		System.out.println(e.getMember());
		
	}

	@Override
	public void entryEvicted(EntryEvent<K, V> e) {
		System.out.println("Add entry event: " + e.getEventType());
		System.out.println(e.getValue());
		System.out.println(e.getMember());
	}

	@Override
	public void entryRemoved(EntryEvent<K, V> e) {
		System.out.println("Removed entry event: " + e.getEventType());
		System.out.println(e.getValue());
		System.out.println(e.getMember());
	}

	@Override
	public void entryUpdated(EntryEvent<K, V> e) {
		System.out.println("Updated entry event: " + e.getEventType());
		System.out.println(e.getValue());
		System.out.println(e.getMember());
	}

}
