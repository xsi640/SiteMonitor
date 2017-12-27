package com.suyang.sitemonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.suyang.sitemonitor.dao.SiteDao;

@RestController
public class SiteController {
	@Autowired
	private SiteDao siteDao;
	
	
}
