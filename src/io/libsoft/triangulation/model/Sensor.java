package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;

public class Sensor {

  private final Model model;

  public Sensor(Model model) {
    this.model = model;
  }

  public Position getTarget(){
    return model.getEntityPosition();
  }
}
