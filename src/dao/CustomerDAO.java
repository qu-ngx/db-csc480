package db_csc480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_csc480.model.Customer;

public class CustomerDAO {
  private final Connection conn;

  public CustomerDAO(Connection conn) {
    this.conn = conn;
  }

  public static void create(Connection conn) throws SQLException {
    String sql = """
        CREATE TABLE IF NOT EXISTS Customer (
          customer_id   INTEGER PRIMARY KEY AUTOINCREMENT,
          name          TEXT    NOT NULL,
          email         TEXT    UNIQUE NOT NULL,
          address       TEXT    NOT NULL,
          phone         TEXT    UNIQUE NOT NULL
        )
        """;
    try (Statement st = conn.createStatement()) {
      st.execute(sql);
    }
  }

  public void clear() {
    try (Statement st = conn.createStatement()) {
      st.execute("DELETE FROM Customer");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Customer insert(Customer c) {
    String sql = "INSERT INTO Customer(name,email,address,phone) VALUES(?,?,?,?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, c.getName());
      ps.setString(2, c.getEmail());
      ps.setString(3, c.getAddress());
      ps.setString(4, c.getPhone());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next())
          c.setCustomerId(rs.getInt(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return c;
  }

  public Customer find(int id) {
    String sql = "SELECT * FROM Customer WHERE customer_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new Customer(
              rs.getInt("customer_id"),
              rs.getString("name"),
              rs.getString("email"),
              rs.getString("address"),
              rs.getString("phone"));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  public List<Customer> getAll() {
    List<Customer> list = new ArrayList<>();
    try (Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Customer")) {
      while (rs.next()) {
        list.add(new Customer(
            rs.getInt("customer_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("address"),
            rs.getString("phone")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return list;
  }
}
