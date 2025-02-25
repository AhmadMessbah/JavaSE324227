import mft.library.model.repository.BimehRepository;
import mft.library.model.service.BimehService;

public class InsuranceTest {
    public static void main(String[] args) throws Exception {
        System.out.println(BimehService.findAll());

//        BimehRepository bimehRepository = new BimehRepository();
//        System.out.println(bimehRepository.findAll());
    }
}
