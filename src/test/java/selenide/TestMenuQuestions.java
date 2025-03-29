package selenide;

import com.codeborne.selenide.Condition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.MainPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestMenuQuestions {
    private final String quest;
    private final int number;

    public TestMenuQuestions(String quest, int number) {
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

    @Test
    public void accordingMenuSelenideTest() {
        MainPage mainPage = new MainPage("https://qa-scooter.praktikum-services.ru/");

        mainPage.acceptCook();

        mainPage.scrollToHeader();

        mainPage.getListMenu().get(number).click();

        assertTrue(mainPage.getListMenu().get(number).shouldBe(Condition.text(quest)).isDisplayed());
    }
}
