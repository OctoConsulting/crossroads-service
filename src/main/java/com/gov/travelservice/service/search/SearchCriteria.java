package com.gov.travelservice.service.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class SearchCriteria {
	private Integer page = 1;
	private Integer pageSize = 100;
	private String sortOrder = "asc";
	private String sortField = null;

	public SearchCriteria() {

	}

	public SearchCriteria(Integer page, Integer pageSize, String sortOrder, String sortField) {
		this.page = page;
		this.pageSize = pageSize;
		setSort(sortOrder, sortField);
	}

	public SearchCriteria(Integer pageSize, String sortOrder, String sortField) {
		this.pageSize = pageSize;
		setSort(sortOrder, sortField);
	}

	public SearchCriteria(String sortOrder, String sortField) {
		setSort(sortOrder, sortField);
	}

	public SearchCriteria(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	private void setSort(String sortOrder, String sortField) {
		if (sortField != null && sortField.trim().length() > 0) {
			this.sortOrder = sortOrder;
			this.sortField = sortField;
		}

	}

	public PageRequest buildPage(String sortField) {
		PageRequest pageRequest = null;
		if (sortField != null && sortField.trim().length() > 0) {
			if ("desc".equalsIgnoreCase(this.sortOrder)) {
				Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortField).ignoreCase();
				pageRequest = PageRequest.of(this.page - 1, this.pageSize, new Sort(order));
			} else if ("asc".equalsIgnoreCase(this.sortOrder)) {
				Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortField).ignoreCase();
				pageRequest = PageRequest.of(this.page - 1, this.pageSize, new Sort(order));
			}
		} else
			pageRequest = PageRequest.of(this.page - 1, this.pageSize);
		return pageRequest;
	}

}
