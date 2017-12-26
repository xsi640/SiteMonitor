package com.suyang.sitemonitor.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.suyang.sitemonitor.dao.FileDao;

@Repository
public class FileDaoImpl implements FileDao {

	private static Logger logger = LoggerFactory.getLogger(FileDaoImpl.class);

	@Autowired
	private GridFsOperations operations;

	@Override
	public GridFSFile save(InputStream inputStream, String fileName, Map<String, String> metadata) {
		DBObject dbData = new BasicDBObject();
		if (metadata != null && metadata.size() > 0) {
			for (String key : metadata.keySet()) {
				dbData.put(key, metadata.get(key));
			}
		}
		return operations.store(inputStream, fileName, dbData);
	}

	@Override
	public InputStream read(String fileName) {
		try {
			return operations.getResource(fileName).getInputStream();
		} catch (IllegalStateException | IOException e) {
			logger.error("read file. filename:" + fileName, e);
		}
		return null;
	}

	@Override
	public GridFSDBFile findOne(Map<String, String> metadata) {
		if(metadata == null || metadata.isEmpty())
			throw new IllegalArgumentException("metadata is empty.");
		Query q = new Query();
		for(String key : metadata.keySet()) {
			q.addCriteria(Criteria.where(key).is(metadata.get(key)));
		}
		return operations.findOne(q);
	}

	@Override
	public List<GridFSDBFile> find(Map<String, String> metadata) {
		if(metadata == null || metadata.isEmpty())
			throw new IllegalArgumentException("metadata is empty.");
		Query q = new Query();
		for(String key : metadata.keySet()) {
			q.addCriteria(Criteria.where(key).is(metadata.get(key)));
		}
		return operations.find(q);
	}

}
