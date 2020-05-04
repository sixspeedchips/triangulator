package io.libsoft.triangulation.model;

import io.libsoft.triangulation.model.utils.Position;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class Space implements Runnable {

  List<Device> devices = new LinkedList<>();
  private boolean running;


  public Space() {
    devices.add(Device.create(100, 100));

  }

  public List<Device> getDevices() {
    return devices;
  }

  @Override
  public void run() {
    // here we will modify the position of our device, let's try a circle
    running = true;
    double x, y;
    double t = 0;
    while (running) {
//      circle
//      x = 100 * Math.cos(t) + 250;
//      y = 100 * Math.sin(t) + 250;
      x = 100*Math.cos(t) - 100*Math.cos(250/100d * t) + 250;
      y = 100*Math.sin(t) - 100*Math.sin(250/100d * t) + 250;

      Device d = devices.get(0);
      d.setPosition(Position.of(x, y));
      t += .001;

      try {
        Thread.sleep(1);
      } catch (InterruptedException ignored) {}
    }

  }

  public void stop() {
    running = false;
  }


}
