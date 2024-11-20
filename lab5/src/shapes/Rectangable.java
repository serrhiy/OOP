package shapes;

import javafx.scene.canvas.GraphicsContext;

public interface Rectangable {
  default void drawRectangle(GraphicsContext context, double x, double y, double dx, double dy, boolean fill) {
    final var width = context.getLineWidth();
    if (fill) context.fillRect(x, y, dx + width, dy + width);
    else context.strokeRect(x, y, dx + width, dy + width);
  }
}
