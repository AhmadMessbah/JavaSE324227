package mft.library.model.repository;

import lombok.extern.log4j.Log4j;
import mft.library.model.entity.DriverLicense;
import mft.library.model.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class DriverLicenseRepository implements Repository <DriverLicense, Integer>{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public DriverLicenseRepository() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connection = connectionProvider.getConnection();
        log.info("Connected to database");
    }

    @Override
    public void save(DriverLicense driverLicense) throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        driverLicense.setId(connectionProvider.nextId("driverLicense_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO DriverLicenses (dl_id, licenseId, dateTime, expire ) VALUES (?,?,?,?)"
        );
        preparedStatement.setInt(1, driverLicense.getId());
        preparedStatement.setInt(2, driverLicense.getLicenseId());
        preparedStatement.setDate(3, Date.valueOf(driverLicense.getDateTime()));
        preparedStatement.setDate(4, Date.valueOf(driverLicense.getExpire()));
        preparedStatement.execute();
        log.info("DriverLicenserepository save executed");


    }

    @Override
    public void edit(DriverLicense driverLicense) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update DriverLicenses SET licenseId =?, dateTime=?, expire=? where dl_id=?"
        );

        preparedStatement.setInt(1, driverLicense.getLicenseId());
        preparedStatement.setDate(2, Date.valueOf(driverLicense.getDateTime()));
        preparedStatement.setDate(3, Date.valueOf(driverLicense.getExpire()));
        preparedStatement.execute();
        log.info("DriverLicenserepository edit executed");

    }

    @Override
    public void remove(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM DriverLicense WHERE dl_id=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        log.info("DriverLicenserepository remove executed");

    }

    @Override
    public List<DriverLicense> findAll() throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join DriverLicenses on DriverLicenses.person_id = persons.p_id"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        List<DriverLicense> driverLicensesList = new ArrayList<>();
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

            DriverLicense driverLicense = DriverLicense
                    .builder()
                    .id(resultSet.getInt("dl_id"))
                    .licenseId(resultSet.getInt("licenseId"))
                    .dateTime(resultSet.getDate("dateTime").toLocalDate())
                    .expire(resultSet.getDate("expire").toLocalDate())
                    .person(person)
                    .build();
            driverLicensesList.add(driverLicense);
        }
        return driverLicensesList;

    }

    @Override
    public DriverLicense findById(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join DriverLicenses on DriverLicenses.person_id = persons.p_id where " +
                        "DriverLicenses.dl_id = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        DriverLicense driverLicense = null;
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

            driverLicense = DriverLicense
                    .builder()
                    .id(resultSet.getInt("dl_id"))
                    .licenseId(resultSet.getInt("licenseId"))
                    .dateTime(resultSet.getDate("dateTime").toLocalDate())
                    .expire(resultSet.getDate("expire").toLocalDate())
                    .person(person)
                    .build();
        }
        return driverLicense;
    }

    public List<DriverLicense> findByLicenseIDAndFamily(String licenseId, String family) throws Exception {
        preparedStatement = connection.prepareStatement(
                "select * from persons right join DriverLicenses on DriverLicenses.person_id = persons.p_id where licenseId like ? and family like ?"
        );
        preparedStatement.setString(1,  licenseId + "%");
        preparedStatement.setString(2,  family + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<DriverLicense> driverLicensesList = new ArrayList<>();
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
            DriverLicense driverLicense = DriverLicense
                    .builder()
                    .id(resultSet.getInt("dl_id"))
                    .licenseId(resultSet.getInt("licenseId"))
                    .dateTime(resultSet.getDate("dateTime").toLocalDate())
                    .expire(resultSet.getDate("expire").toLocalDate())
                    .person(person)
                    .build();
            driverLicensesList.add(driverLicense);
        }
        return driverLicensesList;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
        log.info("DriverLicenseRepository Connection closed");
    }
}
