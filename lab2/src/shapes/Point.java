package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Point extends Shape {

  @Override
  public void draw(GraphicsContext context) {
    final var width = context.getLineWidth();
    context.fillOval(x2 - width, y2 - width, width * 2, width * 2);
  }

}
