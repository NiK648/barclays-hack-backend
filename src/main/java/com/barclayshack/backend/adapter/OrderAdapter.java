package com.barclayshack.backend.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import com.barclayshack.backend.beans.Book;
import com.barclayshack.backend.beans.Order;
import com.barclayshack.backend.beans.OrderItem;
import com.barclayshack.backend.beans.PaymentInfo;
import com.barclayshack.backend.beans.User;
import com.instamojo.wrapper.model.PaymentOrderResponse;

@Repository
public class OrderAdapter {

	public String addOrder(PaymentInfo paymentInfo, double total, String status, String transactionId) {
		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.prepareStatement("insert into orders(username, total, status, transaction_id) values(?,?,?,?)");
			stmt.setString(1, paymentInfo.getUsername());
			stmt.setDouble(2, total);
			stmt.setString(3, status);
			stmt.setString(4, transactionId);
			boolean ret = stmt.execute();
			if (stmt.getUpdateCount() > 0) {
				this.addOrderItems(paymentInfo, transactionId);
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

	public String addOrderItems(PaymentInfo paymentInfo, String transactionId) {
		Order order = this.getOrder(transactionId);
		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.prepareStatement(
					"insert into orderItems(orderId, bookId, title, quantity, price) values(?,?,?,?,?)");
			for (Book book : paymentInfo.getItems()) {
				stmt.setInt(1, order.getId());
				stmt.setInt(2, book.getId());
				stmt.setString(3, book.getTitle());
				stmt.setInt(5, paymentInfo.getCount().get(book.getId()));
				stmt.setInt(5, book.getPrice());
				stmt.addBatch();
			}
			int[] ret = stmt.executeBatch();
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

	public Order getOrder(String transactionId) {
		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		Order result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.prepareStatement(
					"select a.id, a.username,a.total,a.status,a.payment_id,a.transaction_id, b.bookId, b.title, b.quantity, b.price from orders a, orderItems b where a.transaction_id=? and b.orderId=a.id");
			stmt.setString(1, transactionId);
			stmt.execute();
			rs = stmt.getResultSet();
			result = new Order();
			result.setItems(new ArrayList<OrderItem>());
			while (rs.next()) {
				result.setId(rs.getInt("id"));
				result.setUsername(rs.getString("username"));
				result.setTotal(rs.getDouble("total"));
				result.setPayment_id(rs.getString("payment_id"));
				result.setStatus(rs.getString("status"));
				OrderItem item = new OrderItem();
				item.setBookId(rs.getInt("bookId"));
				item.setPrice(rs.getInt("price"));
				item.setQuantity(rs.getInt("quantity"));
				item.setTitle(rs.getString("title"));
				result.getItems().add(item);
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

	public void updatePaymentId(String paymentId, String transactionId) {
		Order order = this.getOrder(transactionId);
		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.prepareStatement("update order set payment_id=? where transaction_id=?");
			stmt.setString(1, paymentId);
			stmt.setString(2, transactionId);
			stmt.execute();
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
	}

	public List<Order> getOrders(String username) {
		String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
		Properties props = new Properties();
		props.setProperty("user", "gmlwekweggkfmc");
		props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
		List<Order> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.prepareStatement(
					"select a.id, a.username,a.total,a.status,a.payment_id,a.transaction_id, b.bookId, b.title, b.quantity, b.price from orders a, orderItems b where a.username=? and b.orderId=a.id order by a.id");
			stmt.setString(1, username);
			stmt.execute();
			rs = stmt.getResultSet();
			result = new ArrayList<Order>();
			Order temp = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				if (temp != null && id != temp.getId()) {
					temp = new Order();
					temp.setItems(new ArrayList<OrderItem>());
					temp.setId(id);
				}
				temp.setUsername(rs.getString("username"));
				temp.setTotal(rs.getDouble("total"));
				temp.setPayment_id(rs.getString("payment_id"));
				temp.setStatus(rs.getString("status"));
				OrderItem item = new OrderItem();
				item.setBookId(rs.getInt("bookId"));
				item.setPrice(rs.getInt("price"));
				item.setQuantity(rs.getInt("quantity"));
				item.setTitle(rs.getString("title"));
				temp.getItems().add(item);

				if (result.indexOf(temp) == -1) {
					result.add(temp);
				}
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
