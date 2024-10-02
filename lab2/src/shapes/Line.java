package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {

  public Line(double x1, double y1, double x2, double y2) {
    super(x1, y1, x2, y2);
  }

  public Line() {
    super();
  }

  @Override
  public void draw(GraphicsContext context) {
    context.strokeLine(x1, y1, x2, y2);
  }
}
