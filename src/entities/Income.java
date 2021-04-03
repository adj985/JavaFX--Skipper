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
import javafx.scene.control.Alert;
import skipper.DbConnection;

/**
 *
 * @author Coa
 */
public class Income {
    private final Integer associatesIncome;
    private final Integer leadersIncome;
    private static Connection conn;
    private static Alert a = new Alert(Alert.AlertType.CONFIRMATION);

    public Income(Integer associatesIncome, Integer leadersIncome ) {
        this.associatesIncome = associatesIncome;
        this.leadersIncome = leadersIncome;
    }
    
    public static Integer getLeadersIncome(){
        try {
            Integer i;
            conn = DbConnection.connect();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT leaders_income FROM income");
            return i = rs.getInt(1);
        } catch (SQLException e) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Problem kod dobavljanja podataka o zaradi\n" + e.getMessage());
            a.show();
        }
        return null;
    }
    
    public static void setLeadersIncome(int income){
        try {
            conn = DbConnection.connect();
            Statement s = conn.createStatement();
            s.execute("Update income SET leaders_income = '"+income+"'");    
        } catch (SQLException e) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Problem kod podešavanja zarade\n" + e.getMessage());
            a.show();
        }
    }
    
    public static Integer getAssociatesIncome(){
        try {
            Integer i;
            conn = DbConnection.connect();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT associates_income FROM income");
            return i = rs.getInt(1);
        } catch (SQLException e) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Problem kod dobavljanja podataka o zaradi\n" + e.getMessage());
            a.show();
        }
        return null;
    }
    
    public static void setAssociatesIncome(int income){
        try {
            conn = DbConnection.connect();
            Statement s = conn.createStatement();
            s.execute("Update income SET asociates_income = '"+income+"'");         
        } catch (SQLException e) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Problem kod podešavanja zarade\n" + e.getMessage());
            a.show();
        }
    }
    
    
}
