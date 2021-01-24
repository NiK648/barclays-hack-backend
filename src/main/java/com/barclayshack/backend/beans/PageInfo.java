package com.barclayshack.backend.beans;

public class PageInfo {

	private int pageSize;
	private int pageNumber;
	private String sortColumn;
	private String sortDirection;
	private String filter;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

}
