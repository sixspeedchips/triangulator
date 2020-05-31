package io.libsoft.triangulation.view;

import io.libsoft.triangulation.model.Entity;
import io.libsoft.triangulation.model.Model;
import io.libsoft.triangulation.model.predictors.Predictor;
import io.libsoft.triangulation.model.utils.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;

public class ModelViewer extends Canvas {

  private Model model;
  private GraphicsContext gc;
  private Image target;
  private Image crosshair;
  private Image car;
  private Predictor predictor;
  private Entity entity;

  public ModelViewer() {
    model = new Model();
    gc = getGraphicsContext2D();
    setWidth(600);
    setHeight(500);
    target = new Image("file:resources/target.png");
    crosshair = new Image("file:resources/img_465471.png");
    car = new Image("file:resources/car.png");
    setOnMouseDragged(event -> {
      model.setTarget(event.getX(), -event.getY());
    });

    setOnMouseClicked(event -> {
      model.setTarget(event.getX(), -event.getY());
    });

    addEventFilter(KeyEvent.KEY_PRESSED, event ->{

    });
    setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.SPACE){
        model.trigger();
      }
    });


  }


  public void draw() {
    gc.setFill(Color.GHOSTWHITE);
    gc.fillRect(0, 0, getWidth(), getHeight());
    gc.setLineCap(StrokeLineCap.ROUND);
    predictor = model.getPredictors().get(0);
    entity = model.getEntity();
    double x = predictor.getCurrentPosition().getX();
    double y = -predictor.getCurrentPosition().getY();

    double xa = entity.getPosition().getX();
    double ya = -entity.getPosition().getY();

    double xt = model.getTarget().getX();
    double yt = -model.getTarget().getY();

    gc.setFill(Color.RED);
    gc.drawImage(crosshair, x - 5, y - 5, 10, 10);
    gc.drawImage(target, xt - 5, yt - 5, 10, 10);

    Vector prediction = model.getPredictors().get(0).getPredictionVector();
    gc.setLineWidth(2);
    gc.setStroke(Color.BLACK);
    gc.setFill(Color.GREEN);
    gc.drawImage(car, xa - 5, ya - 5, 10, 10);
    System.out.println(xa);
    if (prediction != null) {
      gc.setStroke(Color.BLUE);
      gc.strokeLine(xa, ya, xa + prediction.getX(), ya - prediction.getY());
    }
  }


  public void setModel(Model model) {
    this.model = model;
  }
}
