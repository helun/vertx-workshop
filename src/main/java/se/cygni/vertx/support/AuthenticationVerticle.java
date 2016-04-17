package se.cygni.vertx.support;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationVerticle extends AbstractVerticle {

  private final static Map<String, JsonObject> users = new HashMap();
  static {
    users.put("player_one", new JsonObject()
        .put("id", "player_one_id")
        .put("username", "player_one")
        .put("password", "pwd_one")
    );
    users.put("player_two", new JsonObject()
        .put("id", "player_two_id")
        .put("username", "player_two")
        .put("password", "pwd_two")
    );
  }

  @Override
  public void start() throws Exception {
    vertx.eventBus().<JsonObject>consumer("user.auth", request -> {
      JsonObject authRequest = request.body();
      String userName = authRequest.getString("username");

      vertx.setTimer(500, l -> {
        String password = authRequest.getString("password");
        JsonObject user = users.get(userName);
        if (user != null) {
          String existingPwd = user.getString("password");
          if (existingPwd.equals(password)) {
            JsonObject responseJson = user.copy();
            responseJson.remove("password");
            request.reply(responseJson);
            return;
          }
        }
        request.fail(404, "Auth failed");
      });

    });
  }
}
