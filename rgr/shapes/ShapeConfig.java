package shapes;

import javafx.scene.paint.Color;

public class ShapeConfig {
  private Color color;
  private int width;

  public ShapeConfig(final Color color, final int width) {
    this.color = color;
    this.width = width;
  }

  public ShapeConfig() {
    this(Color.BLACK, 1);
  }

  public Color getColor() { return color; }
  public void setColor(final Color color) { this.color = color; }
  public int getWidth() { return width; }
  public void setWidth(final int width) { this.width = width; }
}
