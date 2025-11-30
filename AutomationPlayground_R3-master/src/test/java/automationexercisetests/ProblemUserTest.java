package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class ProblemUserTest {
    Driver driver;
    Homepage homepage;
    LoginPage loginPage;
    ProductsPage productsPage;
    cartPage cartpage;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartpage = new cartPage(driver);
    }

    @Test(priority = 1)
    public void problemUserCanLogin() {
        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("problem_user", "secret_sauce")
                .clickOnLoginButton()
                .checkThatCarIconShouldBeDisplayed();

        homepage.checkThatUserShouldBeLoggedIn();
    }

    @Test(priority = 2)
    public void verifyProductsAreDisplayed() {
        productsPage.checkThatProductsAreDisplayed();
    }

    @Test(priority = 3)
    public void attemptToAddProductToCart() {
        productsPage.addProductToCart("Sauce Labs Backpack");
    }

    @Test(priority = 4)
    public void verifyCartBehavior() {
        int cartCount = productsPage.getCartCount();
        System.out.println("✓ Cart count for problem user: " + cartCount);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
