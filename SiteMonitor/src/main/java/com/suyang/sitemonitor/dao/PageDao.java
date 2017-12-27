package com.suyang.sitemonitor.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.suyang.sitemonitor.domain.Page;

@Repository
public interface PageDao extends MongoRepository<Page, String> {
	public Page findOneBySiteIdAndUrl(String siteId, String url);
}
