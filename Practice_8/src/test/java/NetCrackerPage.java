import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NetCrackerPage {

    private WebDriver driver;
    private Map<String, String> positionsList;
    private WebElement sumySelect;
    private WebElement positionsListElement;
    private List<WebElement> positionsDivList;

    public NetCrackerPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void showOnlySumyPositions() {
        sumySelect = driver.findElement(By.id("location_1752"));
        sumySelect.click();
    }

    public Map<String, String> getPositionsList() {

        positionsList = new HashMap<String, String>();
        positionsListElement = driver.findElement(By.id("positionslist"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        positionsDivList = positionsListElement.findElements(By.tagName("div"));
        for (int i = 0; i < positionsDivList.size(); i++) {
            String s = positionsDivList.get(i).getAttribute("class");
            if (("job result active").equals(s)) {
                String key = positionsDivList.get(i).findElement(By.tagName("h3")).getText();
                String value = positionsDivList.get(i).getAttribute("data-category");
                positionsList.put(key,value);
            }
        }
        return positionsList;
    }
}
