package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static utilities.PropertiesManager.frameworkConfig;

public class FirefoxDriverFactory extends DriverAbstract{

    @Override
    public WebDriver startDriver() {
        FirefoxOptions options = new FirefoxOptions();
        if (frameworkConfig.getProperty("headlessMode").equalsIgnoreCase("true")) {
            options.addArguments("--headless");
        }
        driver = new FirefoxDriver(options);
        return driver;
    }
}
