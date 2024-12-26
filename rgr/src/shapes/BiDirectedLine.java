package shapes;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class BiDirectedLine extends DirectedLine {
  public BiDirectedLine(final List<Double> coords) {
    super(coords);
  }

  public BiDirectedLine(final double x, final double y) {
    super(x, y);
  }

  @Override
  public void draw(final GraphicsContext context) {
    if (coords.size() < 4) return;
    super.draw(context);
    drawArrows(context, coords.get(2), coords.get(3), coords.get(0), coords.get(1));
  }
}
