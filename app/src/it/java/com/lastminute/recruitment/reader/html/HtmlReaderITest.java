package com.lastminute.recruitment.reader.html;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageCannotParse;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles({"it", "html"})
public class HtmlReaderITest {

    @Autowired
    public WikiReader wikiReader;

    @Test
    public void testSite1() {
        // given
        // when
        WikiPage page = wikiReader.read("http://wikiscrapper.test/site1");

        // then
        assertThat(page).isNotNull();
        assertThat(page.getLinks()).isEmpty();
    }

    @Test
    public void testSite2() {
        // given
        // when
        WikiPage page = wikiReader.read("http://wikiscrapper.test/site2");

        // then
        assertThat(page).isNotNull();
        assertThat(page.getLinks()).hasSize(2);
        assertThat(page.getLinks()).containsExactlyInAnyOrder("http://wikiscrapper.test/site4", "http://wikiscrapper.test/site5");
    }

    @Test
    public void testSite0() {
        // given
        // when / then
        assertThatThrownBy(() ->
                wikiReader.read("http://wikiscrapper.test/site0")
        ).isInstanceOf(WikiPageNotFound.class);
    }

    @Test
    public void testSiteWrong() {
        // given
        // when / then
        assertThatThrownBy(() ->
                wikiReader.read("http://wikiscrapper.test/site-wrong")
        ).isInstanceOf(WikiPageCannotParse.class);
    }

}
