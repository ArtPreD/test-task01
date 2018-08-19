package database.service;

import database.entity.Cooperator;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface CooperatorsService {
    List<Cooperator> findAllCoopFromDep(int departmentID) throws SQLException;
    Cooperator findByEmail(String email) throws SQLException;
    Cooperator findById(int id) throws SQLException;
    void deleteCoop(int id) throws SQLException;
    void createCoop(String name, String email, int depId, int salary, Date date) throws SQLException;
    void updateCooperator(Cooperator cooperator) throws SQLException;
}
