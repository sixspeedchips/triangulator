package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.predictors.Prediction;
import io.libsoft.triangulation.model.predictors.PredictorFactory;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Space implements Runnable {

  private double SAMPLE_RATE = 100; // samples/sec
  private long SLEEP = (long) (1000 / SAMPLE_RATE);
  private boolean running = true;
  private Position target;
  private List<Prediction> predictions = new LinkedList<>();
  private Deque<Position> attractorHistory = new LinkedList<>();

  public Space() {
    predictions.add(PredictorFactory.linearPredictor().withSamples(20).withSampleRate(SAMPLE_RATE).create());
    target = Position.at(0,0);
  }

  public List<Prediction> getPredictors() {
    return predictions;
  }

  @Override
  public void run() {
    while (running) {
      predictions.get(0).setTarget(target);
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
    attractorHistory.addFirst(this.target);
    if (attractorHistory.size() > 200){
      attractorHistory.removeLast();
    }
  }

  public Deque<Position> getAttractorHistory() {
    return attractorHistory;
  }
}
