package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

abstract public class BaseTest {
    protected WebDriver browser;

    @Before
    public void prepareTestEnvironment() {
        WebDriverManager.chromedriver().setup();
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        browser.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        BasePage.setDriver(browser);
        browser.navigate().to("https://qa-scooter.praktikum-services.ru/");
    }

    @After
    public void cleanupTestEnvironment() {
        browser.close();
        browser.quit();
    }
}
