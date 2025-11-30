package pages;

import driverfactory.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Homepage {

    private Driver driver;

    By ButtonOnMenu = By.id("react-burger-menu-btn");
    By logoutLink = By.id("logout_sidebar_link");
    By ResetAppSrore = By.id("reset_sidebar_link");
    By carIcon=By.xpath("//a[@class=\"shopping_cart_link\"]");
    By loginButton = By.xpath("//input[@name=\"login-button\"]");
    By passwordField = By.id("password");
    By nameField = By.id("user-name");
    By ProductsLogo=By.xpath("//span[@class=\"title\"]");


    public Homepage(Driver driver) {
        this.driver = driver;
    }

    /******************************* Assertions ***********************************/



    @Step("Check That User Should be Logged in")
    public Homepage checkThatUserShouldBeLoggedIn() {
        Assert.assertTrue(driver.element().isDisplayed(ProductsLogo));
        return this;
    }

    @Step("Check That User Should Be Logged Out")
    public Homepage checkThatUserShouldBeLoggedOut() {
        driver.element().waitForVisibility(loginButton, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(loginButton));
        System.out.println("User logged out successfully!");
        return this;
    }




    /******************************** Actions ***********************************/
    @Step("Click on menu  Button")
    public Homepage clickOnMenuButton(){
        driver.element().
                scrollIntoElement(ButtonOnMenu).
                waitForElementToBeClickable(ButtonOnMenu,Duration.ofSeconds(10)).
                click(ButtonOnMenu);
        return this;
    }



    @Step("Click on Logout link")
    public Homepage clickOnLogoutLink() {
        driver.element()
                .scrollIntoElement(logoutLink)
                .waitForElementToBeClickable(logoutLink, Duration.ofSeconds(10))
                .click(logoutLink);

        driver.element().waitForVisibility(loginButton, Duration.ofSeconds(10));
        System.out.println("User logged out successfully!");
        return this;
    }

}
