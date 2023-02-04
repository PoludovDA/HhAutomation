package FUNC1;

import api.Specifications;
import com.codeborne.selenide.WebDriverRunner;
import core.BaseSeleniumTest;
import helpers.Helper;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;
import pages.MainPage;
import pages.ResultsPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * 1. Ввести в поиск Qa automation java
 * 2. Сохранить число сколько вакансий нашлось
 * 3. Поставить галочку указан доход
 * 4. Проверить что вакансий стало меньше
 * 5. Проверить что у большинства вакансий зп более 90к
 */
public class FindVacansionsTest extends BaseSeleniumTest {
    private final String URL = "https://samara.hh.ru/";
    private final String API_URL = "https://api.hh.ru";

    @Test
    public void checkVacMoreNinety() throws InterruptedException {
        ResultsPage resultsPage = new ResultsPage();

        int countVacansionWithoutVisibleMoney = new MainPage(URL)
                .confirmTown()
                .searchVacansion("Qa automation java")
                .goToFindParams()
                .deleteMyTown()
                .getCountVacansion();

        int countVacansionWithVisibleMoney = resultsPage
                .setVisibleMoneyBox()
                .getCountVacansion();

        Assert.assertTrue(countVacansionWithoutVisibleMoney > countVacansionWithVisibleMoney);

        List<Integer> moneysJob = resultsPage.getMoneyForJobList();
        Assert.assertTrue(Helper.mostMoreThan(moneysJob, 90000));
    }

    @Test
    public void checkApi() {
        Specifications.installSpecifications(Specifications.requestSpecification(API_URL),
                                            Specifications.responseSpecificationUnique(200));

//        JsonPath jsonPath = given().when()
//                .get("/vacancies")
//                .then()
//                .log().all().extract()
//                .body().jsonPath();
//        List<String> namesVac = jsonPath.get("items.name");

        new MainPage(URL)
                .confirmTown()
                .searchVacansion("Qa automation java")
                .goToFindParams()
                .deleteMyTown()
                .clickVacansion(1);

        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        String vacId = driver.getCurrentUrl()
                .split("from")[0]
                .replaceAll("[^0-9]", "");


        JsonPath jsonPath = given()
                .when()
                .get("/vacancies/" + vacId)
                .then().log().all()
                .extract().body().jsonPath();

    }

    @Test
    public void dbTest() {
        try {
            // Адрес нашей базы данных "tsn_pg_demo" на локальном компьютере (localhost)
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";

            // Создание свойств соединения с базой данных
            String login = "postgres";
            String password = "8917037228";

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, login, password);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from hh_log");

            while (resultSet.next())
                System.out.println(resultSet.getString("log"));

            statement.close();

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }
}
