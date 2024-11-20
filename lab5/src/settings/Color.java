package settings;

import java.util.Map;
import java.util.Collection;

public class Color {
  static private Color instance = new Color();
  private javafx.scene.paint.Color currentColor = javafx.scene.paint.Color.BLACK;
  private Map<String, javafx.scene.paint.Color> colors = Map.of(
    "black", javafx.scene.paint.Color.BLACK,
    "red", javafx.scene.paint.Color.RED,
    "blue", javafx.scene.paint.Color.BLUE,
    "green", javafx.scene.paint.Color.GREEN,
    "yellow", javafx.scene.paint.Color.YELLOW,
    "purple", javafx.scene.paint.Color.PURPLE,
    "pink", javafx.scene.paint.Color.PINK,
    "gold", javafx.scene.paint.Color.GOLD,
    "brown", javafx.scene.paint.Color.BROWN,
    "light blue", javafx.scene.paint.Color.LIGHTBLUE
  );
  
  public void setColor(final String color) {
    if (!colors.containsKey(color)) return;
    currentColor = colors.get(color);
  }

  public javafx.scene.paint.Color getCurrentColor() {
    return currentColor;
  }

  public Collection<? extends String> getStringColors() {
    return colors.keySet();
  }

  public Collection<? extends javafx.scene.paint.Color> getColors() {
    return colors.values();
  }

  public static Color getInstance() {
    return instance;
  }
}