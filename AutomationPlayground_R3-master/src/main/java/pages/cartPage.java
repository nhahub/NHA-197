package pages;

import driverfactory.Driver;
import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.core.JsonToken;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class cartPage {

    private Driver driver;

    public cartPage(Driver driver) {
        this.driver = driver;
    }



    /******************************* Locators *************************************/
    By cartLink = By.xpath("//a[@class='shopping_cart_link']");
    By cartBadge = By.className("shopping_cart_badge");
    By checkoutButton = By.id("checkout");
    By continueShoppingButton = By.id("continue-shopping");
    By cartPageTitle = By.xpath("//span[@class='title' and text()='Your Cart']");
    By cartItems = By.className("cart_item");
    By cartItemNames = By.className("inventory_item_name");
    By cartItemPrices = By.className("inventory_item_price");
//  By cartItemQuantities = By.className("cart_quantity");
    By ProductsLogo=By.xpath("//span[@class=\"title\"]");
    String  removeButtonId1 ="remove-sauce-labs-backpack";
    String removeButtonId2 = "remove-sauce-labs-bike-light";
    String removeButtonId3 = "remove-sauce-labs-bolt-t-shirt";
    String  removeButtonId4 = "remove-sauce-labs-fleece-jacket";
    String removeButtonId5 = "remove-sauce-labs-onesie";
    String removeButtonId6 ="remove-test.allthethings()-t-shirt-(red)";

    /********************************* Actions ***********************************/

    @Step("Navigate to cart")
    public cartPage navigateToCart() {
        System.out.println("Navigating to cart...");

        driver.element()
                .waitForElementToBeClickable(cartLink, Duration.ofSeconds(10))
                .scrollIntoElement(cartLink)
                .clickUsingJavaScript(cartLink);

        driver.element().waitForVisibility(cartPageTitle, Duration.ofSeconds(15));

        System.out.println("✓ Navigated to Cart page: " + driver.get().getCurrentUrl());
        return this;
    }

    @Step("Get cart count")
    public int getCartCount() {
        try {
            driver.element().waitForVisibility(cartBadge, Duration.ofSeconds(3));

            String text = driver.element().getTextOf(cartBadge).trim();

            if (text.isEmpty())
                return 0;

            return Integer.parseInt(text);

        } catch (Exception e) {
            System.out.println("the cart s Empty");
            return 0;
        }
    }

    @Step("Get cart item names")
    public List<String> getCartItemNames() {

        List<WebElement> items = driver.get().findElements(cartItemNames);
        List<String> result = new ArrayList<>();

        for (WebElement item : items) {
            result.add(item.getText());
        }

        return result;
    }


    @Step("Get cart item prices")
    public List<String> getCartItemPrices() {
        List<String> prices = new ArrayList<>();
        try {

            List<WebElement> priceElements = driver.get().findElements(cartItemPrices);
            for (WebElement price : priceElements) {
                prices.add(price.getText());
            }
            System.out.println("Cart item prices: " + prices);
        } catch (Exception e) {
            System.out.println("Error getting cart item prices: " + e.getMessage());
        }
        return prices;
    }

    @Step("Check if product {productName} is in cart")
    public boolean isProductInCart(String productName) {

        List<WebElement> items = driver.get().findElements(cartItemNames);

        for (WebElement item : items) {
            if (item.getText().trim().equalsIgnoreCase(productName.trim())) {
                return true;
            }
        }

        return false;
    }


    @Step("Remove product {index} from cart")
    public cartPage removeProductFromCart(int index) {

        System.out.println("Attempting to remove product: " + index);

        By removeButtonId;

        switch (index) {
            case 1:
             removeButtonId=By.id(removeButtonId1);
                break;
            case 2:
                removeButtonId=By.id(removeButtonId2);


                break;
            case 3:
                removeButtonId=By.id(removeButtonId3);


                break;
            case 4:
                removeButtonId=By.id(removeButtonId4);

                break;
            case 5:
                removeButtonId=By.id(removeButtonId5);

                break;
            case 6:
                removeButtonId=By.id(removeButtonId6);

                break;
            default:
                throw new IllegalArgumentException("Invalid product index: " + index);
        }
        try {

            driver.element() .
                    scrollIntoElement(removeButtonId)
                    .waitForElementToBeClickable(removeButtonId, Duration.ofSeconds(5))
                    .clickUsingJavaScript(removeButtonId);

            System.out.println("✓ Clicked on Remove button for product " + index);
            
            // Wait a moment for the removal to process
            Thread.sleep(500);
            
            System.out.println("Product " + index + " removed successfully from cart!");
        } catch (Exception e) {

            System.out.println("Failed to remove product " + index);
            throw new AssertionError("Cannot remove product " + index + " - Remove button not found or not clickable");
        }

        return this;
    }


    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        driver.element().
                waitForElementToBeClickable(checkoutButton,Duration.ofSeconds(10)).
                scrollIntoElement(checkoutButton).
                clickUsingJavaScript(checkoutButton);

        System.out.println("✓ Clicked checkout button");
        return new CheckoutPage(driver);
    }

    @Step("Continue shopping")
    public ProductsPage continueShopping() {
        driver.element().
                waitForElementToBeClickable(continueShoppingButton,Duration.ofSeconds(10)).
                scrollIntoElement(continueShoppingButton).
                clickUsingJavaScript(continueShoppingButton);

        System.out.println("Navigated to Cart page: " + driver.get().getCurrentUrl());

        return new ProductsPage(driver);

    }


    /********************************* Assertions *********************************/

    @Step("Verify user is on cart page")
    public cartPage verifyUserIsOnCartPage() {
        Assert.assertTrue(
                driver.element().isDisplayed(cartPageTitle),
                "User is not on cart page!"
        );
        System.out.println("✓ User is on cart page");
        return this;
    }

    @Step("Verify cart has {expectedCount} items")
    public cartPage verifyCartItemCount(int expectedCount) {
        int actualCount = getCartCount();
        Assert.assertEquals(
                actualCount,
                expectedCount,
                "Cart item count mismatch!"
        );
        System.out.println("✓ Cart has expected " + expectedCount + " items");
        return this;
    }

    @Step("Verify cart is empty")
    public cartPage verifyCartIsEmpty() {
        int count = getCartCount();
        Assert.assertEquals(count, 0, "Cart is not empty!");
        System.out.println("✓ Cart is empty");
        return this;
    }

    @Step("Verify product {productName} is in cart")
    public cartPage verifyProductInCart(String productName) {
        Assert.assertTrue(
                isProductInCart(productName),
                "Product '" + productName + "' not found in cart!"
        );
        System.out.println("✓ Product '" + productName + "' is in cart");
        return this;
    }



}