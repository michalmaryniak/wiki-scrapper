package com.lastminute.recruitment.domain;

public interface WikiPageRepository {

    void save(WikiPage wikiPage);
    WikiPage load(long id);
}
