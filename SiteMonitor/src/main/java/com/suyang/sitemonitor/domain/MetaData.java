package com.suyang.sitemonitor.domain;

import com.suyang.sitemonitor.domain.enums.DataType;

public class MetaData {
	private String text;
	private DataType type;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

}
