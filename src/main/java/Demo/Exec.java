package Demo;

import java.util.Map.Entry;
import java.util.concurrent.Callable;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;

public class Exec {
	public static void main(String[] args) {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();
		final IMap<Integer, String> m = instance.getMap("data");
		ILock l = instance.getLock("lock");
		try {
			l.lock();
			String value = m.get(1);
			m.put(1, value.toUpperCase());
		} finally {
			l.unlock();
		}
		
		m.executeOnKey(1, new AbstractProcessor<Integer, String>() {
			@Override
			public Object process(Entry<Integer, String> arg0) {
				String newValue = arg0.getValue().toUpperCase();
				m.put(arg0.getKey(), newValue);
				return newValue;
			}
		});
	}
}
