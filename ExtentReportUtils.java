package selautomation.common;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.IExtentReportUtils;

/** The Class ExtentReportUtils. */
public class ExtentReportUtils implements IExtentReportUtils {

  public ExtentReportUtils() {
    Logger.log("From Report utils : " + Thread.currentThread().getId() + "\n" + Thread.currentThread().getName());
  }
  
  /**
   * Starts the test in extent report and assigns it a category.
   *  @param testClassName the Test class name
   * @param testName the Test method name
   */
  public void startExtentTest(String testClassName, String testName) {
    Logger.log("******************** Started Test " + testClassName + "_" + testName + " *********************",true);
    /*SeleniumTestBase.logger = SeleniumTestBase.extent.startTest(testClassName + "_" + testName);
    SeleniumTestBase.logger.assignCategory(testClassName);*/
    //SeleniumTestBase.logger = ExtentTestManager.startTest(testClassName + "_" + testName);
    ExtentTest test = ExtentTestManager.startTest(testClassName + "_" + testName);
    test.assignCategory(testClassName);
    //ExtentTestManager.getTest().assignCategory(testClassName);
  }

  /**
   * Starts the test in extent report and assigns it a category.
   * @param testClassName the Test class name
   * @param testName the Test method name
   * @param testDesc Description of the test case
   */
  public ExtentTest startExtentChildTest(String testClassName, String testName, String testDesc) {
    Logger.log("****************** Started Child Test " + testClassName + "_" + testName + " *******************",true);
    /*SeleniumTestBase.logger = SeleniumTestBase.extent.startTest(testClassName + "_" + testName);
    SeleniumTestBase.logger.assignCategory(testClassName);*/
    //SeleniumTestBase.logger = ExtentTestManager.startTest(testClassName + "_" + testName);
    ExtentTest test = ExtentTestManager.startTest(testClassName + "_" + testName, testDesc);
    //test.assignCategory(testClassName);
    return test;
  }
  
  /** Ends the test in extent report. Flush all the test steps to the extent report. */
  public void endExtentTest() {
    /*SeleniumTestBase.extent.endTest(SeleniumTestBase.logger);
    SeleniumTestBase.extent.flush();*/
    /*ExtentTestManager.extent.endTest(ExtentTestManager.getTest());
    ExtentTestManager.extent.flush();*/
    ExtentTestManager.endTest();
    ExtentTestManager.extent.flush();
    Logger.log("**************************************** Test Ended ******************************************",true);
  }
  
  /** Ends the test in extent report. Flush all the test steps to the extent report. */
  public void endExtentChildTest(ExtentTest childTest) {
    /*SeleniumTestBase.extent.endTest(SeleniumTestBase.logger);
    SeleniumTestBase.extent.flush();*/
    /*ExtentTestManager.extent.endTest(ExtentTestManager.getTest());
    ExtentTestManager.extent.flush();*/
    ExtentTestManager.extent.endTest(childTest);
    ExtentTestManager.extent.flush();
    Logger.log("**************************************** Test Ended ******************************************",true);
  }
  
  /**
   * Log info.
   *
   * @param stepName the step name
   * @param info the information that needs to be added to the report
   */
  public void logInfo(String stepName, String info) {
    //SeleniumTestBase.logger.log(LogStatus.INFO, stepName, info.replaceAll("\n", "<br>"));
    ExtentTestManager.getTest().log(LogStatus.INFO, stepName, info.replaceAll("\n", "<br>"));
  }

  /**
   * Log error.
   *
   * @param stepName the step name
   * @param errorMsg the error message to be added to the report
   */
  public void logError(String stepName, String errorMsg) {
    //SeleniumTestBase.logger.log(LogStatus.ERROR, stepName, errorMsg.replaceAll("\n", "<br>"));
    ExtentTestManager.getTest().log(LogStatus.ERROR, stepName, errorMsg.replaceAll("\n", "<br>"));
  }

  /**
   * Log fail.
   *
   * @param stepName the step name
   * @param failMsg the failure message to be added to the report
   */
  public void logFail(String stepName, String failMsg) {
    //SeleniumTestBase.logger.log(LogStatus.FAIL, stepName, failMsg.replaceAll("\n", "<br>"));
    ExtentTestManager.getTest().log(LogStatus.FAIL, stepName, failMsg.replaceAll("\n", "<br>"));
  }

  /**
   * Log pass.
   *
   * @param stepName the step name
   * @param successMsg the success message to be added to the report
   */
  public void logPass(String stepName, String successMsg) {
    //SeleniumTestBase.logger.log(LogStatus.PASS, stepName, successMsg.replaceAll("\n", "<br>"));
    ExtentTestManager.getTest().log(LogStatus.PASS, stepName, successMsg.replaceAll("\n", "<br>"));
  }

  /**
   * Log warning.
   *
   * @param stepName the step name
   * @param warningMsg the warning message to be added to the report
   */
  public void logWarning(String stepName, String warningMsg) {
    //SeleniumTestBase.logger.log(LogStatus.WARNING, stepName, warningMsg.replaceAll("\n", "<br>"));
    ExtentTestManager.getTest().log(LogStatus.WARNING, stepName, warningMsg.replaceAll("\n", "<br>"));
  }

  /**
   * Log skip.
   *
   * @param stepName the step name
   * @param skipMsg the skip message to be added to the report
   */
  public void logSkip(String stepName, String skipMsg) {
    //SeleniumTestBase.logger.log(LogStatus.SKIP, stepName, skipMsg.replaceAll("\n", "<br>"));
    ExtentTestManager.getTest().log(LogStatus.SKIP, stepName, skipMsg.replaceAll("\n", "<br>"));
  }

  /**
   * Log success image.
   *
   * @param imageInfo the information about the image
   * @param image the path of an image to be appended to the report
   */
  public void logSuccessImage(String imageInfo, String image) {
    /*image = SeleniumTestBase.logger.addScreenCapture(image);
    SeleniumTestBase.logger.log(LogStatus.PASS, imageInfo, image);*/
    image = ExtentTestManager.getTest().addScreenCapture(image);
    ExtentTestManager.getTest().log(LogStatus.PASS, imageInfo, image);
  }

  /**
   * Log Failure image.
   *
   * @param imageInfo the information about the image
   * @param image the path of an image to be appended to the report
   */
  public void logFailureImage(String imageInfo, String image) {
    /*image = SeleniumTestBase.logger.addScreenCapture(image);
    SeleniumTestBase.logger.log(LogStatus.FAIL, imageInfo, image);*/
    image = ExtentTestManager.getTest().addScreenCapture(image);
    ExtentTestManager.getTest().log(LogStatus.FAIL, imageInfo, image);
  }

  /**
   * Log Warning image.
   *
   * @param imageInfo the information about the image
   * @param image the path of an image to be appended to the report
   */
  public void logWarningImage(String imageInfo, String image) {
    /*image = SeleniumTestBase.logger.addScreenCapture(image);
    SeleniumTestBase.logger.log(LogStatus.WARNING, imageInfo, image);*/
    image = ExtentTestManager.getTest().addScreenCapture(image);
    ExtentTestManager.getTest().log(LogStatus.WARNING, imageInfo, image);
  }

  /**
   * Log image.
   *
   * @param imageInfo the information about the image
   * @param image the path of an image to be appended to the report
   */
  public void logImage(String imageInfo, String image) {
    /*image = SeleniumTestBase.logger.addScreenCapture(image);
    SeleniumTestBase.logger.log(LogStatus.INFO, imageInfo, image);*/
    image = ExtentTestManager.getTest().addScreenCapture(image);
    ExtentTestManager.getTest().log(LogStatus.INFO, imageInfo, image);
  }
  
  /**
   * Log Label.
   *
   * @param stepName the step name
   * @param info the information that needs to be added to the report
   */
  public void logLabel(String stepName, String info) {
    /*SeleniumTestBase.logger.log(LogStatus.INFO,
        "<strong style=\"background-color:#C0C0C0\">" + stepName + "</strong>",
        "<strong style=\"background-color:#C0C0C0\">" + info + "</strong>");*/
    ExtentTestManager.getTest().log(LogStatus.INFO,
        "<strong style=\"background-color:#C0C0C0\">" + stepName + "</strong>",
        "<strong style=\"background-color:#C0C0C0\">" + info + "</strong>");
  }
}
