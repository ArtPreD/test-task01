package database;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCPostgreSQL {
    private static String URL = "";
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static JDBCPostgreSQL jdbcPostgreSQL;

    private Connection connection = null;

    public static JDBCPostgreSQL getInstance(){
        if(jdbcPostgreSQL == null){
            jdbcPostgreSQL = new JDBCPostgreSQL();
        }
            return jdbcPostgreSQL;
    }

    private JDBCPostgreSQL() {
        init();
    }

    public Connection getDatabaseConnection() throws SQLException {
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if (connection == null){
            throw new SQLException("Cannot connect to database");
        }else {
            return connection;
        }
    }

    public ResultSet getResultSet(String query) throws SQLException {
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        connection.close();
        return resultSet;
    }

    public void writeToDatabase(String query) throws SQLException{
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();

        statement.execute(query);

        statement.close();
        connection.close();
    }

    private void init(){
        readProperty();
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

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null){
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
