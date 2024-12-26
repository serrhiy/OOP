package shapes;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class DirectedLine extends shapes.Line {
  private static final double rotateAngle = Math.PI / 4;
  private static final double arrowLength = 10;

  public DirectedLine(final List<Double> coords) {
    super(coords);
  }

  public DirectedLine(final double x, final double y) {
    super(x, y);
  }

  public static void drawArrows(final GraphicsContext context, double x1, double y1, double x2, double y2) {
    final var angle = Math.atan2(y2 - y1, x2 - x1);
    final var arrowX1 = x2 - arrowLength * Math.cos(angle - rotateAngle);
    final var arrowY1 = y2 - arrowLength * Math.sin(angle - rotateAngle);
    final var arrowX2 = x2 - arrowLength * Math.cos(angle + rotateAngle);
    final var arrowY2 = y2 - arrowLength * Math.sin(angle + rotateAngle);
    context.strokeLine(x2, y2, arrowX1, arrowY1);
    context.strokeLine(x2, y2, arrowX2, arrowY2);
  }

  @Override
  public void draw(final GraphicsContext context) {
    if (coords.size() < 4) return;
    super.draw(context);
    drawArrows(context, coords.get(0), coords.get(1), coords.get(2), coords.get(3));
  }

}
