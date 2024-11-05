package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

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
    context.fillOval(x - width, y - width, width * 2, width * 2);
  }

  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {
    coords.set(0, x2);
    coords.set(1, y2);
  }
}