package shapes;

import javafx.scene.paint.Color;

public class ShapeConfig {
  private Color color;
  private int width;
  private boolean fill;

  public ShapeConfig(final Color color, final int width, final boolean fill) {
    this.color = color;
    this.width = width;
    this.fill = fill;
  }

  public ShapeConfig() {
    this(Color.BLACK, 1, false);
  }

  public Color getColor() { return color; }
  public ShapeConfig setColor(final Color color) {
    this.color = color;
    return this;
  }
  public int getWidth() { return width; }
  public ShapeConfig setWidth(final int width) {
    this.width = width;
    return this;
  }
  public boolean getFill() { return fill; }
  public ShapeConfig setFill(final boolean fill) {
    this.fill = fill;
    return this;
  }

}
