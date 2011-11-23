package com.androidcourse.hw4.dao;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAOInMemImpl implements CategoryDAO {

	private List<String> categories;

	public CategoryDAOInMemImpl() {
		categories = new ArrayList<String>();
		categories.add("Social");
		categories.add("Medical");
	}

	@Override
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
}
