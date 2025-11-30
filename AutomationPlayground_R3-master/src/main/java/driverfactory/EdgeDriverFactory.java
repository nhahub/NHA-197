package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static utilities.PropertiesManager.frameworkConfig;

public class EdgeDriverFactory extends DriverAbstract{

    @Override
    public WebDriver startDriver() {
        EdgeOptions options = new EdgeOptions();
        if (frameworkConfig.getProperty("headlessMode").equalsIgnoreCase("true")) {
            options.addArguments("--headless");
        }
        driver = new EdgeDriver(options);
        return driver;
    }
}
