/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Orders;
import dbConnection.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author D2
 */
public class OrderController {
    
    public static int addOrder(Orders order) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Insert into order_item (orderID,itemCode,type,description,qty,odercost,date,supplierID) values ("+order.getOrderID()+","+order.getItemCode()+",'"+order.getType()+"','"+order.getDescription()+"',"+order.getQty()+","+order.getOrderCost()+","+order.getSupplierID()+")";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static Orders searchOrderBySupplierID(int sid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from order_item where supplierID="+sid+";";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Orders order=new Orders(rst.getInt("orderID"), rst.getInt("itemCode"), rst.getString("type"), rst.getString("description"), rst.getInt("qty"), rst.getDouble("ordercost"), rst.getString("date"), rst.getInt("supplierID"));
            return order;
        }else{
            return null;
        }
    }

    
    public static int deleteOrders(int oid) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Delete from order_item where orderID='"+oid+"'";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static int updateOrders(Orders order) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Update order_item set orderID='"+order.getOrderID()+"',itenCode='"+order.getItemCode()+"',type='"+order.getType()+"',description='"+order.getDescription()+"',qty='"+order.getQty()+"',ordercost='"+order.getOrderCost()+"',date='"+order.getDate()+"',supplierID='"+order.getSupplierID()+"' where orderID='"+order.getOrderID()+"'";
        int res=stm.executeUpdate(sql);
        return res;
    }
/*
    public static ArrayList<Supplier>getAllSupplier()throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Supplier";
        
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Supplier>SupplierList=new ArrayList<Supplier>();
        while(rst.next()){
            Supplier Supplier=new Supplier(rst.getInt("supplierID"),rst.getString("supplierName"),rst.getString("Address"),rst.getString("Email"),rst.getString("OrderType"),rst.getString("Category"));
            SupplierList.add(Supplier);
        }
        return SupplierList;
        }
    
    public static ArrayList<Orders>getAllItem(int sid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select orderID,itemCode,type,description,qty,ordercost,date from order_item where supplierId="+sid+"";
        
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Orders>orderItemList=new ArrayList<Supplier>();
        while(rst.next()){
            Supplier Supplier=new Supplier(rst.getInt("orderID"),rst.getInt("itemCode"),rst.getString("type"),rst.getString("description"),rst.getInt("qty"),rst.getDouble("ordercost"),rst.getString("date"));
            orderItemList.add(Supplier);
        }
        return orderItemList;
        }
    */
}
