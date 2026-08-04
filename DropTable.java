import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DropTable {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilemartdb?useSSL=false&serverTimezone=Asia/Kolkata", "root", "root");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS cart_items");
            System.out.println("cart_items table dropped successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
