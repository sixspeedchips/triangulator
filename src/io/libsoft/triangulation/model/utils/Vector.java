package io.libsoft.triangulation.model.utils;

public class Vector {

  private double x;
  private double y;

  private Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Vector from(double x, double y) {
    return new Vector(x, y);
  }

  public static Vector fromPolar(double theta, double r) {
    double x = r * Math.cos(theta);
    double y = r * Math.sin(theta);
    return new Vector(x, y);
  }

  public static Vector ZERO() {
    return new Vector(0, 0);
  }
  public double getTheta(){
    return Math.atan2(y,x);
  }

  public Vector add(Vector other){
    return Vector.from(this.x + other.x, this.y + other.y);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return "Vector{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
