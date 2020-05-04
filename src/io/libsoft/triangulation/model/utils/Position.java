package io.libsoft.triangulation.model.utils;

public class Position {

  private double x;
  private double y;


  private Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Position of(double x, double y){
    return new Position(x,y);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
}
