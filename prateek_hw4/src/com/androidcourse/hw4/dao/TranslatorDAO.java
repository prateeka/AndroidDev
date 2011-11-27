package com.androidcourse.hw4.dao;

import java.util.List;
import java.util.Map;

import android.os.Bundle;

public interface TranslatorDAO {
	List<String> getCategories();

	List<Map<String, String>> getTranslations(String category);

	List<Map<String, String>> getDefaultTranslations();

	void addTranslations(Bundle bundle);
}
