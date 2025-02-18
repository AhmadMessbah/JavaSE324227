package mft.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

public class Borrow {
    private int id;
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
