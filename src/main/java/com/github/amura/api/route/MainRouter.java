package com.github.amura.api.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by amura on 5/15/17.
 */
@Component
public class MainRouter extends RouteBuilder{

  public void configure() throws Exception {
    restConfiguration().port(8080);

    rest("q")
      .post()
      .to("direct:queue-in");

    from("direct:queue-in")
      .to("aws-sqs://queue-async-test?amazonSQSClient=#mySQSClient");

    from("aws-sqs://queue-async-test?amazonSQSClient=#mySQSClient")
      .to("queueProcessor");
  }
}