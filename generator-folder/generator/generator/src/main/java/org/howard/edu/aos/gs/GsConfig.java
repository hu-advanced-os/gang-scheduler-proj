package org.howard.edu.aos.gs;

import java.util.*;

/**
 * This class manages program command line parameters and configuration values
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsConfig extends GsConfigValues {

  /**
   * Enum VALUES_STATUS is used to indicate the status of the value object during
   * processing.
   */
  static public enum VALUES_STATUS {
    /**
     * Status value {@link VALUES_STATUS_OK} means 'Ok'
     */
    VALUES_STATUS_OK,
    /**
     * Status value {@link VALUES_STATUS_HELP} means 'Help'
     */
    VALUES_STATUS_HELP,
    /**
     * Status value {@link VALUES_STATUS_ERROR} means 'Error'
     */
    VALUES_STATUS_ERROR
  };

  /**
   * Constructor for {@link GsConfig}.
   * 
   * @param args command line array containing parameter values.
   */
  public GsConfig(String[] args) {

    super();

    super.load(args);

    if (getStatus() == GsConfig.VALUES_STATUS.VALUES_STATUS_OK) {
      _isValid = true;
    }

  }

  /**
   * Method displays table showing configured values.
   */
  public void doDump() {
    _logger.info("configuration:");
    _logger.info("-arrival-delta-max:          " + getArrivalDeltaMax());
    _logger.info("-arrival-rate-mean:          " + getArrivalRateMean());
    _logger.info("-cpu-burst-max:              " + getCPUBurstMax());
    _logger.info("-cpu-burst-mean:             " + getCPUBurstMean());
    _logger.info("-cpu-burst-std:              " + getCPUBurstStd());
    _logger.info("-io-burst-max:               " + getIOBurstMax());
    _logger.info("-io-burst-mean:              " + getIOBurstMean());
    _logger.info("-io-burst-std:               " + getIOBurstStd());
    _logger.info("-job-count:                  " + getJobCount());
    _logger.info("-job-type :                  " + getJobType());
    
    if (_VALUE_JOB_TYPE_BOTH.equals(getJobType())) {
      
      _logger.info("-job-type-ratio-interactive: " + getJobTypeRatioInteractive());
      _logger.info("-job-type-ratio-unattended:  " + getJobTypeRatioUnattended());
    }

    _logger.info("-max-retry-limit:            " + getMaxRetryLimit());
    _logger.info("-max-cpu-per-job:            " + getMaxCPUPerJob());
    _logger.info("-num-tasks-mean:             " + getNumTasksMean());
    _logger.info("-num-tasks-std:              " + getNumTasksStd());
    _logger.info("-processing-unit:            " + getProcessingUnit());
    _logger.info("-seed:                       " + getSeed());
    _logger.info("-start-id:                   " + getStartId());
    _logger.info("-task-size-lower:            " + getTaskSizeLower());
    _logger.info("-task-size-upper:            " + getTaskSizeUpper());
    _logger.info("-output:                     " + getOutput());
    _logger.info("-debug:                      " + (getDebug() ? "true" : "false"));    
  }

  /**
   * Method displays 'usage' help information showing command-line options.
   */
  public void doInfo() {
    _logger.info("distributions:");
    _logger.info("");
    _logger.info("arrival rate: poisson  distribution. using -arrival-rate-mean. gap bounded above by -arrival-delta-max.");
    _logger.info("cpu burst:    gaussian distribution. using -cpu-burst-mean, -cpu-burst-std. bounded above by -cpu-burst-max.");
    _logger.info("io burst:     gaussian distribution. using -io-burst-mean, -io-burst-std. bounded above by -io-burst-max.");
    _logger.info("num tasks:    gaussian distribution. using -num-tasks-mean and optionally -num-tasks-std. bounded above by -max-cpu-per-job.");
    _logger.info("job type:     uniform  distribution. using -job-type-ratio-interactive and -job-type-ratio-unattended."); 
    _logger.info("task size:    uniform  distribution. using -task-size-lower and -task-size-upper."); 
  }
    
  /**
   * Method displays 'usage' help information showing command-line options.
   */
  public void doHelp() {
    _logger.info("usage:");
    _logger.info("");

    _logger.info("generator -arrival-rate-mean <double> -cpu-burst-mean <double> -cpu-burst-std <double> -io-burst-mean <double> -io-burst-std <double> -job-count <integer> ");
    
    StringBuilder buffer = new StringBuilder();
    
    buffer.append("  -job-type (" + GsConfigValues._VALUE_JOB_TYPE_INTERACTIVE + "|" + GsConfigValues._VALUE_JOB_TYPE_UNATTENDED + "|" + GsConfigValues._VALUE_JOB_TYPE_BOTH + ") ");
    buffer.append("[ -job-type-ratio-interactive <integer> ] [ -job-type-ratio-unattended <integer> ] -max-cpu-per-job <integer> -num-tasks-mean <double>  ");

    _logger.info(buffer.toString());

    _logger.info("  -num-tasks-std <double> -seed <integer> -task-size-lower <int> -task-size-upper <integer> [-h] [-debug]");
    _logger.info("");

    _logger.info("____________________________________________________________________________________________________________________________________________________________________________________");
    _logger.info("|=================================================================================================================================================================================|");
    _logger.info("| PARAMETER NAME.              | IS MANDATORY | TYPE    | DESCRIPTION.                                                                                                            |");
    _logger.info("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
    _logger.info("| -arrival-delta-max           | optional     | integer | maximum inter poission gap-period. defaults to Integer.MAX_VALUE.                                                       |");
    _logger.info("| -arrival-rate-mean           | mandatory    | double  | mean arrival rate per period for poisson process generator.                                                             |");
    _logger.info("| -cpu-burst-max               | optional     | integer | max cpu burst size. defaults to Integer.MAX_VALUE.                                                                      |");
    _logger.info("| -cpu-burst-mean              | mandatory    | double  | mean cpu burst size.                                                                                                    |");
    _logger.info("| -cpu-burst-std               | mandatory    | double  | standard deviation of cpu bursts size.                                                                                  |");
    _logger.info("| -io-burst-max                | optional     | integer | max io burst size. defaults to Integer.MAX_VALUE.                                                                       |");
    _logger.info("| -io-burst-mean               | mandatory    | double  | mean io burst size.                                                                                                     |");
    _logger.info("| -io-burst-std                | mandatory    | double  | standaerd deviation of io burst size.                                                                                   |");
    _logger.info("| -job-count                   | mandatory    | integer | total number of jobs.                                                                                                   |");
    
    buffer = new StringBuilder();
    
    buffer.append("| -job-type                    | mandatory    | string  | '" + 
        GsConfigValues._VALUE_JOB_TYPE_INTERACTIVE + 
        "' or '" + 
        GsConfigValues._VALUE_JOB_TYPE_UNATTENDED + 
        "', '" + 
        GsConfigValues._VALUE_JOB_TYPE_BOTH + "'.");
    
    buffer.append(" set -job-type-ratio-interactive -job-type-ratio-unattended when job type 'both'. |");
    
    _logger.info(buffer.toString());

    _logger.info("| -job-type-ratio-interactive  | optional     | integer | sets interactive ratio (vs. unattended). only allowed when job-type is 'both'.                                          |");   
    _logger.info("| -job-type-ratio-unattended   | optional     | integer | sets unattended ratio (vs. interactive). only allowed when job-type is 'both'.                                          |");
    
    _logger.info("| -max-retry-limit             | optional     | integer | max retry if sequence number breaches max bound for num tasks, arrival, IO and CPU bursts. defaults to " + 
        GsConstants._DEFAULT_LIMIT_RETRY + 
        ".             |");
    
    _logger.info("| -max-cpu-per-job             | mandatory    | integer | maximum cpu/gpu allowed for any one job. must be a minimum of '3' as lowest cpu-per-job is fixed at '2'.                |");
    _logger.info("| -num-tasks-mean              | mandatory    | double  | mean number of tasks per job. must be less than 'max-cpu-per-job'                                                       |");
    _logger.info("| -num-tasks-std               | optional     | double  | standard deviation of number of tasks per job. default is calculated to keep values between '2' and 'max-cpu-per-job'.  |");

    buffer = new StringBuilder();
    buffer.append("| -processing-unit             | mandatory    | string  | '" + GsConfigValues._VALUE_PROCESSING_UNIT_CPU + "' or '" + GsConfigValues._VALUE_PROCESSING_UNIT_GPU + "'.");
    buffer.append("                                                                                                         |");
    _logger.info(buffer.toString());
    
    _logger.info("| -seed                        | mandatory    | integer | seed for distribution generators.                                                                                       |");
    _logger.info("| -start-id                    | optional     | integer | start id for jobs (inclusive). defaults to '1'.                                                                         |");
    _logger.info("| -task-size-lower             | mandatory    | integer | lower limit of task size i.e. how many bursts.                                                                          |");
    _logger.info("| -task-size-upper             | mandatory    | integer | upper limit of task size i.e. how many bursts.                                                                          |");
    _logger.info("| -h:                          | optional     | flag    | display help information.                                                                                               |");
    _logger.info("| -outpu                       | mandatory    | string  | output file name.                                                                                                       |");
    _logger.info("| -debug                       | optional     | flag    | toggle flag to adjust debug mode. default false.                                                                        |");
    _logger.info("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
    _logger.info("| generator -arrival-rate-mean 1.0 -cpu-burst-mean 10.0 -cpu-burst-std 3.0 -io-burst-mean 10.0 -io-burst-std 3.0 -job-count 10                                                    |");
    _logger.info("|  -job-type interactive -max-cpu-per-job 10 -num-tasks-mean 10.0 -num-tasks-std 3.0 -seed 122006 -task-size-lower 10.0 -task-size-upper 3.0 -output /tmp/gs.out                  |");    
    _logger.info("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
    _logger.info("| generator -arrival-rate-mean 1.0 -cpu-burst-mean 10.0 -cpu-burst-std 3.0 -io-burst-mean 10.0 -io-burst-std 3.0 -job-count 10 -job-type both -job-type-ratio-interactive 4       |");
    _logger.info("|  -job-type-ratio-unattended 1 -max-cpu-per-job 10 -num-tasks-mean 10.0 -num-tasks-std 3.0 -seed 122006 -task-size-lower 10.0 -task-size-upper 3.0 -output /tmp/gs.out           |");
    _logger.info("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
    _logger.info("| generator -h                                                                                                                                                                    |");
    _logger.info("|=================================================================================================================================================================================|");
    _logger.info("");

    _logger.info("NOTE: all numeric values must be positive.");
    _logger.info("");

    doInfo();
    _logger.info("");
    _logger.info(".");
  }

  /**
   * Method returns {@code List<String>} containing configuration errors.
   * 
   * @return {@code List<String>} containing configuration errors.
   */
  public List<String> getErrors() {
    return _errors;
  }

  /**
   * Method returns boolean indicating whether command line parameters include
   * help '-h' flag.
   * 
   * @return boolean indicating whether help command line option is present.
   */
  public boolean getIsHelp() {
    return getStatus() == GsConfig.VALUES_STATUS.VALUES_STATUS_HELP;
  }

  /**
   * Method returns {@link #_isValid} value.
   * 
   * @return boolean indicating whether configuration is valid or not.
   */
  public boolean isValid() {
    return _isValid;
  }

  /**
   * Configured flag indicating whether {@link GsConfig} is valid or
   * not.
   */
  private boolean _isValid = false;
  
  /**
   * Local logger reference for logging operations.
   */
  final private static GsLogger _logger = new GsLogger(GsConfig.class.getName());
}
