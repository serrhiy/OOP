package editors;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shapes.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Editor {
  private final List<Shape> shapes = new ArrayList<>();
  private final List<Shape> selected = new ArrayList<>();
  private static final Editor instance = new Editor();
  private Canvas canvas = null;
  private GraphicsContext context = null;
  private Color color = Color.BLACK;
  private int width = 1;
  private final double selectedK = 2;

  public static Editor getInstance() { return instance; }

  private void clear() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  private void drawShape(final Shape shape, double k) {
    context.setLineWidth(shape.getConfig().getWidth() * k);
    context.setStroke(shape.getConfig().getColor());
    shape.draw(context);
  }

  private void drawShape(final Shape shape) {
    drawShape(shape, 1);
  }

  private void redraw() {
    clear();
    for (final var shape: shapes) {
      final var k = selected.contains(shape) ? selectedK : 1;
      drawShape(shape, k);
    }
  }

  private void drawDashes(final Shape shape) {
    context.setLineDashes(10);
    drawShape(shape);
    context.setLineDashes(0);
  }

  private boolean isPrimaryButton(MouseEvent event) {
    return event.getButton().equals(MouseButton.PRIMARY);
  }

  public Editor start(final Canvas canvas, final Pane root) {
    this.canvas = canvas;
    this.context = canvas.getGraphicsContext2D();
    root.setOnMousePressed((event) -> {
      if (!isPrimaryButton(event)) return;
      selected.clear();
      redraw();
    });
    root.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
      final var isCtrl = event.isControlDown();
      final var isZ = event.getCode().equals(KeyCode.Z);
      if (!(isCtrl && isZ) || shapes.isEmpty()) return;
      final var shape = shapes.removeLast();
      if (selected.contains(shape)) selected.remove(shape);
      redraw();
    });
    root.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
      if (!event.getCode().equals(KeyCode.DELETE)) return;
      for (final var shape: selected) shapes.remove(shape);
      selected.clear();
      redraw();
    });
    canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (event) -> {
      if (isPrimaryButton(event)) return;
      final var x = event.getX();
      final var y = event.getY();
      for (final var shape: shapes) {
        final var contains = shape.contains(x, y);
        if (!contains || selected.contains(shape)) continue;
        selected.add(shape);
        drawShape(shape, selectedK);
      }
    });
    return this;
  }

  public void newShape(final Class<? extends Shape> constructor) {
    canvas.setOnMousePressed((event) -> {
      if (!isPrimaryButton(event)) return;
      onClick(event, constructor);
    });
  }

  private void onClick(final MouseEvent event, final Class<? extends Shape> constructor) {
    try {
      final var declared = constructor.getDeclaredConstructor(double.class, double.class);
      final var shape = declared.newInstance(event.getX(), event.getY());
      shape.getConfig().setColor(color);
      shape.getConfig().setWidth(width);
      canvas.setOnMouseDragged((info) -> {
        if (!isPrimaryButton(info)) return;
        onMove(info, shape);
      });
      canvas.setOnMouseReleased((info) -> {
        if (!isPrimaryButton(info)) return;
        onRelease(shape);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseReleased(null);
        canvas.setOnMousePressed(null);
      });
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
  }

  public List<Shape> shapes() {
    return Collections.unmodifiableList(shapes);
  }

  public Editor restore() {
    shapes.clear();
    clear();
    return this;
  }

  public Editor replace(final List<Shape> shapes) {
    this.shapes.clear();
    this.shapes.addAll(shapes);
    redraw();
    return this;
  }

  public Editor changeColor(final Color color) {
    this.color = color;
    for (final var shape: selected) {
      shape.getConfig().setColor(color);
    }
    selected.clear();
    redraw();
    return this;
  }

  public Editor changeWidth(final int width) {
    this.width = width;
    for (final var shape: selected) {
      shape.getConfig().setWidth(width);
    }
    selected.clear();
    redraw();
    return this;
  }
}
