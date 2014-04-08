package Demo;

import java.io.Serializable;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Sample implements Runnable, Serializable {
	private static final long serialVersionUID = 1L;

	public Sample() {
	};
	
	@Override
	public void run() {
		Config config = new Config();
		config.getGroupConfig().setName("JaxJUG");
		config.getGroupConfig().setPassword("hazelcast");

		HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

		mapOps(instance);
		
	}

	/**
	 * @param instance
	 */
	public void mapOps(HazelcastInstance instance) {
		IMap<Integer, String> data = instance.getMap("data");

		data.put(1, "London");
		data.put(2, "Tokyo");
		data.put(3, "Jacksonville");
		data.put(4, "Paris");
		data.put(5, "Charlottesville");

		data.addLocalEntryListener(new LoggingEntryListener<Integer, String>());
		
		data.addInterceptor(new TestInterceptor());
		
		try { 
			data.put(6, "Constantinople");
		} catch (Exception e) { 
			System.out.println(e.getMessage());
		}
	};
}
