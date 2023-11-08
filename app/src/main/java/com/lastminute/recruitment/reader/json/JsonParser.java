package com.lastminute.recruitment.reader.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.error.WikiPageCannotParse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

record JsonWikiPage(
        String title,
        String content,
        String selfLink,
        List<String> links) {
}

@Component
@Profile("json")
@RequiredArgsConstructor
public class JsonParser {
    final private ObjectMapper objectMapper;

    public WikiPage parse(String json) throws WikiPageCannotParse {
        try {
            final JsonWikiPage jsonWikiPage = objectMapper.readValue(json, JsonWikiPage.class);
            return mapJsonWikiPage2WikiPage(jsonWikiPage);
        } catch (Exception e) {
            throw new WikiPageCannotParse("Cannot parse json", e);
        }
    }

    private WikiPage mapJsonWikiPage2WikiPage(JsonWikiPage page) {
        return new WikiPage(page.title(), page.content(), page.selfLink(), new ArrayList<>(page.links()));
    }
}
