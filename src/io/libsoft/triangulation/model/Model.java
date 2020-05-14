package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.predictors.Prediction;
import io.libsoft.triangulation.model.predictors.PredictorFactory;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Model implements Runnable {

  private double SAMPLE_RATE = 100;
  private long SLEEP = (long) (1000 / SAMPLE_RATE);
  private boolean running = true;
  private Position target = Position.ZERO();
  private List<Element> elements = new LinkedList<>();
  private List<Prediction> predictions = new LinkedList<>();
  private Deque<Position> targetHistory = new LinkedList<>();

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
      try {
        Thread.sleep(SLEEP);
      } catch (InterruptedException ignored) {}
    }

  }

  public void stop() {
    running = false;
  }

  public Position getTarget(){
    return target;
  }

  public void setTarget(double x, double y) {
    this.target = Position.at(x, y);
    targetHistory.addFirst(this.target);
    if (targetHistory.size() > 200){
      targetHistory.removeLast();
    }
  }

  public Deque<Position> getTargetHistory() {
    return targetHistory;
  }
}
