import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataCheck {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilemartdb", "root", "root");
        Statement stmt = conn.createStatement();
        
        System.out.println("--- cart_items for user ---");
        ResultSet rs1 = stmt.executeQuery("SELECT * FROM cart_items");
        while(rs1.next()) {
            System.out.println("cart item: " + rs1.getInt("id") + " product: " + rs1.getInt("product_id") + " qty: " + rs1.getInt("quantity"));
        }

        System.out.println("--- products stock ---");
        ResultSet rs2 = stmt.executeQuery("SELECT product_id, name, stock FROM products");
        while(rs2.next()) {
            System.out.println("prod: " + rs2.getInt("product_id") + " stock: " + rs2.getInt("stock"));
        }
        
        conn.close();
    }
}
