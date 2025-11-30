package automationexercisetests;

import driverfactory.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class FooterTest {
    Driver driver;
    Homepage homepage;
    LoginPage loginPage;
    FooterPage footerPage;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);
        footerPage = new FooterPage(driver);
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
    public void verifyFooterIsDisplayed() {
        footerPage.verifyFooterIsDisplayed();
    }



    @Test(priority = 4)
    public void verifyFooterText() {
        footerPage.verifyFooterTextContains("Sauce Labs");
    }

    @Test(priority = 5)
    public void openLinkedInLinkheFooter (){
        footerPage.clickOnLinkedInLink().
                verifyFacebookLinkOpensNewTab();
    }

    @Test(priority = 6)
    public void openFacebookLinkheFooter() {
        footerPage.clickOnFacebookLink().
                verifyFacebookLinkOpensNewTab();
    }

    @Test(priority = 7)
    public void openTwitterInLinkheFooter() {
        footerPage.clickOnTwitterLink().
                verifyTwitterLinkOpensNewTab();

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
