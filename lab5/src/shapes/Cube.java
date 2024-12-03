package shapes;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

public class Cube extends Shape implements Linable, Rectangable {

  private static final int deltaX = 50;
  private static final int deltaY = -40;

  public Cube() {
    super();
  }

  public Cube(final List<Double> coords) {
    super(coords);
  }

  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    fill = false;
    final var x1 = coords.get(0);
    final var y1 = coords.get(1);
    final var dx = coords.get(2);
    final var dy = coords.get(3);
    Rectangable.super.drawRectangle(context, x1, y1, dx, dy, fill);
    Rectangable.super.drawRectangle(context, x1 + deltaX, y1 + deltaY, dx, dy, fill);
    Linable.super.drawLine(context, x1, y1, x1 + deltaX, y1 + deltaY);
    Linable.super.drawLine(context, x1 + dx, y1, x1 + dx + deltaX, y1 + deltaY);
    Linable.super.drawLine(context, x1, y1 + dy, x1 + deltaX, y1 + dy + deltaY);
    Linable.super.drawLine(context, x1 + dx, y1 + dy, x1 + dx + deltaX, y1 + dy + deltaY);
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
    return "Cube";
  }
}
