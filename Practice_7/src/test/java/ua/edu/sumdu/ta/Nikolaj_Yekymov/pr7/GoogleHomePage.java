import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleHomePage {

    private WebDriver driver;
	private WebElement googleSearchField;
	private WebElement popUpMenu;
	private List<WebElement> popUpList;

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public GoogleResultPage googleSearch(String first_partSrchString, String second_partSrchString, String fullSrchString) {

        googleSearchField = driver.findElement(By.name("q"));
        googleSearchField.sendKeys(first_partSrchString);
        popUpMenu = driver.findElement(By.xpath(".//*[@id='sbtc']/div[2]/div[2]/div[1]"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        popUpList = popUpMenu.findElements(By.tagName("li"));

        for (int i = 0; i < popUpList.size(); i++) {
            String s = popUpList.get(i).getText();
            if (fullSrchString.equals(s)) {
                popUpList.get(i).click();
                break;
            } else if (i == popUpList.size()-1) {
                googleSearchField.sendKeys(second_partSrchString);
                googleSearchField.submit();
            }
        }
        return new GoogleResultPage(driver);
    }

}
