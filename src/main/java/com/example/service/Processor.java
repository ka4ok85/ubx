package com.example.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Processor {

	private String newRequestSetName;
	private String processedRequestSetName;
	private int workersCount = 1;

	
	
	public Processor(String newRequestSetName, String processedRequestSetName, int workersCount) {
		super();
		this.newRequestSetName = newRequestSetName;
		this.processedRequestSetName = processedRequestSetName;
		this.workersCount = workersCount;
	}

	public void run() {
		ExecutorService executor = Executors.newFixedThreadPool(workersCount);
		for (int i = 0; i < workersCount; i++) {
			Runnable worker = new Worker(newRequestSetName, processedRequestSetName);
			executor.execute(worker);
		}

		executor.shutdown();
	}	

	
	public String getNewRequestSetName() {
		return newRequestSetName;
	}
	public void setNewRequestSetName(String newRequestSetName) {
		this.newRequestSetName = newRequestSetName;
	}
	public String getProcessedRequestSetName() {
		return processedRequestSetName;
	}
	public void setProcessedRequestSetName(String processedRequestSetName) {
		this.processedRequestSetName = processedRequestSetName;
	}
	public int getWorkersCount() {
		return workersCount;
	}
	public void setWorkersCount(int workersCount) {
		this.workersCount = workersCount;
	}
	
	
}
