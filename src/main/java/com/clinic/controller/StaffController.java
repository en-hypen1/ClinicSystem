package com.clinic.controller;

import com.clinic.dao.RoleDAO;
import com.clinic.dao.StaffDAO;
import com.clinic.model.StaffTableModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
public class StaffController {

    // =====================================================
    // BUTTONS
    // =====================================================

    @FXML
    private Button activateBtn;

    @FXML
    private Button addStaffBtn;

    @FXML
    private Button deactivateBtb;

    @FXML
    private Button editBtn;


    // =====================================================
    // ROOT
    // =====================================================

    @FXML
    private AnchorPane adminDashboardPane;


    // =====================================================
    // TABLE COLUMNS
    // =====================================================

    @FXML
    private TableColumn<StaffTableModel, String> emailColumn;

    @FXML
    private TableColumn<StaffTableModel, String> nameColumn;

    @FXML
    private TableColumn<StaffTableModel, String> phoneColumn;

    @FXML
    private TableColumn<StaffTableModel, String> photoColumn;

    @FXML
    private TableColumn<StaffTableModel, String> roleColumn;

    @FXML
    private TableColumn<StaffTableModel, String> statusColumn;


    // =====================================================
    // FILTERS
    // =====================================================

    @FXML
    private ComboBox<String> rolesCmb;

    @FXML
    private ComboBox<String> statusCmb;

    @FXML
    private TextField searchField;


    // =====================================================
    // TABLE
    // =====================================================

    @FXML
    private TableView<StaffTableModel> staffTable;


    // =====================================================
    // DATA
    // =====================================================

    private final ObservableList<StaffTableModel> staffList =
            FXCollections.observableArrayList();


    private final StaffDAO staffDAO =
            new StaffDAO();


    // =====================================================
    // INITIALIZE
    // =====================================================

    @FXML
    public void initialize() {

        setupTable();

        setupFilters();

        loadStaff();

        setupSearch();
    }


    // =====================================================
    // SETUP TABLE
    // =====================================================

    private void setupTable() {

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );

        roleColumn.setCellValueFactory(
                new PropertyValueFactory<>("role")
        );

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        photoColumn.setCellValueFactory(
                new PropertyValueFactory<>("photo")
        );


        // =====================================================
        // DISPLAY PROFILE PHOTO INSTEAD OF FILE PATH
        // =====================================================

        photoColumn.setCellFactory(column ->
                new TableCell<StaffTableModel, String>() {

                    private final ImageView imageView =
                            new ImageView();

                    {
                        imageView.setFitWidth(45);
                        imageView.setFitHeight(45);

                        imageView.setPreserveRatio(false);

                        imageView.setSmooth(true);

                        imageView.setCache(true);
                    }

                    @Override
                    protected void updateItem(
                            String photoPath,
                            boolean empty
                    ) {

                        super.updateItem(
                                photoPath,
                                empty
                        );

                        if (empty || photoPath == null
                                || photoPath.isBlank()) {

                            imageView.setImage(null);

                            setGraphic(null);

                            return;
                        }


                        // =====================================
                        // GET PHOTO FROM PROJECT FOLDER
                        // =====================================

                        java.io.File photoFile =
                                new java.io.File(photoPath);


                        if (!photoFile.isAbsolute()) {

                            photoFile =
                                    new java.io.File(
                                            System.getProperty(
                                                    "user.dir"
                                            ),
                                            photoPath
                                    );
                        }


                        // =====================================
                        // LOAD IMAGE
                        // =====================================

                        if (photoFile.exists()) {

                            Image image =
                                    new Image(
                                            photoFile
                                                    .toURI()
                                                    .toString(),
                                            45,
                                            45,
                                            false,
                                            true
                                    );

                            imageView.setImage(
                                    image
                            );

                            setGraphic(
                                    imageView
                            );

                        } else {

                            imageView.setImage(
                                    null
                            );

                            setGraphic(
                                    null
                            );
                        }
                    }
                }
        );


        staffTable.setItems(
                staffList
        );
    }



    // =====================================================
    // LOAD STAFF FROM DATABASE
    // =====================================================

    private void loadStaff() {

        try {

            List<StaffTableModel> staff =
                    staffDAO.getAllStaff();

            staffList.setAll(staff);

            populateRoleFilter();

            applyFilters();

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println(
                    "ERROR LOADING STAFF: "
                            + e.getMessage()
            );
        }
    }


    // =====================================================
    // SETUP FILTERS
    // =====================================================

    private void setupFilters() {

        rolesCmb.getItems().clear();

        rolesCmb.getItems().add("All Roles");

        rolesCmb.getItems().add("Doctor");

        rolesCmb.getItems().add("Receptionist");

        rolesCmb.setValue("All Roles");


        statusCmb.getItems().clear();

        statusCmb.getItems().add("All Status");

        statusCmb.getItems().add("Active");

        statusCmb.getItems().add("Inactive");

        statusCmb.setValue("All Status");


        rolesCmb.valueProperty().addListener(
                (observable, oldValue, newValue) -> {

                    applyFilters();

                }
        );


        statusCmb.valueProperty().addListener(
                (observable, oldValue, newValue) -> {

                    applyFilters();

                }
        );
    }


    // =====================================================
    // ROLE FILTER
    // =====================================================

    private void populateRoleFilter() {

        /*
         * The ComboBox already contains the available
         * staff roles.
         *
         * This method exists so we have one place to
         * update the role filter later if more roles
         * are added to the system.
         */
    }


    // =====================================================
    // SEARCH
    // =====================================================

    private void setupSearch() {

        searchField.textProperty().addListener(
                (observable, oldValue, newValue) -> {

                    applyFilters();

                }
        );
    }


    // =====================================================
    // APPLY FILTERS
    // =====================================================

    private void applyFilters() {

        String searchText =
                searchField.getText() == null
                        ? ""
                        : searchField.getText()
                        .trim()
                        .toLowerCase();


        String selectedRole =
                rolesCmb.getValue();


        String selectedStatus =
                statusCmb.getValue();


        List<StaffTableModel> filteredStaff =
                staffList.stream()

                        // =================================
                        // SEARCH
                        // =================================

                        .filter(staff -> {

                            if (searchText.isEmpty()) {
                                return true;
                            }

                            return staff.getName()
                                    .toLowerCase()
                                    .contains(searchText)

                                    || staff.getEmail()
                                    .toLowerCase()
                                    .contains(searchText)

                                    || (
                                    staff.getPhone() != null
                                            && staff.getPhone()
                                            .toLowerCase()
                                            .contains(searchText)
                            );
                        })


                        // =================================
                        // ROLE
                        // =================================

                        .filter(staff -> {

                            if (selectedRole == null
                                    || selectedRole.equals("All Roles")) {

                                return true;
                            }

                            return staff.getRole()
                                    .equalsIgnoreCase(
                                            selectedRole
                                    );
                        })


                        // =================================
                        // STATUS
                        // =================================

                        .filter(staff -> {

                            if (selectedStatus == null
                                    || selectedStatus.equals("All Status")) {

                                return true;
                            }

                            return staff.getStatus()
                                    .equalsIgnoreCase(
                                            selectedStatus
                                    );
                        })


                        .collect(Collectors.toList());


        staffTable.setItems(
                FXCollections.observableArrayList(
                        filteredStaff
                )
        );
        staffTable.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.PRIMARY
                    && event.getClickCount() == 2) {

                StaffTableModel selectedStaff =
                        staffTable.getSelectionModel()
                                .getSelectedItem();

                if (selectedStaff != null) {

                    openUserInfo(selectedStaff);

                }
            }
        });
    }

// =====================================================
// OPEN USER INFO
// =====================================================

    private void openUserInfo(StaffTableModel staff) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/UserInfo.fxml"
                            )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            UserInfoController controller =
                    loader.getController();

            controller.setStaff(staff);

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "User Information"
            );

            stage.setScene(
                    scene
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

            System.out.println(
                    "ERROR OPENING USER INFO: "
                            + e.getMessage()
            );
        }
    }
    // =====================================================
    // OPEN ADD STAFF
    // =====================================================

    @FXML
    private void openAddStaff() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/view/AddStaffController.fxml"
                            )
                    );


            Scene scene =
                    new Scene(
                            loader.load()
                    );


            Stage stage =
                    new Stage();


            stage.setTitle(
                    "Add Staff"
            );


            stage.setScene(
                    scene
            );


            stage.initModality(
                    Modality.APPLICATION_MODAL
            );


            stage.showAndWait();


            // =============================================
            // REFRESH TABLE AFTER ADD STAFF WINDOW CLOSES
            // =============================================

            loadStaff();


        } catch (IOException e) {

            e.printStackTrace();

            System.out.println(
                    "ERROR OPENING ADD STAFF: "
                            + e.getMessage()
            );
        }
    }
}