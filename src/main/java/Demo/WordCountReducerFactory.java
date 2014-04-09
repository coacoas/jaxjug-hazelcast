package Demo;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class WordCountReducerFactory implements ReducerFactory<String, Long, Long> {
    private static final long serialVersionUID = 1L;

	@Override
    public Reducer<String, Long, Long> newReducer( String key ) {
        return new WordCountReducer(key);
    }

    private class WordCountReducer extends Reducer<String, Long, Long> {
        private volatile long sum = 0;
        private final String key;

        public WordCountReducer(String key) {
        	this.key = key;
		}

		@Override
        public void reduce( Long value ) {
//        	System.out.println(String.format("REDUCER: (%s,%s)", key, value));
            sum += value.longValue();
        }

        @Override
        public Long finalizeReduce() {
            return sum;
        }
    }
}
