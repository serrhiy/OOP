package shapes;

import javafx.scene.canvas.GraphicsContext;

public abstract class Shape {
  protected double x1, y1, x2, y2;

  Shape(double x1, double y1, double x2, double y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  Shape() {
    this(0, 0, 0, 0);
  }

  public void setCoords(double[] coords) {
    if (coords.length != 4) return;
    x1 = coords[0];
    y1 = coords[1];
    x2 = coords[2];
    y2 = coords[3];
  }

  public abstract void draw(final GraphicsContext context);
}
