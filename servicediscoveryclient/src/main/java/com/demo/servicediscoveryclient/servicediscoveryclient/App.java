package com.demo.servicediscoveryclient.servicediscoveryclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class App {
	
	@Autowired
	private EurekaClient client;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@RequestMapping("/")
	public String callService() {
		RestTemplate restTemplate = restTemplateBuilder.build();
		InstanceInfo instanceInfo = client.getNextServerFromEureka("service", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		ResponseEntity<String> response =
				restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
		return response.getBody();
	}

	@RequestMapping("/service")
	public List<String> execute(){
		return Arrays.asList("Value1", "Value2");
	}
	

}

