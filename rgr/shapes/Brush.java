package shapes;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class Brush extends shapes.Shape {

  public Brush(final List<Double> coords) {
    super(coords);
    useDashes = false;
  }

  public Brush(final double x, final double y) {
    this(List.of(x, y));
  }

  @Override
  public Shape update(double x, double y) {
    coords.add(x);
    coords.add(y);
    return this;
  }

  @Override
  public void draw(GraphicsContext context) {
    final var size = coords.size();
    if (size == 2) {
      final var width = context.getLineWidth();
      final var x = coords.get(0);
      final var y = coords.get(1);
      context.fillOval(x - width, y - width, width * 2, width * 2);
    }
    for (int index = 4; index < size; index += 2) {
      final var x1 = coords.get(index - 2);
      final var y1 = coords.get(index - 1);
      final var x2 = coords.get(index);
      final var y2 = coords.get(index + 1);
      context.strokeLine(x1, y1, x2, y2);
    }
  }

}
