package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import editors.Editor;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import shapes.ShapeParser;

public class FileSaver {
  public static void json(final File file, final Stage stage, final Canvas canvas) {
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

  public static void png(final File file, final Stage stage, final Canvas canvas) {
    final var width = (int)stage.getWidth();
    final var height = (int)stage.getHeight();
    final var writableImage = new WritableImage(width, height);
    canvas.snapshot(null, writableImage);
    final var renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
    try {
      ImageIO.write(renderedImage, "png", file);
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  public static String extention(final String filename) {
    final var index = filename.indexOf(".");
    return index == -1 ? "" : filename.substring(index + 1);
  }
}
