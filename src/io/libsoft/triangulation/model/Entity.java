package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;

public class Entity {

  private Position position;
  private Vector velocity;

  public Entity() {
    this.position = Position.ZERO();
    this.velocity = Vector.ZERO();
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Vector getVelocity() {
    return velocity;
  }

  public void setVelocity(Vector velocity) {
    this.velocity = velocity;
  }

  public void multiplyScalar(double v) {
    this.velocity = Vector.from(velocity.getX() * v, velocity.getY() * v);
  }

  public void addVelocity(Vector velocity) {
    double xHat = this.velocity.getX() + velocity.getX();
    double yHat = this.velocity.getY() + velocity.getY();
    this.velocity = Vector.from(xHat, yHat);
  }

  public void update() {
    this.position = Position.at(position.getX() + velocity.getX(), position.getY() + velocity.getY());
  }

  @Override
  public String toString() {
    return "Entity{" +
        "position=" + position +
        ", velocity=" + velocity +
        '}';
  }


}
