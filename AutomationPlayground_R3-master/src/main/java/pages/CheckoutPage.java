package pages;

import driverfactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CheckoutPage {
    private Driver driver;


    public CheckoutPage(Driver driver) {
        this.driver = driver;
    }


By CheckOutButton=By.id("checkout");
By fillFirstName=By.name("firstName");
By fillLastName=By.name("lastName");
By fillpostalCode=By.name("postalCode");
By cancelButton=By.name("cancel");
By ContinueButton=By.name("continue");
By ErrorMessage=By.xpath("//h3[@data-test=\"error\"]");
By CheckoutPageLogo=By.xpath("//span[@class=\"title\"]");
By CheckoutOverViewLogo=By.xpath("//span[@class=\"title\"]");
    /*********************************Actions**********************/

public   CheckoutPage fillLoginForm(String fname, String lname,String postcode) {
    driver.element().fillField(fillFirstName, fname);
    driver.element().fillField(fillLastName, lname);
    driver.element().fillField(fillpostalCode,postcode );
    return this;
}

public   cartPage ClickonCancelButton(){
    driver.element().
            scrollIntoElement(cancelButton).
            waitForElementToBeClickable(cancelButton,Duration.ofSeconds(10)).
            click(cancelButton);
    return new cartPage(driver);
}

    public CheckoutPage fillLoginFormBycompleteData(String fname, String lname) {
        driver.element().fillField(fillFirstName, fname);
        driver.element().fillField(fillLastName, lname);
        // ✅ لاحظ: مش بنملأ postal code عشان نختبر الـ validation
        System.out.println("✓ Filled incomplete form (missing postal code)");
        return this;
    }

    // ✅ أضف method للضغط على Continue بدون تحقق
    public CheckoutPage clickContinueButton() {
        driver.element()
                .scrollIntoElement(ContinueButton)
                .waitForElementToBeClickable(ContinueButton, Duration.ofSeconds(10))
                .click(ContinueButton);
        System.out.println("✓ Clicked Continue button");
        return this;
    }
    

    public CheckoutOverview proceedToCheckoutOverview() {
        driver.element()
                .scrollIntoElement(ContinueButton)
                .waitForElementToBeClickable(ContinueButton, Duration.ofSeconds(10))
                .click(ContinueButton);
        System.out.println("✓ Clicked Continue button");
        return new CheckoutOverview(driver);
    }


/***************************************Assertion*************************/
public CheckoutPage  checkThatErrorShouldBeDisplayedWhenDonnotFillAllData() {
    driver.element().waitForVisibility(ErrorMessage, Duration.ofSeconds(10));
    Assert.assertTrue(driver.element().isDisplayed(ErrorMessage));
    return this;
}
    public CheckoutPage checkThatUserNavigateToCheckoutPageSuccessfull() {
        driver.element().waitForVisibility(CheckoutPageLogo, Duration.ofSeconds(10));

        String actualTitle = driver.element().getTextOf(CheckoutPageLogo).trim();

        Assert.assertEquals(actualTitle, "Checkout: Your Information",
                "User is not on Checkout page! Actual title: " + actualTitle);
        System.out.println("✓ User navigated to Checkout page successfully");
        return this;
    }
    
    public CheckoutOverview checkThatUserNavigateToCheckoutOverViewPageSuccessfull() {
        // Increased wait time for page load
        driver.element().waitForVisibility(CheckoutOverViewLogo, Duration.ofSeconds(15));
        String actualTitle = driver.element().getTextOf(CheckoutOverViewLogo).trim();

        Assert.assertEquals(actualTitle, "Checkout: Overview",
                "User is not on Checkout Overview page! Actual title: " + actualTitle);
        System.out.println("✓ User navigated to Checkout Overview page successfully");
        return new CheckoutOverview(driver);
    }



}
