package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wiki")
@RestController
public class WikiScrapperResource {

    private final WikiScrapper wikiScrapper;

    public WikiScrapperResource(WikiScrapper wikiScrapper) {
        this.wikiScrapper = wikiScrapper;
    }

    @PostMapping("/scrap")
    public void scrapWikipedia(@RequestBody String link) {
        System.out.println("Hello Scrap -> " + link);
        wikiScrapper.read(fixLink(link.trim()));
    }

    private static String fixLink(String link) {
        if (link.endsWith("\"")) {
            link = link.substring(0, link.length() - 1);
        }
        if (link.startsWith("\"")) {
            link = link.substring(1);
        }

        return link;
    }

}
