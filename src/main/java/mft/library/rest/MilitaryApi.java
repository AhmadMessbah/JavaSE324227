package mft.library.rest;

import mft.library.model.entity.MilitaryLicense;
import mft.library.model.entity.Person;
import mft.library.model.service.MilitaryLicenseService;
import mft.library.model.service.PersonService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/military-license")
public class MilitaryApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MilitaryLicense> getMilitaryLicenses() throws Exception {
        MilitaryLicenseService service = new MilitaryLicenseService();
        return service.findAll();
    }
}
