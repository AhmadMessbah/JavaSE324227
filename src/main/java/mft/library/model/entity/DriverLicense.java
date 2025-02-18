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

public class DriverLicense {
    private int id;
    private int licenseId;
    private String person;
    private LocalDate dateTime;
    private LocalDate expire;
    private String city;

    public enum type {
        CAR,
        MOTORCYCLE,
        TRUCK,
        BUS,
        TRACTOR,
        BOAT,
        AIRCRAFT
    }
}
