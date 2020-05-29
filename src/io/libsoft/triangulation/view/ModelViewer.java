package io.libsoft.triangulation.view;

import io.libsoft.triangulation.model.Model;
import io.libsoft.triangulation.model.predictors.Predictor;
import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class ModelViewer extends Canvas {

  private Model model;
  private GraphicsContext gc;

  public ModelViewer() {
    model = new Model();
    gc = getGraphicsContext2D();
    setWidth(600);
    setHeight(500);
    setOnMouseDragged(event -> {
      model.setTarget(event.getX(), -event.getY());
    });

//    setOnMouseClicked(event -> {
//      model.setTarget(event.getX(), -event.getY());
//    });
  }


  public void draw() {
    gc.setFill(Color.GHOSTWHITE);
    gc.fillRect(0, 0, getWidth(), getHeight());
    gc.setLineCap(StrokeLineCap.ROUND);
    Predictor predictor = model.getPredictors().get(0);
    double x = predictor.getCurrentPosition().getX();
    double y = -predictor.getCurrentPosition().getY();
//
    double xa = model.getTarget().getX();
    double ya = -model.getTarget().getY();

    gc.setFill(Color.RED);
    gc.fillOval(x - 5, y - 5, 10, 10);
    Vector prediction = model.getPredictors().get(0).getPredictionVector();
    gc.setLineWidth(2);
    gc.setStroke(Color.BLACK);
//    double[] xArr = predictor.getTargetPositions().stream().map(Position::getX).mapToDouble(Double::doubleValue).toArray();
//    double[] yArr = predictor.getTargetPositions().stream().map(position -> -position.getY()).mapToDouble(Double::doubleValue)
//        .toArray();
//    gc.strokePolyline(xArr, yArr, xArr.length);
//
//    double[] xArrD = predictor.getHistory().stream().map(Position::getX).mapToDouble(Double::doubleValue).toArray();
//    double[] yArrD = predictor.getHistory().stream().map(position -> -position.getY()).mapToDouble(Double::doubleValue)
//        .toArray();
//    gc.strokePolyline(xArrD, yArrD, xArrD.length);

//    double[] xArA = model.getTargetHistory().stream().map(Position::getX).mapToDouble(Double::doubleValue).toArray();
//    double[] yArrA = model.getTargetHistory().stream().map(position -> -position.getY()).mapToDouble(Double::doubleValue)
//        .toArray();
//    gc.strokePolyline(xArA, yArrA, xArA.length);

//
    gc.setFill(Color.GREEN);

    gc.fillOval(xa - 4, ya - 4, 8, 8);
    if (prediction != null) {
      gc.setStroke(Color.BLUE);
//      gc.strokeLine(0,0, xa + slope.getX(), ya - slope.getY());
      gc.strokeLine(xa, ya, xa + prediction.getX(), ya - prediction.getY());
    }
  }


  public void setModel(Model model) {
    this.model = model;
  }
}
