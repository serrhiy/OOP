package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class EllipseCorner extends Shape implements Ellipsable {

  public EllipseCorner() {
    super();
    coords = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
  }
  
  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    Ellipsable.super.drawLine(
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
}