package db_csc480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_csc480.model.Payment;

public class PaymentDAO {
  private final Connection conn;

  public PaymentDAO(Connection conn) {
    this.conn = conn;
  }

  public static void create(Connection conn) throws SQLException {
    String sql = """
        CREATE TABLE IF NOT EXISTS Payment (
          payment_id    INTEGER PRIMARY KEY AUTOINCREMENT,
          order_id      INTEGER REFERENCES "Order"(order_id) ON DELETE CASCADE,
          payment_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
          amount        DECIMAL(10,2) NOT NULL,
          status        VARCHAR(50) CHECK (status IN ('Pending','Completed','Failed')) NOT NULL
        )
        """;
    try (Statement st = conn.createStatement()) {
      st.execute(sql);
    }
  }

  public void clear() {
    try (Statement st = conn.createStatement()) {
      st.execute("DELETE FROM Payment");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Payment insert(Payment p) {
    String sql = "INSERT INTO Payment(order_id,amount,status) VALUES(?,?,?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setInt(1, p.getOrderId());
      ps.setBigDecimal(2, p.getAmount());
      ps.setString(3, p.getStatus());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next())
          p.setPaymentId(rs.getInt(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return p;
  }

  public List<Payment> getAll() {
    List<Payment> list = new ArrayList<>();
    String sql = "SELECT * FROM Payment ORDER BY payment_date DESC";
    try (Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
      while (rs.next()) {
        list.add(new Payment(
            rs.getInt("payment_id"),
            rs.getInt("order_id"),
            rs.getTimestamp("payment_date"),
            rs.getBigDecimal("amount"),
            rs.getString("status")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return list;
  }
}
