import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class GoogleResultPage {

    private WebDriver driver;
    private WebElement searchLinks;
    private List<WebElement> searchLinksList;
    private WebDriver nextDriver;

    public GoogleResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public NetCrackerPage toNeededLink(String link, String alternativeLink) {

        searchLinks = driver.findElement(By.className("srg"));
        searchLinksList = searchLinks.findElements(By.tagName("a"));
        nextDriver = new FirefoxDriver();
        for (int i = 0; i < searchLinksList.size(); i++) {
            String s = searchLinksList.get(i).getAttribute("href");
            if (link.equals(s)) {
                driver.quit();
                nextDriver.get(s);
                break;
            } else if (i == searchLinksList.size()-1) {
                driver.quit();
                nextDriver.get(alternativeLink);
            }
        }
        return new NetCrackerPage(nextDriver);
    }
}
