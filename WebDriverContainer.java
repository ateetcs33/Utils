

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.onehippo.cms7.qa.selenium.wrap.chrome.ChromeDriverWrapper;
import com.onehippo.cms7.qa.selenium.wrap.edge.EdgeDriverWrapper;
import com.onehippo.cms7.qa.selenium.wrap.firefox.FireFoxDriverWrapper;
import com.onehippo.cms7.qa.selenium.wrap.remote.RemoteDriverWrapper;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This container creates and contains a WebDriver. The WebDriver that is created depends on a number of system
 * properties:
 * <ul>
 * <li>browser : Which browser to use. Can be "[headless-]chrome", "[headless-]firefox","ie" or "edge". Defaults to "firefox".</li>
 * <li>webdriver.remote.server : Uses a browser on a remote server, specified by this URL. If this system property is
 * not given, an attempt is made to start one locally</li>
 * <li>webdriver.firefox.bin : The location of the binary of the firefox browser. This is used internally by the
 * FirefoxDriver. Not needed if installed in the default location</li>
 * <li>webdriver.chrome.bin : The location of the binary of the chrome browser. Not needed if installed in the default
 * location</li>
 * </ul>
 */
public class WebDriverContainer {

    public static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
    public static final String WEBDRIVER_EDGE_DRIVER = "webdriver.edge.driver";
    public static final String WEBDRIVER_FIREFOX_DRIVER = "webdriver.gecko.driver";

    private WebDriver driver;

    /**
     * Determines if this driver represents a local or a remote browser
     *
     * @return true if the system property <code>webdriver.remote.server</code> is set
     */
    static boolean hasRemoteDriver() {
        return PropertiesManager.getInstance().getWebdriverRemoteServer() != null &&
                PropertiesManager.getInstance().getWebdriverRemoteServer().length() > 0;
    }

    /**
     * Determines if this driver is a Chrome driver
     *
     * @return true if the driver is an instance of ChromeDriver
     */
    public static boolean isChromeDriver(WebDriver driver) {
        return driver instanceof ChromeDriver;
    }

    /**
     * Determines if this driver is a FireFox driver
     *
     * @return true if the driver is an instance of FirefoxDriver
     */
    @SuppressWarnings("UnusedDeclaration")
    public static boolean isFirefoxDriver(WebDriver driver) {
        return driver.toString().toLowerCase().contains("firefox");
    }

    /**
     * Determines if this driver is an Internet Explorer driver
     *
     * @return true if the driver is an instance of InternetExplorerDriver
     */
    @SuppressWarnings("UnusedDeclaration")
    public static boolean isInternetExplorerDriver(WebDriver driver) {
        return driver instanceof InternetExplorerDriver;
    }

    /**
     * Creates the appropriate WebDriver and sets its size and timeouts
     */
    private WebDriver createNewDriver() {
        WebDriver newDriver;
        BrowserName desiredBrowser = PropertiesManager.getInstance().getDesiredBrowser();

        if (hasRemoteDriver()) {
            newDriver = createRemoteDriver(desiredBrowser);
        } else {
            newDriver = createLocalDriver(desiredBrowser);
        }

        boolean maximiseWindow = Boolean.parseBoolean(PropertiesManager.getInstance().getProperty("maximise.browser.window"));
        if (maximiseWindow) {
            newDriver.manage().window().maximize();
        } else {
           newDriver.manage().window().setSize(getSize());
        }

        newDriver.manage().timeouts().implicitlyWait(PropertiesManager.getInstance().getWebdriverImplicitTimeout(), TimeUnit.MILLISECONDS);
        newDriver.manage().timeouts().setScriptTimeout(PropertiesManager.getInstance().getWebdriverScriptTimeout(), TimeUnit.MILLISECONDS);

        return newDriver;
    }

    private WebDriver createLocalDriver(BrowserName browser) {
        WebDriver d;
        switch (browser) {
            case CHROME:
                d = getLocalChromeDriver();
                break;
            case IE:
                d = getLocalInternetExplorerDriver();
                break;
            case EDGE:
                d = getEdgeDriver();
                break;
            case HEADLESSCHROME:
                d = getHeadlessChrome();
                break;
            case DOCKERCHROME:
                d = getDockerChromeDriver();
                break;
            case HEADLESSFIREFOX:
                d = getHeadlessFirefox();
                break;
            case FIREFOX:
            default:
                d = getLocalFirefoxDriver();
                break;
        }
        return PropertiesManager.getInstance().isUseSafeWebdriverWrapper() ? new SafeWebDriver(d) : d;
    }

    private WebDriver createRemoteDriver(BrowserName browser) {
        Capabilities capabilities;
        switch (browser) {
            case CHROME:
                capabilities = getChromeCapabilities();
                break;
            case IE:
                capabilities = getInternetExplorerCapabilities();
                break;
            case EDGE:
                capabilities = getEdgeCapabilities();
                break;
            case FIREFOX:
            default:
                capabilities = getFirefoxCapabilities();
                break;
        }
        URL url = null;
        try {
            url = new URL(PropertiesManager.getInstance().getWebdriverRemoteServer());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return PropertiesManager.getInstance().isUseSafeWebdriverWrapper() ?
                new SafeRemoteWebDriver(capabilities) :
                new RemoteDriverWrapper(url, capabilities);
    }

    /**
     * Gets the driver in this container
     *
     * @return the driver
     */
    public WebDriver getDriver() {
        if (driver == null) {
            driver = createNewDriver();
        }
        return driver;
    }

    public boolean isDriverStarted() {
        return driver != null;
    }

    private Capabilities getFirefoxCapabilities() {
        DesiredCapabilities cap = DesiredCapabilities.firefox();
        cap.setJavascriptEnabled(true);
        return cap;
    }

    private WebDriver getFirefoxDriverWrapper(FirefoxOptions options) {
        if(System.getProperty(WEBDRIVER_FIREFOX_DRIVER) == null)  {
            WebDriverManager.firefoxdriver().setup();
        }
        return new FireFoxDriverWrapper(options);
    }

    private WebDriver getLocalFirefoxDriver() {
        return getFirefoxDriverWrapper(new FirefoxOptions());
    }


    private Capabilities getChromeCapabilities() {
        return DesiredCapabilities.chrome();
    }

    private WebDriver getChromeDriverWrapper(ChromeOptions options) {
        if(System.getProperty(WEBDRIVER_CHROME_DRIVER) == null) {
            WebDriverManager.chromedriver().setup();
        }
        return new ChromeDriverWrapper(options);
    }

    private WebDriver getLocalChromeDriver() {
        return getChromeDriverWrapper(new ChromeOptions());
    }

    private Capabilities getInternetExplorerCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("requireWindowFocus", true);

        return capabilities;
    }

    private WebDriver getLocalInternetExplorerDriver() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        /* capabilities.setCapability("requireWindowFocus", true);
         * This will mean that you cannot use your machine while the tests are
         * running because it will take control of your mouse cursor, but the
         * flickering issues will go.
         *
         * The flickering issues are caused by IE constantly checking where the
         * mouse cursor is. Selenium tells IE that the mouse cursor should be at a
         * specific position, so IE puts it there, but then checks with windows
         * where the mouse cursor should be and moves it back to its real
         * location. Selenium tries to get around this by constantly telling IE
         * where the mouse pointer is if you have the following capability set:
         * capabilities.setCapability(CapabilityType.ENABLE_PERSISTENT_HOVERING, true);
         */
        options.setCapability("requireWindowFocus", true);

        return new InternetExplorerDriver(options);
    }

    /**
     * Determines the configured browser type.
     *
     * @return BrowserName with the browser type in use
     */
    public BrowserName getBrowser() {
        return PropertiesManager.getInstance().getDesiredBrowser();
    }

    private WebDriver getEdgeDriver() {
    //    EdgeDriverManager.getInstance().setup();
        return new EdgeDriverWrapper();
    }

    private Capabilities getEdgeCapabilities() {
        return DesiredCapabilities.edge();
    }

    Dimension getSize() {
        final String configuredSize = PropertiesManager.getInstance().getProperty("browser.window.dimension");
        if (configuredSize != null) {
            final int width = Integer.parseInt(configuredSize.substring(0, configuredSize.indexOf(':')));
            final int height = Integer.parseInt(configuredSize.substring(configuredSize.indexOf(':') + 1));
            return new Dimension(width, height);
        }
        return new Dimension(1280, 720);

    }

    public WebDriver getHeadlessChrome() {
        Dimension dimension = getSize();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless", String.format("window-size=%d,%d", dimension.width, dimension.height));
        chromeOptions.addArguments("--proxy-server='direct://'");
        chromeOptions.addArguments("--proxy-bypass-list=*");
        return getChromeDriverWrapper(chromeOptions);
    }

    /**
     * Use Chrome headless with --no-sandbox so no root is needed
     */
    private WebDriver getDockerChromeDriver() {
        Dimension dimension = getSize();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless", "--no-sandbox", String.format("window-size=%d,%d", dimension.width, dimension.height));
        return getChromeDriverWrapper(chromeOptions);
    }

    public WebDriver getHeadlessFirefox() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        firefoxOptions.setBinary(firefoxBinary);
        return getFirefoxDriverWrapper(firefoxOptions);
    }
}
