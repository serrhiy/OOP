package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class EllipseCorner extends Shape {

  public EllipseCorner() {
    super();
    coords = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
  }
  
  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    final double x = coords.get(0);
    final double y = coords.get(1);
    final double dx = coords.get(2);
    final double dy = coords.get(3);
    final var lineWidth = context.getLineWidth();
    if (fill) context.fillOval(x, y, dx + lineWidth, dy + lineWidth);
    else context.strokeOval(x, y, dx + lineWidth, dy + lineWidth);
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
}