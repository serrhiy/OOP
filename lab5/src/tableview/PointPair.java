package tableview;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PointPair {
  private final SimpleStringProperty name;
  private final SimpleDoubleProperty x1;
  private final SimpleDoubleProperty y1;
  private final SimpleDoubleProperty x2;
  private final SimpleDoubleProperty y2;

  public PointPair(String name, double x1, double y1, double x2, double y2) {
    this.name = new SimpleStringProperty(name);
    this.x1 = new SimpleDoubleProperty(x1);
    this.y1 = new SimpleDoubleProperty(y1);
    this.x2 = new SimpleDoubleProperty(x2);
    this.y2 = new SimpleDoubleProperty(y2);
  }

  public PointPair setPoints(double x1, double y1, double x2, double y2) {
    this.x1.set(x1);
    this.y1.set(y1);
    this.x2.set(x2);
    this.y2.set(y1);
    return this;
  }

  public SimpleStringProperty getName() { return name; }
  public SimpleDoubleProperty getX1() { return x1; }
  public SimpleDoubleProperty getY1() { return y1; }
  public SimpleDoubleProperty getX2() { return x2; }
  public SimpleDoubleProperty getY2() { return y2; }
}
