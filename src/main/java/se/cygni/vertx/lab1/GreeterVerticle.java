package se.cygni.vertx.lab1;

import io.vertx.core.AbstractVerticle;

public class GreeterVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.eventBus().<String>consumer("greet.echo", message -> {
      message.reply("Hello " + message.body());
    });

    vertx.eventBus().<String>consumer("greet.shared-data", message -> {
      String greetformat = greetFormat();
      message.reply(String.format(greetformat, message.body()));
    });
  }

  private String greetFormat() {
    return vertx.sharedData().<String, String >getLocalMap("shared-data").get("greetformat");
  }
}
