package automationexercisetests;

import driverfactory.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class RegisterAndLogin {

    Driver driver;
    Homepage homepage;
    LoginPage loginPage;


    @BeforeClass
    public void setUp() {
        driver = new Driver();

        homepage = new Homepage(driver);
        loginPage = new LoginPage(driver);

    }

    @Test(priority = 1)
    public void userCanLoginSuccessfully() {

        loginPage.checkThatUserShouldBeNavigatedToLoginSignUpPage()
                .fillLoginForm("standard_user", "secret_sauce")
                .clickOnLoginButton()
                .checkThatCarIconShouldBeDisplayed()
               ;
        homepage.checkThatUserShouldBeLoggedIn();
    }
    @Test(priority = 2)
    public void userCanLogoutSuccessfully() {
        homepage.clickOnMenuButton()
                .clickOnLogoutLink()
                .checkThatUserShouldBeLoggedOut();
    }

@AfterClass
   public void tearDown() {
       driver.quit();
   }
}
