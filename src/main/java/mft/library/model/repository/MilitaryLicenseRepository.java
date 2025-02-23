package mft.library.model.repository;

import lombok.extern.log4j.Log4j;
import mft.library.model.entity.MilitaryLicense;
import mft.library.model.entity.enums.Province;
import mft.library.model.entity.enums.MilitaryType;

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
                "INSERT INTO military_license (military_id, first_name, last_name, military_type, province, start_military_date, end_military_date)" +
                        " VALUES (?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, militaryLicense.getMilitaryId());
        preparedStatement.setString(2, militaryLicense.getFirstName());
        preparedStatement.setString(3, militaryLicense.getLastName());
        preparedStatement.setString(4, String.valueOf(militaryLicense.getType()));
        preparedStatement.setString(5, String.valueOf(militaryLicense.getProvince()));
        preparedStatement.setDate(6, Date.valueOf(militaryLicense.getStartMilitaryDate()));
        preparedStatement.setDate(7, Date.valueOf(militaryLicense.getEndMilitaryDate()));
        preparedStatement.execute();
    }

    @Override
    public void edit(MilitaryLicense militaryLicense) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE military_license SET military_id=?, first_name=?, last_name=?, military_type=?, province=?, start_military_date=?, end_military_date=? WHERE id=?"
        );
        preparedStatement.setInt(1, militaryLicense.getMilitaryId());
        preparedStatement.setString(2, militaryLicense.getFirstName());
        preparedStatement.setString(3, militaryLicense.getLastName());
        preparedStatement.setString(4, String.valueOf(militaryLicense.getType()));
        preparedStatement.setString(5, String.valueOf(militaryLicense.getProvince()));
        preparedStatement.setDate(6, Date.valueOf(militaryLicense.getStartMilitaryDate()));
        preparedStatement.setDate(7, Date.valueOf(militaryLicense.getEndMilitaryDate()));
        preparedStatement.setInt(8, militaryLicense.getId());
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
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM military_license WHERE is_deleted = 0 ORDER BY id"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        List<MilitaryLicense> militaryLicenseList = new ArrayList<>();
        while (resultSet.next()) {
            MilitaryLicense militaryLicense = MilitaryLicense
                    .builder()
                    .id(resultSet.getInt("id"))
                    .militaryId(resultSet.getInt("military_id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .startMilitaryDate(resultSet.getDate("start_military_date").toLocalDate())
                    .endMilitaryDate(resultSet.getDate("end_military_date").toLocalDate())
                    .province(Province.valueOf(resultSet.getString("province")))
                    .type(MilitaryType.valueOf(resultSet.getString("military_type")))
                    .endMilitaryDate(resultSet.getDate("end_military_date").toLocalDate())
                    .build();
            militaryLicenseList.add(militaryLicense);
        }
        return militaryLicenseList;
    }

    @Override
    public MilitaryLicense findById(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM military_license WHERE id=? AND is_deleted=0"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        MilitaryLicense militaryLicense = null;
        if (resultSet.next()) {
            militaryLicense = MilitaryLicense
                    .builder()
                    .id(resultSet.getInt("id"))
                    .militaryId(resultSet.getInt("military_id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .type(MilitaryType.valueOf(resultSet.getString("military_type")))
                    .province(Province.valueOf(resultSet.getString("province")))
                    .startMilitaryDate(resultSet.getDate("start_military_date").toLocalDate())
                    .endMilitaryDate(resultSet.getDate("end_military_date").toLocalDate())
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
