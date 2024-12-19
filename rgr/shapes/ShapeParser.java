package shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class ShapeParser {

  private static final Map<String, Class<? extends Shape>> shapes = Map.of(
    Brush.class.getSimpleName(), Brush.class,
    Ellipse.class.getSimpleName(), Ellipse.class,
    Line.class.getSimpleName(), Line.class,
    Rectangle.class.getSimpleName(), Rectangle.class,
    DirectedLine.class.getSimpleName(), DirectedLine.class,
    BiDirectedLine.class.getSimpleName(), BiDirectedLine.class
  );

  public static Pair<Pair<ShapeConfig, String>, List<Double>> parse(final JSONObject shape) {
    final var name = shape.getString("name");
    final var width = shape.getInt("width");
    final var color = shape.getString("color");
    final var fill = shape.getBoolean("fill");
    final var numbers = shape.getJSONArray("coords");
    final var coords = new ArrayList<Double>();
    for (final var number: numbers) {
      final var coord = Double.parseDouble(number.toString());
      coords.add(coord);
    }
    final var config = new ShapeConfig(Color.valueOf(color), width, fill);
    return new Pair<>(new Pair<>(config, name), coords);
  }

  public static List<Shape> parse(final JSONArray shapes) {
    final var result = new ArrayList<Shape>();
    for (final var item: shapes) {
      final var shape = new JSONObject(item.toString());
      final var parsed = parse(shape);
      final var info = parsed.getKey();
      final var config = info.getKey();
      final var name = info.getValue();
      final var coords = parsed.getValue();
      try {
        final var constructor = ShapeParser.shapes.get(name);
        final var declared = constructor.getDeclaredConstructor(List.class);
        final var newshape = declared.newInstance(coords);
        newshape.apply(config);
        result.add(newshape);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static JSONObject serialise(final Shape shape) {
    final var config = shape.config;
    final var item = new JSONObject();
    final var name = shape.getClass().getSimpleName();
    item.put("name", name);
    item.put("width", config.getWidth());
    item.put("color", config.getColor().toString());
    item.put("fill", config.getFill());
    item.put("coords", shape.getCoords());
    return item;
  }

  public static JSONArray serialise(final List<Shape> shapes) {
    final var result = new JSONArray();
    for (final var shape: shapes) {
      final var serialised = serialise(shape);
      result.put(serialised);
    }
    return result;
  }
}
