package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.predictors.LinearPredictor;
import io.libsoft.triangulation.model.predictors.Predictor;
import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Model implements Runnable {

  private final double SAMPLE_RATE = 100;
  private final long SLEEP = (long) (1000 / SAMPLE_RATE);
  private final int HISTORY_SIZE = 200;
  private final double ALPHA = 1e-3;
  private final List<Element> elements;
  private final List<Predictor> predictors;
  private final List<Thread> predictorThreads;
  private final Deque<Position> targetHistory;
  private boolean running;
  private Entity entity;
  private Position target;


  public Model() {
    target = Position.at(100, -300);
    entity = new Entity();
    predictors = new LinkedList<>();
    elements = new LinkedList<>();
    targetHistory = new LinkedList<>();
    predictorThreads = new LinkedList<>();

    Predictor linearPredictor = new LinearPredictor(new Sensor(this));
    predictors.add(linearPredictor);
    elements.add(linearPredictor);
  }

  public Entity getEntity() {
    return entity;
  }

  @Override
  public void run() {
    running = true;

//    for (Predictor predictor : predictors) {
//      Thread thread = new Thread(predictor);
//      thread.start();
//      predictorThreads.add(thread);
//    }
//    while (running) {
    for (int i = 0; i < 100000; i++) {
      double xV = ALPHA * (target.getX() - entity.getPosition().getX());
      double yV = ALPHA * (target.getY() - entity.getPosition().getY());
      entity.addVelocity(Vector.from(xV, yV));
      entity.multiplyScalar(5e-3);
      entity.update();
    }


//    }
    for (Thread thread : predictorThreads) {
      thread.interrupt();
    }

  }

  public void stop() {
    running = false;
  }


  public void trigger() {
  }

  public void setTarget(double x, double y) {

    this.target = Position.at(x, y);
    System.out.println("Model.setTarget");
    System.out.println(this.target);
//    targetHistory.push(target);
//
//    if (targetHistory.size() > HISTORY_SIZE) {
//      targetHistory.removeLast();
//    }
  }

  public Deque<Position> getTargetHistory() {
    return targetHistory;
  }

  public List<Predictor> getPredictors() {
    return predictors;
  }

  public Position getEntityPosition() {
    return entity.getPosition();
  }

  public Position getTarget() {
    return target;
  }
}

