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
public class Vehicle {
    private int CustomerID;
    private String vehicleNo;
    private int year;
    private String colour;
    private String model;
    private int ODOReading;
    private String chassisNo;
    private String borrowDate;
    
    public Vehicle(){}
    
    public Vehicle(int CustomerID,String vehicleNo,int year,String colour,String model,int ODOReading,String chassisNo,String borrowDate){
        this.CustomerID=CustomerID;
        this.vehicleNo=vehicleNo;
        this.year=year;
        this.colour=colour;
        this.model=model;
        this.ODOReading=ODOReading;
        this.chassisNo=chassisNo;
        this.borrowDate=borrowDate;
    }
    
    public Vehicle(String vehicleNo,int year,String colour,String model,int ODOReading,String chassisNo,String borrowDate){
        this.vehicleNo=vehicleNo;
        this.year=year;
        this.colour=colour;
        this.model=model;
        this.ODOReading=ODOReading;
        this.chassisNo=chassisNo;
        this.borrowDate=borrowDate;
    }
    
    public Vehicle(String vehicleNo,String model){
        this.vehicleNo=vehicleNo;
        this.model=model;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public int getYear() {
        return year;
    }

    public String getColour() {
        return colour;
    }

    public String getModel() {
        return model;
    }

    public int getODOReading() {
        return ODOReading;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public String getBorrowDate() {
        return borrowDate;
    }
    
    
}
