package api.author.authors.controller;

import api.author.authors.entity.Author;
import api.author.authors.entity.Work;
import api.author.authors.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("/search")
    public Author searchAuthorByName(@RequestParam String name) {

        return authorService.searchAuthorByName(name);
    }

    @GetMapping("/{authorId}/works")
    public List<Work> searchWorksByAuthorId(@PathVariable String authorId) {

        return authorService.searchWorksByAuthorId(authorId);
    }


}
