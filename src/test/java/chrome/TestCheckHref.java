package chrome;

import core.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.MainPage;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestCheckHref extends BaseTest {
    private final String expected;
    private final String nameLogo;

    public TestCheckHref(String expected, String nameLogo) {
        this.expected = expected;
        this.nameLogo = nameLogo;
    }

    @Parameterized.Parameters
    public static Object[][] getQuest() {
        return new Object[][]{
                {"https://yandex.ru/", "Yandex"},
                {"https://qa-scooter.praktikum-services.ru/", "Scooter"}
        };
    }

    @Test
    public void checkHref() {
        MainPage mainPage = new MainPage();

        assertEquals("Ссылка не корректная", expected, mainPage.checkLogo(nameLogo));
    }
}
