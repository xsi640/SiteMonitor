package com.suyang.sitemonitor.runner;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSFile;
import com.poreader.common.HttpUtils;
import com.poreader.common.HttpUtils.Method;
import com.poreader.common.RegexUtils;
import com.poreader.common.TypeParse;
import com.poreader.common.UUIDUtils;
import com.poreader.common.engines.StandardCircleEngine;
import com.suyang.sitemonitor.dao.ErrorPageDao;
import com.suyang.sitemonitor.dao.FileDao;
import com.suyang.sitemonitor.dao.PageDao;
import com.suyang.sitemonitor.dao.SiteDao;
import com.suyang.sitemonitor.domain.ErrorPage;
import com.suyang.sitemonitor.domain.ListSpider;
import com.suyang.sitemonitor.domain.MaxPageSpider;
import com.suyang.sitemonitor.domain.MetaData;
import com.suyang.sitemonitor.domain.Page;
import com.suyang.sitemonitor.domain.Site;
import com.suyang.sitemonitor.domain.enums.DataType;
import com.suyang.sitemonitor.domain.enums.PageType;
import com.suyang.sitemonitor.exceptions.ExistsException;
import com.suyang.sitemonitor.utils.SpringUtils;
import com.suyang.sitemonitor.domain.PageSpider;

public class SiteRunner implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(SiteRunner.class);

	private FileDao fileDao = SpringUtils.getBean(FileDao.class);
	private PageDao pageDao = SpringUtils.getBean(PageDao.class);
	private SiteDao siteDao = SpringUtils.getBean(SiteDao.class);
	private ErrorPageDao errorPageDao = SpringUtils.getBean(ErrorPageDao.class);

	private Site site;
	private StandardCircleEngine engine;
	private int maxPage;

	public SiteRunner(Site site) {
		this.site = site;
		engine = new StandardCircleEngine(this);
		engine.setDetectSpanInSecs(site.getSpiderSeconds() * 1000);
		engine.start();
	}

	@Override
	public void run() {
		try {
			this.validate();
		} catch (IllegalArgumentException e) {
			logger.error("validate erorr name:" + site == null ? "null" : site.getName(), e);
		}

		logger.info("处理上次失败的");
		handleErrorPage();
		logger.info("开始抓取");
		try {
			// 获取最大页码
			getMaxPage();
			for (int i = 0; i <= this.maxPage; i++) {
				String url = getListUrl(i);
				getListPage(url);
			}
		} catch (ExistsException e) {
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.info("本次抓取完成.");
		site.setLastCrawTime(new Date());
		siteDao.save(site);
	}

	private void handleErrorPage() {
		List<ErrorPage> errors = errorPageDao.findBySiteId(site.getId());
		if (errors == null || errors.isEmpty())
			return;

		for (ErrorPage error : errors) {
			String url = error.getUrl();
			if (error.getType().equals(PageType.List)) {
				getListPage(url);
			} else if (error.getType().equals(PageType.Page)) {
				getPageDetail(url);
			}
		}
		errorPageDao.delete(errors);
	}

	private void getListPage(String url) {
		ListSpider spider = this.site.getListUrlSpider();
		Document doc = getPage(url);
		if (doc == null) {
			// 记录失败的
			ErrorPage err = new ErrorPage();
			err.setType(PageType.List);
			err.setSiteId(site.getId());
			err.setUrl(url);
			errorPageDao.insert(err);
		} else {
			Elements elePages = doc.select(spider.getSelector());
			for (Element ele : elePages) {
				String u = ele.attr(spider.getAttr());
				getPageDetail(u);
			}
		}
	}

	private void getPageDetail(String url) {
		Map<String, PageSpider> map = this.site.getMetaDataSpider();
		Page page = pageDao.findOneBySiteIdAndUrl(site.getId(), url);
		if (page == null) {
			Document doc = getPage(url);
			if (doc == null) {
				// 记录失败的
				ErrorPage err = new ErrorPage();
				err.setType(PageType.Page);
				err.setSiteId(site.getId());
				err.setUrl(url);
				errorPageDao.insert(err);
			} else {
				page = new Page();
				page.setSiteId(site.getId());
				page.setUrl(url);
				BasicDBObject dbObject = page.getMetadata();
				for (String key : map.keySet()) {
					PageSpider spider = map.get(key);
					MetaData data = new MetaData();
					String value = "";
					Element ele = doc.selectFirst(spider.getSelector());
					if (ele != null) {
						if (spider.getType().equals(DataType.Html)) {
							value = ele.html();
						} else if (spider.getType().equals(DataType.Text)) {
							value = ele.text();
							if (RegexUtils.isUrl(value)) {
								String newUrl = "";
								try {
									newUrl = HttpUtils.getRedirectUrl(value);
								} catch (IOException e) {
									logger.error("get redirect url. url:" + url, e);
								}
								if (!StringUtils.isEmpty(newUrl)) {
									value = newUrl;
								}
							}
						} else if (spider.getType().equals(DataType.File)) {
							value = ele.text();
							if (RegexUtils.isUrl(value)) {
								value = saveFile(value);
							}
						}
					}

					data.setType(spider.getType());
					data.setText(value);
					dbObject.put(key, data);
				}
				pageDao.insert(page);
			}
		} else {
			throw new ExistsException("the page exists");
		}
	}

	private String saveFile(String src) {
		try {
			HttpURLConnection connection = HttpUtils.getConnection(src, null, Method.GET);
			InputStream inputStream = HttpUtils.getInputStream(connection, null, null);
			GridFSFile fs = fileDao.save(inputStream, UUIDUtils.toString(UUID.randomUUID()), null);
			if (fs != null)
				return fs.getFilename();
		} catch (IOException e) {
			logger.error(MessageFormat.format("save image error url:{}", src), e);
		}
		return "";
	}

	private void getMaxPage() throws Exception {
		MaxPageSpider spider = this.site.getMaxPageSpider();
		String url = getListUrl(1);
		Document doc = getPage(url);
		if (doc == null) {
			throw new Exception("get max page error. url:" + url);
		}
		Element ele = doc.selectFirst(spider.getSelector());
		if (spider.isSpit()) {
			String[] arr = ele.text().split(spider.getSpit());
			if (arr.length >= spider.getIndex()) {
				this.maxPage = TypeParse.toInt(arr[spider.getIndex()]);
			}
		} else {
			this.maxPage = TypeParse.toInt(ele.text());
		}
	}

	private Document getPage(String url) {
		try {
			HttpUtils.trustHttpsEveryone();
			Connection conn = HttpConnection.connect(url);
			conn.timeout(120000);
			conn.header("Accept-Encoding", "gzip,deflate,sdch");
			conn.header("Connection", "close");
			return conn.get();
		} catch (IOException e) {
			logger.error("getPage error url:" + url, e);
			return null;
		}
	}

	private String getListUrl(int index) {
		return MessageFormat.format(this.site.getUrlTemplate(), index);
	}

	private void validate() {
		if (site == null)
			throw new IllegalArgumentException("site is null!!");

		if (site.getMaxPageSpider() == null || site.getListUrlSpider() == null || site.getMetaDataSpider() == null) {
			throw new IllegalArgumentException("spider is null!!");
		}

		if (StringUtils.isEmpty(site.getUrlTemplate())) {
			throw new IllegalArgumentException("url template is null!!!");
		}
	}
}
