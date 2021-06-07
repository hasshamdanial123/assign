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
	  @Autowired
         private MockMvc mvc;

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
	@Autowired
        private MockMvc mvc;
 
        @Test
        public void sendMessageTest() throws Exception 
            {
             mvc.perform( MockMvcRequestBuilders
             .get("/v1/api/sqs")
             .accept(MediaType.APPLICATION_JSON))
             .andDo(print())
             .andExpect(status().isOk())
            }
	@Test
        public void PostMessageTest() throws Exception 
          {
           mvc.perform( MockMvcRequestBuilders
             .post("/v1/api/sqs")
             .content(asJsonString("Testing POST request"))
             .contentType(MediaType.APPLICATION_JSON)
             .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isCreated())
      }
	
}
