package editors;

import javafx.scene.layout.Pane;
import shapes.Rectangle;

public class RectangleCornerEditor extends Editor {

  public RectangleCornerEditor(final Pane pane) {
    super(pane, new Rectangle());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }
}