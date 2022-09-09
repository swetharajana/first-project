package Minorproject;

import java.sql.Connection;
import java.sql.DriverManager;

public class connectiondb {
	static Connection connect=null;
	public static Connection getConnection() 
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connect=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl.iiht.tech", "scott", "tiger");
			if(connect!=null) {
				System.out.println("Connection Established");
			}
			else {
				System.out.println("can't connect");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Connection Failed");
			
		}
		return connect;
	}
}
	
