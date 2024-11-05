package shapes;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;
import java.util.ArrayList;

public class RectangleCorner extends Shape implements Rectangable {
  public RectangleCorner() {
    super();
    coords = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
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
}
