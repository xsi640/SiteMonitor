package com.suyang.sitemonitor.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.poreader.common.UUIDUtils;
import com.suyang.sitemonitor.domain.enums.PageType;

public class ErrorPage {
	@Id
	private String id = UUIDUtils.toString(UUID.randomUUID());
	private String url;
	private PageType type;
	private String siteId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageType getType() {
		return type;
	}

	public void setType(PageType type) {
		this.type = type;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
