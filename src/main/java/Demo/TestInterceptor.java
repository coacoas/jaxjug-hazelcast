package Demo;

import com.hazelcast.map.MapInterceptor;

public class TestInterceptor implements MapInterceptor {
	private static final long serialVersionUID = 1L;

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
		if (value instanceof String) {
			return ((String) value).toUpperCase();
		} else {
			return value;
		}
	}

	@Override
	public Object interceptPut(Object oldValue, Object newValue) {
		if (newValue.equals("Constantinople")) {
			throw new RuntimeException("It's Istanbul, not Constantinople!");
		}
		return newValue;
	}

	@Override
	public Object interceptRemove(Object value) {
		return value;
	}

}
