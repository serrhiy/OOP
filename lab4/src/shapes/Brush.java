package shapes;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class Brush extends Shape {

  public Brush() {
    super();
    coords = new ArrayList<>();
    useDashes = false;
  }

  @Override
  public void draw(GraphicsContext context) {
    final var size = coords.size();
    if (size <= 2) return;
    var prevX = coords.get(0);
    var prevY = coords.get(1);
    for (int i = 2; i < size; i += 2) {
      final var x = coords.get(i);
      final var y = coords.get(i + 1);
      context.strokeLine(prevX, prevY, x, y);
      prevX = x;
      prevY = y;
    }
  }

  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {
    coords.add(x2);
    coords.add(y2);
  }
}