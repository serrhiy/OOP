package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventEmitter<T> {
  private final Map<String, List<Consumer<T>>> listeners = new HashMap<>();

  public EventEmitter<T> on(final String name, final Consumer<T> listener) {
    final var exists = listeners.containsKey(name);
    if (exists) listeners.get(name).add(listener);
    else listeners.put(name, new ArrayList<>(List.of(listener)));
    return this;
  }

  public EventEmitter<T> emit(final String name, final T data) {
    if (!listeners.containsKey(name)) return this;
    for (final var listener: listeners.get(name)) {
      listener.accept(data);
    }
    return this;
  }

  public EventEmitter<T> off(final String name) {
    if (listeners.containsKey(name)) listeners.remove(name);
    return this;
  }

  public EventEmitter<T> off(final String name, final Consumer<T> listener) {
    if (listeners.containsKey(name)) {
      final var list = listeners.get(name);
      if (list.contains(listener)) list.remove(listener);
    }
    return this;
  }
}
