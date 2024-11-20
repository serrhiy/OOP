package shapes;

import javafx.scene.canvas.GraphicsContext;

public interface Linable {
  public default void drawLine(GraphicsContext context, double x1, double y1, double x2, double y2) {
    context.strokeLine(x1, y1, x2, y2);
  }
}
