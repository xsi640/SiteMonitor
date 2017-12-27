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
	private int spiderSeconds;

	private MaxPageSpider maxPageSpider;
	private ListSpider listUrlSpider;
	private Map<String, PageSpider> metaDataSpider;

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

	public int getSpiderSeconds() {
		return spiderSeconds;
	}

	public void setSpiderSeconds(int spiderSeconds) {
		this.spiderSeconds = spiderSeconds;
	}

	public MaxPageSpider getMaxPageSpider() {
		return maxPageSpider;
	}

	public void setMaxPageSpider(MaxPageSpider maxPageSpider) {
		this.maxPageSpider = maxPageSpider;
	}

	public ListSpider getListUrlSpider() {
		return listUrlSpider;
	}

	public void setListUrlSpider(ListSpider listUrlSpider) {
		this.listUrlSpider = listUrlSpider;
	}

	public Map<String, PageSpider> getMetaDataSpider() {
		return metaDataSpider;
	}

	public void setMetaDataSpider(Map<String, PageSpider> metaDataSpider) {
		this.metaDataSpider = metaDataSpider;
	}
}
