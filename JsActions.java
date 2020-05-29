
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;

/**
 * Helper class to execute controller manipulations like drag and drop using Javascript
 */
public class JsActions {

    private WebDriver driver;

    private static String JS_ACTIONS = "jsActions";

    private static final Map<String, String> scriptIdentifier = new HashMap<String, String>() {
        {
            put(JS_ACTIONS, "/com/onehippo/cms7/qa/js/actions.js");

        }
    };

    public JsActions(WebDriver driver) {
        this.driver = driver;
    }


    /**
     * Starts dragging dragElement simulating mouseover and mousedown events
     *
     * @param dragElement element which should be dragged
     * @return self reference
     */
    public JsActions clickAndHold(WebElement dragElement) {

        executeJsFunction(JS_ACTIONS, "clickAndHold", dragElement);
        return this;
    }

    /**
     * Drag and drop dragging element by offset from top left corner of dragging element
     *
     * @param mirrorElement element to drag. Usually dragula mirror.
     * @param xOffset       X offset from top left corner of dragging element
     * @param yOffset       Y offset fro top left corner of dragging element
     * @return self reference
     */
    public JsActions dragAndDropMirrorByOffset(WebElement mirrorElement, int xOffset, int yOffset) {
        executeJsFunction(JS_ACTIONS, "dragAndDropMirrorByOffset", mirrorElement, xOffset, yOffset);
        return this;
    }

    /**
     * Drag and drop mirror to the center of target element
     *
     * @param mirrorElement element to drag. Usually Dragula mirror
     * @param targetElement target element
     * @return self reference
     */
    public JsActions dragAndDropMirror(WebElement mirrorElement, WebElement targetElement) {
        executeJsFunction(JS_ACTIONS, "dragAndDropMirror", mirrorElement, targetElement);
        return this;
    }


    /**
     * Drag and drop element to the center of target element
     *
     * @param dragElement   element to drag
     * @param targetElement element to drop
     * @return self reference
     */
    public JsActions dragAndDrop(WebElement dragElement, WebElement targetElement) {
        executeJsFunction(JS_ACTIONS, "dragAndDrop", dragElement, targetElement);
        return this;
    }


    /**
     * Triggers mouseover on
     *
     * @param elementToMoveTo element to move to
     * @return self reference
     */
    public JsActions moveToElement(WebElement elementToMoveTo) {
        moveToElement(elementToMoveTo, 0, 0);
        return this;
    }

    /**
     * Moves mouse to element with offset from its top left corner
     *
     * @param elementToMoveTo element to move to
     * @param xOffset         x offset
     * @param yOffset         y offset
     * @return elf reference;
     */
    public JsActions moveToElement(WebElement elementToMoveTo, int xOffset, int yOffset) {
        scrollToElement(elementToMoveTo);
        executeJsFunction(JS_ACTIONS, "moveToElement", elementToMoveTo, xOffset, yOffset);
        return this;
    }

    /**
     * Clicks on element with current mouse position
     *
     * @return selfReference
     */
    public JsActions click() {
        executeJsFunction(JS_ACTIONS, "actionClick");
        return this;
    }

    /**
     * Selects text of element with text
     *
     * @param elWithText element with text content
     * @param text       text to select
     * @return self reference
     */
    public JsActions selectText(WebElement elWithText, String text) {
        scrollToElement(elWithText);
        final String textContent = elWithText.getText();
        int startRange = textContent.indexOf(text);
        String message = String.format("No such text in text content of element." +
                " Text content :%s, text to select:%s", textContent, text);
        Assert.assertTrue(message, startRange > -1);
        int endRange = startRange + text.length();
        executeJsFunction(JS_ACTIONS, "selectText", elWithText, startRange, endRange);
        return this;
    }

    /**
     * Performs mouse up after some actions like clickAndHold
     */
    public void  release(){
     executeJsFunction(JS_ACTIONS, "release");
    }

    /**
    * Simulates mouse move from current mouse position by offset
     * @param xOffset
     * @param yOffset
     * @return self reference
     */
    public JsActions moveMouseByOffset(int xOffset, int yOffset){
        executeJsFunction(JS_ACTIONS, "moveMouseByOffset", xOffset, yOffset);
        return this;
    }
    /**
     * Executes javascript function from JS file
     *
     * @param scriptId     string identifier to load javascript file
     * @param functionName function to execute
     * @param args         function arguments
     * @return self reference
     */
    private Object executeJsFunction(String scriptId, String functionName, Object... args) {

        String params = "";
        for (int i = 0; i < args.length; i++) {
            params += String.format("arguments[%s]", i);
            if (i != args.length - 1 && args.length > 1) {
                params += ",";
            }
        }

        String functionToExecute = String.format("return %s(%s);", functionName, params);
        injectScript(scriptId);
        return ((JavascriptExecutor) driver).executeScript(functionToExecute, args);
    }

    /**
     * Inject script from JS file
     *
     * @param scriptId relative path to JS file from resources
     */
    private void injectScript(String scriptId) {

        String scriptCheck = String.format("return window.%s !== undefined;", scriptId);
        final boolean isScriptPresent = Boolean.parseBoolean(String.valueOf(((JavascriptExecutor) driver).executeScript(scriptCheck)));
        if (!isScriptPresent) {
            final InputStream resource = ClassLoader.class.getResourceAsStream(scriptIdentifier.get(scriptId));
            Scanner scanner = new Scanner(resource, "utf-8").useDelimiter("\\A");

            String script = String.format("var scriptTag=window.document.createElement('script'); scriptTag.textContent=`var %s=true; %s`;"
                    + " window.document.head.appendChild(scriptTag);", scriptId, scanner.next());

            ((JavascriptExecutor) driver).executeScript(script);
        }

    }

    private void scrollToElement(WebElement element) {
        ((Locatable)element).getCoordinates().inViewPort();
    }

}
