import java.beans.Statement;
import java.sql.*;


public class Main {
    static final String db_url = "jdbc:mysql://127.0.0.1:3306/out_the_oven";
    static final String user = "root";
    static final String pass = "";

    public static void main(String[] args) {

        try(Connection conn = DriverManager.getConnection(db_url, user, pass);
            java.sql.Statement stmt = conn.createStatement();
        ){
            
        } catch (SQLException e){
            e.printStackTrace();
        }
            
    }
}