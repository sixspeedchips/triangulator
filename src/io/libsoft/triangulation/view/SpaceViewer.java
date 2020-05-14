package io.libsoft.triangulation.view;

import io.libsoft.triangulation.model.Model;
import io.libsoft.triangulation.model.predictors.Prediction;
import io.libsoft.triangulation.model.utils.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class SpaceViewer extends Canvas {

  private Model space;
  private GraphicsContext gc;

  public SpaceViewer() {
    space = new Model();
    gc = getGraphicsContext2D();
    setWidth(1000);
    setHeight(1000);
    setOnMouseDragged(event -> {
      space.setTarget(event.getX(), -event.getY());
    });

    setOnMouseClicked(event -> {
      space.setTarget(event.getX(), -event.getY());
    });
  }


  public void draw() {
    gc.setFill(Color.GHOSTWHITE);
    gc.fillRect(0, 0, getWidth(), getHeight());
    gc.setLineCap(StrokeLineCap.ROUND);
    Prediction predictor = space.getPredictors().get(0);
    double x = predictor.getCurrentPosition().getX();
    double y = -predictor.getCurrentPosition().getY();

    double xa = space.getTarget().getX();
    double ya = -space.getTarget().getY();

    gc.setFill(Color.RED);
    gc.fillOval(x - 5, y - 5, 10, 10);
    Vector prediction = space.getPredictors().get(0).getPredictionVector();
    gc.setLineWidth(2);
    gc.setStroke(Color.BLACK);
//    double[] xArr = predictor.getTargetPositions().stream().map(Position::getX).mapToDouble(Double::doubleValue).toArray();
//    double[] yArr = predictor.getTargetPositions().stream().map(position -> -position.getY()).mapToDouble(Double::doubleValue)
//        .toArray();
//    gc.strokePolyline(xArr, yArr, xArr.length);

//    double[] xArrD = device.getHistory().stream().map(Position::getX).mapToDouble(Double::doubleValue).toArray();
//    double[] yArrD = device.getHistory().stream().map(position -> -position.getY()).mapToDouble(Double::doubleValue)
//        .toArray();
//    gc.strokePolyline(xArrD, yArrD, xArrD.length);

//    double[] xArA = space.getAttractorHistory().stream().map(Position::getX).mapToDouble(Double::doubleValue).toArray();
//    double[] yArrA = space.getAttractorHistory().stream().map(position -> -position.getY()).mapToDouble(Double::doubleValue)
//        .toArray();
//    gc.strokePolyline(xArA, yArrA, xArA.length);
//
//
    gc.setFill(Color.GREEN);

    gc.fillOval(xa - 4, ya - 4, 8, 8);
    if (prediction != null) {
      gc.setStroke(Color.BLUE);
//      gc.strokeLine(0,0, xa + slope.getX(), ya - slope.getY());
      gc.strokeLine(xa, ya, xa + prediction.getX(), ya - prediction.getY());
    }
  }


  public void setSpace(Model space) {
    this.space = space;
  }
}
