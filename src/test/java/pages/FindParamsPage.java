package pages;

import core.BaseSeleniumPage;
import helpers.Helper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FindParamsPage extends BaseSeleniumPage {

    @FindBy(xpath = "//button[@class='bloko-tag-button']")
    private WebElement deleteTown;

    @FindBy(xpath = "//button[contains(@data-qa, 'submit')]")
    private  WebElement submitFind;

    public ResultsPage deleteMyTown() {
        Helper.scrollToElement(deleteTown, driver);
        deleteTown.click();
        submitFind.click();
        return new ResultsPage();
    }

    public FindParamsPage() {
        PageFactory.initElements(driver, this);
    }
}
