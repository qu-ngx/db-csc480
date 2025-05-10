package db_csc480.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

import db_csc480.model.Customer;
import db_csc480.model.Order;
import db_csc480.model.OrderItem;
import db_csc480.model.Payment;
import db_csc480.model.Product;

// Update the import statement at the top of the file
import db_csc480.dao.ProductDAO;

public class DatabaseManager {
  private Connection conn;
  private CustomerDAO customerDAO;
  private ProductDAO productDAO;
  private OrderDAO orderDAO;
  private OrderItemDAO orderItemDAO;
  private PaymentDAO paymentDAO;
  private final String url = "jdbc:sqlite:db/ecommerce.db";

  public DatabaseManager() {
    try {
      conn = DriverManager.getConnection(url);
      conn.setAutoCommit(false);
      createTables(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Cannot connect to database", e);
    }
    customerDAO = new CustomerDAO(conn);
    productDAO = new ProductDAO(conn);
    orderDAO = new OrderDAO(conn);
    orderItemDAO = new OrderItemDAO(conn);
    paymentDAO = new PaymentDAO(conn);
  }

  private void createTables(Connection conn) throws SQLException {
    CustomerDAO.create(conn);
    ProductDAO.create(conn);
    OrderDAO.create(conn);
    OrderItemDAO.create(conn);
    PaymentDAO.create(conn);
    conn.commit();
  }

  public void clearTables() {
    paymentDAO.clear();
    orderItemDAO.clear();
    orderDAO.clear();
    productDAO.clear();
    customerDAO.clear();
  }

  public Customer insertCustomer(Customer c) {
    return customerDAO.insert(c);
  }

  public Product insertProduct(Product p) {
    return productDAO.insert(p);
  }

  public Order insertOrder(Order o) {
    return orderDAO.insert(o);
  }

  public OrderItem insertOrderItem(OrderItem oi) {
    return orderItemDAO.insert(oi);
  }

  public Payment insertPayment(Payment p) {
    return paymentDAO.insert(p);
  }

  public Collection<Customer> getCustomers() {
    return customerDAO.getAll();
  }

  public Collection<Product> getProducts() {
    return productDAO.getAll();
  }

  public Collection<Order> getOrders() {
    return orderDAO.getAll();
  }

  public Collection<Payment> getPayments() {
    return paymentDAO.getAll();
  }

  public Collection<OrderItem> getOrderItems(int oid) {
    return orderItemDAO.findByOrder(oid);
  }

  public void commit() {
    try {
      conn.commit();
    } catch (SQLException e) {
      throw new RuntimeException("Commit failed", e);
    }
  }

  public void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException("Cannot close", e);
    }
  }
}
