package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Homepage;
import pages.LoginPage;
import pages.ProductsPage;
import pages.cartPage;

public class CartTest {

        Driver driver;
        Homepage homepage;
        LoginPage loginPage;
        ProductsPage productspage;
        cartPage cartpage;


    @BeforeClass
    public void setUp() {
        driver = new Driver();
        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);
        productspage = new ProductsPage(driver);
        cartpage=new cartPage(driver);
    }
    @Test(priority = 1)
    public void userCanLoginSuccessfully() {
        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("standard_user", "secret_sauce")
                .clickOnLoginButton()
                .checkThatCarIconShouldBeDisplayed();

        homepage.checkThatUserShouldBeLoggedIn();
    }
    @Test(priority = 2,dependsOnMethods="userCanLoginSuccessfully")
   public void AddMultipleProductsToCart(){
        productspage.addProductToCart("Sauce Labs Backpack")
                .addProductToCart("Sauce Labs Bolt T-Shirt")
                .addProductToCart("Sauce Labs Onesie").getCartCount();

    }
    @Test(priority = 3,dependsOnMethods="AddMultipleProductsToCart")
    public void navigateYoCartPage(){
        cartpage.navigateToCart().verifyUserIsOnCartPage()
        ;
    }
    @Test(priority = 4)
    public void RemoveItemsFromCartPage(){
        cartpage.removeProductFromCart(1) .getCartCount();
    }
    @Test(priority = 5,dependsOnMethods="RemoveItemsFromCartPage")
    public void UserCanContinueShopping(){
        cartpage.continueShopping().
                checkThatProductsAreDisplayed();

    }
   // @Test(priority = 6,dependsOnMethods="UserCanContinueShopping")
//    public void UserCanCheckOutSuccessfully(){
//        cartpage.
//                CheckoutThePrice().
//                VerifyThatuserGotocheckoutsuccessfully();
//    }


//  @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
    }
