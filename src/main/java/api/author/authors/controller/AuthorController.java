package api.author.authors.controller;

import api.author.authors.entity.Author;
import api.author.authors.entity.Work;
import api.author.authors.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "1. Author Controller", description = "Author Related APIs")
@Slf4j
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("/search")
    @Operation(summary = "API ID: Author001")
    public Author searchAuthorByName(@RequestParam String name) {

        return authorService.searchAuthorByName(name);
    }

    @GetMapping("/{authorId}/works")
    @Operation(summary = "API ID: Author002")
    public List<Work> searchWorksByAuthorId(@PathVariable String authorId) {
        return authorService.searchWorksByAuthorId(authorId);
    }


}
