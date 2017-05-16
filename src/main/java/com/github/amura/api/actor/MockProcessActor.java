package com.github.amura.api.actor;

import co.paralleluniverse.actors.BasicActor;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import com.github.amura.api.message.MockRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amura on 5/16/17.
 */
public class MockProcessActor extends BasicActor<MockRequestMessage, Void> {
  private static final Logger LOGGER = LoggerFactory.getLogger(MockProcessActor.class);
  private static final AtomicInteger counter = new AtomicInteger();

  public MockProcessActor() {
    super("MockProcess");
  }

  protected Void doRun() throws InterruptedException, SuspendExecution {
    final MockRequestMessage req = receive();
    final int id = counter.incrementAndGet();
    int rand = new Random().nextInt(8);
    Strand.sleep(1000 + (rand * 1000));
    LOGGER.info(String.format("Actor %s recv: %d", getName(), id));

    return null;
  }
}
