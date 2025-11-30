package pages;

import driverfactory.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutOverview {
    Driver driver;

    public CheckoutOverview(Driver driver) {
        this.driver = driver;
    }

    By finishButton = By.name("finish");
    By cancelButton = By.name("cancel");
    By productNames = By.className("inventory_item_name");
    By CheckoutOverViewLogo = By.xpath("//span[@class=\"title\"]");
    By productItems = By.className("cart_item");
    By backToProductsButton = By.id("back-to-products");
    By paymentInfo = By.xpath("//div[@data-test='payment-info-value']");
    By shippingInfo = By.xpath("//div[@data-test='shipping-info-value']");
    By taxLabel = By.className("summary_tax_label");
    By totalLabel = By.className("summary_total_label");
    By subtotalLabel = By.className("summary_subtotal_label");


    /******************************Actions*********************/


    @Step("Click on product {productName}")
    public CheckoutOverview clickOnProduct(String productName) {
        driver.element().waitForVisibility(productItems, Duration.ofSeconds(15));
        By productLink = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");

        driver.element()
                .waitForElementToBeClickable(productLink, Duration.ofSeconds(15))
                .scrollIntoElement(productLink)
                .clickUsingJavaScript(productLink);

        System.out.println("✓ Clicked on product: " + productName);
        return this;
    }


    @Step("Check that products are displayed")
    public CheckoutOverview checkThatProductsAreDisplayed() {
        driver.element().waitForVisibility(productItems, Duration.ofSeconds(5));
        int count = driver.get().findElements(productItems).size();
        Assert.assertTrue(count > 0, "No products found on the page!");
        System.out.println("✅ Found " + count + " products");
        return this;
    }


    @Step("Click on Finish button")
    public SuccessPage clickOnFinishButton() {
        driver.element()
                .waitForElementToBeClickable(finishButton, Duration.ofSeconds(15))
                .scrollIntoElement(finishButton)
                .clickUsingJavaScript(finishButton);
        return new SuccessPage(driver);
    }

    @Step("Click on Cancel button")
    public ProductsPage clickOnCancelButton() {
        driver.element()
                .waitForElementToBeClickable(cancelButton, Duration.ofSeconds(15))
                .scrollIntoElement(cancelButton)
                .clickUsingJavaScript(cancelButton);
        return new ProductsPage(driver);
    }

    @Step("Get payment information")
    public CheckoutOverview getPaymentInfo() {
        driver.element().waitForVisibility(paymentInfo, Duration.ofSeconds(10));
        String payment = driver.element().getTextOf(paymentInfo).trim();
        System.out.println("Payment Info: " + payment);
        return this;
    }

    @Step("Get shipping information")
    public CheckoutOverview getShippingInfo() {
        driver.element().waitForVisibility(shippingInfo, Duration.ofSeconds(10));
        String shipping = driver.element().getTextOf(shippingInfo).trim();
        System.out.println("Shipping Info: " + shipping);
        return this;
    }

    @Step("Get tax")
    public String getTax() {
        driver.element().waitForVisibility(taxLabel, Duration.ofSeconds(10));
        String tax = driver.element().getTextOf(taxLabel).trim();
        System.out.println("Tax: " + tax);
        return tax;
    }

    @Step("Get tax amount")
    public double getTaxAmount() {
        String tax = getTax();

        String amount = tax.replaceAll("[^0-9.]", "");
        double taxamount=Double.parseDouble(amount);
        System.out.println("taxAmount:"+taxamount);
        return Double.parseDouble(amount);
    }

    @Step("Get total")
    public String getTotal() {
        driver.element().waitForVisibility(totalLabel, Duration.ofSeconds(10));
        String total = driver.element().getTextOf(totalLabel).trim();
        System.out.println("Total: " + total);
        return total;
    }

    @Step("Get total amount")
    public double getTotalAmount() {
        String total = getTotal();
        // Extract number from "Total: $32.39"
        String amount = total.replaceAll("[^0-9.]", "");
        return Double.parseDouble(amount);
    }

    @Step("Get subtotal")
    public String getSubtotal() {
        driver.element().waitForVisibility(subtotalLabel, Duration.ofSeconds(10));
        String subtotal = driver.element().getTextOf(subtotalLabel).trim();
        System.out.println("Subtotal: " + subtotal);
        return subtotal;
    }

    @Step("Get subtotal amount")
    public double getSubtotalAmount() {
        String subtotal = getSubtotal();
        // Extract number from "Item total: $29.99"
        String amount = subtotal.replaceAll("[^0-9.]", "");
        return Double.parseDouble(amount);
    }

    @Step("Get product names in checkout")
    public List<String> getProductNamesInCheckout() {
        driver.element().waitForVisibility(productItems, Duration.ofSeconds(10));

        List<WebElement> items = driver.get().findElements(productNames);
        List<String> names = new ArrayList<>();

        for (WebElement item : items) {
            names.add(item.getText());
        }

        System.out.println("Products in checkout: " + names);
        return names;
    }


    /**********************************Aseertions**********************************/

    @Step("Verify user is on Checkout Overview page")
    public CheckoutOverview verifyOnCheckoutOverviewPage() {
        driver.element().waitForVisibility(CheckoutOverViewLogo, Duration.ofSeconds(10));

        String actualTitle = driver.element().getTextOf(CheckoutOverViewLogo).trim();

        Assert.assertEquals(actualTitle, "Checkout: Overview",
                "User is not on Checkout Overview page! Actual title: " + actualTitle);

        System.out.println("✓ User is on Checkout Overview page");
        return this;
    }

    @Step("Verify user is on Product Details page")
    public CheckoutOverview verifyOnProductDetailsPage() {
        driver.element().waitForVisibility(backToProductsButton, Duration.ofSeconds(10));

        Assert.assertTrue(driver.element().isDisplayed(backToProductsButton),
                "Back to Products button is not displayed!");

        System.out.println("✓ User is on Product Details page");
        return this;
    }

    @Step("Verify total calculation is correct")
    public CheckoutOverview verifyTotalCalculation() {
        double subtotal = getSubtotalAmount();
        double tax = getTaxAmount();
        double total = getTotalAmount();

        double expectedTotal = subtotal + tax;

        Assert.assertEquals(total, expectedTotal, 0.01,
                "Total calculation is incorrect! Expected: " + expectedTotal + ", Actual: " + total);

        System.out.println("✓ Total calculation is correct: " + total);
        return this;
    }

    @Step("Verify product {productName} is in checkout")
    public CheckoutOverview verifyProductInCheckout(String productName) {
        List<String> products = getProductNamesInCheckout();

        Assert.assertTrue(products.contains(productName),
                "Product '" + productName + "' not found in checkout!");

        System.out.println("✓ Product '" + productName + "' is in checkout");
        return this;
    }

    @Step("Verify products are displayed")
    public CheckoutOverview verifyProductsAreDisplayed() {
        driver.element().waitForVisibility(productItems, Duration.ofSeconds(5));

        int count = driver.get().findElements(productItems).size();
        Assert.assertTrue(count > 0, "No products found on the page!");

        System.out.println("✅ Found " + count + " products");
        return this;
    }
@Step
    public CheckoutOverview verifySubtotalIsPositive() {
        double subtotal = getSubtotalAmount();

        Assert.assertTrue(subtotal > 0,
                "Subtotal should be greater than 0");

        System.out.println("✅ Test 9: Subtotal is positive: $" + subtotal);
return this;
    }
    @Step
    public void verifyTaxIsCalculatedCorrectly() {
        double subtotal = getSubtotalAmount();
        double tax = getTaxAmount();
        double expectedTax = subtotal * 0.08;

        Assert.assertEquals(tax, expectedTax, 0.01,
                "Tax should be 8% of subtotal");

        System.out.println("✅ Test 10: Tax calculation verified (8%)");
    }
    public CheckoutOverview checkThatUserNavigateToCheckoutOverViewPageSuccessfull() {
      driver.element().waitForVisibility(CheckoutOverViewLogo, Duration.ofSeconds(10));
       String actualTitle = driver.element().getTextOf(CheckoutOverViewLogo).trim();

     Assert.assertEquals(actualTitle, "Checkout: Overview",
               "User is not on Checkout Overview page! Actual title: " + actualTitle);
        System.out.println("✓ User navigated to Checkout Overview page successfully");
       return this;
   }

}