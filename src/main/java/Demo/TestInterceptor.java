package Demo;

import com.hazelcast.map.MapInterceptor;

public class TestInterceptor implements MapInterceptor { 
  @Override
  public void afterGet(Object value) { 
    System.out.println("Getting: " + value.toString());
  }
  @Override 
  public void afterPut(Object value) { 
    System.out.println("Putting value: " + value.toString());
  }
  @Override
  public void afterRemove(Object value) { 
    System.out.println("Removing value: " + value.toString());
  }

  @Override
  public Object interceptGet(Object value) {
  	if (value instanceof Integer && (Integer) value < 10) { 
  		return (Integer)value * 10;
  	} else { 
  		return value;
  	}
  }

  @Override
  public Object interceptPut(Object oldValue, Object newValue) { 
    if (oldValue instanceof Integer && (Integer)oldValue > 100) { 
    	throw new RuntimeException("You can't modify a value that big");
    } 
    return newValue;
  }

  @Override
  public Object interceptRemove(Object value) { 
  	return value;
  }

}
