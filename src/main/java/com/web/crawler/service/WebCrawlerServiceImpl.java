package com.web.crawler.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.web.crawler.model.Link;
import com.web.crawler.model.LinkType;
import com.web.crawler.model.SiteMap;

import reactor.core.publisher.Mono;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

	@Autowired
	WebClient webClient;

	@Override
	public Mono<SiteMap> getSiteMap(String webURL) {
		return webClient.get().uri(webURL)
				.retrieve()
				.bodyToMono(String.class)
				.flatMap(content -> {
					SiteMap siteMap = new SiteMap();
					siteMap.getLinks().addAll(getLinksFromHTMLContent(content, webURL));
					return Mono.just(siteMap);
				});
	}

	private List<Link> getLinksFromHTMLContent(String content, String domain) {
		List<Link> links = new ArrayList<>();
		Document document = Jsoup.parse(content);
		String query = "a[href*=##domain##]".replaceAll("##domain##", domain);
		Elements elements = document.select(query);
		elements.stream().forEach(element -> {
			String url=element.absUrl("href");
			String name= element.text();
			links.add(new Link(name, LinkType.URL, url));
		});
		return links;
	}

}
