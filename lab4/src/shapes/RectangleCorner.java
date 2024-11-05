package shapes;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;
import java.util.ArrayList;

public class RectangleCorner extends Shape {
  public RectangleCorner() {
    super();
    coords = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
  }

  @Override
  public void draw(GraphicsContext context) {
    final var x = coords.get(0);
    final var y = coords.get(1);
    final var dx = coords.get(2);
    final var dy = coords.get(3);
    final var lineWidth = context.getLineWidth();
    if (fill) context.fillRect(x, y, dx + lineWidth, dy + lineWidth);
    else context.strokeRect(x, y, dx + lineWidth, dy + lineWidth);
  }

  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {
    coords.set(0, Math.min(x1, x2));
    coords.set(1, Math.min(y1, y2));
    coords.set(2, Math.abs(x2 - x1));
    coords.set(3, Math.abs(y2 - y1));
  }
}