/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemgt;

import static commonClasses.Validation.validateInt;
import inventory.ims;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import servicemgt.imports.DBconnect;



public class JobCard extends javax.swing.JFrame {
    
   // public void extendFrame() {
        
    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //int height = screenSize.height;
    //int width = screenSize.width;
    //this.getContentPane().setPreferredSize(new Dimension(width, height));
    //this.pack();
//}
  
     Connection  conx = DBconnect.connect();
     PreparedStatement pst = null;
     ResultSet rs = null;
     public static String division="";
     public static int appointmentID;
//     System.out.println(appointmentID);
   
    float ODOReading;
    //int appointmentID;
    
    public void extendFrame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        this.getContentPane().setPreferredSize(new Dimension(width,height));
        this.pack();
    }
    
    public JobCard() {
         
             initComponents();
             extendFrame();
             
              mythread1 t = new mythread1();
                t.start();
             loadItemTable();
             AllloadJobcardTable();
             btnCalculateTotal.setEnabled(false);
             btnGenerateRpt.setEnabled(false);
           
             SimpleDateFormat formatter = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());
             Date d = new Date();
             lblDate.setText(formatter.format(d));
             lblReDate.setText(formatter.format(d));
             lblReDate.setText(formatter.format(d));
                   
                        
    }//jobcard overridden constructor
    
  
    
    
     public class mythread1 extends Thread{
    
        public void run(){
        
            while(true){

                
            setDate();
            
               
            try {
                
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }}
    
    }
    
    
    
    
    
        private void setDate(){
       // SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd",Locale.getDefault());
       // lg_date.setText(dte);
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        java.util.Date today = Calendar.getInstance().getTime();
        String dateAsString = dateFormatter.format(today);
        String timeAsString = timeFormatter.format(today);
        lgTime.setText(timeAsString);
        lgDate.setText(dateAsString);
        
        java.util.Date now = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEEEE", Locale.US); 
        String asWeek = dateFormat.format(now);
        stringDay.setText(asWeek);
        //lg_date.setText(dateAsString);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void loadData(){
    
         try {
             System.out.println("wadawada");
             
            Statement sta = null;        
            sta=conx.createStatement();
            String q1 = "SELECT custName,VehiRegNO,contactNo FROM appointment where appointmentID = '"+appointmentID+"'  ";
            ResultSet res=sta.executeQuery(q1);
             
             //rs = pst.executeQuery();
             
             String custName="";
             String vehicleNo="";
             String contactNo="";
             while(res.next())
             {
                 System.out.println("wadawadaaaa");
                 custName = res.getString("custName");
                 vehicleNo =  res.getString("VehiRegNO");
                 contactNo =  res.getString("contactNo");
                
             }//while
             txtCustomerName.setText(custName);
             txtVehicleNo.setText(vehicleNo);
             txtTelephoneNo.setText(contactNo);
             RcustName.setText(custName);
           
         } //end load data
         catch (SQLException ex) {
             Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
         }
                     
   
}
    
    
    public JobCard(int appid) {
        initComponents();
     // extendFrame();
        mythread1 t = new mythread1();
        t.start();
        loadItemTable();
        AllloadJobcardTable();
        appointmentID=appid;
        System.out.println(appointmentID);
        btnCalculateTotal.setEnabled(false);
        btnGenerateRpt.setEnabled(false);
        loadData();
        
        
        SimpleDateFormat formatter = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());
        Date d = new Date();
        lblDate.setText(formatter.format(d));
        lblReDate.setText(formatter.format(d));
        lblReDate.setText(formatter.format(d));
        temp.setText(String.valueOf(appid));
      
       
      
    }
    

  public void loadJobcardTable()
    {
        
        try {
             int appID = Integer.parseInt(temp.getText());
             System.out.println(appID);
            String sql = "SELECT * FROM jobcard where appointmentID = '"+appID+"'";
                   
            pst = conx.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            tblJobcardView.setAutoCreateColumnsFromModel(false);
            tblJobcardView.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }// method to load the Jobcard table
  
   public void AllloadJobcardTable()
    {
        try {
             
            String sql = "SELECT * FROM jobcard";
                   
            pst = conx.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            tblJobcardView.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }// method to load all the jobcards

    
    public void loadServiceTable()
    {
        try {
            int appID = Integer.parseInt(temp.getText());
            
          
            String sql = "SELECT s.serviceName,s.service_cost "
                    + "FROM service s, serv_appoint sa "
                    + "where sa.appointmentID = '"+appID+"' AND sa.serviceID = s.serviceID ";
            pst = conx.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            tableRequestedServices.setAutoCreateColumnsFromModel(false);
            tableRequestedServices.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }// method to load the Service table
    
    public void loadServiceCart()
    {
   
            int ro = tblJobcardView.getRowCount();
            String j = tblJobcardView.getValueAt(ro-1, 0).toString();
            int job = Integer.parseInt(j);
            
            tblServiceCart.setAutoCreateColumnsFromModel(false);
            DefaultTableModel model = (DefaultTableModel) tblServiceCart.getModel();
        //   model.addRow(new Object[]{id, unitprice,qty});
            model.removeRow(rows);
           
            
        FinalArray.add(Temp); 
    
    } // Method to load the service cart
    
    
       public void loadItemTable()
    {
        try {
          
            String sql = "SELECT itemCode,itemName,category,stock,unitPrice FROM item ";
            
            pst = conx.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            tableItems.setAutoCreateColumnsFromModel(false);
            tableItems.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }// method to load the Item table 
   
     
     public void addJobCard(float o , int a){
          
  
            this.ODOReading = o;
        //    this.appointmentID = a;
            
            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
            //Date d = new Date();
            // lblDate.setText(formatter.format(d));
             // JOptionPane.showMessageDialog(null, jdate);
          
        conx = DBconnect.connect();
        if(txtODOReading.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please enter the ODO Reading");
            
        }//closeifclause
        else
        {
        try {
            
            String datee = lblDate.getText();
        
            
            String query1 = "INSERT into jobcard (date,ODOReading,appointmentID) values('"+datee+"','"+ODOReading+"','"+appointmentID+"')";
            pst = conx.prepareStatement(query1);
            pst.execute();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
        }
         JOptionPane.showMessageDialog(null, "The record has been successfully entered");
         
          loadServiceTable();
          loadJobcardTable();
        }//close elsecluase
        
     }//ENDaddJobCard
     
       
   public void editJobcard(int jobcardID) throws SQLException{
       
        int odo = Integer.parseInt(txtODOReading.getText());
    
       String query4=" Update jobcard set ODOReading = '"+odo+"' where jobCardID = '"+jobcardID+"'";
       
       pst=conx.prepareStatement(query4);
       pst.execute();
       JOptionPane.showMessageDialog(null, "The record has been successfully edited");
       AllloadJobcardTable();
      
       
   }
    public static ArrayList<ArrayList<Double>> FinalArray = new ArrayList<ArrayList<Double>>();
    
    public static ArrayList<Double> Temp =  new ArrayList<Double>();
    
 
 
    public void addToServiceCart()
    {
        conx = DBconnect.connect();
        int row = tableItems.getSelectedRow();

        String id = tableItems.getValueAt(row, 0).toString();
        String unitprice = tableItems.getValueAt(row, 4).toString();
        String qty = txtQty.getText();
        
        
        if(qty.isEmpty())
        
         JOptionPane.showMessageDialog(null, "Please enter the required quantity");
        else
        {
               DefaultTableModel model = (DefaultTableModel) tblServiceCart.getModel();
               boolean ok = false;
               int i=0;
               int chkqty=0;
               int count = model.getRowCount();
               int b= Integer.parseInt(id);
               while((ok!=true) && (i<count))
               {
                    
                   int chkid = Integer.parseInt(model.getValueAt(i, 0).toString());
                 
                   if(b==chkid)
                   {
                    
                       chkqty = Integer.parseInt(model.getValueAt(i, 2).toString());
                      
                       chkqty=chkqty+Integer.parseInt(qty);
                       model.removeRow(i);
                       
                      
                        ok=true;
                   }
                  
                   i++;
               }//while
               
                if(ok==true)
                {
                    
                    qty = String.valueOf(chkqty);
                          
                    model.addRow(new Object[]{id, unitprice,qty});
                  
                }
                else
                {
                    
                    model.addRow(new Object[]{id, unitprice,qty});
                    System.out.println(id);
                    System.out.println(unitprice);
                    System.out.println(qty);
                } 
                    double id2 = Double.parseDouble(id);
                    double unitprice2 = Double.parseDouble(unitprice);
                    double qty2 = Double.parseDouble(qty);
                    int r = tblServiceCart.getRowCount();
               
                   // System.out.println(id2);
                    
//                    Temp.add(id2);
//                    Temp.add(unitprice2);
//                    Temp.add(qty2);
                
               txtQty.setText(null);
        
        }
    }
    
     
public int takeQty(int id) throws SQLException{

    String sql = "select stock from item where itemCode='"+id+"'";
    int ans=0;
     Statement st= null;
     Statement sta = conx.createStatement();
     ResultSet res = sta.executeQuery(sql);
     
     while(res.next()){
     
         ans = res.getInt("stock");
     }
    
    
    
    
    
    
    
    
    return ans;
}
    
    
    
    public void confirmCart()
    {
        int ro = tblJobcardView.getRowCount();
        String j = tblJobcardView.getValueAt(ro-1, 0).toString();
        int job = Integer.parseInt(j);
         
        FinalArray.add(Temp); 
              
        double id=0,qty=0,up=0;
        int rem=0,rem2=0,strtval=0,endval=0,midval=0;
        strtval=0;
        midval=1;
        endval=2;
        
        for(ArrayList<Double> r : FinalArray )
        { 
            rem=r.size()/3;
            rem2=r.size();
           
            while(rem>=0)
            {
              
                
               while(strtval<endval && endval<=rem2)
                {
                   try {
                       id=r.get(strtval);
                       up=r.get(midval);
                       qty=r.get(endval);
                       System.out.println(id);
                        System.out.println(up);
                        System.out.println(qty);
                       
                       
                       String Sql = "INSERT INTO serv_job (jobcardID,itemCode,qty,unitPrice) values ('"+job+"','"+id+"','"+qty+"','"+up+"')";
                       pst = conx.prepareStatement(Sql);
                       pst.execute();
                       
                       int stk; 
                       stk = takeQty((int)id);
                       
                       int stk1=stk-(int)qty;
                       
                       String invent = "update item set stock='"+stk1+"' where itemCode='"+id+"'";
                       pst= conx.prepareStatement(invent);
                       pst.execute();
                       
                       strtval+=3;
                       midval+=3;
                       endval+=3;
                       
                       
                   } catch (SQLException ex) {
                       Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
                   }
                }
               rem--;
            }
        }
        FinalArray.clear();
        Temp.clear();
             
    
    }
    
    
  
    public void deleteFromServiceCart(int ic,double up,int qty)
       
    {
     
        int selectiondel=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this record?");
        
       
        if(selectiondel==0)
        {
           DefaultTableModel model = (DefaultTableModel) tblServiceCart.getModel();
            
            model.removeRow(rows);
       
//            Temp.remove(up);
//            Temp.remove(qty);
//            Temp.remove(ic); 
            
            //FinalArray.add(Temp);
            JOptionPane.showMessageDialog(null, "The record has been successfully deleted");
           
            
        }
    
   //}
    }
    float totalprice;
    public void calulateTotal()
    {
        DefaultTableModel model = (DefaultTableModel) tblServiceCart.getModel();
        int rowCount = tblServiceCart.getRowCount();
        
       
        int rowID, Qty = 0;
        totalprice = 0;
        float unitp = 0;
        
        
        for(int i=0;i<=rowCount-1;i++)
        {
           
            unitp = Float.parseFloat(model.getValueAt(i, 1 ).toString());
            Qty = Integer.parseInt(model.getValueAt(i, 2).toString());
           
          
            totalprice = totalprice + (unitp*Qty);
            
         
        }
           lblItemCost.setText(String.valueOf(totalprice));
           System.out.println(totalprice);
           
    }
    
    float answer;
    public void createFinalReceipt()
    {
         try {
             float serviceCost=0;
             answer=0;
             int r2 = tableRequestedServices.getRowCount();
                        
             for(int i=0 ; i<=r2-1 ; i++)
             {
                 
                 serviceCost = Float.parseFloat(tableRequestedServices.getValueAt(i, 1 ).toString());
                 answer = answer + serviceCost;
                 
             }
             
            
             lblServiceCharges.setText(String.valueOf(answer));
             lblTotalPrice.setText(String.valueOf(totalprice));
             
             int ro = tblJobcardView.getRowCount();
             String j = tblJobcardView.getValueAt(ro-1, 0).toString();
             int job = Integer.parseInt(j);
            
             
             String sql = "SELECT   s.itemCode,s.unitPrice,s.qty,i.itemName from serv_job s, item i where jobcardID = '"+job+"'AND s.itemCode =i.itemCode  ";
             pst = conx.prepareStatement(sql);
             ResultSet rs = pst.executeQuery();
             
             tblFinalReceipt.setModel(DbUtils.resultSetToTableModel(rs));
             
             String ddd = lblReDate.getText();
             
            //adds the selected items to the receipt 
             String sql3 = "INSERT into receipt (job_cost,item_cost,jobCardID,date) values ('"+answer+"','"+totalprice+"','"+job+"','"+ddd+"')";
             pst = conx.prepareStatement(sql3);
             pst.execute();
         
             String sql2 = "SELECT receiptID from receipt where jobCardID = '"+job+"'";
            // pst = conx.prepareStatement(sql2);
            // pst.execute();
            
             Statement st = null;
             Statement sta = conx.createStatement();
             
             ResultSet res= sta.executeQuery(sql2);
            String id1=" ";
             
             while(res.next()){
                 
                 id1=res.getString("receiptID");
            }
            
             lblReciept.setText(id1);
             
         } catch (SQLException ex) {
             Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
         }
    
    }
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblJobcardID = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        txtODOReading = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCreate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnviewAll = new javax.swing.JButton();
        temp = new javax.swing.JLabel();
        txtVehicleNo = new javax.swing.JLabel();
        txtTelephoneNo = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblJobcardView = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        startDate = new com.toedter.calendar.JDateChooser();
        endDate = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableItems = new javax.swing.JTable();
        lblAdd = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRequestedServices = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblServiceCart = new javax.swing.JTable();
        lblServiceCart = new javax.swing.JLabel();
        btnCalculateTotal = new javax.swing.JButton();
        lblDelete = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        btnGenerateRpt = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblItemCost = new javax.swing.JLabel();
        lblServiceCharges = new javax.swing.JLabel();
        lblReceipt = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblFinalReceipt = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblReDate = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        lblReciept = new javax.swing.JLabel();
        RcustName = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        stringDay = new javax.swing.JLabel();
        lgDate = new javax.swing.JLabel();
        lgTime = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Vehicle No");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Telephone No");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Job Card ID");

        lblJobcardID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblJobcardID.setText("ID");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Appointment ID");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Job Card");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Date");

        lblDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDate.setText("ID");

        txtODOReading.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtODOReading.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtODOReadingKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("ODO Reading");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Customer Name");

        btnCreate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_add_big.png"))); // NOI18N
        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_previous_256.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnviewAll.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnviewAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_quickview-icon.png"))); // NOI18N
        btnviewAll.setText("View All");
        btnviewAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnviewAllActionPerformed(evt);
            }
        });

        temp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        temp.setText("jLabel27");

        txtVehicleNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtVehicleNo.setText("jLabel6");

        txtTelephoneNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTelephoneNo.setText("jLabel6");

        txtCustomerName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCustomerName.setText("jLabel6");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(101, 101, 101)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelephoneNo)
                                    .addComponent(txtVehicleNo)
                                    .addComponent(lblJobcardID, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(39, 39, 39)
                                        .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(temp))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtODOReading, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerName))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCreate, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(btnReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnviewAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addComponent(btnCreate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addGap(18, 18, 18)
                        .addComponent(btnviewAll))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDate))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtODOReading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(lblJobcardID)
                                .addComponent(jLabel4)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(temp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtVehicleNo)
                                    .addComponent(jLabel11)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtCustomerName))))))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTelephoneNo))
                .addGap(43, 43, 43))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));

        btnEdit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit_png.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Search_png.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblJobcardView.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblJobcardView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblJobcardView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblJobcardViewMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblJobcardView);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Please select a row from the table to edit");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("Search Jobcards");

        dateChooser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_reports.png"))); // NOI18N
        jButton2.setText("Basic Report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        startDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        endDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Start Date");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("End Date");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_reports.png"))); // NOI18N
        jButton3.setText("Parameterized Report");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnSearch))
                            .addComponent(jLabel1))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton3)
                            .addComponent(btnEdit))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch))
                .addGap(41, 41, 41)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(29, 29, 29)
                        .addComponent(btnEdit)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Create/Edit Job Card", jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Please select the items used and click Add button");

        tableItems.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tableItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item Code", "Item Name", "Category", "Stock", "Unit Price"
            }
        ));
        tableItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableItemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableItems);

        lblAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_add_big.png"))); // NOI18N
        lblAdd.setText("Add");
        lblAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblAddActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Quantity");

        txtQty.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel13)
                        .addGap(39, 39, 39)
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(lblAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("The Requested Services");

        tableRequestedServices.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tableRequestedServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Service Name", "Service Cost"
            }
        ));
        jScrollPane3.setViewportView(tableRequestedServices);

        tblServiceCart.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblServiceCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Unit Price", "Quantity"
            }
        ));
        tblServiceCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblServiceCartMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblServiceCart);

        lblServiceCart.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblServiceCart.setText("Service Cart");

        btnCalculateTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCalculateTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_calculator_green.png"))); // NOI18N
        btnCalculateTotal.setText("Calculate Total");
        btnCalculateTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculateTotalActionPerformed(evt);
            }
        });

        lblDelete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_garbage_delete.png"))); // NOI18N
        lblDelete.setText("Delete");
        lblDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDeleteMouseClicked(evt);
            }
        });
        lblDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblDeleteActionPerformed(evt);
            }
        });

        btnConfirm.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnConfirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_3floppy_mount.png"))); // NOI18N
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnGenerateRpt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnGenerateRpt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_reports.png"))); // NOI18N
        btnGenerateRpt.setText("Generate Receipt");
        btnGenerateRpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateRptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCalculateTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGenerateRpt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(85, 85, 85))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblServiceCart)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblServiceCart))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCalculateTotal)
                        .addGap(18, 18, 18)
                        .addComponent(btnGenerateRpt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Total Service Charges");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setText("Total Item Charges");

        lblItemCost.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lblServiceCharges.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel25)
                        .addGap(117, 117, 117)
                        .addComponent(lblServiceCharges, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel28)
                        .addGap(133, 133, 133)
                        .addComponent(lblItemCost, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(lblItemCost, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblServiceCharges, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add to Service Cart", jPanel3);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Service Receipt");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Date");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Customer Name");

        tblFinalReceipt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblFinalReceipt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item Code", "Description", "Unit Price", "Quantity"
            }
        ));
        jScrollPane6.setViewportView(tblFinalReceipt);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Electro Automotives (PVT) Ltd.");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel18.setText("57, Cotta Road, Colombo 08");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel19.setText("Tel: +94 117727537");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel20.setText("Fax:+94 772753625");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel21.setText("email: info@sparkev.lk");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel22.setText("Web: www.sparkev.lk");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Receipt No");

        lblReDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblReDate.setText("date");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("Total Price");

        lblTotalPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lblReciept.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblReciept.setText("jLabel27");

        RcustName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        RcustName.setText("jLabel6");

        javax.swing.GroupLayout lblReceiptLayout = new javax.swing.GroupLayout(lblReceipt);
        lblReceipt.setLayout(lblReceiptLayout);
        lblReceiptLayout.setHorizontalGroup(
            lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblReceiptLayout.createSequentialGroup()
                .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblReceiptLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel17))
                    .addGroup(lblReceiptLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel14))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(lblReceiptLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblReceiptLayout.createSequentialGroup()
                        .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(lblReceiptLayout.createSequentialGroup()
                                .addComponent(lblReDate)
                                .addGap(197, 197, 197)
                                .addComponent(jLabel23)
                                .addGap(129, 129, 129)
                                .addComponent(lblReciept))
                            .addComponent(RcustName))
                        .addGap(569, 569, 569))
                    .addGroup(lblReceiptLayout.createSequentialGroup()
                        .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(lblReceiptLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(61, 61, 61)
                                .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        lblReceiptLayout.setVerticalGroup(
            lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblReceiptLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addGap(9, 9, 9)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addGap(27, 27, 27)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel23)
                    .addComponent(lblReDate)
                    .addComponent(lblReciept))
                .addGap(27, 27, 27)
                .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(RcustName))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(lblReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Final Reciept", lblReceipt);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel31.setText("Service Management");

        stringDay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        stringDay.setText("Date");

        lgDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lgDate.setText("jLabel4");

        lgTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lgTime.setText("jLabel4");

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_home.png"))); // NOI18N
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel19.setBackground(new java.awt.Color(102, 211, 37));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lgDate, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stringDay, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lgTime, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)))
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lgDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(stringDay, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lgTime)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel31)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
      
       // String dat = txtDate.getText();
        String ODOReadin = txtODOReading.getText();
        //String appointmentI = temp.getText();
        
        
        float ODOReading = Float.parseFloat(ODOReadin);
        System.out.print(ODOReading);
       // int appointmentID = appointmentID;
        
        addJobCard(ODOReading,appointmentID);
        System.out.println(appointmentID);
        loadServiceTable();
       
         
        
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        
        lblJobcardID.setText("ID");
        temp.setText(null);
        txtVehicleNo.setText(null);
        txtTelephoneNo.setText(null);
        txtODOReading.setText(null);
        txtCustomerName.setText(null);
        //txtAreaAddress.setText(null);
      
    }//GEN-LAST:event_btnResetActionPerformed

    private void tblJobcardViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblJobcardViewMouseClicked
       
        int row = tblJobcardView.getSelectedRow();
        String id = tblJobcardView.getValueAt(row, 0).toString();
        String odo = tblJobcardView.getValueAt(row, 2).toString();
        
        lblJobcardID.setText(id);
        txtODOReading.setText(odo);
           
      //  loadServiceTable();  //set the appID
       
    }//GEN-LAST:event_tblJobcardViewMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
         
        String ODOReadin = txtODOReading.getText();
        String jobcar = lblJobcardID.getText();

         
         if(ODOReadin.isEmpty())
        {
             JOptionPane.showMessageDialog(null, "please choose a record from the table to edit");
        }
        
        else{
             
            int selection=JOptionPane.showConfirmDialog(null,"Are you sure you want to update this record?");
        
             if(selection==0)
        {
            
              float ODOReading = Float.parseFloat(ODOReadin);
              int jobcardID = Integer.parseInt(jobcar);
           
              try {
                   editJobcard(jobcardID);
                } catch (SQLException ex) {
                    Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
                }
              
        }
        
        }
        
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnviewAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnviewAllActionPerformed
       AllloadJobcardTable();
    }//GEN-LAST:event_btnviewAllActionPerformed

    private void tableItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableItemsMouseClicked
        int row = tableItems.getSelectedRow();

        String itemname = tableItems.getValueAt(row, 1).toString();
       
    }//GEN-LAST:event_tableItemsMouseClicked

    private void lblAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblAddActionPerformed
         addToServiceCart();

// TODO add your handling code here:
    }//GEN-LAST:event_lblAddActionPerformed

    private void btnCalculateTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculateTotalActionPerformed
        btnGenerateRpt.setEnabled(true);
        calulateTotal();
        createFinalReceipt();
        
    }//GEN-LAST:event_btnCalculateTotalActionPerformed

    private void lblDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDeleteMouseClicked

    private void lblDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDeleteActionPerformed

       // System.out.println("delete row"+rows);
        
         deleteFromServiceCart(ic,up,qty);
         //loadServiceCart();
        
      
    }//GEN-LAST:event_lblDeleteActionPerformed
           // String serv_jobID;
            int ic;
            double up;
            int qty;
            int rows;
    private void tblServiceCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblServiceCartMouseClicked
             
      //   try {
            
            conx = DBconnect.connect();
            DefaultTableModel model = (DefaultTableModel) tblServiceCart.getModel();
            rows = tblServiceCart.getSelectedRow();
             
           
            
            ic = Integer.parseInt(model.getValueAt(rows, 0 ).toString());
             up = Double.parseDouble(model.getValueAt(rows, 1 ).toString());
            qty = Integer.parseInt(model.getValueAt(rows, 2 ).toString());
            
           // System.out.println(ic);
           // System.out.println(up);
           // System.out.println(qty);
          
          
    }//GEN-LAST:event_tblServiceCartMouseClicked

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
              
        DefaultTableModel model = (DefaultTableModel) tblServiceCart.getModel();
          int r = tblServiceCart.getRowCount();
          
          int row = model.getRowCount();
          
          for(int i=0; i<row ; i++)
          {
              Temp.add(Double.parseDouble(model.getValueAt(i, 0).toString()));
              Temp.add(Double.parseDouble(model.getValueAt(i, 1).toString()));
              Temp.add(Double.parseDouble(model.getValueAt(i, 2).toString()));
          }
        
          confirmCart();
          lblDelete.setEnabled(false);
          
        btnCalculateTotal.setEnabled(true);
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        
            
        Date d = new Date();
        //lblDate.setText(formatter.format(d));
         try {
             SimpleDateFormat formatter = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());
          
             String dd = formatter.format(dateChooser.getDate());
            // JOptionPane.showMessageDialog(null, dd);
             
             
            String query =" Select * from jobcard where date='"+dd+"'";
            pst = conx.prepareStatement(query);
             ResultSet rs4=pst.executeQuery();
             tblJobcardView.setModel(DbUtils.resultSetToTableModel(rs4));
         }
             
             catch (SQLException ex) {
             Logger.getLogger(JobCard.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnGenerateRptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateRptActionPerformed
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnGenerateRptActionPerformed

    private void txtODOReadingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODOReadingKeyPressed
       validateInt(txtODOReading);
    }//GEN-LAST:event_txtODOReadingKeyPressed

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        validateInt(txtQty);
    }//GEN-LAST:event_txtQtyKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
           try {
            
            String report = "C:\\Users\\chathura\\Documents\\NetBeansProjects\\voltage\\src\\reports\\jobcard.jrxml";
            JasperReport jp = JasperCompileManager.compileReport(report);
            JasperPrint jd = JasperFillManager.fillReport(jp, null,conx);
            JasperViewer.viewReport(jd,false);
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       try {
            
            SimpleDateFormat dte1 = new SimpleDateFormat("YYY-MM-dd",Locale.getDefault());
            String d_date1 = dte1.format(startDate.getDate());
            
            SimpleDateFormat dte2 = new SimpleDateFormat("YYY-MM-dd",Locale.getDefault());
            String d_date2 = dte2.format(endDate.getDate());
              
            JasperDesign jd = JRXmlLoader.load("C:\\Users\\chathura\\Documents\\NetBeansProjects\\voltage\\src\\reports\\jobcard_para.jrxml");
            String sql = "SELECT * FROM jobcard WHERE date between '"+d_date1+"' and '"+d_date2+"'" ;
            JRDesignQuery newquery = new JRDesignQuery();
            newquery.setText(sql);
            jd.setQuery(newquery);
            JasperReport jp = JasperCompileManager.compileReport(jd);
            JasperPrint js = JasperFillManager.fillReport(jp, null,conx);
            JasperViewer.viewReport(js,false);
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        new voltage.Fdivision().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JobCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JobCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JobCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JobCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JobCard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel RcustName;
    private javax.swing.JButton btnCalculateTotal;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnGenerateRpt;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnviewAll;
    private com.toedter.calendar.JDateChooser dateChooser;
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton lblAdd;
    private javax.swing.JLabel lblDate;
    private javax.swing.JButton lblDelete;
    private javax.swing.JLabel lblItemCost;
    private javax.swing.JLabel lblJobcardID;
    private javax.swing.JLabel lblReDate;
    private javax.swing.JPanel lblReceipt;
    private javax.swing.JLabel lblReciept;
    private javax.swing.JLabel lblServiceCart;
    private javax.swing.JLabel lblServiceCharges;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JLabel lgDate;
    private javax.swing.JLabel lgTime;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JLabel stringDay;
    private javax.swing.JTable tableItems;
    private javax.swing.JTable tableRequestedServices;
    private javax.swing.JTable tblFinalReceipt;
    private javax.swing.JTable tblJobcardView;
    private javax.swing.JTable tblServiceCart;
    private javax.swing.JLabel temp;
    private javax.swing.JLabel txtCustomerName;
    private javax.swing.JTextField txtODOReading;
    private javax.swing.JTextField txtQty;
    private javax.swing.JLabel txtTelephoneNo;
    private javax.swing.JLabel txtVehicleNo;
    // End of variables declaration//GEN-END:variables
}
