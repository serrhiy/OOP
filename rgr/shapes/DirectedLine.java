package shapes;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class DirectedLine extends shapes.Line {
  public DirectedLine(final List<Double> coords) {
    super(coords);
  }

  public DirectedLine(final double x, final double y) {
    super(x, y);
  }

  @Override
  public void draw(final GraphicsContext context) {
    if (coords.size() < 4) return;
    final var rotateAngle = Math.PI / 4;
    final var arrowLength = 10;
    final var x1 = coords.get(0);
    final var y1 = coords.get(1);
    final var x2 = coords.get(2);
    final var y2 = coords.get(3);
    final var angle = Math.atan2(y2 - y1, x2 - x1);
    final var arrowX1 = x2 - arrowLength * Math.cos(angle - rotateAngle);
    final var arrowY1 = y2 - arrowLength * Math.sin(angle - rotateAngle);
    final var arrowX2 = x2 - arrowLength * Math.cos(angle + rotateAngle);
    final var arrowY2 = y2 - arrowLength * Math.sin(angle + rotateAngle);
    context.strokeLine(x1, y1, x2, y2);
    context.strokeLine(x2, y2, arrowX1, arrowY1);
    context.strokeLine(x2, y2, arrowX2, arrowY2);
  }

}
