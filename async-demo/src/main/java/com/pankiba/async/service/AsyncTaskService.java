package com.pankiba.async.service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncTaskService {

	// Simple async method (no return value)
	@Async
	public void asyncMethodWithoutReturn() {

		log.info("AsyncTaskService :: asyncMethodWithoutReturn :: stared in new thread : "
				+ Thread.currentThread().getName());

		// Simulate long-running task
		asyncSimulation();

		System.out.println("Async task completed!");

	}

	// Async method returning a CompletableFuture
	@Async
	public CompletableFuture<String> asyncMethodWithReturn() {

		log.info("AsyncTaskService :: asyncMethodWithReturn :: stared in new thread : "
				+ Thread.currentThread().getName());
		// Simulate long-running task
		asyncSimulation();
		return CompletableFuture.completedFuture("Async task result!");
	}

	private void asyncSimulation() {

		long endTime = System.currentTimeMillis() + 20_000; // Calculate end time once
		long nextPrintTime = System.currentTimeMillis() + 5_000; // Next print timestamp

		while (System.currentTimeMillis() < endTime) {
			long currentTime = System.currentTimeMillis();

			// Print if it's time (more efficient condition)
			if (currentTime >= nextPrintTime) {
				long remaining = (endTime - currentTime + 999) / 1000; // Round up seconds
				log.info("Running... Time left: " + remaining + "s");
				nextPrintTime += 5_000; // Schedule next print
			}

			// More efficient sleep strategy
			long sleepTime = Math.min(100, nextPrintTime - currentTime);
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // Proper interrupt handling
					break;
				}
			}
		}
	}
}
