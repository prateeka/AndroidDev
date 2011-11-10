package com.androidcourse.hw3.util;

public class Factory {
	static DrawableImageMapper drawableImageMapper = null;

	static public DrawableImageMapper getDrawableImageMapperInstance() {
		if (drawableImageMapper == null) {
			drawableImageMapper = new DrawableImageMapper();
		}
		return drawableImageMapper;
	}
}
