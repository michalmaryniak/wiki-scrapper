package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageCannotParse;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WikiScrapper {
    //TODO: need to use a proper logging library
    private final static System.Logger logger = System.getLogger(WikiScrapper.class.getName());
    private final WikiReader wikiReader;
    private final WikiPageRepository repository;

    public WikiScrapper(WikiReader wikiReader, WikiPageRepository repository) {
        this.wikiReader = wikiReader;
        this.repository = repository;
    }

    private final Set<String> visitedLinks = ConcurrentHashMap.newKeySet();

    public void read(String link) throws WikiPageNotFound, WikiPageCannotParse {
        visitedLinks.add(link);
        visitLink(link);
    }

    private void visitLinkIfNeeded(String link) {
        if (!visitedLinks.contains(link)) {
            visitedLinks.add(link);
            tryToVisitLink(link);
        }
    }

    private void tryToVisitLink(String link) {
        try {
            visitLink(link);
            //no retries, just logging error and continues
        } catch (WikiPageNotFound e) {
            logger.log(System.Logger.Level.ERROR, String.format("Page: %s not found", link), e);
        } catch (WikiPageCannotParse e) {
            logger.log(System.Logger.Level.ERROR, String.format("Page: %s parsing failed", link), e);
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, String.format("Cannot read page: %s", link), e);
        }
    }

    private void visitLink(String link) throws WikiPageNotFound, WikiPageCannotParse {
        final WikiPage page = wikiReader.read(link);
        if (page == null) {
            throw new WikiPageNotFound(String.format("Cannot visit link: %s", link));
        }
        repository.save(page);
        page.getLinks().forEach(this::visitLinkIfNeeded);
    }

}
