package shapes;

public class EllipseCenter extends EllipseCorner {
  @Override
  public void setCoords(double x1, double y1, double x2, double y2) {
    super.setCoords(2 * x1 - x2, 2 * y1 - y2, x2, y2);
  }
}