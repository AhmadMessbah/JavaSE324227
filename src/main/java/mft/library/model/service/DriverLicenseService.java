package mft.library.model.service;

import mft.library.model.entity.DriverLicense;
import mft.library.model.repository.DriverLicenseRepository;

import java.util.List;

public class DriverLicenseService {

    public static void save(DriverLicense driverLicense) throws Exception {
        if (!(driverLicense.getDateTime().getYear() >= 2015 && driverLicense.getExpire().getYear() <= 2025)) {
            throw new Exception("Invalid date");
        }

        try (DriverLicenseRepository driverLicenseRepository = new DriverLicenseRepository()) {
            driverLicenseRepository.save(driverLicense);
        }
    }
    
    public static DriverLicense edit(DriverLicense driverLicense) throws Exception {
        findById(driverLicense.getId());
        if (!(driverLicense.getDateTime().getYear() >= 2015 && driverLicense.getExpire().getYear() <= 2025)) {
            throw new Exception("Invalid date");
        }
        try (DriverLicenseRepository driverLicenseRepository = new DriverLicenseRepository()) {
            driverLicenseRepository.edit(driverLicense);
        }
        return driverLicense;
    }

    public static void delete(int id) throws Exception {
        findById(id);
        try (DriverLicenseRepository driverLicenseRepository = new DriverLicenseRepository()) {
            driverLicenseRepository.remove(id);
        }
    }

    public static List<DriverLicense> findAll() throws Exception {
        try (DriverLicenseRepository driverLicenseRepository = new DriverLicenseRepository()) {
            List<DriverLicense> driverLicenses = driverLicenseRepository.findAll();
            if (driverLicenses.isEmpty()) {
                throw new Exception("No driver license found");
            }
            return driverLicenses;
        }
    }


    public static DriverLicense findById(int id) throws Exception {
        try (DriverLicenseRepository driverLicenseRepository = new DriverLicenseRepository()) {
            DriverLicense driverLicense = driverLicenseRepository.findById(id);
            if (driverLicense == null) {
                throw new Exception("driver license not found");
            }
            return driverLicense;
        }
    }

    public static List<DriverLicense> findByLicenseIDAndFamily(String licenseId, String family) throws Exception {
        try (DriverLicenseRepository driverLicenseRepository = new DriverLicenseRepository()) {
            List<DriverLicense> DriverLicenseList = driverLicenseRepository.findByLicenseIDAndFamily(licenseId, family);
            if (DriverLicenseList.isEmpty()) {
                throw new Exception("No driver license found");
            }
            return DriverLicenseList;
        }
    }

}
