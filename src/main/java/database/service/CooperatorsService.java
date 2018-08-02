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
        return ListCreator.createCoopList(jdbcPostgreSQL.getResultSet(
                "SELECT * FROM COOPERATORS WHERE DEPARTMENT_ID=" + departmentID));
    }

    public Cooperator findByEmail(String email) throws SQLException {
        ResultSet resultSet = jdbcPostgreSQL.getResultSet(
                "SELECT * FROM COOPERATORS WHERE EMAIL=" + "'" + email + "'");
        if(resultSet.next()) {
            return new Cooperator(resultSet.getInt("ID"), resultSet.getString("NAME"),
                    resultSet.getString("EMAIL"), resultSet.getInt("SALARY"),
                    resultSet.getDate("DATE"));
        }else {
            return null;
        }
    }

    public Cooperator findById(int id) throws SQLException {
        ResultSet resultSet = jdbcPostgreSQL.getResultSet(
                "SELECT * FROM COOPERATORS WHERE ID=" + "'" + id + "'");
        if(resultSet.next()) {
            return new Cooperator(id, resultSet.getString("NAME"),
                    resultSet.getString("EMAIL"), resultSet.getInt("SALARY"),
                    resultSet.getDate("DATE"));
        }else {
            return null;
        }
    }

    public void deleteCoop(int id) throws SQLException{
        jdbcPostgreSQL.writeToDatabase("DELETE FROM COOPERATORS WHERE ID=" + id);
    }

    public void createCoop(String name, String email, int depId, int salary, Date date) throws SQLException{
        PreparedStatement pr = jdbcPostgreSQL.getDatabaseConnection().prepareStatement(
                "INSERT INTO COOPERATORS(NAME, EMAIL, DEPARTMENT_ID, SALARY, DATE)" +
                        " VALUES('"+name+"', '"+email+"', "+depId+", "+salary+", ?)");
        pr.setObject(1, new java.sql.Date(date.getTime()));
        pr.executeUpdate();
        pr.close();
    }

    public void updateCooperator(Cooperator cooperator) throws SQLException {
        PreparedStatement pr = jdbcPostgreSQL.getDatabaseConnection().prepareStatement(
                "UPDATE COOPERATORS SET NAME='" + cooperator.getName() +
                        "', EMAIL='" + cooperator.getEmail() + "', SALARY=" + cooperator.getSalary() + ", DATE=?" +
                        " WHERE ID=" + cooperator.getId());
        pr.setObject(1, new java.sql.Date(cooperator.getDate().getTime()));
        pr.executeUpdate();
        pr.close();
    }
}
