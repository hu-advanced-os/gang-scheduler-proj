package org.howard.edu.aos.gs;

/**
 * This class implements a gaussian sequence of integers.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsSequenceGaussian extends GsSequence {

  /**
   * Constructor.
   * @param seed sequence seed value.
   * @param mean mean.
   * @param std standard deviation.
   */
  public GsSequenceGaussian(int seed, double mean, double std) {
    
    super(seed);
    
    _mean = mean;

    _std = std;
  }

  /**
   * Method returns next in gaussian sequence.
   * @return long next value in gaussian sequence.
   */
  public long next() {
    
    return nextGaussian();
  }

  /**
   * Method returns next in gaussian sequence.
   * @return long next value in gaussian sequence.
   */
  public long nextGaussian() {
    
    double result = _random.nextGaussian() * _std + _mean;
    
    if (result < 0) {
      
      result = _mean + (_mean - result);
    }
    
    return (long) result;
  }

  /**
   * Mean.
   */
  final private double _mean;

  /**
   * Standard deviation.
   */
  final private double _std;
}
