/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Vehicle;
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
public class VehicleController {
    
    public static int addVehicle(Vehicle vehicle) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Insert into Vehicle values ('"+vehicle.getCustomerID()+"','"+vehicle.getVehicleNo()+"',"+vehicle.getYear()+",'"+vehicle.getColour()+"','"+vehicle.getModel()+"',"+vehicle.getODOReading()+",'"+vehicle.getChassisNo()+"','"+vehicle.getBorrowDate()+"')";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static int deleteVehicleRecord(int cid,String vehicleNo) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Delete from Vehicle where CustID="+cid+" AND VehicleNo='"+vehicleNo+"'";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static int updateVehicleRecord(Vehicle vehicle) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Update Vehicle set Year='"+vehicle.getYear()+"',Colour='"+vehicle.getColour()+"',Model='"+vehicle.getModel()+"',ODOReading="+vehicle.getODOReading()+",ChassisNo='"+vehicle.getChassisNo()+"',Borrowed_Date='"+vehicle.getBorrowDate()+"' where CustID="+vehicle.getCustomerID()+" AND VehicleNo='"+vehicle.getVehicleNo()+"'";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
     public static ArrayList<Vehicle> searchVehicleDetails(int cid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Vehicle where CustID="+cid+";";
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Vehicle>vehicledetailList=new ArrayList<Vehicle>();
        while(rst.next()){
            Vehicle vehicle=new Vehicle(rst.getString("VehicleNo"),rst.getInt("Year"),rst.getString("Colour"),rst.getString("Model"),rst.getInt("ODOReading"),rst.getString("ChassisNo"),rst.getString("Borrowed_Date"));
            vehicledetailList.add(vehicle);
        }
            return vehicledetailList;
        }
     
     public static ArrayList<Vehicle> searchVehicleDetailsByNIC(String nic)throws ClassNotFoundException,SQLException{//Malsha's Method
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select v.VehicleNo,v.Model from vehicle v,customer c where c.CustID=v.CustID AND c.NIC='"+nic+"'";
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Vehicle>vehicledetailList=new ArrayList<Vehicle>();
        while(rst.next()){
            Vehicle vehicle=new Vehicle(rst.getString("VehicleNo"),rst.getString("Model"));
            vehicledetailList.add(vehicle);
        }
            return vehicledetailList;
        }
     
    public static int CountVehicleDetails(int cid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select Count(VehicleNo) AS 'Count' From Vehicle where CustID="+cid+" ";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            int count=rst.getInt("Count");
            return count;
        }else{
            return 0;
        }
    }
    
}
