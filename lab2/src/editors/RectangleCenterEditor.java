package editors;

import shapes.Rectangle;

public class RectangleCenterEditor extends Editor {

  public RectangleCenterEditor() {
    super(new Rectangle());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ 2 * startX - x, 2 * startY - y, x, y };
  }
}
