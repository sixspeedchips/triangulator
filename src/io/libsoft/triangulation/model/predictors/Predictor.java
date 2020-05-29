package io.libsoft.triangulation.model.predictors;

import io.libsoft.triangulation.model.Element;
import io.libsoft.triangulation.model.utils.Position;
import io.libsoft.triangulation.model.utils.Vector;

public interface Predictor extends Element, Runnable{

  void setTarget(Position position);

  Position getCurrentPosition();

  Vector getPredictionVector();

}
