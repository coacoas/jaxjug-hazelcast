package Demo;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class WordCountCombinerFactory implements CombinerFactory<String, Long, Long> {
    @Override
    public Combiner<String, Long, Long> newCombiner( String key ) {
        return new WordCountCombiner();
    }

    private class WordCountCombiner extends Combiner<String, Long, Long> {
        private long sum = 0;

        @Override
        public void combine( String key, Long value ) {
            sum++;
        }

        @Override
        public Long finalizeChunk() {
        	long chunk = sum;
        	sum = 0;
        	return chunk;
        }
    }
}
