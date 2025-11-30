package pages;

import driverfactory.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.time.Duration;

public class SuccessPage {
    Driver driver;
    public SuccessPage(Driver driver){
        this.driver=driver;
    }
    By Successmessage= By.xpath("//h2[@class=\"complete-header\"]");
    By BackToHomeButton=By.id("back-to-products");
    /**************************************Actions**************************************/
@Step("click On Back To Home Button")
    public ProductsPage clickOnBackToHomeButton() {
        driver.element()
                .waitForElementToBeClickable(BackToHomeButton, Duration.ofSeconds(15))
                .scrollIntoElement(BackToHomeButton)
                .clickUsingJavaScript(BackToHomeButton);
        return new ProductsPage(driver);
    }
    @Step("Get success message text")
    public SuccessPage getSuccessMessage() {
        driver.element().waitForVisibility(Successmessage, Duration.ofSeconds(10));
        String message = driver.element().getTextOf(Successmessage).trim();
        System.out.println("Success message: " + message);
        return this;
    }


    /**************************************Assertions**************************************/
    @Step("verify That User At Success Page")
    public SuccessPage verifyThatUserAtSuccessPage(){
        driver.element().waitForVisibility(Successmessage, Duration.ofSeconds(10));
        String actualMessage = driver.element().getTextOf(Successmessage).trim();

        Assert.assertEquals(actualMessage, "Thank you for your order!",
                "Success message mismatch!");
        System.out.println("✓ User is on Success page");
        return this;
    }
    @Step("Verify success message is displayed")
    public SuccessPage verifySuccessMessageDisplayed() {
        driver.element().waitForVisibility(Successmessage, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(Successmessage),
                "Success message is not displayed!");
        System.out.println("✓ Success message is displayed");
        return this;
    }
    @Step("Verify back to home button is displayed")
    public SuccessPage verifyBackButtonDisplayed() {
        driver.element().waitForVisibility(BackToHomeButton, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(BackToHomeButton),
                "Back to home button is not displayed!");
        System.out.println("✓ Back to home button is displayed");
        return this;
    }


}
