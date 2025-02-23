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

public class Person {
    private int id;
    private String name;
    private String family;
    private LocalDate birthDate;
    private String username;
    private String password;
    private boolean active = true;
}
