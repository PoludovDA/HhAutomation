package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BaseSeleniumPage {

    @FindBy(xpath = "//div/fieldset/input[@id='a11y-search-input']")
    private WebElement findInput;

    @FindBy(xpath = "//button[contains(@data-qa, 'clarify')]")
    private WebElement otherTown;

    @FindBy(xpath = "//button[contains(@data-qa, 'confirm')]")
    private WebElement rightTown;

    public MainPage(String url) {
        driver.get(url);
        PageFactory.initElements(driver, this);
    }

    public MainPage confirmTown() {
        rightTown.click();
        return this;
    }

    public ResultsPage searchVacansion(String nameVac) {
        findInput.sendKeys(nameVac, Keys.ENTER);
        return new ResultsPage();
    }
}
