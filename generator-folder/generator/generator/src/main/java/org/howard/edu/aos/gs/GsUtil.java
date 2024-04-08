package org.howard.edu.aos.gs;

import java.util.regex.*;

/**
 * This class contains utility functions.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsUtil {
  
  /**
   * Constructor. Does nothing.
   */
  private GsUtil() {
    
  }

  /**
   * Method to determine if a string is a valid positive integer.
   * @param s String value to be tested.
   * @return true if positive integer, false otherwise.
   */
  public static boolean isInteger(String s) {
    
    if (s == null) {
      
      return false;
    }
    
    if (s.equals("0")) {
    
      return true;
    }
    
    return _CONSTANT_POSITIVE_INT_REGEXP_PATTERN.matcher(s).matches();
  }

  /**
   * Method to determine if a string is a valid positive integer.
   * @param s String value to be tested.
   * @return true if positive double, false otherwise.
   */
  public static boolean isDouble(String s) {
    
    double result = Double.MIN_VALUE;

    try {

      result = Double.parseDouble(s);
    
      if (result < 0) {
        
        return false;
      }
      
      return true;
      
    } catch (Exception e) {

    }
    
    return false;
  }

  /**
   * Method to convert string to a positive integer.
   * @param s String to be converted.
   * @return converted integer value if success {@see java.lang.Integer.MIN_VALUE} otherwise. 
   */
  public static int toInteger(String s) {

    int result = Integer.MIN_VALUE;

    try {

      if (isInteger(s)) {

        result = Integer.parseInt(s);
      }
    
      if (result < 0) {
        
        return Integer.MIN_VALUE;
      }
      
    } catch (Exception e) {

    }
    
    return result;
  }

  /**
   * Method to convert string to a positive double.
   * @param s String to be converted.
   * @return converted double value if success {@see java.lang.Double.MIN_VALUE} otherwise. 
   */
  public static double toDouble(String s) {

    double result = Double.MIN_VALUE;

    try {

      if (isDouble(s)) {

        result = Double.parseDouble(s);
      }

      if (result < 0) {
        
        return Double.MIN_VALUE;
      }
      
    } catch (NumberFormatException e) {

    }
    
    return result;
  }

  /**
   * Regular expression for positive integer.
   */
  final private static Pattern _CONSTANT_POSITIVE_INT_REGEXP_PATTERN = Pattern.compile("^[1-9]([0-9])*$");
}
