package FUNC1;

import core.SeleniumDataBaseTest;
import helpers.Helper;
import helpers.HelperDB;
import org.junit.Test;
import pages.MainPage;

import java.sql.SQLException;
import java.util.Map;

/**
 * 1. Ввести в поиск Qa automation java
 * 2. Поставить галочку указан доход
 * 3. Отсортировать вакансии в порядке возрастания
 * 4. Сохранить в базу данных название вакансии, зарплату
 * 5. Проверить что сортировка выполнена успешно
 */
public class HhBdTest extends SeleniumDataBaseTest {
    private final String URL = "https://samara.hh.ru/";

    @Test
    public void checkSortMoney() throws InterruptedException, SQLException {
        Map<String, Integer> vacansions = new MainPage(URL)
                .confirmTown()
                .searchVacansion("эскорт")
                .goToFindParams()
                .deleteMyTown()
                .setVisibleMoneyBox()
                .getNameAndMoneyJobMap();

        //Сортировка словаря по значению
        Map<String, Integer> sortedVacansions = Helper.sortedMapByValues(vacansions);

        HelperDB.addVacansionListBd(sortedVacansions, statement);
        HelperDB.truncateTable("job", statement);
    }
}
