package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Elipse extends Shape {
  @Override
  public void draw(GraphicsContext context, boolean fill) {
    final double dx = Math.abs(x2 - x1);
    final double dy = Math.abs(y2 - y1);
    final double x = (x1 + x2 - dx) / 2;
    final double y = (y1 + y2 - dy) / 2;
    final var lineWidth = context.getLineWidth();
    if (fill) context.fillOval(x, y, dx + lineWidth, dy + lineWidth);
    else context.strokeOval(x, y, dx + lineWidth, dy + lineWidth);
  }
}
