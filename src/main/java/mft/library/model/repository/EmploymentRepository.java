package mft.library.model.repository;

import mft.library.model.entity.Employment;
import mft.library.model.entity.JobHistory;
import mft.library.model.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmploymentRepository Repository<Employment, Integer> {
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public EmploymentRepository() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connection = connectionProvider.getConnection();
    }

    @Override
    public void save(Employment employment) throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        employment.setId(connectionProvider.nextId("employment_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO EMPLOYMENTS (e_id, person, department, job, salary, start_date, end_date, person_id) VALUES (?,?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, employment.getId());
        preparedStatement.setString(2, employment.getPerson());
        preparedStatement.setString(3, employment.getDepartment());
        preparedStatement.setString(4, employment.getJob());
        preparedStatement.setLong(5, employment.getSalary());
        preparedStatement.setDate(6, Date.valueOf(employment.getStartDate()));
        preparedStatement.setDate(7, Date.valueOf(employment.getEndDate()));
        preparedStatement.setInt(8, employment.getPerson().getId());
        preparedStatement.execute();
    }

    @Override
    public void edit(Employment employment) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update EMPLOYMENTS SET PERSON =?, DEPARTMENT=?, JOB=?, SALARY=?, START_DATE=?, END_DATE=?, PERSON_ID=? where e_id=?"
        );
        preparedStatement.setString(1, employment.getPerson());
        preparedStatement.setString(2, employment.getDepartment());
        preparedStatement.setString(3, employment.getJob());
        preparedStatement.setLong(4, employment.getSalary());
        preparedStatement.setDate(5, Date.valueOf(employment.getStartDate()));
        preparedStatement.setDate(6, Date.valueOf(employment.getEndDate()));
        preparedStatement.setInt(7, employment.getPerson().getId());
        preparedStatement.setInt(8, employment.getId());
        preparedStatement.execute();
    }

    @Override
    public void remove(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM EMPLOYMENTS WHERE E_ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<Employment> findAll() throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join employment on employments.person_id = persons.p_id"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Employment> employmentList = new ArrayList<>();
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

            Employment employment = Employment
                    .builder()
                    .id(resultSet.getInt("E_ID"))
                    .person(resultSet.getString("PERSON"))
                    .startDate(resultSet.getDate("START_DATE").toLocalDate())
                    .endDate(resultSet.getDate("END_DATE").toLocalDate())
                    .department(resultSet.getString("DEPARTMENT"))
                    .job(resultSet.getString("JOB"))
                    .salary(resultSet.getLong("SALARY"))
                    .person(person)
                    .build();
            employmentList.add(employment);
        }
        return employmentList;
    }

    @Override
    public Employment findById(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join employments on employments.person_id = persons.p_id where employments.e_id = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Employment employment = null;
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

            employment = Employment
                    .builder()
                    .id(resultSet.getInt("E_ID"))
                    .person(resultSet.getString("PERSON"))
                    .startDate(resultSet.getDate("START_DATE").toLocalDate())
                    .endDate(resultSet.getDate("END_DATE").toLocalDate())
                    .department(resultSet.getString("DEPARTMENT"))
                    .job(resultSet.getString("JOB"))
                    .salary(resultSet.getLong("SALARY"))
                    .person(person)
                    .build();
        }
        return employment;
    }

    public List<Employment> findByPersonAndDepartment(String person, String department) throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join jobs on jobs.person_id = persons.p_id where person like ? and department like ?"
        );
        preparedStatement.setString(1,  person + "%");
        preparedStatement.setString(2,  department + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Employment> employmentList = new ArrayList<>();
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

            Employment employment = Employment
                    .builder()
                    .id(resultSet.getInt("E_ID"))
                    .person(resultSet.getString("PERSON"))
                    .startDate(resultSet.getDate("START_DATE").toLocalDate())
                    .endDate(resultSet.getDate("END_DATE").toLocalDate())
                    .department(resultSet.getString("DEPARTMENT"))
                    .job(resultSet.getString("JOB"))
                    .salary(resultSet.getLong("SALARY"))
                    .person(person)
                    .build();
            employmentList.add(employment);
        }
        return employmentList;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
