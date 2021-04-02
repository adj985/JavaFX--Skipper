/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skipper;

import entities.Associates;
import entities.Sales;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Coa
 */
public class SkipperViewController implements Initializable {

    /**
     * Sales tab
     */
    @FXML
    private Tab salesTab;
    @FXML
    private ComboBox<Associates> associateComboBox;
    @FXML
    private TextField amountField;
    @FXML
    private DatePicker datePick;
    @FXML
    private Button okButton;
    @FXML
    private CheckBox associateCheckBox;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<Sales> salesTable;
    @FXML
    private TableColumn<Sales, LocalDate> dateOfSaleCol;
    @FXML
    private TableColumn<Sales, String> associateCol;
    @FXML
    private TableColumn<Sales, Integer> totalAmountCol;
    @FXML
    private TableColumn<Sales, String> cityCol;
    @FXML
    private TableColumn<Sales, String> status;
    @FXML
    private Button updateButton;

    private final Alert a = new Alert(Alert.AlertType.ERROR);

    @FXML
    private void saveTheSale(ActionEvent event) {
        Associates as;
        String associate;
        String stat;
        String city;
        Double totalAmount;
        String dateOfSale;

        try {
            totalAmount = Double.valueOf(amountField.getText());
            dateOfSale = datePick.getValue().toString();
            if (associateCheckBox.isSelected()) {
                as = associateComboBox.getSelectionModel().getSelectedItem();
                associate = as.getFirstName() + " " + as.getLastName();
                stat = as.getStatus();
                city = as.getResidence();
            } else {
                associate = "Licna prodaja";
                stat = "Lider";
                city = "Kragujevac";
            }
            Sales s = new Sales(null, associate, stat, city, totalAmount, datePick.getValue());

            // Checking for duplicate entries
            for (int i = 0; i < salesTable.getItems().size(); i++) {
                if (salesTable.getItems().get(i).getAssociate().equals(s.getAssociate()) && salesTable.getItems().get(i).getTotalAmount().equals(s.getTotalAmount()) && salesTable.getItems().get(i).getDateOfSale().equals(s.getDateOfSale())) {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText(null);
                    a.setContentText("Vec postoji prodaja koju pokusavate da unesete!");
                    a.show();
                    return;
                }
            }
            Sales.saveSales(associate, stat, city, totalAmount, dateOfSale);
            associateCheckBox.setSelected(false);
            amountField.clear();
            datePick.setValue(null);
            salesTable.getItems().clear();
            salesTable.setItems(Sales.getSales());

        } catch (NumberFormatException | NullPointerException ex) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setHeaderText(null);
            a.setContentText("Niste uneli podatke o iznosu i/ili datumu, eventualno nije odabran saradnik ukoliko je cekirano polje za saradnika.\nUnesite trazene podatke.");
            a.show();
        } catch (Exception e) {
            a.setContentText("Problem pri cuvanju prodaje!\n" + e.getMessage());
            a.setHeaderText(null);
            a.show();
        }

    }

    @FXML
    private void loadAssociates(ActionEvent event) {
        if (associateCheckBox.isSelected()) {
            associateComboBox.setDisable(false);
            associateComboBox.setItems(Associates.getAssociates());
        } else {
            associateComboBox.setItems(null);
            associateComboBox.setDisable(true);
        }
    }

    @FXML
    private void clearSalesFields(ActionEvent event) {
        datePick.setValue(null);
        associateCheckBox.setSelected(false);
        associateComboBox.setDisable(true);
        amountField.clear();
        okButton.setDisable(false);
        updateButton.setDisable(true);
    }

    @FXML
    private void fillTheFields(MouseEvent event) {
        if (!salesTable.getSelectionModel().isEmpty()) {
            okButton.setDisable(true);
            updateButton.setDisable(false);
            amountField.setText(salesTable.getSelectionModel().getSelectedItem().getTotalAmount().toString());
            datePick.setValue(salesTable.getSelectionModel().getSelectedItem().getDateOfSale());
        }
    }

    @FXML
    private void updateSales(ActionEvent event) {
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Da li ste sigurni da zelite da izmenite podatke o prodaji?");
        a.setHeaderText(null);
        a.showAndWait();

        if (a.getResult() == ButtonType.OK) {
            String associate;
            String city;
            String stat;

            if (associateCheckBox.isSelected()) {
                associate = associateComboBox.getSelectionModel().getSelectedItem().getFirstName() + " " + associateComboBox.getSelectionModel().getSelectedItem().getLastName();
                city = associateComboBox.getSelectionModel().getSelectedItem().getResidence();
                stat = associateComboBox.getSelectionModel().getSelectedItem().getStatus();
            } else {
                associate = salesTable.getSelectionModel().getSelectedItem().getAssociate();
                city = salesTable.getSelectionModel().getSelectedItem().getCity();
                stat = salesTable.getSelectionModel().getSelectedItem().getStatus();
            }

            Sales.updateSales(salesTable.getSelectionModel().getSelectedItem().getSalesId(), associate, city, stat, Double.parseDouble(amountField.getText()), datePick.getValue().toString());
            salesTable.getItems().clear();
            salesTable.setItems(Sales.getSales());
        }
    }

    /**
     * Associates tab
     */
    @FXML
    private Tab associatesTab;
    @FXML
    private TextField associatesFirstNameField;
    @FXML
    private TextField associatesLastNameField;
    @FXML
    private TextField associatesResidenceField;
    @FXML
    private DatePicker associatesDateOfBirth;
    @FXML
    private DatePicker associatesStartDate;
    @FXML
    private Button associatesSaveButton;
    @FXML
    private Button associatesClearButton;
    @FXML
    private Button associatesUpdateButton;
    @FXML
    private TableView<Associates> associatesTable;
    @FXML
    private TableColumn<Associates, String> associatesFirstNameCol;
    @FXML
    private TableColumn<Associates, String> associatesLastNameCol;
    @FXML
    private TableColumn<Associates, String> associatesResidenceCol;
    @FXML
    private TableColumn<Associates, LocalDate> associatesDateOfBirthCol;
    @FXML
    private TableColumn<Associates, LocalDate> associatesStartDateCol;
    @FXML
    private TableColumn<Associates, String> statusCol;
    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private void saveAssociate(ActionEvent event) {
        String firstName;
        String lastName;
        String residence;
        String associateStatus;
        LocalDate dateOfBirth;
        LocalDate startDate;
        if (associatesFirstNameField.getText().isEmpty() || associatesLastNameField.getText().isEmpty() || associatesResidenceField.getText().isEmpty()) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Morate popuniti sva polja");
            a.show();
        } else {
            try {
                firstName = associatesFirstNameField.getText();
                lastName = associatesLastNameField.getText();
                residence = associatesResidenceField.getText();
                associateStatus = statusComboBox.getSelectionModel().getSelectedItem();
                dateOfBirth = associatesDateOfBirth.getValue();
                startDate = associatesStartDate.getValue();
                Associates.saveAssociate(firstName, lastName, residence, associateStatus, dateOfBirth, startDate);
            } catch (Exception e) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setContentText("Morate odabrati status i izabrati datume.\n" + e.getMessage());
                a.show();
            }
            associatesTable.getItems().clear();
            associatesTable.setItems(Associates.getAssociates());
        }
    }

    @FXML
    private void fillAssociatesFields(MouseEvent event) {
        if (!associatesTable.getSelectionModel().isEmpty()) {
            associatesFirstNameField.setText(associatesTable.getSelectionModel().getSelectedItem().getFirstName());
            associatesLastNameField.setText(associatesTable.getSelectionModel().getSelectedItem().getLastName());
            associatesResidenceField.setText(associatesTable.getSelectionModel().getSelectedItem().getResidence());
        }
    }

    @FXML
    private void updateAssociates(ActionEvent event) {
        String firstName;
        String lastName;
        String residence;
        String associateStatus;
        LocalDate dateOfBirth;
        LocalDate startDate;

        try {
            firstName = associatesFirstNameField.getText();
            lastName = associatesLastNameField.getText();
            residence = associatesResidenceField.getText();
            if (statusComboBox.getSelectionModel().isEmpty()) {
                associateStatus = associatesTable.getSelectionModel().getSelectedItem().getStatus();
            } else{
                associateStatus = statusComboBox.getSelectionModel().getSelectedItem();
            }
            if (associatesDateOfBirth.getValue() == null) {
                dateOfBirth = associatesTable.getSelectionModel().getSelectedItem().getDateOfBirth();
            } else{
                dateOfBirth = associatesDateOfBirth.getValue();
            }
            if (associatesStartDate.getValue() == null) {
                startDate = associatesTable.getSelectionModel().getSelectedItem().getStartDate();
            } else{
                startDate = associatesStartDate.getValue();
            }
            Associates.updateAssociates(associatesTable.getSelectionModel().getSelectedItem().getId(), firstName, lastName, residence, lastName, dateOfBirth, startDate);
        } catch(Exception e){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Problem kod ažuriranja saradnika!\n" + e.getMessage());
            a.show();
        }

    }

    /**
     * Overview tab
     */
    @FXML
    private Tab overviewTab;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populating sales table
        dateOfSaleCol.setCellValueFactory(new PropertyValueFactory<>("dateOfSale"));
        associateCol.setCellValueFactory(new PropertyValueFactory<>("associate"));
        totalAmountCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Sales> s = Sales.getSales();
        salesTable.setItems(s);

        updateButton.setDisable(true);

        statusComboBox.setItems(FXCollections.observableArrayList("Saradnik", "Lider"));

        //Populating associates table
        associatesFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        associatesLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        associatesResidenceCol.setCellValueFactory(new PropertyValueFactory<>("residence"));
        associatesDateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        associatesStartDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Associates> a = Associates.getAssociates();
        associatesTable.setItems(a);

    }

}
