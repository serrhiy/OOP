package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public abstract class Shape {
  protected List<Double> coords;
  public Color color = Color.BLACK;
  public boolean fill = false;
  public double dashes = 0;
  public boolean useDashes = true;

  public Shape() {
    this(new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0)));
  }

  public Shape(final List<Double> points) {
    coords = points;
  }

  protected void prepareContext(final GraphicsContext context) {
    context.setStroke(color);
    context.setFill(color);
    context.setLineDashes(dashes);
  }

  public abstract void draw(final GraphicsContext context);

  public abstract void setCoords(double x1, double y1, double x2, double y2);
  public void onStart(GraphicsContext context, double x, double y) {
    
  }
  public abstract Pair<Pair<Double, Double>, Pair<Double, Double>> getDisplayCoords();
  public abstract String getName();
}
