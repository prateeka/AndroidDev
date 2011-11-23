package com.androidcourse.hw4.factory;

import com.androidcourse.hw4.dao.CategoryDAO;
import com.androidcourse.hw4.dao.CategoryDAOInMemImpl;

public class Factory {
	private static Factory factory;
	private CategoryDAO categoryDAO;

	private Factory() {
	}

	static public Factory getFactory() {
		if (factory == null) {
			factory = new Factory();
		}
		return factory;
	}

	public CategoryDAO getCategoryDAO() {
		if (categoryDAO == null) {
			categoryDAO = new CategoryDAOInMemImpl();
		}
		return categoryDAO;
	}
}
