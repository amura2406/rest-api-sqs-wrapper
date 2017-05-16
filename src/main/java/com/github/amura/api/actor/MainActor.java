package com.github.amura.api.actor;

import co.paralleluniverse.actors.ActorRef;
import co.paralleluniverse.actors.BasicActor;
import co.paralleluniverse.actors.behaviors.RequestReplyHelper;
import co.paralleluniverse.fibers.SuspendExecution;
import com.github.amura.api.message.MockRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amura on 5/16/17.
 */
public class MainActor extends BasicActor<String, Void> {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainActor.class);

  public MainActor() {
    super("MainActor");
  }

  protected Void doRun() throws InterruptedException, SuspendExecution {
    for(;;){
      final String body = receive();

      final MockRequestMessage msg = new MockRequestMessage(body);
      final ActorRef<MockRequestMessage> procActor = new MockProcessActor().spawn();
      final String resp = RequestReplyHelper.call(procActor, msg);

      LOGGER.info(String.format("Actor %s recv: %s", procActor.getName(), resp));
    }
  }
}
