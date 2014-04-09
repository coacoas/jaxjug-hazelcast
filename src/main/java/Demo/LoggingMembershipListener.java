package Demo;

import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

public class LoggingMembershipListener implements MembershipListener {

	@Override
	public void memberAdded(MembershipEvent arg0) {
		System.out.println("New member added: " + arg0.getMember());
	}

	@Override
	public void memberAttributeChanged(MemberAttributeEvent arg0) {
		System.out.println("Member attribute changed: " + arg0.getOperationType()+ " on " + arg0.getMember());
	}

	@Override
	public void memberRemoved(MembershipEvent arg0) {
		System.out.println("Member left the cluster: " + arg0.getMember());
	}

}
