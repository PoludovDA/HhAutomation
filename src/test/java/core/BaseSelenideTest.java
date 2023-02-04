package core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

abstract public class BaseSelenideTest {

    /**
     * Инициализация selenide с настройками
     */
    public void setUp() {
        //Установка драйвера + путь к нему + запуск
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        //Верить вебдрайверменеджер
        Configuration.driverManagerEnabled = true;
        //Размер окна браузера
        Configuration.browserSize = "2200x1180";
        //Показывать ли вирт браузер
        Configuration.headless = false;
    }

    /**
     * Выполнение метода перед каждым запуском тестов
     */
    @BeforeEach
    public void init() {
        setUp();
    }

    /**
     * Выполнение метода после каждого закрытия тестов
     */
    @AfterEach
    public void tearDown(){
        Selenide.closeWebDriver();
    }
}