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
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    private final Alert a = new Alert(Alert.AlertType.ERROR);

    @FXML
    private void selectAnAssociate(ActionEvent event) {
    }

    @FXML
    private void saveTheSale(ActionEvent event) {
        Associates associate = null;
        String ass;
        String stat;
        String city;
        Double totalAmount;
        String dateOfSale;

        try {
            totalAmount = Double.valueOf(amountField.getText());
            dateOfSale = datePick.getValue().toString();
            if (associateCheckBox.isSelected()) {
                associate = associateComboBox.getSelectionModel().getSelectedItem();
                ass = associate.getFirstName() + " " + associate.getLastName();
                stat = associate.getStatus();
                city = associate.getResidence();
            } else {
                ass = "Licna prodaja";
                stat = "Lider";
                city = "Kragujevac";
            }
            Sales s = new Sales(ass, stat, city, totalAmount, datePick.getValue());

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
//            Sales.saveSales(ass, stat, city, totalAmount, dateOfSale);

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
    private void clearTheFields(ActionEvent event) {
        datePick.setValue(null);
        associateCheckBox.setSelected(false);
        associateComboBox.setDisable(true);
        amountField.clear();
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
    private void fillTheFields(MouseEvent event) {
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
    private TableColumn<Associates, Integer> associatesIdCol;
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
    private ComboBox<String> leaderComboBox;

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

        leaderComboBox.setItems(FXCollections.observableArrayList("Saradnik", "Lider"));
        leaderComboBox.getSelectionModel().selectFirst();

        //Populating associates table
        associatesIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
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
