package shapes;

import javafx.scene.canvas.GraphicsContext;

public interface Shapeble {
  public void setCoords(double x1, double y1, double x2, double y2);
  public default void onStart(GraphicsContext context, double x, double y) {
    
  }
}
