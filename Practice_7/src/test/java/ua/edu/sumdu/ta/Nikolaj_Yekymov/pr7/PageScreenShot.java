package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr7;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class PageScreenShot {

    private WebDriver driver;
    private WebElement screenFileName;
    private File screenFile;
    private String path;

    public PageScreenShot(WebDriver driver) {
        this.driver = driver;
    }

    public void getPageScreenShot() {

        screenFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        screenFileName = driver.findElement(By.tagName("h1"));
        path = "./target/screenshots/" + screenFileName.getText() + ".png";
        try {
            FileUtils.copyFile(screenFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
