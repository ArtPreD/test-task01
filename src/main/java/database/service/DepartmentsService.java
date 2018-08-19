package database.service;

import database.entity.Department;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentsService {
    List<Department> findAll() throws SQLException;
    Department findByName(String name) throws SQLException;
    Department findDepById(int id) throws SQLException;
    void createDepartment(String name) throws SQLException;
    void deleteDepartment(int id) throws SQLException;
    void updateDepartment(Department department) throws SQLException;
}
