package se.cygni.vertx.support;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsVerticle extends AbstractVerticle {

  private final static Map<String, JsonObject> settings = new HashMap();
  static {
    settings.put("player_one_id", new JsonObject()
        .put("setting1", "a string value")
        .put("setting2", Integer.MAX_VALUE)

    );
    settings.put("player_two_id", new JsonObject()
        .put("setting1", "another string value")
        .put("setting2", Integer.MIN_VALUE)
    );
  }

  @Override
  public void start() throws Exception {
    vertx.eventBus().<String>consumer("user.settings", request -> {
      String userId = request.body();
      request.reply(settings.getOrDefault(userId, new JsonObject()));
    });
  }
}
