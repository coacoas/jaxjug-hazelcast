package Demo;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.hazelcast.config.Config;
import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

public class WordCountExample {
	private final HazelcastInstance instance; 
	
	
	public WordCountExample(HazelcastInstance instance) {
		this.instance = instance;
	}

	public void load() throws IOException {
		IMap<String, String> data = instance.getMap("files");
		addFile(data, new File("data/f1.txt"));
		addFile(data, new File("data/f2.txt"));
		addFile(data, new File("data/f3.txt"));
		addFile(data, new File("data/f4.txt"));
	}

	private void addFile(IMap<String, String> data, File file) throws IOException {
		data.put(file.getPath(), Files.toString(file, Charsets.UTF_8));
	}

	public JobCompletableFuture<Map<String, Long>> mapreduce() throws InterruptedException, ExecutionException {
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
		return future;	
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException { 
		Config config = new Config();
		config.getGroupConfig().setName("mapreduce");
		config.getGroupConfig().setPassword("hazelcast");
		config.getManagementCenterConfig().setEnabled(false);
		
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
		WordCountExample wc = new WordCountExample(instance);
		wc.load();
		Future<Map<String, Long>> result = wc.mapreduce();
		
		System.out.println(result.get(10, TimeUnit.SECONDS).entrySet());
		instance.shutdown();		
	}
}
