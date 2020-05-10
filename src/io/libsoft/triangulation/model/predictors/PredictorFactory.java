package io.libsoft.triangulation.model.predictors;

import io.libsoft.triangulation.model.predictors.LinearPredictor.Builder;

public class PredictorFactory {


  public static LinearPredictor.Builder linearPredictor() {
    return new Builder();
  }

}
