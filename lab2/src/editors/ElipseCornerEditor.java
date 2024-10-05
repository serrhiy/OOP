package editors;

import javafx.scene.layout.Pane;
import shapes.Elipse;

public class ElipseCornerEditor extends Editor {

  public ElipseCornerEditor(Pane pane) {
    super(pane, new Elipse());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }
  
}
