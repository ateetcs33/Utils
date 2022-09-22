package selautomation.common;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.testng.Assert;
import selautomation.constants.ExcelConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelLibrary {
  ExtentReportUtils reportUtils = null;
  
  /**
   * Constructor
   *
   * @param reportUtils
   */
  public ExcelLibrary(ExtentReportUtils reportUtils) {
    this.reportUtils = reportUtils;
    switch (Constants.realm) {
      case Constants.PROD:
      case Constants.PROD_NAVAPP:
      case Constants.STAGING:
      case Constants.STAGING_NAVAPP:
        //ExcelConstants.dataFilePath = ExcelConstants.TEST_MERCHANT_DATA;
        ExcelConstants.dataFilePath = ExcelConstants.LIVE_MERCHANT_DATA;
        break;
      default:
        ExcelConstants.dataFilePath = ExcelConstants.LIVE_MERCHANT_DATA;
        break;
    }
  }
  
  /**
   * Gets the excel data.
   *
   * @param sheetName the sheet name
   * @param tcID the Test case ID
   * @return the Map object with columnName as Key and corresponding Cell data as its Value
   */
  public HashMap<String, String> getExcelDataMap(String sheetName, String tcID) {
    //String dataPath =  System.getProperty("user.dir") + "/src/test/resources/properties/TestData.xlsx";
    String dataPath =  System.getProperty("user.dir") + ExcelConstants.dataFilePath;
    //String dataPath =  "./src/test/resources/properties/TestData.xlsx";
    Workbook wb = null;
    Sheet s = null;
    HashMap<String, String> data = new HashMap<String, String>();
    try {
      FileInputStream fis = new FileInputStream(dataPath);
      wb = WorkbookFactory.create(fis);
      Logger.log("No of Sheets : " + wb.getNumberOfSheets());
      s = wb.getSheet(sheetName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (EncryptedDocumentException e) {
      e.printStackTrace();
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //getLastRowNum() returns an int which is zero based
    //index of the number of rows of data in that sheet
    int rowCount = s.getLastRowNum();
    Logger.log("Row count : " + rowCount);
    String testID = null;
    String key = null;
    String keyValue = null;
    boolean testFound = false;
    for (int row = 0; row <= rowCount; row++) {
      testID  = s.getRow(row).getCell(1).getStringCellValue();
      //Logger.log(testID);
      if (testID.equalsIgnoreCase(tcID)) {
        testFound = true;
        int colCount = s.getRow(row).getLastCellNum();
        Logger.log("Collumn count : " + colCount);
        Logger.log("******************************************************************************************");
        for (int cell = 0; cell < colCount; cell++) {
          key = s.getRow(0).getCell(cell).getStringCellValue();
          keyValue = s.getRow(row).getCell(cell).getStringCellValue();
          Logger.log("Column name : " + key + " ==> and its Value : " + keyValue);
          data.put(key, keyValue);
        }
        Logger.log("******************************************************************************************");
        //return data;
        break;
      }
      if (testFound) {
        break;
      }
    }
    if (!testFound) {
      Logger.log("No data found for the Test Case id: " + tcID + " in Sheet : " + sheetName,true);
      reportUtils.logFail("Fetch test data from Excel",
          "No data found for the Test Case id: " + tcID + " in Sheet : " + sheetName);
      data = null;
    }
    Logger.log("Entire Map :\n" + data);
    return data;
  }
  
  /**
   * Sets the excel data.
   *
   * @param sheetName the sheet name
   * @param tcName to identify the row
   * @param columnHeading to identify the cell
   * @param data the data to be set.
   */
  public void setExcelData(String sheetName, String tcName,
                           String columnHeading, String data) {
    //String dataPath =  System.getProperty("user.dir") + "/src/test/resources/properties/TestData.xlsx";
    String dataPath =  System.getProperty("user.dir") + ExcelConstants.dataFilePath;
    //String dataPath =  "./src/test/resources/properties/TestData.xlsx";
    try {
      FileInputStream fis = new FileInputStream(dataPath);
      Workbook wb = WorkbookFactory.create(fis);
      Sheet s = wb.getSheet(sheetName);
      int colCount = s.getRow(0).getLastCellNum();
      ArrayList<String> columnHeadings = new ArrayList<>();
      for (int cell = 0; cell < colCount; cell++) {
        columnHeadings.add(cell, s.getRow(0).getCell(cell).getStringCellValue());
      }
      int rowCount = s.getLastRowNum();
      int testNameColumn = columnHeadings.indexOf(ExcelConstants.TEST_CASE_NAME);
      int cellNum = columnHeadings.indexOf(columnHeading);
      for (int row = 0; row < rowCount; row++) {
        if (tcName.equalsIgnoreCase(s.getRow(row).getCell(testNameColumn).getStringCellValue())) {
          s.getRow(row).getCell(cellNum).setCellValue(data);
          break;
        }
      }
      FileOutputStream fos = new FileOutputStream(dataPath);
      wb.write(fos);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (EncryptedDocumentException e) {
      e.printStackTrace();
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets the excel data.
   *
   * @param realm the Test case ID
   * @return the Map object with columnName as Key and corresponding Cell data as its Value
   */
  public HashMap<String, String> getConfigDataMap(String realm) {
    //String dataPath =  System.getProperty("user.dir") + "/src/test/resources/properties/TestData.xlsx";
    String dataPath =  System.getProperty("user.dir") + ExcelConstants.dataFilePath;
    Workbook wb = null;
    Sheet s = null;
    HashMap<String, String> data = new HashMap<String, String>();
    try {
      FileInputStream fis = new FileInputStream(dataPath);
      wb = WorkbookFactory.create(fis);
      Logger.log("No of Sheets : " + wb.getNumberOfSheets());
      s = wb.getSheet(ExcelConstants.CONFIG_SHEET);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (EncryptedDocumentException e) {
      e.printStackTrace();
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //getLastRowNum() returns an int which is zero based
    //index of the number of rows of data in that sheet
    int rowCount = s.getLastRowNum();
    Logger.log("Row count : " + rowCount);
    String testID = null;
    String key = null;
    String keyValue = null;
    boolean testFound = false;
    for (int row = 0; row <= rowCount; row++) {
      testID  = s.getRow(row).getCell(0).getStringCellValue();
      //Logger.log(testID);
      if (testID.equalsIgnoreCase(realm)) {
        testFound = true;
        int colCount = s.getRow(row).getLastCellNum();
        Logger.log("Collumn count : " + colCount);
        Logger.log("********************** Config Details for Realm : " + realm + " **********************");
        for (int cell = 0; cell < colCount; cell++) {
          key = s.getRow(0).getCell(cell).getStringCellValue();
          keyValue = s.getRow(row).getCell(cell).getStringCellValue();
          Logger.log("Column name : " + key + " ==> and its Value : " + keyValue);
          data.put(key, keyValue);
        }
        Logger.log("******************************************************************************************");
        //return data;
        break;
      }
      if (testFound) {
        break;
      }
    }
    if (!testFound) {
      Logger.log("No data found for the Realm: " + realm + " in Sheet : " + ExcelConstants.CONFIG_SHEET,true);
      reportUtils.logFail("Fetch test data from Excel",
          "No data found for the Realm: " + realm + " in Sheet : " + ExcelConstants.CONFIG_SHEET);
      Assert.fail("No data found for the Realm: " + realm + " in Sheet : " + ExcelConstants.CONFIG_SHEET);
      data = null;
    }
    Logger.log("Entire Map :\n" + data);
    return data;
  }
  
}
