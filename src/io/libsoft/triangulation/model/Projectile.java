package io.libsoft.triangulation.model;

import com.sun.scenario.effect.impl.prism.PrImage;
import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;
import java.util.HashMap;
import java.util.Map;

public class Projectile implements Element {

  private Position position;
  private Vector velocity;

  public Projectile(Vector velocity) {
    this.position = Position.ZERO();
    this.velocity = velocity;
  }

  public Projectile() {
    this.position = Position.ZERO();
    this.velocity = Vector.ZERO();
  }

  @Override
  public void update() {

  }
}
