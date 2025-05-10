package db_csc480.model;

public class Customer {
  private int customerId;
  private String name, email, address, phone;

  public Customer(int customerId, String name, String email, String address, String phone) {
    this.customerId = customerId;
    this.name = name;
    this.email = email;
    this.address = address;
    this.phone = phone;
  }

  public Customer(String name, String email, String address, String phone) {
    this(0, name, email, address, phone);
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int id) {
    this.customerId = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String n) {
    this.name = n;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String e) {
    this.email = e;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String a) {
    this.address = a;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String p) {
    this.phone = p;
  }
}
