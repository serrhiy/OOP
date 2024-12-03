package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

public class Point extends Shape {

  public Point() {
    super();
    coords = new ArrayList<>(List.of(0.0, 0.0));
  }

  @Override
  public void onStart(GraphicsContext context, double x, double y) {
    this.setCoords(0, 0, x, y);
    this.draw(context);
  }

  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    final var x = coords.get(0);
    final var y = coords.get(1);
    final var width = context.getLineWidth();
    context.fillOval(x - width, y - width, width * 2, width * 2);
  }

  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {
    coords.set(0, x2);
    coords.set(1, y2);
  }

  @Override
  public Pair<Pair<Double, Double>, Pair<Double, Double>> getDisplayCoords() {
    final var x = coords.get(0);
    final var y = coords.get(1);
    final var point = new Pair<>(x, y);
    return new Pair<>(point, point);
  }

  @Override
  public String getName() {
    return "Point";
  }
}