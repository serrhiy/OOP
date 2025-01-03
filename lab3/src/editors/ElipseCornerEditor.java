package editors;

import shapes.Elipse;

public class ElipseCornerEditor extends Editor {

  public ElipseCornerEditor() {
    super(new Elipse());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }
  
}