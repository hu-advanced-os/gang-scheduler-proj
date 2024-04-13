package org.howard.edu.aos.gs;

/**
 * Class for constant values.
 */
abstract public class GsConstants {

  /**
   * Constructor.
   */
  private GsConstants() {
    
  }
  
  /**
   * The minimum number of tasks per job.
   */
  final static public int _NUM_TASKS_MIN = 2;

  /**
   * The minimum number of bursts per task.
   */
  final static public int _NUM_BURSTS_MIN = 1;

  /**
   * The minimum number of CPU bursts.
   */
  final static public int _CPU_BURSTS_MIN = 1;

  /**
   * The minimum number of IO bursts.
   */
  final static public int _IO_BURSTS_MIN = 1;
  
  /**
   * The default sequence number retries.
   */
  final static public int _DEFAULT_LIMIT_RETRY = 100;

  /**
   * The minimum sequence number retries.
   */
  final static public int _MIN_LIMIT_RETRY = 1;

  /**
   * The maximum sequence number retries.
   */
  final static public int _MAX_LIMIT_RETRY = 1000000;
}
