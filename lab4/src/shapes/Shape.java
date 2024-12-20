package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public abstract class Shape {
  protected List<Double> coords;
  public Color color = Color.BLACK;
  public boolean fill = false;
  public double dashes = 0;
  public boolean useDashes = true;

  protected void prepareContext(final GraphicsContext context) {
    context.setStroke(color);
    context.setFill(color);
    context.setLineDashes(dashes);
  }

  public abstract void draw(final GraphicsContext context);

  public abstract void setCoords(double x1, double y1, double x2, double y2);
  public void onStart(GraphicsContext context, double x, double y) {
    
  }
}
