package db_csc480.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {
  private int paymentId, orderId;
  private Timestamp paymentDate;
  private BigDecimal amount;
  private String status;

  public Payment(int paymentId, int orderId, Timestamp paymentDate, BigDecimal amount, String status) {
    this.paymentId = paymentId;
    this.orderId = orderId;
    this.paymentDate = paymentDate;
    this.amount = amount;
    this.status = status;
  }

  public Payment(int orderId, BigDecimal amount, String status) {
    this(0, orderId, null, amount, status);
  }

  public int getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(int id) {
    this.paymentId = id;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int o) {
    this.orderId = o;
  }

  public Timestamp getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Timestamp d) {
    this.paymentDate = d;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal a) {
    this.amount = a;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String s) {
    this.status = s;
  }
}
