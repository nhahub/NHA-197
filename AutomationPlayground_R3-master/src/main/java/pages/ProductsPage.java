package pages;

import driverfactory.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ProductsPage {
    private Driver driver;
    private WebDriverWait wait;

    public ProductsPage(Driver driver) {
        this.driver = driver;
    }

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        }
        return wait;
    }

    // Locators
    By productItems = By.className("inventory_item");
    By productNames = By.className("inventory_item_name");
    By productPrices = By.className("inventory_item_price");
    By cartBadge = By.className("shopping_cart_badge");
    By ProductPageTitle=By.xpath("//span[@class=\"title\"]");

    /******************************* Assertions ***********************************/
@Step("Check That user at product page")
public ProductsPage CheckThatuseratproductpage(){

    driver.element().waitForVisibility(ProductPageTitle, Duration.ofSeconds(10));
    String actualMessage = driver.element().getTextOf(ProductPageTitle).trim();

    Assert.assertEquals(actualMessage, "Products");
    System.out.println("✓ User is on Product page");
    return this;

}

    @Step("Check that products are displayed")
    public ProductsPage checkThatProductsAreDisplayed() {
        driver.element().waitForVisibility(productItems,Duration.ofSeconds(5));



        int count = driver.get().findElements(productItems).size();
        Assert.assertTrue(count > 0, "No products found on the page!");
        System.out.println("✅ Found " + count + " products");
        return this;
    }

    @Step("Check that product count is {expectedCount}")
    public ProductsPage checkProductCount(int expectedCount) {
        int actualCount = driver.get().findElements(productItems).size();

        Assert.assertEquals(actualCount, expectedCount,
                "Product count mismatch! Expected: " + expectedCount +
                        ", but found: " + actualCount);

        System.out.println(" Product count verified: " + actualCount);
        return this;
    }
    /******************************** Actions ***********************************/

    @Step("Display all products")
    public ProductsPage displayAllProducts() {
        List<WebElement> products = driver.get().findElements(productNames);

        System.out.println("\n========== Products List ==========");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i).getText());
        }
        System.out.println("===================================\n");

        return this;
    }
    private By getAddToCartButton(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        return By.id(buttonId);
    }

    private By getRemoveButton(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        return By.id(buttonId);
    }

    private String getProductNameByIndex(int index) {
        List<WebElement> products = driver.get().findElements(productNames);
        
        // Validate index bounds (0-5 for 6 products)
        if (index < 0 || index > 5) {
            throw new IllegalArgumentException(
                "Invalid product index: " + index + ". Valid range is 0-5");
        }
        
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found on page");
        }
        
        return products.get(index).getText();
    }
    @Step("Add product to cart")
    public ProductsPage addProductToCart(int index) {
        String productName = getProductNameByIndex(index);
        return addProductToCart(productName);
    }
    @Step("remove from  cart")
    public ProductsPage removeProductFromCart(int index) {
        String productName = getProductNameByIndex(index);
        return removeProductFromCart(productName);
    }



    @Step("Add product {productName} to cart")
    public ProductsPage addProductToCart(String productName) {
        driver.element().waitForVisibility(productItems, Duration.ofSeconds(20));

        By addToCartButton = getAddToCartButton(productName);
        By removeButton = getRemoveButton(productName);


        driver.element()
                .scrollIntoElement(addToCartButton)
                .waitForElementToBeClickable(addToCartButton, Duration.ofSeconds(20))
                .clickUsingJavaScript(addToCartButton);

        System.out.println("✓ Clicked on Add to cart button for: " + productName);

        try {
            driver.element().waitForVisibility(removeButton, Duration.ofSeconds(20));
            boolean isRemoveVisible = driver.element().isDisplayed(removeButton);

            if (isRemoveVisible) {
                System.out.println("✅ SUCCESS: " + productName + " added to cart!");
            } else {
                throw new AssertionError("Remove button not visible after add");
            }

        } catch (TimeoutException e) {
            System.out.println(" FAILED: " + productName + " was NOT added to cart!");
            throw new AssertionError("Failed to add " + productName + " - Remove button never appeared", e);
        }

        return this;
    }

    // نفس الفكرة للـ removeProductFromCart:
    @Step("Remove product {productName} from cart")
    public ProductsPage removeProductFromCart(String productName) {
        driver.element().waitForVisibility(productItems, Duration.ofSeconds(15));

        By addToCartButton = getAddToCartButton(productName);
        By removeButton = getRemoveButton(productName);

        driver.element()
                .waitForElementToBeClickable(removeButton, Duration.ofSeconds(15))
                .scrollIntoElement(removeButton)
                .clickUsingJavaScript(removeButton);

        System.out.println("✓ Clicked on Remove from cart button for: " + productName);

        try {
            driver.element()
                    .waitForVisibility(addToCartButton, Duration.ofSeconds(10))
                    .isDisplayed(addToCartButton);

            System.out.println("✅ SUCCESS: " + productName + " removed from cart!");

        } catch (TimeoutException e) {
            System.out.println("❌ FAILED: " + productName + " was NOT removed from cart!");
            throw new AssertionError("Failed to remove " + productName + " - Add button never appeared", e);
        }

        return this;
    }

    @Step("Get price for product: {productName}")
    public String getPriceByProductName(String productName) {
        List<WebElement> names = driver.get().findElements(productNames);
        List<WebElement> prices = driver.get().findElements(productPrices);

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().equals(productName)) {
                String price = prices.get(i).getText();
                System.out.println(productName + " costs: " + price);
                return price;
            }
        }

        System.out.println("Product not found: " + productName);
        return "Product not found!";
    }

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
}