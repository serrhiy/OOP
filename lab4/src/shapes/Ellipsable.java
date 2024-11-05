package shapes;

import javafx.scene.canvas.GraphicsContext;

public interface Ellipsable {
  public default void drawLine(GraphicsContext context, double x, double y, double dx, double dy, boolean fill) {
    final var width = context.getLineWidth();
    if (fill) context.fillOval(x, y, dx + width, dy + width);
    else context.strokeOval(x, y, dx + width, dy + width);  
  }
}
