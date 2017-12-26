package com.suyang.sitemonitor.dao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public interface FileDao {
	public GridFSFile save(InputStream inputStream, String fileName, Map<String, String> metadata);
	
	public InputStream read(String fileName);
	
	public GridFSDBFile findOne(Map<String, String> metadata);
	
	public List<GridFSDBFile> find(Map<String, String> metadata);
}
