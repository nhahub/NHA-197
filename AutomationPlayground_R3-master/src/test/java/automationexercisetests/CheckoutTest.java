package automationexercisetests;

import driverfactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import java.time.Duration;

public class CheckoutTest {
    Driver driver;
    Homepage homepage;
    LoginPage loginPage;
    ProductsPage productspage;
    CheckoutPage checkoutPage;
    CheckoutOverview checkoutOverview;
    cartPage cartpage;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);
        productspage = new ProductsPage(driver);
        checkoutPage = new CheckoutPage(driver);
        checkoutOverview = new CheckoutOverview(driver);
        cartpage = new cartPage(driver);
    }

    @Test(priority = 1)
    public void userCanLoginSuccessfully() {
        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("standard_user", "secret_sauce")
                .clickOnLoginButton()
                .checkThatCarIconShouldBeDisplayed();

        homepage.checkThatUserShouldBeLoggedIn();
    }

    @Test(priority = 2)
    public void addProductsToCart() {
        productspage.addProductToCart("Sauce Labs Backpack")
                .addProductToCart("Sauce Labs Bolt T-Shirt")
                .addProductToCart("Sauce Labs Onesie");
    }



    @Test(priority = 3)
    public void navigateToCartPage() {
        cartpage.navigateToCart()
                .verifyUserIsOnCartPage()
                .verifyCartItemCount(3); // ✅ تحقق من عدد المنتجات
    }

    @Test(priority = 4)
    public void testCheckoutWithIncompleteData() {
        // اضغط على Checkout
        cartpage.proceedToCheckout();

        // تحقق من الوصول لصفحة Checkout
        checkoutPage.checkThatUserNavigateToCheckoutPageSuccessfull();

        // املأ بيانات ناقصة
        checkoutPage.fillLoginFormBycompleteData("habiba", "Ayman")
                .clickContinueButton()
                .checkThatErrorShouldBeDisplayedWhenDonnotFillAllData();
    }

    @Test(priority = 5)
    public void completeCheckoutWithValidData() {
        checkoutPage.fillLoginForm("habiba", "Ayman", "7899")
                .clickContinueButton()
                .checkThatUserNavigateToCheckoutOverViewPageSuccessfull();


        checkoutOverview.verifyProductsAreDisplayed();
    }



    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
