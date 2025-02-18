package mft.library.model.repository;

import lombok.extern.log4j.Log4j;
import mft.library.model.entity.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j
public class MemberRepository implements Repository <Member, Integer>{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public MemberRepository() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connection = connectionProvider.getConnection();
    }

    @Override
    public void save(Member member) throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        member.setId(connectionProvider.nextId("member_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO MEMBERS (ID, NAME, FAMILY, BIRTH_DATE, USERNAME, PASSWORD, IS_ACTIVE) VALUES (?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, member.getId());
        preparedStatement.setString(2, member.getName());
        preparedStatement.setString(3, member.getFamily());
        preparedStatement.setDate(4, Date.valueOf(member.getBirthDate()));
        preparedStatement.setString(5, member.getUsername());
        preparedStatement.setString(6, member.getPassword());
        preparedStatement.setBoolean(7, member.isActive());
        preparedStatement.execute();
    }

    @Override
    public void edit(Member member) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE MEMBERS SET NAME=?, FAMILY=?, BIRTH_DATE=?, PASSWORD=?, IS_ACTIVE=? WHERE ID=?"
        );
        preparedStatement.setString(1, member.getName());
        preparedStatement.setString(2, member.getFamily());
        preparedStatement.setDate(3, Date.valueOf(member.getBirthDate()));
        preparedStatement.setString(4, member.getPassword());
        preparedStatement.setBoolean(5, member.isActive());
        preparedStatement.setInt(6, member.getId());
        preparedStatement.execute();
    }

    @Override
    public void remove(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM MEMBERS WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<Member> findAll() throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM MEMBERS ORDER BY FAMILY, NAME"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Member> memberList = new ArrayList<>();
        while(resultSet.next()) {
            Member member = Member
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .active(resultSet.getBoolean("IS_ACTIVE"))
                    .build();
            memberList.add(member);
        }
        return memberList;
    }

    @Override
    public Member findById(Integer id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM MEMBERS WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Member member = null;
        if(resultSet.next()) {
             member = Member
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .active(resultSet.getBoolean("IS_ACTIVE"))
                    .build();
        }
        return member;
    }

    public List<Member> findByNameAndFamily(String name, String family) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM MEMBERS WHERE NAME LIKE ? AND FAMILY LIKE ? ORDER BY FAMILY, NAME"
        );
        preparedStatement.setString(1,  name + "%");
        preparedStatement.setString(2,  family + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Member> memberList = new ArrayList<>();
        while(resultSet.next()) {
            Member member = Member
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .active(resultSet.getBoolean("IS_ACTIVE"))
                    .build();
            memberList.add(member);
        }
        return memberList;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
        log.info("MemberRepository Connection closed");
    }
}
