package com.lastminute.recruitment.reader;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RepositoryHelper {
    private final WikiPageRepository repository;

    public Map<Long, WikiPage> loadAllPages() {
        long i = 0;
        WikiPage page;
        final Map<Long, WikiPage> allPages = new HashMap<>();
        do {
            page = repository.load(i);
            if (page != null) {
                allPages.put(i, page);
            }
            i++;
        } while (page != null);
        return allPages;
    }
}