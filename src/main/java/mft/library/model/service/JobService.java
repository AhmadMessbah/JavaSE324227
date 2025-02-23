package mft.library.model.service;

import mft.library.model.entity.JobHistory;
import mft.library.model.repository.JobRepository;

import java.util.List;

public class JobService {

    public static void save(JobHistory jobHistory) throws Exception {
        if (!(jobHistory.getStartDate().getYear() >= 2000 && jobHistory.getEndDate().getYear() <= 2024)) {
            throw new Exception("Invalid date");
        }
        try (JobRepository jobRepository = new JobRepository()) {
            jobRepository.save(jobHistory);
        }
    }


    public static JobHistory edit(JobHistory jobHistory) throws Exception {
        findById(jobHistory.getId());
        if (!(jobHistory.getStartDate().getYear() >= 1980 && jobHistory.getStartDate().getYear() <= 2020)) {
            throw new Exception("Invalid date");
        }
        try (JobRepository jobRepository = new JobRepository()) {
            jobRepository.edit(jobHistory);
        }
        return jobHistory;
    }

    public static void delete(int id) throws Exception {
        findById(id);
        try (JobRepository jobRepository = new JobRepository()) {
            jobRepository.remove(id);
        }
    }

    public static List<JobHistory> findAll() throws Exception {
        try (JobRepository jobRepository = new JobRepository()) {
            List<JobHistory> jobHistories = jobRepository.findAll();
            if (jobHistories.isEmpty()) {
                throw new Exception("No job history found");
            }
            return jobHistories;
        }
    }


    public static JobHistory findById(int id) throws Exception {
        try (JobRepository jobRepository = new JobRepository()) {
            JobHistory jobHistory = jobRepository.findById(id);
            if (jobHistory == null) {
                throw new Exception("JobHistory not found");
            }
            return jobHistory;
        }
    }

    public static List<JobHistory> findByPersonAndJob(String person, String job) throws Exception {
        try (JobRepository jobRepository = new JobRepository()) {
            List<JobHistory> jobHistoryList = jobRepository.findByPersonAndJob(person, job);
            if (jobHistoryList.isEmpty()) {
                throw new Exception("No job history found");
            }
            return jobHistoryList;
        }
    }

}
