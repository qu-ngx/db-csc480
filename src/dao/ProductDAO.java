package db_csc480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db_csc480.model.Product;

public class ProductDAO {
  private final Connection conn;

  public ProductDAO(Connection conn) {
    this.conn = conn;
  }

  public static void create(Connection conn) throws SQLException {
    String sql = """
        CREATE TABLE IF NOT EXISTS Product (
          product_id      INTEGER PRIMARY KEY AUTOINCREMENT,
          name            TEXT    NOT NULL,
          description     TEXT,
          price           DECIMAL(10,2) NOT NULL,
          stock_quantity  INTEGER NOT NULL CHECK (stock_quantity >= 0)
        )
        """;
    try (Statement st = conn.createStatement()) {
      st.execute(sql);
    }
  }

  public void clear() {
    try (Statement st = conn.createStatement()) {
      st.execute("DELETE FROM Product");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Product insert(Product p) {
    String sql = "INSERT INTO Product(name,description,price,stock_quantity) VALUES(?,?,?,?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, p.getName());
      ps.setString(2, p.getDescription());
      ps.setBigDecimal(3, p.getPrice());
      ps.setInt(4, p.getStockQuantity());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next())
          p.setProductId(rs.getInt(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return p;
  }

  public Product find(int id) {
    String sql = "SELECT * FROM Product WHERE product_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new Product(
              rs.getInt("product_id"),
              rs.getString("name"),
              rs.getString("description"),
              rs.getBigDecimal("price"),
              rs.getInt("stock_quantity"));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  public List<Product> getAll() {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT * FROM Product ORDER BY name";
    try (Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
      while (rs.next()) {
        list.add(new Product(
            rs.getInt("product_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBigDecimal("price"),
            rs.getInt("stock_quantity")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return list;
  }
}
