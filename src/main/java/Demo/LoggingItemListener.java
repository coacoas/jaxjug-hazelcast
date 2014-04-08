package Demo;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;

public class LoggingItemListener<E> implements ItemListener<E> {
	@Override
	public void itemAdded(ItemEvent<E> e) {
		System.out.println("Added " + e.getItem());
	}

	@Override
	public void itemRemoved(ItemEvent<E> e) {
		System.out.println("Removed " + e.getItem());
	}
}
