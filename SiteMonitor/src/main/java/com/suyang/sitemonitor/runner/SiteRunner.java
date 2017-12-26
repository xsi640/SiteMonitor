package com.suyang.sitemonitor.runner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.poreader.common.HttpUtils;
import com.poreader.common.engines.StandardCircleEngine;
import com.suyang.sitemonitor.domain.Site;

public class SiteRunner implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(SiteRunner.class);

	private Site site;
	private StandardCircleEngine engine;

	public SiteRunner(Site site) {
		this.site = site;
		engine = new StandardCircleEngine(this);
		engine.setDetectSpanInSecs(site.getSpiderSeconds() * 1000);
	}

	@Override
	public void run() {
		engine.start();
	}

	private void spiding() {
		try {
			this.validate();
		} catch (IllegalArgumentException e) {
			logger.error("validate erorr name:" + site == null ? "null" : site.getName(), e);
		}

		// 获取最大页码
	}

	private Document getPage(String url) {
		HttpUtils.trustHttpsEveryone();
		return null;
	}

	private void getMaxPage() {

	}

	private void validate() {
		if (site == null)
			throw new IllegalArgumentException("site is null!!");

		if (site.getMaxPageSpider() == null || site.getListUrlSpider() == null || site.getMetaDataSpider() == null) {
			throw new IllegalArgumentException("spider is null!!");
		}
	}
}
