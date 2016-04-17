package se.cygni.vertx.lab1;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class GreeterTest {

  /**
   * The class GreeterVerticle is already defined.
   * Make the tests pass by implement missing functionality in se.cygni.vertx.lab1.GreeterVerticle
   */

  Vertx vertx = Vertx.vertx();

  @Before
  public void setUp(TestContext ctx) throws Exception {
    vertx.deployVerticle("se.cygni.vertx.lab1.GreeterVerticle", ctx.asyncAssertSuccess());
  }

  /**
   * Test that the GreeterVerticle responds with "Hello " + message.body()
   */
  @Test
  public void echoGreet(TestContext ctx) throws Exception {
    // Create an Async so that the test will wait until all assertions are done
    Async async = ctx.async();
    vertx.eventBus().<String>send("greet.echo", "World", response -> {
      ctx.assertTrue(response.succeeded(), "Got no response");
      ctx.assertEquals("Hello World", response.result().body());
      async.complete();
    });
    // stop the method from existing before test is complete
    async.await();
  }

  /**
   *
   * This test uses a shared local map to set a greet format.
   * The GreeterVerticle should listen to a new address: "greet.shared-data"
   * Lookup greet format in the shared local map named: "shared-data"
   * and use message.body() as input to the String format method.
   *
   */
  @Test
  public void sharedDataGreet(TestContext ctx) throws Exception {

    vertx.sharedData().getLocalMap("shared-data").put("greetformat", "Hola %s!");

    sendAndAssertReply("Mundo", "Hola Mundo!", ctx);

    vertx.sharedData().getLocalMap("shared-data").put("greetformat", "Bonjour %s!");

    sendAndAssertReply("le monde", "Bonjour le monde!", ctx);
  }

  private void sendAndAssertReply(String message, String expectedReply, TestContext ctx) {
    Async async = ctx.async();
    vertx.eventBus().<String>send("greet.shared-data", message, response -> {
      ctx.assertTrue(response.succeeded(), "Got no response");
      ctx.assertEquals(expectedReply, response.result().body());
      async.complete();
    });
    async.await();
  }
}
