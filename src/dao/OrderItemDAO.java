package db_csc480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_csc480.model.OrderItem;

public class OrderItemDAO {
  private final Connection conn;

  public OrderItemDAO(Connection conn) {
    this.conn = conn;
  }

  public static void create(Connection conn) throws SQLException {
    String sql = """
        CREATE TABLE IF NOT EXISTS Order_Item (
          order_id    INTEGER REFERENCES "Order"(order_id) ON DELETE CASCADE,
          product_id  INTEGER REFERENCES Product(product_id) ON DELETE CASCADE,
          quantity    INTEGER NOT NULL CHECK (quantity > 0),
          subtotal    DECIMAL(10,2) NOT NULL,
          PRIMARY KEY (order_id, product_id)
        )
        """;
    try (Statement st = conn.createStatement()) {
      st.execute(sql);
    }
  }

  public void clear() {
    try (Statement st = conn.createStatement()) {
      st.execute("DELETE FROM Order_Item");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public OrderItem insert(OrderItem oi) {
    String sql = "INSERT INTO Order_Item(order_id,product_id,quantity,subtotal) VALUES(?,?,?,?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, oi.getOrderId());
      ps.setInt(2, oi.getProductId());
      ps.setInt(3, oi.getQuantity());
      ps.setBigDecimal(4, oi.getSubtotal());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return oi;
  }

  public List<OrderItem> findByOrder(int orderId) {
    List<OrderItem> list = new ArrayList<>();
    String sql = "SELECT * FROM Order_Item WHERE order_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, orderId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(new OrderItem(
              rs.getInt("order_id"),
              rs.getInt("product_id"),
              rs.getInt("quantity"),
              rs.getBigDecimal("subtotal")));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return list;
  }
}
