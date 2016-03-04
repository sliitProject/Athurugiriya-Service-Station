/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author D2
 */
public class Orders {
    private int orderID;
    private int itemCode;
    private String description;
    private String type;
    private int qty;
    private int supplierID;
    private double orderCost;
    private String date;
    
    public Orders(){}
    
    public Orders(int orderID, int itemCode, String type, String description, int qty, double orderCost, String date, int supplierID){
        this.orderID=orderID;
        this.itemCode=itemCode;
        this.type=type;
        this.description=description;
        this.qty=qty;
        this.orderCost=orderCost;
        this.date=date;
        this.supplierID=supplierID;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getItemCode() {
        return itemCode;
    }

    public int getQty() {
        return qty;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public String getType() {
        return type;
    }
    
    
}
