package org.howard.edu.aos.gs;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a simulation task.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsTask {

  /**
   * Constructor.
   * @param id task id
   * @param num_bursts number of bursts in task.
   * @param cpu_burst_max max size of CPU burst.
   * @param io_burst_max max size of IO burst.
   * @param max_retry_limit max number of sequence retries.
   * @param cpu_burst_sequence integer sequence.
   * @param io_burst_sequence integer sequence.
   */
  public GsTask(int id,
      int num_bursts,
      int cpu_burst_max,
      int io_burst_max,
      int max_retry_limit,
      GsSequenceGaussian cpu_burst_sequence,
      GsSequenceGaussian io_burst_sequence) {
    
    _id = id;
    
    _num_bursts = num_bursts;
    
    _cpu_burst_max = cpu_burst_max;
    
    _io_burst_max = io_burst_max;
    
    _max_retry_limit = max_retry_limit;
    
    _cpu_burst_sequence = cpu_burst_sequence;
    
    _io_burst_sequence = io_burst_sequence;
  }
  
  /**
   * Method generates task simulation burst data based on config data.
   * @return boolean true if generate succeeds, false otherwise.
   */
  public boolean generate() {
   
    for (int i = 1; i <= _num_bursts; i++) {
      
      if((i % 2) == 0) {
  
        if (!generateCPUBurst(i)) {
          
          return false;
        }
        
      } else {
                
        if (!generateIOBurst(i)) {
          
          return false;
        }
      }      
    }    
        
    return true;
  }

  /**
   * Method generates a task simulation IO burst.
   * @param id burst number
   * @return boolean true if generate succeeds, false otherwise.
   */
  private boolean generateIOBurst(int id) {
    
    long io_burst = _io_burst_sequence.next();

    int retries = 0;
    
    while (io_burst <= 0 || io_burst > _io_burst_max) {

      if (retries++ > _max_retry_limit) {

        break;
      }
      
      _logger.debug("burst[io] " + String.valueOf(id) + " retry #" + String.valueOf(retries) + " io burst " + 
            String.valueOf(io_burst) + " max " + String.valueOf(_io_burst_max));
      
      io_burst = _io_burst_sequence.next();
    }

    if (io_burst <= 0 || io_burst > _io_burst_max) {

      _logger.error("IO burst retry limit breached with delta " + String.valueOf(io_burst));
      
      return false;
    }
    
    _bursts.add((int) io_burst);
    
    return true;
  }

  /**
   * Method generates a task simulation CPU burst.
   * @param id burst number
   * @return boolean true if generate succeeds, false otherwise.
   */
  private boolean generateCPUBurst(int id) {
    
    long cpu_burst = _cpu_burst_sequence.next();

    int retries = 0;
    
    while (cpu_burst <= 0 || cpu_burst > _cpu_burst_max) {

      if (retries++ > _max_retry_limit) {

        break;
      }
      
      _logger.debug("burst[cpu] " + String.valueOf(id) + " retry #" + String.valueOf(retries) + " cpu burst " + 
            String.valueOf(cpu_burst) + " max " + String.valueOf(_cpu_burst_max));
      
      cpu_burst = _cpu_burst_sequence.next();
    }

    if (cpu_burst <= 0 || cpu_burst > _cpu_burst_max) {

      _logger.error("CPU burst retry limit breached with delta " + String.valueOf(cpu_burst));
      
      return false;
    }
    
    _bursts.add((int) cpu_burst);
    
    return true;    
  }

  /**
   * Method generates simulation data output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  public boolean generateOutput(StringBuilder buffer) {
    
    buffer.append("{ \"task\": {");
    
    if (!generateOutputId(buffer)) {
    
      return false;
    }

    buffer.append(",");

    if (!generateOutputBursts(buffer)) {
      
      return false;
    }

    buffer.append("}}");
    
    return true;
  }

  /**
   * Method generates id output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  private boolean generateOutputId(StringBuilder buffer) {
    
    buffer.append(" \"id\": ");
        
    buffer.append(_id);
    
    return true;
  }

  /**
   * Method generates burst output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  private boolean generateOutputBursts(StringBuilder buffer) {
    
    buffer.append("\"bursts\": [ ");

    boolean first = true;
    
    for (Integer burst : _bursts) {
      
      if (!first) {
        
        buffer.append(",");
      }
      
      buffer.append(burst);
      
      first = false;
    }
    
    buffer.append("]");

    return true;
  }
  
  /**
   * Number of bursts in task.
   */
  final private int _num_bursts;
  
  /**
   * CPU burst sizes sequence gaussian sequence generator.
   */
  final private GsSequenceGaussian _cpu_burst_sequence;
  
  /**
   * IO burst sizes sequence gaussian sequence generator.
   */
  final private GsSequenceGaussian _io_burst_sequence;
  
  /**
   * Max CPU burst value.
   */
  final private int _cpu_burst_max;

  /**
   * Max CPU burst value.
   */
  final private int _io_burst_max;

  /**
   * Max retry limit for sequences.
   */
  final private int _max_retry_limit;

  /**
   * List containing bursts.
   */
  final private List<Integer> _bursts = new ArrayList<Integer>();
  
  /**
   * Task id.
   */
  final private int _id;
  
  /**
   * Local logger reference for logging operations.
   */
  final private static GsLogger _logger = new GsLogger(GsTask.class.getName());
}
