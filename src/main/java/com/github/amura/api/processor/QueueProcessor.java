package com.github.amura.api.processor;

import co.paralleluniverse.actors.ActorRef;
import com.github.amura.api.actor.MainActor;
import com.github.amura.api.actor.MockProcessActor;
import com.github.amura.api.message.MockRequestMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by amura on 5/16/17.
 */
@Component
public class QueueProcessor implements Processor {

  private static final Logger LOGGER = LoggerFactory.getLogger(QueueProcessor.class);

//  private ActorRef mainActor;
//
//  public QueueProcessor() {
//    this.mainActor = new MainActor().spawn();
//  }

  public void process(Exchange exchange) throws Exception {
    final String body = exchange.getIn().getBody(String.class);
    LOGGER.info("BODY: " + body);

    final MockRequestMessage msg = new MockRequestMessage(body);
    final ActorRef<MockRequestMessage> mockActor = new MockProcessActor().spawn();
    mockActor.send(msg);
  }
}
