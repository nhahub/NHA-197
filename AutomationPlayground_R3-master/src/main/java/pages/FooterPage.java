package pages;

import driverfactory.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.time.Duration;
import java.util.Set;

public class FooterPage {
    Driver driver;

    public FooterPage(Driver driver) {
        this.driver = driver;
    }

    // Locators
    By footer = By.className("footer");
    By twitterLink = By.xpath("//a[@data-test='social-twitter']");
    By facebookLink = By.xpath("//a[@data-test='social-facebook']");
    By linkedinLink = By.xpath("//a[@data-test='social-linkedin']");
    By footerText = By.className("footer_copy");

    /**************************************Actions**************************************/

    @Step("Click on Twitter link")
    public FooterPage clickOnTwitterLink() {
        driver.element()
                .waitForElementToBeClickable(twitterLink, Duration.ofSeconds(10))
                .scrollIntoElement(twitterLink)
                .clickUsingJavaScript(twitterLink);
        System.out.println("✓ Clicked on Twitter link");
        return this;
    }

    @Step("Click on Facebook link")
    public FooterPage clickOnFacebookLink() {
        driver.element()
                .waitForElementToBeClickable(facebookLink, Duration.ofSeconds(10))
                .scrollIntoElement(facebookLink)
                .clickUsingJavaScript(facebookLink);
        System.out.println("✓ Clicked on Facebook link");
        return this;
    }

    @Step("Click on LinkedIn link")
    public FooterPage clickOnLinkedInLink() {
        driver.element()
                .waitForElementToBeClickable(linkedinLink, Duration.ofSeconds(10))
                .scrollIntoElement(linkedinLink)
                .clickUsingJavaScript(linkedinLink);
        System.out.println("✓ Clicked on LinkedIn link");
        return this;
    }

    @Step("Get footer text")
    public String getFooterText() {
        driver.element().waitForVisibility(footerText, Duration.ofSeconds(10));
        String text = driver.element().getTextOf(footerText).trim();
        System.out.println("Footer text: " + text);
        return text;
    }

    /**************************************Assertions**************************************/

    @Step("Verify footer is displayed")
    public FooterPage verifyFooterIsDisplayed() {
        driver.element().waitForVisibility(footer, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(footer),
                "Footer is not displayed!");
        System.out.println("✓ Footer is displayed");
        return this;
    }

    @Step("Verify Twitter link is displayed")
    public FooterPage verifyTwitterLinkIsDisplayed() {
        driver.element().waitForVisibility(twitterLink, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(twitterLink),
                "Twitter link is not displayed!");
        System.out.println("✓ Twitter link is displayed");
        return this;
    }

    @Step("Verify Facebook link is displayed")
    public FooterPage verifyFacebookLinkIsDisplayed() {
        driver.element().waitForVisibility(facebookLink, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(facebookLink),
                "Facebook link is not displayed!");
        System.out.println("✓ Facebook link is displayed");
        return this;
    }

    @Step("Verify LinkedIn link is displayed")
    public FooterPage verifyLinkedInLinkIsDisplayed() {
        driver.element().waitForVisibility(linkedinLink, Duration.ofSeconds(10));
        Assert.assertTrue(driver.element().isDisplayed(linkedinLink),
                "LinkedIn link is not displayed!");
        System.out.println("✓ LinkedIn link is displayed");
        return this;
    }

    @Step("Verify all social media links are displayed")
    public FooterPage verifyAllSocialMediaLinksDisplayed() {
        verifyTwitterLinkIsDisplayed();
        verifyFacebookLinkIsDisplayed();
        verifyLinkedInLinkIsDisplayed();
        System.out.println("✓ All social media links are displayed");
        return this;
    }

    @Step("Verify footer text contains expected text")
    public FooterPage verifyFooterTextContains(String expectedText) {
        String actualText = getFooterText();
        Assert.assertTrue(actualText.contains(expectedText),
                "Footer text doesn't contain: " + expectedText);
        System.out.println("✓ Footer text contains: " + expectedText);
        return this;
    }

    @Step("Verify Twitter link opens in new tab")
    public FooterPage verifyTwitterLinkOpensNewTab() {
        String mainWindow = driver.get().getWindowHandle();



        Set<String> windows = driver.get().getWindowHandles();
        Assert.assertTrue(windows.size() > 1, "New tab did not open!");

        for (String window : windows) {
            if (!window.equals(mainWindow)) {
                driver.get().switchTo().window(window);
                System.out.println("✓ Twitter link opened in new tab");
//                driver.get().close();
                break;
            }
        }

        driver.get().switchTo().window(mainWindow);
        return this;
    }

    @Step("Verify Facebook link opens in new tab")
    public FooterPage verifyFacebookLinkOpensNewTab() {
        String mainWindow = driver.get().getWindowHandle();



        Set<String> windows = driver.get().getWindowHandles();
        Assert.assertTrue(windows.size() > 1, "New tab did not open!");

        for (String window : windows) {
            if (!window.equals(mainWindow)) {
                driver.get().switchTo().window(window);
                System.out.println("✓ Facebook link opened in new tab");
//                driver.get().close();
                break;
            }
        }

        driver.get().switchTo().window(mainWindow);
        return this;
    }

    @Step("Verify LinkedIn link opens in new tab")
    public FooterPage verifyLinkedInLinkOpensNewTab() {
        String mainWindow = driver.get().getWindowHandle();



        Set<String> windows = driver.get().getWindowHandles();
        Assert.assertTrue(windows.size() > 1, "New tab did not open!");

        for (String window : windows) {
            if (!window.equals(mainWindow)) {
                driver.get().switchTo().window(window);
                System.out.println("✓ LinkedIn link opened in new tab");
//                driver.get().close();
                break;
            }
        }

        driver.get().switchTo().window(mainWindow);
        return this;
    }
}
