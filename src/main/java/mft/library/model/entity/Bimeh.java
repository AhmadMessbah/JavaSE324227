package mft.library.model.entity;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import mft.library.model.entity.enums.InsuranceType;
import mft.library.model.entity.enums.InsuranceStatus;
import java.time.LocalDate;


@Setter
@Getter
@SuperBuilder
@ToString
@NoArgsConstructor

public class Bimeh {
    private int id;
    private String policyNumber;
    private InsuranceType insuranceType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Person person;
    private InsuranceStatus status;

}

