package com.barclayshack.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclayshack.backend.adapter.BookAdapter;
import com.barclayshack.backend.beans.Book;
import com.barclayshack.backend.beans.FilteredList;
import com.barclayshack.backend.beans.PageInfo;

@Service
public class BookService {

	@Autowired
	private BookAdapter adapter;

	public FilteredList<Book> fetchBooks(PageInfo pageInfo) {
		FilteredList<Book> result = new FilteredList<Book>();
		result.setItems(adapter.fetchBooks(pageInfo));
		result.setTotal(adapter.countBooks(pageInfo));
		return result;
	}

}
