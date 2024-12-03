package editors;

import shapes.Shape;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import settings.Color;
import settings.Fill;
import java.util.function.Consumer;

public class Editor {
  private static final double lineDashes = 10;
  private double startX = 0;
  private double startY = 0;
  private boolean drawing = false;
  private List<Shape> shapes = new ArrayList<Shape>();
  private Canvas canvas;
  private GraphicsContext context;
  private Map<String, List<Consumer<Shape>>> listeners = new HashMap<>();

  private static Editor instance = null;

  public Editor setCanvas(final Canvas canvas) {
    this.canvas = canvas;
    context = canvas.getGraphicsContext2D();
    canvas.widthProperty().addListener((_) -> {
      clear();
      drawAll();
    });
    canvas.heightProperty().addListener((_) -> {
      clear();
      drawAll();
    });
    return this;
  }

  public static Editor getInstance() {
    instance = instance == null ? new Editor() : instance;
    return instance;
  }

  private void redraw() {
    clear();
    drawAll();
  }

  private void drawAll() {
    for (final var shape: shapes) shape.draw(context);
  }

  private void clear() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public void add(final Shape shape) {
    shapes.add(shape);
  }

  public void addToCanvas(final Shape shape) {
    this.add(shape);
    redraw();
    this.emit("create", shape);
  }

  public void pop() {
    if (shapes.size() == 0) return;
    final var shape = shapes.removeLast();
    redraw();
    this.emit("delete", shape);
  }

  public void onLeftButtonDown(double x, double y) {
    startX = x;
    startY = y;
    final var shape = shapes.getLast();
    shape.dashes = shape.useDashes ? lineDashes : 0;
    shape.color = Color.getInstance().getCurrentColor();
    shape.fill = Fill.getInstance().getFill();
    shape.onStart(context, x, y);
  }

  public void onMouseMove(double x, double y) {
    if (drawing) clear();
    else drawing = true;
    shapes.getLast().setCoords(startX, startY, x, y);
    drawAll();
  }

  public void onLeftButtonUp(double x, double y) {
    clear();
    final var shape = shapes.getLast();
    shape.setCoords(startX, startY, x, y);
    shape.dashes = 0;
    drawAll();
    drawing = false;
    emit("create", shape);
  }

  public Editor on(final String eventName, final Consumer<Shape> listener) {
    final var exists = listeners.containsKey(eventName);
    if (exists) listeners.get(eventName).add(listener);
    else listeners.put(eventName, List.of(listener));
    return this;
  }

  public Editor emit(final String eventName, final Shape shape) {
    final var exists = listeners.containsKey(eventName);
    if (!exists) return this;
    for (final var listener: listeners.get(eventName)) {
      listener.accept(shape);
    }
    return this;
  }

  public Editor reset() {
    for (final var shape: shapes) emit("delete", shape);
    shapes.clear();
    clear();
    return this;
  }

  public List<Shape> shapes() {
    return List.copyOf(shapes);
  }
}