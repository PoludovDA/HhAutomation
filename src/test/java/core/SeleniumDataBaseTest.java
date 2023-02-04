package core;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.sql.*;

abstract public class SeleniumDataBaseTest extends BaseSeleniumTest {

    protected Statement statement;
    protected ResultSet resultSet;

    @Before
    public void initConnection() throws IOException {

        System.getProperties().load(ClassLoader.getSystemResourceAsStream("database.properties"));
        String typeSql = System.getProperty("typeSql");
        String loginConnection = System.getProperty("loginConnection");
        String passwordConnection = System.getProperty("passwordConnection");
        String server = System.getProperty("server");
        String port = System.getProperty("port");
        String database = System.getProperty("database");

        try {
            log.info("Создание подключения к бд");
            Class.forName("org." + typeSql + ".Driver");
            String url = "jdbc:" + typeSql + "://" + server + ":" + port + "/" + database;

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, loginConnection, passwordConnection);

            // Создание оператора доступа к базе данных
            statement = connection.createStatement();
            log.info("Подключение завершено");

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

    @After
    public void closeConnection() {
        try {
            statement.close();
            log.info("Соединение с бд закрыто");
        } catch (SQLException throwables) {
            log.info("Ошибка в закрытии подключения БД");
            throwables.printStackTrace();
        }
    }
}
