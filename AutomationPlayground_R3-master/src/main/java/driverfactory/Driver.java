package driverfactory;

import browseractions.BrowserActions;
import elementactions.ElementActions;
import listeners.webdriver.DriverListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import static utilities.PropertiesManager.frameworkConfig;

public class Driver {

//    private WebDriver driver;
    private ThreadLocal<WebDriver> driver;

    public Driver(){
        String driverType = frameworkConfig.getProperty("BrowserType");
        WebDriver undecoratedDriver = getDriverFactory(driverType).startDriver();
        assert undecoratedDriver != null;
        driver = new ThreadLocal<>();
        driver.set(new EventFiringDecorator<>(WebDriver.class,
                new DriverListener(undecoratedDriver)).decorate(undecoratedDriver));

        System.out.println("Starting the execution via " + driverType + " driver");
        driver.get().manage().window().maximize();

        if(!frameworkConfig.getProperty("BaseUrl").isEmpty()) {
            driver.get().navigate().to(frameworkConfig.getProperty("BaseUrl"));
        }

    }

    public Driver(String driverType){
        WebDriver undecoratedDriver = getDriverFactory(driverType).startDriver();
        assert undecoratedDriver != null;
        driver = new ThreadLocal<>();
        driver.set(new EventFiringDecorator<>(WebDriver.class,
                new DriverListener(undecoratedDriver)).decorate(undecoratedDriver));

        System.out.println("Starting the execution via " + driverType + " driver");
        driver.get().manage().window().maximize();

        if(!frameworkConfig.getProperty("BaseUrl").isEmpty()) {
            driver.get().navigate().to(frameworkConfig.getProperty("BaseUrl"));
        }

    }

    private DriverAbstract getDriverFactory(String driverType) {

        switch(driverType) {
            case "CHROME": {
                return new ChromeDriverFactory();
            }

            case "FIREFOX": {
                return new FirefoxDriverFactory();
            }

            case "EDGE": {
                return new EdgeDriverFactory();
            }

            default:{
                throw new IllegalStateException("Unexpected Driver: " + driverType);
            }
        }
    }

    public WebDriver get() {
        return driver.get();
    }

    public void quit() {
        driver.get().quit();
    }

    public ElementActions element() {
        return new ElementActions(driver.get());
    }

    public BrowserActions browser() {
        return new BrowserActions(driver.get());
    }

}
