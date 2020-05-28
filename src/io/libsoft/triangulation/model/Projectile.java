package io.libsoft.triangulation.model;

import com.sun.scenario.effect.impl.prism.PrImage;
import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;
import java.util.HashMap;
import java.util.Map;

public class Projectile implements Element {

  private Position position;
  private Vector velocity = Vector.ZERO();

  public Projectile(Vector velocity) {
    this.velocity = velocity;
  }

  public Projectile() {
  }

  @Override
  public void update() {
//    double nextX =
//    this.position =


  }
}
