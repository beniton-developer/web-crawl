package com.web.crawler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

	private String name;
	private LinkType linkType;
	private String url;
	private final List<Link> subLinks = new ArrayList<>();

}
