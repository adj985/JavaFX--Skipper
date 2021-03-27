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
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import skipper.DbConnection;

/**
 *
 * @author Coa
 */
public class Sales {
    
    private String associate;
    private String status;
    private String city;
    private Double totalAmount;
    private LocalDate dateOfSale;

    private static Connection conn = null;
    private static ObservableList<Sales> salesList = FXCollections.observableArrayList();  
    private static Alert a = new Alert(Alert.AlertType.CONFIRMATION);

    public Sales() {
    }

    public Sales(String associate, String status, String city, Double totalAmount, LocalDate dateOfSale) {
        this.associate = associate;
        this.status = status;
        this.city = city;
        this.totalAmount = totalAmount;
        this.dateOfSale = dateOfSale;
    }

    public String getAssociate() {
        return associate;
    }

    public void setAssociate(String associate) {
        this.associate = associate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        Sales.conn = conn;
    }

    public static ObservableList<Sales> getSalesList() {
        return salesList;
    }

    public static void setSalesList(ObservableList<Sales> salesList) {
        Sales.salesList = salesList;
    }

    @Override
    public String toString() {
        return "Sales{" + "associate=" + associate + ", status=" + status + ", city=" + city + ", totalAmount=" + totalAmount + ", dateOfSale=" + dateOfSale + '}';
    }

    /**
     * Return all the sales from the database
     * 
     * @return 
     */
    public static ObservableList<Sales> getSales(){
        salesList.clear();
        try{
            String associate;
            String status;
            String city;
            Double totalAmount;
            String dateOfSale;
            LocalDate date;

            conn = DbConnection.connect();
            Statement s = conn.createStatement();
            s.execute("SELECT * FROM sales ORDER BY date_of_sale");
            ResultSet rs = s.getResultSet();
            
            while(rs.next()){
                associate = rs.getString("associate");
                status = rs.getString("status");
                city = rs.getString("city");
                totalAmount = rs.getDouble("total_amount");
                dateOfSale = rs.getString("date_of_sale");
                date = LocalDate.parse(dateOfSale, DateTimeFormatter.ISO_DATE);

                salesList.add(new Sales(associate, status, city, totalAmount, date));
            }
            return salesList;
            
        } catch (SQLException e) {
        } finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("getSales() issue!");
            }
        }
        return null;
    }
    
    /**
     * Saving a new sale in the database
     * 
     * @param associate
     * @param status
     * @param city
     * @param totalAmount
     * @param dateOfSale 
     */
    public static void saveSales(String associate, String status, String city, Double totalAmount, String dateOfSale){
        try {
            conn = DbConnection.connect();
            Statement s = conn.createStatement();
            s.execute("INSERT INTO sales (sales_id, associate, status, city, total_amount, date_of_sale) VALUES (null, '"+associate+"', '"+status+"','"+city+"','"+totalAmount+"','"+dateOfSale+"')");
            a.setHeaderText(null);
            a.setContentText("Uspešno ste sačuvali prodaju!");
            a.show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText(e.getMessage());
            a.show();
        } finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
}
