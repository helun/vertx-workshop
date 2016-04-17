package se.cygni.vertx.lab1;

import io.vertx.core.AbstractVerticle;

public class GreeterVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.eventBus().<String>consumer("echo.greet", message -> {

    });
  }
}
