/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import skipper.DbConnection;

/**
 *
 * @author Coa
 */
public class Associates {

    private static Connection conn;

    private Integer id;
    private String firstName;
    private String lastName;
    private String residence;
    private String status;
    private LocalDate dateOfBirth;
    private LocalDate startDate;

    private static ObservableList<Associates> associatesList = FXCollections.observableArrayList();
    private static Alert a = new Alert(Alert.AlertType.CONFIRMATION);

    public Associates() {
    }

    public Associates(Integer id, String firstName, String lastName, String residence, String status, LocalDate dateOfBirth, LocalDate startDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.residence = residence;
        this.status = status;
        this.dateOfBirth = dateOfBirth;
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public static ObservableList<Associates> getAssociates() {
        associatesList.clear();
        try {
            Integer id;
            String firstName;
            String lastName;
            String residence;
            String status;
            LocalDate dateOfBirth;
            LocalDate startDate;

            Connection conn = DbConnection.connect();
            Statement s = conn.createStatement();
            s.execute("SELECT * FROM associates ORDER BY first_name");
            ResultSet rs = s.getResultSet();
            while (rs.next()) {
                id = Integer.parseInt(rs.getString("associate_id"));
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                residence = rs.getString("residence");
                status = rs.getString("status");
                dateOfBirth = LocalDate.parse(rs.getString("date_of_birth"));
                startDate = LocalDate.parse(rs.getString("start_date"));

                associatesList.add(new Associates(id, firstName, lastName, residence, status, dateOfBirth, startDate));
            }
            return associatesList;

        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Greska prilikom ucitavanja saradnika!!\n" + ex.getMessage());
            a.setTitle(null);
            a.show();
        }
        return null;
    }

    public static void saveAssociate(String firstName, String lastName, String residence, String status, LocalDate dateOfBirth, LocalDate startDate) {
        try {
            conn = DbConnection.connect();
            Statement s = conn.createStatement();
            s.execute("INSERT INTO associates(associate_id, first_name, last_name, residence, status, date_of_birth, start_date) VALUES(null, '" + firstName + "', '" + lastName + "', '" + residence + "', '" + status + "', '" + dateOfBirth.toString() + "', '" + startDate.toString() + "')");
            a.setHeaderText(null);
            a.setContentText("Uspešno ste kreirali novog saradnika!");
            a.show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText(e.getMessage());
            a.show();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
    }

    public static void updateAssociates(int id, String firstName, String lastName, String residence, String status, LocalDate dateOfBirth, LocalDate startDate) {
        try {
            conn = DbConnection.connect();
            Statement s = conn.createStatement();
            s.execute("UPDATE associates SET first_name = '" + firstName + "', last_name = '" + lastName + "', residence = '" + residence + "', status = '" + status + "', date_of_birth = '" + dateOfBirth.toString() + "', start_date = '" + startDate.toString() + "' WHERE associate_id = '" + id + "'");
            a.setHeaderText(null);
            a.setContentText("Uspešno ste izmenili podatke!");
            a.show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText(e.getMessage());
            a.show();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
    }

    @Override
    public String toString() {
        return id + ". " + firstName + " " + lastName + ", " + residence + ", " + status;
    }

}
