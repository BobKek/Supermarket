package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class Model {
    ObservableList<String> names, products, rewards, suppliers;

    Connection connection;

    public Model(){
        this.connection = createConnection();
    }


    public Connection createConnection(){
        connection = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SuperMarket", "root", "");

        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public String formatDate(String s){
        String[] split = s.split("-");
        String date = split[2] + "-" + split[0] + "-" + split[1];
        return date;
    }


    ////////// Getting Tables //////////
    public void getEmployees() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Employee");



            try {
                while (resultSet.next()){
                    list.add(resultSet.getString(2));

                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }catch (Exception e2) {
            e2.printStackTrace();
        }
        names = FXCollections.observableArrayList(list);

    }

    public void getSuppliers() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Supplier");

            try {
                while (resultSet.next()){
                    list.add(resultSet.getString(2));

                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }catch (Exception e2) {
            e2.printStackTrace();
        }
        suppliers = FXCollections.observableArrayList(list);
    }

    public void getRewards() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name,cost from Reward r join product p where r.productid = p.productid");

            try {
                while (resultSet.next()){
                    list.add(resultSet.getString(1) + ", " + resultSet.getString(2) + " points");

                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }catch (Exception e2) {
            e2.printStackTrace();
        }
        rewards = FXCollections.observableArrayList(list);
    }

    public void getProducts() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Product");

            try {
                while (resultSet.next()){
                    list.add(resultSet.getString(2));

                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }catch (Exception e2) {
            e2.printStackTrace();
        }
        products = FXCollections.observableArrayList(list);
    }

    public ArrayList<Position> getPositions(){

        ArrayList<Position> positionsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from Position");
            while (resultSet.next()) {
                int positionid = resultSet.getInt(1);
                String position_name = resultSet.getString("position_name");
                positionsList.add(new Position(positionid,position_name));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return positionsList;
    }

    public ArrayList<Branch> getBranches() {

        ArrayList<Branch> branchsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from Branch");
            while (resultSet.next()) {
                int branchid = resultSet.getInt(1);
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                branchsList.add(new Branch(branchid,city,country));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return branchsList;
    }

    public ArrayList<Category> getCategories() {

        ArrayList<Category> categoriesList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from Category");
            while (resultSet.next()) {
                int categoryid = resultSet.getInt("categoryid");
                String name = resultSet.getString("name");
                categoriesList.add(new Category(categoryid,name));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return categoriesList;
    }

    public ArrayList<Product> getProductsForComboBox() {

        ArrayList<Product> productsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from Product");
            while (resultSet.next()) {
                int productid = resultSet.getInt("productid");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String description = resultSet.getString("description");
                int categoryid = resultSet.getInt("categoryid");

                productsList.add(new Product(productid,name,price,description,categoryid));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return productsList;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    ////////// Inserting Into Tables //////////

    public void addEmployee(Employee employee){
        try {
            String query = "INSERT INTO Employee (first_name, last_name, phone, salary, positionid, branchid,birthday,employment_date) " +
                    "VALUES ( ?, ?, ?, ?, ?, ?,?,CURDATE());";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.firstName);
            preparedStatement.setString(2, employee.lastName);
            preparedStatement.setString(3, employee.phone);
            preparedStatement.setInt(4, employee.salary);
            preparedStatement.setInt(5, employee.positionid);
            preparedStatement.setInt(6,employee.branchid);
            preparedStatement.setString(7, employee.birthday);
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void addSupplier(Supplier supplier){
        try {
            String query = "INSERT INTO Supplier (name, phone) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplier.name);
            preparedStatement.setString(2, supplier.phone);
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addReward(Reward reward){
        try {
            String query = "INSERT INTO Reward (productid, cost) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reward.productid);
            preparedStatement.setInt(2, reward.cost);
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void addProduct(Product product){
        try {
            String query = "INSERT INTO Product (name, price, description, categoryid) VALUES ( ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.name);
            preparedStatement.setInt(2, product.price);
            preparedStatement.setString(3, product.description);
            preparedStatement.setInt(4, product.categoryid);
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addCategory(Category category){
        try{
            String query = "INSERT INTO Category (name) VALUES ( ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.name);
            preparedStatement.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
