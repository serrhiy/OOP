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

  @SuppressWarnings("unused")
  public Editor(final Canvas canvas) {
    this.canvas = canvas;
    context = canvas.getGraphicsContext2D();
    canvas.widthProperty().addListener((event) -> {
      clear();
      drawAll();
    });
    canvas.heightProperty().addListener((event) -> {
      clear();
      drawAll();
    });
  }

  public void drawAll() {
    for (final var shape: shapes) shape.draw(context);
  }

  public void clear() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public void add(final Shape shape) {
    shapes.add(shape);
  }

  public void pop() {
    if (shapes.size() > 0) shapes.pop();
  }

  public void onLeftButtonDown(double x, double y) {
    startX = x;
    startY = y;
    final var shape = shapes.peek();
    shape.dashes = shape.useDashes ? lineDashes : 0;
    shape.color = Color.getInstance().getCurrentColor();
    shape.fill = Fill.getInstance().getFill();
    shape.onStart(context, x, y);
  }

  public void onMouseMove(double x, double y) {
    if (drawing) clear();
    else drawing = true;
    shapes.peek().setCoords(startX, startY, x, y);
    drawAll();
  }

  public void onLeftButtonUp(double x, double y) {
    clear();
    final var shape = shapes.peek();
    shape.setCoords(startX, startY, x, y);
    shape.dashes = 0;
    drawAll();
    drawing = false;
  }  
}