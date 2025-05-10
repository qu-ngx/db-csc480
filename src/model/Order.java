package db_csc480.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
  private int orderId;
  private int customerId;
  private Timestamp orderDate;
  private BigDecimal totalAmount;
  private List<OrderItem> items = new ArrayList<>();

  public Order(int orderId, int customerId, Timestamp orderDate, BigDecimal totalAmount) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.orderDate = orderDate;
    this.totalAmount = totalAmount;
  }

  public Order(int customerId, BigDecimal totalAmount) {
    this(0, customerId, null, totalAmount);
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int id) {
    this.orderId = id;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int c) {
    this.customerId = c;
  }

  public Timestamp getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Timestamp d) {
    this.orderDate = d;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal a) {
    this.totalAmount = a;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> list) {
    this.items = list;
  }
}
