package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr7;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.io.*;
import org.apache.commons.io.FileUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebSiteTest {
	
	@Test
    public void testCase() {
		
		System.setProperty("webdriver.gecko.driver","./src/test/resources/geckodriver.exe");
        //Step 1
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.google.com.ua/");
        //Step 2
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("NetCracker Su");
        //Steps 3,4,5
        WebElement popUpMenu = driver.findElement(By.xpath(".//*[@id='sbtc']/div[2]/div[2]/div[1]"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> popUpList = popUpMenu.findElements(By.tagName("li"));

        for (int i = 0; i < popUpList.size(); i++) {
            String s = popUpList.get(i).getText();
            if (s.equals("NetCracker Sumy")) {
                popUpList.get(i).click();
                break;
            } else if (i == popUpList.size()-1) {
                element.sendKeys("my");
                element.submit();
            }
        }

        //Step 6
        WebElement searchLinks = driver.findElement(By.className("srg"));
        List<WebElement> searchLinksList = searchLinks.findElements(By.tagName("a"));
        WebDriver nextDriver = new FirefoxDriver();
        for (int i = 0; i < searchLinksList.size(); i++) {
            String s = searchLinksList.get(i).getAttribute("href");
            if (s.equals("NetCracker. :: Суми")) {
                driver.quit();
                nextDriver.get(s);
                break;
            } else if (i == searchLinksList.size()-1) {
                driver.quit();
                nextDriver.get("http://www.netcracker.com/ukr/vacancies");
            }
        }

        //Step 7
        WebElement nextElement = nextDriver.findElement(By.id("location_1752"));
        nextElement.click();
		
        //Step 8
        File screenFile = ((TakesScreenshot)nextDriver).getScreenshotAs(OutputType.FILE);
        WebElement h1 = nextDriver.findElement(By.tagName("h1"));
        String path = "./target/screenshots/" + h1.getText() + ".png";
        try {
            FileUtils.copyFile(screenFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
        //Step 9
        Map<String, String> hashMap = new HashMap<String, String>();

        WebElement positionslist = nextDriver.findElement(By.id("positionslist"));
        nextDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        List<WebElement> positionsDivList = positionslist.findElements(By.tagName("div"));
        for (int i = 0; i < positionsDivList.size(); i++) {
            String s = positionsDivList.get(i).getAttribute("class");
            if (s.equals("job result active")) {
				String key = positionsDivList.get(i).findElement(By.tagName("h3")).getText();
				String value = positionsDivList.get(i).getAttribute("data-category");
                hashMap.put(key,value);
            }
        }
		
		//Step 10
        Map<String, String> expectedHashMap = new HashMap<String, String>();
        expectedHashMap.put("IT Help Desk Engineer","Engineering");
        expectedHashMap.put("Senior Technical Solution Support Engineer","Customer Assurance");
        expectedHashMap.put("Control Inspector","Customer Assurance");
        expectedHashMap.put("Release Manager","Customer Assurance");
        expectedHashMap.put("Junior TA Engineer","Customer Assurance");
        expectedHashMap.put("OSG Specialist (Operations Support Group)","PMO");
        expectedHashMap.put("Customer Support Analyst","Customer Assurance");
        expectedHashMap.put("Senior QA Engineer","Customer Assurance");
        expectedHashMap.put("Technical Writer","Knowledge Management and Research");
        expectedHashMap.put("Technical Support Manager","Customer Assurance");
        expectedHashMap.put("Wiki Support Specialist","Knowledge Management and Research");
        expectedHashMap.put("Customer Support Analyst (Business Trips to Canada)","Customer Assurance");
        expectedHashMap.put("QA Engineer","Customer Assurance");
        expectedHashMap.put("Assistant of Trainings Sector (with fluent English)","HR and Administration");
        expectedHashMap.put("Junior QA Engineer","Customer Assurance");
        expectedHashMap.put("TA Engineer","Customer Assurance");
        expectedHashMap.put("Technical Solution Support Engineer","Customer Assurance");
        expectedHashMap.put("Senior TA Engineer","Customer Assurance");

        boolean actual = false;
        assertEquals("Size of open positions collection is incorrect", 18, hashMap.size());
        for (Map.Entry expectedEntry : expectedHashMap.entrySet()) {
            for (Map.Entry actualEntry : hashMap.entrySet()) {
                if (expectedEntry.equals(actualEntry)) {
                    actual = true;
                    break;
                } else {
                    actual = false;
                }
            }
            if (!actual) {
                break;
            }
        }
        assertEquals("One or more elements of hashMap are absent", true, actual);
        nextDriver.quit();
    }
}
