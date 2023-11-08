package com.lastminute.recruitment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.WikiPageRepository;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.persistence.InMemoryWikiPageRepository;
import com.lastminute.recruitment.reader.html.HtmlParser;
import com.lastminute.recruitment.reader.html.HtmlWikiReader;
import com.lastminute.recruitment.reader.json.JsonParser;
import com.lastminute.recruitment.reader.json.JsonWikiReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class WikiScrapperConfiguration {

    final private ApplicationContext applicationContext;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HtmlWikiClient htmlWikiClient() {
        return new HtmlWikiClient();
    }

    @Bean
    public JsonWikiClient jsonWikiClient() {
        return new JsonWikiClient();
    }

    @Bean
    @Profile("html")
    public WikiReader htmlWikiReader() {
        return new HtmlWikiReader(applicationContext.getBean(HtmlParser.class), applicationContext.getBean(HtmlWikiClient.class));
    }

    @Bean
    @Profile("json")
    public WikiReader jsonWikiReader() {
        return new JsonWikiReader(applicationContext.getBean(JsonParser.class), applicationContext.getBean(JsonWikiClient.class));
    }

    @Bean
    public WikiPageRepository wikiPageRepository() {
        return new InMemoryWikiPageRepository();
    }

    @Bean
    public WikiScrapper wikiScrapper() {
        return new WikiScrapper(applicationContext.getBean(WikiReader.class), applicationContext.getBean(WikiPageRepository.class));
    }
}
