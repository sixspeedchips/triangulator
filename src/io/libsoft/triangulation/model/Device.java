package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Device implements Runnable {

  private static final int LIMIT = 2;
  private List<Sensor> sensors = new ArrayList<>();

  private Position position;
  private Vector velocity;
  private ConcurrentLinkedQueue<Position> history = new ConcurrentLinkedQueue<>();
  private Deque<Position> targetPositions = new LinkedList<>();
  private Position prediction;

  private Device(Position position, Vector velocity) {
    this.position = position;
    this.velocity = velocity;
  }

  private Device(Position position) {
    this.position = position;
    this.velocity = Vector.ZERO();
  }

  static Device create(double x, double y) {
    return new Device(Position.at(x, y));
  }

  static Device create(double x, double y, double xVelocity, double yVelocity) {
    return new Device(Position.at(x, y), Vector.from(xVelocity, yVelocity));
  }


  public void updatePosition() {
    makePrediction();
    Position attractor = targetPositions.peekLast();
    double xHatNew = 2e-2 * (attractor.getX() - position.getX());
    double yHatNew = 2e-2 * (attractor.getY() - position.getY());
    velocity = Vector.from(xHatNew, yHatNew);
    double newX = position.getX() + velocity.getX();
    double newY = position.getY() + velocity.getY();
    position = Position.at(newX, newY);
  }

  private void makePrediction() {
    Position.slope(targetPositions.getFirst(), targetPositions.getLast());
  }

  @Override
  public void run() {

  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    if (history.size() > 100) {
      history.add(position);
    }
    history.add(position);
    this.position = position;
  }

  public void addAttractor(Position attractor) {
    targetPositions.offerLast(attractor);
    if (targetPositions.size() > LIMIT) {
      targetPositions.pollFirst();
    }
  }




  private double distance(Position other) {
    return Math.sqrt(Math.pow((other.getX() - position.getX()), 2) + Math.pow((other.getY() - position.getY()), 2));
  }
}
