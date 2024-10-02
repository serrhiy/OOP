package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Elipse extends Shape {

  public Elipse(double x1, double y1, double x2, double y2) {
    super(x1, y1, x2, y2);
  }

  public Elipse() {
    super();
  }

  @Override
  public void draw(GraphicsContext context) {
    final double dx = Math.abs(x2 - x1);
    final double dy = Math.abs(y2 - y1);
    final double x = (x1 + x2 - dx) / 2;
    final double y = (y1 + y2 - dy) / 2;
    context.strokeOval(x, y, dx, dy);
  }
}
