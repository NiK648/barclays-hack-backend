package com.barclayshack.backend.beans;

import java.util.List;
import java.util.Map;

public class PaymentInfo {

	private List<Book> items;
	private Map<Integer, Integer> count;
	private String name;
	private String email;
	private String phone;

	public List<Book> getItems() {
		return items;
	}

	public void setItems(List<Book> items) {
		this.items = items;
	}

	public Map<Integer, Integer> getCount() {
		return count;
	}

	public void setCount(Map<Integer, Integer> count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
