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

public class JobHistory {
    private int id;
    private String person;
    private String job;
    private String company;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

}
