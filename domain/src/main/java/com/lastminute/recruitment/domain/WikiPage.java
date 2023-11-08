package com.lastminute.recruitment.domain;

import java.util.List;

public class WikiPage {

    private static final int TO_STRING_CONTENT_MAX_LENGTH = 15;
    private final String title;
    private final String content;
    private final String selfLink;
    private final List<String> links;

    public WikiPage(String title, String content, String selfLink, List<String> links) {
        this.title = title;
        this.content = content;
        this.selfLink = selfLink;
        this.links = links;
    }

    public List<String> getLinks() {
        return links;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "WikiPage{" +
                "title='" + title + '\'' +
                ", content='" + content.substring(0, Math.min(content.length() - 1, TO_STRING_CONTENT_MAX_LENGTH)) + '\'' +
                ", selfLink='" + selfLink + '\'' +
                ", links=" + links +
                '}';
    }
}
