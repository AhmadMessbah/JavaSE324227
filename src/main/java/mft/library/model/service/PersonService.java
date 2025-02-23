package mft.library.model.service;

import mft.library.model.entity.Person;
import mft.library.model.repository.PersonRepository;

import java.util.List;

public class PersonService {
    public static void save(Person person) throws Exception {
        if (!(person.getBirthDate().getYear() >= 1980 && person.getBirthDate().getYear() <= 2020)) {
            throw new Exception("Invalid Birth Year");
        }
        try (PersonRepository personRepository = new PersonRepository()) {
            personRepository.save(person);
        }
    }

    public static void edit(Person person) throws Exception {
        findById(person.getId());
        if (!(person.getBirthDate().getYear() >= 1980 && person.getBirthDate().getYear() <= 2020)) {
            throw new Exception("Invalid Birth Year");
        }
        try (PersonRepository personRepository = new PersonRepository()) {
            personRepository.edit(person);
        }
    }

    public static void remove(int id) throws Exception {
        findById(id);
        try (PersonRepository personRepository = new PersonRepository()) {
            personRepository.remove(id);
        }
    }

    public static List<Person> findAll() throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            List<Person> personList = personRepository.findAll();
            if (personList.isEmpty()) {
                throw new Exception("Member not found");
            }
            return personList;
        }
    }

    public static Person findById(int id) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            Person person = personRepository.findById(id);
            if (person == null) {
                throw new Exception("Member not found");
            }
            return person;
        }
    }

    public static List<Person> findByNameAndFamily(String name, String family) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()) {
            List<Person> personList = personRepository.findByNameAndFamily(name, family);
            if (personList.isEmpty()) {
                throw new Exception("Member not found");
            }
            return personList;
        }
    }
}
