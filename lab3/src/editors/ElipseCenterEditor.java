package editors;

import javafx.scene.layout.Pane;
import shapes.Elipse;

public class ElipseCenterEditor extends Editor {

  public ElipseCenterEditor(Pane pane) {
    super(pane, new Elipse());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ 2 * startX - x, 2 * startY - y, x, y };
  }

}
