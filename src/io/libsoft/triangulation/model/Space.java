package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import java.util.LinkedList;
import java.util.List;

public class Space implements Runnable {

  List<Device> devices = new LinkedList<>();
  private boolean running;
  private Position attractor;

  public Space() {
    devices.add(Device.create(100, 100));
    attractor = Position.at(100,100);
  }

  public List<Device> getDevices() {
    return devices;
  }

  @Override
  public void run() {

    running = true;

    while (running) {
      devices.get(0).addAttractor(attractor);
      devices.get(0).updatePosition();
      try {
        Thread.sleep(10);
      } catch (InterruptedException ignored) {}
    }

  }

  public void stop() {
    running = false;
  }

  public Position getAttractor(){
    return attractor;
  }

  public void setAttractor(double x, double y) {
    this.attractor = Position.at(x, y);
  }
}
