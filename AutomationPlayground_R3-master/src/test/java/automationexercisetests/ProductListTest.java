package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class ProductListTest {
    Driver driver;
    Homepage homepage;
    LoginPage loginPage;
    ProductsPage productspage;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);
        productspage = new ProductsPage(driver);
    }

    @Test(priority = 1)
    public void userCanLoginSuccessfully() {
        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("standard_user", "secret_sauce")
                .clickOnLoginButton()
                .checkThatCarIconShouldBeDisplayed();

        homepage.checkThatUserShouldBeLoggedIn();
    }

    @Test(priority = 2, dependsOnMethods = "userCanLoginSuccessfully")
    public void verifyProductsAreDisplayed() {
        productspage.checkThatProductsAreDisplayed()
                .displayAllProducts()
                .checkProductCount(6);

        productspage.getPriceByProductName("Sauce Labs Backpack");
    }

    @Test(priority = 3, dependsOnMethods = "verifyProductsAreDisplayed")
    public void addProductsToCart() {
        productspage.addProductToCart("Sauce Labs Backpack")
                .addProductToCart("Sauce Labs Bolt T-Shirt")
                .addProductToCart("Sauce Labs Onesie").
                addProductToCart(4).getCartCount();

    }

    @Test(priority = 4, dependsOnMethods = "addProductsToCart")
    public void removeProductFromCart() {

        productspage.removeProductFromCart("Sauce Labs Backpack")
                .removeProductFromCart("Sauce Labs Bolt T-Shirt").
                removeProductFromCart(4).
                getCartCount();

    }
//
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}