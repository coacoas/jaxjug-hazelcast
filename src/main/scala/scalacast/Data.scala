package scalacast

import com.hazelcast.core.Hazelcast
import com.hazelcast.query.SqlPredicate
import java.util.Collection
import Demo.Listener

case class Employee(name: String, age: Int, salary: Long, active: Boolean)

class Query {
	val instance = Hazelcast.newHazelcastInstance()
	val m = instance.getMap[Int, Employee]("personnel")
    
	m.put(1, Employee("Bill", 41, 50, false))
	m.put(2, Employee("Eyal", 25, 250000, true))
	
	m.addIndex("name", false);
	m.addIndex("salary", true);
	
	val expensive: Collection[Employee] = m.values(new SqlPredicate("active=true and salary > 100000"))
	
//	m.addEntryListener(new Listener[Int, Employee], new SqlPredicate("age < 18"), true)
	
}