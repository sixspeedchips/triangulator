package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import java.util.ArrayList;
import java.util.List;

public class Device implements Runnable {

  private List<Sensor> sensors = new ArrayList<>();

  private Position position;

  private Device(Position position) {
    this.position = position;
  }

  static Device create(int x, int y) {
    return new Device(Position.of(x, y));
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  @Override
  public void run() {

  }

  public Position getPosition() {
    return position;
  }
}
