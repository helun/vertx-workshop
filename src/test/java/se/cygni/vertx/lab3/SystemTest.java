package se.cygni.vertx.lab3;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class SystemTest {

  /**
   * Implement a user login service with an REST API:
   *
   * You should use two services that are already implemented:
   * AuthenticationVerticle
   * SettingsVerticle
   *
   * Remember to deploy the verticles!
   *
   * AUTHENTICATION:
   *
   * Eventbus address: "user.auth"
   * Request format (JsonObject):
   * {
   *   "username":"the username",
   *   "password":"the password"
   * }
   *
   * Successful response:
   * {
   *   "id": "the user id",
   *   "username": "the username"
   * }
   *
   * Failed authentication:
   * The message will fail with code 404.
   *
   * USER SETTINGS:
   * Eventbus address: "user.settings"
   *
   * Request format (String):
   * user id
   *
   * Successful response:
   * A json object with all settings
   *
   * API SPECIFICATION:
   *
   * The application should expose a login endpoint on port 8080.
   *
   * POST /login
   * Body: see loginRequest below
   *
   * Expected response on successful login:
   * 200 OK
   * content-type: application/json
   *
   * Body: see expectedLoginResponse below
   *
   * Expected response on failed login:
   * 404 Not found
   *
   * The response should be delayed for one second if the login fails.
   *
   * Bonus Exercise 1:
   * Increase the response delay if consecutive login requests fails for the same user.
   *
   * Users should be authenticated against the AuthenticationVerticle.
   * If auth succeeds then the users settings should be fetched from SettingsVerticle.
   *
   * Bonus Exercise 2:
   * Reimplement your system in RX-Java style.
   * Start by extending io.vertx.rxjava.core.AbstractVerticle and use Observables instead of callbacks.
   *
   */


  final static String AUTH_VERTICLE_NAME = "se.cygni.vertx.support.AuthenticationVerticle";
  final static String USER_SETTINGS_VERTICLE_NAME = "se.cygni.vertx.support.SettingsVerticle";

  JsonObject loginRequest = new JsonObject()
                            .put("username", "player_one")
                            .put("password", "pwd_one");

  JsonObject expectedLoginResponse = new JsonObject()
                            .put("token", "a-dummy-token")
                            .put("settings", new JsonObject()
                                 .put("setting1", "a string value")
                                 .put("setting2", Integer.MAX_VALUE)
                            );

  @Before
  public void setUp(TestContext ctx) throws Exception {
    // deploy verticles here
  }

  @Test
  public void should_return_login_reponse_on_successful_login(TestContext ctx) throws Exception {

  }

  @Test
  public void should_return_404_reponse_on_failed_login(TestContext ctx) throws Exception {

  }
}
