package firefox;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import page.MainPage;
import page.order.OrderPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestSuccessfulFirefox {
    private final int number;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String period;

    public TestSuccessfulFirefox(int number, String name, String surname,
                                 String address, String metro, String phone,
                                 String date, String period) {
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.period = period;
    }

    @Parameterized.Parameters
    public static Object[][] getQuest() {
        return new Object[][]{
                {0, "Иван", "Иванов", "Москва, Тверская 1",
                        "Сокольники", "89856906969", "05.09.2023", "сутки"},
                {1, "Мария", "Петрова", "Новосибирск, Ленина 1",
                        "Тверская", "89070906923", "06.09.2023", "двое суток"}
        };
    }

    private WebDriver driver;

    @Before
    public void startUp() {
        WebDriverManager.firefoxdriver().setup();
    }

    @Test
    public void orderTest() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage mainPage = new MainPage(driver);

        mainPage.acceptByCook();

        mainPage.scrollToByOrder(number);

        mainPage.getOrderButtonsSelenium().get(number).click();

        OrderPage orderPage = new OrderPage(driver);

        orderPage.setName(name);
        orderPage.setSurname(surname);
        orderPage.setAddress(address);
        orderPage.setStationMetro(metro);
        orderPage.setPhone(phone);

        orderPage.clickNextButton();

        orderPage.setDate(date);
        orderPage.setRentalPeriod(period);
        orderPage.checkBoxBlackScooterSelenium();

        orderPage.clickOrderButton();
        orderPage.clickOkButton();

        assertTrue("Заказ не оформлен", orderPage.orderIsProcessedIsVisible());
    }

    @After
    public void teardown() {
        // Закрой браузер
        driver.close();
        driver.quit();
    }
}
