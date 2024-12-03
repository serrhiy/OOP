package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;
import java.util.List;

public class RectangleCorner extends Shape implements Rectangable {
  public RectangleCorner() {
    super();
  }

  public RectangleCorner(final List<Double> coords) {
    super(coords);
  }

  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    Rectangable.super.drawRectangle(
      context,
      coords.get(0),
      coords.get(1),
      coords.get(2),
      coords.get(3),
      fill
    );
  }

  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {

    coords.set(0, Math.min(x1, x2));
    coords.set(1, Math.min(y1, y2));
    coords.set(2, Math.abs(x2 - x1));
    coords.set(3, Math.abs(y2 - y1));
  }

  @Override
  public Pair<Pair<Double, Double>, Pair<Double, Double>> getDisplayCoords() {
    final var first = new Pair<>(coords.get(0), coords.get(1));
    final var second = new Pair<>(coords.get(2), coords.get(3));
    return new Pair<>(first, second);
  }

  @Override
  public String getName() {
    return "Rectangle";
  }
}
