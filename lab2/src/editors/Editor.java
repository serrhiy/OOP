package editors;

import shapes.Shape;
import canvas.Canvas;
import settings.Color;
import settings.Fill;

public abstract class Editor {
  private static double lineDashes = 10;

  protected double startX = 0;
  protected double startY = 0;
  protected boolean drawing = false;
  protected final Shape shape;

  public Editor(final Shape shape) {
    this.shape = shape;
  }

  public void onLeftButtonDown(double x, double y) {
    startX = x;
    startY = y;
  }

  public void onMouseMove(double x, double y) {
    if (drawing) Canvas.pop();
    else drawing = true;
    final var coords = getCoords(startX, startY, x, y);
    shape.setCoords(coords);
    shape.dashes = lineDashes;
    shape.color = Color.getCurrentColor();
    shape.fill = Fill.getFill();
    Canvas.push(shape);
  }

  public void onLeftButtonUp(double x, double y) {
    Canvas.pop();
    final var coords = getCoords(startX, startY, x, y);
    shape.setCoords(coords);
    shape.dashes = 0;
    Canvas.push(shape);
    drawing = false;
  }

  protected abstract double[] getCoords(double startX, double startY, double x, double y);
}
