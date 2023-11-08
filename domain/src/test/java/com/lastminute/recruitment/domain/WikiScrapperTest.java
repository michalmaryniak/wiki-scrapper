package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WikiScrapperTest {

    @Mock
    WikiReader wikiReader;

    @Mock
    WikiPageRepository repository;

    @Test
    void simplePositiveRead() {
        // given
        final WikiPage page1 = new WikiPage("t1", "c1", "link1", List.of());
        when(wikiReader.read(page1.getSelfLink())).thenReturn(page1);

        final WikiScrapper wikiScrapper = new WikiScrapper(wikiReader, repository);

        // when
        wikiScrapper.read("link1");

        // then
        verify(repository, times(1)).save(page1);
    }

    @Test
    void readWithLinkToItself() {
        // given
        final WikiPage page1 = new WikiPage("t1", "c1", "link1", List.of("link1"));
        when(wikiReader.read(page1.getSelfLink())).thenReturn(page1);

        final WikiScrapper wikiScrapper = new WikiScrapper(wikiReader, repository);
        // when
        wikiScrapper.read("link1");

        // then
        verify(repository, times(1)).save(page1);
    }

    @Test
    void readWithLinkToPage2() {
        // given
        final WikiPage page1 = new WikiPage("t1", "c1", "link1", List.of("link2"));
        when(wikiReader.read(page1.getSelfLink())).thenReturn(page1);

        final WikiPage page2 = new WikiPage("t2", "c2", "link2", List.of());
        when(wikiReader.read(page2.getSelfLink())).thenReturn(page2);

        final WikiScrapper wikiScrapper = new WikiScrapper(wikiReader, repository);
        // when
        wikiScrapper.read("link1");

        // then
        verify(repository, times(1)).save(page1);
        verify(repository, times(1)).save(page2);
    }

    @Test
    void read3Pages() {
        // given
        final WikiPage page1 = new WikiPage("t1", "c1", "link1", List.of("link2", "link3"));
        when(wikiReader.read(page1.getSelfLink())).thenReturn(page1);

        final WikiPage page2 = new WikiPage("t2", "c2", "link2", List.of("link2", "link3", "link2"));
        when(wikiReader.read(page2.getSelfLink())).thenReturn(page2);

        final WikiPage page3 = new WikiPage("t3", "c2", "link3", List.of("link2", "link3", "link1"));
        when(wikiReader.read(page3.getSelfLink())).thenReturn(page3);

        final WikiScrapper wikiScrapper = new WikiScrapper(wikiReader, repository);
        // when
        wikiScrapper.read("link1");

        // then
        verify(repository, times(1)).save(page1);
        verify(repository, times(1)).save(page2);
        verify(repository, times(1)).save(page3);
    }

    @Test
    void readNotExistingPage() {
        // given

        final WikiScrapper wikiScrapper = new WikiScrapper(wikiReader, repository);
        // when / then
        assertThatThrownBy(() -> wikiScrapper.read("link1")).isInstanceOf(WikiPageNotFound.class);
    }

    @Test
    void readWithLinkToNonExistingPage() {
        // given
        final WikiPage page1 = new WikiPage("t1", "c1", "link1", List.of("link100"));
        when(wikiReader.read(page1.getSelfLink())).thenReturn(page1);

        final WikiScrapper wikiScrapper = new WikiScrapper(wikiReader, repository);
        // when
        wikiScrapper.read("link1");

        // then
        verify(repository, times(1)).save(page1);
        // link to non-existing page is ignored
    }
}