

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onehippo.cms7.qa.controls.BaseControl;
import com.onehippo.cms7.qa.controls.Control;

public class Quickly {

    private static final int IMPLICIT_WAIT_SECONDS = 5;
    private static final Logger log = LoggerFactory.getLogger(Quickly.class);

    /**
     * Find a web element without (implicit) waiting.
     *
     * This utility can be handy to speed up tests that ensure that a certain web element is not present.
     *
     * @param driver web driver to use
     * @param by how to find the element
     * @return the element found. Throws an exception if not found.
     */
    public static WebElement findElement(final WebDriver driver, final By by) {
        return findElement(driver, null, by);
    }

    /**
     * Find a web element without (implicit) waiting.
     *
     * This utility can be handy to speed up tests that ensure that a certain web element is not present.
     *
     * @param driver web driver to use
     * @param scope DOM-scope in which to search
     * @param by how to find the element
     * @return the element found. Throws an exception if not found.
     */
    public static WebElement findElement(final WebDriver driver, final WebElement scope, final By by) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        try {
            if (scope != null) {
                return scope.findElement(by);
            } else {
                return driver.findElement(by);
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
        }
    }

    /**
     * Find a web element without (implicit) waiting.
     *
     * @param control parent control in scope of which element should be found
     * @param by finding criteria
     * @return  element found. Throws an exception if not found.
     */
    public static WebElement findElement(final Control control, final By by) {
        return findElement(control.getDriver(), control.getContainer(), by);
    }


    /**
     * Find a list of web elements without (implicit) waiting.
     *
     * This utility can be handy to speed up tests that ensure that certain web elements are not present.
     *
     * @param driver web driver to use
     * @param by how to find the elements
     * @return the elements found.
     */
    public static List<WebElement> findElements(final WebDriver driver, final By by) {
        return findElements(driver, null, by);
    }

    /**
     * Find a list of web elements without (implicit) waiting.
     *
     * This utility can be handy to speed up tests that ensure that certain web elements are not present.
     *
     * @param driver web driver to use
     * @param scope DOM-scope in which to search
     * @param by how to find the elements
     * @return the elements found.
     */
    public static List<WebElement> findElements(final WebDriver driver, final WebElement scope, final By by) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        try {
            if (scope != null) {
                return scope.findElements(by);
            } else {
                return driver.findElements(by);
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
        }
    }

    /**
     * Check for the presence of an element, given a CSS selector and a stable UI.
     *
     * @param driver web driver to use
     * @param cssSelector CSS selector for to-be-checked web element
     * @return true/false
     */
    public static boolean checkPresenceByCssSelector(final WebDriver driver, final String cssSelector) {
        try {
            return Quickly.findElement(driver, By.cssSelector(cssSelector)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Wait for the page to load.
     *
     * @param driver WebDriver pointing to the frame in which the page is supposed to load.
     */
    public static void waitForPageToLoad(final WebDriver driver) {
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);

        final String hasPageLoadedScript =
                "var callback = arguments[arguments.length - 1];" +
                "if (document.readyState === 'complete') {" +
                "  callback('true');" +
                "} else {" +
                "  window.onload = function () {" +
                "    callback('true')" +
                "  };" +
                "}";

        final String result = ((JavascriptExecutor) driver).executeAsyncScript(hasPageLoadedScript).toString();

        if (!Boolean.valueOf(result)) {
            log.warn("Waiting for page to load failed.");
        }
    }
}
