

package connections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Access {
    public static void setData(String query) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connect.getConnection();
        Statement stm = con.createStatement();
        stm.executeUpdate(query);
        
    }
    
    public static ResultSet getData(String query) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connect.getConnection();
        Statement stm = con.createStatement();
        ResultSet rst = stm.executeQuery(query);
        return rst;
    }
}
