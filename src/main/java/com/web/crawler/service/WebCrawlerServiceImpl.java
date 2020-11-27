package com.web.crawler.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
		SiteMap siteMap = new SiteMap();
		return webClient.get().uri(webURL)
				//.header(HttpHeaders.CONTENT_TYPE,MediaType.TEXT_HTML_VALUE)
				.retrieve()
				//.toEntity(String.class)
				.bodyToMono(String.class)
				//.subscribe(System.out::println);
				//.doOnSuccess(res-> Mono.just(res))
				//.subscribe(System.out::println)
				.flatMap(content -> {
					SiteMap siteMap1 = new SiteMap();
					siteMap1.getLinks().addAll(getLinksFromHTMLContent(content, webURL));
					return Mono.just(siteMap1);
				});
		//return response;
//		return response.subscribe(content -> {
//			siteMap.getLinks().addAll(getLinksFromHTMLContent(content, webURL));
//			return Mono.just(siteMap);
//		});
		//return Mono.just(siteMap);
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
