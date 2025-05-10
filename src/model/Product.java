package db_csc480.model;

import java.math.BigDecimal;

public class Product {
  private int productId;
  private String name, description;
  private BigDecimal price;
  private int stockQuantity;

  public Product(int productId, String name, String description, BigDecimal price, int stockQuantity) {
    this.productId = productId;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public Product(String name, String desc, BigDecimal price, int qty) {
    this(0, name, desc, price, qty);
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int id) {
    this.productId = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String n) {
    this.name = n;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String d) {
    this.description = d;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal p) {
    this.price = p;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int q) {
    this.stockQuantity = q;
  }
}
