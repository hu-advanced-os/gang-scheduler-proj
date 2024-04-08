package org.howard.edu.aos.gs;

import java.util.Random;

/**
 * This class implements the abstract base class of a statistical sequence of integers.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public abstract class GsSequence {
  
  /**
   * Constructor.
   * @param seed sequence seed value.
   */
  public GsSequence(int seed) {

    _random = new Random(seed);
  }
  
  /**
   * Method return standard deviation within a bound based on provided mean value.
   * @param mean mean value of normal distribution.
   * @param boundary upper bound of result (for scaling).
   * @return double calculated standard deviation.
   */
  public static double celculateBoundedNormalStd(double mean, int boundary) { // NOTE: femi

    double limit = Math.log(boundary);
    
    double sum_of_sq_diffs_n = 0.0;

    double sum_of_sq_diffs_p = 0.0;

    for(int i = 1; i < limit; i++) {

      double value = i - mean;
      
      if (value < 0) {

        sum_of_sq_diffs_n += Math.sqrt(Math.abs(value));

      } else {
        
        sum_of_sq_diffs_p += Math.sqrt(value);
      }      
    }

    double sum_of_net_sq_diffs = Math.abs(sum_of_sq_diffs_p + sum_of_sq_diffs_n);

    return Math.sqrt(sum_of_net_sq_diffs / limit);
  }
  
  /**
   * Method to get the next integer in the sequence.
   * @return int  next integer in the sequence.
   */
  abstract public long next();
  
  /**
   * Object for generating random sequences following different distributions.
   */
  final protected Random _random;
}
