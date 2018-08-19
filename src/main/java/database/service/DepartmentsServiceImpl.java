package database.service;

import database.JDBCPostgreSQL;
import database.entity.Cooperator;
import database.entity.Department;
import database.service.utils.ListCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentService {

    private JDBCPostgreSQL jdbcPostgreSQL;
    private CooperatorsService cooperatorsService;

    public DepartmentService() {
        this.jdbcPostgreSQL = new JDBCPostgreSQL();
        this.cooperatorsService = new CooperatorsService();
    }

    public List<Department> findAll() throws SQLException {
        Connection connection = jdbcPostgreSQL.getDatabaseConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM DEPARTMENTS");
        List<Department> departments = ListCreator.createDepList(statement.executeQuery());
        statement.close();
        connection.close();
        return departments;
    }

    public Department findByName(String name) throws SQLException {
        Connection connection = jdbcPostgreSQL.getDatabaseConnection();
        PreparedStatement statement = connection
                .prepareStatement("SELECT * FROM DEPARTMENTS WHERE NAME=?");
        statement.setObject(1, name);
        ResultSet resultSet = statement.executeQuery();
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                return new Department(id, name);
            } else {
                connection.close();
                return null;
            }
        }finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    public Department findDepById(int id) throws SQLException {
        Connection connection = jdbcPostgreSQL.getDatabaseConnection();
        PreparedStatement statement = connection
                .prepareStatement("SELECT * FROM DEPARTMENTS WHERE ID=?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        try {
            if (resultSet.next()) {
                String name = resultSet.getString("NAME");
                return new Department(id, name);
            } else {
                return null;
            }
        }finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    public void createDepartment(String name) throws SQLException {
        Connection connection = jdbcPostgreSQL.getDatabaseConnection();
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO DEPARTMENTS(NAME) VALUES(?)");
        statement.setObject(1, name);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public void deleteDepartment(int id) throws SQLException {
        Connection connection = jdbcPostgreSQL.getDatabaseConnection();
        List<Cooperator> cooperators = cooperatorsService.findAllCoopFromDep(id);
        PreparedStatement statement;
        if (cooperators != null) {
            statement = connection
                    .prepareStatement("DELETE FROM COOPERATORS WHERE DEPARTMENT_ID=?");
            statement.setObject(1, id);
            statement.executeUpdate();
        }
        statement = connection
                .prepareStatement("DELETE FROM DEPARTMENTS WHERE ID=?");
        statement.setObject(1, id);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public void updateDepartment(Department department) throws SQLException {
        Connection connection = jdbcPostgreSQL.getDatabaseConnection();
        PreparedStatement statement = connection
                .prepareStatement("UPDATE  DEPARTMENTS SET NAME=? WHERE ID=?");
        statement.setObject(1, department.getName());
        statement.setObject(2, department.getId());
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}
