package org.howard.edu.aos.gs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class implements an output file.
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsFile {

  /**
   * Constructor.
   * @param filename name of file to write to.
   */
  public GsFile(String filename) {

    _filename = filename;

    _file = new File(_filename);
    
    try {
      
      _file.createNewFile();
    
    } catch (IOException e) {
    
      _logger.exception(e);
      
      return;
    }
    
    if (!_file.exists() || !_file.canWrite()) {
      
      _logger.error("filename '",
          filename,
          "' not writable");

      return;
    }
    
    _valid = true;
  }
  
  /**
   * Method returns valid status.
   * 
   * @return boolean true if valid, false otherwise.
   */
  public boolean getValid() {
    
    return _valid;
  }
  
  /**
   * Method to write data to configured file.
   * 
   * @param data String data to be written to file.
   * @return boolean true of success, false otherwise.
   */
  public boolean write(String data) {

    if (!_valid) {
    
      return false;
    }
    
    try {

      boolean append = _append;
      
      _append = true;
      
      FileWriter fw = new FileWriter(_file.getAbsoluteFile(), append);

      BufferedWriter bw = new BufferedWriter(fw);
      
      PrintWriter out = new PrintWriter(bw);
      
      out.print(data);
      
      out.flush();

      out.close();
      
      return true;
      
    } catch (IOException e) {

      _logger.exception(e);
    
      _valid = false;
      
      return false;
    }
  }

  /**
   * File for output.
   */
  private boolean _append = false;

  /**
   * File for output.
   */
  final private File _file;
 
  /**
   * 
   * Flag indicating whether file is valid.
   */
  private boolean _valid = false;
  
  /**
   * Filename 
   */ 
  final private String _filename;
  
  /**
   * Local logger reference for logging operations.
   */
  final private static GsLogger _logger = new GsLogger(GsFile.class.getName());
}
