package com.lastminute.recruitment.reader.html;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.reader.RepositoryHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"it", "html"})
public class HtmlControllerITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RepositoryHelper repositoryHelper;

    @Test
    public void testController() {
        // given
        // when
        final ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/wiki/scrap", "\"http://wikiscrapper.test/site5\"", Void.class);

        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        final Map<Long, WikiPage> allPages = repositoryHelper.loadAllPages();

        assertThat(allPages).hasSize(5);

        List<String> allLinks = allPages.values().stream().map(WikiPage::getSelfLink).toList();
        assertThat(allLinks).hasSize(5);
        assertThat(allLinks).containsExactlyInAnyOrder(
                "http://wikiscrapper.test/site1",
                "http://wikiscrapper.test/site2",
                "http://wikiscrapper.test/site3",
                "http://wikiscrapper.test/site4",
                "http://wikiscrapper.test/site5");
    }

    @Test
    public void testControllerNotFound() {
        // given
        // when
        final ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/wiki/scrap", "\"http://wikiscrapper.test/site0\"", Void.class);

        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testControllerWrongPage() {
        // given
        // when
        final ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/wiki/scrap", "\"http://wikiscrapper.test/site-wrong\"", Void.class);

        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    public void testControllerEmptyRequest() {
        // given
        // when
        final ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/wiki/scrap", "", Void.class);

        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
