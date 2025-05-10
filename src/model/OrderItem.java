package db_csc480.model;

import java.math.BigDecimal;

public class OrderItem {
  private int orderId, productId, quantity;
  private BigDecimal subtotal;

  public OrderItem(int orderId, int productId, int quantity, BigDecimal subtotal) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.subtotal = subtotal;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int o) {
    this.orderId = o;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int p) {
    this.productId = p;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int q) {
    this.quantity = q;
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(BigDecimal s) {
    this.subtotal = s;
  }
}
