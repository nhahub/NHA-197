package pages;

import driverfactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage {

    private Driver driver;
    private WebDriverWait wait;
    By loginForm = By.cssSelector("div.login-box");
    By nameField = By.id("user-name");
    By passwordField = By.id("password");
    By logoutLink = By.xpath("(//a[@class=\"bm-item menu-item\"])[3]");
    By loginButton = By.xpath("//input[@name=\"login-button\"]");
    By incorrectCredentialsError = By.xpath("//h3[@data-test=\"error\"]");
    By carIcon=By.xpath("//a[@class=\"shopping_cart_link\"]");
    By errorMessage=By.xpath("//h3[@data-test=\"error\"]");
    By ButtonOnMenu = By.id("react-burger-menu-btn");


    public LoginPage(Driver driver) {
        this.driver = driver;
    }

    /********************************** Assertions *********************************/

    public LoginPage checkThatUserShouldBeNavigatedToLoginSignUpPage() {

        Assert.assertTrue(driver.element().isDisplayed(loginButton));
        return this;
    }

    public LoginPage checkThatErrorShouldBeDisplayedWhenEnteringWrongCredentials() {
        driver.element().waitForVisibility(incorrectCredentialsError, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(incorrectCredentialsError));
        return this;
    }
    public LoginPage checkThatCarIconShouldBeDisplayed() {
        Assert.assertTrue(driver.element().isDisplayed(carIcon));
        return this;
    }

    public LoginPage verifyErrorMessageDisplayed() {
        driver.element().waitForVisibility(errorMessage, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(errorMessage),
                "Error message is not displayed!");
        System.out.println("✓ Error message is displayed");
        return this;
    }
    public LoginPage verifyErrorMessageContains(String expectedText) {
        String actualMessage = getErrorMessage();
        Assert.assertTrue(actualMessage.toLowerCase().contains(expectedText.toLowerCase()),
                "Error message doesn't contain: " + expectedText);
        System.out.println("✓ Error message contains: " + expectedText);
        return this;
    }


    /********************************* Actions ************************************/

    public String getErrorMessage() {
        driver.element().waitForVisibility(errorMessage, Duration.ofSeconds(10));
        String message = driver.element().getTextOf(errorMessage).trim();
        System.out.println("Error message: " + message);
        return message;
    }

    public LoginPage fillLoginForm(String name, String password) {
        driver.element().fillField(nameField, name);
        driver.element().fillField(passwordField, password);
        return this;
    }


public LoginPage clickOnLoginButton() {

        driver.element().click(loginButton);


    return this;
    }
    public LoginPage clickOnLogoutLink() {
      driver.element().waitForVisibility(ButtonOnMenu,Duration.ofSeconds(10)).
              waitForElementToBeClickable(ButtonOnMenu,Duration.ofSeconds(10)).
              click(ButtonOnMenu);
        driver.element().waitForVisibility(logoutLink,Duration.ofSeconds(10)).
                waitForElementToBeClickable(logoutLink,Duration.ofSeconds(10)).
                click(logoutLink);
        return this;}

}
