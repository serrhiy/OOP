package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.Clipboard;
import javafx.util.Pair;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONObject;

public class MainController {
  @FXML
  private Canvas canvas;
  private final double padding = 20;
  private final double ticksLength = 7;
  private final int yTiks = 10;
  private final double dotWidth = 6;

  private JSONObject getJson(final String source) {
    try {
      return new JSONObject(source);      
    } catch (Exception e) {
      return null;
    }
  }

  public static String truncate(double input) {
    DecimalFormat decimalFormat = new DecimalFormat("##.##");
    decimalFormat.setRoundingMode(RoundingMode.DOWN);
    String formatResult = decimalFormat.format(input);
    return formatResult;
  }

  private void drawYTicks(final GraphicsContext context, int maxY) {
    final var height = canvas.getHeight();
    final var center = height / 2;
    final var heightLength = center - padding;
    final var step = heightLength / yTiks;
    for (int index = 0; index < yTiks; index++) {
      final var down = center + (index + 1) * step;
      final var up = center - (index + 1) * step;
      final var cost = Math.abs(maxY / (double)yTiks * (index + 1));
      context.strokeLine(padding - ticksLength, up, padding + ticksLength, up);
      context.strokeLine(padding - ticksLength, down, padding + ticksLength, down);
      context.strokeText(truncate(cost), padding + ticksLength, up);
      context.strokeText(truncate(-cost), padding + ticksLength, down);
    }
  }

  private void drawXTicks(final GraphicsContext context, int maxX) {
    final var width = canvas.getWidth();
    final var height = canvas.getHeight();
    final var widthLength = width - 2 * padding;
    final var step = widthLength / (maxX - 1);
    final var xHeight = height / 2;
    for (int index = 0; index < maxX; index++) {
      final var position = index * step + padding;
      context.strokeLine(position, xHeight - ticksLength, position, xHeight + ticksLength);
      context.strokeText(String.valueOf(index), position, xHeight + ticksLength * 2);
    }
  }

  private void drawAxes(int maxX, int maxY) {
    final var width = canvas.getWidth();
    final var height = canvas.getHeight();
    final var context = canvas.getGraphicsContext2D();
    context.strokeLine(padding / 2, height / 2, width - padding / 2, height / 2);
    context.strokeLine(padding, padding / 2, padding, height - padding / 2);
    context.strokeText("y", padding / 2, padding / 2);
    context.strokeText("x", width - padding / 2, height / 2);
    drawXTicks(context, maxX);
    drawYTicks(context, maxY);
  }

  private void curwe(final ArrayList<Pair<Double, Double>> points) {
    final var context = canvas.getGraphicsContext2D();
    final var p = points.getFirst();
    context.strokeOval(p.getKey() - dotWidth / 2, p.getValue()- dotWidth / 2, dotWidth, dotWidth);
    for (int index = 0; index < points.size() - 1; index++) {
      final var first = points.get(index);
      final var second = points.get(index + 1);
      final var x1 = first.getKey();
      final var y1 = first.getValue();
      final var x2 = second.getKey();
      final var y2 = second.getValue();
      context.strokeOval(x2 - dotWidth / 2, y2 - dotWidth / 2, dotWidth, dotWidth);
      context.strokeLine(x1, y1, x2, y2);
    }
  }

  @FXML
  private void initialize() {
    Platform.runLater(() -> {
      final var clipboard = Clipboard.getSystemClipboard();
      final var content = clipboard.getString();
      final var json = getJson(content);
      if (json == null || !json.has("data")) return;
      final var data = json.getJSONArray("data");
      final var min = json.getDouble("min");
      final var max = json.getDouble("max");

      final var points = new ArrayList<Double>();
      for (final var point: data) {
        final var number = Double.valueOf(point.toString());
        points.add(number);
      }
      final var absMax = Math.max(Math.abs(min), Math.abs(max));
      drawAxes(points.size(), (int)Math.ceil(absMax));
      final var width = canvas.getWidth();
      final var height = canvas.getHeight();
      final var widthLength = width - 2 * padding;
      final var step = widthLength / (points.size() - 1);
      final var normalisedPoints = new ArrayList<Pair<Double, Double>>();
      final var minimum = height - padding + (min + absMax) * (2 * padding - height) / (2 * absMax);
      final var maximum = height - padding + (max + absMax) * (2 * padding - height) / (2 * absMax);
      for (int index = 0; index < points.size(); index++) {
        final var y = points.get(index);
        final var xPos = index * step + padding;
        final var yPos = minimum + (y - min) * (maximum - minimum) / (max - min);
        final var point = new Pair<>(xPos, yPos);
        normalisedPoints.add(point);
      }
      curwe(normalisedPoints);
    });
  }
}
