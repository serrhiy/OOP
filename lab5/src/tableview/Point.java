package tableview;

import javafx.beans.property.SimpleDoubleProperty;

public class Point {
  private final SimpleDoubleProperty x1;
  private final SimpleDoubleProperty y1;
  private final SimpleDoubleProperty x2;
  private final SimpleDoubleProperty y2;

  public Point(double x1, double y1, double x2, double y2) {
    this.x1 = new SimpleDoubleProperty(x1);
    this.y1 = new SimpleDoubleProperty(y1);
    this.x2 = new SimpleDoubleProperty(x2);
    this.y2 = new SimpleDoubleProperty(y2);
  }

  public Point setPoints(double x1, double y1, double x2, double y2) {
    this.x1.set(x1);
    this.y1.set(y1);
    this.x2.set(x2);
    this.y2.set(y1);
    return this;
  }
}
