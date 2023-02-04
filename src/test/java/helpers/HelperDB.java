package helpers;

import org.openqa.selenium.WebElement;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class HelperDB {

    /**
     * Очищает все данные из таблицы включая инкремент
     * @param tableName - имя таблицы
     */
    public static void truncateTable(String tableName, Statement statement) {
        try {
            statement.executeQuery("truncate " + tableName);
        } catch (SQLException throwables) {
            if(!throwables.getLocalizedMessage().equals("Запрос не вернул результатов."))
                throwables.printStackTrace();
        }
    }

    public static void addVacansionListBd(Map<String, Integer> jobList, Statement statement) {
        jobList.forEach((k, v)-> {
            try {
                statement.executeQuery("INSERT INTO job (name, money) " +
                        "VALUES ('" + k + "', " + v.toString() + ");");
            } catch (SQLException throwables) {
                if(!throwables.getLocalizedMessage().equals("Запрос не вернул результатов."))
                    throwables.printStackTrace();
            }
        });
    }
}
