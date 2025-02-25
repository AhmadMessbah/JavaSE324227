package mft.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import mft.library.model.entity.enums.MilitaryType;
import mft.library.model.entity.enums.Province;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class MilitaryLicense {
    //militaryId, person, startMilitaryDate, endMilitaryDate, type, city
    private int id;
    private int militaryId;
    private Person person;
    private MilitaryType type;
    private Province province;
    private LocalDate startMilitaryDate;
    private LocalDate endMilitaryDate;
}
