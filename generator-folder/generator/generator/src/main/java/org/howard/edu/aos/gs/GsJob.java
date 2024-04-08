package org.howard.edu.aos.gs;

/**
 * This class implements a simulation job.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsJob {

  /**
   * Constructor.
   * @param id job id
   * @param priority job priority.
   * @param arrival_time arrival time.
   * @param num_tasks task size.
   * @param type job type.
   * @param processing_unit processing unit value,
   * @param bursts_size_sequence integer sequence.
   * @param cpu_burst_sequence integer sequence.
   * @param io_burst_sequence integer sequence.
   */
  public GsJob(int id, 
      int priority,
      int arrival_time, 
      int num_tasks, 
      String type,
      String processing_unit,
      GsSequenceUniform bursts_size_sequence,
      GsSequenceGaussian cpu_burst_sequence,
      GsSequenceGaussian io_burst_sequence) {
    
    _id = id;
    
    _arrival_time = arrival_time;
   
    _type = type;

    _processing_unit = processing_unit;

    _priority = priority; 

    _num_tasks = num_tasks;

    _tasks = new GsTasks(_num_tasks, 
        bursts_size_sequence,
        cpu_burst_sequence,
        io_burst_sequence); 
  }
  
  /**
   * Method generates task simulation data based on config data.
   * @return boolean true if generate succeeds, false otherwise.
   */
  public boolean generate() {
   
    return _tasks.generate();
  }
  
  /**
   * Method generates simulation data output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  public boolean generateOutput(StringBuilder buffer) {
    
    buffer.append("{ \"job\": {");
    
    if (!generateOutputId(buffer)) {
    
      return false;
    }

    buffer.append(",");
    
    if (!generateOutputArrivalTime(buffer)) {
      
      return false;
    }

    buffer.append(",");

    if (!generateOutputType(buffer)) {
      
      return false;
    }

    buffer.append(",");

    if (!generateeOutputProcessingUnit(buffer)) {
      
      return false;
    }

    buffer.append(",");

    if (!generateOutputPriority(buffer)) {
      
      return false;
    }

    buffer.append(",");

    if (!generateeOutputNumTasks(buffer)) {
      
      return false;
    }

    buffer.append(",");

    buffer.append(_tasks.toString());

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
   * Method generates arrival time output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  private boolean generateOutputArrivalTime(StringBuilder buffer) {
    
    buffer.append(" \"arrival-time\": ");
        
    buffer.append(_arrival_time);

    return true;
  }
  
  /**
   * Method generates type output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  private boolean generateOutputType(StringBuilder buffer) {
    
    buffer.append(" \"type\": \"");
    
    buffer.append(_type);
    
    buffer.append("\"");

    return true;
  }

  /**
   * Method generates processing unit output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  private boolean generateeOutputProcessingUnit(StringBuilder buffer) {
    
    buffer.append(" \"processing-unit\": \"");
    
    buffer.append(_processing_unit);
    
    buffer.append("\"");

    return true;
  }
  
  /**
   * Method generates priority output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  private boolean generateOutputPriority(StringBuilder buffer) {
    
    buffer.append(" \"priority\": ");
    
    buffer.append(_priority);
    
    return true;
  }
  
  /**
   * Method generates num tasks output.
   * @param buffer buffer object to write string to.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  private boolean generateeOutputNumTasks(StringBuilder buffer) {
    
    buffer.append(" \"num-tasks\": ");
    
    buffer.append(_num_tasks);
    
    return true;
  }
  
  /**
   * Task list.
   */
  final private GsTasks _tasks;
  
  /**
   * Job id.
   */
  final private int _id;
  
  /**
   * Arrival time.
   */
  final private int _arrival_time;

  /**
   * Job type.
   */
  final private String _type;

  /**
   * Processing unit.
   */
  final private String _processing_unit;

  /**
   * Priority.
   */
  final private int _priority;

  /**
   * Number of tasks.
   */
  final private int _num_tasks;
}
