/*
 * 
 */
package com.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

// TODO: Auto-generated Javadoc
/**
 * The Class CreatingXml.
 */
public class CreatingXml {
	
	/**
	 * Read test names.
	 *
	 * @return the list of TestCase names
	 */
	public static String controllerPath = "./src/test/resources/Controller/ExecutionController.xlsx";
	public static List<String> readTestNames()
	{
		
		List<String> testcasenames=new ArrayList<String>();
	//	String path="./src/test/resources/Controller/ExecutionController.xlsx";
		ExcelLibrary excel = new ExcelLibrary();
		int testcasecount = excel.getRowCount(controllerPath, "ExeController");
		//System.out.println(testcasecount);
		for(int i=0;i<=testcasecount;i++)
		{
			String status = excel.getExcelData(controllerPath,"ExeController", i, 3);
			if(status.equalsIgnoreCase("yes"))
			{
				String testcasename = excel.getExcelData(controllerPath,"ExeController", i, 2);
				testcasenames.add(testcasename);
			}
		}
			return testcasenames;
		
	}
	
	/**
	 * The main method.
	 */
	public static void main(String[] args) throws IOException {
	//public void creatTestNGXML() {
		System.out.println("Creating Testng XML");
		XmlSuite suite = new XmlSuite();
		suite.setName("TmpSuite");
		 
		XmlTest test = new XmlTest(suite);
		test.setName("TmpTest");
		List<XmlClass> classes = new ArrayList<XmlClass>();
		List<String>testCasesToExecute=readTestNames();
		for(int i=0;i<testCasesToExecute.size();i++)
		{
			System.out.println(testCasesToExecute.get(i));
			classes.add(new XmlClass(testCasesToExecute.get(i)));
		}	
		/*classes.add(new XmlClass("scripts.ToVerifyInvalidLoginLogout"));
		classes.add(new XmlClass("scripts.ToVerifyTheVersion"));
		classes.add(new XmlClass("scripts.validLoginLogout"));*/
		test.setXmlClasses(classes) ;
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		//System.out.println(suite);
		File file = new File("testng.xml");
        //System.out.println("file"+file);

        FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.write(suite.toXml());
	        writer.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}			
}
