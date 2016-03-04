/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Supplier;
import dbConnection.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Nipuni
 */
public class SupplierController {

    public static int addSupplier(Supplier Supplier) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Insert into supplier (supplierID,supplierName,address,email,category) values ("+Supplier.getSupID()+",'"+Supplier.getSupName()+"','"+Supplier.getAddress()+"','"+Supplier.getEmail()+"','"+Supplier.getCategory()+"')";
        int res=stm.executeUpdate(sql);
        return res;
    }
   
    public static int addSupplierContact(Supplier Supplier) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Insert into supplier values ("+Supplier.getSupID()+","+Supplier.getMobileNo()+","+Supplier.getOfficeNo()+");";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static Supplier searchSupplierBysid(int sid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from supplier where supplierID="+sid+";";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Supplier Supplier=new Supplier(rst.getInt("supplierID"),rst.getString("supplierName"),rst.getString("address"),rst.getString("email"),rst.getString("category"));
            return Supplier;
        }else{
            return null;
        }
    }
    
    public static Supplier searchSupplierByName(String name)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Supplier where supplierName='"+name+"';";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Supplier Supplier=new Supplier(rst.getInt("supplierID"),rst.getString("supplierName"),rst.getString("Address"),rst.getString("Email"),rst.getString("Category"));
            return Supplier;
        }else{
            return null;
        }
    }
    
    public static Supplier searchSupplierIDByName(String name)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select supplierID from supplier where supplierName='"+name+"';";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Supplier supplier=new Supplier(rst.getInt("supplierID"));
            return supplier;
        }else{
            return null;
        }
    }

    
    public static Supplier searchSupplierContact(int sid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from supplier where supplierID="+sid+";";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Supplier Supplier=new Supplier(rst.getInt("mobileNo"),rst.getInt("officeNo"));
            return Supplier;
        }else{
            return null;
        }
    }
    
    
    public static int deleteSupplier(int sid) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Delete from Supplier where supplierID='"+sid+"'";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static int updateSupplier(Supplier Supplier) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Update Supplier set supplierName='"+Supplier.getSupName()+"',Address='"+Supplier.getAddress()+"',Email='"+Supplier.getEmail()+"',OrderType='"+Supplier.getType()+"' where supplierID='"+Supplier.getSupID()+"'";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static int updateSupplierContact(Supplier Supplier) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Update Supplier set Mobile="+Supplier.getMobileNo()+",Office="+Supplier.getOfficeNo()+" where supplierID="+Supplier.getSupID()+" ";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static ArrayList<Supplier>getAllSupplier()throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Supplier";
        
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Supplier>SupplierList=new ArrayList<Supplier>();
        while(rst.next()){
            Supplier Supplier=new Supplier(rst.getInt("supplierID"),rst.getString("supplierName"),rst.getString("Address"),rst.getString("Email"),rst.getString("Category"));
            SupplierList.add(Supplier);
        }
        return SupplierList;
        }
    
    public static ArrayList<Supplier>getAllItem(int sid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select orderID,itemCode,type,description,qty,ordercost,date from order_item where supplierId="+sid+"";
        
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Supplier>orderItemList=new ArrayList<Supplier>();
        while(rst.next()){
            Supplier Supplier=new Supplier(rst.getInt("orderID"),rst.getInt("itemCode"),rst.getString("type"),rst.getString("description"),rst.getInt("qty"),rst.getDouble("ordercost"),rst.getString("date"));
            orderItemList.add(Supplier);
        }
        return orderItemList;
        }
}
