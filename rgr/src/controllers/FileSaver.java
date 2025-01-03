package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import org.json.JSONObject;
import editors.Editor;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;
import shapes.ShapeParser;
import java.awt.image.BufferedImage;

public class FileSaver {
  public static void jsonSave(final File file, final Editor editor) {
    try (final var writer = new BufferedWriter(new FileWriter(file, false))) {
      final var canvas = editor.getCanvas();
      final var shapes = editor.shapes();
      final var result = new JSONObject();
      final var window = new JSONObject();
      final var data = ShapeParser.serialise(shapes);
      window.put("width", canvas.getWidth());
      window.put("height", canvas.getHeight());
      result.put("window", window);
      result.put("shapes", data);
      writer.write(result.toString());
    } catch (final Exception exception) {
      exception.printStackTrace();
    }
  }

  public static Pair<Double, Double> jsonOpen(final File file, final Editor editor) {
    try {
      final var bytes = Files.readAllBytes(file.toPath());
      final var text = new String(bytes);
      final var json = new JSONObject(text);
      final var window = json.getJSONObject("window");
      final var shapes = json.getJSONArray("shapes");
      final var width = window.getDouble("width");
      final var height = window.getDouble("height");
      final var newshapes = ShapeParser.parse(shapes);
      final var canvas = editor.getCanvas();
      canvas.setWidth(width);
      canvas.setHeight(height);
      editor.replace(newshapes);
      return new Pair<Double, Double>(width, height);
    } catch (final Exception exception) {
      exception.printStackTrace();
      return new Pair<Double, Double>(0.0, 0.0);
    }
  }

  public static void pngSave(final File file, final Editor editor) {
    final var canvas = editor.getCanvas();
    final var width = (int)canvas.getWidth();
    final var height = (int)canvas.getHeight();
    final var writableImage = new WritableImage(width, height);
    canvas.snapshot(null, writableImage);
    final var renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
    try {
      ImageIO.write(renderedImage, "png", file);
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  public static Pair<Double, Double> binaryOpen(final File file, final Editor editor) {
    try {
      final var stream = new FileInputStream(file);
      final var image = new Image(stream);
      editor.restore().setBacground(image);
      return new Pair<Double, Double>(image.getWidth(), image.getHeight());
    } catch (final IOException exception) {
      exception.printStackTrace();
      return new Pair<Double, Double>(0.0, 0.0);
    }
  }

  public static void jpgSave(final File file, final Editor editor) {
    final var canvas = editor.getCanvas();
    final var width = (int)canvas.getWidth();
    final var height = (int)canvas.getHeight();
    final var writableImage = new WritableImage(width, height);
    canvas.snapshot(null, writableImage);
    final var awtImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    final var renderedImage = SwingFXUtils.fromFXImage(writableImage, awtImage);
    try {
      ImageIO.write(renderedImage, "jpg", file);
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  public static String extention(final String filename) {
    final var index = filename.indexOf(".");
    return index == -1 ? "" : filename.substring(index + 1);
  }
}
