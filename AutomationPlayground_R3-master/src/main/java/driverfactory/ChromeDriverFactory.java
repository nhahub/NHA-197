package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static utilities.PropertiesManager.frameworkConfig;

public class ChromeDriverFactory extends DriverAbstract{

    @Override
    public WebDriver startDriver() {
        ChromeOptions options = new ChromeOptions();
        if (frameworkConfig.getProperty("headlessMode").equalsIgnoreCase("true")) {
            options.addArguments("--headless");
        }
        driver = new ChromeDriver(options);
        return driver;
    }
}
