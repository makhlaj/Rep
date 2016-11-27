package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr7;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class WebSiteTest {

    private WebDriver driver;

    @BeforeClass
    public static void geckoDriverInPath() {
        System.setProperty("webdriver.gecko.driver","./src/test/resources/geckodriver.exe");
    }

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.google.com.ua/");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCase() {
        //Step 1
        GoogleHomePage homePage = new GoogleHomePage(driver);
        //Step 2,3,4
        GoogleResultPage resultPage = homePage.googleSearch("NetCracker Su", "my", "NetCracker Sumy");
        //Step 5,6
        NetCrackerPage netCrackerPage = resultPage.toNeededLink("NetCracker. :: Суми","http://www.netcracker.com/ukr/vacancies");
        //Step 7
        netCrackerPage.showOnlySumyPositions();
        //Step 8
        driver = netCrackerPage.getDriver();
        PageScreenShot screenShot = new PageScreenShot(driver);
        screenShot.getPageScreenShot();
        //Step 9
        Map<String, String> positionsList = netCrackerPage.getPositionsList();
        //Step 10
        positionsListChecking(positionsList);
    }

    private void positionsListChecking(Map<String, String> actualHashMap) {

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

        assertEquals("Size of open positions collection is incorrect", 18, actualHashMap.size());
        assertEquals("One or more elements of hashMap are absent or incorrect", expectedHashMap, actualHashMap);
    }
}
