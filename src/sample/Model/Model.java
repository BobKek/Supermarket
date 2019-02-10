package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.objects.*;

import java.sql.*;
import java.util.ArrayList;

public class Model {
    private static Model model = null; //Model is a singleton class
    public ObservableList<Employee> employees;
    public ObservableList<Supplier> suppliers;
    public ObservableList<Reward> rewards;
    public ObservableList<Product> products;
    public ObservableList<OrderProduct> orders;

    public int currentOrder = 0;

    Connection connection;

    private Model(){
        this.connection = createConnection();
    }

    public static Model getInstanceOfModel(){ //Get instance method of Model class
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public Connection createConnection(){
        connection = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SuperMarket", "root", "root");

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

    public void buyProduct(int customerId ,int productId, int quantity) {
        try {
            String query = "Insert Into OrderDetails (orderid, productid, quantity) values (?, ?, ?)";
            if (currentOrder == 0){
                String orderQuery = "Insert into `order` (date ,customerid, employeeid) values (curdate(), ?, 1001)";
                PreparedStatement preparedStatement = model.connection.prepareStatement(orderQuery);
                preparedStatement.setInt(1, customerId);
                preparedStatement.execute();

                String orderIdQuery = "select orderid from `order` order by orderid desc";
                PreparedStatement ps = model.connection.prepareStatement(orderIdQuery);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    currentOrder = rs.getInt(1);
            }
            PreparedStatement preparedStatement = model.connection.prepareStatement(query);
            preparedStatement.setInt(1, currentOrder);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    ////////// Getting Tables //////////
    public void getOrders(Customer customer){
        ArrayList<OrderProduct> list = new ArrayList<>();
        int orderid, employeeid, customerid, productid, quantity, categoryid;
        String date, productName, description, categoryName;
        float price;
        try {
            String query = "select quantity, c.name, p.productid, p.name, price, description, p.categoryid from (OrderDetails od join Product p) join Category c" +
                    " on od.orderid = ? and p.productid = od.productid and c.categoryid = p.categoryid";
            PreparedStatement ps = connection.prepareStatement(query);

            String getOrders = "select * from `Order` where customerid = ?";
            PreparedStatement psGetOrders = connection.prepareStatement(getOrders);
            psGetOrders.setInt(1, customer.getCustomerid());
            ResultSet resultSet = psGetOrders.executeQuery();
            try {
                while (resultSet.next()){
                    orderid = resultSet.getInt("orderid");
                    ps.setInt(1, orderid);
                    date = resultSet.getString("date");
                    employeeid = resultSet.getInt("employeeid");
                    customerid = resultSet.getInt("customerid");
                    Order order = new Order(orderid, date, employeeid, customerid);

                    ps.setInt(1, orderid);

                    ResultSet orderDetails = ps.executeQuery();
                    while(orderDetails.next()) {
                        OrderProduct orderProduct = null;
                        productid = orderDetails.getInt("p.productid");
                        price = orderDetails.getFloat("price");
                        productName = orderDetails.getString("p.name");

                        description = orderDetails.getString("description");

                        categoryid = orderDetails.getInt("p.categoryid");
                        categoryName = orderDetails.getString("c.name");

                        quantity = orderDetails.getInt("quantity");

                        orderProduct = new OrderProduct(productid, productName, price, description, categoryid, categoryName, quantity, orderid);
                        order.getOrderProductsArrayList().add(orderProduct);
                        list.add(orderProduct);
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }catch (Exception e2) {
            e2.printStackTrace();
        }
        orders = FXCollections.observableArrayList(list);
    }

    public void getEmployees() {
        int empid, salary, positionid, branchid;
        String name, phone, birthday, employmentDate, positionName, country, city;
        ArrayList<Employee> empsTemp = new ArrayList<>();
        Employee employee;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from EmployeeView");
            try {
                while (resultSet.next()){
                    empid = resultSet.getInt("employeeid");
                    name = resultSet.getString("name");
                    phone = resultSet.getString("phone");
                    birthday = resultSet.getString("birthday");
                    salary = resultSet.getInt("salary");
                    employmentDate = resultSet.getString("employment_date");
                    branchid = resultSet.getInt("branchid");
                    positionid = resultSet.getInt("positionid");
                    positionName = resultSet.getString("positionname");
                    country = resultSet.getString("country");
                    city = resultSet.getString("city");

                    employee = new Employee(empid, name, phone, birthday, salary, employmentDate, branchid, positionid, positionName, country, city);
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
            ResultSet resultSet = statement.executeQuery("select p.productid, name, points, price from Reward r join Product p where r.productid = p.productid");
            try {
                while (resultSet.next()){
                    productid = resultSet.getInt("productid");
                    price = resultSet.getInt("price");
                    cost = resultSet.getInt("points");
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

    public ArrayList<Product> searchProducts(String keyword){
        ArrayList<Product> list = new ArrayList<>();
        int productid, categoryid;
        float price;
        String name, description, categoryName;
        Product product;
        try {
            String query = "select * from Product p join Category c where p.categoryid = c.categoryid and p.name like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            //String formattedKeyword = keyword;
            String formattedKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, formattedKeyword);
            ResultSet resultSet = preparedStatement.executeQuery();

            try {
                while (resultSet.next()){
                    productid = resultSet.getInt("productid");
                    price = resultSet.getFloat("price");
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
        return list;
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
                String name = resultSet.getString("name");
                positionsList.add(new Position(positionid,name));
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
            String query = "INSERT INTO Employee (name, phone, salary, positionid, branchid,birthday,employment_date) " +
                    "VALUES ( ?, ?, ?, ?, ?, ?,CURDATE());";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.name);
            //preparedStatement.setString(2, employee.lastName);
            preparedStatement.setString(2, employee.phone);
            preparedStatement.setInt(3, employee.salary);
            preparedStatement.setInt(4, employee.positionid);
            preparedStatement.setInt(5, employee.branchid);
            preparedStatement.setString(6, employee.birthday);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editEmployee(Employee employee){
        try {
            String query = "UPDATE Employee set name = ?, phone = ?, salary = ?, positionid = ?, branchid = ?" +
                    " where employeeid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPhone());
            preparedStatement.setInt(3, employee.getSalary());
            preparedStatement.setInt(4, employee.getPositionid());
            preparedStatement.setInt(5, employee.getBranchid());
            preparedStatement.setInt(6, employee.getEmployeeid());
            preparedStatement.execute();
        } catch(Exception e) {
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
            String query = "INSERT INTO Reward (productid, points) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reward.productid);
            preparedStatement.setInt(2, reward.points);
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
            preparedStatement.setFloat(2, product.price);
            preparedStatement.setString(3, product.description);
            preparedStatement.setInt(4, product.categoryid);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String query = "UPDATE Product set name = ?, price = ?, description = ? where productid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.name);
            preparedStatement.setFloat(2, product.price);
            preparedStatement.setString(3, product.description);
            preparedStatement.setInt(4, product.getProductid());
            preparedStatement.execute();
        } catch (Exception e) {
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

    public void addBranch(Branch branch){
        try {
            String query = "INSERT INTO Branch (city, country) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, branch.city);
            preparedStatement.setString(2, branch.country);
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void deleteCategory(Category category){
        try {
            String query = "DELETE from Category where categoryid = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, category.categoryid);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteBranch(Branch branch){
        try {
            String query = "DELETE from Branch where branchid = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, branch.branchid);
            preparedStatement.executeUpdate();
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

    public Customer getCustomer(String username){
        Customer customer = null;
        try {
            String query = "Select * from Customer where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();


            int customerid;
            String name;
            String password;
            String phone;
            String email;
            int points;
            while(resultSet.next()){
                customerid = resultSet.getInt("customerid");
                name = resultSet.getString("name");
                password = resultSet.getString("password");
                phone = resultSet.getString("phone");
                email = resultSet.getString("email");
                points = resultSet.getInt("points");
                customer = new Customer(name, username, password, phone, email, customerid, points);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }

}
