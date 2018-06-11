package com.pegaxchange.java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pegaxchange.java.bean.Product;

public class DBServices {

	public static Connection getConnection() {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection con = DriverManager.getConnection("jdbc:mysql://mysqldb.cbniqtuskgza.us-east-2.rds.amazonaws.com:3306/mySqlDBName", "gil", "305844789");

			//return con;
			
			
			  Class.forName("com.mysql.jdbc.Driver");
		      String dbName = System.getProperty("RDS_DB_NAME1");
		      System.out.println("dbName: "+dbName);
		      String userName = System.getProperty("RDS_USERNAME1");
		      String password = System.getProperty("RDS_PASSWORD1");
		      String hostname = System.getProperty("RDS_HOSTNAME1");
		      String port = System.getProperty("RDS_PORT1");
		      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
		      System.out.println(">>>> jdbcUrl: "+jdbcUrl);
		      Connection con = DriverManager.getConnection(jdbcUrl);
		      System.out.println("Remote connection successful.");
		      return con;
			
		} catch (Exception e) {
			System.out.println("------- ERROR getConnection -------");
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	public static void saveProducts(Product p) {
		try {
			Connection con = getConnection();
			if (con==null) {
				System.out.println("Connection is null.");
				return;
			}
			PreparedStatement pstmt = con.prepareStatement("insert into mySqlDBName.products (id, category, name, price, added_on) values (?,?,?,?, now())");
			pstmt.setInt(1, p.getId());
			pstmt.setString(2, p.getCategory());
			pstmt.setString(3, p.getName());
			pstmt.setDouble(4, p.getUnitPrice());
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println("------- ERROR saveProducts -------");
			e.printStackTrace(System.out);
		}
	}
	
	public static List<Product> getProducts() {
		List<Product> products = new ArrayList<Product>();
		try {
			Connection con = getConnection();
			if (con==null) {
				System.out.println("Connection is null.");
				return products;
			}
			PreparedStatement pstmt = con.prepareStatement("select id, category, name, price from mySqlDBName.products");
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()) {
				Product product = new Product();
				product.setCategory(res.getString("category"));
				product.setId(res.getInt("id"));
				product.setName(res.getString("name"));
				product.setUnitPrice(res.getDouble("price"));
				products.add(product);
			}
			
			res.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println("------- ERROR saveProducts -------");
			e.printStackTrace(System.out);
		}
		
		return products;
	}

	public static void main(String[] args) {
		
		getConnection();
	}

}
