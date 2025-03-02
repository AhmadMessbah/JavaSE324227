package mft.library.model.repository;

import lombok.extern.log4j.Log4j;
import mft.library.model.entity.MilitaryLicense;
import mft.library.model.entity.Person;
import mft.library.model.entity.enums.Province;
import mft.library.model.entity.enums.MilitaryType;
import mft.library.model.repository.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class MilitaryLicenseRepository implements Repository<MilitaryLicense, Integer> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public MilitaryLicenseRepository() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connection = connectionProvider.getConnection();
        log.info("Connected to database");
    }

    @Override
    public void save(MilitaryLicense militaryLicense) throws Exception {
//        ConnectionProvider connectionProvider = new ConnectionProvider();
//        militaryLicenseEntity.setId(connectionProvider.nextId("military_license_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO military_license (military_id, military_type, province, start_military_date, end_military_date,person_id)" +
                        " VALUES (?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, militaryLicense.getMilitaryId());
        preparedStatement.setString(2, String.valueOf(militaryLicense.getType()));
        preparedStatement.setString(3, String.valueOf(militaryLicense.getProvince()));
        preparedStatement.setDate(4, Date.valueOf(militaryLicense.getStartMilitaryDate()));
        preparedStatement.setDate(5, Date.valueOf(militaryLicense.getEndMilitaryDate()));
        preparedStatement.setInt(6,militaryLicense.getPerson().getId());
        preparedStatement.execute();
    }

    @Override
    public void edit(MilitaryLicense militaryLicense) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE military_license SET military_id=?, military_type=?, province=?, start_military_date=?, end_military_date=? WHERE id=? and person_id=?"
        );
        preparedStatement.setInt(1, militaryLicense.getMilitaryId());
        preparedStatement.setString(2, String.valueOf(militaryLicense.getType()));
        preparedStatement.setString(3, String.valueOf(militaryLicense.getProvince()));
        preparedStatement.setDate(4, Date.valueOf(militaryLicense.getStartMilitaryDate()));
        preparedStatement.setDate(5, Date.valueOf(militaryLicense.getEndMilitaryDate()));
        preparedStatement.setInt(6, militaryLicense.getId());
        preparedStatement.setInt(7, militaryLicense.getPerson().getId());
        preparedStatement.execute();
    }

    @Override
    public void remove(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE military_license SET is_deleted=1 WHERE id=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<MilitaryLicense> findAll() throws Exception {
        String sql = "SELECT military_license.id AS militaryLicenseId, military_id, military_type, province, " +
                "start_military_date, end_military_date," +
                " persons.p_id AS personId, name, family, birth_date" +
                " FROM military_license LEFT JOIN persons ON military_license.PERSON_ID = persons.P_ID";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<MilitaryLicense> militaryLicenseList = new ArrayList<>();
        while (resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("personId"))
                    .name(resultSet.getString("name"))
                    .family(resultSet.getString("family"))
                    .birthDate(resultSet.getDate("birth_date").toLocalDate())
                    .build();
            MilitaryLicense militaryLicense = MilitaryLicense
                    .builder()
                    .id(resultSet.getInt("militaryLicenseId"))
                    .militaryId(resultSet.getInt("military_id"))
                    .startMilitaryDate(resultSet.getDate("start_military_date").toLocalDate())
                    .endMilitaryDate(resultSet.getDate("end_military_date").toLocalDate())
                    .province(Province.valueOf(resultSet.getString("province")))
                    .type(MilitaryType.valueOf(resultSet.getString("military_type")))
                    .person(person)
                    .build();
            militaryLicenseList.add(militaryLicense);
        }
        return militaryLicenseList;
    }

    @Override
    public MilitaryLicense findById(Integer id) throws Exception {
        String sql =
                "SELECT military_license.id AS militaryLicenseId, military_id, military_type, province, " +
                        "start_military_date, end_military_date," +
                        " persons.p_id AS personId, name, family, birth_date" +
                        " FROM military_license" +
                        " LEFT JOIN persons ON military_license.person_id = persons.p_id" +
                        " WHERE military_license.id =? AND military_license.is_deleted = 0;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        MilitaryLicense militaryLicense = null;
        if (resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("personId"))
                    .name(resultSet.getString("name"))
                    .family(resultSet.getString("family"))
                    .birthDate(resultSet.getDate("birth_date").toLocalDate())
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .active(resultSet.getBoolean("is_active"))
                    .build();

            militaryLicense = MilitaryLicense
                    .builder()
                    .id(resultSet.getInt("militaryLicenseId"))
                    .militaryId(resultSet.getInt("military_id"))
                    .type(MilitaryType.valueOf(resultSet.getString("military_type")))
                    .province(Province.valueOf(resultSet.getString("province")))
                    .startMilitaryDate(resultSet.getDate("start_military_date").toLocalDate())
                    .endMilitaryDate(resultSet.getDate("end_military_date").toLocalDate())
                    .person(person)
                    .build();
        }
        return militaryLicense;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
        log.info("MilitaryLicenseRepository Connection closed");
    }
}
