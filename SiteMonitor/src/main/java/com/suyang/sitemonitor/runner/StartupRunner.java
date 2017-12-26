package com.suyang.sitemonitor.runner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.poreader.common.ThreadUtils;
import com.suyang.sitemonitor.dao.SiteDao;
import com.suyang.sitemonitor.domain.Site;

@Component
public class StartupRunner implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(StartupRunner.class);

	@Autowired
	private SiteDao siteDao;

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("running wall server...");
		try {
			List<Site> sites = siteDao.findAll();
			for (Site site : sites) {
				SiteRunner siteRunner = new SiteRunner(site);
				ThreadUtils.run(siteRunner);
			}
		} catch (Exception ex) {
			logger.error("running wall error. message:{}", ex.getMessage());
		}
	}
}
