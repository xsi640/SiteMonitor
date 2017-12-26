package com.suyang.sitemonitor.domain;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.mongodb.BasicDBObject;
import com.poreader.common.UUIDUtils;

public class Page {
	@Id
	private String id = UUIDUtils.toString(UUID.randomUUID());
	private String url;
	private BasicDBObject metadata = new BasicDBObject();
	private Date createTime = new Date();

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

	public BasicDBObject getMetadata() {
		return metadata;
	}

	public void setMetadata(BasicDBObject metadata) {
		this.metadata = metadata;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
