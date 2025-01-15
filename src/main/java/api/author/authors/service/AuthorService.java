package api.author.authors.service;

import api.author.authors.entity.Author;
import api.author.authors.entity.Work;

import java.util.List;

public interface AuthorService {
    Author searchAuthorByName(String name);

    List<Work> searchWorksByAuthorId(String authorId);
}
