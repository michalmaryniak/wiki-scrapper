package com.lastminute.recruitment.reader.html;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.error.WikiPageCannotParse;
import com.lastminute.recruitment.reader.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HtmlParserTest {

    final private HtmlParser parser = new HtmlParser();

    @Test
    void parseHtml() throws IOException, URISyntaxException {
        // given
        final String fileContent = FileUtils.readResource(this.getClass(), "test-site.html");

        // when
        final WikiPage page = parser.parse(fileContent);

        // then
        assertThat(page.getLinks()).isNotNull();
        assertThat(page.getLinks()).hasSize(4);
        assertThat(page.getLinks()).containsExactly(
                "http://wikiscrapper.test/site1",
                "http://wikiscrapper.test/site2",
                "http://wikiscrapper.test/site3",
                "http://wikiscrapper.test/site4");
    }

    @Test
    void parseWrongHtml() throws IOException, URISyntaxException {
        // given
        final String fileContent = FileUtils.readResource(this.getClass(), "test-wrong-site.html");

        // when / then
        assertThatThrownBy(() -> parser.parse(fileContent)).isInstanceOf(WikiPageCannotParse.class);
    }

}