package mft.library.model.repository;

import lombok.extern.log4j.Log4j;
import mft.library.model.entity.JobHistory;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class JobRepository implements Repository<JobHistory, Integer> {
    private Connection connection;
    private PreparedStatement preparedStatement;

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

    }

    @Override
    public void remove(Integer id) throws Exception {

    }

    @Override
    public List<JobHistory> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public JobHistory findById(Integer id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
