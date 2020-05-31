package io.libsoft.triangulation.controller;


import io.libsoft.triangulation.model.Model;
import io.libsoft.triangulation.view.ModelViewer;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class DrawController {

  @FXML
  public ModelViewer modelViewer;

  @FXML
  public Text text;

  @FXML
  private Button run;
  @FXML
  private Button stop;


  private boolean running;
  private GFXUpdater updater;
  private Model model;

  @FXML
  private void initialize() {

    model = new Model();
    modelViewer.setModel(model);
    updater = new GFXUpdater();
    updater.start();

  }

  public void start() {
    new Thread(model).start();
  }




  public void stop() {
  }

  private void updateView() {
    modelViewer.draw();
  }


  private class GFXUpdater extends AnimationTimer {

    @Override
    public void handle(long now) {
      updateView();
    }


  }

}
