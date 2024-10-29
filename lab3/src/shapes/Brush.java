package shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class Brush extends Shape {
  final List<Line> lines = new ArrayList<>();

  @Override
  public void draw(GraphicsContext context) {
    for (final var line: lines) line.draw(context);
  }

  public void addLine(final Line line) {
    lines.add(line);
  }
}