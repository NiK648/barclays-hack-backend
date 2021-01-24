package com.barclayshack.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barclayshack.backend.beans.Book;
import com.barclayshack.backend.beans.FilteredList;
import com.barclayshack.backend.beans.PageInfo;
import com.barclayshack.backend.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService service;

	@PostMapping("/listItems")
	public FilteredList<Book> fetchBooks(@RequestBody PageInfo pageInfo) {
		return service.fetchBooks(pageInfo);
	}

}
