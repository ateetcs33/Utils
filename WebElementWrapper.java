

import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.internal.WrapsElement;

/**
 * Wrapper that tries to avoid StaleElementExceptions. Only problematic methods are caught as of yet.
 */
public class WebElementWrapper implements WebElement, Locatable, WrapsElement {
    private WebElement element;
    private final SearchContext parent;
    private final By myBy;
    private final int index;

    public WebElementWrapper(WebElement element, SearchContext parent, By myBy) {
        this(element, parent, myBy, -1);
    }

    public WebElementWrapper(WebElement element, SearchContext parent, By myBy, int index) {
        this.element = element;
        this.parent = parent;
        this.myBy = myBy;
        this.index = index;
    }

    @Override
    public void clear() {
        execute(new SafeInvoker<Void>() {
            @Override
            public Void run() {
                element.clear();
                return null;
            }
        });
    }

    @Override
    public void click() {
        execute(new SafeInvoker<Void>() {
            @Override
            public Void run() {
                element.click();
                return null;
            }
        });
    }

    @Override
    public WebElement findElement(final By locator) {
        return new WebElementWrapper(execute(new SafeInvoker<WebElement>() {
            @Override
            public WebElement run() {
                return element.findElement(locator);
            }
        }), this, locator);
    }

    @Override
    public List<WebElement> findElements(final By locator) {
        List<WebElement> result = execute(new SafeInvoker<List<WebElement>>() {
            @Override
            public List<WebElement> run() {
                return element.findElements(locator);
            }
        });
        ListIterator<WebElement> it = result.listIterator();
        int j = 0;
        while (it.hasNext()) {
            WebElement el = it.next();
            it.set(new WebElementWrapper(el, this, locator, j++));
        }
        return result;
    }

    @Override
    public String getAttribute(final String arg0) {
        return execute(new SafeInvoker<String>() {
            @Override
            public String run() {
                return element.getAttribute(arg0);
            }
        });
    }

    @Override
    public String getCssValue(final String arg0) {
        return execute(new SafeInvoker<String>() {
            @Override
            public String run() {
                return element.getCssValue(arg0);
            }
        });
    }

    @Override
    public Point getLocation() {
        return execute(new SafeInvoker<Point>() {
            @Override
            public Point run() {
                return element.getLocation();
            }
        });
    }

    @Override
    public Dimension getSize() {
        return execute(new SafeInvoker<Dimension>() {
            @Override
            public Dimension run() {
                return element.getSize();
            }
        });
    }

    @Override
    public Rectangle getRect() {
        return execute(new SafeInvoker<Rectangle>() {
            @Override
            public Rectangle run() {
                return element.getRect();
            }
        });
    }

    @Override
    public String getTagName() {
        return execute(new SafeInvoker<String>() {
            @Override
            public String run() {
                return element.getTagName();
            }
        });
    }

    @Override
    public String getText() {
        return execute(new SafeInvoker<String>() {
            @Override
            public String run() {
                return element.getText();
            }
        });
    }

    @Override
    public boolean isDisplayed() {
        return execute(new SafeInvoker<Boolean>() {
            @Override
            public Boolean run() {
                return element.isDisplayed();
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return execute(new SafeInvoker<Boolean>() {
            @Override
            public Boolean run() {
                return element.isEnabled();
            }
        });
    }

    @Override
    public boolean isSelected() {
        return execute(new SafeInvoker<Boolean>() {
            @Override
            public Boolean run() {
                return element.isSelected();
            }
        });
    }

    @Override
    public void sendKeys(final CharSequence... arg0) {
        execute(new SafeInvoker<Void>() {
            @Override
            public Void run() {
                element.sendKeys(arg0);
                return null;
            }
        });
    }

    @Override
    public void submit() {
        execute(new SafeInvoker<Void>() {
            @Override
            public Void run() {
                element.submit();
                return null;
            }
        });
    }

    @Override
    public Coordinates getCoordinates() {
        return execute(new SafeInvoker<Coordinates>() {
            @Override
            public Coordinates run() {
                return ((Locatable) element).getCoordinates();
            }
        });
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    // Find the new me, if possible
    private WebElement backFindMe() {
        if (index == -1) {
            element = parent.findElement(myBy);
        } else {
            // This is not so safe. Print warning
            System.err.println("WARNING: Using index as a locator for backtrace");
            new Exception().printStackTrace(System.err);
            List<WebElement> containsMe = parent.findElements(myBy);
            if (containsMe.size() <= index) {
                //I'm definitely not there anymore
                throw new NoSuchElementException("Backtrace of stale element failed. Element doesn't exist (anymore)");
            } else {
                element = containsMe.get(index);
            }
        }
        return element;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) {
        return execute(new SafeInvoker<X>() {
            @Override
            public X run() {
                return element.getScreenshotAs(outputType);
            }
        });
    }

    private abstract class SafeInvoker<T> {
        public abstract T run();
    }

    private <T> T execute(SafeInvoker<T> action) {
        try {
            return action.run();
        } catch (StaleElementReferenceException see) {
            backFindMe();
            return action.run();
        }
    }

}
