package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {
  @Override
  public void draw(GraphicsContext context, boolean fill) {
    context.strokeLine(x1, y1, x2, y2);
  }
}
