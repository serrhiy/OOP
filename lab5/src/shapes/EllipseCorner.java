package shapes;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

public class EllipseCorner extends Shape implements Ellipsable {

  public EllipseCorner() {
    super();
  }

  public EllipseCorner(final List<Double> coords) {
    super(coords);
  }
  
  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    Ellipsable.super.drawEllipse(
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
    final double dx = Math.abs(x2 - x1);
    final double dy = Math.abs(y2 - y1);
    coords.set(0, (x1 + x2 - dx) / 2);
    coords.set(1, (y1 + y2 - dy) / 2);
    coords.set(2, dx);
    coords.set(3, dy);
  }

  @Override
  public Pair<Pair<Double, Double>, Pair<Double, Double>> getDisplayCoords() {
    final var first = new Pair<>(coords.get(0), coords.get(1));
    final var second = new Pair<>(coords.get(2), coords.get(3));
    return new Pair<>(first, second);
  }

  @Override
  public String getName() {
    return "Ellipse";
  }
}