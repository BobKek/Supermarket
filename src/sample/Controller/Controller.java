package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import sample.*;
import sample.Model.Model;
import sample.Model.objects.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    Model model = Model.getInstanceOfModel();
    Login login = new Login();
    @FXML
    public Label changesLabel;

    public Controller(){
        //updateViewFromModel();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //region Global Panes
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
    //endregion

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //region Side Menu

    ///////////// Side Menu Buttons /////////////
    @FXML
    public Button manageEmployeesButton;
    @FXML
    public Button manageSuppliesButton;
    @FXML
    public Button manageRewardsButton;
    @FXML
    public Button manageProductsButton;

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
    //endregion

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////// Employee Management /////////////

    //region Employees Management

    ///////////// Variables /////////////

    ///////////// Add employee variables /////////////
    @FXML
    public GridPane addEmployeeGridPane;
    @FXML
    public TextField empFirstnameTextField;
    @FXML
    public TextField empLastnameTextField;
    @FXML
    public TextField empPhoneTextField;
    @FXML
    public TextField empSalaryTextField;
    @FXML
    public DatePicker empBirthdatePicker;
    @FXML
    public ComboBox<Position> roleEmpComboBox;
    @FXML
    public ComboBox<Branch> branchEmpComboBox;
    @FXML
    public Button addEmployeeButton;
    @FXML
    public Button submitAddButton;
    @FXML
    public Button clearEmpData;
    @FXML
    public Button viewEmployeesButton;

    ///////////// Table View ////////////////////////////////////////////////////////////////////////
    @FXML
    public TableView<Employee> employeesTableView;

    @FXML
    public TableColumn<Employee , Integer> employeeIdTableColumn;
    @FXML
    public TableColumn<Employee , String> employeeNameTableColumn;
    @FXML
    public TableColumn<Employee , String> employeePhoneTableColumn;
    @FXML
    public TableColumn<Employee , String> employeeBirthdayTableColumn;
    @FXML
    public TableColumn<Employee , Integer> employeeSalaryTableColumn;
    @FXML
    public TableColumn<Employee , Position> employeePositionTableColumn;
    @FXML
    public TableColumn<Employee , Branch> employeeBranchTableColumn;

    @FXML
    public void employeeApplyChangesHandler(){
        Employee employee = model.employees.get(employeesTableView.getFocusModel().getFocusedCell().getRow());
        model.editEmployee(employee);
        changesLabel.setText("Changes Applied!");
        changesLabel.setTextFill(Color.GREEN);
        changesLabel.setVisible(true);
    }

    public void setUpEmployeesTable() {
        employeesTableView.setEditable(true);
        employeeNameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeNameTableColumn.setOnEditStart(event -> {
            changesLabel.setText("Changes not applied!");
            changesLabel.setTextFill(Color.RED);
            changesLabel.setVisible(true);
        });
        employeeNameTableColumn.setOnEditCommit(event -> {
            final String fnValue = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(fnValue);
        });
        employeeNameTableColumn.setOnEditCancel(event -> {
            changesLabel.setVisible(false);
        });

        employeePhoneTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        employeePhoneTableColumn.setOnEditCommit(event -> {
            final String phoneValue = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhone(phoneValue);
        });

        employeeSalaryTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        employeeSalaryTableColumn.setOnEditCommit(event -> {
            final int salaryValue = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSalary(salaryValue);
        });

        employeePositionTableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(model.getPositions())));
        employeePositionTableColumn.setOnEditCommit(event -> {
            final Position posValue = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPosition(posValue);
        });

        employeeBranchTableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(model.getBranches())));
        employeeBranchTableColumn.setOnEditCommit(event -> {
            final Branch branchValue = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setBranch(branchValue);
        });

        employeesTableView.refresh();
        employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeePhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        employeeBirthdayTableColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        employeeSalaryTableColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        employeePositionTableColumn.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        employeeBranchTableColumn.setCellValueFactory(new PropertyValueFactory<>("branchLocation"));

        model.getEmployees();
        employeesTableView.setItems(model.employees);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void viewEmployeesHandler() {
        manageEmployeesButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#11AAFF;");

        manageSuppliesButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1A00AA;");

        manageRewardsButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1C046B;");

        manageProductsButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1A00AA;");

        addEmployeeGridPane.setVisible(false);
        employeesTableView.setVisible(true);
        submitAddButton.setVisible(false);
        clearEmpData.setVisible(false);
        changesLabel.setVisible(false);
        editBranchAnchorPane.setVisible(false);

        branchEmpComboBox.setPromptText("Select Branch");
        model.getEmployees();
        employeesTableView.setItems(model.employees);
    }


    //Add Employee Button Pressed
    @FXML
    public void addEmployeeViewHandler(){
        employeesTableView.setVisible(false);
        addEmployeeGridPane.setVisible(true);
        submitAddButton.setVisible(true);
        clearEmpData.setVisible(true);
        editBranchAnchorPane.setVisible(false);

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

        newEmployee.name = empFirstnameTextField.getText() + " " + empLastnameTextField.getText();
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
        roleEmpComboBox.getSelectionModel().clearSelection();
        roleEmpComboBox.setPromptText("Select Position");
        branchEmpComboBox.getSelectionModel().clearSelection();
        branchEmpComboBox.setPromptText("Select Branch");
    }
    //endregion

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //region Suppliers Management

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

    ////////////// Table View //////////////////////////////////////////////////////////////////////
    @FXML
    public TableView<Supplier> suppliersTableView;

    @FXML
    public TableColumn<Supplier, Integer> supplierIdTableColumn;
    @FXML
    public TableColumn<Supplier, String> supplierNameTableColumn;
    @FXML
    public TableColumn<Supplier, String> supplierPhoneTableColumn;

    public void setUpSuppliersTable() {

        supplierIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("supplierid"));
        supplierNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        model.getSuppliers();
        suppliersTableView.setItems(model.suppliers);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    public void viewSuppliersHandler() {
        manageEmployeesButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1C046B;");

        manageSuppliesButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#11AAFF;");

        manageRewardsButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1C046B;");

        manageProductsButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1A00AA;");

        suppliersTableView.setVisible(true);
        addSupplierGridPane.setVisible(false);
        submitAddSupplierButton.setVisible(false);
        clearSupplierDataButton.setVisible(false);

        model.getSuppliers();
        suppliersTableView.setItems(model.suppliers);
    }

    @FXML
    public void addSupplierViewHandler(){
        addSupplierGridPane.setVisible(true);
        submitAddSupplierButton.setVisible(true);
        clearSupplierDataButton.setVisible(true);
        suppliersTableView.setVisible(false);

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
    //endregion

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //region Rewards Management

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


    /////////////////Table View////////////////////////////////////////////////////////////////////
    @FXML
    public TableView<Reward> rewardsTableView;

    @FXML
    public TableColumn<Reward , Integer> rewardIdTableColumn;
    @FXML
    public TableColumn<Reward , String> rewardNameTableColumn;
    @FXML
    public TableColumn<Reward , Integer> rewardPriceTableColumn;
    @FXML
    public TableColumn<Reward , Integer> rewardPointsTableColumn;

    /*
    @FXML
    public void rewardApplyHandler(){
        //code to apply
        changesLabel.setText("Changes Applied!");
        changesLabel.setTextFill(Color.GREEN);
        changesLabel.setVisible(true);
    }*/

    public void setUpRewardsTable() {
        rewardIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));
        rewardNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        rewardPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        rewardPointsTableColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        rewardPointsTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        rewardPointsTableColumn.setOnEditStart(event -> {
            changesLabel.setText("Changes not applied!");
            changesLabel.setTextFill(Color.RED);
            changesLabel.setVisible(true);
        });
        rewardPointsTableColumn.setOnEditCommit(event -> {
            final int newPoints = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPoints(newPoints);
        });
        rewardPointsTableColumn.setOnEditCancel(event -> {
            changesLabel.setVisible(false);
        });

        rewardsTableView.refresh();
        model.getRewards();
        rewardsTableView.setItems(model.rewards);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void viewRewardsHandler() {
        manageEmployeesButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1C046B;");

        manageSuppliesButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1A00AA;");

        manageRewardsButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#11AAFF;");

        manageProductsButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1A00AA;");

        rewardsTableView.setVisible(true);
        addRewardGridPane.setVisible(false);
        clearRewardDataButton.setVisible(false);
        submitAddRewardButton.setVisible(false);

        model.getRewards();
        rewardsTableView.setItems(model.rewards);

    }

    @FXML
    public void addRewardViewHandler() {
        addRewardGridPane.setVisible(true);
        clearRewardDataButton.setVisible(true);
        submitAddRewardButton.setVisible(true);
        rewardsTableView.setVisible(false);

        ArrayList<Product> productsForComboBox = model.getProductsForComboBox();
        ObservableList<Product> observableProducts = FXCollections.observableArrayList(productsForComboBox);
        rewardProductComboBox.setItems(observableProducts);
    }

    @FXML
    public void addRewardHandler() {
        Reward reward = new Reward();

        reward.productid = rewardProductComboBox.getValue().productid;
        reward.productName = rewardProductComboBox.getValue().name;
        reward.points = Integer.valueOf(rewardPointsTextField.getText());

        try {
            model.addReward(reward);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void clearRewardDataHandler() {
        rewardProductComboBox.getSelectionModel().clearSelection();
        rewardProductComboBox.setPromptText("Select Product");
        rewardPointsTextField.setText("");
    }
    //endregion

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //region Products Management

    ///////////// Add product variables /////////////
    @FXML
    public GridPane addProductGridPane;
    @FXML
    public Button viewProductsButton;
    @FXML
    public Button clearProductDataButton;
    @FXML
    public Button submitAddProductButton;
    @FXML
    public TextField productNameTextField;
    @FXML
    public TextField productPriceTextField;
    @FXML
    public ComboBox<Category> productCategoryComboBox;
    @FXML
    public TextArea productDescriptionTextArea;

    ///////////// Table View ///////////////////////////////////////////////////////////////////////////////

    @FXML
    public TableView<Product> productsTableView;

    @FXML
    public TableColumn<Product , Integer> productIdTableColumn;
    @FXML
    public TableColumn<Product , String> productNameTableColumn;
    @FXML
    public TableColumn<Product , Float> productPriceTableColumn;
    @FXML
    public TableColumn<Product , String> productCategoryTableColumn;
    @FXML
    public TableColumn<Product , String> productDescriptionTableColumn;

    @FXML
    public void productApplyChangesHandler(){
        model.products = productsTableView.getItems();
        for(Product p : model.products){
            model.addProduct(p); //add products to database
        }
        changesLabel.setText("Changes Applied!");
        changesLabel.setTextFill(Color.GREEN);
        changesLabel.setVisible(true);
    }

    public void setUpProductsTable(){
        productsTableView.setEditable(true);
        //Setting up Name TableColumn to be editable
        //And to make it update the changesLabel to its required state
        productNameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productNameTableColumn.setOnEditStart(event -> {
            changesLabel.setText("Changes not applied!");
            changesLabel.setTextFill(Color.RED);
            changesLabel.setVisible(true);
        });
        productNameTableColumn.setOnEditCommit(event -> {
            final String newName = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            productNameTableColumn.getTableView().getItems().get(event.getTablePosition().getRow()).setName(newName);
        });
        productNameTableColumn.setOnEditCancel(event -> {
            changesLabel.setVisible(false);
        });

        //Now Setting up Price TableColumn to be editable
        //
        productPriceTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        productPriceTableColumn.setOnEditStart(event -> {
            changesLabel.setText("Changes not applied!");
            changesLabel.setTextFill(Color.RED);
            changesLabel.setVisible(true);
        });
        productPriceTableColumn.setOnEditCommit(event -> {
            final float newPrice = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            productPriceTableColumn.getTableView().getItems().get(event.getTablePosition().getRow()).setPrice(newPrice);
        });
        productPriceTableColumn.setOnEditCancel(event -> {
            changesLabel.setVisible(false);
        });

        //Now Setting up Description TableColumn to be editable
        //
        productDescriptionTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productDescriptionTableColumn.setOnEditStart(event -> {
            changesLabel.setText("Changes not applied!");
            changesLabel.setTextFill(Color.RED);
            changesLabel.setVisible(true);
        });
        productDescriptionTableColumn.setOnEditCancel(event -> {
            changesLabel.setVisible(false);
        });
        productDescriptionTableColumn.setOnEditCommit(event -> {
            final String newDesc = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            productNameTableColumn.getTableView().getItems().get(event.getTablePosition().getRow()).setName(newDesc);
        });

        productsTableView.refresh();

        productIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        productDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        model.getProducts();
        productsTableView.setItems(model.products);

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void viewProductsHandler(){
        manageEmployeesButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1C046B;"); //Dark

        manageSuppliesButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1A00AA;"); //Light

        manageRewardsButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#1C046B;"); //Dark

        manageProductsButton.setStyle("-fx-max-width:390px;\n" +
                "-fx-background-radius:0;\n" +
                "-fx-background-color:#11AAFF;"); //Active
        addProductGridPane.setVisible(false);
        submitAddProductButton.setVisible(false);
        clearProductDataButton.setVisible(false);
        productsTableView.setVisible(true);
        changesLabel.setVisible(false);

        model.getProducts();
        productsTableView.setItems(model.products);
    }

    @FXML
    public void addProductViewHandler(){
        addProductGridPane.setVisible(true);
        submitAddProductButton.setVisible(true);
        clearProductDataButton.setVisible(true);
        productsTableView.setVisible(false);

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

    @FXML
    public void deleteCategoryHandler(){
        Dialog<Category> dialog = new Dialog<>();
        dialog.setTitle("Delete Category");
        dialog.setHeaderText("Select category to delete");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ComboBox<Category> comboBox = new ComboBox<>(FXCollections.observableArrayList(model.getCategories()));

        comboBox.getSelectionModel().selectFirst();
        dialogPane.setContent(comboBox);
        dialog.setResultConverter((ButtonType button) -> {
            if(button == ButtonType.OK){
                if(comboBox.getValue() != null) {
                    model.deleteCategory(comboBox.getValue());
                }
            }
            return null;
        });
        dialog.showAndWait();
        viewProductsHandler();
    }
    //endregion

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //region Branch Management

    @FXML
    public AnchorPane editBranchAnchorPane;
    @FXML
    public TextField addBranchCountryTextField;
    @FXML
    public TextField addBranchCityTextField;
    @FXML
    public ComboBox<Branch> deleteBranchComboBox;
    @FXML
    public void editBranchesViewHandler(){
        editBranchAnchorPane.setVisible(true);
        employeesTableView.setVisible(false);
        addEmployeeGridPane.setVisible(false);
        deleteBranchComboBox.setItems(FXCollections.observableArrayList(model.getBranches()));
        deleteBranchComboBox.getSelectionModel().clearSelection();
        deleteBranchComboBox.setPromptText("--Select Branch--");
        addBranchCityTextField.clear();
        addBranchCountryTextField.clear();
    }

    @FXML
    public void addBranchHandler(){
        String city = addBranchCityTextField.getText();
        String country = addBranchCountryTextField.getText();

        Branch branch = new Branch(country, city);
        model.addBranch(branch);
        editBranchesViewHandler();
    }

    @FXML
    public void deleteBranchHandler(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() != ButtonType.OK){
            return;
        }
        else{
            model.deleteBranch(deleteBranchComboBox.getValue());
            editBranchesViewHandler();
        }
    }

    //endregion

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //region Logout
    @FXML
    public ImageView logoutButton;
    @FXML
    public void logoutHandler(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() != ButtonType.OK){
            return;
        }
//        Main.mainStage.setWidth(800);
//        Main.mainStage.setHeight(600);
        Main.mainStage.getScene().setRoot(Main.loginRoot);
        Main.mainStage.setTitle("Login");

        viewEmployeesHandler();
        login.isLoggedIn = false;
    }
    //endregion

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewEmployeesHandler();

        setUpViews();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setUpViews(){
        setUpSuppliersTable();
        setUpEmployeesTable();
        setUpRewardsTable();
        setUpProductsTable();
    }

}




