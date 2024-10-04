package editors;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import settings.Color;
import shapes.Shape;

public abstract class Editor {
  private final Pane pane;
  protected double startX = 0;
  protected double startY = 0;
  protected boolean drawing = false;
  protected final Shape shape;

  public Editor(final Pane pane, final Shape shape) {
    this.pane = pane;
    this.shape = shape;
  }

  protected GraphicsContext createContext() {
    final var width = pane.getWidth();
    final var height = pane.getHeight();
    final var canvas = new Canvas(width, height);
    pane.getChildren().add(canvas);
    return canvas.getGraphicsContext2D();
  }

  protected void deleteLastCanvas() {
    pane.getChildren().removeLast();
  }

  public void onLeftButtonDown(double x, double y) {
    startX = x;
    startY = y;
  }

  public void onMouseMove(double x, double y) {
    if (drawing) deleteLastCanvas();
    else drawing = true;
    final var coords = getCoords(startX, startY, x, y);
    shape.setCoords(coords);
    final var context = createContext();
    context.setLineDashes(10);
    shape.draw(context);
  }

  public void onLeftButtonUp(double x, double y) {
    deleteLastCanvas();
    final var coords = getCoords(startX, startY, x, y);
    shape.setCoords(coords);
    final var context = createContext();
    Color.applyCurentColor(context);
    context.setLineDashes(0);
    shape.draw(context);
    Color.applyCurentColor(context);
    drawing = false;
  }

  protected abstract double[] getCoords(double startX, double startY, double x, double y);
}
