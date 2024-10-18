package editors;

import javafx.scene.layout.Pane;
import settings.Color;
import shapes.Point;
import javafx.scene.canvas.GraphicsContext;

public class BrushEditor extends Editor {

  private GraphicsContext context;

  public BrushEditor(Pane pane) {
    super(pane, null);
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }

  private void drawPoint(final GraphicsContext context, final double x, final double y) {
    final var point = new Point(x, y);
    point.setWidth(1);
    point.draw(context, false);
  }

  @Override
  public void onLeftButtonDown(double x, double y) {
    context = super.createContext();
    Color.applyCurentColor(context);
    drawPoint(context, x, y);
    context.beginPath();
    context.moveTo(x, y);
  }
  
  @Override
  public void onMouseMove(double x, double y) {
    context.lineTo(x, y);
    context.stroke();
  }

  @Override
  public void onLeftButtonUp(double x, double y) {
    drawPoint(context, x, y);
  }
}
