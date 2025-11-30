package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ScreenshotManager {

    private ScreenshotManager() {

    }

    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        // Use absolute path for Allure compatibility
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
}
