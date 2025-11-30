package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class SuccessTest {
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
            successPage=new SuccessPage(driver);
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
        public void addMultipleProductsToCart() {
            productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Bike Light")
                    .addProductToCart("Sauce Labs Bolt T-Shirt").getCartCount();



        }

        @Test(priority = 3)
        public void navigateToCheckoutOverview() {
            cartpage.navigateToCart()
                    .verifyUserIsOnCartPage()
                    .verifyCartItemCount(3)
                    .proceedToCheckout();

            checkoutPage.checkThatUserNavigateToCheckoutPageSuccessfull()
                    .fillLoginForm("Habiba", "Ayman", "12345")
                    .clickContinueButton()
                    .checkThatUserNavigateToCheckoutOverViewPageSuccessfull();

        }

        @Test(priority = 4)
        public void verifyCheckoutOverviewPageElements() {
            checkoutOverview.verifyOnCheckoutOverviewPage()
                    .verifyProductsAreDisplayed();


        }



        @Test(priority = 5)
        public void verifyPaidInformation() {
            checkoutOverview.getShippingInfo().
                    getPaymentInfo().verifySubtotalIsPositive().
                    getTaxAmount();
            checkoutOverview.verifyTotalCalculation();
            checkoutOverview.verifyTaxIsCalculatedCorrectly();



        }

        @Test(priority = 6)
        public void userCanCancelCheckout() {
            checkoutOverview.clickOnCancelButton().
                    checkThatProductsAreDisplayed();

        }

        @Test(priority = 7)
        public void userCanCompleteCheckout() {
            productsPage.addProductToCart(4);

            cartpage.navigateToCart()
                    .proceedToCheckout();

            checkoutPage.fillLoginForm("habiba","ayman","908")
                    .clickContinueButton()
                    .checkThatUserNavigateToCheckoutOverViewPageSuccessfull();

            checkoutOverview.clickOnFinishButton();
        }
    @Test(priority = 8)
    public void verifySuccessPage() {
        successPage.verifyThatUserAtSuccessPage()
                .verifySuccessMessageDisplayed()
                .verifyBackButtonDisplayed();
    }
    @Test(priority = 9)
    public void ReturnToProductPageAgain() {
        successPage.clickOnBackToHomeButton().
                    CheckThatuseratproductpage();
    }




//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
    }


