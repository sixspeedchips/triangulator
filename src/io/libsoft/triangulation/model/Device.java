package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import java.util.ArrayList;
import java.util.List;

public class Device implements Runnable{

  private List<Sensor> sensors = new ArrayList<>();

  private Position position;

  public Device(Position position) {
    this.position = position;
  }


  @Override
  public void run() {

  }


  public Position getPosition() {
    return position;
  }
}
