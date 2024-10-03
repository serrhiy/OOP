package editors;

import javafx.scene.layout.Pane;
import shapes.Line;

public class LineEditor extends Editor {

  public LineEditor(Pane pane) {
    super(pane);
  }

  @Override
  public void onMouseMove(double x, double y) {
    if (drawing) super.deleteLastCanvas();
    else drawing = true;
    final var tempLine = new Line(startX, startY, x, y);
    final var context = super.createContext();
    context.setLineDashes(10);
    tempLine.draw(context);
  }

  @Override
  public void onLeftButtonUp(double x, double y) {
    super.deleteLastCanvas();
    final var line = new Line(startX, startY, x, y);
    final var context = super.createContext();
    context.setLineDashes(0);
    line.draw(context);
    drawing = false;
  }

}
