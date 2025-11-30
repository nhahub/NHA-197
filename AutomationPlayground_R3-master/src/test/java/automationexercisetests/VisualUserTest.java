package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class VisualUserTest {
    Driver driver;
    Homepage homepage;
    LoginPage loginPage;
    ProductsPage productsPage;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @Test(priority = 1)
    public void visualUserCanLogin() {
        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("visual_user", "secret_sauce")
                .clickOnLoginButton()
                .checkThatCarIconShouldBeDisplayed();

        homepage.checkThatUserShouldBeLoggedIn();
    }

    @Test(priority = 2)
    public void verifyProductsPageLayout() {
        productsPage.checkThatProductsAreDisplayed();

    }

    @Test(priority = 3)
    public void verifyProductCount() {
        productsPage.checkProductCount(6);
        System.out.println("✓ Product count verified");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
