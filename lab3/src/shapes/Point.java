package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Point extends Shape {

  private double width = 0;

  public Point(double x1, double y1) {
    super(0, 0, x1, y1);
  }

  public Point() {
    super(0, 0, 0, 0);
  }

  public void setWidth(final double width) {
    this.width = width;
  }

  @Override
  public void draw(GraphicsContext context) {
    final var width = this.width == 0 ? context.getLineWidth() : this.width;
    context.fillOval(x2 - width, y2 - width, width * 2, width * 2);
  }

}