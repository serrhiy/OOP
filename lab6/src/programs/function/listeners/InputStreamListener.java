package listeners;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.json.JSONObject;

import java.util.function.Consumer;

public class InputStreamListener {
  private static InputStreamListener instance = null;
  private static final Map<String, List<Consumer<JSONObject>>> listeners = new HashMap<>();
  
  public static InputStreamListener getInstance() {
    if (instance != null) return instance; 
    instance = new InputStreamListener();
    CompletableFuture.runAsync(() -> {
      final var isReader = new InputStreamReader(System.in);
      try (final var reader = new BufferedReader(isReader)) {
        while (true) {
          final var line = reader.readLine();
          if (line == null) continue;
          final var json = new JSONObject(line);
          final var service = json.getString("service");
          final var data = json.has("data") ? json.getJSONObject("data") : new JSONObject();
          instance.emit(service, data);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    return instance;
  }

  public InputStreamListener on(final String eventName, final Consumer<JSONObject> listener) {
    final var exists = listeners.containsKey(eventName);
    if (exists) listeners.get(eventName).add(listener);
    else listeners.put(eventName, List.of(listener));
    return this;
  }

  private InputStreamListener emit(final String eventName, final JSONObject json) {
    final var exists = listeners.containsKey(eventName);
    if (!exists) return this;
    for (final var listener: listeners.get(eventName)) {
      listener.accept(json);
    }
    return this;
  }
}