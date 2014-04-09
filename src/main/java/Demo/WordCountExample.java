package Demo;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

public class WordCountExample {
	
	public void load(HazelcastInstance instance) throws IOException {
		IMap<String, String> data = instance.getMap("files");
		addFile(data, new File("data/f1.txt"));
		addFile(data, new File("data/f2.txt"));
		addFile(data, new File("data/f3.txt"));
		addFile(data, new File("data/f4.txt"));
	}

	private void addFile(IMap<String, String> data, File file) throws IOException {
		data.put(file.getPath(), Files.toString(file, Charsets.UTF_8));
	}

	public void mapreduce(HazelcastInstance instance) throws InterruptedException, ExecutionException {
		JobTracker tracker = instance.getJobTracker("word-count");
		IMap<String, String> data = instance.getMap("files");
		KeyValueSource<String, String> source = KeyValueSource.fromMap(data);
		Job<String, String> job = tracker.newJob(source);
		
		JobCompletableFuture<Map<String, Long>> future = 
				job.mapper(new WordCountMapper()).
					combiner(new WordCountCombinerFactory()).
					reducer(new WordCountReducerFactory()).
					submit();
		
		future.andThen(new ExecutionCallback<Map<String, Long>>() {
			@Override
			public void onFailure(Throwable arg0) {
				System.out.println("MapReduce failed: " + arg0.getMessage());
			}

			@Override
			public void onResponse(Map<String, Long> arg0) {
				for (Map.Entry<String, Long> words : arg0.entrySet()) {
					System.out.println(words.getKey() + " -> " + words.getValue());
				}
			}});		
	}

}
