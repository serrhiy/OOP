package editors;

import javafx.scene.layout.Pane;
import shapes.Rectangle;

public class RectangleAngleEditor extends Editor {

  public RectangleAngleEditor(final Pane pane) {
    super(pane, new Rectangle());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }
}
