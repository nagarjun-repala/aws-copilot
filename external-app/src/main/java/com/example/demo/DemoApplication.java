package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) throws IOException, InterruptedException{
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
public String home() throws IOException, InterruptedException{

	return "Hello World from external application!";
  }
	@RequestMapping("/internal")
public String internalApp() throws IOException, InterruptedException{
	try{
		String serviceEndpoint = System.getenv("COPILOT_SERVICE_DISCOVERY_ENDPOINT");
		String serviceName = System.getenv("SERVICE_NAME_TO_CONNECT");
		String internalAppEndpoint = "http://" + serviceName + "." + serviceEndpoint + ":8080";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(internalAppEndpoint))
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		return response.body();					

	}catch(InterruptedException e){
     // this part is executed when an exception (in this example InterruptedException) occurs
	 System.out.println("Exception");

	}
	return "Exception";	
  }
	@RequestMapping("/secret")
public String secret() throws IOException, InterruptedException{
	String secret = System.getenv("dd-api-key");

	return "Secret";
  }    
    
}
