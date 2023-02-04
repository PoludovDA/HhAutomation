package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultsPage extends BaseSeleniumPage {

    @FindBy(xpath = "//a[@data-qa='advanced-search']")
    private WebElement bigFind;

    @FindBy(xpath = "//div[@data-qa='vacancies-search-header']/h1")
    private WebElement countVacansion;

    @FindBy(xpath = "//div[@class='bloko-form-item']//span[text()='Указан доход']")
    private WebElement visibleMoneyBox;

    @FindBy(xpath = "//span[contains(@data-qa, 'vacancy')]")
    private List<WebElement> vacansionMoneyList;

    @FindBy(xpath = "//a[@class='serp-item__title']")
    private List<WebElement> vacansionList;

    public void clickVacansion(int i) {
        vacansionList.get(i).click();
    }

    public FindParamsPage goToFindParams() {
        bigFind.click();
        return new FindParamsPage();
    }

    public Integer getCountVacansion() {
        return Integer.parseInt(countVacansion.getText().replaceAll("[^0-9]", ""));
    }

    public ResultsPage setVisibleMoneyBox() throws InterruptedException {
        visibleMoneyBox.click();
        Thread.sleep(2000);
        return this;
    }

    public List<Integer> getMoneyForJobList() {
        List<String> moneyJobListStr = vacansionMoneyList.stream().map(WebElement :: getText).collect(Collectors.toList());
        List<Integer> moneyJobList = new ArrayList<>();
        moneyJobListStr.forEach(x-> moneyJobList.add((Integer.parseInt(x
                .split(" – ")[0]
                .replaceAll("[^0-9]", "")))));

        return moneyJobList.stream().filter(x-> x > 20000).collect(Collectors.toList());
    }

    public Map<String, Integer> getNameAndMoneyJobMap() {
        List<String> nameList = vacansionList.stream().map(WebElement :: getText).collect(Collectors.toList());
        List<String> moneyJobListStr = vacansionMoneyList.stream().map(WebElement :: getText).collect(Collectors.toList());
        List<Integer> moneyJobList = new ArrayList<>();
        moneyJobListStr.forEach(x-> moneyJobList.add((Integer.parseInt(x
                .split(" – ")[0]
                .replaceAll("[^0-9]", "")))));

        for (int i = 0; i < moneyJobList.size(); i++) {
            if (moneyJobList.get(i) < 20000) {
                nameList.remove(i);
                moneyJobList.remove(i);
            }
        }

        Map<String, Integer> res = new HashMap<>();
        for (int i = 0; i < nameList.size(); i++) {
            res.put(nameList.get(i), moneyJobList.get(i));
        }
        return res;
    }

    public ResultsPage() {
        PageFactory.initElements(driver, this);
    }
}
