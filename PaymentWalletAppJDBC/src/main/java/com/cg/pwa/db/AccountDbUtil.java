package com.cg.pwa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cg.pwa.exception.AccountException;

public class AccountDbUtil {
	public static Connection getConnection() throws AccountException{
		String url="jdbc:mysql://localhost:3306/JdbcDB";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url,"root","root");
		}catch(ClassNotFoundException | SQLException e){
			throw new AccountException(e.getMessage());
		}
	}
}
