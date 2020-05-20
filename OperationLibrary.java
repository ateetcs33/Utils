package com.testyantra.library;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
//import com.kirwa.nxgreport.NXGReports;
//import com.kirwa.nxgreport.logging.LogAs;
import com.testyantra.util.BrowserUtil;
 


public class OperationLibrary 
{
	/*
	 Generic methods present in webdriver
	 */
	public static HashMap<String, String> map = new HashMap<String, String>();
    //Implicit Wait.
	public void waitForPageToLoad(int time){
		try
		{	
	        BrowserUtil.driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		}
		catch (NoSuchElementException e) 
		{
			e.printStackTrace();
		}
	}
	//Maximizing Window.
	public void maximizeWindow(){
	BrowserUtil.driver.manage().window().maximize();
	}

	//Explicit Wait.
	public void waitForElementPresent(WebElement element){
		try
		{
			int time=15;
			WebDriverWait wait = new WebDriverWait(BrowserUtil.driver, time);
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch (ElementNotVisibleException e) {
			e.printStackTrace();
		}
	}
	//Clearing data from webelement.
	public void clear(WebElement element){
	    try
	    {
	    	element.clear();
	    }
	    catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Get tag name of webelement.
	public String getTagName(WebElement element) {
		  try
		    {
				return element.getTagName();	
		    }
		  catch (StaleElementReferenceException e)
		    {
		    	e.printStackTrace();
			}
		  return null;
	}
	//Get size of webelement.
	public Dimension getSize(WebElement element) {
	      try
		    {
				return element.getSize();	
		    }
		  catch (StaleElementReferenceException e)
		    {
		    	e.printStackTrace();
			}
		return null;	
	}
	//Get rectangle of webelement.
	public Rectangle getRect(WebElement element) {
		 try
		    {
			 	 return element.getRect();	
			 	
		    }
		 catch (StaleElementReferenceException e)
		    {
		    	e.printStackTrace();
			}
	    return null;	
	}
	//Get location of webelement.
	public Point getLocation(WebElement element) {
		 try
		    {
				return element.getLocation();	
		    }
		 catch (StaleElementReferenceException e)
		    {
		    	e.printStackTrace();
			}
		return null;	
	}
	//GetText from webelement.
	public String getText(WebElement element){
		 try
		    {
				return element.getText();
		    }
	    catch (StaleElementReferenceException e)
		    {
		    	e.printStackTrace();
			}
		return null;
	}
	//Get css value from webelement.
	public String getCssValue(WebElement element,String text){
		 try
		    {
				return element.getCssValue(text);
		    }
		catch (StaleElementReferenceException e)
		    {
		    	e.printStackTrace();
			}
		return null;
	}
	//Get attribute from webelement.
	public String getAttribute(WebElement element,String text){
		 try
		    {
				return element.getAttribute(text);
		    }
	    catch (StaleElementReferenceException e)
		    {
		    	e.printStackTrace();
			}
		return null;
	}
	/*//Get Class from webelement.
	public void getClass(WebElement element){
	element.getClass();
	}*/
	
	//Typing data on to webelement.
	public void type(WebElement element,String text){
	    try
		   {
	    	if(element.isDisplayed()){
	    		element.clear();	
	    		element.sendKeys(text);
	    		NXGReports.addStep(element+" typed", LogAs.PASSED, null);
	    	}else{
	    		try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    		type(element, text);
	    	}
		   }
	   catch (StaleElementReferenceException e)
		    {
		    	e.printStackTrace();
			}
	}
	//Clicking on webelement.
	public void click(WebElement element) throws InterruptedException{
	try 
		{
			element.click();
			NXGReports.addStep(element+" clicked", LogAs.PASSED, null);
//			js.executeScript(arg0, arg1)
//			new WebDriverWait(BrowserUtil.driver, 10).Until(((JavaScriptExecutor) BrowserUtil.driver).executeScript("return document.readyState",null).Equals("complete"));
//			new WebDriverWait(BrowserUtil.driver, 10).Until(ExpectedConditions.jsReturnsValue(javaScript);
			/*while(true){
				JavascriptExecutor js = (JavascriptExecutor) BrowserUtil.driver;  
				if(((String) js.executeScript("return document.readyState")).equals("complete")){
					break;
				}
			}*/
//			Thread.sleep(3000);
		}
	catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	catch (WebDriverException e) {
		new WebDriverWait(BrowserUtil.driver, 10).until(ExpectedConditions.elementToBeClickable(element));
		click(element);
	//	NXGReports.addStep(element+" clicked", LogAs.PASSED, null);
	}
	}
	
	/*
	 End of generic methods
	 */
	
	/*
	 Actions Class Methods
	 Methods depreciated from Actions class not implemented
	 1)Pause method.
	 Methods yet to be implemented
	 1)Keys method.
	 */
	
	//Click action(Actions class).
	/*public void click(WebDriver driver){
		try
		{
			Actions act = new Actions(driver);
			act.click();
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}*/
	//Right click.
	public void contextClick(WebDriver driver){
		try
		{
			Actions act = new Actions(driver);
			act.contextClick();
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}	
	}
	//Double click.
	public void rightClick(WebDriver driver){
		try
		{
			Actions act = new Actions(driver);
			act.doubleClick();
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Perform action.
	public void perform(WebDriver driver){
		try
		{
			Actions act = new Actions(driver);
			act.perform();
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Build action.
	public void build(WebDriver driver){
		try
		{
			Actions act = new Actions(driver);
			act.build();
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//ClickAndHold action.
	public void clickAndHold(WebDriver driver){
		try
		{
			Actions act = new Actions(driver);
			act.clickAndHold();
		} 
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Release action.
	public void release(WebDriver driver){
		try
		{
			Actions act = new Actions(driver);
			act.release();
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Move to webelement.
	public void moveToElement(WebElement element,WebDriver driver){
		try 
		{
			Actions act = new Actions(driver);
			act.moveToElement(element).perform();
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Move to webelement based on x & y offset.
	public void moveToElement(WebElement element,WebDriver driver,int x,int y){
		try
		{
			Actions act = new Actions(driver);
			act.moveToElement(element,x,y).perform();
		} 
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Move based on x & y offset.
	public void moveByOffset(WebDriver driver,int x,int y){
		try 
		{
			Actions act = new Actions(driver);
			act.moveByOffset(x,y);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Right click on webelement.
	public void contextClick(WebDriver driver,WebElement element){
		try
		{
			Actions act = new Actions(driver);
			act.contextClick(element);
		} 
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}	
	}
	//Double click on webelement.
	public void rightClick(WebDriver driver,WebElement element){
		try 
		{
			Actions act = new Actions(driver);
			act.doubleClick(element);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//ClickAndHold action on webelement.
	public void clickAndHold(WebDriver driver,WebElement element){
		try 
		{
			Actions act = new Actions(driver);
			act.clickAndHold(element);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Release action on webelement.
	public void release(WebDriver driver,WebElement element){
		try 
		{
			Actions act = new Actions(driver);
			act.release(element);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Drag and drop from one webelement to another webelement.
	public void dragAndDrop(WebDriver driver,WebElement element1,WebElement element2) {
		try 
		{
			Actions act = new Actions(driver);
			act.dragAndDrop(element1, element2);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}		
	}
	//Drag and drop by offset.
	public void dragAndDrop(WebDriver driver,WebElement element,int x,int y) {
		try 
		{
			Actions act = new Actions(driver);
			act.dragAndDropBy(element, x, y);
		} 
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	/*
	 Actions class methods completed.
	 */
	
	/*
	 Select class Methods
	 */
	//Select element by visible text. 
	public void selectByVisibleText(WebElement element,String text){
		try 
		{
			Select sel = new Select(element);
			sel.selectByVisibleText(text);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Select element by index.
	public void selectByIndex(WebElement element,int index){
		try 
		{
			Select sel = new Select(element);
			sel.selectByIndex(index);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Select element by value.
	public void selectByValue(WebElement element,String value){
		try 
		{
			Select sel = new Select(element);
			sel.selectByValue(value);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Get first selected option .
	public String getFirstSelectedOption(WebElement element){
		try
		{
			String text="";	
			Select sel =new Select(element);
			WebElement element1=sel.getFirstSelectedOption();
			text=element1.getText();
			return text;
		} 
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	return null;
	}
	//Get last selected option
	public String getLastSelectedOption(WebElement element){
		try 
		{
			String text="";	
			List<WebElement>list=element.findElements(By.tagName("option"));
			for (int i = 0; i < list.size(); i++) {
			  text=list.get(list.size()-1).getText();
			}
			return text;
		} 
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	return null;
	}
	//Deselect element by visible text 
	public void deSelectByVisibleText(WebElement element,String text){
		try
		{
			Select sel = new Select(element);
			sel.deselectByVisibleText(text);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Deselect element by index.	
	public void deSelectByIndex(WebElement element,int index){
		try 
		{
			Select sel = new Select(element);
			sel.deselectByIndex(index);
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Deselect element by value.
	public void deSelectByValue(WebElement element,String value){
		try 
		{
			Select sel = new Select(element);
			sel.deselectByValue(value);
		} 
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Deselect all element.
	public void deSelectAll(WebElement element){
		try
		{
			Select sel = new Select(element);
			sel.deselectAll();
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Select all the options. 
	public void selectAll(WebElement element){
		try 
		{
			String text="";	
			Select sel = new Select(element);
			List<WebElement>list=sel.getOptions();
			Iterator<WebElement>it=list.iterator();
			while (it.hasNext()) {
				text=it.next().getText();
			}
			sel.selectByVisibleText(text);
		} 
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	//Select all the options.
	public String[] getAllSelectedOption(WebElement element){
		try 
		{
			Select sel =new Select(element);
			List<WebElement> list=sel.getAllSelectedOptions();
			String []text= new String[list.size()];
			for (int i = 0; i < text.length; i++) {
			  text[i]=list.get(i).getText();
			}
			return text;
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	return null;
	}
	//Getting text from all the selected option
	public String[] getOption(WebElement element){
		try 
		{
			Select sel =new Select(element);
			List<WebElement>list=sel.getOptions();
			String []text= new String[list.size()];
			for (int i = 0; i < text.length; i++) {
			  text[i]=list.get(i).getText();
			}
			return text;
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	return null;
	}
	/*
	 Select class methods completed
	 */
	
	/*
	 Alert class methods
	 */
	//Accepting alert pop-up.
	public void alertOk(){
		try 
		{
			Alert ok=BrowserUtil.driver.switchTo().alert();
			ok.accept();
		} 
		catch (NoAlertPresentException e) 
		{
			e.printStackTrace();
		}
	}
	//Dismissing alert pop-up.
	public void alertCancel(){
		try
		{
			Alert ok=BrowserUtil.driver.switchTo().alert();
			ok.dismiss();
		} 
		catch (NoAlertPresentException e)
		{
			e.printStackTrace();
		}
	}
	//Getting text from alert pop-up.
	public String alertGetText(){
		try
		{
			Alert ok=BrowserUtil.driver.switchTo().alert();
			String text=ok.getText();
			return text;
		} 
		catch (NoAlertPresentException e) 
		{
			e.printStackTrace();
		}
	return null;
	}
	/*
	 Alert class methods completed.
	 */
	
	//Get Window Handles.
	public HashMap<String, String> getWindowHandles(){
		try 
		{
			Set<String> set=BrowserUtil.driver.getWindowHandles();
			Iterator<String>it=set.iterator();
			map.put("parentWindow", it.next());
			map.put("childWindow", it.next());
			return map;
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	return null;
	}
	//Switching to Parent Window.
	public void switchParentWindow(String pWindow){
		try 
		{
			BrowserUtil.driver.switchTo().window(map.get(pWindow));
		}
		catch (NoSuchWindowException e)
		{
			e.printStackTrace();
		}	
	}
	//Switching To Child Window.
	public void switchChildWindow(String cWindow){
		try 
		{
			BrowserUtil.driver.switchTo().window(map.get(cWindow));
		} 
		catch (NoSuchWindowException e) 
		{
			e.printStackTrace();
		}	
	}
	//To Display elements present in page
	public String[] displayAllElementsPresentInModule(String xpathElement){
		try 
		{
			List<WebElement>list=BrowserUtil.driver.findElements(By.xpath(xpathElement));
			String []text= new String[list.size()];
			for (int i = 0; i < text.length; i++) {
			   text[i]=list.get(i).getText();
			}
			return text;
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
		return null;
	}
	//To select all the checkbox present in webpage
	public void selectAllCheckBoxPresentInModule(String xpathElement){
		try 
		{
			List<WebElement>list=BrowserUtil.driver.findElements(By.xpath(xpathElement));
			Iterator<WebElement>it=list.iterator();
			while (it.hasNext()) {
			    it.next().click();
			}
		}
		catch (StaleElementReferenceException e)
	    {
	    	e.printStackTrace();
		}
	}
	
	//To save attachments.
	public FirefoxProfile BrowserUtilNativePopUp(String path,String Mime,int key){
	    try
	    {
			FirefoxProfile p= new FirefoxProfile();
			p.setPreference("BrowserUtil.download.folderList", key);
			p.setPreference("BrowserUtil.helperApps.neverAsk.saveToDisk", Mime);
			p.setPreference("BrowserUtil.download.dir", path);
			return p;
		}
	    catch (Exception e) 
	    {
			e.printStackTrace();
		}
	return null;
	}
	
	//To upload file.
	public void fileAttachmentPopUp(String path) throws AWTException{
		try 
		{
			StringSelection sel = new StringSelection(path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
			Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_CONTROL);
			rob.keyPress(KeyEvent.VK_V);
			rob.keyPress(KeyEvent.VK_ENTER);
		}
		catch (HeadlessException e) 
		{
			e.printStackTrace();
		}
	}
	
	//To close driver control
	public void closeDriver(){
		BrowserUtil.driver.close();
	}
		
	//To quit driver control.
	public void driverQuit(){
		BrowserUtil.driver.quit();	
	}
	
	//Scroll to element.
	public void scrollToElement(WebElement element){
		((JavascriptExecutor)BrowserUtil.driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public static void makeExecutable(String path) throws IOException {
    Set<PosixFilePermission> permissions = new HashSet<>();
    //permissions.add(PosixFilePermission.OWNER_EXECUTE);
    permissions.add(PosixFilePermission.OWNER_READ);
    permissions.add(PosixFilePermission.OWNER_WRITE);
    permissions.add(PosixFilePermission.OWNER_EXECUTE);
    
    /*permissions.add(PosixFilePermission.GROUP_READ);
    permissions.add(PosixFilePermission.GROUP_WRITE);
    permissions.add(PosixFilePermission.GROUP_EXECUTE);
  
    permissions.add(PosixFilePermission.OTHERS_READ);
    permissions.add(PosixFilePermission.OTHERS_WRITE);
    permissions.add(PosixFilePermission.OTHERS_EXECUTE);*/
    File file = new File(path);
    Files.setPosixFilePermissions(file.toPath(), permissions);
  }

  private static String executeCommand(String commandName, String... args) {
    CommandLine cmd = new CommandLine(commandName, args);
    cmd.execute();
    
    String output = cmd.getStdOut();
    if (cmd.getExitCode() == 0 || cmd.getExitCode() ==  128 || cmd.getExitCode() ==  255) {
      return output;
    }
    throw new RuntimeException("exec return code " + cmd.getExitCode() + ": " + output);
  }
}

