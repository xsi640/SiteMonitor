package com.suyang.sitemonitor.domain;

public class Spider {
	private String selector;
	private boolean isHtml;
	private String attr;
	private boolean isSpit;
	private String spit;
	private int index;

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public boolean isSpit() {
		return isSpit;
	}

	public void setSpit(boolean isSpit) {
		this.isSpit = isSpit;
	}

	public String getSpit() {
		return spit;
	}

	public void setSpit(String spit) {
		this.spit = spit;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
