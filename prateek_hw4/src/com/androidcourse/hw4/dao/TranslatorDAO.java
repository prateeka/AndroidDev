package com.androidcourse.hw4.dao;

import android.database.Cursor;
import android.os.Bundle;

public interface TranslatorDAO {

	long addTranslations(Bundle bundle);

	Cursor getCategoryCursor();

	Cursor getTranslationCursor(long selectedCategoryItemID);
}
