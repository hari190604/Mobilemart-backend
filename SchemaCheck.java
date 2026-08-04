import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class SchemaCheck {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilemartdb", "root", "root");
        Statement stmt = conn.createStatement();
        
        System.out.println("--- addresses table ---");
        ResultSet rs1 = stmt.executeQuery("SHOW CREATE TABLE addresses");
        while(rs1.next()) {
            System.out.println(rs1.getString(2));
        }

        conn.close();
    }
}
