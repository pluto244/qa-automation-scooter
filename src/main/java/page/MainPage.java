package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.*;

/**
 * Класс главной страницы, наследуется от BasePage.
 * Содержит методы для взаимодействия с элементами главной страницы,
 * реализованные как на Selenide, так и на Selenium.
 */
public class MainPage extends BasePage {

    /**
     * Конструктор с открытием указанного URL
     * @param url адрес страницы для открытия
     */
    public MainPage(String url) {
        Selenide.open(url);
    }

    /**
     * Конструктор с инициализацией драйвера
     * @param driver экземпляр WebDriver
     */
    public MainPage(WebDriver driver) {
        BasePage.driver = driver;
    }

    /**
     * Пустой конструктор
     */
    public MainPage(){
    }

    // Локаторы элементов (Selenide)
    private final SelenideElement nameMenu = $x(".//div[text()='Вопросы о важном']");
    private final SelenideElement cookButton = $(byId("rcc-confirm-button"));
    private final ElementsCollection listMenu = $$x(".//div[@class='accordion__item']");

    // Локаторы элементов (Selenium)
    private final By nameMenuSelenium = By.xpath(".//div[text()='Вопросы о важном']");
    private final By cookButtonSelenium = By.id("rcc-confirm-button");
    private final By orderButton = By.xpath(".//*[text()='Заказать']");
    private final By listMenuSelenium = By.xpath(".//div[@class='accordion__item']");
    private final By orderButtonsSelenium = By.xpath(".//*[contains(@class,'ra12g') and text()='Заказать']");

    /**
     * Проверяет ссылку логотипа
     * @param logo название логотипа (alt-текст)
     * @return значение атрибута href у логотипа
     */
    public String checkLogo(String logo) {
        return driver.findElement(By.xpath(".//img[@alt='" + logo + "']/parent::a")).getAttribute("href");
    }

    // Методы Selenide

    /**
     * Прокручивает страницу до раздела "Вопросы о важном"
     */
    public void scrollToHeader() {
        nameMenu.scrollTo();
    }

    /**
     * Принимает cookies, если отображается кнопка
     */
    public void acceptCook() {
        if (cookButton.isDisplayed()) {
            cookButton.click();
        }
    }

    /**
     * Получает список вопросов из аккордеона
     * @return коллекцию элементов вопросов
     */
    public ElementsCollection getListMenu() {
        return listMenu;
    }

    // Методы Selenium

    /**
     * Получает список элементов заголовков вопросов
     * @return список WebElement
     */
    public List<WebElement> getElementsHeaderBy() {
        return driver.findElements(listMenuSelenium);
    }

    /**
     * Получает список элементов с текстом ответов
     * @return список WebElement
     */
    public List<WebElement> getElementsBy() {
        return driver.findElements(By.xpath(".//div[@class='accordion__item']//p"));
    }

    /**
     * Прокручивает страницу до раздела "Вопросы о важном" (Selenium)
     */
    public void scrollToByHeader() {
        WebElement element = driver.findElement(nameMenuSelenium);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * Получает список кнопок "Заказать"
     * @return список WebElement кнопок заказа
     */
    public List<WebElement> getOrderButtonsSelenium() {
        return driver.findElements(orderButtonsSelenium);
    }

    /**
     * Прокручивает страницу до указанной кнопки "Заказать"
     * @param number индекс кнопки в списке
     */
    public void scrollToByOrder(int number) {
        WebElement element = driver.findElements(orderButtonsSelenium).get(number);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * Принимает cookies (Selenium)
     */
    public void acceptByCook() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver не инициализирован");
        }
        try {
            driver.findElement(cookButtonSelenium).click();
        } catch (Exception e) {
            System.out.println("Не удалось найти кнопку cookies: " + e.getMessage());
        }
    }

    /**
     * Нажимает кнопку "Заказать"
     */
    public void clickOrder(){
        driver.findElement(orderButton).click();
    }

    /**
     * Ожидает появления элемента с ответом по указанному индексу
     * @param number индекс элемента в списке
     */
    public void waitElementOfList(int number){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(getElementsBy().get(number)));
    }
}