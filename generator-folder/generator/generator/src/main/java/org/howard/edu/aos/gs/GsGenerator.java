package org.howard.edu.aos.gs;

/**
 * Program to generate simulation data.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsGenerator {

  /**
   * Main method.
   * @param args String array containing program arguments.
   */
  public static void main(String[] args) {
    
    GsConfig config = new GsConfig(args);
    
    if (config.getIsHelp()) {
      config.doHelp();
      
      return;
    }

    if (!config.isValid()) {

      GsGenerator.logger().error("invalid parameters ",
          config.getErrors().size() +
          " errors");
      
      for (String error : config.getErrors()) {
        
        GsGenerator.logger().error("invalid parameters ",
            error);
      
      }

      return;
    }

    config.doDump();

    GsGenerator generator = new GsGenerator(config);

    if (!generator.getValid()) {
      
      GsGenerator.logger().error("generator failed to initialise");
    
      return;    
    }
    
    GsGenerator.logger().info("start.");
    
    if (generator.generate()) {
      
      GsGenerator.logger().info("writing output to file '" + config.getOutput() + "'.");
      
      if (!generator.output()) {
        
        _logger.error("could not output generated data");
      }
    
    } else {
      
      _logger.error("could not generate data");
    }

    config.doInfo();
    
    GsGenerator.logger().info("stop.");
  }
  
  /**
   * Constructor {@link GsGenerator}.
   * 
   * @param config {@link GsConfig} configuration.
   */
  public GsGenerator(GsConfig config) {

    _config = config;

    if (_config == null || !_config.isValid()) {
      
      _logger.error("config not value");
    
      _jobs = null;
      
      _file = null;
      
      return;
    }

    _jobs = new GsJobs(config);
    
    _file = new GsFile(config.getOutput());

    _valid = true;
  }

  /**
   * Method generates simulation data based on config data.
   * @return boolean true if generate succeeds, false otherwise.
   */
  public boolean generate() {
  
    return _jobs.generate();
  }

  /**
   * Method returns boolean indicating whether {@link GsGenerator} object is in
   * a valid state.
   * 
   * @return boolean where true indicates {@link GsGenerator} object is valid,
   *         false otherwise.
   */
  public boolean getValid() {
    
    return _valid;
  }
  
  /**
   * Method returns reference to logger object.
   * @return GsGeneratorLogger logger.
   */
  static public GsLogger logger() {
    return _logger;
  }
  
  /**
   * Method outputs generated data.
   * @return boolean true if generate succeeds, false otherwise.
   */
  public boolean output() {
  
    _file.write(_jobs.toString());
    
    return true;
  }

  /**
   * Configuration object containing parameter settings.
   */
  final private GsConfig _config;

  /**
   * Boolean indicating whether this {@link GsGenerator} object is in a valid
   * state.
   */
  private boolean _valid = false;

  /**
   * Jobs list. 
   */
  final private GsJobs _jobs;

  /**
   * Output file 
   */
  final private GsFile _file;
  
  /**
   * Local logger reference for logging operations.
   */
  final private static GsLogger _logger = new GsLogger(GsGenerator.class.getName());  
}

