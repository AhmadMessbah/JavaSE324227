import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import mft.library.model.entity.Member;
import mft.library.model.repository.MemberRepository;
import mft.library.model.service.MemberService;
import org.apache.log4j.Logger;

import java.time.LocalDate;


public class JavaDataCreator {
//    static Logger logger = Logger.getLogger(JavaDataCreator.class);

    public void main(String[] args) throws Exception {
//        Member member = Member
//                .builder()
//                .name("ali")
//                .family("alipour")
//                .username("aaa1111")
//                .password("ali123")
//                .birthDate(LocalDate.of(2000,1,1))
//                .build();
//        MemberService.save(member);

//        logger.info("anjam shod");
//        logger.error("anjam nashod");

//        MemberService.remove(201);

//        System.out.println(MemberService.findByNameAndFamily("S", ""));

//        Faker faker = new Faker();
//
//
//        for (int i = 0; i < 200; i++) {
//            Member member = Member
//                    .builder()
//                    .name(faker.name().firstName())
//                    .family(faker.name().lastName())
//                    .birthDate(
//                            LocalDate.of(
//                                    faker.number().numberBetween(1970, 2020),
//                                    faker.number().numberBetween(1, 12),
//                                    faker.number().numberBetween(1, 20))
//                    )
//                    .username(faker.name().username())
//                    .password(faker.internet().password())
//                    .active(true)
//                    .build();
//            try{
//                MemberService.save(member);
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//        }
    }
}
