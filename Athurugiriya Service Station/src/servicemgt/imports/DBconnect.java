/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemgt.imports;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Hashu_pc
 */
public class DBconnect {
    public static Connection connect()
    {
        Connection conx = null;
        
        try{
        
        Class.forName("com.mysql.jdbc.Driver");
        conx = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/voltage","root","");
        
        }
        catch(Exception e)
        {
            System.out.println(e);
                
        }
        return conx;
    
    
    }
    
}
