package mft.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import mft.library.model.entity.enums.DriverLicenseType;


import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

public class DriverLicense {
    private int id;
    private int licenseId;
    private LocalDate dateTime;
    private LocalDate expire;
    private DriverLicenseType driverLicenseType;
    private String city;
    private Person person;

}
