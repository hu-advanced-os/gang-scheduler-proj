package org.howard.edu.aos.gs;

import java.util.ArrayList;

import java.util.List;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * This class implements a set of simulation jobs.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsJobs {

  /**
   * Constructor.
   * @param config {@link GsConfig} configuration.
   */
  public GsJobs(GsConfig config) {
   
    _config = config;    
    
    _arrival_sequence = new GsSequencePoisson(_config.getSeed(), 
        _config.getArrivalRateMean());
    
    _tasks_size_sequence = new GsSequenceGaussian(_config.getSeed(), 
        _config.getNumTasksMean(), 
        _config.getNumTasksStd());
    
    _bursts_size_sequence = new GsSequenceUniform(_config.getSeed(),
        _config.getTaskSizeLower(),
        _config.getTaskSizeUpper());
    
    _cpu_burst_sequence = new GsSequenceGaussian(_config.getSeed(), 
        _config.getCPUBurstMean(), 
        _config.getCPUBurstStd());

    _io_burst_sequence = new GsSequenceGaussian(_config.getSeed(), 
        _config.getIOBurstMean(), 
        _config.getIOBurstStd());
    
    _job_type_sequence = new GsSequenceUniform(_config.getSeed(),
        0,
        _config.getJobTypeRatioInteractive() + _config.getJobTypeRatioUnattended());
  }
  
  /**
   * Method generates simulation data based on config data.
   * @return boolean true if generate succeeds, false otherwise.
   */
  public boolean generate() {
    
    for (int i = 0; i < _config.getJobCount(); i++) {

      int id = _config.getStartId() + i;
      
      int retries = 0;

      long arrival_sequence = _arrival_sequence.next();

      {
        while (_arrival_sequence.delta() > _config.getArrivalDeltaMax()) {

          if (retries++ > _config.getMaxRetryLimit()) {

            break;
          }

          _logger.debug("job " + String.valueOf(id) + " retry #" + String.valueOf(retries) + " arrival value " + 
              String.valueOf(_arrival_sequence.delta()) + " > " + String.valueOf(_config.getArrivalDeltaMax()));

          _arrival_sequence.reverse();
          
          arrival_sequence = _arrival_sequence.next();
        }

        if (_arrival_sequence.delta() > _config.getArrivalDeltaMax()) {

          _logger.error("arrival sequence retry limit breached with delta " + String.valueOf(_arrival_sequence.delta()));

          return false;
        }
      }
            
      long num_tasks = _tasks_size_sequence.next();

      {
        retries = 0;

        while (num_tasks < GsConstants._NUM_TASKS_MIN || num_tasks > _config.getMaxCPUPerJob()) {

          if (retries++ > _config.getMaxRetryLimit()) {

            break;
          }

          _logger.debug("job " + String.valueOf(id) + " retry #" + String.valueOf(retries) + " num tasks " + 
              String.valueOf(num_tasks) + " max " + String.valueOf(_config.getMaxCPUPerJob()));
          
          num_tasks = _tasks_size_sequence.next();
        }

        if (num_tasks < GsConstants._NUM_TASKS_MIN || num_tasks > _config.getMaxCPUPerJob()) {

          _logger.error("num tasks sequence retry limit breached with value " + String.valueOf(num_tasks));
          
          return false;
        }
      }

      String job_type = this.getJobType();

      int priority = -1;

      {
        if (GsConfigValues._VALUE_JOB_TYPE_INTERACTIVE.equals(job_type)) {

          priority = 1;

        } else if (GsConfigValues._VALUE_JOB_TYPE_UNATTENDED.equals(job_type)) {

          priority = 0;

        } else {

          _logger.error("job type invalid");

          return false;
        }
      }

      String processing_unit = _config.getProcessingUnit();

      GsJob job = new GsJob(id, 
          priority,
          (int) arrival_sequence,
          (int) num_tasks,
          _config.getCPUBurstMax(),
          _config.getIOBurstMax(),
          _config.getMaxRetryLimit(),
          job_type, 
          processing_unit,
          _bursts_size_sequence,
          _cpu_burst_sequence,
          _io_burst_sequence);
      
      if (!job.generate()) {
      
        return false;
      }
      
      _jobs.add(job);
    }

    return generateOutput();
  }
  
  /**
   * Method generates simulation data output.
   * @return boolean true if generateOutput succeeds, false otherwise.
   */
  public boolean generateOutput() {
    
    StringBuilder buffer = new StringBuilder();  

    buffer.append("{");

    buffer.append(" \"max-cpu-per-job\": ");
    
    buffer.append(_config.getMaxCPUPerJob());

    buffer.append(",");

    buffer.append(" \"jobs\": [");

    boolean first = true;
    
    for (GsJob job : _jobs) {
      
      if (!first) {
        
        buffer.append(",");
      }
      
      if (!job.generateOutput(buffer)) {
        
        return false;
      }
      
      first = false;
    }

    buffer.append("]}");
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    JsonElement json = null;
    
    try {
    
      json = JsonParser.parseString(buffer.toString());
    
    } catch (Exception e) {
    
      _logger.exception(e);
      
      return false;
    }
    
    _buffer.append(gson.toJson(json));

    _buffer.append(_newlline);
    
    return true;
  }
  
  /**
   * Method returns the effective job type based on config type and ratios.
   * @return String containing effective config type.
   */
  private String getJobType() {

    String job_type = _config.getJobType();

    if (GsConfigValues._VALUE_JOB_TYPE_INTERACTIVE.equals(job_type) ||
        GsConfigValues._VALUE_JOB_TYPE_UNATTENDED.equals(job_type)) {

      return job_type;
    }

    if (_job_type_sequence.next() <= _config.getJobTypeRatioInteractive()) {
    
      return GsConfigValues._VALUE_JOB_TYPE_INTERACTIVE;
    }
    
    return GsConfigValues._VALUE_JOB_TYPE_UNATTENDED;
  }
  
  /**
   * Creates a string representation of current object.
   * @return String string representation of data/ 
   */
  public String toString() {
    
    return _buffer.toString();
  }
  
  /**
   * Arrival sequence number generator object.
   */
  final private GsSequencePoisson _arrival_sequence;
  
  /**
   * Task size number generator object.
   */
  final private GsSequenceGaussian _tasks_size_sequence;
  
  /**
   * Task size number generator object.
   */
  final private GsSequenceUniform _bursts_size_sequence;

  /**
   * CPU burst number generator object.
   */
  final private GsSequenceGaussian _cpu_burst_sequence;

  /**
   * IO burst number generator object.
   */
  final private GsSequenceGaussian _io_burst_sequence;

  /**
   * Task size number generator object.
   */
  final private GsSequenceUniform _job_type_sequence;

  /**
   * Configuration object containing parameter settings.
   */
  final private GsConfig _config;

  /**
   * Newline character sequence. 
   */
  final private String _newlline = System.getProperty("line.separator");

  /**
   * List containing generated jobs.
   */
  final private List<GsJob> _jobs = new ArrayList<GsJob>();
  
  /**
   * Buffer for generated json.
   */
  final private StringBuilder _buffer = new StringBuilder();  
  
  /**
   * Local logger reference for logging operations.
   */
  final private static GsLogger _logger = new GsLogger(GsJobs.class.getName());
}

