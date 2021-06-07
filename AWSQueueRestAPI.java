package com.techtlak.sqs.api;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/sqs")
public class AWSQueueRestAPI {

	private static final Logger LOG = LoggerFactory.getLogger(AWSQueueRestAPI.class);

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String sqsEndPoint;

	@GetMapping
	public void sendMessage() {
		queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload("Hello from AWS SQS Service").build());
	}
	
	@PostMapping
	public void postMessage(@RequestBody String msg) {
		queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload(msg).build());
	}
	
	@SqsListener("demoqueu")
	public void getMessage(String message) {
		LOG.info("Message from Received from AWS SQS Queue - " + message);
	}
	@Test
	public void sendMessageTest() throws Exception {
	   String uri = "v1/api/sqs";
	   
	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	      .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	}
	@Test
	public void PostMessageTest() throws Exception {
	   String uri = "v1/api/sqs";
	   String message = "Testing POST service";
	   String inputJson = super.mapToJson(message);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(201, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(content, "Testing is done Successfully");
	}
}
