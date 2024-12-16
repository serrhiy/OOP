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
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import shapes.ShapeParser;

public class FileSaver {
  public static void jsonSave(final File file, final Stage stage, final Canvas canvas) {
    try (final var writer = new BufferedWriter(new FileWriter(file, false))) {
      final var shapes = Editor.getInstance().shapes();
      final var result = new JSONObject();
      final var window = new JSONObject();
      final var data = ShapeParser.serialise(shapes);
      window.put("width", stage.getWidth());
      window.put("height", stage.getHeight());
      result.put("window", window);
      result.put("shapes", data);
      writer.write(result.toString());
    } catch (final Exception exception) {
      exception.printStackTrace();
    }
  }

  public static void jsonOpen(final File file, final Stage stage, final Canvas canvas) {
    try {
      final var bytes = Files.readAllBytes(file.toPath());
      final var text = new String(bytes);
      final var json = new JSONObject(text);
      final var window = json.getJSONObject("window");
      final var shapes = json.getJSONArray("shapes");
      final var width = window.getDouble("width");
      final var height = window.getDouble("height");
      final var newshapes = ShapeParser.parse(shapes);
      stage.setWidth(width);
      stage.setHeight(height);
      Editor.getInstance().replace(newshapes);
    } catch (final Exception exception) {
      exception.printStackTrace();
    }
  }

  public static void pngSave(final File file, final Stage stage, final Canvas canvas) {
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

  public static void pngOpen(final File file, final Stage stage, final Canvas canvas) {
    try {
      final var stream = new FileInputStream(file);
      final var image = new Image(stream);
      Editor.getInstance().restore().setBacground(image);
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  public static String extention(final String filename) {
    final var index = filename.indexOf(".");
    return index == -1 ? "" : filename.substring(index + 1);
  }
}
