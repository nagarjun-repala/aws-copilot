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
import org.apache.logging.log4j.ThreadContext;
import datadog.trace.api.CorrelationIdentifier;
import org.json.simple.JSONObject;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) throws IOException, InterruptedException{
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
public String home() throws IOException, InterruptedException{
	JSONObject obj = new JSONObject();
	
	obj.put("dd.trace_id", CorrelationIdentifier.getTraceId());
	obj.put("dd.span_id", CorrelationIdentifier.getSpanId());
	obj.put("data", "Calling external application...");
	System.out.println(obj);
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
	System.out.println("Calling internal application from external...");				
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





// There must be spans started and active before this block.
