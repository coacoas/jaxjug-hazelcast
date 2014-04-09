package Demo;

import java.util.StringTokenizer;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class WordCountMapper implements
		Mapper<String, String, String, Long> {

	private static final long serialVersionUID = 1L;

	@Override
	public void map(String key, String value, Context<String, Long> context) {
        StringTokenizer tokenizer = new StringTokenizer( value.toLowerCase() );
        while ( tokenizer.hasMoreTokens() ) {
            context.emit( tokenizer.nextToken(), 1L );
        }
	}
}
