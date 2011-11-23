package com.androidcourse.hw4.activity.listTranslations;

class DisplayTranslationDerived extends DisplayTranslation {
	private int categoryPositionSelected;

	@Override
	protected void refreshTranslation(int pos) {
		System.out.println("pos passed : " + pos);
		categoryPositionSelected = pos;
	}

	protected String getCategorySelected() {
		System.out.println("categoryPositionSelected passed : "
				+ categoryPositionSelected);
		return categoryDAO.getCategories().get(categoryPositionSelected);
	}
}
