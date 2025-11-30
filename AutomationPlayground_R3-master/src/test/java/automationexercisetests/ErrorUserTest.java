package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class ErrorUserTest {
    Driver driver;
    Homepage homepage;
    LoginPage loginPage;
    ProductsPage productsPage;
    cartPage cartpage;
    CheckoutPage checkoutPage;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartpage = new cartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test(priority = 1)
    public void errorUserCanLogin() {
        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("error_user", "secret_sauce")
                .clickOnLoginButton()
                .checkThatCarIconShouldBeDisplayed();

        homepage.checkThatUserShouldBeLoggedIn();
    }

    @Test(priority = 2)
    public void attemptToAddProduct() {

            productsPage.addProductToCart("Sauce Labs Backpack");


    }

    @Test(priority = 3)
    public void attemptCheckout() {

            cartpage.navigateToCart()
                    .proceedToCheckout();

            checkoutPage.fillLoginForm("Habiba", "Ayman", "12345")
                    .clickContinueButton();



    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
