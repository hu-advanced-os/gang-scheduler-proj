package org.howard.edu.aos.gs;

import java.util.*;

/**
 * This encapsulates Parameter key values
 * 
 * @author cc
 * @version %I%, %G%
 * @since 0.1
 */
public class GsParameterKeys {

  /**
   * Constructor for {@link GsParameterKeys} is private and not meant
   * to be used.
   */
  private GsParameterKeys() {
  }

  /**
   * Method returns boolean indicating argument transformation succeeded or not.
   * Values in command line arguments 'args' variable will be transformed into
   * name/value pairs and deposited in results parameter.
   * 
   * @param args   String array containing command line arguments.
   * @param values Array for return of results.
   * @param errors Array for errors encountered during parameter loading.
   * @return {@link GsConfig.VALUES_STATUS} enum designating status of
   *         load operation.
   */
  public static GsConfig.VALUES_STATUS load(String[] args, Map<String, String> values,
      List<String> errors) {

    String name = null;

    for (int i = 0; i < args.length; i++) {

      String token = args[i].trim();

      if (token.compareToIgnoreCase("-h") == 0) {

        values.clear();
        
        errors.clear();

        return GsConfig.VALUES_STATUS.VALUES_STATUS_HELP;
      }

      if (name == null || (!token.isEmpty() && token.charAt(0) == '-')) {

        token = token.toLowerCase();

        if (token.length() < 2) {

          errors.add("empty value at position " + Integer.toString(i) + " '" + args[i] + "'");

          return GsConfig.VALUES_STATUS.VALUES_STATUS_ERROR;
        }

        if (token.charAt(0) != '-') {

          errors.add("ill-formed value at position " + Integer.toString(i) + " '" + args[i] + "'");

          return GsConfig.VALUES_STATUS.VALUES_STATUS_ERROR;
        }

        name = token.substring(1);

        if (values.containsKey(name)) {

          errors.add("duplicate value at position " + Integer.toString(i) + " '" + args[i] + "'");

          return GsConfig.VALUES_STATUS.VALUES_STATUS_ERROR;
        }

        if (!isValidParameterNameToken(name, errors)) {

          errors.add("invalid name at position " + Integer.toString(i) + " '" + args[i] + "'");

          return GsConfig.VALUES_STATUS.VALUES_STATUS_ERROR;
        }

        values.put(name, "");

      } else {

        if (!isValidParameterValueToken(token, errors)) {

          errors.add("invalid name at position " + Integer.toString(i) + " '" + args[i] + "'");

          return GsConfig.VALUES_STATUS.VALUES_STATUS_ERROR;
        }

        values.put(name, token);

        name = null;
      }
    }

    return GsConfig.VALUES_STATUS.VALUES_STATUS_OK;
  }

  /**
   * Method returns a boolean indicating whether the parameter token is a valid
   * parameter name token.
   *
   * @param token  the String token value to be checked.
   * @param errors any errors are written to this variable.
   * @return boolean indicating whether parameter token is a valid input value or
   *         not.
   */
  private static boolean isValidParameterNameToken(String token, List<String> errors) {

    for (char c : token.toCharArray()) {

      if (!(Character.isLetterOrDigit(c) || c == '-' || c == '.')) {

        errors.add("invalid character '" + c + "' found in token '" + token + "'");

        return false;

      }
    }

    return true;
  }

  /**
   * Method returns a boolean indicating whether the parameter token is a valid
   * parameter value token.
   *
   * @param token  the String token value to be checked.
   * @param errors any errors are written to this variable.
   * @return boolean indicating whether parameter token is a valid input value or
   *         not.
   */
  private static boolean isValidParameterValueToken(String token, List<String> errors) {

    char[] chars = token.toCharArray();

    for (int i = 0; i < chars.length; i++) {

      int ascii = (int) chars[i];

      if (!(Character.isAlphabetic(ascii) || (ascii >= 32 && ascii <= 123) || ascii == 125 || ascii == 9)) {

        StringBuilder buffer = new StringBuilder();

        buffer.append("invalid character found in token at index '");
        buffer.append(i);
        buffer.append("'");

        if (i > 0) {
          buffer.append(" after value '");
          buffer.append(chars[i - 1]);
          buffer.append("'");
        }

        errors.add(buffer.toString());

        return false;
      }
    }

    return true;
  }

  /**
   * Parameter constant '{@value _KEY_ARRIVAL_RATE_MEAN}'.
   */
  final public static String _KEY_ARRIVAL_RATE_MEAN = "arrival-rate-mean";

  /**
   * Parameter constant '{@value _KEY_CPU_BURST_MEAN}'.
   */
  final public static String _KEY_CPU_BURST_MEAN = "cpu-burst-mean";

  /**
   * Parameter constant '{@value _KEY_CPU_BURST_STD}'.
   */
  final public static String _KEY_CPU_BURST_STD = "cpu-burst-std";

  /**
   * Parameter constant '{@value _KEY_IO_BURST_MEAN}'.
   */
  final public static String _KEY_IO_BURST_MEAN = "io-burst-mean";

  /**
   * Parameter constant '{@value _KEY_IO_BURST_STD}'.
   */
  final public static String _KEY_IO_BURST_STD = "io-burst-std";

  /**
   * Parameter constant '{@value _KEY_JOB_COUNT}'.
   */
  final public static String _KEY_JOB_COUNT = "job-count";

  /**
   * Parameter constant '{@value _KEY_JOB_TYPE}'.
   */
  final public static String _KEY_JOB_TYPE = "job-type";

  /**
   * Parameter constant '{@value _KEY_JOB_TYPE_RATIO_INTERACTIVE}'.
   */
  final public static String _KEY_JOB_TYPE_RATIO_INTERACTIVE = "job-type-ratio-interactive";

  /**
   * Parameter constant '{@value _KEY_JOB_TYPE_RATIO_UNATTENDED}'.
   */
  final public static String _KEY_JOB_TYPE_RATIO_UNATTENDED = "job-type-ratio-unattended";

  /**
   * Parameter constant '{@value _KEY_MAX_CPU_PER_JOB}'.
   */
  final public static String _KEY_MAX_CPU_PER_JOB = "max-cpu-per-job";

  /**
   * Parameter constant '{@value _KEY_NUM_TASKS_MEAN}'.
   */
  final public static String _KEY_NUM_TASKS_MEAN = "num-tasks-mean";

  /**
   * Parameter constant '{@value _KEY_NUM_TASKS_STD}'.
   */
  final public static String _KEY_NUM_TASKS_STD = "num-tasks-std";

  /**
   * Parameter constant '{@value _KEY_PROCESSING_UNIT}'.
   */
  final public static String _KEY_PROCESSING_UNIT = "processing-unit";

  /**
   * File Parameter constant '{@value _KEY_SEED}'.
   */
  final public static String _KEY_SEED = "seed";

  /**
   * Parameter constant '{@value _KEY_TASK_SIZE_LOWER}'.
   */
  final public static String _KEY_TASK_SIZE_LOWER = "task-size-lower";

  /**
   * Parameter constant '{@value _KEY_TASK_SIZE_UPPER}'.
   */
  final public static String _KEY_TASK_SIZE_UPPER = "task-size-upper";

  /**
   * Parameter constant '{@value _KEY_DEBUG}'.
   */
  final public static String _KEY_DEBUG = "debug";

  /**
   * Parameter constant '{@value _KEY_START_ID}'.
   */
  final public static String _KEY_START_ID = "start-id";

  /**
   * Parameter constant '{@value _KEY_OUTPUT}'.
   */
  final public static String _KEY_OUTPUT = "output";

  /**
   * Parameter array containing all parameter constants.
   */
  final public static String[] _KEYS = new String[] { _KEY_ARRIVAL_RATE_MEAN, 
      _KEY_CPU_BURST_MEAN, 
      _KEY_CPU_BURST_STD, 
      _KEY_IO_BURST_MEAN, 
      _KEY_IO_BURST_STD, 
      _KEY_JOB_COUNT, 
      _KEY_JOB_TYPE,
      _KEY_JOB_TYPE_RATIO_INTERACTIVE,
      _KEY_JOB_TYPE_RATIO_UNATTENDED,
      _KEY_MAX_CPU_PER_JOB, 
      _KEY_NUM_TASKS_MEAN, 
      _KEY_NUM_TASKS_STD, 
      _KEY_PROCESSING_UNIT,
      _KEY_SEED, 
      _KEY_START_ID,
      _KEY_TASK_SIZE_LOWER, 
      _KEY_TASK_SIZE_UPPER,
      _KEY_DEBUG,
      _KEY_OUTPUT };
}
