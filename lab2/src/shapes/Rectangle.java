package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {
  @Override
  public void draw(GraphicsContext context, boolean fill) {
    final var dx = Math.abs(x2 - x1);
    final var dy = Math.abs(y2 - y1);
    final var x = Math.min(x1, x2);
    final var y = Math.min(y1, y2);
    if (fill) context.fillRect(x, y, dx, dy);
    else context.strokeRect(x, y, dx, dy);
  }
}
