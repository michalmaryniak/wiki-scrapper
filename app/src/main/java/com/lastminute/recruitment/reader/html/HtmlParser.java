package com.lastminute.recruitment.reader.html;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.error.WikiPageCannotParse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static java.util.function.Predicate.not;

@Component
@Profile("html")
public class HtmlParser {
    public WikiPage parse(String html) throws WikiPageCannotParse {
        try {
            final Document document = Jsoup.parse(html);
            return new WikiPage(document.title(),
                    document.html(),
                    findSelfLink(document.head()),
                    findLinks(document.body())
            );
        } catch (Exception e) {
            throw new WikiPageCannotParse("Cannot parse html", e);
        }
    }

    private static String findSelfLink(Element head) {
        final Element element = head.selectFirst("meta[selfLink]");
        Objects.requireNonNull(element, "Cannot find element: meta[selfLink]");
        return element.attr("selfLink");
    }

    private List<String> findLinks(Element body) {
        final Elements links = body.select("a[href]");
        return links.stream().map(this::toHref)
                .filter(Objects::nonNull)
                .filter(not(String::isBlank))
                .toList();
    }

    private String toHref(Element element) {
        return element.attr("href");
    }
}
