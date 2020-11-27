package com.web.crawler;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.web.crawler.model.Link;
import com.web.crawler.model.LinkType;
import com.web.crawler.model.SiteMap;
import com.web.crawler.service.WebCrawlerService;

import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class WebCrawlerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	WebCrawlerService webCrawlerService;

	@SneakyThrows
	@Test
	void getSiteMap() {
		String webURL = "someurl";
		
		SiteMap siteMap = new SiteMap();
		Link link1 = new Link("Link name1", LinkType.Image, "url");
		Link link2 = new Link("Link name2", LinkType.URL, "url");
		Link link3 = new Link("Link name3", LinkType.Others, "url");
		Link link4 = new Link("Link name4", LinkType.Image, "url");
		link2.getSubLinks().add(link4);

		siteMap.getLinks().add(link1);
		siteMap.getLinks().add(link2);
		siteMap.getLinks().add(link3);

		when(webCrawlerService.getSiteMap(webURL)).thenReturn(Mono.just(siteMap));

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/sitemap?url=someurl").contentType(MediaType.APPLICATION_JSON_VALUE));
		
		result.andExpect(jsonPath("$.links", hasSize(3))).andDo(print());
	}

}
