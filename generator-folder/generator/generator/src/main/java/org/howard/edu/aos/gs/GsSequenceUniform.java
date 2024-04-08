package org.howard.edu.aos.gs;

/**
 * This class implements a uniform sequence of integers.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsSequenceUniform extends GsSequence {

  /**
   * Constructor.
   * @param seed sequence seed value.
   * @param lower lower bound for distribution.
   * @param upper upper bound for distribution.
   */
  public GsSequenceUniform(int seed, int lower, int upper) {
    
    super(seed);
    
    if (upper <= lower) {
      
      throw new IllegalArgumentException("upper must be greater than lower.");
    }
    
    _lower = lower;
    
    _upper = upper;
  }
  
  /**
   * Method returns next in uniform sequence.
   * return long return next long in sequence.
   */
  public long next() {
    
    return nextUniform();
  }
  
  /**
   * Method returns next value from uniform sequence.
   * @return next long from sequence.
   */
  public long nextUniform() {

    long random = _random.nextLong(_upper - _lower);
    
    random += _lower;
    
    return random;
  }
  
  /**
   * Lower bound for uniform distribution.
   */
  final private int _lower;

  /**
   * Upper bound for uniform distribution.
   */
  final private int _upper;
}
