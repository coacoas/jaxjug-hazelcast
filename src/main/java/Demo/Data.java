package Demo;

import scalacast.Employee;
import scalacast.Listener;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import com.hazelcast.transaction.TransactionContext;

public class Data {
	public static void main(String[] args) throws InterruptedException {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();
		IMap<Integer, String> m = instance.getMap("data");
		TransactionContext ctx = instance.newTransactionContext();
		try { 
			ctx.beginTransaction();
			m.put(1, "London");
			m.put(2, "Tokyo");
			m.put(3, "Jacksonville");
			m.put(4, "Paris");
			m.put(5, "New York");
			ctx.commitTransaction();
		} catch (Exception e) { 
			ctx.rollbackTransaction();
		
		IMap<Integer, Employee> e = instance.getMap("employees");
		e.addEntryListener(new Listener<Integer, Employee>(), new SqlPredicate("age < 18"), false);
	}
}
