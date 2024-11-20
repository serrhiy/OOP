package settings;

public class Fill {
  private static Fill instance = new Fill();
  private boolean fill = false;

  public boolean getFill() {
    return fill;
  }

  public void setFill(final boolean flag) {
    fill = flag;
  }

  public static Fill getInstance() {
    return instance;
  }
}
