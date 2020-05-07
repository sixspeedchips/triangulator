package io.libsoft.triangulation.model.utils;

public class Position {

  private double x;
  private double y;


  private Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Position at(double x, double y){
    return new Position(x,y);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public static double slope(Position p1, Position p2){
    double theta = 0f;
    double slope = (p2.y - p1.y) / (p2.x - p1.x);
    System.out.println(slope);
    return theta;
  }

}
