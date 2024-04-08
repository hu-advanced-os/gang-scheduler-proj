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
   * @param cpu_burst_sequence integer sequence.
   * @param io_burst_sequence integer sequence.
   */
  public GsTask(int id,
      int num_bursts,
      GsSequenceGaussian cpu_burst_sequence,
      GsSequenceGaussian io_burst_sequence) {
    
    _id = id;
    
    _num_bursts = num_bursts;
    
    _cpu_burst_sequence = cpu_burst_sequence;
    
    _io_burst_sequence = io_burst_sequence;
  }
  
  
  /**
   * Method generates task simulation burst data based on config data.
   * @return boolean true if generate succeeds, false otherwise.
   */
  public boolean generate() {
   
    for (int i = 1; i <= _num_bursts; i++) {
      
      long burst = 0;
      
      if((i % 2) == 0) {
  
        burst = _cpu_burst_sequence.next();
        
        while (burst == 0) {
          
          burst = _cpu_burst_sequence.next();
        }
  
      } else {
                
        burst = _io_burst_sequence.next();

        while (burst == 0) {
          
          burst = _io_burst_sequence.next();
        }
      }
      
      if (burst > Integer.MAX_VALUE) {
        
        _logger.error("burst set to max int");
        
        burst = Integer.MAX_VALUE;
      }
             
      _bursts.add((int) burst);
    }    
        
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
