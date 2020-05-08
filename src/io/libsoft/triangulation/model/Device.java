package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Device implements Runnable {

  private static final int LIMIT = 20;
  private List<Sensor> sensors = new ArrayList<>();

  private Position position;
  private Vector velocity;
  private ConcurrentLinkedQueue<Position> history = new ConcurrentLinkedQueue<>();
  private Deque<Position> tPositions = new LinkedList<>();
  private Vector prediction;


  public Deque<Position> getTPositions() {
    return tPositions;
  }

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
    Position attractor = Position.at(prediction.getX(), prediction.getY());
    double xHatNew = 2e-2 * (attractor.getX() - position.getX());
    double yHatNew = 2e-2 * (attractor.getY() - position.getY());
    velocity = Vector.from(xHatNew, yHatNew);
    double newX = position.getX() + velocity.getX();
    double newY = position.getY() + velocity.getY();
    System.out.println(position);
    position = Position.at(newX, newY);
  }

  private void makePrediction() {
    double sumX = 0.0;
    double sumX2 = 0.0;
    double sumY = 0.0;
    for (Position position : tPositions) {
      sumX += position.getX();
      sumX2 += position.getX() * position.getX();
      sumY += position.getY();
    }
    double xBar = sumX / tPositions.size();
    double yBar = sumY / tPositions.size();

    double XXbar = 0.0;
    double YYbar = 0.0;
    double XYbar = 0.0;

    for (Position position : tPositions) {
      XXbar += Math.pow(position.getX() - xBar, 2);
      YYbar += Math.pow(position.getY() - yBar, 2);
      XYbar += (position.getX() - xBar) * (position.getY() - yBar);
    }

    double theta = -Math.atan2(XYbar / XXbar * Math.signum(tPositions.peekLast().getX() - tPositions.peekFirst().getX())
        , Math.signum(tPositions.peekFirst().getX() - tPositions.peekLast().getX()));
    double r = Math.sqrt(Math.pow(tPositions.peekFirst().getX() - tPositions.peekLast().getX(), 2)
        + Math.pow(tPositions.peekFirst().getY() - tPositions.peekLast().getY(),2));
    double speed = r / 1000 * tPositions.size();
    prediction = Vector.fromPolar(theta, r);
  }

  @Override
  public void run() {

  }

  public Vector getPrediction() {
    return prediction;
  }

  public void setPosition(Position position) {
    if (history.size() > 100) {
      history.add(position);
    }
    history.add(position);
    this.position = position;
  }

  public void addAttractor(Position attractor) {
    tPositions.push(attractor);
    if (tPositions.size() > LIMIT) {
      tPositions.removeLast();
    }
  }




  public Position getPosition() {
    return position;
  }
}
