package database.service;

import database.JDBCPostgreSQL;
import database.entity.Cooperator;
import database.service.utils.ListCreator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CooperatorsService {

    private JDBCPostgreSQL jdbcPostgreSQL;

    public CooperatorsService() {
        this.jdbcPostgreSQL = JDBCPostgreSQL.getInstance();
    }

    public List<Cooperator> findAllCoopFromDep(int departmentID) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("SELECT * FROM COOPERATORS WHERE DEPARTMENT_ID=?");
        statement.setObject(1, departmentID);
        List<Cooperator> cooperators = ListCreator.createCoopList(statement.executeQuery());
        statement.close();
        return cooperators;
    }

    public Cooperator findByEmail(String email) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("SELECT * FROM COOPERATORS WHERE EMAIL=?");
        statement.setObject(1, email);
        ResultSet resultSet = statement.executeQuery();
        Cooperator cooperator = createCoopFromResultSet(resultSet);
        statement.close();
        return cooperator;
    }

    public Cooperator findById(int id) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("SELECT * FROM COOPERATORS WHERE ID=?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        Cooperator cooperator = createCoopFromResultSet(resultSet);
        statement.close();
        return cooperator;
    }


    public void deleteCoop(int id) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("DELETE FROM COOPERATORS WHERE ID=?");
        statement.setObject(1, id);
        statement.executeUpdate();
        statement.close();
    }

    public void createCoop(String name, String email, int depId, int salary, Date date) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement(
                        "INSERT INTO COOPERATORS(NAME, EMAIL, DEPARTMENT_ID, SALARY, DATE) VALUES(?,?,?,?,?)");
        statement.setObject(1, name);
        statement.setObject(2, email);
        statement.setObject(3, depId);
        statement.setObject(4, salary);
        statement.setObject(5, new java.sql.Date(date.getTime()));
        statement.executeUpdate();
        statement.close();
    }

    public void updateCooperator(Cooperator cooperator) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement(
                        "UPDATE COOPERATORS SET NAME=?, EMAIL=?, SALARY=?, DATE=? WHERE ID=?");
        statement.setObject(1, cooperator.getName());
        statement.setObject(2, cooperator.getEmail());
        statement.setObject(3, cooperator.getSalary());
        statement.setObject(4, new java.sql.Date(cooperator.getDate().getTime()));
        statement.setObject(5, cooperator.getId());
        statement.executeUpdate();
        statement.close();
    }

    private Cooperator createCoopFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Cooperator(
                    resultSet.getInt("ID"),
                    resultSet.getString("NAME"),
                    resultSet.getString("EMAIL"),
                    resultSet.getInt("SALARY"),
                    resultSet.getDate("DATE"));
        } else {
            return null;
        }
    }
}
