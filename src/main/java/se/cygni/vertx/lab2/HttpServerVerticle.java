package se.cygni.vertx.lab2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;

public class HttpServerVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    HttpServerOptions options = new HttpServerOptions()
        .setHost("localhost")
        .setPort(8080);

    HttpServer server = vertx.createHttpServer(options);

    server.requestHandler(request -> {

    }).listen(result -> {
      if (result.succeeded()) {
        System.out.println("Server started");
        startFuture.complete();
      } else {
        System.err.println("Server failed to start");
        startFuture.fail(result.cause());
      }
    });

  }
}
