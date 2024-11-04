package editors;

import shapes.Shape;
import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import settings.Color;
import settings.Fill;


public class Editor {
  private static double lineDashes = 10;

  private double startX = 0;
  private double startY = 0;
  private boolean drawing = false;
  private static Stack<Shape> shapes = new Stack<>();
  private final Canvas canvas;
  private GraphicsContext context;

  public Editor(final Canvas canvas) {
    this.canvas = canvas;
    context = canvas.getGraphicsContext2D();
  }

  public void draw(final Shape shape) {
    context.setStroke(shape.color);
    context.setFill(shape.color);
    context.setLineWidth(shape.width);
    context.setLineDashes(shape.dashes);
    shape.draw(context);
  }

  public void drawAll() {
    for (final var shape: shapes) draw(shape);
  }

  private void clear() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public void add(final Shape shape) {
    shapes.add(shape);
  }

  public void onLeftButtonDown(double x, double y) {
    startX = x;
    startY = y;
  }

  public void onMouseMove(double x, double y) {
    if (drawing) clear();
    else drawing = true;
    final var shape = shapes.pop();
    shape.setCoords(startX, startY, x, y);
    shape.dashes = lineDashes;
    shape.color = Color.getCurrentColor();
    shape.fill = Fill.getFill();
    shapes.add(shape);
    drawAll();
  }

  public void onLeftButtonUp(double x, double y) {
    final var shape = shapes.pop();
    clear();
    shape.setCoords(startX, startY, x, y);
    shape.dashes = 0;
    shapes.add(shape);
    drawAll();
    drawing = false;
  }  
}