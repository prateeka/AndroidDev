package com.androidcourse.hw4.dao;

import java.util.List;
import java.util.Map;

public interface TranslationDAO {
	List<String> getCategories();

	List<Map<String, String>> getTranslations();
}
