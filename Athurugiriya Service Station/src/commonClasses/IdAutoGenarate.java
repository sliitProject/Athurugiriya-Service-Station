/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commonClasses;



import dbConnection.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author cmjd
 */
public class IdAutoGenarate {
    static int currentId;
    
     public static void getNextID1(JTextField txtID,String column, String table) {
        try {
            Connection conn=DBConnection.getDBConnection().getConnection();
            Statement stm=conn.createStatement();
            String sql="SELECT " + column + " FROM " + table + " ORDER BY 1 DESC LIMIT 1";
            ResultSet rst = stm.executeQuery(sql);
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumIntegerDigits(3);
            int current = 0000;
            while (rst.next()) {
                current = rst.getInt(1);
            }


            current++;
            txtID.setText(nf.format(current));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void getNextID2(JLabel txtID, String column, String table) {
        try {
            Connection conn=DBConnection.getDBConnection().getConnection();
            Statement stm=conn.createStatement();
            String sql="SELECT " + column + " FROM " + table + " ORDER BY 1 DESC LIMIT 1";
            ResultSet rst = stm.executeQuery(sql);
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumIntegerDigits(3);
            int current = 0000;
            while (rst.next()) {
                current = rst.getInt(1);
            }

            current++;
            txtID.setText(nf.format(current));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    
}