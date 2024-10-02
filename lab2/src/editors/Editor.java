package editors;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public abstract class Editor {
  private final Pane pane;
  protected double startX = 0;
  protected double startY = 0;
  protected boolean drawing = false;

  public Editor(final Pane pane) {
    this.pane = pane;
  }

  protected GraphicsContext createContext() {
    final var width = pane.getPrefWidth();
    final var height = pane.getPrefHeight();
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

  public abstract void onMouseMove(double x, double y);

  public abstract void onLeftButtonUp(double x, double y);

}
