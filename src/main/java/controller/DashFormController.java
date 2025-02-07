package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashFormController {

    public AnchorPane loadFormContent;

    public void btnItemFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("../view/item_form.fxml");

        assert  resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.loadFormContent.getChildren().clear();
        this.loadFormContent.getChildren().add(load);
    }

    public void btnOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("../view/order_form.fxml");

        assert  resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.loadFormContent.getChildren().clear();
        this.loadFormContent.getChildren().add(load);

    }

    public void btnCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("../view/customer_form.fxml");

        assert  resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.loadFormContent.getChildren().clear();
        this.loadFormContent.getChildren().add(load);
    }
}
