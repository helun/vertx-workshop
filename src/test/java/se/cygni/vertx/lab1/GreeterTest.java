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

  Vertx vertx = Vertx.vertx();

  @Before
  public void setUp(TestContext ctx) throws Exception {
    vertx.deployVerticle("java:se.cygni.vertx.lab1.GreeterVerticle", ctx.asyncAssertSuccess());
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
    // Specifies that two countdowns should happen before the async is complete.
    Async async = ctx.async(2);

    vertx.sharedData().getLocalMap("shared-data").put("greetformat", "Hola %s!");

    vertx.eventBus().<String>send("greet.shared-data", "Mundo", response -> {
      ctx.assertTrue(response.succeeded(), "Got no response");
      ctx.assertEquals("Hola Mundo!", response.result().body());
      async.countDown();
    });

    vertx.sharedData().getLocalMap("shared-data").put("greetformat", "Bonjour %s!");

    vertx.eventBus().<String>send("greet.shared-data", "le monde", response -> {
      ctx.assertTrue(response.succeeded(), "Got no response");
      ctx.assertEquals("Bonjour le monde!", response.result().body());
      async.countDown();
    });

    async.await();
  }
}
