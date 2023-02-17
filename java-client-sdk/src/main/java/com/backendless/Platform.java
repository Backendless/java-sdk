package com.backendless;

/**
 * This class is used by the CodeRunner during initialization process.
 * It set isCodeRunner field to true.
 */
class Platform
{
  private static boolean isAndroid = false;
  private static boolean isCodeRunner = false;

  static boolean isAndroid()
  {
    return isAndroid;
  }

  static boolean isCodeRunner()
  {
    return isCodeRunner;
  }
}
