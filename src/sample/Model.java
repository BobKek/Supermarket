package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class Model {
    ObservableList<Employee> employees;
    ObservableList<Supplier> suppliers;
    ObservableList<Reward> rewards;
    ObservableList<Product> products;


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
        int empid, salary, positionid, branchid;
        String firstName, lastName, phone, birthday, employmentDate, positionName;
        ArrayList<Employee> empsTemp = new ArrayList<>();
        Employee employee;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Employee e join Position p where e.positionid = p.positionid");
            try {
                while (resultSet.next()){
                    empid = resultSet.getInt("employeeid");
                    firstName = resultSet.getString("first_name");
                    lastName = resultSet.getString("last_name");
                    phone = resultSet.getString("phone");
                    birthday = resultSet.getString("birthday");
                    salary = resultSet.getInt("salary");
                    employmentDate = resultSet.getString("employment_date");
                    branchid = resultSet.getInt("branchid");
                    positionid = resultSet.getInt("positionid");
                    positionName = resultSet.getString("position_name");

                    employee = new Employee(empid, firstName, lastName, phone, birthday, salary, employmentDate, branchid, positionid, positionName);
                    empsTemp.add(employee);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }catch (Exception e2) {
            e2.printStackTrace();
        }
        employees = FXCollections.observableArrayList(empsTemp);
    }

    public void getSuppliers() {
        ArrayList<Supplier> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Supplier");

            try {
                while (resultSet.next()){
                    list.add(new Supplier(resultSet.getInt("supplierid"),resultSet.getString("name") , resultSet.getString("phone")));

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
        ArrayList<Reward> list = new ArrayList<>();
        int productid, price, cost;
        String name;
        Reward reward;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select p.productid, name, cost, price from Reward r join Product p where r.productid = p.productid");
            try {
                while (resultSet.next()){
                    productid = resultSet.getInt("productid");
                    price = resultSet.getInt("price");
                    cost = resultSet.getInt("cost");
                    name = resultSet.getString("name");
                    reward = new Reward(productid, name, cost, price);
                    list.add(reward);
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
        ArrayList<Product> list = new ArrayList<>();
        int productid, price, categoryid;
        String name, description, categoryName;
        Product product;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Product p join Category c where p.categoryid = c.categoryid");

            try {
                while (resultSet.next()){
                    productid = resultSet.getInt("productid");
                    price = resultSet.getInt("price");
                    categoryid = resultSet.getInt("categoryid");
                    name = resultSet.getString("name");
                    description = resultSet.getString("description");
                    categoryName = resultSet.getString("c.name");
                    product = new Product(productid, name, price, description, categoryid, categoryName);
                    list.add(product);
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
        }catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
        }
        catch (Exception e){
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


    public boolean verifyUser(String username , String password , String side) {
        try {
            String query = "Select username , password from " + side + " where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first() && resultSet.getString("password").equals(password))
                return true;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
