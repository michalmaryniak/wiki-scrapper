package com.lastminute.recruitment.reader.html;

import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageCannotParse;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.reader.PathHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;

@RequiredArgsConstructor
public class HtmlWikiReader implements WikiReader {

    final private HtmlParser parser;
    final private HtmlWikiClient wikiClient;

    @Override
    public WikiPage read(String link) throws WikiPageNotFound, WikiPageCannotParse {
        try {
            final String htmlPath = wikiClient.readHtml(link);
            final String htmlContent = IOUtils.toString(PathHelper.fixPath(htmlPath), Charset.defaultCharset());
            return parser.parse(htmlContent);
        } catch (WikiPageCannotParse e) {
            throw e;
        } catch (Exception e) {
            throw new WikiPageNotFound(String.format("Cannot access link: %s", link), e);
        }
    }


}
