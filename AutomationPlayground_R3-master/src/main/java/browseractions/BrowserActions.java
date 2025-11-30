package browseractions;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class BrowserActions {

    private WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public BrowserActions getToURL(String url) {
        System.out.println("Get to: " + url);
        driver.get(url);
        return this;
    }
    public BrowserActions navigateTo(String url) {
        System.out.println("Navigating to: " + url);
        driver.navigate().to(url);
        return this;
    }

    public BrowserActions navigateForward() {
        System.out.println("Navigating Forward.....");
        driver.navigate().forward();
        return this;
    }

    public BrowserActions refreshPage() {
        System.out.println("Refreshing Page.....");
        driver.navigate().refresh();
        return this;
    }

    public BrowserActions scrollToBottom() {
        new Actions(driver).scrollByAmount(0, 2500).build().perform();
        return this;
    }

    public BrowserActions scrollToAmount(int width, int height) {
        new Actions(driver).scrollByAmount(width, height).build().perform();
        return this;
    }

    /****************************************** Cookies ****************************************/

    public BrowserActions addCookie(Cookie cookie) {
        driver.manage().addCookie(cookie);
        return this;
    }


    public BrowserActions deleteCookie(Cookie cookie) {
        driver.manage().deleteCookie(cookie);
        return this;
    }

    public BrowserActions deleteCookieWithName(String name) {
        driver.manage().deleteCookieNamed(name);
        return this;
    }

    public BrowserActions deleteAllCookies() {
        driver.manage().deleteAllCookies();
        return this;
    }
}
