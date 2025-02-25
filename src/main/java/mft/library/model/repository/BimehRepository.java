package mft.library.model.repository;

import mft.library.model.entity.Bimeh;
import mft.library.model.entity.Person;
import java.util.List;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.util.ArrayList;


public class BimehRepository implements Repository <Bimeh,Integer> {

    private static Connection connection;
    private static PreparedStatement preparedStatement;


    public BimehRepository() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connection = connectionProvider.getConnection();
    }

    @Override
    public void save(Bimeh bimeh) throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        bimeh.setId(connectionProvider.nextId("bimeh_seq"));
        String sql = "INSERT INTO BIMEH (b_id, policyNumber, insuranceType, startDate, endDate, status, person_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, bimeh.getId());
        preparedStatement.setString(2, bimeh.getPolicyNumber());
        preparedStatement.setString(3, String.valueOf(bimeh.getInsuranceType()));
        preparedStatement.setDate(4, Date.valueOf(bimeh.getStartDate()));
        preparedStatement.setDate(5, Date.valueOf(bimeh.getEndDate()));
        preparedStatement.setString(6, String.valueOf(bimeh.getStatus()));
        preparedStatement.setInt(7, bimeh.getPerson().getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void edit(Bimeh bimeh) throws Exception {
        String sql = "UPDATE BIMEH SET  insuranceType = ?, startDate = ?, endDate = ?, status = ?" +
                "WHERE b_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(bimeh.getInsuranceType()));
        preparedStatement.setDate(2, Date.valueOf(bimeh.getStartDate()));
        preparedStatement.setDate(3, Date.valueOf(bimeh.getEndDate()));
        preparedStatement.setString(4, String.valueOf(bimeh.getStatus()));
        preparedStatement.setInt(5, bimeh.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void remove(Integer id) throws Exception {
        String sql = "DELETE FROM BIMEH WHERE b_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1,id);
        prepareStatement.executeUpdate();
    }

    @Override
    public List<Bimeh> findAll() throws Exception {
        String sql ="select * from persons right join bimeh on bimeh.person_id = persons.p_id";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Bimeh> bimehList = new ArrayList<>();
        while(resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("p_id"))
                    .name(resultSet.getString("name"))
                    .family(resultSet.getString("family"))
                    .birthDate(resultSet.getDate("birth_date").toLocalDate())
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .active(resultSet.getBoolean("is_active"))
                    .build();

            Bimeh bimeh = Bimeh
                    .builder()
                    .id(resultSet.getInt("b_id"))
                    .policyNumber(resultSet.getString("policyNumber"))
                    .insuranceType(InsuranceType.valueOf(resultSet.getString("insuranceType")))
                    .startDate(resultSet.getDate("startDate").toLocalDate())
                    .endDate(resultSet.getDate("endDate").toLocalDate())
                    .status(InsuranceStatus.valueOf(resultSet.getString("status")))
                    .person(person)
                    .build();
            bimehList.add(bimeh);
        }
        return bimehList;
    }

    @Override
    public Bimeh findById(Integer id) throws Exception {
        String sql = "select * from persons right join bimeh on bimeh.person_id = persons.p_id where bimeh.b_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        ResultSet resultSet = prepareStatement.executeQuery();

        if (resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("p_id"))
                    .name(resultSet.getString("name"))
                    .family(resultSet.getString("family"))
                    .birthDate(resultSet.getDate("birth_date").toLocalDate())
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .active(resultSet.getBoolean("is_active"))
                    .build();
            Bimeh bimeh = Bimeh
                    .builder()
                    .id(resultSet.getInt("b_id"))
                    .policyNumber(resultSet.getString("policyNumber"))
                    .insuranceType(InsuranceType.valueOf(resultSet.getString("insuranceType")))
                    .startDate(resultSet.getDate("startDate").toLocalDate())
                    .endDate(resultSet.getDate("endDate").toLocalDate())
                    .person(person)
                    .status(InsuranceStatus.valueOf(resultSet.getString("status")))
                    .build();
            return bimeh;
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }

}





