package com.github.amura.api.processor;

import co.paralleluniverse.actors.ActorRef;
import co.paralleluniverse.actors.ActorSpec;
import co.paralleluniverse.actors.MailboxConfig;
import co.paralleluniverse.actors.behaviors.Supervisor;
import co.paralleluniverse.actors.behaviors.Supervisor.ChildSpec;
import static co.paralleluniverse.actors.behaviors.Supervisor.ChildMode.*;
import co.paralleluniverse.actors.behaviors.SupervisorActor;
import static co.paralleluniverse.actors.behaviors.SupervisorActor.RestartStrategy.*;
import co.paralleluniverse.fibers.FiberFactory;
import co.paralleluniverse.fibers.FiberForkJoinScheduler;
import co.paralleluniverse.fibers.FiberScheduler;
import co.paralleluniverse.strands.channels.Channels;
import com.github.amura.api.actor.MockProcessActor;
import com.github.amura.api.message.MockRequestMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by amura on 5/16/17.
 */
@Component
public class QueueProcessor implements Processor {

  private static final Logger LOGGER = LoggerFactory.getLogger(QueueProcessor.class);

  private FiberScheduler scheduler;
  private FiberFactory factory;

  private ChildSpec childSpec;
  private Supervisor supervisor;

  public QueueProcessor() {
    this.factory = this.scheduler = new FiberForkJoinScheduler("test", 4, null, false);
    this.childSpec = new ChildSpec(null, TRANSIENT, 5, 1, TimeUnit.SECONDS, 100, ActorSpec.of(MockProcessActor.class));
    this.supervisor = new SupervisorActor(
      "Supervisor",
      new MailboxConfig(5, Channels.OverflowPolicy.BLOCK),
      ONE_FOR_ONE
    ).spawn(factory);
  }

  public void process(Exchange exchange) throws Exception {
    final String body = exchange.getIn().getBody(String.class);
    LOGGER.info("BODY: " + body);

    final ActorRef<Object> worker = supervisor.addChild(childSpec);
    final MockRequestMessage msg = new MockRequestMessage(body);
    worker.send(msg);
  }
}
