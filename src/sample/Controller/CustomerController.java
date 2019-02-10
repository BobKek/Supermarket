package sample.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.converter.IntegerStringConverter;
import sample.Main;
import sample.Model.Model;
import sample.Model.objects.Customer;
import sample.Model.objects.Order;
import sample.Model.objects.OrderProduct;
import sample.Model.objects.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    Model model = Model.getInstanceOfModel();
    private Customer loggedInCustomer = null;

    @FXML
    public TableView<Product> productsTableView;

    @FXML
    public TableColumn<Product , Integer> productIdTableColumn;
    @FXML
    public TableColumn<Product , String> productNameTableColumn;
    @FXML
    public TableColumn<Product , Integer> productPriceTableColumn;
    @FXML
    public TableColumn<Product , String> productCategoryTableColumn;
    @FXML
    public TableColumn<Product , String> productDescriptionTableColumn;

    //TableView of purchased products
    @FXML
    public TableView<OrderProduct> ordersTableView;

    @FXML
    public TableColumn<Product , Integer> orderIdTableColumn;
    @FXML
    public TableColumn<Product , String> orderProductNameTableColumn;
    @FXML
    public TableColumn<Product , Float> orderProductPriceTableColumn;
    @FXML
    public TableColumn<Product , Integer> orderProductQuantityTableColumn;
    @FXML
    public TableColumn<Product , Float> orderProductTotalPriceColumn;

    ///////////////////////////////////

    @FXML
    public TextField searchTextField, productQuantityTextField;

    @FXML
    public void searchHandler(){
        if(searchTextField.getText() == null){
            return;
        }
        String keyword = searchTextField.getText();
        ArrayList<Product> filteredProductsArrayList = model.searchProducts(keyword);
        if(ordersTableView.isVisible()) {
//            ordersTableView.setItems(FXCollections.observableArrayList(filteredProductsArrayList));
        }
        else{
            productsTableView.setItems(FXCollections.observableArrayList(filteredProductsArrayList));
        }
    }

    @FXML
    public void resetTableData(){
        if(ordersTableView.isVisible()) {
            setUpOrdersTable();
        }
        else
            productsTableView.setItems(FXCollections.observableArrayList(model.products));
    }

    @FXML
    public void viewProducts(){
        ordersTableView.setVisible(false);
    }

    @FXML
    public void viewMyOrders(){
        ordersTableView.setVisible(true);
        setUpOrdersTable();
    }

    @FXML
    public TextField productIdTextField;
    @FXML
    public void addOrderHandler(){
        if(productIdTextField.getText() == null){
            return;
        }
        int pid = Integer.valueOf(productIdTextField.getText());
        int qty = Integer.valueOf(productQuantityTextField.getText());
        loggedInCustomer.buyProduct(pid, qty);
        setUpOrdersTable();
        // we add an order to the current loggedInCustomer
    }

    public void setUpProductsTable(){
        productsTableView.setEditable(false);
        productIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        productDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        model.getProducts();
        productsTableView.setItems(model.products);

    }

    public void setUpOrdersTable() {
        ordersTableView.setEditable(false);
        orderIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderProductNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderProductPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderProductQuantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderProductTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        model.getOrders(loggedInCustomer);
        ordersTableView.setItems(model.orders);

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void logoutHandler(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() != ButtonType.OK){
            return;
        }
        Main.mainStage.getScene().setRoot(Main.loginRoot);
        Main.mainStage.setTitle("Login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpProductsTable();
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public void setLoggedInCustomer(Customer loggedInCustomer) {
        this.loggedInCustomer = loggedInCustomer;
    }
}
