package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

public class Brush extends Shape {

  public Brush(final List<Double> coords) {
    super(coords);
    useDashes = false;
  }

  public Brush() {
    this(new ArrayList<>());
  }

  @Override
  public void onStart(GraphicsContext context, double x, double y) {
    this.setCoords(0, 0, x, y);
  }

  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    final var size = coords.size();
    if (size <= 2) return;
    var prevX = coords.get(0);
    var prevY = coords.get(1);
    for (int i = 2; i < size; i += 2) {
      final var x = coords.get(i);
      final var y = coords.get(i + 1);
      context.strokeLine(prevX, prevY, x, y);
      prevX = x;
      prevY = y;
    }
  }

  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {
    coords.add(x2);
    coords.add(y2);
  }

  @Override
  public Pair<Pair<Double, Double>, Pair<Double, Double>> getDisplayCoords() {
    final var x1 = coords.get(0);
    final var y1 = coords.get(1);
    final var x2 = coords.get(coords.size() - 2);
    final var y2 = coords.get(coords.size() - 1);
    final var first = new Pair<>(x1, y1);
    final var second = new Pair<>(x2, y2);
    return new Pair<>(first, second);
  }

  @Override
  public String getName() {
    return "Brush";
  }
}