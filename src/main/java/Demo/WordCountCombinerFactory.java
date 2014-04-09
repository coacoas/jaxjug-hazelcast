package Demo;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class WordCountCombinerFactory implements CombinerFactory<String, Long, Long> {
    private static final long serialVersionUID = 1L;

	@Override
    public Combiner<String, Long, Long> newCombiner( String key ) {
        return new WordCountCombiner(key);
    }

    private class WordCountCombiner extends Combiner<String, Long, Long> {
        private long sum = 0;
		private final String key;

        public WordCountCombiner(String key) {
        	this.key = key;
		}

		@Override
        public void combine( String key, Long value ) {
//			System.out.println(String.format("COMBINER: (%s, %s)", key, value));
            sum++;
        }

        @Override
        public Long finalizeChunk() {
//        	System.out.println("COMBINER: Chunk " + key);
        	long chunk = sum;
        	sum = 0;
        	return chunk;
        }
    }
}
