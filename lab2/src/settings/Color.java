package settings;

import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import java.util.Collection;

public class Color {
  static private javafx.scene.paint.Color currentColor = javafx.scene.paint.Color.BLACK;
  static private Map<String, javafx.scene.paint.Color> colors = Map.of(
    "black", javafx.scene.paint.Color.BLACK,
    "red", javafx.scene.paint.Color.RED,
    "blue", javafx.scene.paint.Color.BLUE,
    "green", javafx.scene.paint.Color.GREEN
  );
  
  static public void setColor(final String color) {
    if (!colors.containsKey(color)) return;
    currentColor = colors.get(color);
  }

  static public void resetColor(final GraphicsContext context) {
    currentColor = javafx.scene.paint.Color.BLACK;
    context.setStroke(currentColor);
  }

  static public void applyCurentColor(final GraphicsContext context) {
    context.setStroke(currentColor);
  }

  static public Collection<? extends String> getStringColors() {
    return colors.keySet();
  }

  static public Collection<? extends javafx.scene.paint.Color> getColors() {
    return colors.values();
  }
}
