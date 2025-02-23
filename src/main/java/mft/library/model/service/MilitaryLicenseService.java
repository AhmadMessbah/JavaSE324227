package mft.library.model.service;

import mft.library.model.entity.MilitaryLicense;
import mft.library.model.repository.MilitaryLicenseRepository;

import java.util.List;

public class MilitaryLicenseService {
    public static void save(MilitaryLicense militaryLicense) throws Exception {
        if (militaryLicense.getFirstName().isEmpty()){
            throw new Exception("First name is empty");
        }

        if (!(militaryLicense.getStartMilitaryDate().getYear() >= 1980 && militaryLicense.getStartMilitaryDate().getYear() <= 2024)) {
            throw new Exception("Invalid start date");
        }
        if (!(militaryLicense.getEndMilitaryDate().getYear() >= 1980 && militaryLicense.getEndMilitaryDate().getYear() <= 2024)) {
            throw new Exception("Invalid end date");
        }
        if (militaryLicense.getStartMilitaryDate().getYear() > militaryLicense.getEndMilitaryDate().getYear()) {
            throw new Exception("Start date cannot be greater than end date ");
        }
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            militaryLicenseRepository.save(militaryLicense);
        }
    }

    public static void edit(MilitaryLicense militaryLicense) throws Exception {
        findById(militaryLicense.getId());
        if (!(militaryLicense.getStartMilitaryDate().getYear() >= 1980 && militaryLicense.getStartMilitaryDate().getYear() <= 2024)) {
            throw new Exception("Invalid start date");
        }
        if (!(militaryLicense.getEndMilitaryDate().getYear() >= 1980 && militaryLicense.getEndMilitaryDate().getYear() <= 2024)) {
            throw new Exception("Invalid end date");
        }
        if (militaryLicense.getStartMilitaryDate().getYear() > militaryLicense.getEndMilitaryDate().getYear()) {
            throw new Exception("Start date cannot be greater than end date ");
        }
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            militaryLicenseRepository.edit(militaryLicense);
        }
    }

    public static void remove(int id) throws Exception {
        findById(id);
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            militaryLicenseRepository.remove(id);
        }
    }

    public static List<MilitaryLicense> findAll() throws Exception {
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            List<MilitaryLicense> militaryLicenseList = militaryLicenseRepository.findAll();
            if (militaryLicenseList.isEmpty()) {
                throw new Exception("Nothing found");
            }
            return militaryLicenseList;
        }
    }

    public static MilitaryLicense findById(int id) throws Exception {
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            MilitaryLicense militaryLicense = militaryLicenseRepository.findById(id);
            if (militaryLicense == null) {
                throw new Exception("No license found");
            }
            return militaryLicense;
        }
    }
}
