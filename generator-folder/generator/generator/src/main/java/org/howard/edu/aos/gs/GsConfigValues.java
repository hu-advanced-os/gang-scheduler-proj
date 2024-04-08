package org.howard.edu.aos.gs;

import java.util.*;

/**
 * This encapsulates Parameter key values
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsConfigValues {
  
  /**
   * Constructor for {@link GsConfigValues}.
   */
  public GsConfigValues() {
  }

  /**
   * Method loads parameter settings from command line arguments.
   * 
   * @param args String array containing command line arguments.
   */
  void load(String[] args) {

    Map<String, String> values = new HashMap<String, String>();

    _status = GsParameterKeys.load(args, values, _errors);

    if (_status != GsConfig.VALUES_STATUS.VALUES_STATUS_OK) {
      
      return;
    }

    if (!setValues(values)) {
    
      _status = GsConfig.VALUES_STATUS.VALUES_STATUS_ERROR;
      
      return;
    }

    _status = GsConfig.VALUES_STATUS.VALUES_STATUS_OK;
  }

  /**
   * Method returns the status of {@link GsConfig} object.
   * 
   * @return {@link Enum} {@link GsConfig.VALUES_STATUS} value
   *         indicating status.
   */
  public GsConfig.VALUES_STATUS getStatus() {
    
    return _status;
  }

  /**
   * Method loads configuration from file and returns boolean indicating result of
   * loading and processing command line arguments.
   * 
   * @param values {@code Map<String, String>} containing command line name/value
   *               pairs.
   * 
   * @return boolean indicating whether load was successful.
   */
  private boolean setValues(Map<String, String> values) {

    // NOTE; this order is important.
    
    if (!setArrivalRateMean(values)         ||
        !setCPUBurstMean(values)            ||
        !setCPUBurstStd(values)             ||
        !setIOBurstMean(values)             ||
        !setIOBurstStd(values)              ||
        !setJobCount(values)                ||
        !setJobType(values)                 ||
        !setJobTypeRatioInteractive(values) ||
        !setJobTypeRatioUnattended(values)  ||
        !setMaxCPUPerJob(values)            ||
        !setNumTasksMean(values)            ||
        !setNumTasksStd(values)             ||
        !setProcessingUnit(values)          ||
        !setSeed(values)                    ||
        !setStartId(values)                 ||
        !setTaskSizeLower(values)           ||
        !setTaskSizeUpper(values)           ||
        !setDebug(values)                   ||
        !setOutput(values)) {

      return false;
    }

    Set<String> inKeys = new HashSet<String>(values.keySet());
    
    Set<String> outKeys = new HashSet<String>(Arrays.asList(GsParameterKeys._KEYS));

    inKeys.removeAll(outKeys);
    
    outKeys.removeAll(values.keySet());

    if (inKeys.isEmpty() && outKeys.isEmpty()) {

      return true;
    }

    for (String key : inKeys) {
      
      _errors.add("parameter not recognized '" + key + "'");
    }

    for (String key : outKeys) {
      
      _errors.add("parameter not set '" + key + "'");
    }

    return false;
  }

  /**
   * Method to store 'mandatory parameter missing' error.
   * 
   * @param parameterName name of parameter to report.
   */
  private void logMissingParameterError(String parameterName) {
    
    StringBuilder buffer = new StringBuilder();
    
    buffer.append("mandatory parameter -");
    buffer.append(parameterName);
    buffer.append(" missing");
    
    _errors.add(buffer.toString());
  }

  /**
   * Method sets parameter field {@link _arrival_rate_mean}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setArrivalRateMean(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_ARRIVAL_RATE_MEAN)) {

      _arrival_rate_mean = GsUtil.toDouble(values.get(GsParameterKeys._KEY_ARRIVAL_RATE_MEAN));

      if (!checkLowerBound(_arrival_rate_mean, GsParameterKeys._KEY_ARRIVAL_RATE_MEAN)) {
        
        return false;
      }

      return _arrival_rate_mean != Double.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_ARRIVAL_RATE_MEAN);

    return false;
  }

  /**
   * Method sets parameter field {@link _cpu_burst_mean}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setCPUBurstMean(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_CPU_BURST_MEAN)) {

      _cpu_burst_mean = GsUtil.toDouble(values.get(GsParameterKeys._KEY_CPU_BURST_MEAN));
      
      if (!checkLowerBound(_cpu_burst_mean, GsParameterKeys._KEY_CPU_BURST_MEAN)) {
        
        return false;
      }

      return _cpu_burst_mean != Double.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_CPU_BURST_MEAN);

    return false;
  }

  /**
   * Method sets parameter field {@link _cpu_burst_std}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setCPUBurstStd(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_CPU_BURST_STD)) {

      _cpu_burst_std = GsUtil.toDouble(values.get(GsParameterKeys._KEY_CPU_BURST_STD));

      if (!checkLowerBound(_cpu_burst_std, GsParameterKeys._KEY_CPU_BURST_STD)) {
        
        return false;
      }

      return _cpu_burst_std != Double.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_CPU_BURST_STD);

    return false;
  }
  
  //
  
  /**
   * Method sets parameter field {@link _io_burst_mean}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setIOBurstMean(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_IO_BURST_MEAN)) {

      _io_burst_mean = GsUtil.toDouble(values.get(GsParameterKeys._KEY_IO_BURST_MEAN));
      
      if (!checkLowerBound(_io_burst_mean, GsParameterKeys._KEY_IO_BURST_MEAN)) {
        
        return false;
      }

      return _io_burst_mean != Double.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_IO_BURST_MEAN);

    return false;
  }

  /**
   * Method sets parameter field {@link _io_burst_std}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setIOBurstStd(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_IO_BURST_STD)) {

      _io_burst_std = GsUtil.toDouble(values.get(GsParameterKeys._KEY_IO_BURST_STD));
      
      if (!checkLowerBound(_io_burst_std, GsParameterKeys._KEY_IO_BURST_STD)) {
        
        return false;
      }

      return _io_burst_std != Double.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_IO_BURST_STD);

    return false;
  }
  
  //
  
  /**
   * Method sets parameter field {@link _job_count}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setJobCount(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_JOB_COUNT)) {

      _job_count = GsUtil.toInteger(values.get(GsParameterKeys._KEY_JOB_COUNT));
      
      if (!checkLowerBound(_job_count, GsParameterKeys._KEY_JOB_COUNT)) {
        
        return false;
      }

      return _job_count != Integer.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_JOB_COUNT);

    return false;
  }
    
  //
  
  /**
   * Method sets parameter field {@link _job_type}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setJobType(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_JOB_TYPE)) {

      String job_type = values.get(GsParameterKeys._KEY_JOB_TYPE);
      
      if (_VALUE_JOB_TYPE_BOTH.equals(job_type)        ||
          _VALUE_JOB_TYPE_INTERACTIVE.equals(job_type) ||
          _VALUE_JOB_TYPE_UNATTENDED.equals(job_type)) {
        
        _job_type = job_type;

        return true;
      }  
    }

    logMissingParameterError(GsParameterKeys._KEY_JOB_TYPE);

    return false;
  }
  
  //
  
  /**
   * Method sets parameter field {@link _job_type_ratio_interactive}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setJobTypeRatioInteractive(Map<String, String> values) {

    String job_type = values.get(GsParameterKeys._KEY_JOB_TYPE);
    
    if (!_VALUE_JOB_TYPE_BOTH.equals(job_type)) {
      
      if (values.containsKey(GsParameterKeys._KEY_JOB_TYPE_RATIO_INTERACTIVE)) {

        StringBuilder buffer = new StringBuilder();
        
        buffer.append("parameter -");
        buffer.append(GsParameterKeys._KEY_JOB_TYPE_RATIO_INTERACTIVE);
        buffer.append("' should only be set when job type is '");
        buffer.append(_VALUE_JOB_TYPE_BOTH);
        buffer.append("'");
        
        _errors.add(buffer.toString());

        return false;
      }
      
      values.put(GsParameterKeys._KEY_JOB_TYPE_RATIO_INTERACTIVE, "");
      
      return true;
    }

    if (values.containsKey(GsParameterKeys._KEY_JOB_TYPE_RATIO_INTERACTIVE)) {

      _job_type_ratio_interactive = GsUtil.toInteger(values.get(GsParameterKeys._KEY_JOB_TYPE_RATIO_INTERACTIVE));
      
      if (!checkLowerBound(_job_type_ratio_interactive, GsParameterKeys._KEY_JOB_TYPE_RATIO_INTERACTIVE)) {
        
        return false;
      }

      return _job_type_ratio_interactive != Integer.MIN_VALUE;
    }
    
    logMissingParameterError(GsParameterKeys._KEY_JOB_TYPE_RATIO_INTERACTIVE);

    return false;
  }

  //
  
  /**
   * Method sets parameter field {@link _job_type_ratio_unattended}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setJobTypeRatioUnattended(Map<String, String> values) {

    String job_type = values.get(GsParameterKeys._KEY_JOB_TYPE);
    
    if (!_VALUE_JOB_TYPE_BOTH.equals(job_type)) {
      
      if (values.containsKey(GsParameterKeys._KEY_JOB_TYPE_RATIO_UNATTENDED)) {

        StringBuilder buffer = new StringBuilder();
        
        buffer.append("parameter -");
        buffer.append(GsParameterKeys._KEY_JOB_TYPE_RATIO_UNATTENDED);
        buffer.append("' should only be set when job type is '");
        buffer.append(_VALUE_JOB_TYPE_BOTH);
        buffer.append("'");
        
        _errors.add(buffer.toString());

        return false;
      }

      values.put(GsParameterKeys._KEY_JOB_TYPE_RATIO_UNATTENDED, "");
      
      return true;
    }

    if (values.containsKey(GsParameterKeys._KEY_JOB_TYPE_RATIO_UNATTENDED)) {

      _job_type_ratio_unattended = GsUtil.toInteger(values.get(GsParameterKeys._KEY_JOB_TYPE_RATIO_UNATTENDED));
      
      if (!checkLowerBound(_job_type_ratio_unattended, GsParameterKeys._KEY_JOB_TYPE_RATIO_UNATTENDED)) {
        
        return false;
      }
      
      return _job_type_ratio_unattended != Integer.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_JOB_TYPE_RATIO_UNATTENDED);

    return false;
  }
  
  //
  
  /**
   * Method sets parameter field {@link _max_cpu_per_job}.
   * Max CPU per job must be greater than '2'.
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setMaxCPUPerJob(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_MAX_CPU_PER_JOB)) {

      _max_cpu_per_job = GsUtil.toInteger(values.get(GsParameterKeys._KEY_MAX_CPU_PER_JOB));
      
      if (!checkLowerBound(_max_cpu_per_job, GsConstants._NUM_TASKS_MIN + 1, GsParameterKeys._KEY_MAX_CPU_PER_JOB)) {
        
        return false;
      }
            
      return _max_cpu_per_job != Integer.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_MAX_CPU_PER_JOB);

    return false;
  }
  
  //
  
  /**
   * Method sets parameter field {@link _num_tasks_mean}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setNumTasksMean(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_NUM_TASKS_MEAN)) {

      _num_tasks_mean = GsUtil.toDouble(values.get(GsParameterKeys._KEY_NUM_TASKS_MEAN));
      
      if (!checkLowerBound(_num_tasks_mean, GsConstants._NUM_TASKS_MIN + 1, GsParameterKeys._KEY_NUM_TASKS_MEAN) ||
          _num_tasks_mean > getMaxCPUPerJob()) {
        
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("parameter -");
        buffer.append(GsParameterKeys._KEY_NUM_TASKS_MEAN);
        buffer.append("' '");
        buffer.append(_num_tasks_mean);
        buffer.append("' cannot be more than field '");
        buffer.append(GsParameterKeys._KEY_MAX_CPU_PER_JOB);
        buffer.append("' '");
        buffer.append(getMaxCPUPerJob());
        buffer.append("'");
        
        _errors.add(buffer.toString());
        
        return false;
      }

      return _num_tasks_mean != Double.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_NUM_TASKS_MEAN);

    return false;
  }

  /**
   * Method sets parameter field {@link _num_tasks_std}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setNumTasksStd(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_NUM_TASKS_STD)) {

      _num_tasks_std = GsUtil.toDouble(values.get(GsParameterKeys._KEY_NUM_TASKS_STD));
      
      if (!checkLowerBound(_num_tasks_std, GsParameterKeys._KEY_NUM_TASKS_STD)) {
        
        return false;
      }

      return _num_tasks_std != Double.MIN_VALUE;
    
    } else {
      
      _num_tasks_std = GsSequence.celculateBoundedNormalStd(_num_tasks_mean, _max_cpu_per_job); 
      
      if (_num_tasks_std >= 0 &&
          _num_tasks_std <= _max_cpu_per_job) { // NYI check this bound is required.
        
        values.put(GsParameterKeys._KEY_NUM_TASKS_STD, Double.toString(_num_tasks_std));
        
        return true;
      }        
    }

    StringBuilder buffer = new StringBuilder();
    
    buffer.append("parameter -");
    buffer.append(GsParameterKeys._KEY_NUM_TASKS_STD);
    buffer.append(" could in range could not be inferred from mean value '");
    buffer.append(_num_tasks_mean);
    buffer.append("' max cpu per job '");
    buffer.append(_max_cpu_per_job);
    buffer.append("'");

    _errors.add(buffer.toString());
    
    return false;
  }

  //
  
  /**
   * Method sets parameter field {@link _output}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setOutput(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_OUTPUT)) {

      _output = values.get(GsParameterKeys._KEY_OUTPUT);

      return _output.length() > 0;
    }

    logMissingParameterError(GsParameterKeys._KEY_OUTPUT);

    return false;
  }
  
  //
  
  /**
   * Method sets parameter field {@link _processing_unit}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setProcessingUnit(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_PROCESSING_UNIT)) {

      String processing_unit = values.get(GsParameterKeys._KEY_PROCESSING_UNIT);
      
      if (_VALUE_PROCESSING_UNIT_CPU.equals(processing_unit) ||
          _VALUE_PROCESSING_UNIT_GPU.equals(processing_unit)) {
        
        _processing_unit = processing_unit;

        return true;
      }  
    }

    logMissingParameterError(GsParameterKeys._KEY_PROCESSING_UNIT);

    return false;
  }
  
  //
  
  /**
   * Method sets parameter field {@link _seed}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setSeed(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_SEED)) {

      _seed = GsUtil.toInteger(values.get(GsParameterKeys._KEY_SEED));
      
      if (!checkLowerBound(_seed, GsParameterKeys._KEY_SEED)) {
        
        return false;
      }

      return _seed != Integer.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_SEED);

    return false;
  }

  //
  
  /**
   * Method sets parameter field {@link _start_id}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setStartId(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_START_ID)) {

      _start_id = GsUtil.toInteger(values.get(GsParameterKeys._KEY_START_ID));
      
      if (!checkLowerBound(_start_id, GsParameterKeys._KEY_START_ID)) {
        
        return false;
      }

      return _start_id != Integer.MIN_VALUE;
    
    } else {
      
      _start_id = 1;
      
      values.put(GsParameterKeys._KEY_START_ID, "1");

    }
    
    return true;
  }

  //
  
  /**
   * Method sets parameter field {@link _task_size_lower}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setTaskSizeLower(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_TASK_SIZE_LOWER)) {

      _task_size_lower = GsUtil.toInteger(values.get(GsParameterKeys._KEY_TASK_SIZE_LOWER));
      
      if (!checkLowerBound(_task_size_lower, GsParameterKeys._KEY_TASK_SIZE_LOWER)) {
        
        return false;
      }

      return _task_size_lower != Integer.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_TASK_SIZE_LOWER);

    return false;
  }

  /**
   * Method sets parameter field {@link _task_size_upper}
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setTaskSizeUpper(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_TASK_SIZE_UPPER)) {

      _task_size_upper = GsUtil.toInteger(values.get(GsParameterKeys._KEY_TASK_SIZE_UPPER));
      
      if (!checkLowerBound(_task_size_upper, GsParameterKeys._KEY_TASK_SIZE_UPPER)) {
        
        return false;
      }

      return _task_size_upper != Integer.MIN_VALUE;
    }

    logMissingParameterError(GsParameterKeys._KEY_TASK_SIZE_UPPER);

    return false;
  }
    
  //
  
  /**
   * Method sets parameter field {@link _debug}.
   * 
   * @param values contains all loaded configuration parameter values.
   * @return boolean indicating success or fail.
   */
  private boolean setDebug(Map<String, String> values) {

    if (values.containsKey(GsParameterKeys._KEY_DEBUG)) {

      String value = values.get(GsParameterKeys._KEY_DEBUG);

      if (!(value == null || value.isBlank() || value.isEmpty())) {

        StringBuilder buffer = new StringBuilder();

        buffer.append("flag parameter -");
        buffer.append(GsParameterKeys._KEY_DEBUG);
        buffer.append(" should not have a value");

        _errors.add(buffer.toString());

        return false;
      }

      _debug = true;

      return true;
    
    } else {
      
      values.put(GsParameterKeys._KEY_DEBUG, "");
    }

    _debug = false;

    return true;
  }
  
  //

  /**
   * Method returns arrival mean rate.
   * @return double rate mean.
   */
  public double getArrivalRateMean() {
    
    return _arrival_rate_mean;
  }

  /**
   * Method returns CPU burst mean rate.
   * @return double CPU mean.
   */
  public double getCPUBurstMean() {
    
    return _cpu_burst_mean;
  }

  /**
   * Method returns CPU burst standard deviation.
   * @return double CPU std.
   */
  public double getCPUBurstStd() {
    
    return _cpu_burst_std;
  }

  /**
   * Method returns debug.
   * 
   * @return boolean indicating whether debug mode is on or not.
   */
  public boolean getDebug() {
    return _debug;
  }

  /**
   * Method returns IO burst mean rate.
   * @return double IO mean.
  */
  public double getIOBurstMean() {
    
    return _io_burst_mean;
  }

  /**
   * Method returns IO burst standard deviation.
   * @return double IO std.
   */
  public double getIOBurstStd() {
    
    return _io_burst_std;
  }

  /**
   * Method returns job count.
   * @return int count.
   */
  public int getJobCount() {
    
    return _job_count;
  }

  /**
   * Method returns job type.
   * @return String job type.
   */
  public String getJobType() {
    
    return _job_type;
  }

  /**
   * Method returns the job type ratio interactive.
   * @return int ratio interactive.
   */
  public int getJobTypeRatioInteractive() {
  
    return _job_type_ratio_interactive;
  }

  /**
   * Method returns the job type ratio unattended.
   * @return int ratio unattended.
   */
  public int getJobTypeRatioUnattended() {
    
    return _job_type_ratio_unattended;
  }

  /**
   * Method returns max CPU per job.
   * @return int max CPU per job value.
   */
  public int getMaxCPUPerJob() {
    
    return _max_cpu_per_job;
  }

  /**
   * Method returns number of tasks mean.
   * @return double number of tasks mean.
   */
  public double getNumTasksMean() {
    
    return _num_tasks_mean;
  }

  /**
   * Method returns number of tasks standard deviation.
   * @return double number of tasks std.
   */
  public double getNumTasksStd() {
    
    return _num_tasks_std;
  }

  /**
   * Method returns output filename.
   * @return String output filename.
   */
  public String getOutput() {
    
    return _output;
  }

  /**
   * Method returns processing unit.
   * @return String processing unit.
   */
  public String getProcessingUnit() {
    
    return _processing_unit;
  }

  /**
   * Method returns distribution seed.
   * @return int distribution seed.
   */
  public int getSeed() {
    
    return _seed;
  }

  /**
   * Method returns start id.
   * @return int start id.
   */
  public int getStartId() {
    
    return _start_id;
  }

  /**
   * Method returns size of task lower.
   * @return double task size lower.
   */
  public int getTaskSizeLower() {
    
    return _task_size_lower;
  }

  /**
   * Method returns size of task upper.
   * @return double task size upper.
   */
  public int getTaskSizeUpper() {
    
    return _task_size_upper;
  }

  /**
   * Method checks whether a value is above lower bound value.
   * @param value value to be checked.
   * @param field field name for reporting.
   * @return boolean true if value is above bound, false otherwise.
   */
  private boolean checkLowerBound(double value, String field) {

    return checkLowerBound(value, 0, field);
  }
  
  /**
   * Method checks whether a value is above lower bound value.
   * @param value value to be checked.
   * @param bound lower bound for check.
   * @param field field name for reporting.
   * @return boolean true if value is above bound, false otherwise.
   */
  private boolean checkLowerBound(double value, double bound, String field) {
    
    if (value < bound) {
      
      StringBuilder buffer = new StringBuilder();
      
      buffer.append("mandatory parameter -");
      buffer.append(field);
      buffer.append(" is too small - found '");
      buffer.append(value);
      buffer.append("'");
      
      _errors.add(buffer.toString());
      
      return false;
    }

    return true;
  }
  
  /**
   * Processing status.
   */
  private GsConfig.VALUES_STATUS _status = GsConfig.VALUES_STATUS.VALUES_STATUS_ERROR;

  /**
   * {@code List<String>} containing a list of errors encountered during parameter
   * processing.
   */
  protected List<String> _errors = new ArrayList<String>();

  /**
   * Arrival mean rate.
   */
  private double _arrival_rate_mean = Double.MIN_VALUE;

  /**
   * CPU burst mean rate.
   */
  private double _cpu_burst_mean = Double.MIN_VALUE;

  /**
   * CPU burst standard deviation.
   */
  private double _cpu_burst_std = Double.MIN_VALUE;

  /**
   * Configured flag for additional debug output.
   */
  private boolean _debug = false;

  /**
   * Configured output filename.
   */
  private String _output;

  /**
   * IO burst mean rate.
   */
  private double _io_burst_mean = Double.MIN_VALUE;

  /**
   * IO burst standard deviation.
   */
  private double _io_burst_std = Double.MIN_VALUE;

  /**
   * Job count.
   */
  private int _job_count = Integer.MIN_VALUE;

  /**
   * Job type.
   */
  private String _job_type = "";

  /**
   * Number of tasks mean.
   */
  private int _job_type_ratio_interactive = Integer.MIN_VALUE;

  /**
   * Number of tasks standard deviation.
   */
  private int _job_type_ratio_unattended = Integer.MIN_VALUE;

  /**
   * Max CPU per job.
   */
  private int _max_cpu_per_job = Integer.MIN_VALUE;

  /**
   * Number of tasks mean.
   */
  private double _num_tasks_mean = Double.MIN_VALUE;

  /**
   * Number of tasks standard deviation.
   */
  private double _num_tasks_std = Double.MIN_VALUE;

  /**
   * Processing unit.
   */
  private String _processing_unit = "";

  /**
   * Distribution seed.
   */
  private int _seed = Integer.MIN_VALUE;

  /**
   * Start id.
   */
  private int _start_id = Integer.MIN_VALUE;

  /**
   * Size of task lower bound.
   */
  private int _task_size_lower = Integer.MIN_VALUE;

  /**
   * Size of task upper bound.
   */
  private int _task_size_upper = Integer.MIN_VALUE;
  
  /**
   * Job type interactive.
   */
  final public static String _VALUE_JOB_TYPE_INTERACTIVE = "interactive";

  /**
   * Job type unattended.
   */
  final public static String _VALUE_JOB_TYPE_UNATTENDED = "unattended";

  /**
   * Job type unattended.
   */
  final public static String _VALUE_JOB_TYPE_BOTH = "both";

  /**
   * Processing unit CPU interactive.
   */
  final public static String _VALUE_PROCESSING_UNIT_CPU = "CPU";

  /**
   * Processing unit GPU interactive.
   */
  final public static String _VALUE_PROCESSING_UNIT_GPU = "GPU";

}
