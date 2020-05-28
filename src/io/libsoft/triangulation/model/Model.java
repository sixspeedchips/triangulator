package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.predictors.Prediction;
import io.libsoft.triangulation.model.predictors.PredictorFactory;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Model implements Runnable {

  private final double SAMPLE_RATE = 100;
  private final long SLEEP = (long) (1000 / SAMPLE_RATE);
  private boolean running = true;
  private Position target = Position.ZERO();
  private final List<Element> elements = new LinkedList<>();
  private final List<Prediction> predictions = new LinkedList<>();
  private final Deque<Position> targetHistory = new LinkedList<>();
  private final int HISTORY_SIZE = 200;

  public Model() {
    Prediction linearPredictor = PredictorFactory.linearPredictor().withSamples(20).withSampleRate(SAMPLE_RATE).create();
    predictions.add(linearPredictor);
    elements.add(linearPredictor);

  }

  public List<Prediction> getPredictors() {
    return predictions;
  }

  @Override
  public void run() {
    while (running) {
      for (Element element : elements) {
        element.update();
      }
//      try {
//        Thread.sleep(SLEEP);
//      } catch (InterruptedException ignored) {}
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
    for (Prediction prediction : predictions) {
      prediction.setTarget(target);
    }
    targetHistory.addFirst(this.target);
    if (targetHistory.size() > HISTORY_SIZE){
      targetHistory.removeLast();
    }
  }

  public Deque<Position> getTargetHistory() {
    return targetHistory;
  }
}

