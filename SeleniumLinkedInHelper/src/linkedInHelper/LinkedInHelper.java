package linkedInHelper;

import utility.UserSelection;
import myComponents.MyWriter;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class LinkedInHelper {
	
	private static String loadingTimeOut="10000";
	private static int totalTimes;
	private static String userName;
	private static String userPin;
	private static String website;
	private static String browserType;
	private static String browserLocation;
	private static String browserInfor;
	private static int portNum;
	private static UserSelection selection;
	private static MyWriter writer;
	
	public LinkedInHelper(){
		
		LinkedInHelper.userName = "785003254@qq.com";
		LinkedInHelper.userPin = "zhu785003254";
		LinkedInHelper.website= "https://www.linkedin.com";
		LinkedInHelper.browserType = "firefox"; ;
		LinkedInHelper.browserLocation = "D:/Program Files (x86)/Mozilla Firefox/firefox.exe";
		LinkedInHelper.browserInfor = browserType + " " + browserLocation;
		LinkedInHelper.portNum= 4444;
		LinkedInHelper.totalTimes=50;
		LinkedInHelper.selection=UserSelection.ENDORSING;
		writer=new MyWriter(false);
			
	}
	
	public LinkedInHelper(String userN,String userP,String web,String broType,String broLoc, int portN,int tot, UserSelection us,MyWriter w){
		LinkedInHelper.userName=userN;
		LinkedInHelper.userPin=userP;
		LinkedInHelper.website=web;
		LinkedInHelper.browserType=broType;
		LinkedInHelper.browserLocation=broLoc;
		LinkedInHelper.browserInfor = browserType + " " + browserLocation;
		LinkedInHelper.portNum=portN;
		LinkedInHelper.totalTimes=tot;
		LinkedInHelper.selection=us;
		LinkedInHelper.writer=w;
	}
	
	
	
	public void runTool() throws InterruptedException {
		

		// Instantiate the RC Server
		Selenium selenium = new DefaultSelenium("localhost", portNum,
				browserInfor, website);
		selenium.start(); // Start
		selenium.open("/"); // Open the URL
		selenium.windowMaximize();

		
		try{
			loginInLinkedIn(userName, userPin, selenium);
			if(selection.equals(UserSelection.ADDCONNECTION)){
				autoAddConnections(totalTimes, selenium);
			}else{
				autoENDORSING(totalTimes, selenium);
			}
						
		}
		finally{
			selenium.close();
			selenium.stop();
		}
	}

	/**
	 * This is for logging in LinkedIn Profile automatically.
	 * 
	 * @param userName
	 * @param userPin
	 * @param selenium
	 * @throws InterruptedException
	 */
	public static void loginInLinkedIn(String userName, String userPin,
			Selenium selenium) throws InterruptedException {
		selenium.focus("id=session_key-login");
		selenium.type("css=input[name=\"session_key\"]", userName);
		selenium.focus("id=session_password-login");
		selenium.type("css=input[name=\"session_password\"]", userPin);
		Thread.sleep(2000);
		//selenium.waitForPageToLoad(loadingTimeOut);
		safeClick(
				selenium,
				"xpath=.//*[@id='header']/div/div/form/fieldset/ul/li[3]/input",
				UserSelection.DEFAULT);
		selenium.waitForPageToLoad(loadingTimeOut);
	}

	/**
	 * This is for add connections automatically.
	 * 
	 * @param totalTimes
	 * @param selenium
	 * @throws InterruptedException
	 */
	public static void autoAddConnections(int totalTimes, Selenium selenium)
			throws InterruptedException {

		safeClick(
				selenium,
				"xpath=.//*[@id='body']/div/div[2]/div[3]/div[1]/div[2]/div[1]/h3/a",
				UserSelection.DEFAULT);
		Thread.sleep(3000);
		for (int time = 1; time < totalTimes; time++) {
			for (int i = 1; i <= 12; i++) {
				String xpath = "xpath=/html/body/div[5]/div/div[2]/div/div/div[1]/ul/li["
						+ i + "]/div[1]/button[1]";
				safeClick(selenium, xpath, UserSelection.DEFAULT);
				Thread.sleep(1000);

			}
			Thread.sleep(3000);
		}
	}

	/**
	 * This is for ENDORSING all connections automatically.
	 * 
	 * @param totalTimes
	 * @param selenium
	 * @throws InterruptedException
	 */
	public static void autoENDORSING(int totalTimes, Selenium selenium)
			throws InterruptedException {

		safeClick(selenium,
				"xpath=.//*[@id='header']/div[2]/div/ul[1]/li[3]/a",
				UserSelection.DEFAULT);
		selenium.waitForPageToLoad(loadingTimeOut);
		Thread.sleep(2000);

		for (int id = 1; id <= totalTimes; id++) {
			for (int j = 1; j <= id*2; j++) {
				selenium.keyPressNative("40");
			}
			String targetPerson = "xpath=.//*[@id='wrapper']/section[1]/ul/li["
					+ id + "]/div[3]/h3/a";
			safeClick(selenium, targetPerson, UserSelection.DEFAULT);
			//selenium.waitForPageToLoad(loadingTimeOut);
			Thread.sleep(5000);
			safeClick(
					selenium,
					"xpath=.//*[@id='top-card']/div/div[1]/div/div[2]/div[2]/div/div/dl/dd[4]/a",
					UserSelection.DEFAULT);
			Thread.sleep(3000);
 			//selenium.waitForPageToLoad(loadingTimeOut);
			safeClick(selenium, "xpath=.//*[@id='ENDORSING-submit']",
					UserSelection.ENDORSING);
			Thread.sleep(3000);
			
			safeClick(selenium,
					"xpath=.//*[@id='header']/div[2]/div/ul[1]/li[3]/a",
					UserSelection.DEFAULT);
			Thread.sleep(5000);
			//selenium.waitForPageToLoad(loadingTimeOut);
		}

	}

	public static void safeClick(Selenium s, String path, UserSelection type) {

		if (s.isElementPresent(path)) {
			s.click(path);

			if (type.equals(UserSelection.ENDORSING)) {
				writer.write("Successfully ENDORSING for : "
						+ safeGetText(s, "//*[@id='name']/h1/span/span[1]"));
			} else {
				writer.write("Successfuly Click " + safeGetText(s, path));
				// writer.write("Successfuly Click Path: "+path);
			}
		} else {

			if (type.equals(UserSelection.ENDORSING)) {
				writer.write("Nothing to ENDORSING for : "
						+ safeGetText(s, "//*[@id='name']/h1/span/span[1]"));
			} else {
				s.refresh();
				s.waitForPageToLoad(loadingTimeOut);
				if (s.isElementPresent(path)) {
					s.click(path);
				}else{
					writer.write("Error! Path:" + path + " is not found");
				}
				
			}

		}
	}

	public static String safeGetText(Selenium s, String path) {

		if (s.isElementPresent(path)) {
			return s.getText(path);
		} else {

		}

		return "";

	}

}