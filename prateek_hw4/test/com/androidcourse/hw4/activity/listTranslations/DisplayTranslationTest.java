package com.androidcourse.hw4.activity.listTranslations;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;

import com.androidcourse.hw4.dao.CategoryDAO;
import com.androidcourse.hw4.dao.CategoryDAOInMemImpl;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAdapterView;
import com.xtremelabs.robolectric.shadows.ShadowView;

@RunWith(RobolectricTestRunner.class)
public class DisplayTranslationTest {

	private static final int CATEGORY_SOCIAL_IDX = 0;
	private static final String CATEGORY_SOCIAL = "Social";
	private static final String CATEGORY_MEDICAL = "Medical";
	private static final int CATEGORY_MEDICAL_IDX = 1;

	/*
	 * @Test
	 * public void shouldHaveHappySmiles() throws Exception {
	 * String hello = new DisplayTranslation().getResources().getString(
	 * R.string.hello);
	 * assertThat(hello, equalTo("Hello World, Prateek_hw4Activity!"));
	 * }
	 */

	/*-@Before
	public void setUp() throws Exception {
		displayTranslation = getDisplayTranslationDerived();
		displayTranslation.onCreate(null);
		spinner = (ShadowAdapterView) displayTranslation
				.findViewById(R.id.spinner1);
	}
	 */

	@Test
	public void assertDisplayTranslationSpinnerOnClickReturnsValidCategory()
			throws Exception {
		DisplayTranslationDerived displayTranslation = getDisplayTranslationDerived();
		displayTranslation.onCreate(null);
		displayTranslation.setCategoryDAO(getCategoryDAO());

		ShadowAdapterView spinner = new ShadowAdapterView();
		spinner.setAdapter(displayTranslation.getSpinnerAdapter());
		spinner.setOnItemSelectedListener(getSpinnerOnItemSelectedListener(displayTranslation));

		spinner.setSelection(CATEGORY_SOCIAL_IDX);
		spinner.setSelection(1);
		assertEquals(getCategoryDAO().getCategories().get(CATEGORY_SOCIAL_IDX),
				displayTranslation.getCategorySelected());
		System.out.println(spinner.getOnItemSelectedListener());
		/*-spinner.setSelection(CATEGORY_MEDICAL_IDX);
		assertEquals(
				getCategoryDAO().getCategories().get(CATEGORY_MEDICAL_IDX),
				displayTranslation.getCategorySelected());*/
	}

	protected View findViewByIDUsingShadowView(int id) {
		return new ShadowView().findViewById(id);
	}

	private CategoryDAO getCategoryDAO() {
		CategoryDAOInMemImpl categoryDAOImpl = new CategoryDAOInMemImpl();
		List<String> categories = new ArrayList<String>();
		categories.add(CATEGORY_SOCIAL);
		categories.add(CATEGORY_MEDICAL);
		categoryDAOImpl.setCategories(categories);
		return categoryDAOImpl;
	}

	private DisplayTranslationDerived getDisplayTranslationDerived() {
		return new DisplayTranslationDerived();
	}

	private SpinnerOnItemSelectedListener getSpinnerOnItemSelectedListener(
			DisplayTranslationDerived displayTranslation) {
		return new SpinnerOnItemSelectedListener(displayTranslation);
	}
}
