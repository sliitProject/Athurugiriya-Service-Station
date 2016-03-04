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
public class Email {
    private int custID;
    private String name;
    private String emailAddress;
    private int appID;
    private String appDate;
    private String appTime;
    private String timeStatus;
    private int portNo;
    private String notification;
    
    public Email(){}
    
    public Email(int cid,String name,String email,int appid,String date,String time,String status,int port,String notifctn){
        this.custID=cid;
        this.name=name;
        this.emailAddress=email;
        this.appID=appid;
        this.appDate=date;
        this.appTime=time;
        this.timeStatus=status;
        this.portNo=port;
        this.notification=notifctn;
    }

    public String getAppDate() {
        return appDate;
    }

    public int getAppID() {
        return appID;
    }

    public String getAppTime() {
        return appTime;
    }

    public int getCustID() {
        return custID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public int getPortNo() {
        return portNo;
    }

    public String getTimeStatus() {
        return timeStatus;
    }

    public String getNotification() {
        return notification;
    }
    
    
    
}
