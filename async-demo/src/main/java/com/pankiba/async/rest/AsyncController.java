package com.pankiba.async.rest;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pankiba.async.service.AsyncTaskService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AsyncController {

	@Autowired
	private AsyncTaskService asyncTaskService;

	@GetMapping("/run-async")
	public String runAsyncTask() {

		log.info("AsyncController :: before calling asyncMethodWithoutReturn :: " + Thread.currentThread().getName());
		asyncTaskService.asyncMethodWithoutReturn();
		log.info("AsyncController :: after calling asyncMethodWithoutReturn :: " + Thread.currentThread().getName());
		return "Async task started! Check logs.";
	}

	@GetMapping("/run-async-with-result")
	public CompletableFuture<String> runAsyncTaskWithResult() {

		log.info("AsyncController :: before calling asyncMethodWithReturn :: " + Thread.currentThread().getName());
		CompletableFuture<String> completableFuture = asyncTaskService.asyncMethodWithReturn();
		log.info("AsyncController :: after calling asyncMethodWithReturn :: " + Thread.currentThread().getName());
		return completableFuture;
	}
}
