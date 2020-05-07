package io.libsoft.triangulation.view;

import io.libsoft.triangulation.model.Device;
import io.libsoft.triangulation.model.Space;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SpaceViewer extends Canvas {

  private Space space;
  private GraphicsContext gc;

  public SpaceViewer() {
    space = new Space();
    gc = getGraphicsContext2D();
    setWidth(1000);
    setHeight(1000);
    setOnMouseDragged(event -> {
      space.setAttractor(event.getX(), event.getY());
    });

  }


  public void draw() {
    gc.clearRect(0,0, getWidth(), getHeight());
    gc.setFill(Color.BLACK);
    gc.fillOval(space.getAttractor().getX()-5, space.getAttractor().getY()-5, 20,20);
    gc.setFill(Color.RED);
    for (Device device : space.getDevices()) {
      gc.fillOval(device.getPosition().getX(), device.getPosition().getY(), 10, 10);
    }

  }


  public void setSpace(Space space) {
    this.space = space;
  }
}
