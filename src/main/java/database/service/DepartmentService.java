package database.service;

import database.JDBCPostgreSQL;
import database.entity.Department;
import database.service.utils.ListCreator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentService {

    private JDBCPostgreSQL jdbcPostgreSQL;

    public DepartmentService() {
        this.jdbcPostgreSQL = JDBCPostgreSQL.getInstance();
    }

    public List<Department> findAll() throws SQLException {
        return ListCreator.createDepList(jdbcPostgreSQL.getResultSet(
                "SELECT * FROM DEPARTMENTS"));
    }

    public Department findByName(String name) throws SQLException {
        ResultSet resultSet = jdbcPostgreSQL.getResultSet(
                "SELECT * FROM DEPARTMENTS WHERE NAME=" + "'" + name + "'");
        if(resultSet.next()) {
            return new Department(resultSet.getInt("ID"), name);
        }else {
            return null;
        }
    }

    public Department findDepById(int id) throws SQLException{
        ResultSet resultSet = jdbcPostgreSQL.getResultSet(
                "SELECT * FROM DEPARTMENTS WHERE ID=" + "'" + id + "'");
        if(resultSet.next()) {
            return new Department(id, resultSet.getString("NAME"));
        }else {
            return null;
        }
    }

    public void createDepartment(String name) throws SQLException{
        jdbcPostgreSQL.writeToDatabase("INSERT INTO DEPARTMENTS(NAME) VALUES('" + name + "')");
    }

    public void deleteDepartment(int id) throws SQLException{
        jdbcPostgreSQL.writeToDatabase("DELETE FROM DEPARTMENTS WHERE ID=" + id);
    }

    public void updateDepartment(Department department) throws SQLException {
        jdbcPostgreSQL.writeToDatabase("UPDATE DEPARTMENTS SET NAME='" + department.getName() +
        "' WHERE ID=" + department.getId());
    }
}
