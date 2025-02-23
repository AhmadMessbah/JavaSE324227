package mft.library.model.repository;

import mft.library.model.entity.JobHistory;
import mft.library.model.entity.Person;

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
                "INSERT INTO JOBS (j_id, job,company,description,start_date,end_date, person_id) VALUES (?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, jobHistory.getId());
        preparedStatement.setString(2, jobHistory.getJob());
        preparedStatement.setString(3, jobHistory.getCompany());
        preparedStatement.setString(4, jobHistory.getDescription());
        preparedStatement.setDate(5, Date.valueOf(jobHistory.getStartDate()));
        preparedStatement.setDate(6, Date.valueOf(jobHistory.getEndDate()));
        preparedStatement.setInt(7, jobHistory.getPerson().getId());
        preparedStatement.execute();
    }

    @Override
    public void edit(JobHistory jobHistory) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update JOBS SET JOB =?, COMPANY=?, DESCRIPTION=?, START_DATE=?, END_DATE=?, PERSON_ID=? where j_id=?"
        );
        preparedStatement.setString(1, jobHistory.getJob());
        preparedStatement.setString(2, jobHistory.getCompany());
        preparedStatement.setString(3, jobHistory.getDescription());
        preparedStatement.setDate(4, Date.valueOf(jobHistory.getStartDate()));
        preparedStatement.setDate(5, Date.valueOf(jobHistory.getEndDate()));
        preparedStatement.setInt(6, jobHistory.getPerson().getId());
        preparedStatement.setInt(7, jobHistory.getId());
        preparedStatement.execute();
    }

    @Override
    public void remove(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM JOBS WHERE J_ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<JobHistory> findAll() throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join jobs on jobs.person_id = persons.p_id"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        List<JobHistory> jobList = new ArrayList<>();
        while(resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("P_ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .active(resultSet.getBoolean("IS_ACTIVE"))
                    .build();

            JobHistory jobHistory = JobHistory
                    .builder()
                    .id(resultSet.getInt("J_ID"))
                    .job(resultSet.getString("JOB"))
                    .startDate(resultSet.getDate("START_DATE").toLocalDate())
                    .endDate(resultSet.getDate("END_DATE").toLocalDate())
                    .company(resultSet.getString("COMPANY"))
                    .description(resultSet.getString("DESCRIPTION"))
                    .person(person)
                    .build();
            jobList.add(jobHistory);
        }
        return jobList;
    }

    @Override
    public JobHistory findById(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join jobs on jobs.person_id = persons.p_id where jobs.j_id = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        JobHistory jobHistory = null;
        if(resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("P_ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .active(resultSet.getBoolean("IS_ACTIVE"))
                    .build();

            jobHistory = JobHistory
                    .builder()
                    .id(resultSet.getInt("J_ID"))
                    .job(resultSet.getString("JOB"))
                    .startDate(resultSet.getDate("START_DATE").toLocalDate())
                    .endDate(resultSet.getDate("END_DATE").toLocalDate())
                    .company(resultSet.getString("COMPANY"))
                    .description(resultSet.getString("DESCRIPTION"))
                    .person(person)
                    .build();
        }
        return jobHistory;
    }

    public List<JobHistory> findByJobAndFamily(String job, String family) throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join jobs on jobs.person_id = persons.p_id where job like ? and family like ?"
        );
        preparedStatement.setString(1,  job + "%");
        preparedStatement.setString(2,  family + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<JobHistory> jobHistoryList = new ArrayList<>();
        while(resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("P_ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .active(resultSet.getBoolean("IS_ACTIVE"))
                    .build();

            JobHistory jobHistory = JobHistory
                    .builder()
                    .id(resultSet.getInt("J_ID"))
                    .job(resultSet.getString("JOB"))
                    .startDate(resultSet.getDate("START_DATE").toLocalDate())
                    .endDate(resultSet.getDate("END_DATE").toLocalDate())
                    .company(resultSet.getString("COMPANY"))
                    .description(resultSet.getString("DESCRIPTION"))
                    .person(person)
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
