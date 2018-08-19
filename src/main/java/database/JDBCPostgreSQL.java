package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCPostgreSQL {
    private static String URL = "";
    private static String USERNAME = "";
    private static String PASSWORD = "";

    private static HikariDataSource data;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public JDBCPostgreSQL() {
        if (data == null) {
            readProperty();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL + "&user=" + USERNAME + "&password=" + PASSWORD);
            config.setMaximumPoolSize(16);
            data = new HikariDataSource(config);
            init();
        }
    }

    public Connection getDatabaseConnection() throws SQLException {
        Connection connection = data.getConnection();
        if (connection == null) {
            throw new SQLException("Cannot connect to database");
        } else {
            return connection;
        }
    }

    private void init() {
        Connection connection = null;
        Statement statement = null;

        String createTableDepartments = "CREATE TABLE IF NOT EXISTS DEPARTMENTS (" +
                "ID SERIAL NOT NULL, " +
                "NAME VARCHAR(250) NOT NULL UNIQUE, " +
                "PRIMARY KEY (ID) )";

        String createTableCooperators = "CREATE TABLE IF NOT EXISTS COOPERATORS (" +
                "ID SERIAL NOT NULL, " +
                "NAME VARCHAR(250) NOT NULL, " +
                "DEPARTMENT_ID INTEGER NOT NULL, " +
                "SALARY INTEGER NOT NULL," +
                "DATE DATE, " +
                "EMAIL VARCHAR(250) NOT NULL UNIQUE, " +
                "PRIMARY KEY (ID), " +
                "FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENTS(ID)) ";

        try {
            connection = getDatabaseConnection();
            statement = connection.createStatement();

            statement.execute(createTableDepartments);
            statement.execute(createTableCooperators);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readProperty() {
        Properties property = new Properties();
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            property.load(cl.getResourceAsStream("test-config.properties"));

            URL = property.getProperty("db.host");
            USERNAME = property.getProperty("db.login");
            PASSWORD = property.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
