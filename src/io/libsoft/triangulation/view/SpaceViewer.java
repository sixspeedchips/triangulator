package io.libsoft.triangulation.view;

import io.libsoft.triangulation.model.Device;
import io.libsoft.triangulation.model.Space;
import io.libsoft.triangulation.model.utils.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class SpaceViewer extends Canvas {

  private Space space;
  private GraphicsContext gc;

  public SpaceViewer() {
    space = new Space();
    gc = getGraphicsContext2D();
    setWidth(1000);
    setHeight(1000);
    setOnMouseDragged(event -> {
      space.setAttractor(event.getX(), -event.getY());
//      System.out.println(-event.getY());
    });
    setOnMouseClicked(event -> {
//      System.out.println(event.getY());
    });
  }


  public void draw() {
    gc.setFill(Color.GHOSTWHITE);
    gc.fillRect(0, 0, getWidth(), getHeight());
    gc.setLineCap(StrokeLineCap.ROUND);
    Device device = space.getDevices().get(0);
    double x = device.getPosition().getX();
    double y = -device.getPosition().getY();

    double xa = space.getAttractor().getX();
    double ya = -space.getAttractor().getY();

    gc.setFill(Color.RED);
    gc.fillOval(x-5, y-5 , 10, 10);
    Vector slope = space.getDevices().get(0).getPrediction();
    gc.setLineWidth(3);
    gc.setStroke(Color.BLACK);
    gc.strokeLine(0, 0, x, y);
    if (!Double.isNaN(slope.getX())) {
      gc.setStroke(Color.BLUE);
      gc.strokeLine(0,0, xa + slope.getX(), ya - slope.getY());
      gc.strokeLine(x,y, xa + slope.getX(), ya - slope.getY());
    }
    gc.setFill(Color.GREEN);
    gc.fillOval(xa-4, ya-4, 8,8);
  }


  public void setSpace(Space space) {
    this.space = space;
  }
}
