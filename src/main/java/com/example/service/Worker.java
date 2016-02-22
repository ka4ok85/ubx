package com.example.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.example.AppConfig;
import com.example.domain.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@ComponentScan("com.example")
@ContextConfiguration(classes={AppConfig.class})
public class Worker implements Runnable {

	@Autowired
	private JedisPool jedisPool;
	
	private String newRequestSetName;
	private String processedRequestSetName;

	public Worker(String newRequestSetName, String processedRequestSetName) {
		super();
		this.newRequestSetName = newRequestSetName;
		this.processedRequestSetName = processedRequestSetName;
		
		jedisPool =  new JedisPool(new JedisPoolConfig(), "localhost");
	}

	@Override
	public void run() {
		System.out.println(jedisPool);
		Jedis jedis = null;
		String requestJSON;
        try {
        	jedis = jedisPool.getResource();
        	File file;
        	Scanner scanner;
        	String email;
        	while ((requestJSON = jedis.spop(newRequestSetName)) != null) {
            	Gson gson = new GsonBuilder().create();
            	Request request = gson.fromJson(requestJSON, Request.class);
            	file = new File(request.getFilename());
            	scanner = new Scanner(file);
            	BloomFilter<String> bloomFilter = new FilterBuilder(1000, 0.01)
            		    .name(request.getName())
            		    .redisBacked(true)
            		    .buildBloomFilter();

            	while (scanner.hasNextLine()) {
            		email = scanner.nextLine();
            		bloomFilter.add(email);
            	}
            	
            	jedis.sadd(processedRequestSetName, requestJSON);
        	}

        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
        	if (jedis != null) {
        		jedis.close();
        	}
        }
		
	}

}
