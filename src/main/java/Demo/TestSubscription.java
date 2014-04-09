package Demo;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class TestSubscription implements MessageListener<String> {

	@Override
	public void onMessage(Message<String> arg0) {
		System.out.println("Received: " + arg0);
	} 
	
}