package model;
import java.io.IOException;
import java.sql.*;
import java.util.TimeZone;

public class JDBCMysql {
	
	static Connection db = null;
	static DatabaseMetaData dbmd;

	public JDBCMysql() throws ClassNotFoundException, SQLException, java.io.IOException{
		String database = "//localhost:3306/forum";
		String username = "root";
		String password = "root";
		Class.forName("com.mysql.cj.jdbc.Driver");
		db = DriverManager.getConnection("jdbc:mysql:"+database+"?serverTimezone=" + TimeZone.getDefault().getID(), username, password);
	}
	
	public static Connection getConnection(){

		if(db==null) {
			try {
				new JDBCMysql();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return db;
	}

}