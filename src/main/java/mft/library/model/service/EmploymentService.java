package mft.library.model.service;

import mft.library.model.entity.Employment;
import mft.library.model.entity.JobHistory;
import mft.library.model.repository.EmploymentRepository;
import mft.library.model.repository.JobRepository;

import java.util.List;

public class EmploymentService {
    public static void save(Employment employment) throws Exception {
        if (!(employment.getStartDate().getYear() >= 2000 && employment.getEndDate().getYear() <= 2024)) {
            throw new Exception("Invalid date");
        }
        try (EmploymentRepository employmentRepository = new EmploymentRepository()) {
            employmentRepository.save(employment);
        }
    }


    public static Employment edit(Employment employment) throws Exception {
        findById(employment.getId());
        if (!(employment.getStartDate().getYear() >= 1980 && employment.getStartDate().getYear() <= 2020)) {
            throw new Exception("Invalid date");
        }
        try (EmploymentRepository employmentRepository = new EmploymentRepository()) {
            employmentRepository.edit(employment);
        }
        return employment;
    }

    public static void delete(int id) throws Exception {
        findById(id);
        try (EmploymentRepository employmentRepository = new EmploymentRepository()) {
            employmentRepository.remove(id);
        }
    }

    public static List<Employment> findAll() throws Exception {
        try (EmploymentRepository employmentRepository = new EmploymentRepository()) {
            List<Employment> employments = employmentRepository.findAll();
            if (employments.isEmpty()) {
                throw new Exception("No employment found");
            }
            return employments;
        }
    }


    public static Employment findById(int id) throws Exception {
        try (EmploymentRepository employmentRepository = new EmploymentRepository()) {
            Employment employment = employmentRepository.findById(id);
            if (employment == null) {
                throw new Exception("Employment not found");
            }
            return employment;
        }
    }

}
