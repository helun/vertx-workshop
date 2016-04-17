package se.cygni.vertx.lab3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class ApiVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    HttpServerOptions options = new HttpServerOptions()
        .setHost("localhost")
        .setPort(8080);

    HttpServer server = vertx.createHttpServer(options);

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.post("/login").handler(this::handleLoginRequest);

    server.requestHandler(router::accept).listen(result -> {
      if (result.succeeded()) {
        System.out.println("Server started");
        startFuture.complete();
      } else {
        System.err.println("Server failed to start");
        startFuture.fail(result.cause());
      }
    });
  }

  private void handleLoginRequest(RoutingContext ctx) {
    JsonObject loginRequest = ctx.getBodyAsJson();
    vertx.eventBus().send("", "");
  }

}
