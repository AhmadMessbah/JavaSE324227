package mft.library.model.repository;

import mft.library.model.entity.JobHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobRepository implements Repository<JobHistory, Integer> {
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public JobRepository() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connection = connectionProvider.getConnection();
    }

    @Override
    public void save(JobHistory jobHistory) throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        jobHistory.setId(connectionProvider.nextId("job_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO JOBS (id, person, job,company,description,start_date,end_date) VALUES (?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, jobHistory.getId());
        preparedStatement.setString(2, jobHistory.getPerson());
        preparedStatement.setString(3, jobHistory.getJob());
        preparedStatement.setString(4, jobHistory.getCompany());
        preparedStatement.setString(5, jobHistory.getDescription());
        preparedStatement.setDate(6, Date.valueOf(jobHistory.getStartDate()));
        preparedStatement.setDate(7, Date.valueOf(jobHistory.getEndDate()));
        preparedStatement.execute();
    }

    @Override
    public void edit(JobHistory jobHistory) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update JOBS SET PERSON=?, JOB =?, COMPANY=?, DESCRIPTION=?, START_DATE=?, END_DATE=? where id=?"
        );
        preparedStatement.setString(1, jobHistory.getPerson());
        preparedStatement.setString(2, jobHistory.getJob());
        preparedStatement.setString(3, jobHistory.getCompany());
        preparedStatement.setString(4, jobHistory.getDescription());
        preparedStatement.setDate(5, Date.valueOf(jobHistory.getStartDate()));
        preparedStatement.setDate(6, Date.valueOf(jobHistory.getEndDate()));
        preparedStatement.execute();

    }

    @Override
    public void remove(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM JOBS WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<JobHistory> findAll() throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM JOBS ORDER BY PERSON, JOB"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        List<JobHistory> jobList = new ArrayList<>();
        while(resultSet.next()) {
            JobHistory jobHistory = JobHistory
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .person(resultSet.getString("NAME"))
                    .job(resultSet.getString("FAMILY"))
                    .startDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .endDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .company(resultSet.getString("USERNAME"))
                    .description(resultSet.getString("PASSWORD"))
                    .build();
            jobList.add(jobHistory);
        }
        return jobList;
    }

    @Override
    public JobHistory findById(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM JOBS WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        JobHistory jobHistory = null;
        if(resultSet.next()) {
            jobHistory = JobHistory
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .person(resultSet.getString("PERSON"))
                    .job(resultSet.getString("JOB"))
                    .startDate(resultSet.getDate("START_DATE").toLocalDate())
                    .endDate(resultSet.getDate("END_DATE").toLocalDate())
                    .company(resultSet.getString("COMPANY"))
                    .description(resultSet.getString("DESCRIPTION"))
                    .build();
        }
        return jobHistory;
    }

    public List<JobHistory> findByPersonAndJob(String person, String job) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM JOBS WHERE PERSON LIKE ? AND JOB LIKE ? ORDER BY JOB, PERSON"
        );
        preparedStatement.setString(1,  person + "%");
        preparedStatement.setString(2,  job + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<JobHistory> jobHistoryList = new ArrayList<>();
        while(resultSet.next()) {
            JobHistory jobHistory=JobHistory
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .person(resultSet.getString("PERSON"))
                    .job(resultSet.getString("JOB"))
                    .startDate(resultSet.getDate("START_DATE").toLocalDate())
                    .endDate(resultSet.getDate("END_DATE").toLocalDate())
                    .company(resultSet.getString("COMPANY"))
                    .description(resultSet.getString("DESCRIPTION"))
                    .build();
            jobHistoryList.add(jobHistory);
        }
        return jobHistoryList;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
