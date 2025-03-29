package chrome;

import core.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.MainPage;
import page.order.OrderPage;

import static org.junit.Assert.assertEquals;

/**
 * Параметризованный тестовый класс для проверки сообщений об ошибках
 * при оформлении заказа. Наследуется от BaseTest.
 */
@RunWith(Parameterized.class) // Аннотация для параметризованных тестов
public class TestCheckError extends BaseTest {
    private final String message; // Ожидаемое сообщение об ошибке
    private final int number;    // Номер поля/ошибки в списке

    /**
     * Конструктор для параметризованного теста
     * @param message ожидаемое сообщение об ошибке
     * @param number номер поля/ошибки в списке
     */
    public TestCheckError(String message, int number) {
        this.message = message;
        this.number = number;
    }

    /**
     * Метод предоставляет данные для параметризованного теста
     * @return массив параметров для теста:
     *         - сообщение об ошибке
     *         - номер поля/ошибки
     */
    @Parameterized.Parameters
    public static Object[][] getQuest() {
        return new Object[][]{
                {"Введите корректное имя", 0},
                {"Введите корректную фамилию", 1},
                {"Введите корректный адрес", 2},
                {"Выберите станцию", 3},
                {"Введите корректный номер", 4},
        };
    }

    /**
     * Тест проверяет сообщения об ошибках валидации полей формы заказа
     * 1. Принимает куки
     * 2. Нажимает кнопку "Заказать"
     * 3. Нажимает "Далее" без заполнения полей
     * 4. Проверяет сообщение об ошибке для текущего параметра теста
     */
    @Test
    public void checkErrorMessage() {
        MainPage mainPage = new MainPage();
        mainPage.acceptByCook();
        mainPage.clickOrder();

        OrderPage orderPage = new OrderPage();
        orderPage.clickNextButton();

        // Проверяем соответствие сообщения об ошибке ожидаемому
        assertEquals("Сообщение о подсказке не корректное",
                message,
                orderPage.getListErrorMessage().get(number).getText());
    }

}