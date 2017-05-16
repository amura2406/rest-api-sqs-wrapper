package com.github.amura.api.configuration;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by amura on 5/15/17.
 */
@Configuration
public class SQSConfiguration{

  @Bean("mySQSClient")
  public AmazonSQSAsync getAmazonSQSAsyncClient(){
    AwsClientBuilder.EndpointConfiguration ec = new AwsClientBuilder.EndpointConfiguration("http://localhost:9324", "elasticmq");
    AWSCredentialsProvider credPrv = new ClasspathPropertiesFileCredentialsProvider("aws-sqs.properties");

    return AmazonSQSAsyncClientBuilder
            .standard()
            .withCredentials(credPrv)
            .withEndpointConfiguration(ec)
            .build();
  }
}
