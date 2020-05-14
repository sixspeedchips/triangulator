package io.libsoft.triangulation.model.predictors;

import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;
import java.util.Deque;
import java.util.LinkedList;

public class LinearPredictor implements Prediction {

  private int samples;
  private double sampleRate;
  private Position position = Position.ZERO();
  private Vector velocity = Vector.ZERO();
  private Position target = Position.ZERO();
  private double ALPHA = 1;


  private Deque<Position> history = new LinkedList<>();
  private Deque<Position> targetPositions = new LinkedList<>();
  private Vector prediction;

  private LinearPredictor(Builder builder) {
    samples = builder.samples;
    sampleRate = builder.sampleRate;
  }

  @Override
  public void update() {

    if (prediction != null) {
      target = Position.at(prediction.getX() + targetPositions.peekFirst().getX(),
          prediction.getY() + targetPositions.peekFirst().getY());
    } else {
      target = targetPositions.peekFirst();
    }
    double xHatNew = ALPHA * (target.getX() - position.getX());
    double yHatNew = ALPHA * (target.getY() - position.getY());
    velocity = Vector.from(xHatNew, yHatNew);
    double newX = position.getX() + velocity.getX();
    double newY = position.getY() + velocity.getY();
    position = Position.at(newX, newY);
  }

  private void makePrediction() {

    double sumX = 0.0;
    double sumX2 = 0.0;
    double sumY = 0.0;
    for (Position position : targetPositions) {
      sumX += position.getX();
      sumX2 += position.getX() * position.getX();
      sumY += position.getY();
    }
    double xBar = sumX / targetPositions.size();
    double yBar = sumY / targetPositions.size();

    double XXbar = 0.0;
    double YYbar = 0.0;
    double XYbar = 0.0;

    for (Position position : targetPositions) {
      XXbar += Math.pow(position.getX() - xBar, 2);
      YYbar += Math.pow(position.getY() - yBar, 2);
      XYbar += (position.getX() - xBar) * (position.getY() - yBar);
    }
    double slope = XYbar / XXbar;

    double theta = -Math.atan2(slope *
            Math.signum(targetPositions.peekLast().getX() - targetPositions.peekFirst().getX()),
        Math.signum(targetPositions.peekFirst().getX() - targetPositions.peekLast().getX()));

    double r = Math.sqrt(Math.pow(targetPositions.peekFirst().getX() - targetPositions.peekLast().getX(), 2) +
        Math.pow(targetPositions.peekFirst().getY() - targetPositions.peekLast().getY(), 2));
    r /= 5;
//    r = targetPositions.size();
//    r = sampleRate;
//    System.out.println(r);
    if (Double.isNaN(theta)) {
      prediction = null;
    } else {
      prediction = Vector.fromPolar(theta, r);
    }
  }

  public void setTarget(Position target) {
    targetPositions.push(target);
    if (targetPositions.size() > samples) {
      targetPositions.removeLast();
    }
    history.add(position);
    if (history.size() > 200) {
      history.remove();
    }
    makePrediction();
  }

  @Override
  public Vector getPredictionVector() {
    return prediction;
  }


  public Deque<Position> getTargetPositions() {
    return targetPositions;
  }

  public Vector getPrediction() {
    return prediction;
  }

  public Position getCurrentPosition() {
    return position;
  }


  public static class Builder {

    private int samples = 2;
    private double sampleRate = 1;

    public Builder withSamples(int val) {
      samples = val;
      return this;
    }

    public LinearPredictor create() {
      return new LinearPredictor(this);
    }

    public Builder withSampleRate(double sampleRate) {
      this.sampleRate = sampleRate;
      return this;
    }
  }
}
