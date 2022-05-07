package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderCTL implements Initializable {
    public TableView tbOrder;
    public TableColumn<Order,String> sIDCardOrder;
    public TableColumn<Order,Integer> sIDRoom;
    public TableColumn<Order,String> scheckin;
    public TableColumn<Order,String> sIDCardCI;
    public TableColumn<Order,String> scheckout;
    public TableColumn<Order,String> sIDCardCO;
    public TableColumn<Order,Integer> sdeposite;
    public TableColumn<Order,String> snote;
    public TableColumn<Order, Button> sedit;
    public TableColumn<Order, Button> sremove;

    public TextField sSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sIDCardOrder.setCellValueFactory(new PropertyValueFactory<>("IDCardOrder"));
        sIDRoom.setCellValueFactory(new PropertyValueFactory<>("IDRoom"));
        scheckin.setCellValueFactory(new PropertyValueFactory<>("checkin"));
        sIDCardCI.setCellValueFactory(new PropertyValueFactory<>("IDCardCI"));
        scheckout.setCellValueFactory(new PropertyValueFactory<>("checkout"));
        sIDCardCO.setCellValueFactory(new PropertyValueFactory<>("IDCardCO"));
        sdeposite.setCellValueFactory(new PropertyValueFactory<>("deposite"));
        snote.setCellValueFactory(new PropertyValueFactory<>("note"));
        sedit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        sremove.setCellValueFactory(new PropertyValueFactory<>("remove"));

        try{
            OrderRepository sr = new OrderRepository();
            ArrayList<Order> as =  sr.list();
            ObservableList<Order> list = FXCollections.observableArrayList();
            list.addAll(as);
            tbOrder.setItems(list);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void BackHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Main.rootStage.setScene(new Scene(root,1200,800));
    }

    public void AddOrder(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addorder.fxml"));
        Main.rootStage.setScene(new Scene(root,1200,800));
    }

    public void search(){
        OrderRepository sr = new OrderRepository();

        ObservableList<Order> list = FXCollections.observableArrayList();
        ArrayList<Order> as =  sr.list();
        list.addAll(as);
        String s = sSearch.getText();
        ArrayList<Order> kq = list.stream()
                .filter(p->p.getIDCardOrder().contains(s) ||
                        p.getIDRoom().toString().contains(s)||
                        p.getCheckin().toString().contains(s) ||
                        p.getIDCardCI().contains(s) ||
                        p.getCheckout().toString().contains(s) ||
                        p.getIDCardCO().contains(s) ||
                        p.getDeposite().contains(s) ||
                        p.getNote().contains(s)).collect(Collectors.toCollection(ArrayList::new));
        list.addAll(kq);
        tbOrder.setItems(list);
    }

}