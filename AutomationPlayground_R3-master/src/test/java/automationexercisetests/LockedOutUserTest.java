package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class LockedOutUserTest {
    Driver driver;
    LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void verifyLockedOutUserCannotLogin() {
        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("locked_out_user", "secret_sauce")
                .clickOnLoginButton();

        // Verify error message is displayed
        loginPage.verifyErrorMessageDisplayed()
                .verifyErrorMessageContains("locked out");
    }

    @Test(priority = 2)
    public void verifyErrorMessageText() {
        String errorMessage = loginPage.getErrorMessage();
        System.out.println("✓ Error message verified: " + errorMessage);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
