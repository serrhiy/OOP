package shapes;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class Line extends shapes.Shape {

  public Line(final List<Double> coords) {
    super(coords);
  }

  public Line(final double x, final double y) {
    super(List.of(x, y, 0.0, 0.0));
  }

  @Override
  public Shape update(final double x, final double y) {
    coords.set(2, x);
    coords.set(3, y);
    return this;
  }

  @Override
  public void draw(final GraphicsContext context) {
    if (coords.size() < 4) return;
    context.strokeLine(coords.get(0), coords.get(1), coords.get(2), coords.get(3));
  }

  static public boolean lineContaines(double x1, double y1, double x2, double y2, double x, double y, double width) {
    final var dx = x2 - x1;
    final var dy = y2 - y1;
    final var numerator = dx * (y1 - y) - (x1 - x) * dy;
    final var denumerator = Math.sqrt(dx * dx + dy * dy);
    final var distance = Math.abs(numerator) / denumerator;
    final var inRange = (x - x1) * (x - x2) <= width;
    return distance < width * 2 && inRange;
  }

  @Override
  public boolean contains(final double x, final double y) {
    final var x1 = coords.get(0);
    final var y1 = coords.get(1);
    final var x2 = coords.get(coords.size() - 2);
    final var y2 = coords.get(coords.size() - 1);
    return lineContaines(x1, y1, x2, y2, x, y, config.getWidth());
  }
}
