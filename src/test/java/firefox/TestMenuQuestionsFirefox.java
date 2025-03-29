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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.MainPage;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestMenuQuestionsFirefox {
    private final String quest;
    private final int number;

    public TestMenuQuestionsFirefox(String quest, int number) {
        this.quest = quest;
        this.number = number;
    }

    @Parameterized.Parameters
    public static Object[][] getQuest() {
        return new Object[][]{
                {"Сутки — 400 рублей. Оплата курьеру — наличными или картой.", 0},
                {"Пока что у нас так: один заказ — один самокат. Если хотите" +
                        " покататься с друзьями, можете просто сделать несколько" +
                        " заказов — один за другим.", 1},
                {"Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая" +
                        " в течение дня. Отсчёт времени аренды начинается с момента," +
                        " когда вы оплатите заказ курьеру. Если мы привезли самокат" +
                        " 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", 2},
                {"Только начиная с завтрашнего дня. Но скоро станем расторопнее.", 3},
                {"Пока что нет! Но если что-то срочное — всегда можно позвонить" +
                        " в поддержку по красивому номеру 1010.", 4},
                {"Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь" +
                        " суток — даже если будете кататься без передышек и во сне." +
                        " Зарядка не понадобится.", 5},
                {"Да, пока самокат не привезли. Штрафа не будет, объяснительной записки" +
                        " тоже не попросим. Все же свои.", 6},
                {"Да, обязательно. Всем самокатов! И Москве, и Московской области.", 7}
        };
    }

    private WebDriver driver;

    @Before
    public void startUp() {
        WebDriverManager.firefoxdriver().setup();
    }

    @Test
    public void accordingMenuFirefoxTest() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage mainPage = new MainPage(driver);

        mainPage.acceptByCook();

        mainPage.scrollToByHeader();

        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(mainPage.getElementsHeaderBy().get(number)));
        mainPage.getElementsHeaderBy().get(number).click();

        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(mainPage.getElementsBy().get(number)));

        assertEquals("Текст не совпадает", quest, mainPage.getElementsBy().get(number).getText());
    }

    @After
    public void teardown() {
        // Закрой браузер
        driver.close();
        driver.quit();
    }
}
