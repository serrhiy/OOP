package editors;

import javafx.scene.layout.Pane;
import settings.Color;
import shapes.Brush;
import javafx.scene.canvas.GraphicsContext;

public class BrushEditor extends Editor {

  private GraphicsContext context;

  public BrushEditor(Pane pane) {
    super(pane, new Brush());
  }

  @Override
  protected double[] getCoords(double startX, double startY, double x, double y) {
    return new double[]{ startX, startY, x, y };
  }

  public void onLeftButtonDown(double x, double y) {
    context = super.createContext();
    Color.applyCurentColor(context);
    context.beginPath();
    context.moveTo(x, y);
  }
  
  public void onMouseMove(double x, double y) {
    context.lineTo(x, y);
    context.stroke();
  }

  public void onLeftButtonUp(double x, double y) {

  }
}
