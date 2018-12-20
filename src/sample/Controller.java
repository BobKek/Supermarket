package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    //View view = new View();
    Model model = new Model();
    Connection connection = model.connection;





    ///////////// Side Menu Buttons /////////////
    @FXML
    public Button manageEmployeesButton;
    @FXML
    public Button manageSuppliesButton;
    @FXML
    public Button manageRewardsButton;
    @FXML
    public Button manageProductsButton;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public Button viewProductsButton;


    ///////////// Global Panes /////////////
    @FXML
    public FlowPane mainFlowPane;
    @FXML
    public AnchorPane manageEmployeesAnchorPane;

    @FXML
    public AnchorPane manageProductsAnchorPane;

    @FXML
    public AnchorPane manageRewardsAnchorPane;

    @FXML
    public AnchorPane manageSuppliesAnchorPane;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public ListView<String> employeesListView;
    @FXML
    public ListView<String> suppliersListView;
    @FXML
    public ListView<String> rewardsListView;
    @FXML
    public ListView<String> productsListView;


    @FXML
    public Button viewEmployeesButton;

    ///////////// Add employee variables /////////////
    @FXML
    public GridPane addEmployeeGridPane;
    @FXML
    public Button addEmployeeButton;
    @FXML
    public TextField empFirstnameTextField;
    @FXML
    public TextField empLastnameTextField;
    @FXML
    public DatePicker empBirthdatePicker;
    @FXML
    public TextField empPhoneTextField;
    @FXML
    public TextField empSalaryTextField;
    @FXML
    public ComboBox<Position> roleEmpComboBox;
    @FXML
    public ComboBox<Branch> branchEmpComboBox;
    @FXML
    public Button submitAddButton;
    @FXML
    public Button clearEmpData;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////// Add product variables /////////////
    @FXML
    public GridPane addProductGridPane;
    @FXML
    public Button clearProductDataButton;
    @FXML
    public Button submitAddProductButton;
    @FXML
    public Button addCategoryButton;

    @FXML
    public TextField productNameTextField;
    @FXML
    public TextField productPriceTextField;
    @FXML
    public ComboBox<Category> productCategoryComboBox;
    @FXML
    public TextArea productDescriptionTextArea;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////// Add supplier variables /////////////
    @FXML
    public GridPane addSupplierGridPane;
    @FXML
    public Button clearSupplierDataButton;
    @FXML
    public Button submitAddSupplierButton;

    @FXML
    public TextField supplierNameTextField;
    @FXML
    public TextField supplierPhoneTextField;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////// Add reward variables /////////////
    @FXML
    public GridPane addRewardGridPane;
    @FXML
    public Button clearRewardDataButton;
    @FXML
    public Button submitAddRewardButton;


    public ComboBox<Product> rewardProductComboBox;
    @FXML
    public TextField rewardPointsTextField;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public String currentlyViewing = "";


    public Controller(){
        //updateViewFromModel();
    }


    ///////////// Side Menu Handlers /////////////

    @FXML
    public void manageEmployeesHandler(){

        int index = mainFlowPane.getChildren().indexOf(manageEmployeesAnchorPane);
        mainFlowPane.getChildren().remove(index);
        mainFlowPane.getChildren().add(1,manageEmployeesAnchorPane);
        viewEmployeesHandler();
    }

    @FXML
    public void manageSuppliesHandler(){
        int index = mainFlowPane.getChildren().indexOf(manageSuppliesAnchorPane);
        mainFlowPane.getChildren().remove(index);
        mainFlowPane.getChildren().add(1,manageSuppliesAnchorPane);
        viewSuppliersHandler();
    }

    @FXML
    public void manageRewardsHandler(){
        int index = mainFlowPane.getChildren().indexOf(manageRewardsAnchorPane);
        mainFlowPane.getChildren().remove(index);
        mainFlowPane.getChildren().add(1,manageRewardsAnchorPane);
        viewRewardsHandler();
    }

    @FXML
    public void manageProductsHandler(){
        int index = mainFlowPane.getChildren().indexOf(manageProductsAnchorPane);
        mainFlowPane.getChildren().remove(index);
        mainFlowPane.getChildren().add(1,manageProductsAnchorPane);
        viewProductsHandler();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    ///////////// Employee Management /////////////

    @FXML
    public void viewEmployeesHandler() {
        addEmployeeGridPane.setVisible(false);
        employeesListView.setVisible(true);
        submitAddButton.setVisible(false);
        clearEmpData.setVisible(false);

        branchEmpComboBox.setPromptText("Select Branch");
        model.getEmployees();
        employeesListView.setItems(model.names);
    }


    //Add Employee Button Pressed
    @FXML
    public void addEmployeeViewHandler(){
        employeesListView.setVisible(false);
        addEmployeeGridPane.setVisible(true);
        submitAddButton.setVisible(true);
        clearEmpData.setVisible(true);


        //Setting up comboBoxes
        ArrayList<Position> positions = model.getPositions();
        ObservableList<Position> observablePositions = FXCollections.observableArrayList(positions);
        roleEmpComboBox.setItems(observablePositions);

        ArrayList<Branch> branches = model.getBranches();
        ObservableList<Branch> observableBranches = FXCollections.observableArrayList(branches);
        branchEmpComboBox.setItems(observableBranches);
    }


    //Add Employee
    @FXML
    public void addEmployeeHandler(){
        Employee newEmployee = new Employee();

        newEmployee.firstName = empFirstnameTextField.getText();
        newEmployee.lastName = empLastnameTextField.getText();
        newEmployee.phone = empPhoneTextField.getText();
        newEmployee.birthday = model.formatDate(empBirthdatePicker.getEditor().getText().replace("/", "-"));
        newEmployee.salary = Integer.valueOf(empSalaryTextField.getText());
        newEmployee.branchid = branchEmpComboBox.getValue().branchid;
        newEmployee.positionid = roleEmpComboBox.getValue().positionid;

        model.addEmployee(newEmployee);
    }

    //Clear All Fields
    @FXML
    public void clearEmployeeDataHandler(){
        empFirstnameTextField.setText("");
        empLastnameTextField.setText("");
        empBirthdatePicker.setValue(null);
        empPhoneTextField.setText("");
        empSalaryTextField.setText("");
        roleEmpComboBox.getSelectionModel().clearAndSelect(0);
        branchEmpComboBox.getSelectionModel().clearAndSelect(0);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////// Supplies Management /////////////

    @FXML
    public void viewSuppliersHandler() {
        suppliersListView.setVisible(true);
        addSupplierGridPane.setVisible(false);
        submitAddSupplierButton.setVisible(false);
        clearSupplierDataButton.setVisible(false);

        model.getSuppliers();
        suppliersListView.setItems(model.suppliers);
    }

    @FXML
    public void addSupplierViewHandler(){
        addSupplierGridPane.setVisible(true);
        submitAddSupplierButton.setVisible(true);
        clearSupplierDataButton.setVisible(true);
        suppliersListView.setVisible(false);

    }

    @FXML
    public void addSupplierHandler(){

        Supplier supplier = new Supplier();

        supplier.name = supplierNameTextField.getText();
        supplier.phone = supplierPhoneTextField.getText();

        model.addSupplier(supplier);

    }

    @FXML
    public void clearSupplierDataHandler(){
        supplierNameTextField.setText("");
        supplierPhoneTextField.setText("");
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////// Rewards Management /////////////

    @FXML
    public void viewRewardsHandler() {
        rewardsListView.setVisible(true);
        addRewardGridPane.setVisible(false);
        clearRewardDataButton.setVisible(false);
        submitAddRewardButton.setVisible(false);

        model.getRewards();
        rewardsListView.setItems(model.rewards);

    }

    @FXML
    public void addRewardViewHandler() {
        addRewardGridPane.setVisible(true);
        clearRewardDataButton.setVisible(true);
        submitAddRewardButton.setVisible(true);
        rewardsListView.setVisible(false);

        ArrayList<Product> productsForComboBox = model.getProductsForComboBox();
        ObservableList<Product> observableProducts = FXCollections.observableArrayList(productsForComboBox);
        rewardProductComboBox.setItems(observableProducts);

    }

    @FXML
    public void addRewardHandler() {
        Reward reward = new Reward();

        reward.productid = rewardProductComboBox.getValue().productid;
        reward.productname = rewardProductComboBox.getValue().name;
        reward.cost = Integer.valueOf(rewardPointsTextField.getText());

        model.addReward(reward);
    }

    @FXML
    public void clearRewardDataHandler() {
        rewardProductComboBox.getSelectionModel().clearSelection();
        rewardProductComboBox.setPromptText("Select Product");
        rewardPointsTextField.setText("");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////// Products Management /////////////

    @FXML
    public void viewProductsHandler(){
        addProductGridPane.setVisible(false);
        submitAddProductButton.setVisible(false);
        clearProductDataButton.setVisible(false);
        addCategoryButton.setVisible(false);
        productsListView.setVisible(true);


        model.getProducts();
        productsListView.setItems(model.products);



    }

    @FXML
    public void addProductViewHandler(){
        addProductGridPane.setVisible(true);
        submitAddProductButton.setVisible(true);
        clearProductDataButton.setVisible(true);
        addCategoryButton.setVisible(true);
        productsListView.setVisible(false);



        ArrayList<Category> categories = model.getCategories();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        productCategoryComboBox.setItems(observableCategories);
    }

    @FXML
    public void addProductHandler(){
        Product product = new Product();

        product.name = productNameTextField.getText();
        product.categoryid = productCategoryComboBox.getValue().categoryid;
        product.description = productDescriptionTextArea.getText();
        product.price = Integer.valueOf(productPriceTextField.getText());

        model.addProduct(product);

    }

    @FXML
    public void clearProductDataHandler(){
        productNameTextField.setText("");
        productCategoryComboBox.getSelectionModel().clearSelection();
        productCategoryComboBox.setPromptText("Select Category");
        productPriceTextField.setText("");
        productDescriptionTextArea.setText("");
    }

    @FXML
    public void addCategoryHandler(){
        TextInputDialog textInputDialog = new TextInputDialog("Add Category");
        textInputDialog.setTitle("Adding a category");
        textInputDialog.setHeaderText("Enter Category name");
        textInputDialog.setContentText("Name:");
        Optional<String> resultOfTextInputDialog = textInputDialog.showAndWait();

        resultOfTextInputDialog.ifPresent(name -> {
            Category newCat = new Category(name);
            model.addCategory(newCat);
            addProductViewHandler();

        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewEmployeesHandler();
    }
}







