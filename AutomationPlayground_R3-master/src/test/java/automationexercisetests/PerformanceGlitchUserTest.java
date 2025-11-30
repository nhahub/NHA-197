package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class PerformanceGlitchUserTest {
    Driver driver;
    Homepage homepage;
    LoginPage loginPage;
    ProductsPage productsPage;
    cartPage cartpage;
    CheckoutPage checkoutPage;
    CheckoutOverview checkoutOverview;
    SuccessPage successPage;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartpage = new cartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        checkoutOverview = new CheckoutOverview(driver);
        successPage = new SuccessPage(driver);
    }

    @Test(priority = 1)
    public void performanceUserCanLogin() {
        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("performance_glitch_user", "secret_sauce")
                .clickOnLoginButton()
                .checkThatCarIconShouldBeDisplayed();

        homepage.checkThatUserShouldBeLoggedIn();

    }

    @Test(priority = 2)
    public void addProductToCart() {
        productsPage.addProductToCart("Sauce Labs Backpack");
    }

    @Test(priority = 3)
    public void completeCheckoutFlow() {
        cartpage.navigateToCart()
                .proceedToCheckout();

        checkoutPage.fillLoginForm("Habiba", "Ayman", "12345")
                .clickContinueButton()
                .checkThatUserNavigateToCheckoutOverViewPageSuccessfull();

        checkoutOverview.clickOnFinishButton();

        successPage.verifyThatUserAtSuccessPage();

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
