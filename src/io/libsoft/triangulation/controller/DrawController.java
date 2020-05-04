package io.libsoft.triangulation.controller;


import io.libsoft.triangulation.model.Space;
import io.libsoft.triangulation.view.SpaceViewer;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class DrawController {

  @FXML
  public SpaceViewer spaceViewer;

  @FXML
  public Text text;

  private boolean running;
  private GFXUpdater updater;
  private Space space;

  @FXML
  private void initialize() {
    space = new Space();
    new Thread(space).start();
    spaceViewer.setSpace(space);
    updater = new GFXUpdater();
    updater.start();

  }

  public void start() {

  }


  public void stop() {
    updater.stop();
  }

  private void updateView() {
    spaceViewer.draw();
  }


  private class GFXUpdater extends AnimationTimer {

    @Override
    public void handle(long now) {
      updateView();
    }


  }

}
