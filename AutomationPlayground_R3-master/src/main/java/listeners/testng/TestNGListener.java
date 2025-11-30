package listeners.testng;

import driverfactory.Driver;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.AllureReportManager;
import utilities.ScreenshotManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import static utilities.PropertiesManager.frameworkConfig;
import static utilities.PropertiesManager.initializeProperties;

public class TestNGListener implements IExecutionListener, ITestListener {

    @Override
    public void onExecutionStart() {
        System.out.println("**************** Welcome to Selenium Framework *****************");
        initializeProperties();
        Allure.getLifecycle();
        if(frameworkConfig.getProperty("cleanAllureReportBeforeExecution").equalsIgnoreCase("true")) {
            AllureReportManager.cleanAllureReport();
        }
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("********************* End of Execution *********************");

        if(frameworkConfig.getProperty("openReportAfterExecution").equalsIgnoreCase("true")) {
            try {
                System.out.println("Opening Allure Report.....");
                Runtime.getRuntime().exec("generateReport.bat");
            } catch (IOException e) {
                System.out.println("Unable to open the report " + e.getMessage());
            }
        }
    }


    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("*******  Starting Test: " + result.getName() + " *****************");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("*******  Success of Test: " + result.getName() + " ***************");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed");
        System.out.println("Taking screen shot.....");

        Driver driver = null;
        ThreadLocal<Driver> driverThreadLocal;
        Object currentClass = result.getInstance();
        Field[] fields = result.getTestClass().getRealClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true); // Make private fields accessible
                
                if (field.getType() == Driver.class) {
                    driver = (Driver) field.get(currentClass);
                }

                if (field.getType() == ThreadLocal.class) {
                    driverThreadLocal = (ThreadLocal<Driver>) field.get(currentClass);
                    driver = driverThreadLocal.get();
                }
            }
        } catch (IllegalAccessException exception) {
            System.out.println("Unable to get field, Field Should be public");
        }

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

}
