package mft.library.model.repository;

import java.sql.*;


public class ConnectionProvider {
    public Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.OracleDriver");
        return DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "javase",
                "java123");
    }

    public int nextId(String sequenceName) throws Exception {
        PreparedStatement preparedStatement = getConnection().prepareStatement(String.format("SELECT %s.nextval as NEXT_ID from dual", sequenceName));
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("NEXT_ID");
    }
}
