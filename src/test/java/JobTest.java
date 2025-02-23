import mft.library.model.entity.JobHistory;
import mft.library.model.entity.Person;
import mft.library.model.service.JobService;
import mft.library.model.service.PersonService;

import java.time.LocalDate;

public class JobTest {
    public static void main(String[] args) throws Exception {
//        Person person = Person
//                .builder()
//                .id(1)
//                .name("reza")
//                .family("rezaii")
//                .birthDate(LocalDate.of(2000, 11, 10))
//                .username("reza")
//                .password("reza123")
//                .active(true)
//                .build();
//        PersonService.save(person);
//        JobHistory jobHistory = JobHistory
//                .builder()
//                .job("Mali")
//                .person(person)
//                .company("MFT")
//                .startDate(LocalDate.of(2020, 1, 1))
//                .endDate(LocalDate.of(2020, 12, 29))
//                .description("Tozihat")
//                .build();
//
//        JobService.save(jobHistory);
        System.out.println(JobService.findByJobAndFamily("E", "a"));

    }
}
