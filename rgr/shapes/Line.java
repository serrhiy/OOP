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
  public Shape update(double x, double y) {
    coords.set(2, x);
    coords.set(3, y);
    return this;
  }

  @Override
  public void draw(final GraphicsContext context) {
    context.strokeLine(coords.get(0), coords.get(1), coords.get(2), coords.get(3));
  }
}
