package org.howard.edu.aos.gs;

import java.util.ArrayList;

import java.util.List;

/**
 * This class implements a set of simulation tasks.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsTasks {

  /**
   * Constructor.
   * @param num_tasks number of tasks in this job (gang).
   * @param bursts_size_sequence integer sequence.
   * @param cpu_burst_sequence integer sequence.
   * @param io_burst_sequence integer sequence.
   */
  public GsTasks(int num_tasks, 
      GsSequenceUniform bursts_size_sequence,
      GsSequenceGaussian cpu_burst_sequence,
      GsSequenceGaussian io_burst_sequence) {
   
    _num_tasks = num_tasks;
    
    _bursts_size_sequence = bursts_size_sequence;
    
    _cpu_burst_sequence = cpu_burst_sequence;
    
    _io_burst_sequence = io_burst_sequence;    
  }
  
  /**
   * Method generates simulation data based on config data.
   * @return boolean true if generate succeeds, false otherwise.
   */
  public boolean generate() {
    
    for (int i = 1; i <= _num_tasks; i++) {
      
      long size = _bursts_size_sequence.next();
      
      while (size == 0) {
        
        size = _bursts_size_sequence.next();
      }

      if (size > Integer.MAX_VALUE) {
        
        _logger.error("task size set to max int");

        size = Integer.MAX_VALUE;
      }
      
      GsTask task = new GsTask(i, 
          (int) size,
          _cpu_burst_sequence,
          _io_burst_sequence);

      if (!task.generate()) {
        
        return false;
      }
      
      _tasks.add(task);
    }

    return generateOutput();
  }
  
  /**
   * Method generates simulation data output.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  private boolean generateOutput() {
    
    _buffer.append("\"tasks\": [");

    boolean first = true;
    
    for (GsTask task : _tasks) {
      
      if (!first) {
        
        _buffer.append(",");
      }
      
      if (!task.generateOutput(_buffer)) {
        
        return false;
      }
      
      first = false;
    }

    _buffer.append("]");

    _buffer.append(_newlline);
    
    return true;
  }
  
  /**
   * Creates a string representation of current object.
   * @return String string representation of data/ 
   */
  public String toString() {
    
    return _buffer.toString();
  }
  
  /**
   * Bursts size sequence uniform sequence generator.
   */
  final private GsSequenceUniform _bursts_size_sequence;
  
  /**
   * CPU burst sizes sequence gaussian sequence generator.
   */
  final private GsSequenceGaussian _cpu_burst_sequence;
  
  /**
   * IO burst sizes sequence gaussian sequence generator.
   */
  final private GsSequenceGaussian _io_burst_sequence;

  /**
   * Number of tasks in job.
   */
  final private int _num_tasks;

  /**
   * Newline character sequence. 
   */
  final private String _newlline = System.getProperty("line.separator");

  /**
   * List containing generated tasks.
   */
  final private List<GsTask> _tasks = new ArrayList<GsTask>();
  
  /**
   * Buffer for generated json.
   */
  final private StringBuilder _buffer = new StringBuilder();    
  
  /**
   * Local logger reference for logging operations.
   */
  final private static GsLogger _logger = new GsLogger(GsTasks.class.getName());
}

