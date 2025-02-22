package mft.library.model.repository;

import lombok.extern.log4j.Log4j;
import mft.library.model.entity.MilitaryLicenseEntity;
import mft.library.model.entity.enums.Province;
import mft.library.model.entity.enums.MilitaryType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class MilitaryLicenseRepository implements Repository<MilitaryLicenseEntity, Integer> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public MilitaryLicenseRepository() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connection = connectionProvider.getConnection();
    }

    @Override
    public void save(MilitaryLicenseEntity militaryLicenseEntity) throws Exception {
//        ConnectionProvider connectionProvider = new ConnectionProvider();
//        militaryLicenseEntity.setId(connectionProvider.nextId("military_license_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO military_license (military_id, first_name, last_name, military_type, province, start_military_date, end_military_date)" +
                        " VALUES (?,?,?,?,?,?,?)"
        );
//        preparedStatement.setInt(1, militaryLicenseEntity.getId());
        preparedStatement.setInt(1, militaryLicenseEntity.getMilitaryId());
        preparedStatement.setString(2, militaryLicenseEntity.getFirstName());
        preparedStatement.setString(3, militaryLicenseEntity.getLastName());
        preparedStatement.setString(4, String.valueOf(militaryLicenseEntity.getType()));
        preparedStatement.setString(5, String.valueOf(militaryLicenseEntity.getProvince()));
        preparedStatement.setDate(6, Date.valueOf(militaryLicenseEntity.getStartMilitaryDate()));
        preparedStatement.setDate(7, Date.valueOf(militaryLicenseEntity.getEndMilitaryDate()));
        preparedStatement.execute();
    }

    @Override
    public void edit(MilitaryLicenseEntity militaryLicenseEntity) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE military_license SET military_id=?, first_name=?, last_name=?, military_type=?, province=?, start_military_date=?, end_military_date=? WHERE id=?"
        );
        preparedStatement.setInt(1, militaryLicenseEntity.getMilitaryId());
        preparedStatement.setString(2, militaryLicenseEntity.getFirstName());
        preparedStatement.setString(3, militaryLicenseEntity.getLastName());
        preparedStatement.setString(4, String.valueOf(militaryLicenseEntity.getType()));
        preparedStatement.setString(5, String.valueOf(militaryLicenseEntity.getProvince()));
        preparedStatement.setDate(6, Date.valueOf(militaryLicenseEntity.getStartMilitaryDate()));
        preparedStatement.setDate(7, Date.valueOf(militaryLicenseEntity.getEndMilitaryDate()));
        preparedStatement.setInt(8, militaryLicenseEntity.getId());
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
    public List<MilitaryLicenseEntity> findAll() throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM military_license ORDER BY last_name"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        List<MilitaryLicenseEntity> militaryLicenseEntityList = new ArrayList<>();
        while (resultSet.next()) {
            MilitaryLicenseEntity militaryLicenseEntity = MilitaryLicenseEntity
                    .builder()
                    .id(resultSet.getInt("id"))
                    .militaryId(resultSet.getInt("military_id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .startMilitaryDate(resultSet.getDate("start_military_date").toLocalDate())
                    .endMilitaryDate(resultSet.getDate("end_military_date").toLocalDate())
                    .build();
            militaryLicenseEntityList.add(militaryLicenseEntity);
        }
        return militaryLicenseEntityList;
    }

    @Override
    public MilitaryLicenseEntity findById(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM military_license WHERE id=?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        MilitaryLicenseEntity militaryLicenseEntity = null;
        if (resultSet.next()) {
            militaryLicenseEntity = MilitaryLicenseEntity
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
        return militaryLicenseEntity;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
        log.info("MemberRepository Connection closed");
    }
}
