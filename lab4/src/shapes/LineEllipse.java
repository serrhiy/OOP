package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class LineEllipse extends Shape implements Linable, Ellipsable {
  final static int ellipseRadius = 20;

  public LineEllipse() {
    super();
    coords = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
  }

  @Override
  public void draw(GraphicsContext context) {
    prepareContext(context);
    final var x1 = coords.get(0);
    final var y1 = coords.get(1);
    final var x2 = coords.get(2);
    final var y2 = coords.get(3);
    final var dx = x2 - x1;
    final var dy = y2 - y1;
    final var angle = Math.atan2(dy, dx);
    final var lineWidth = context.getLineWidth();
    final var length = ellipseRadius / 2 + lineWidth;
    Linable.super.drawLine(
      context,
      x1 + length * Math.cos(angle),
      y1 + length * Math.sin(angle),
      x2 + length * Math.cos(Math.PI + angle),
      y2 + length * Math.sin(Math.PI + angle)
    );
    Ellipsable.super.drawEllipse(context,
      x1 - ellipseRadius / 2,
      y1 - ellipseRadius / 2,
      ellipseRadius,
      ellipseRadius,
      fill
    );
    Ellipsable.super.drawEllipse(context,
      x2 - ellipseRadius / 2,
      y2 - ellipseRadius / 2,
      ellipseRadius,
      ellipseRadius,
      fill
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
