package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

public class Line extends Shape implements Linable {

  public Line() {
    super();
    coords = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
  }

  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    Linable.super.drawLine(
      context, 
      coords.get(0),
      coords.get(1),
      coords.get(2),
      coords.get(3)
    );
  }

  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {
    coords.set(0, x1);
    coords.set(1, y1);
    coords.set(2, x2);
    coords.set(3, y2);
  }

  @Override
  public Pair<Pair<Double, Double>, Pair<Double, Double>> getDisplayCoords() {
    final var first = new Pair<>(coords.get(0), coords.get(1));
    final var second = new Pair<>(coords.get(2), coords.get(3));
    return new Pair<>(first, second);
  }

  @Override
  public String getName() {
    return "Line";
  }
}