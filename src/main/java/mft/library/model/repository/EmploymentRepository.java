package mft.library.model.repository;

import mft.library.model.entity.Employment;
import mft.library.model.entity.Person;
import mft.library.model.repository.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmploymentRepository implements Repository<Employment , Integer> {
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
                "INSERT INTO EMPLOYMENT (e_id, department, job, salary, start_date, end_date, person_id) VALUES (?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, employment.getId());
        preparedStatement.setString(2, employment.getDepartment());
        preparedStatement.setString(3, employment.getJob());
        preparedStatement.setLong(4, employment.getSalary());
        preparedStatement.setDate(5, Date.valueOf(employment.getStartDate()));
        preparedStatement.setDate(6, Date.valueOf(employment.getEndDate()));
        preparedStatement.setInt(7, employment.getPerson().getId());
        preparedStatement.execute();
    }

    @Override
    public void edit(Employment employment) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update EMPLOYMENT SET  DEPARTMENT=?, JOB=?, SALARY=?, START_DATE=?, END_DATE=?, PERSON_ID=? where e_id=?"
        );

        preparedStatement.setString(1, employment.getDepartment());
        preparedStatement.setString(2, employment.getJob());
        preparedStatement.setLong(3, employment.getSalary());
        preparedStatement.setDate(4, Date.valueOf(employment.getStartDate()));
        preparedStatement.setDate(5, Date.valueOf(employment.getEndDate()));
        preparedStatement.setInt(6, employment.getPerson().getId());
        preparedStatement.setInt(7, employment.getId());
        preparedStatement.execute();
    }

    @Override
    public void remove(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM EMPLOYMENT WHERE e_id=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<Employment> findAll() throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join employment on employment.person_id = persons.p_id"
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
                "select * from persons right join employment on employment.person_id = persons.p_id where employment.e_id = ?"
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

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
