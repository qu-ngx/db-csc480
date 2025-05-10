package db_csc480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_csc480.model.Order;

public class OrderDAO {
  private final Connection conn;

  public OrderDAO(Connection conn) {
    this.conn = conn;
  }

  public static void create(Connection conn) throws SQLException {
    String sql = """
        CREATE TABLE IF NOT EXISTS "Order" (
          order_id      INTEGER PRIMARY KEY AUTOINCREMENT,
          customer_id   INTEGER REFERENCES Customer(customer_id) ON DELETE CASCADE,
          order_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
          total_amount  DECIMAL(10,2) NOT NULL
        )
        """;
    try (Statement st = conn.createStatement()) {
      st.execute(sql);
    }
  }

  public void clear() {
    try (Statement st = conn.createStatement()) {
      st.execute("DELETE FROM \"Order\"");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Order insert(Order o) {
    String sql = "INSERT INTO \"Order\"(customer_id,total_amount) VALUES(?,?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setInt(1, o.getCustomerId());
      ps.setBigDecimal(2, o.getTotalAmount());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next())
          o.setOrderId(rs.getInt(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return o;
  }

  public Order find(int id) {
    String sql = "SELECT * FROM \"Order\" WHERE order_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new Order(
              rs.getInt("order_id"),
              rs.getInt("customer_id"),
              rs.getTimestamp("order_date"),
              rs.getBigDecimal("total_amount"));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  public List<Order> getAll() {
    List<Order> list = new ArrayList<>();
    String sql = "SELECT * FROM \"Order\" ORDER BY order_date DESC";
    try (Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
      while (rs.next()) {
        list.add(new Order(
            rs.getInt("order_id"),
            rs.getInt("customer_id"),
            rs.getTimestamp("order_date"),
            rs.getBigDecimal("total_amount")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return list;
  }
}
