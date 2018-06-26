package webdrivertest;

import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by simpsof on 22/01/2016.
 */
public abstract class WebDriverBase {
    protected WebDriver driver;
    @Before

    public void setUp(){
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);

        Dimension dimension = new Dimension(300, 17208);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("test-type");
        capabilities.setCapability("chrome.binary", "Users/simpsof/Desktop/chromedriver");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        capabilities.setCapability("enablePersitentHover", false);
        driver = new ChromeDriver(capabilities);
        //driver.manage().window().maximize();
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }
}
