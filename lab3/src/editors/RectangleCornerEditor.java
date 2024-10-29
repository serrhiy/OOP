package editors;

import shapes.Rectangle;

public class RectangleCornerEditor extends Editor {

  public RectangleCornerEditor() {
    super(new Rectangle());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }
}