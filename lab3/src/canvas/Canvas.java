package canvas;
import java.util.Stack;

import javafx.scene.canvas.GraphicsContext;
import shapes.Shape;

public class Canvas {
  private static javafx.scene.canvas.Canvas canvas = null;
  private static GraphicsContext context = null;
  private static Stack<Shape> shapes = new Stack<>();

  public static void setCanvas(final javafx.scene.canvas.Canvas canvas) {
    Canvas.canvas = canvas;
    context = canvas.getGraphicsContext2D();
  }

  private static void clear() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public static void drawAll() {
    for (final var shape: shapes) draw(shape);
  }

  public static void draw(final Shape shape) {
    context.setStroke(shape.color);
    context.setFill(shape.color);
    context.setLineWidth(shape.width);
    context.setLineDashes(shape.dashes);
    shape.draw(context);
  }

  public static void push(final Shape shape) {
    shapes.push(shape);
    draw(shape);
  }

  public static void pop() {
    if (shapes.isEmpty()) return;
    shapes.pop();
    clear();
    drawAll();
  }
}