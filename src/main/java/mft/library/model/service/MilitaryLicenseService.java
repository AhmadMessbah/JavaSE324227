package mft.library.model.service;

import mft.library.model.entity.MilitaryLicenseEntity;
import mft.library.model.repository.MilitaryLicenseRepository;

import java.util.List;

public class MilitaryLicenseService {
    public static void save(MilitaryLicenseEntity militaryLicenseEntity) throws Exception {
        if (!(militaryLicenseEntity.getStartMilitaryDate().getYear() >= 1980 && militaryLicenseEntity.getStartMilitaryDate().getYear() <= 2024)) {
            throw new Exception("Invalid start date");
        }
        if (!(militaryLicenseEntity.getEndMilitaryDate().getYear() >= 1980 && militaryLicenseEntity.getEndMilitaryDate().getYear() <= 2024)) {
            throw new Exception("Invalid end date");
        }
        if (militaryLicenseEntity.getStartMilitaryDate().getYear() > militaryLicenseEntity.getEndMilitaryDate().getYear()) {
            throw new Exception("Start date cannot be greater than end date ");
        }
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            militaryLicenseRepository.save(militaryLicenseEntity);
        }
    }

    public static void edit(MilitaryLicenseEntity militaryLicenseEntity) throws Exception {
        findById(militaryLicenseEntity.getId());
        if (!(militaryLicenseEntity.getStartMilitaryDate().getYear() >= 1980 && militaryLicenseEntity.getStartMilitaryDate().getYear() <= 2024)) {
            throw new Exception("Invalid start date");
        }
        if (!(militaryLicenseEntity.getEndMilitaryDate().getYear() >= 1980 && militaryLicenseEntity.getEndMilitaryDate().getYear() <= 2024)) {
            throw new Exception("Invalid end date");
        }
        if (militaryLicenseEntity.getStartMilitaryDate().getYear() > militaryLicenseEntity.getEndMilitaryDate().getYear()) {
            throw new Exception("Start date cannot be greater than end date ");
        }
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            militaryLicenseRepository.edit(militaryLicenseEntity);
        }
    }

    public static void remove(int id) throws Exception {
        findById(id);
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            militaryLicenseRepository.remove(id);
        }
    }

    public static List<MilitaryLicenseEntity> findAll() throws Exception {
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            List<MilitaryLicenseEntity> militaryLicenseList = militaryLicenseRepository.findAll();
            if (militaryLicenseList.isEmpty()) {
                throw new Exception("Military license not found");
            }
            return militaryLicenseList;
        }
    }

    public static MilitaryLicenseEntity findById(int id) throws Exception {
        try (MilitaryLicenseRepository militaryLicenseRepository = new MilitaryLicenseRepository()) {
            MilitaryLicenseEntity militaryLicenseEntity = militaryLicenseRepository.findById(id);
            if (militaryLicenseEntity == null) {
                throw new Exception("Member not found");
            }
            return militaryLicenseEntity;
        }
    }
}
