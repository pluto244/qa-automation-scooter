package page.order;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Класс страницы оформления заказа, наследуется от базовой страницы (BasePage)
 * Содержит методы для взаимодействия с элементами формы заказа
 */
public class OrderPage extends BasePage {

    /**
     * Конструктор с инициализацией драйвера
     * @param driver экземпляр WebDriver для управления браузером
     */
    public OrderPage(WebDriver driver) {
        BasePage.driver = driver;
    }

    /**
     * Пустой конструктор для возможности создания страницы без явной передачи драйвера
     */
    public OrderPage(){}

    // Локаторы элементов формы заказа

    // Поля ввода информации о клиенте
    private final By name = By.xpath(".//input[@placeholder='* Имя']");
    private final By surname = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By address = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metro = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phone = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Поля ввода информации о заказе
    private final By date = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriod = By.xpath(".//div[@class='Dropdown-placeholder']");
    private final By blackScooter = By.id("black");

    // Кнопки
    private final By nextButton = By.xpath(".//button[text()='Далее']");
    private final By orderFinalButton = By.xpath(".//button[text()='Назад']/parent::div/button[text()='Заказать']");
    private final By okButton = By.xpath(".//button[text()='Да']");

    // Элементы подтверждения и сообщения
    private final By orderIsProcessed = By.xpath(".//div[text()='Заказ оформлен']");
    private final By listErrorMessage = By.xpath(".//div[contains(@class,'Form')]//div[contains(@class,'ErrorMessage') or contains(@class,'MetroError')]");

    // Кнопка принятия куки
    private final By cookButtonSelenium = By.id("rcc-confirm-button");

    /**
     * Нажимает кнопку "Далее"
     */
    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    /**
     * Нажимает кнопку подтверждения "Да" в модальном окне
     */
    public void clickOkButton() {
        driver.findElement(okButton).click();
    }

    /**
     * Нажимает кнопку "Заказать" на финальном этапе
     */
    public void clickOrderButton() {
        driver.findElement(orderFinalButton).click();
    }

    /**
     * Заполняет поле "Имя"
     * @param name имя клиента
     */
    public void setName(String name) {
        driver.findElement(this.name).sendKeys(name);
    }

    /**
     * Заполняет поле "Фамилия"
     * @param surname фамилия клиента
     */
    public void setSurname(String surname) {
        driver.findElement(this.surname).sendKeys(surname);
    }

    /**
     * Заполняет поле "Адрес"
     * @param address адрес доставки
     */
    public void setAddress(String address) {
        driver.findElement(this.address).sendKeys(address);
    }

    /**
     * Выбирает станцию метро из выпадающего списка
     * @param station название станции метро
     */
    public void setStationMetro(String station) {
        driver.findElement(metro).sendKeys(station);
        waitMetroIsVisible(station);
        containsText(station).click();
    }

    /**
     * Заполняет поле "Телефон"
     * @param phone номер телефона клиента
     */
    public void setPhone(String phone) {
        driver.findElement(this.phone).sendKeys(phone);
    }

    /**
     * Устанавливает дату доставки
     * @param date дата доставки в формате строки
     */
    public void setDate(String date) {
        driver.findElement(this.date).sendKeys(date);
        containsText("5").click();
    }

    /**
     * Устанавливает срок аренды
     * @param period срок аренды (например, "сутки")
     */
    public void setRentalPeriod(String period) {
        driver.findElement(rentalPeriod).click();
        containsText(period).click();
    }

    /**
     * Активирует чекбокс "Черный самокат"
     */
    public void checkBoxBlackScooterSelenium() {
        driver.findElement(blackScooter).click();
    }

    /**
     * Проверяет видимость сообщения об успешном оформлении заказа
     * @return true если сообщение видимо, иначе false
     */
    public boolean orderIsProcessedIsVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(orderIsProcessed));
        return driver.findElement(orderIsProcessed).isDisplayed();
    }

    /**
     * Получает список сообщений об ошибках валидации
     * @return список WebElement с сообщениями об ошибках
     */
    public List<WebElement> getListErrorMessage() {
        return driver.findElements(listErrorMessage);
    }

    /**
     * Вспомогательный метод для поиска элемента по содержащемуся тексту
     * @param text искомый текст
     * @return WebElement содержащий указанный текст
     */
    private WebElement containsText(String text) {
        return driver.findElement(By.xpath(".//*[contains(text(),'" + text + "')]"));
    }

    /**
     * Ожидает появления станции метро в выпадающем списке
     * @param station название станции метро
     */
    private void waitMetroIsVisible(String station) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text(),'" + station + "')]")));
    }
}