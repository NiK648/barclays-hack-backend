package com.barclayshack.backend.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import com.barclayshack.backend.beans.User;

@Repository
public class LoginAdapter {

	public String register(User user) {
		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.prepareStatement("insert into users(username, password, name, email, phone) values(?,?,?,?,?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getPhone());
			boolean ret = stmt.execute();
			if (stmt.getUpdateCount() > 0) {
				return "success";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "failed";
	}

	public User login(User user) {
		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		User result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.prepareStatement("select * from users where username=? and password=?");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.execute();
			rs = stmt.getResultSet();
			if (rs.next()) {
				result = new User();
				result.setUsername(rs.getString("username"));
				result.setName(rs.getString("name"));
				result.setEmail(rs.getString("email"));
				result.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
