import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataCheck2 {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilemartdb", "root", "root");
        Statement stmt = conn.createStatement();
        
        System.out.println("--- products with null stock or price ---");
        ResultSet rs2 = stmt.executeQuery("SELECT product_id, stock, price FROM products WHERE stock IS NULL OR price IS NULL");
        while(rs2.next()) {
            System.out.println("prod: " + rs2.getInt("product_id") + " stock is null? " + (rs2.getObject("stock") == null));
        }
        System.out.println("done checking nulls");
        conn.close();
    }
}
