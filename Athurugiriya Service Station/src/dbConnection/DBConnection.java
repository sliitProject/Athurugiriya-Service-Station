/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Nipuni
 */
public class DBConnection {
    private static DBConnection dBConnection;
    private Connection conn;
    
    private DBConnection()throws ClassNotFoundException,SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/voltage","root","");
    }
    public static DBConnection getDBConnection()throws ClassNotFoundException,SQLException{
        if(dBConnection==null){
            dBConnection=new DBConnection();
        }
        return dBConnection;
        }
    public Connection getConnection()throws ClassNotFoundException,SQLException{
        return conn;
    }
    
}
