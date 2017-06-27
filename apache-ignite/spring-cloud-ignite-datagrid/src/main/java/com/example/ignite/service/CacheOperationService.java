package com.example.ignite.service;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
@Component
public class CacheOperationService {

	@Autowired
	IgniteCache<Integer, String> cache;

	public IgniteCache<Integer, String> operation(){

		// Store keys in cache (values will end up on different cache nodes).
		for (int i = 0; i < 10; i++)
			cache.put(i, Integer.toString(i));

		for (int i = 0; i < 10; i++)
			System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');


		// Put-if-absent which returns previous value.
	/*	String oldVal = cache.getAndPutIfAbsent("Hello", Integer.toString(11));
		System.out.println("Put-if-absent which returns previous value "+oldVal);

		// Put-if-absent which returns boolean success flag.
		boolean success = cache.putIfAbsent("World", Integer.toString(22));
		System.out.println("Put-if-absent which returns boolean success flag. "+success);

		// Replace-if-exists operation (opposite of getAndPutIfAbsent), returns previous value.
		oldVal = cache.getAndReplace("Hello", Integer.toString(11));
		System.out.println("Replace-if-exists operation (opposite of getAndPutIfAbsent), returns previous value. "+oldVal);

		// Replace-if-exists operation (opposite of putIfAbsent), returns boolean success flag.
		success = cache.replace("World", Integer.toString(22));
		System.out.println("Replace-if-exists operation (opposite of putIfAbsent), returns boolean success flag. "+success);

		// Replace-if-matches operation.
		success = cache.replace("World", Integer.toString(2), Integer.toString(22));
		System.out.println("Replace-if-matches operation. "+success);

		// Remove-if-matches operation.
		success = cache.remove("Hello", Integer.toString(1));
		System.out.println("Remove-if-matches operation. "+success);*/

		return cache;
	}

	public IgniteCache<Integer, String> operationEntryProcessor(){

		for ( int i = 0; i < 10; i++ ) {
			cache.invoke(i, new EntryProcessor<Integer, String, Void>() {
				@Override
				public Void process(MutableEntry<Integer, String> entry, Object... arguments) throws EntryProcessorException {
					String val = entry.getValue();
					entry.setValue(val == null ? 1+"" : val + 1);
					return null;
				}
			});
		}
		
		return cache;
	}


	public IgniteCache<Integer, String> asyncOperation(){

		// Enable asynchronous mode. 启用异步模式
		IgniteCache<Integer, String> asyncCache  = cache.withAsync();

		//Asynhronously store value in cache.  Asynhronously值存储在缓存中
		asyncCache.getAndPut(1,"ssssss");
		asyncCache.getAndPut(2,"sssfffsss");

		// Get future for the above invocation.
		IgniteFuture<Integer> fut = asyncCache.future();

		// Asynchronously listen for the operation to complete.
		fut.listen(f -> {
			System.out.println("Previous cache value: " + f.get());
		});

		return asyncCache;
	}

}
