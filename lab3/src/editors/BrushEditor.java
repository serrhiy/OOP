package editors;

import canvas.Canvas;
import settings.Color;
import shapes.Brush;
import shapes.Line;

public class BrushEditor extends Editor {
  public final Brush brush;
  double prevX = 0;
  double prevY = 0;

  public BrushEditor() {
    super(new Brush());
    brush = (Brush)super.shape;
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }

  public void onLeftButtonDown(double x, double y) {
    prevX = x;
    prevY = y;
    brush.color = Color.getCurrentColor();
  }
  
  public void onMouseMove(double x, double y) {
    if (drawing) Canvas.pop();
    else drawing = true;
    brush.addLine(new Line(prevX, prevY, x, y));
    prevX = x;
    prevY = y;
    Canvas.push(brush);
  }
}