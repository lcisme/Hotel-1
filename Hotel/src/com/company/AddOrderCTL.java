package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddOrderCTL implements Initializable {
    public TextField sIDOrder;
    public ComboBox<Customer> sIDCardOrder;
    public TextField sIDRoom;
    public DatePicker scheckin;
    public ComboBox<Customer> sIDCardCI;
    public DatePicker scheckout;
    public ComboBox<Customer> sIDCardCO;
    public TextField sdeposite;
    public TextArea snote;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CustomerRepository sr = new CustomerRepository();
        ArrayList<Customer> as = sr.list();
        ObservableList<Customer> ctlist = FXCollections.observableArrayList();
        ctlist.addAll(as);
        sIDCardOrder.setItems(ctlist);
        sIDCardCI.setItems(ctlist);
        sIDCardCO.setItems(ctlist);
    }

    public Order editData;
    public void setEditData(Order editData){
        this.editData=editData;
        this.sIDOrder.setText(editData.getIDOrder().toString());
        this.sIDOrder.setDisable(true);
        this.sIDRoom.setText(editData.getIDRoom().toString());
        this.scheckin.setValue(editData.getCheckin().toLocalDate());
        this.scheckout.setValue(editData.getCheckout().toLocalDate());
        this.sdeposite.setText(editData.getDeposite());
        this.snote.setText(editData.getNote());
    }


    public void backorder(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("order.fxml"));
        Main.rootStage.setScene(new Scene(root,1200,800));
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        String IDOrder = this.sIDOrder.getText();
        Customer selectedIDCard = sIDCardOrder.getSelectionModel().getSelectedItem();
        String IDRoom = this.sIDRoom.getText();
        LocalDate checkin = this.scheckin.getValue();
        Customer selectedIDCardCI = sIDCardCI.getSelectionModel().getSelectedItem();
        LocalDate checkout = this.scheckout.getValue();
        Customer selectedIDCardCO = sIDCardCO.getSelectionModel().getSelectedItem();
        String deposite = this.sdeposite.getText();
        String note = this.snote.getText();

        try{
            OrderRepository sr = new OrderRepository();
            if(this.editData==null){
                Order s = new Order(Integer.valueOf(IDOrder),selectedIDCard.IDCard,Integer.valueOf(IDRoom), Date.valueOf(checkin),selectedIDCardCI.IDCard,Date.valueOf(checkout),selectedIDCardCO.IDCard,deposite,note);
                sr.create(s);
            }else {

                Order s = new Order(Integer.valueOf(IDOrder),selectedIDCard.IDCard,Integer.valueOf(IDRoom), Date.valueOf(checkin),selectedIDCardCI.IDCard,Date.valueOf(checkout),selectedIDCardCO.IDCard,deposite,note);
                sr.update(s);
            }



        }catch (Exception e){
            System.out.println(e.getMessage());
        }



        Parent root = FXMLLoader.load(getClass().getResource("order.fxml"));
        Main.rootStage.setScene(new Scene(root,1200,800));
    }
}