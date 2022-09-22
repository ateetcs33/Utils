package selautomation.common;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.OperatingSystem;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DriverManager {

  static Map webDriverMap = new HashMap<Integer, WebDriver>();
  //static ExtentReports extent = ExtentManager.getReporter();

  /**
   * Returns the driver of the current thread
   *
   * @return
   */
  public static synchronized WebDriver getDriver() {
    return (WebDriver) webDriverMap.get((int) (long) (Thread.currentThread().getId()));
  }

  /**
   * Closes the current thread driver
   */
  public static synchronized void endDriver() {
    getDriver().close();
    //((WebDriver) webDriverMap.get((int) (long) (Thread.currentThread().getId()))).close();
  }

  /**
   * End all driver
   */
  public static synchronized void endallDriver() {
    webDriverMap.get((int) (long) (Thread.currentThread().getId()));
    Set keys = webDriverMap.keySet();
    Logger.log("From AfterSuite - Threads active : " + keys.size());
    /*for (Object key : keys) {
      ((WebDriver) webDriverMap.get(key)).close();
    }*/
  }

  /**
   * Initializes the WebDriver and assigns it to the current thread
   *
   * @return
   */
  public static synchronized WebDriver startdriver() throws MalformedURLException {
    /*ExtentTest test = extent.startTest(testName, desc);
    webDriverMap.put((int) (long) (Thread.currentThread().getId()), test);
    return test;*/

    //WebDriver driver;
    Logger.log("Initializing driver : " + (int) (long) (Thread.currentThread().getId())
        + "\n" + Thread.currentThread().getName(),true);
    //if (this.driver == null) {
    String osType = System.getProperty("os.name");
    String osArch = System.getProperty("os.arch");
    String osVersion = System.getProperty("os.version");
    Logger.log("Running on OS Type : " + osType + " => OS Arch : " + osArch + " => OS Version : " + osVersion,true);
    //File binary = null;
    // Chrome options definition
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("disable-infobars");
    //chromeOptions.addArguments("--headless");
    WebDriver driver;
    //if (!osType.toLowerCase().contains("mac")) {
    if (osType.toLowerCase().contains("mac")) {
      ChromeDriverManager.chromedriver().operatingSystem(OperatingSystem.MAC).setup();
      //ChromeDriverManager.chromedriver().operatingSystem(OperatingSystem.MAC).version("78").setup();
      //ChromeDriverManager.getInstance().operatingSystem(OperatingSystem.valueOf("MAC")).version("73").setup();
      /*binary = new File(ChromeDriverManager.chromedriver().operatingSystem(OperatingSystem.MAC).version("78")
          .getBinaryPath());
      Logger.log("Browser binary path" + binary);*/
      driver = new ChromeDriver(chromeOptions);
      Capabilities capabilities = ((ChromeDriver) driver).getCapabilities();
    } else {
      DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
      desiredCapabilities.setBrowserName("chrome");
      desiredCapabilities.setPlatform(Platform.LINUX);
      desiredCapabilities.setVersion("");
      chromeOptions.addArguments("--headless");
      chromeOptions.merge(desiredCapabilities);

      String hubUrl = "http://localhost:4444/wd/hub";

      driver = new RemoteWebDriver(new URL(hubUrl), desiredCapabilities);
    }
    driver.manage().window().setPosition(new Point(0, 0));
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    EventFiringWebDriver eventFiringWebDriver;
    eventFiringWebDriver = new EventFiringWebDriver(driver);
    //Now create object of EventListerHandler to register it with EventFiringWebDriver
    EventListener eventListener = new EventListener();
    eventFiringWebDriver.register(eventListener);
    driver = eventFiringWebDriver;
    //driver.manage().window().fullscreen();
    /*Toolkit toolkit = Toolkit.getDefaultToolkit();
    int width = (int) toolkit.getScreenSize().getWidth();
    int height = (int) toolkit.getScreenSize().getHeight();
    // For Dimension class, Import following library "org.openqa.selenium.Dimension"
    driver.manage().window().setSize(new Dimension(width, height));*/
    //}
    driver.manage().window().maximize();
    //driver.manage().window().fullscreen();
    webDriverMap.put((int) (long) (Thread.currentThread().getId()), driver);
    Logger.log("Window Size : " + driver.manage().window().getSize(),true);
    Logger.log("driver position : " + driver.manage().window().getPosition(),true);
    return driver;
  }

}
