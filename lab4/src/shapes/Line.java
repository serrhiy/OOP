package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {

  public Line() {
    super();
    coords = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
  }

  @Override
  public void draw(GraphicsContext context) {
    context.strokeLine(
      coords.get(0),
      coords.get(1),
      coords.get(2),
      coords.get(3)
    );
  }

  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {
    coords.set(0, x1);
    coords.set(1, y1);
    coords.set(2, x2);
    coords.set(3, y2);
  }
}