package editors;

import javafx.scene.layout.Pane;
import shapes.Rectangle;

public class RectangleEditor extends Editor {

  public RectangleEditor(Pane pane) {
    super(pane);
  }

  @Override
  public void onMouseMove(double x, double y) {
    if (drawing) super.deleteLastCanvas();
    else drawing = true;
    final var tempLine = new Rectangle(x, y, 2 * startX - x, 2 * startY - y);
    final var context = super.createContext();
    context.setLineDashes(10);
    tempLine.draw(context);
  }

  @Override
  public void onLeftButtonUp(double x, double y) {
    super.deleteLastCanvas();
    final var line = new Rectangle(x, y, 2 * startX - x, 2 * startY - y);
    final var context = super.createContext();
    context.setLineDashes(0);
    line.draw(context);
    drawing = false;
  }

}
