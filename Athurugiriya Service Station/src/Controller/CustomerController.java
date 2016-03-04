/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.Customer;
import Model.Email;
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
public class CustomerController {
    
    public static int addCustomer(Customer customer) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Insert into Customer values ("+customer.getCustID()+",'"+customer.getCustName()+"','"+customer.getAddress()+"','"+customer.getCntctPerson()+"','"+customer.getEmail()+"','"+customer.getNic()+"','"+customer.getRegDate()+"')";
        int res=stm.executeUpdate(sql);
        return res;
    }
   
    public static int addCustomerContact(Customer customer) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Insert into Customer_Contact values ("+customer.getCustID()+","+customer.getMobileNo()+","+customer.getOfficeNo()+");";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static Customer searchCustomerByCID(int cid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Customer where CustID="+cid+";";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Customer customer=new Customer(rst.getInt("CustID"),rst.getString("Name"),rst.getString("Address"),rst.getString("Contact_Person"),rst.getString("Email"),rst.getString("NIC"),rst.getString("Registered_Date"));
            return customer;
        }else{
            return null;
        }
    }
    
    public static Customer searchCustomerByName(String name)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Customer where Name='"+name+"';";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Customer customer=new Customer(rst.getInt("CustID"),rst.getString("Name"),rst.getString("Address"),rst.getString("Contact_Person"),rst.getString("Email"),rst.getString("NIC"),rst.getString("Registered_Date"));
            return customer;
        }else{
            return null;
        }
    }
    
    public static Customer searchCustomerIDByName(String name)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select CustID from Customer where Name='"+name+"';";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Customer customer=new Customer(rst.getInt("CustID"));
            return customer;
        }else{
            return null;
        }
    }
    
    public static Customer searchCustomerIDByNIC(String nic)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select CustID from Customer where NIC='"+nic+"';";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Customer customer=new Customer(rst.getInt("CustID"));
            return customer;
        }else{
            return null;
        }
    }
    
    public static Customer searchCustomerByNIC(String nic)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Customer where NIC='"+nic+"';";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Customer customer=new Customer(rst.getInt("CustID"),rst.getString("Name"),rst.getString("Address"),rst.getString("Contact_Person"),rst.getString("Email"),rst.getString("NIC"),rst.getString("Registered_Date"));
            return customer;
        }else{
            return null;
        }
    }
    
    public static Customer searchCustomerContact(int cid)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Customer_Contact where CustID="+cid+";";
        ResultSet rst=stm.executeQuery(sql);
        
        if(rst.next()){
            Customer customer=new Customer(rst.getInt("Mobile"),rst.getInt("Office"));
            return customer;
        }else{
            return null;
        }
    }
    
    
    public static int deleteCustomer(int cid) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Delete from Customer where CustID="+cid+"";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static int updateCustomer(Customer customer) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Update Customer set Name='"+customer.getCustName()+"',Address='"+customer.getAddress()+"',Email='"+customer.getEmail()+"',NIC='"+customer.getNic()+"',Registered_Date='"+customer.getRegDate()+"' where CustID="+customer.getCustID()+"";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static int updateCustomerContact(Customer customer) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Update Customer_Contact set Mobile='"+customer.getMobileNo()+"',Office='"+customer.getOfficeNo()+"' where CustID='"+customer.getCustID()+"'";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
    public static ArrayList<Customer>getAllCustomer()throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select * from Customer";
        
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Customer>customerList=new ArrayList<Customer>();
        while(rst.next()){
            Customer customer=new Customer(rst.getInt("CustID"),rst.getString("Name"),rst.getString("Address"),rst.getString("Contact_Person"),rst.getString("Email"),rst.getString("NIC"),rst.getString("Registered_Date"));
            customerList.add(customer);
        }
        return customerList;
        }
    
    public static ArrayList<Customer>getAllCustomerContacts()throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select c.CustID,Name,Address,Contact_Person,Email,NIC,Registered_Date,Mobile,Office from Customer c,Customer_Contact cc where c.CustID=cc.CustID";
        
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Customer>customerContactList=new ArrayList<Customer>();
        while(rst.next()){
            Customer customer=new Customer(rst.getInt("CustID"),rst.getString("Name"),rst.getString("Address"),rst.getString("Contact_Person"),rst.getString("Email"),rst.getString("NIC"),rst.getString("Registered_Date"),rst.getInt("Mobile"),rst.getInt("Office"));
            customerContactList.add(customer);
        }
        return customerContactList;
        }
    
    public static ArrayList<Customer> getClientsSortByName(String name)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select Name from Customer group by '"+name+"';";
        
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Customer> nameList=new ArrayList<Customer>();
        
        while(rst.next()){
            Customer custName=new Customer(rst.getString("Name"));
            nameList.add(custName);
        }
        
        return nameList;
    }
    
    public static ArrayList<Customer> getClientsSortById(int id)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select Name from CustID group by '"+id+"';";
        
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Customer> nameList=new ArrayList<Customer>();
        
        while(rst.next()){
            Customer custName=new Customer(rst.getInt("CustID"));
            nameList.add(custName);
        }
        
        return nameList;
    }
    
    public static ArrayList<Email> searchAppointments(String date)throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Select c.CustID,c.Name,c.Email,a.appointmentID,a.date,a.Time,a.TimeStatus,a.portNo,a.emailNotification From Customer c,Appointment a where c.CustID=a.CustID AND a.date='"+date+"'";
        ResultSet rst=stm.executeQuery(sql);
        
        ArrayList<Email> appointmentList=new ArrayList<Email>();
        
        while(rst.next()){
            Email em=new Email(rst.getInt("c.CustID"),rst.getString("c.Name"),rst.getString("c.Email"),rst.getInt("a.appointmentID"),rst.getString("a.date"),rst.getString("a.Time"),rst.getString("a.TimeStatus"),rst.getInt("a.portNo"),rst.getString("a.emailNotification"));
            appointmentList.add(em);
        }
            return appointmentList;
    }
    
    public static int updateAppointmentNotification(Email email) throws ClassNotFoundException,SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        Statement stm=conn.createStatement();
        String sql="Update Appointment set emailNotification='"+email.getNotification()+"' where CustID="+email.getAppID()+"; ";
        int res=stm.executeUpdate(sql);
        return res;
    }
    
}
