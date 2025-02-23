package mft.library.model.repository;

import lombok.extern.log4j.Log4j;
import mft.library.model.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class PersonRepository implements Repository <Person, Integer>{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public PersonRepository() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connection = connectionProvider.getConnection();
    }

    @Override
    public void save(Person person) throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        person.setId(connectionProvider.nextId("member_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO PERSONS (P_ID, NAME, FAMILY, BIRTH_DATE, USERNAME, PASSWORD, IS_ACTIVE) VALUES (?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, person.getId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getFamily());
        preparedStatement.setDate(4, Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(5, person.getUsername());
        preparedStatement.setString(6, person.getPassword());
        preparedStatement.setBoolean(7, person.isActive());
        preparedStatement.execute();
    }

    @Override
    public void edit(Person person) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE PERSONS SET NAME=?, FAMILY=?, BIRTH_DATE=?, PASSWORD=?, IS_ACTIVE=? WHERE P_ID=?"
        );
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getFamily());
        preparedStatement.setDate(3, Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(4, person.getPassword());
        preparedStatement.setBoolean(5, person.isActive());
        preparedStatement.setInt(6, person.getId());
        preparedStatement.execute();
    }

    @Override
    public void remove(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM PERSONS WHERE P_ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<Person> findAll() throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM PERSONS ORDER BY FAMILY, NAME"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Person> personList = new ArrayList<>();
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
            personList.add(person);
        }
        return personList;
    }

    @Override
    public Person findById(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM PERSONS WHERE P_ID=?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Person person = null;
        if(resultSet.next()) {
             person = Person
                    .builder()
                    .id(resultSet.getInt("P_ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .active(resultSet.getBoolean("IS_ACTIVE"))
                    .build();
        }
        return person;
    }

    public List<Person> findByNameAndFamily(String name, String family) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM PERSONS WHERE NAME LIKE ? AND FAMILY LIKE ? ORDER BY FAMILY, NAME"
        );
        preparedStatement.setString(1,  name + "%");
        preparedStatement.setString(2,  family + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Person> personList = new ArrayList<>();
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
            personList.add(person);
        }
        return personList;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
