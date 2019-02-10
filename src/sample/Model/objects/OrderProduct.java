package sample.Model.objects;

public class OrderProduct extends Product {
    private int quantity;
    private int orderId;

    public OrderProduct(int productid, String name, float price, String description, int categoryid, String categoryName, int quantity, int orderId) {
        super(productid, name, price, description, categoryid, categoryName);
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public float getTotal() {
        return quantity * price;
    }
}
