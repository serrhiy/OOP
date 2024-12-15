package shapes;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends shapes.Shape {

  public Rectangle(final List<Double> coords) {
    super(coords);
  }

  public Rectangle(final double x, final double y) {
    super(List.of(x, y, 0.0, 0.0));
  }

  @Override
  public Shape update(final double x, final double y) {
    coords.set(2, x);
    coords.set(3, y);
    return this;
  }

  @Override
  public void draw(GraphicsContext context) {
    final var x1 = coords.get(0);
    final var y1 = coords.get(1);
    final var x2 = coords.get(2);
    final var y2 = coords.get(3);
    final var dx = Math.abs(x2 - x1);
    final var dy = Math.abs(y2 - y1);
    final var x = (x1 + x2 - dx) / 2;
    final var y = (y1 + y2 - dy) / 2;
    context.strokeRect(x, y, dx, dy);
  }
}
