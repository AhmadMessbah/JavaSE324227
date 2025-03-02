package mft.library.rest;

import mft.library.model.entity.Person;
import mft.library.model.service.PersonService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/persons")
public class PersonApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons() throws Exception {
        PersonService service = new PersonService();
        return service.findAll();
    }
}
