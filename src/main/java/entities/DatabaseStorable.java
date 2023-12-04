package entities;

import java.sql.Connection;
import java.sql.SQLException;

interface DatabaseStorable {
    void insertData(Connection connection) throws SQLException;
}
