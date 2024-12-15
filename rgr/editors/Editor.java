package editors;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import shapes.Shape;
import java.util.ArrayList;
import java.util.List;
import events.EventEmitter;

public class Editor {
  private final List<Shape> shapes = new ArrayList<>();
  private static final Editor instance = new Editor();
  private Canvas canvas = null;
  private GraphicsContext context = null;

  public static Editor getInstance() { return instance; }

  private void redraw() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    for (final var shape: shapes) shape.draw(context);
  }

  public Editor start(final Canvas canvas, final EventEmitter<Class<? extends Shape>> events) {
    this.canvas = canvas;
    this.context = canvas.getGraphicsContext2D();
    events.on("shape", (constructor) -> onNewShape(constructor, events));
    return this;
  }

  private void onNewShape(final Class<? extends Shape> constructor, final EventEmitter<Class<? extends Shape>> events) {
    canvas.setOnMousePressed((event) -> {
      if (!event.getButton().equals(MouseButton.PRIMARY)) return;
      try {
        final var declared = constructor.getDeclaredConstructor(double.class, double.class);
        final var shape = declared.newInstance(event.getX(), event.getY());
        shapes.add(shape);
        canvas.setOnMouseDragged((info) -> {
          shape.update(info.getX(), info.getY());
          redraw();
        });
        canvas.setOnMouseReleased((info) -> {
          shape.update(info.getX(), info.getY());
          shape.draw(context);
          redraw();
          events.emit("shape", constructor);
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
