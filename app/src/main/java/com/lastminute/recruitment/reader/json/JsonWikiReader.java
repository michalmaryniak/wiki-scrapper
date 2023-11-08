package com.lastminute.recruitment.reader.json;

import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageCannotParse;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.reader.PathHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;

@RequiredArgsConstructor
public class JsonWikiReader implements WikiReader {

    final private JsonParser jsonParser;
    final private JsonWikiClient wikiClient;

    @Override
    public WikiPage read(String link) throws WikiPageNotFound, WikiPageCannotParse {
        try {
            final String jsonPath = wikiClient.readJson(link);
            final String jsonContent = IOUtils.toString(PathHelper.fixPath(jsonPath), Charset.defaultCharset());
            return jsonParser.parse(jsonContent);
        } catch (WikiPageCannotParse e) {
            throw e;
        } catch (Exception e) {
            throw new WikiPageNotFound(String.format("Cannot access link: %s", link), e);
        }
    }

}
