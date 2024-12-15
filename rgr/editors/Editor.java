package editors;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shapes.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Editor {
  private final List<Shape> shapes = new ArrayList<>();
  private static final Editor instance = new Editor();
  private Canvas canvas = null;
  private GraphicsContext context = null;
  private Color color = Color.BLACK;
  private int width = 1;

  public static Editor getInstance() { return instance; }

  private void clear() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  private void drawShape(final Shape shape) {
    context.setLineWidth(shape.width);
    context.setStroke(shape.color);
    shape.draw(context);
  }

  private void redraw() {
    clear();
    for (final var shape: shapes) drawShape(shape);
  }

  private void drawDashes(final Shape shape) {
    context.setLineDashes(10);
    drawShape(shape);
    context.setLineDashes(0);
  }

  public Editor start(final Canvas canvas) {
    this.canvas = canvas;
    this.context = canvas.getGraphicsContext2D();
    return this;
  }

  public void newShape(final Class<? extends Shape> constructor) {
    canvas.setOnMousePressed((event) -> {
      if (!event.getButton().equals(MouseButton.PRIMARY)) return;
      onClick(event, constructor);
    });
  }

  private void onClick(final MouseEvent event, final Class<? extends Shape> constructor) {
    try {
      final var declared = constructor.getDeclaredConstructor(double.class, double.class);
      final var shape = declared.newInstance(event.getX(), event.getY());
      shape.color = this.color;
      shape.width = this.width;
      canvas.setOnMouseDragged((info) -> onMove(info, shape));
      canvas.setOnMouseReleased((_) -> onRelease(shape));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void onMove(final MouseEvent event, final Shape shape) {
    shape.update(event.getX(), event.getY());
    redraw();
    if (shape.useDashes) drawDashes(shape);
    else drawShape(shape);
  }

  private void onRelease(final Shape shape) {
    shapes.add(shape);
    redraw();
    newShape(shape.getClass());
  }

  public List<Shape> shapes() {
    return Collections.unmodifiableList(shapes);
  }

  public Editor restore() {
    shapes.clear();
    clear();
    return this;
  }

  public Editor add(final Shape shape) {
    shapes.add(shape);
    redraw();
    return this;
  }

  public Editor changeColor(final Color color) {
    this.color = color;
    return this;
  }

  public Editor changeWidth(final int width) {
    this.width = width;
    return this;
  }
}
