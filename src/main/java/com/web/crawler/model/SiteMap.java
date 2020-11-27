package com.web.crawler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SiteMap {
	
	private List<Link> links = new ArrayList<>();
}
