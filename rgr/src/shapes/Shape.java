package shapes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public abstract class Shape {
  public boolean useDashes = true;
  protected final List<Double> coords;
  protected ShapeConfig config;

  public Shape(final List<Double> coords) {
    this.coords = new ArrayList<>(coords);
    this.config = new ShapeConfig();
  }

  public Shape(final double x, final double y) {
    this(List.of(x, y));
  }

  public Shape apply(final ShapeConfig config) {
    this.config = config;
    return this;
  }

  public ShapeConfig getConfig() { return config; }

  public List<Double> getCoords() { return coords; }

  public abstract Shape update(final double x, final double y);

  public abstract void draw(final GraphicsContext context);

  public abstract boolean contains(final double x, final double y);
}
