package com.example.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.UbxApplication;
import com.example.mvcmodels.UBXRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;;

@Controller
public class WelcomeController {

	@Autowired
	private JedisPool jedisPool;
	
	private static final Logger log = LoggerFactory.getLogger(UbxApplication.class);
	
	private static final String newRequestSetName = "ubxRequests";
	private static final String processedRequestSetName = "ubxRequestsProcessed";

    @RequestMapping(value="/welcome", method=RequestMethod.GET)
    public String welcome(Model model) {

        return "welcome";
    }

    
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public String addSingleRequest(@Valid UBXRequest uBXRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "welcome";
        }    	

        Jedis jedis = null;
        try {
        	Gson gson = new Gson();
        	String uBXRequestJSONString = gson.toJson(uBXRequest);
        	jedis = jedisPool.getResource();
        	jedis.sadd(newRequestSetName, uBXRequestJSONString);
        } finally {
        	if (jedis != null) {
        		jedis.close();
        	}
        }
        
        return "add-success";
    }
	
    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String addTestRequests(Model model) {

    	ArrayList<String> urlList = new ArrayList<String>();
    	urlList.add("http://localhost:8080/add?id=2&name=males&filename=batch002.txt");
    	urlList.add("http://localhost:8080/add?id=3&name=males&filename=batch003.txt");
   		urlList.add("http://localhost:8080/add?id=4&name=females&filename=batch004.txt");
    	urlList.add("http://localhost:8080/add?id=5&name=females&filename=batch005.txt");
    	urlList.add("http://localhost:8080/add?id=6&name=females&filename=batch006.txt");

    	URL url;
    	URLConnection conn;
    	BufferedReader br;
    	String inputLine;
    	try {
    		for (String urlString : urlList) {
                url = new URL(urlString);
                conn = url.openConnection();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((inputLine = br.readLine()) != null) {
                    System.out.println(inputLine);
                }

                br.close();
			}
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "test-success";
    }
}
