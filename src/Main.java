package db_csc480;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

import db_csc480.dao.DatabaseManager;
import db_csc480.model.Customer;
import db_csc480.model.Order;
import db_csc480.model.OrderItem;
import db_csc480.model.Payment;
import db_csc480.model.Product;

public class Main {
  private static final Scanner in = new Scanner(System.in);
  private static final PrintStream out = System.out;

  public static void main(String[] args) {
    DatabaseManager dbm = new DatabaseManager();
    displayMenu();
    loop: while (true) {
      switch (request("Selection (0-quit, 9-menu)? ")) {
        case "0":
          break loop;
        case "1":
          reset(dbm);
          break;
        case "2":
          listCustomers(dbm);
          break;
        case "3":
          listProducts(dbm);
          break;
        case "4":
          listOrders(dbm);
          break;
        case "5":
          listPayments(dbm);
          break;
        default:
          displayMenu();
          break;
      }
    }
    out.println("Exiting.");
    dbm.close();
  }

  private static void displayMenu() {
    out.println("0: Quit");
    out.println("1: Reset tables & seed data");
    out.println("2: List Customers");
    out.println("3: List Products");
    out.println("4: List Orders");
    out.println("5: List Payments");
  }

  private static String request(String p) {
    out.print(p);
    out.flush();
    return in.nextLine();
  }

  private static void reset(DatabaseManager dbm) {
    dbm.clearTables();
    Customer alice = dbm.insertCustomer(
        new Customer("Alice", "alice@example.com", "123 Main St", "555-1234"));
    Customer bob = dbm.insertCustomer(
        new Customer("Bob", "bob@example.com", "456 High St", "555-5678"));

    Product widget = dbm.insertProduct(
        new Product("Widget", "Useful widget", new BigDecimal("9.99"), 100));
    Product gadget = dbm.insertProduct(
        new Product("Gadget", "Fancy gadget", new BigDecimal("19.99"), 50));

    Order order1 = dbm.insertOrder(
        new Order(alice.getCustomerId(), new BigDecimal("39.97")));
    dbm.insertOrderItem(new OrderItem(
        order1.getOrderId(), widget.getProductId(), 2,
        widget.getPrice().multiply(new BigDecimal(2))));
    dbm.insertOrderItem(new OrderItem(
        order1.getOrderId(), gadget.getProductId(), 1,
        gadget.getPrice().multiply(new BigDecimal(1))));
    dbm.insertPayment(new Payment(
        order1.getOrderId(), order1.getTotalAmount(), "Completed"));

    dbm.commit();
    out.println("Sample data seeded.\n");
  }

  private static void listCustomers(DatabaseManager dbm) {
    out.printf("%-3s %-10s %-20s %-15s %-12s%n",
        "ID", "Name", "Email", "Address", "Phone");
    out.println("---------------------------------------------------------------");
    for (Customer c : dbm.getCustomers()) {
      out.printf("%-3d %-10s %-20s %-15s %-12s%n",
          c.getCustomerId(), c.getName(), c.getEmail(),
          c.getAddress(), c.getPhone());
    }
    out.println();
  }

  private static void listProducts(DatabaseManager dbm) {
    out.printf("%-3s %-10s %-20s %-8s %-5s%n",
        "ID", "Name", "Description", "Price", "Stock");
    out.println("--------------------------------------------------");
    for (Product p : dbm.getProducts()) {
      out.printf("%-3d %-10s %-20s %8.2f %-5d%n",
          p.getProductId(), p.getName(),
          p.getDescription(), p.getPrice(), p.getStockQuantity());
    }
    out.println();
  }

  private static void listOrders(DatabaseManager dbm) {
    out.printf("%-3s %-3s %-20s %-10s%n",
        "OID", "CID", "Date", "Total");
    out.println("-------------------------------------------------");
    for (Order o : dbm.getOrders()) {
      out.printf("%-3d %-3d %-20s %10.2f%n",
          o.getOrderId(), o.getCustomerId(),
          o.getOrderDate(), o.getTotalAmount());
      for (OrderItem oi : dbm.getOrderItems(o.getOrderId())) {
        out.printf("    Prod %d x%d Sub=%.2f%n",
            oi.getProductId(), oi.getQuantity(), oi.getSubtotal());
      }
    }
    out.println();
  }

  private static void listPayments(DatabaseManager dbm) {
    out.printf("%-3s %-3s %-20s %-8s %-10s%n",
        "PID", "OID", "Date", "Amount", "Status");
    out.println("-----------------------------------------------------");
    for (Payment p : dbm.getPayments()) {
      out.printf("%-3d %-3d %-20s %8.2f %-10s%n",
          p.getPaymentId(), p.getOrderId(),
          p.getPaymentDate(), p.getAmount(), p.getStatus());
    }
    out.println();
  }
}
