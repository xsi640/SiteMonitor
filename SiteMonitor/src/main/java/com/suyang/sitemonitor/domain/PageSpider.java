package com.suyang.sitemonitor.domain;

import com.suyang.sitemonitor.domain.enums.DataType;

public class PageSpider {
	private String selector;
	private DataType type;
	private boolean isSaveToFile;

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public boolean isSaveToFile() {
		return isSaveToFile;
	}

	public void setSaveToFile(boolean isSaveToFile) {
		this.isSaveToFile = isSaveToFile;
	}
}
