package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

	private DAO() {

	}

	public static Connection connectDatabase() {
		String databasePath = "resource/database.accdb";
		String connectSV = "jdbc:ucanaccess://" + databasePath;
		try {
			return DriverManager.getConnection(connectSV);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		DAO.connectDatabase();
	}
}
