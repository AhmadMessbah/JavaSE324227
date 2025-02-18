package mft.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import mft.library.model.entity.Member;
import mft.library.model.service.MemberService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class MemberController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, familyTxt, userTxt, nameSearchTxt, familySearchTxt;

    @FXML
    private PasswordField passTxt;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private DatePicker birthDate;

    @FXML
    private RadioButton activeRdo, disableRdo;

    @FXML
    private ToggleGroup activeToggleGroup;

    @FXML
    private TableView<Member> memberTable;

    @FXML
    private TableColumn<Member, Integer> idCol;

    @FXML
    private TableColumn<Member, String> nameCol, familyCol, userCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("MemberView initialized");
        resetForm();

        saveBtn.setOnAction(event -> {
            try {
                Member member = Member
                        .builder()
                        .name(nameTxt.getText())
                        .family(familyTxt.getText())
                        .birthDate(birthDate.getValue())
                        .username(userTxt.getText())
                        .password(passTxt.getText())
                        .active(activeRdo.isSelected())
                        .build();
                MemberService.save(member);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Saved : " + member, ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Member Saved : " + member);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                log.error(e);
            }
        });
        editBtn.setOnAction(event -> {
            try {
                Member member = Member
                        .builder()
                        .id(Integer.parseInt(idTxt.getText()))
                        .name(nameTxt.getText())
                        .family(familyTxt.getText())
                        .birthDate(birthDate.getValue())
                        .username(userTxt.getText())
                        .password(passTxt.getText())
                        .active(activeRdo.isSelected())
                        .build();
                MemberService.edit(member);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Edited", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Member Edited : " + member);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                log.error(e);
            }
        });
        removeBtn.setOnAction(event -> {
            try {
                MemberService.remove(Integer.parseInt(idTxt.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Removed", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Member Removed");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                log.error(e);
            }
        });

        nameSearchTxt.setOnKeyReleased(event -> {
            try{
                refreshTable(MemberService.findByNameAndFamily(nameSearchTxt.getText() , familySearchTxt.getText()));
            }catch (Exception e){
                log.error(e);
            }
        });
        familySearchTxt.setOnKeyReleased(event -> {
            try{
                refreshTable(MemberService.findByNameAndFamily(nameSearchTxt.getText() , familySearchTxt.getText()));
            }catch (Exception e){
                log.error(e);
            }
        });

        memberTable.setOnMouseReleased(event -> {
            Member member = memberTable.getSelectionModel().getSelectedItem();
            idTxt.setText(String.valueOf(member.getId()));
            nameTxt.setText(member.getName());
            familyTxt.setText(member.getFamily());
            birthDate.setValue(member.getBirthDate());
            userTxt.setText(member.getUsername());
            passTxt.setText(member.getPassword());
            if (member.isActive()) {
                activeRdo.setSelected(true);
                disableRdo.setSelected(false);
            } else {
                activeRdo.setSelected(false);
                disableRdo.setSelected(true);
            }
        });
    }

    private void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        birthDate.setValue(null);
        userTxt.clear();
        passTxt.clear();
        activeRdo.setSelected(true);
        nameSearchTxt.clear();
        familySearchTxt.clear();
        try {
            refreshTable(MemberService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private void refreshTable(List<Member> memberList) {
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList(memberList);
        memberTable.getItems().clear();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        familyCol.setCellValueFactory(new PropertyValueFactory<>("family"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        memberTable.setItems(memberObservableList);
    }
}
