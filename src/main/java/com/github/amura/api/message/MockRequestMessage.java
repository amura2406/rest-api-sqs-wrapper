package com.github.amura.api.message;

import co.paralleluniverse.actors.behaviors.RequestMessage;

/**
 * Created by amura on 5/16/17.
 */
public class MockRequestMessage extends RequestMessage<String>{

  private String body;

  public MockRequestMessage(String body) {
    this.body = body;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
