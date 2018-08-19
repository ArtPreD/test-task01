package database.service.utils;

import database.entity.Cooperator;
import database.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ListCreator {

    public static List<Department> createDepList(ResultSet resultSet) throws SQLException {

        List<Department> departments = new LinkedList<>();
        while (resultSet.next()) {
            departments.add(new Department(resultSet.getInt("ID"),
                    resultSet.getString("NAME")));
        }
        resultSet.close();
        if (departments.isEmpty()) {
            return null;
        }
        return departments;
    }

    public static List<Cooperator> createCoopList(ResultSet resultSet) throws SQLException {
        List<Cooperator> cooperators = new LinkedList<>();
        while (resultSet.next()) {
            cooperators.add(new Cooperator(resultSet.getInt("ID"),
                    resultSet.getString("NAME"), resultSet.getString("EMAIL"),
                    resultSet.getInt("SALARY"), resultSet.getDate("DATE")));
        }
        resultSet.close();
        if (cooperators.isEmpty()) {
            return null;
        }
        return cooperators;
    }
}
