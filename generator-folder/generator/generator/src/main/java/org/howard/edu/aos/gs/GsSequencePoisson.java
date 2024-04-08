package org.howard.edu.aos.gs;

/**
 * This class implements a poisson sequence of integers.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsSequencePoisson extends GsSequence {

  /**
   * Constructor.
   * @param seed sequence seed value.
   * @param mean mean value for poisson distribution.
   */
  public GsSequencePoisson(int seed, double mean) {

    super(seed);
    
    _mean = mean;
  }

  /**
   * Method to get the next integer in the sequence.
   * @return long  next integer in the sequence.
   */
  public long next() {
   
    return nextPoisson();
  }  
  
  /**
   * Method to get the next integer in the sequence.
   * @return long  next integer in the sequence.
   */
  private long nextPoisson() {
    
    // SEE knuth algorithm https://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables
    
    double L = Math.exp(-_mean);
    
    int k = 0;
    
    double p = 1.0;
    
    do {
        
      p = p * _random.nextDouble();
        
      k++;
        
    } while (p > L);
      
    _last += (k - 1);
    
    return _last;
  }  

  /**
   * Mean value in distribution.
   */
  final private double _mean;
  
  /**
   * The last value returned by next.
   */
  private long _last = 0;
}
