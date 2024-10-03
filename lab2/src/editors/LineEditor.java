package editors;

import javafx.scene.layout.Pane;
import shapes.Line;

public class LineEditor extends Editor {

  public LineEditor(final Pane pane) {
    super(pane, new Line());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }
}
