package com.barclayshack.backend;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.barclayshack.backend.beans.Book;

@Configuration
public class LoadDatabase {

	private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

	private static final boolean initdb = false;

	@Bean
	ApplicationRunner initDatabase() {
		return appRunner -> {

			if (LoadDatabase.initdb) {
				HttpsURLConnection con = null;
				Scanner s = null;

				List<String> imageUrls = new ArrayList<String>();

				try {
					URL u = new URL("https://s3-ap-southeast-1.amazonaws.com/he-public-data/bookimage816b123.json");
					con = (HttpsURLConnection) u.openConnection();
					con.connect();
					s = new Scanner(new InputStreamReader(con.getInputStream()));
					StringBuilder response = new StringBuilder();
					while (s.hasNext()) {
						response.append(s.next());
					}
					JsonParser parser = JsonParserFactory.getJsonParser();
					List<Object> list = parser.parseList(response.toString());
					for (Object obj : list) {
						imageUrls.add((String) ((Map<String, Object>) obj).get("Image"));
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (s != null) {
						s.close();
					}
					if (con != null) {
						con.disconnect();
					}
				}

				List<Book> books = new ArrayList<Book>();

				try {
					URL u = new URL("https://s3-ap-southeast-1.amazonaws.com/he-public-data/books8f8fe52.json");
					con = (HttpsURLConnection) u.openConnection();
					con.connect();
					s = new Scanner(new InputStreamReader(con.getInputStream()));
					s.useDelimiter("\n");
					StringBuilder response = new StringBuilder();
					while (s.hasNext()) {
						response.append(s.next());
					}
					JsonParser parser = JsonParserFactory.getJsonParser();
					List<Object> list = parser.parseList(response.toString());
					Random random = new Random();
					for (Object obj : list) {
						Map<String, Object> temp = (Map<String, Object>) obj;
						try {
							Book book = new Book();
							book.setId((int) temp.get("bookID"));
							book.setTitle((String) temp.get("title"));
							if (book.getTitle().length() > 255) {
								continue;
							}
							book.setAuthors((String) temp.get("authors"));
							if (book.getAuthors().length() > 255) {
								continue;
							}
							book.setAverageRating(Double.valueOf(temp.get("average_rating").toString()));
							book.setIsbn((int) temp.get("isbn"));
							book.setLanguageCode((String) temp.get("language_code"));
							book.setRatingsCount((int) temp.get("ratings_count"));
							book.setPrice((int) temp.get("price"));
							int nextRan = random.nextInt(10);
							book.setImage(imageUrls.get(nextRan));
							books.add(book);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (s != null) {
						s.close();
					}
					if (con != null) {
						con.disconnect();
					}
				}

				String url = "jdbc:postgresql://ec2-54-158-1-189.compute-1.amazonaws.com:5432/d7nsjpeetsu83l";
				Properties props = new Properties();
				props.setProperty("user", "gmlwekweggkfmc");
				props.setProperty("password", "45ce1feef742dd1de9295245598c22c5c0daf15ca5a3432fee8e2c19f9e3ed34");
				Connection conn = null;
				PreparedStatement stmt = null;
				try {
					conn = DriverManager.getConnection(url, props);
					stmt = conn.prepareStatement("insert into books values(?,?,?,?,?,?,?,?,?)");
					for (Book book : books) {
						stmt.setInt(1, book.getId());
						stmt.setString(2, book.getTitle());
						stmt.setString(3, book.getAuthors());
						stmt.setDouble(4, book.getAverageRating());
						stmt.setInt(5, book.getIsbn());
						stmt.setString(6, book.getLanguageCode());
						stmt.setInt(7, book.getRatingsCount());
						stmt.setInt(8, book.getPrice());
						stmt.setString(9, book.getImage());
						stmt.addBatch();
					}
					stmt.executeBatch();
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

		};
	}

}