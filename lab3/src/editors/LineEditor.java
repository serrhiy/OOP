package editors;

import shapes.Line;

public class LineEditor extends Editor {

  public LineEditor() {
    super(new Line());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }
}