package com.suyang.sitemonitor.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.suyang.sitemonitor.domain.Site;

@Repository
public interface SiteDao extends MongoRepository<Site, String> {

}
