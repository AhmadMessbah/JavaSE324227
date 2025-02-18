package mft.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int pageCount;
}
