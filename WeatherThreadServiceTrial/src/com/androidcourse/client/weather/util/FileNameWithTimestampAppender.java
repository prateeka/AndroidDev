package com.androidcourse.client.weather.util;

import java.io.File;

import org.apache.log4j.FileAppender;
import org.apache.log4j.spi.ErrorCode;

public class FileNameWithTimestampAppender extends FileAppender {
	
	private final long timestamp = System.currentTimeMillis();
	public static final String DEFAULT_FILENAME = "default.log";
	
	@Override
	public void activateOptions() {
		if (fileName != null) {
			try {
				fileName = getNewLogFileName();
				setFile(fileName, fileAppend, bufferedIO, bufferSize);
			}
			catch (Exception e) {
				errorHandler.error("Error while activating log options", e,
						ErrorCode.FILE_OPEN_FAILURE);
			}
		}
	}
	
	protected String getNewLogFileName() {
		
		if (fileName == null) {
			fileName = DEFAULT_FILENAME;
		}
		
		final String DOT = ".";
		final File logFile = new File(fileName);
		final String fileName = logFile.getName();
		String newFileName = "";
		
		final int dotIndex = fileName.indexOf(DOT);
		if (dotIndex != -1) {
			// the file name has an extension. so, insert the time stamp
			// between the file name and the extension
			newFileName = fileName.substring(0, dotIndex) + getTimestamp()
					+ DOT + fileName.substring(dotIndex + 1);
		} else {
			// the file name has no extension. So, just append the timestamp
			// at the end.
			newFileName = fileName + getTimestamp();
		}
		return logFile.getParent() + File.separator + newFileName;
	}
	
	protected void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	protected long getTimestamp() {
		return timestamp;
	}
}
