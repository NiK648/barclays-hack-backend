package com.barclayshack.backend.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import com.barclayshack.backend.beans.Book;
import com.barclayshack.backend.beans.PageInfo;

@Repository
public class BookAdapter {

	public int countBooks(PageInfo pageInfo) {

		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.createStatement();
			StringBuilder sb = new StringBuilder("select count(*) from books");
			if (pageInfo.getFilter() != null && !pageInfo.getFilter().isEmpty()) {
				sb.append(" where LOWER(title) like '%").append(pageInfo.getFilter().toLowerCase()).append("%'");
			}
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				result = rs.getInt("count");
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

	public List<Book> fetchBooks(PageInfo pageInfo) {
		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		List<Book> result = new ArrayList<Book>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.createStatement();
			StringBuilder sb = new StringBuilder("select * from books");
			if (pageInfo.getFilter() != null && !pageInfo.getFilter().isEmpty()) {
				sb.append(" where LOWER(title) like '%").append(pageInfo.getFilter().toLowerCase()).append("%'");
			}
			if (pageInfo.getSortColumn() != null && !pageInfo.getSortColumn().isEmpty()) {
				sb.append(" order by ").append(pageInfo.getSortColumn());
				if (pageInfo.getSortDirection() != null && !pageInfo.getSortDirection().isEmpty()) {
					sb.append(" ").append(pageInfo.getSortDirection());
				}
			}
			if (pageInfo.getPageSize() > 0) {
				sb.append(" LIMIT " + pageInfo.getPageSize());
			}
			if (pageInfo.getPageNumber() > 0) {
				sb.append(" OFFSET " + ((pageInfo.getPageNumber() - 1) * pageInfo.getPageSize()));
			}
			rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				Book l = new Book();
				l.setId(rs.getInt("id"));
				l.setTitle(rs.getString("title"));
				l.setAuthors(rs.getString("authors"));
				l.setAverageRating(rs.getDouble("average_rating"));
				l.setIsbn(rs.getInt("isbn"));
				l.setLanguageCode(rs.getString("language"));
				l.setRatingsCount(rs.getInt("ratings_count"));
				l.setPrice(rs.getInt("price"));
				l.setImage(rs.getString("image"));
				result.add(l);
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
