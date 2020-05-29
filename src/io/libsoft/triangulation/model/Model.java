package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.predictors.LinearPredictor;
import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.predictors.Predictor;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Model implements Runnable {

  private final double SAMPLE_RATE = 100;
  private final long SLEEP = (long) (1000 / SAMPLE_RATE);
  private final int HISTORY_SIZE = 200;
  private boolean running;
  private Position target;
  private final List<Element> elements;
  private final List<Predictor> predictors;
  private final List<Thread> predictorThreads;
  private final Deque<Position> targetHistory;

  public Model() {
    target = Position.ZERO();
    predictors = new LinkedList<>();
    elements = new LinkedList<>();
    targetHistory = new LinkedList<>();
    predictorThreads = new LinkedList<>();


    Predictor linearPredictor = new LinearPredictor(new Sensor(this));
    predictors.add(linearPredictor);
    elements.add(linearPredictor);
  }

  public List<Predictor> getPredictors() {
    return predictors;
  }

  @Override
  public void run() {
    running = true;

    for (Predictor predictor : predictors) {
      Thread thread = new Thread(predictor);
      thread.start();
      predictorThreads.add(thread);
    }

    while (running) {

    }

    for (Thread thread : predictorThreads) {
      thread.interrupt();
    }

  }

  public void stop() {
    running = false;
  }

  public Position getTarget(){
    return target;
  }

  public void setTarget(double x, double y) {
    target = Position.at(x, y);
    targetHistory.push(target);
    if (targetHistory.size() > HISTORY_SIZE){
      targetHistory.removeLast();
    }
  }

  public Deque<Position> getTargetHistory() {
    return targetHistory;
  }
}

