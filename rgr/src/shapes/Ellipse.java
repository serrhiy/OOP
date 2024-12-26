package shapes;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends shapes.Shape {

  public Ellipse(final List<Double> coords) {
    super(coords);
  }

  public Ellipse(final double x, final double y) {
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
    final var width = context.getLineWidth();
    final var x1 = coords.get(0);
    final var y1 = coords.get(1);
    final var x2 = coords.get(2);
    final var y2 = coords.get(3);
    final var dx = Math.abs(x2 - x1);
    final var dy = Math.abs(y2 - y1);
    final var x = (x1 + x2 - dx) / 2;
    final var y = (y1 + y2 - dy) / 2;
    if (config.getFill()) context.fillOval(x, y, dx + width, dy + width);
    else context.strokeOval(x, y, dx + width, dy + width);
  }

  @Override
  public boolean contains(final double x, final double y) {
    final var x1 = coords.get(0);
    final var y1 = coords.get(1);
    final var x2 = coords.get(2);
    final var y2 = coords.get(3);
    final var center_x = (x1 + x2) / 2;
    final var center_y = (y1 + y2) / 2;
    final var a = x2 - center_x;
    final var b = y2 - center_y;
    final var first = ((x - center_x) * (x - center_x)) / (a * a);
    final var second = ((y - center_y) * (y - center_y)) / (b * b);
    final var sum = first + second;
    if (config.getFill()) return sum <= 1;
    final var delta = config.getWidth() / 100.0 * 2;
    return Math.abs(1 - sum) < delta;
  }
}
