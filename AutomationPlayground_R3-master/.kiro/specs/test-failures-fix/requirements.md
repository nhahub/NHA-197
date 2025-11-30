# Requirements Document

## Introduction

This document outlines the requirements for fixing 10 failed test cases in the Selenium automation test suite and resolving screenshot attachment issues in test reports. The performance user test failure is intentional and will not be addressed.

## Glossary

- **Test Suite**: The collection of automated Selenium tests for the SauceDemo application
- **Screenshot Manager**: The utility class responsible for capturing and attaching screenshots to test reports
- **Allure Report**: The test reporting framework used to display test results and attachments
- **Checkout Flow**: The sequence of pages from cart → checkout information → checkout overview → success
- **Product Index**: The numeric position of a product in the inventory list (0-based)

## Requirements

### Requirement 1

**User Story:** As a QA engineer, I want the checkout flow tests to navigate correctly through all pages, so that I can verify the complete purchase workflow.

#### Acceptance Criteria

1. WHEN a test navigates to the checkout overview page THEN the system SHALL verify the page title contains "Checkout: Overview"
2. WHEN filling checkout information and clicking continue THEN the system SHALL wait for the overview page to load completely
3. WHEN the checkout overview page loads THEN the system SHALL verify all expected elements are present before proceeding
4. WHEN tests complete the checkout flow THEN the system SHALL reach the success page without navigation errors

### Requirement 2

**User Story:** As a QA engineer, I want product selection by index to work correctly, so that tests can add specific products to the cart reliably.

#### Acceptance Criteria

1. WHEN a test adds a product by index THEN the system SHALL validate the index is within the valid range (0-5)
2. WHEN an invalid product index is provided THEN the system SHALL throw a descriptive error message
3. WHEN adding a product by index THEN the system SHALL locate the correct product element from the inventory list
4. WHEN the product is added THEN the system SHALL verify the remove button appears for that specific product

### Requirement 3

**User Story:** As a QA engineer, I want screenshots to be properly attached to Allure reports when tests fail, so that I can debug failures effectively.

#### Acceptance Criteria

1. WHEN a test fails THEN the system SHALL capture a screenshot immediately
2. WHEN a screenshot is captured THEN the system SHALL save it to the screenshots directory with a unique name
3. WHEN attaching to Allure report THEN the system SHALL use the correct Allure annotation with the screenshot file path
4. WHEN the Allure report is generated THEN the system SHALL display the screenshot inline without "Attachment isn't Found" errors
5. WHEN saving screenshots THEN the system SHALL use absolute file paths for Allure attachment compatibility

