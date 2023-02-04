package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

abstract public class BaseSeleniumTest {

    protected WebDriver driver;
    protected static final Logger log = Logger.getLogger(BaseSeleniumTest.class.getName());

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        BaseSeleniumPage.setDriver(driver);
        log.info("Настройка вебдрайвера завершена");
    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
        log.info("Веб драйвер и браузер закрыты");
    }

}
