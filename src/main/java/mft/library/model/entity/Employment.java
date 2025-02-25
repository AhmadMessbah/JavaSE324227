package mft.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import mft.library.model.entity.Person;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

public class Employment {
    private int id;
    private Person person;
    private String department;
    private String job;
    private Long salary;
    private LocalDate startDate;
    private LocalDate endDate;
}
