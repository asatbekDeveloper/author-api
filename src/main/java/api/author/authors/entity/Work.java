package api.author.authors.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Author author;
}