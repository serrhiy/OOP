package editors;

import shapes.Point;

public class PointEditor extends Editor {

  public PointEditor() {
    super(new Point());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }
  
  @Override
  public void onLeftButtonDown(double x, double y) {
    super.onLeftButtonDown(x, y);
    super.onMouseMove(x, y);
  }
}