package com.suyang.sitemonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.suyang.sitemonitor.dao.SiteDao;
import com.suyang.sitemonitor.domain.Site;

@RestController
public class SiteController {
	@Autowired
	private SiteDao siteDao;

	@RequestMapping(value = "/api/site/{id}", method = RequestMethod.GET)
	public Site findOne(@PathVariable("id") String id) {
		return siteDao.findOne(id);
	}
	
	@RequestMapping(value = "/api/site", method = RequestMethod.GET)
	public List<Site> findAll(){
		return siteDao.findAll();
	}
	
	@RequestMapping(value = "/api/site", method = RequestMethod.POST)
	public Site save(@RequestBody Site site) {
		return siteDao.insert(site);
	}
	
	@RequestMapping(value = "/api/site", method = RequestMethod.PUT)
	public Site modify(@RequestBody Site site) {
		return siteDao.save(site);
	}
	
	@RequestMapping(value = "/api/site/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") String id) {
		siteDao.delete(id);
	}
}
