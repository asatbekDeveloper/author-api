package api.author.authors.service.impl;

import api.author.authors.entity.Author;
import api.author.authors.entity.Work;
import api.author.authors.repository.AuthorRepository;
import api.author.authors.repository.WorkRepository;
import api.author.authors.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final WorkRepository workRepository;

    private final RestTemplate restTemplate;


    public Author searchAuthorByName(String name) {
        Optional<Author> author = authorRepository.findByName(name);
        if (author.isPresent()) {
            return author.get();
        }

        String url = "https://openlibrary.org/search/authors.json?q=" + name;
        var response = restTemplate.getForObject(url, Map.class);
        if (response != null && response.containsKey("docs")) {
            var docs = (List<Map<String, Object>>) response.get("docs");
            if (!docs.isEmpty()) {
                var firstDoc = docs.get(0);
                String key = (String) firstDoc.get("key");

                Author authorExistByOpenLibraryId = isAuthorExistByOpenLibraryId(key);
                if (Objects.nonNull(authorExistByOpenLibraryId)) return authorExistByOpenLibraryId;

                Author newAuthor = new Author();
                newAuthor.setName((String) firstDoc.get("name"));
                newAuthor.setOpenLibraryId(key);
                return authorRepository.save(newAuthor);
            }
        }

        throw new RuntimeException("Author not found");
    }

    public List<Work> searchWorksByAuthorId(String openLibraryId) {

        Author author = findByOpenLibraryIdIfNotExistSaveAuthor(openLibraryId);

        List<Work> works = workRepository.findByAuthorId(author.getId());
        if (!works.isEmpty()) {
            return works;
        }

        String url = "https://openlibrary.org/authors/" + openLibraryId + "/works.json";
        var response = restTemplate.getForObject(url, Map.class);
        if (response != null && response.containsKey("entries")) {
            var entries = (List<Map<String, Object>>) response.get("entries");
            for (var entry : entries) {
                Work work = new Work();
                work.setTitle((String) entry.get("title"));
                work.setAuthor(author);
                workRepository.save(work);
            }
        }

        return workRepository.findByAuthorId(author.getId());


    }

    public Author findByOpenLibraryIdIfNotExistSaveAuthor(String openLibraryId) {
        Author author = isAuthorExistByOpenLibraryId(openLibraryId);

        if (Objects.isNull(author)) {
            String url = "https://openlibrary.org/authors/" + openLibraryId + ".json";
            var response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("personal_name")) {
                String authorName = (String) response.get("personal_name");
                Author newAuthor = new Author();
                newAuthor.setName(authorName);
                newAuthor.setOpenLibraryId(openLibraryId);
                return authorRepository.save(newAuthor);
            }
            throw new RuntimeException("Author not found");
        }
        return author;
    }


    public Author isAuthorExistByOpenLibraryId(String openLibraryId) {
        return authorRepository.findByOpenLibraryId(openLibraryId)
                .orElse(null);
    }

}
