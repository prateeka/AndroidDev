package com.actionbarsherlock.sample.shakespeare;

import java.util.HashMap;
import java.util.Map;

public final class Notes {
	private static Map<String, String> notes = new HashMap<String, String>();
	private static String[] titles;
	
	static {
		populateNotes();
		populatetitles();
	}
	
	private static void populateNotes() {
		notes.put("key1", "value1");
		notes.put("key2", "value2");
		notes.put("key3", "value3");
		notes.put("key4", "value4");
		notes.put("key5", "value5");
		notes.put("key6", "value6");
		notes.put("key7", "value7");
	}
	
	private static void populatetitles() {
		titles = notes.keySet().toArray(new String[0]);
	}
	
	public synchronized static String[] getTitles() {
		return titles;
	}
	
	public synchronized static String getDetails(int position) {
		return notes.get(titles[position]);
	}
	
	public synchronized static String getTitles(int position) {
		return titles[position];
	}
}
