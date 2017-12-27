package com.suyang.sitemonitor.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.suyang.sitemonitor.domain.ErrorPage;

@Repository
public interface ErrorPageDao extends MongoRepository<ErrorPage, String> {
	List<ErrorPage> findBySiteId(String siteId);
}
