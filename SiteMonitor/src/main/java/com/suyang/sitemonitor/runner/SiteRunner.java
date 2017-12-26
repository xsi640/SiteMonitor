package com.suyang.sitemonitor.runner;

import com.suyang.sitemonitor.domain.Site;

public class SiteRunner implements Runnable {

	private Site site;
	
	public SiteRunner(Site site) {
		this.site = site;
	}
	
	@Override
	public void run() {
		
	}

}
