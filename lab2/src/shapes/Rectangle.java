package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {

  public Rectangle(double x1, double y1, double x2, double y2) {
    super(x1, y1, x2, y2);
  }

  public Rectangle() {
    super();
  }

  @Override
  public void draw(GraphicsContext context) {
    context.strokeLine(x1, y1, x2, y1);
    context.strokeLine(x2, y1, x2, y2);
    context.strokeLine(x2, y2, x1, y2);
    context.strokeLine(x1, y2, x1, y1);
  }
}
