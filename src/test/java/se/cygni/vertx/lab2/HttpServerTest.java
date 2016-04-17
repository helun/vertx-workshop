package se.cygni.vertx.lab2;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

@RunWith (VertxUnitRunner.class)
public class HttpServerTest {

  public static final String HOST = "localhost";
  Vertx vertx = Vertx.vertx();
  HttpClient client = vertx.createHttpClient(new HttpClientOptions()
                                                 .setDefaultHost(HOST)
                                                 .setDefaultPort(PORT));
  public static final int PORT = 8080;

  @Before
  public void setUp(TestContext ctx) throws Exception {
    vertx.deployVerticle("java:se.cygni.vertx.lab2.HttpServerVerticle", ctx.asyncAssertSuccess());
  }

  @Test (timeout = 1000)
  public void testBasicGet(TestContext ctx) throws Exception {
    Async async = ctx.async();
    client.getNow("/hello", response -> {
      ctx.assertEquals(200, response.statusCode(), "request should respond with http status 200 OK");
      response.bodyHandler(body -> {
        JsonObject responseJson = body.toJsonObject();
        ctx.assertEquals("Hello World", responseJson.getString("greet"));
        async.complete();
      });
    });
    async.await();
  }

  @Test (timeout = 1000)
  public void post(TestContext ctx) throws Exception {
    Async async = ctx.async();
    String greet = UUID.randomUUID().toString();
    String postBody = new JsonObject()
        .put("greet", greet)
        .encode();

    client.post("/hello", response -> {
      ctx.assertEquals(200, response.statusCode(), "request should respond with http status 200 OK");
      response.bodyHandler(body -> {
        JsonObject responseJson = body.toJsonObject();
        ctx.assertEquals(greet, responseJson.getString("greet"));
        async.complete();
      });
    }).putHeader("content-type", "application/json")
      .putHeader("content-length", Integer.toString(postBody.length()))
      .end(postBody);

    async.await();
  }
}
