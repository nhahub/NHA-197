package elementactions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementActions {

    private WebDriver driver;
    private JavascriptExecutor javascriptExecutor;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        javascriptExecutor = (JavascriptExecutor) this.driver;
    }

    public ElementActions click(By locator) {
        System.out.println("Click on: " + locator.toString());
        try {
            driver.findElement(locator).click();
        } catch (ElementClickInterceptedException | NoSuchElementException
                 | StaleElementReferenceException | TimeoutException exception) {
            scrollIntoElement(locator);
            clickUsingJavaScript(locator);
        }

        return this;
    }

    public ElementActions clickUsingJavaScript(By locator) {
        System.out.println("Click on: " + locator.toString() + " Using JavaScript");
        javascriptExecutor.executeScript("arguments[0].click();", driver.findElement(locator));
        return this;
    }

    public ElementActions scrollIntoElement(By locator) {
        System.out.println("Scrolling to Element: " + locator.toString());
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        return this;
    }

    public ElementActions selectByIndex(By locator, int index) {
        System.out.println("Selecting Option " + index + "from dropdown: " + locator.toString());
        new Select(driver.findElement(locator)).selectByIndex(index);
        return this;
    }

    public ElementActions selectByValue(By locator, String value) {
        System.out.println("Selecting Value: " + value + "from dropdown: " + locator.toString());
        new Select(driver.findElement(locator)).selectByValue(value);
        return this;
    }

    public ElementActions fillField(By locator, String text) {
        clearField(locator);
        System.out.println("Fill " + text + " in " + locator);
        driver.findElement(locator).sendKeys(text);
        return this;
    }

    public ElementActions clearField(By locator) {
        System.out.println("Clearing " + locator);
        driver.findElement(locator).clear();
        return this;
    }

    public String getTextOf(By locator) {
        System.out.println("Getting text from locator: " + locator.toString());
        return driver.findElement(locator).getText();
    }

    public Boolean isDisplayed(By locator) {
        System.out.println("Checking" + locator.toString().split(":", 2)[1] + " if Displayed");
        return driver.findElement(locator).isDisplayed();
    }

    public Boolean isSelected(By locator) {
        System.out.println("Checking" + locator.toString().split(":", 2)[1] + " if Selected");
        return driver.findElement(locator).isSelected();
    }

    public Boolean isClickable(By locator) {
        System.out.println("Checking" + locator.toString().split(":", 2)[1] + " if Clickable");
        return driver.findElement(locator).isEnabled();
    }

    public ElementActions waitForVisibility(By locator, Duration duration) {
        System.out.println("Wait " + duration.toString() + " for " + locator.toString() + " To be visible");
        new WebDriverWait(driver, duration).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this;
    }

    public ElementActions waitForInvisibility(By locator, Duration duration) {
        System.out.println("Wait " + duration.toString() + " for " + locator.toString() + " To be invisible");
        new WebDriverWait(driver, duration).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        return this;
    }

    public ElementActions waitForElementToBeClickable(By locator, Duration duration) {
        System.out.println("Wait " + duration.toString() + " for " + locator.toString() + " To be clickable");
        new WebDriverWait(driver, duration).until(ExpectedConditions.elementToBeClickable(locator));
        return this;
    }

}
