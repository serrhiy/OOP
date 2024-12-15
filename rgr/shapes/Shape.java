package shapes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

  public boolean useDashes = true;
  public Color color = Color.BLACK;
  public int width = 3;
  protected final List<Double> coords;

  public Shape(final List<Double> coords) {
    this.coords = new ArrayList<>(coords);
  }

  public Shape(final double x, final double y) {
    this(List.of(x, y));
  }

  public List<Double> getCoords() { return coords; }

  public abstract Shape update(final double x, final double y);

  public abstract void draw(final GraphicsContext context);
}
