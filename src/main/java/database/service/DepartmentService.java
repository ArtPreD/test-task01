package database.service;

import database.JDBCPostgreSQL;
import database.entity.Cooperator;
import database.entity.Department;
import database.service.utils.ListCreator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentService {

    private JDBCPostgreSQL jdbcPostgreSQL;
    private CooperatorsService cooperatorsService;

    public DepartmentService() {
        this.jdbcPostgreSQL = JDBCPostgreSQL.getInstance();
        cooperatorsService = new CooperatorsService();
    }

    public List<Department> findAll() throws SQLException {
        return ListCreator.createDepList(jdbcPostgreSQL.getResultSet(
                "SELECT * FROM DEPARTMENTS"));
    }

    public Department findByName(String name) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("SELECT * FROM DEPARTMENTS WHERE NAME=?");
        statement.setObject(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("ID");
            statement.close();
            return new Department(id, name);
        } else {
            statement.close();
            return null;
        }
    }

    public Department findDepById(int id) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("SELECT * FROM DEPARTMENTS WHERE ID=?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("NAME");
            statement.close();
            return new Department(id, name);
        } else {
            statement.close();
            return null;
        }
    }

    public void createDepartment(String name) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("INSERT INTO DEPARTMENTS(NAME) VALUES(?)");
        statement.setObject(1, name);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteDepartment(int id) throws SQLException {
        List<Cooperator> cooperators = cooperatorsService.findAllCoopFromDep(id);
        PreparedStatement statement;
        if (cooperators != null) {
            statement = jdbcPostgreSQL.getDatabaseConnection()
                    .prepareStatement("DELETE FROM COOPERATORS WHERE DEPARTMENT_ID=?");
            statement.setObject(1, id);
            statement.executeUpdate();
            statement.close();
        }
        statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("DELETE FROM DEPARTMENTS WHERE ID=?");
        statement.setObject(1, id);
        statement.executeUpdate();
        statement.close();
    }

    public void updateDepartment(Department department) throws SQLException {
        PreparedStatement statement = jdbcPostgreSQL.getDatabaseConnection()
                .prepareStatement("UPDATE  DEPARTMENTS SET NAME=? WHERE ID=?");
        statement.setObject(1, department.getName());
        statement.setObject(2, department.getId());
        statement.executeUpdate();
        statement.close();
    }
}
