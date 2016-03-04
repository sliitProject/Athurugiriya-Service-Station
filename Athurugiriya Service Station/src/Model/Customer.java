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
public class Customer {
    
    private int CustID;
    private String CustName;
    private String Address;
    private int mobileNo;
    private int officeNo;
    private String cntctPerson;
    private String email;
    private String nic;
    private String regDate;

    public Customer(){}
    
    public Customer(int ID,String Name,String Address,String cntctPerson,String mail,String NIC,String date,int mobNo,int offiNo){
        this.CustID = ID;
        this.CustName = Name;
        this.Address = Address;
        this.mobileNo = mobNo;
        this.officeNo = offiNo;
        this.cntctPerson=cntctPerson;
        this.email = mail;
        this.nic = NIC;
        this.regDate=date;
    }
    
    public Customer(int ID,String Name,String Address,int mobNo,int offiNo,String cntctPerson,String mail,String NIC){
        this.CustID = ID;
        this.CustName = Name;
        this.Address = Address;
        this.mobileNo = mobNo;
        this.officeNo = offiNo;
        this.cntctPerson=cntctPerson;
        this.email = mail;
        this.nic = NIC;
    }
    
    public Customer(int ID,String Name,String Address,String cnctPersn,String mail,String NIC,String date){
        this.CustID = ID;
        this.CustName = Name;
        this.Address = Address;
        this.cntctPerson=cnctPersn;
        this.email = mail;
        this.nic = NIC;
        this.regDate=date;
    }
    
    
    public Customer(int cid,int mobNo,int offiNo){
        this.CustID=cid;
        this.mobileNo=mobNo;
        this.officeNo=offiNo;
    }
    
    public Customer(int mobNo,int offiNo){
        this.mobileNo=mobNo;
        this.officeNo=offiNo;
    }
    
    public Customer(int cid){
        this.CustID=cid;
    }
    
    public Customer(String name){
        this.CustName=name;
    }
    
    public int getCustID() {
        return CustID;
    }

    public String getCustName() {
        return CustName;
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

    public String getCntctPerson() {
        return cntctPerson;
    }

    public String getEmail() {
        return email;
    }

    public String getNic() {
        return nic;
    }

    public String getRegDate() {
        return regDate;
    }
    
    
}
