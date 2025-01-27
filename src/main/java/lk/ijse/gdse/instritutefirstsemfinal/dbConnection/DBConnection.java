package lk.ijse.gdse.instritutefirstsemfinal.dbConnection;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;

    private Connection connection;

    String userName = "root";
    String password = "Manu2006";
    String url = "jdbc:mysql://localhost:3306/studyHall";

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(url,userName,password);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
}
