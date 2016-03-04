/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Nipuni
 */
public class Supplier {
    private int SupID;
    private String SupName;
    private String Address;
    private int mobileNo;
    private int officeNo;
    private String email;
    private String OrderType;
    private String Catergory;
    
    private int ItemCode;
    private int OrderId;
    private String type;
    private String des;
    private int Qty;
    private String date;
    private double cost;

    public Supplier(){}
    
    public Supplier(int ID,String Name,String Address,int mobNo,int offiNo,String mail,String Catergory){
        this.SupID = ID;
        this.SupName = Name;
        this.Address = Address;
        this.mobileNo = mobNo;
        this.officeNo = offiNo;
        this.email = mail;
        this.Catergory=Catergory;
    }
    
    public Supplier(int ID,String Name,String Address,String mail,String Catergory){
        this.SupID = ID;
        this.SupName = Name;
        this.Address = Address;
        this.email = mail;
        this.Catergory=Catergory;
    }
    
    
    public Supplier(int ID,int mobNo,int offiNo){
        this.SupID = ID;
        this.mobileNo = mobNo;
        this.officeNo = offiNo;
    }
    
     public Supplier(int ID){
        this.SupID = ID;
    }
    
    public Supplier(int mobNo,int offiNo){
        this.mobileNo = mobNo;
        this.officeNo = offiNo;
    }
    
    public Supplier(int oid,int ItemCode,String type,String des,int Qty,double cost,String date,int sid){
        this.OrderId=oid;
        this.ItemCode=ItemCode;
        this.type=type;
        this.des=des;
        this.Qty=Qty;
        this.cost=cost;
        this.date=date;
        this.SupID=sid;
    }
    
        public Supplier(int oid,int ItemCode,String type,String des,int Qty,double cost,String date){
        this.OrderId=oid;
        this.ItemCode=ItemCode;
        this.type=type;
        this.des=des;
        this.Qty=Qty;
        this.cost=cost;
        this.date=date;
    }

    public int getSupID() {
        return SupID;
    }

    public String getSupName() {
        return SupName;
    }

    public String getAddress() {
        return Address;
    }

    public int getMobileNo() {
        return mobileNo;
    }

    public int getOfficeNo() {
        return officeNo;
    }

    public String getEmail() {
        return email;
    }

    public String getCategory() {
        return Catergory;
    }

    public int getItemCode() {
        return ItemCode;
    } 

    public double getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public String getDes() {
        return des;
    }

    public int getOrderId() {
        return OrderId;
    }

    public int getQty() {
        return Qty;
    }

    public String getType() {
        return type;
    }
    
    
}
