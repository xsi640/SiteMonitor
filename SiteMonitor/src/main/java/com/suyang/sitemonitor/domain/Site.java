package com.suyang.sitemonitor.domain;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.poreader.common.UUIDUtils;

public class Site {
	@Id
	private String id = UUIDUtils.toString(UUID.randomUUID());
	@NotNull
	private String name;
	private String description;

	private String urlTemplate;
	private Date createTime = new Date();
	private Date lastCrawTime;

	private Spider maxPageSpider;
	private Spider listUrlSpider;
	private Map<String, Spider> metaDataSpider;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlTemplate() {
		return urlTemplate;
	}

	public void setUrlTemplate(String urlTemplate) {
		this.urlTemplate = urlTemplate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastCrawTime() {
		return lastCrawTime;
	}

	public void setLastCrawTime(Date lastCrawTime) {
		this.lastCrawTime = lastCrawTime;
	}

	public Spider getMaxPageSpider() {
		return maxPageSpider;
	}

	public void setMaxPageSpider(Spider maxPageSpider) {
		this.maxPageSpider = maxPageSpider;
	}

	public Spider getListUrlSpider() {
		return listUrlSpider;
	}

	public void setListUrlSpider(Spider listUrlSpider) {
		this.listUrlSpider = listUrlSpider;
	}

	public Map<String, Spider> getMetaDataSpider() {
		return metaDataSpider;
	}

	public void setMetaDataSpider(Map<String, Spider> metaDataSpider) {
		this.metaDataSpider = metaDataSpider;
	}
}
