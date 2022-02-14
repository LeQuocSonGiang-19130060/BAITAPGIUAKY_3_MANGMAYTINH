package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {

	

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

	public List<Product> getByPrice(double price) {
		ArrayList<Product> result = new ArrayList<Product>();
		String sql = "SELECT * FROM product WHERE price = ?";
		Connection conn = connectDatabase();
		try {
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setDouble(1, price);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setID(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				result.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Product> getByName(String name) {
		ArrayList<Product> result = new ArrayList<Product>();
		String sql = "SELECT * FROM product WHERE name = ?";
		Connection conn = connectDatabase();
		try {
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setString(1,name);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setID(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				result.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Product getByID(String id) {
		String sql = "SELECT * FROM product WHERE id = ?";
		Connection conn = connectDatabase();
		try {
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setString(1, id);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setID(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public boolean insertProduct(Product p) {
		String sql = "INSERT INTO product(id,name,price) VALUES(?,?,?)";
		Connection conn = connectDatabase();
		try {
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setString(1, p.getID());
			stm.setString(2, p.getName());
			stm.setDouble(3, p.getPrice());
			stm.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteProduct(String id) {
		String sql = "DELETE FROM product WHERE id = ?";
		Connection conn = connectDatabase();
		try {
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setString(1, id);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		DAO.connectDatabase();
	}
}
