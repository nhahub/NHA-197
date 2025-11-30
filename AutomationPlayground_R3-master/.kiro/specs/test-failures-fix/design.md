# Design Document

## Overview

This design document outlines the technical approach to fix 10 failed test cases in the Selenium automation test suite and resolve screenshot attachment issues in Allure reports. The solution focuses on three main areas:

1. **Checkout Flow Navigation**: Ensuring proper page transitions and element verification in the checkout process
2. **Product Selection by Index**: Validating and handling product index selection correctly
3. **Screenshot Attachment**: Fixing the Allure report screenshot attachment mechanism

## Architecture

The solution follows the existing Page Object Model (POM) architecture with the following components:

```
Test Layer (CheckoutTest, SuccessTest)
    ↓
Page Objects (CheckoutPage, CheckoutOverview, ProductsPage)
    ↓
Driver Wrapper (Driver class)
    ↓
Selenium WebDriver
    ↓
Listeners (TestNGListener)
    ↓
Utilities (ScreenshotManager, AllureReportManager)
```

## Components and Interfaces

### 1. CheckoutPage Enhancements

**Current Issues:**
- Navigation methods don't properly wait for page transitions
- Inconsistent return types between methods

**Proposed Changes:**
- Add explicit waits after clicking Continue button
- Ensure all navigation methods return the correct page object
- Add validation for page title before proceeding

### 2. ProductsPage Index Validation

**Current Issues:**
- `addProductToCart(int index)` doesn't validate index bounds
- Error messages are not descriptive when invalid index is provided

**Proposed Changes:**
- Add index validation (0-5 for 6 products)
- Throw `IllegalArgumentException` with descriptive message for invalid indices
- Update `getProductNameByIndex()` to use 0-based indexing consistently

### 3. ScreenshotManager Fix

**Current Issues:**
- Uses relative path `./screenshots` which doesn't work with Allure
- TestNGListener constructs incorrect file path for Allure attachment
- Missing file extension in path construction

**Proposed Changes:**
- Use absolute paths for screenshot storage
- Fix path construction in TestNGListener to include `/screenshots/` directory
- Add `.jpg` extension to file path when attaching to Allure

## Data Models

### Screenshot Path Structure
```
Current (Broken):
- Save: ./screenshots/testName.jpg
- Attach: {user.dir}/testName (missing directory and extension)

Fixed:
- Save: {user.dir}/screenshots/testName.jpg
- Attach: {user.dir}/screenshots/testName.jpg
```

### Product Index Mapping
```
Valid Indices: 0-5 (6 products total)
Index 0 → Sauce Labs Backpack
Index 1 → Sauce Labs Bike Light
Index 2 → Sauce Labs Bolt T-Shirt
Index 3 → Sauce Labs Fleece Jacket
Index 4 → Sauce Labs Onesie
Index 5 → Test.allTheThings() T-Shirt (Red)
```

## Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system-essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*

### Property 1: Checkout navigation consistency
*For any* checkout flow execution, when filling valid information and clicking continue, the system should navigate to the overview page with title "Checkout: Overview"
**Validates: Requirements 1.1, 1.2**

### Property 2: Product index bounds validation
*For any* product index outside the range [0-5], the system should throw an IllegalArgumentException with a descriptive error message
**Validates: Requirements 2.1, 2.2**

### Property 3: Screenshot file existence
*For any* failed test, the screenshot file should exist at the expected absolute path before attempting Allure attachment
**Validates: Requirements 3.1, 3.2**

### Property 4: Screenshot attachment path correctness
*For any* screenshot captured, the Allure attachment path should include the screenshots directory and .jpg extension
**Validates: Requirements 3.3, 3.4, 3.5**

## Error Handling

### 1. Navigation Errors
- **Timeout on page load**: Increase wait time to 15 seconds for checkout overview page
- **Wrong page reached**: Add assertion to verify page title before proceeding
- **Element not found**: Add explicit wait for page title element

### 2. Product Index Errors
- **Index out of bounds**: Throw `IllegalArgumentException` with message: "Invalid product index: {index}. Valid range is 0-5"
- **Product list empty**: Throw `NoSuchElementException` with message: "No products found on page"

### 3. Screenshot Errors
- **File not created**: Log error message and continue test execution
- **File not found for attachment**: Log warning but don't fail the test
- **IO Exception**: Catch and log exception, display "Attachment isn't Found" message

## Testing Strategy

### Unit Testing Approach

Unit tests will verify specific scenarios:

1. **Checkout Navigation Tests**
   - Test successful navigation with valid data
   - Test error message display with incomplete data
   - Test page title verification at each step

2. **Product Index Tests**
   - Test valid indices (0-5)
   - Test invalid indices (-1, 6, 100)
   - Test boundary values (0, 5)

3. **Screenshot Tests**
   - Test screenshot file creation
   - Test file path construction
   - Test Allure attachment with correct path

### Property-Based Testing Approach

**Library**: We will use **JUnit QuickCheck** for property-based testing in Java.

**Configuration**: Each property test will run a minimum of 100 iterations.

**Test Tagging**: Each property-based test will be tagged with:
```java
// Feature: test-failures-fix, Property 1: Checkout navigation consistency
```

**Property Tests**:

1. **Property Test 1: Checkout Navigation**
   - Generate random valid user data (firstName, lastName, postalCode)
   - Execute checkout flow
   - Verify page title is "Checkout: Overview"
   - **Feature: test-failures-fix, Property 1: Checkout navigation consistency**

2. **Property Test 2: Product Index Validation**
   - Generate random invalid indices (outside 0-5)
   - Attempt to add product by index
   - Verify IllegalArgumentException is thrown
   - **Feature: test-failures-fix, Property 2: Product index bounds validation**

3. **Property Test 3: Screenshot Path Construction**
   - Generate random test names
   - Construct screenshot paths
   - Verify path includes "/screenshots/" and ".jpg"
   - **Feature: test-failures-fix, Property 3: Screenshot file existence**
   - **Feature: test-failures-fix, Property 4: Screenshot attachment path correctness**

## Implementation Details

### 1. CheckoutPage.java Changes

```java
public CheckoutOverview checkThatUserNavigateToCheckoutOverViewPageSuccessfull() {
    // Add longer wait time
    driver.element().waitForVisibility(CheckoutOverViewLogo, Duration.ofSeconds(15));
    
    String actualTitle = driver.element().getTextOf(CheckoutOverViewLogo).trim();
    
    Assert.assertEquals(actualTitle, "Checkout: Overview",
            "User is not on Checkout Overview page! Actual title: " + actualTitle);
    
    System.out.println("✓ User navigated to Checkout Overview page successfully");
    return new CheckoutOverview(driver);
}
```

### 2. ProductsPage.java Changes

```java
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
```

### 3. ScreenshotManager.java Changes

```java
public static void captureScreenshot(WebDriver driver, String screenshotName) {
    // Use absolute path
    String absolutePath = System.getProperty("user.dir") + File.separator + "screenshots";
    File screenshotsDirectory = new File(absolutePath);
    
    if(!screenshotsDirectory.exists()) {
        boolean created = screenshotsDirectory.mkdirs();
        if (created) {
            System.out.println("Screenshots Directory Created");
        }
    }
    
    Path destination = Paths.get(absolutePath, screenshotName + ".jpg");
    byte[] byteArray = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    
    try{
        Files.write(destination, byteArray, StandardOpenOption.CREATE);
        System.out.println("Screenshot saved: " + destination.toString());
    } catch (IOException e) {
        System.out.println("Unable to save screenshot: " + e.getMessage());
    }
}
```

### 4. TestNGListener.java Changes

```java
@Override
public void onTestFailure(ITestResult result) {
    System.out.println("Test Failed");
    System.out.println("Taking screen shot.....");
    
    Driver driver = getDriverFromTest(result);
    
    if (driver != null) {
        ScreenshotManager.captureScreenshot(driver.get(), result.getName());
        
        // Fix: Include screenshots directory and .jpg extension
        String fullPath = System.getProperty("user.dir") + 
                         File.separator + "screenshots" + 
                         File.separator + result.getName() + ".jpg";
        
        File screenshotFile = new File(fullPath);
        
        if (screenshotFile.exists()) {
            try {
                Allure.addAttachment(
                    result.getMethod().getConstructorOrMethod().getName(),
                    FileUtils.openInputStream(screenshotFile));
                System.out.println("Screenshot attached to Allure report");
            } catch (IOException e) {
                System.out.println("Failed to attach screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("Screenshot file not found: " + fullPath);
        }
    }
}
```

## Success Criteria

1. All 10 failed tests pass successfully
2. Screenshots appear correctly in Allure reports without "Attachment isn't Found" errors
3. Product index validation throws appropriate exceptions for invalid indices
4. Checkout flow navigates correctly through all pages
5. No regression in existing passing tests
