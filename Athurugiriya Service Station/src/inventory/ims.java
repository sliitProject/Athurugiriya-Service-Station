/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory;

/**
 *
 * @author chathura
 */

import static commonClasses.Validation.validateDouble;
import static commonClasses.Validation.validateInt;
import connections.DB_Connect;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import voltage.Fdivision;

public class ims extends javax.swing.JFrame {
    public boolean btnevent = false;
    Connection conn=null;
    PreparedStatement pst = null;
    Connection conn1=null;
    PreparedStatement pst1 = null;
    ResultSet rs=null;
    private Object e;
    public static String division="";
    

    /**
     * Creates new form ims
     */
    
    public void extendFrame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        this.getContentPane().setPreferredSize(new Dimension(width,height));
        this.pack();
    }
    
    public ims() {
        initComponents();
        extendFrame();
        System.out.println(division);
        conn = DB_Connect.getConnection();
        item_tableload();
       
       mythread1 t = new mythread1();
       mythread2 th = new mythread2();
        t.start();
        th.start();
            notification();
           
       
        addEntry.setVisible(false);
    }
    
     public class mythread2 extends Thread{
    
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
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
     public void item_tableload(){
   
       String sql1 = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel from item where category='Spare Parts'";
       String sql2 = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel from item where category='Lubricants'";
       String sql3 = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel from item where category='Chemicals'";
            
        try {
            pst = conn.prepareStatement(sql1);
            rs = pst.executeQuery();
            item_table_S.setAutoCreateColumnsFromModel(false);
            item_table_S.setModel(DbUtils.resultSetToTableModel(rs));
            
            pst = conn.prepareStatement(sql2);
            rs = pst.executeQuery();
            item_table_L.setAutoCreateColumnsFromModel(false);
            item_table_L.setModel(DbUtils.resultSetToTableModel(rs));
            
            pst = conn.prepareStatement(sql3);
            rs = pst.executeQuery();
            item_table_C.setAutoCreateColumnsFromModel(false);
            item_table_C.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (SQLException ex) {
            Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
        }
           
               
       
   }
     
    
     public void noti_tableload(){
         String sql="SELECT * from notification";
         
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            notifi_table.setAutoCreateColumnsFromModel(false);
            notifi_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     }
    
     
     
     
     
     
     private void Update_Table(){
     
         try{
         
             String sql=" select* from item";
             pst=conn.prepareStatement(sql);
             rs=pst.executeQuery();
             item_table_S.setModel(DbUtils.resultSetToTableModel(rs));
         
         }catch(Exception e){
         
             JOptionPane.showMessageDialog(null, e);
         
         }
     
     
     }
     
     
     
     
    public class mythread1 extends Thread{
    
        public void run(){
        
            while(true){
 
                
            
            notification();              
            try {
                
                this.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }}
    
    }
     
     
     
     
     private void notification() {
  
         
         
         
         String db_notifi_table="";
      //   db_notifi_table="";
         
         
         int id;
         int com_id;
         String name="";
         String cat="";
         int stk;
         boolean id_com= false;
         String sql="select itemCode,itemName,category,stock from item WHERE stock<=reOrderLevel";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
          
         
                //notifi_table.setModel(DbUtils.resultSetToTableModel(rs));
                int no=0;
                Statement st= null;
             Statement sta = conn.createStatement();
           ResultSet res = sta.executeQuery(sql);
                
                while(res.next()){
                    id_com=false;
                        no++;
                        id = res.getInt("itemCode");
                        name = res.getString("itemName");
                        cat = res.getString("category");
                        stk = res.getInt("stock");
                        db_notifi_table = "select * from notification";
                             st = conn.createStatement();
                             ResultSet rs1 = st.executeQuery(db_notifi_table);
                             while(rs1.next()){ 
                                 com_id = rs1.getInt("itemcode");
                                 
                            
                                 if(com_id==id){
                                     id_com=true;
                                   
                                    
                                     
                                 }
   
                             }
                           
                        if(id_com==false){     
                           
                            db_notifi_table = "INSERT INTO notification values ('"+id+"','"+name+"','"+cat+"','"+stk+"','Pending')";
                            
                           
                            pst=conn.prepareStatement(db_notifi_table);
                            pst.execute();
                        }
                }
                
                
                
                
                                    int chk_id;
                                    String chnge_level = "SELECT itemCode,itemName,category,stock from item WHERE stock>reOrderLevel";
                                    pst=conn.prepareStatement(chnge_level);
                                    ResultSet rs3=pst.executeQuery();
                                    while(rs3.next()){
                                        chk_id = rs3.getInt("itemCode");
                                        String not ="SELECT * from notification where itemcode='"+chk_id+"'";
                                        st = conn.createStatement();
                                        ResultSet re = st.executeQuery(not);
                                        while(re.next()){
                                            
                                           // JOptionPane.showMessageDialog(null, "menna wadaaaa");
                                            String delete_row = "DELETE FROM notification WHERE itemcode = '"+chk_id+"'";
                                            pst=conn.prepareStatement(delete_row);
                                            pst.execute();
                                        }
                                            
                                    }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                String num = String.valueOf(no);
                if(no>=1){
                    notify_label.setOpaque(true);
                    notify_label.setHorizontalAlignment(SwingConstants.CENTER);
                    notify_label.setBackground(Color.green);
                    notify_label.setVisible(true);
                    notify_label.setText(num+" notification/s");
                    
                }
                else
                    notify_label.setVisible(false);
            
               noti_tableload();
            
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
         
         
       
     
     }
     
     private void addItem(String catId, String itemName, String unit,String stk,String lvl){
    
           
        if(!itemName.equals("") && !unit.equals("") && !stk.equals("") && !lvl.equals("") && !catId.equals("<select>")){

            float unitPrice = Float.parseFloat(unit);
            int stock = Integer.parseInt(stk);
            int level = Integer.parseInt(lvl);
            int x=-1;
            
            if(level>=stock){
                 x=JOptionPane.showConfirmDialog(null,"Reorder level is equal/greater than the current stock level, Do you really want to proceed?","Caution!",JOptionPane.YES_NO_CANCEL_OPTION);
                    
                }
         
           // System.out.println(x);
            if(x==0 || x==-1){
            
        try{
            String q = "INSERT INTO item (itemName,category,stock,unitPrice,reOrderLevel ) values('"+itemName+"','"+catId+"','"+stock+"','"+unitPrice+"','"+level+"')";
            
            pst = conn.prepareStatement(q);
            pst.execute();

            item_tableload();
            JOptionPane.showMessageDialog(null, "Item added successfully!");
            clear();
            
            
            String early = "SELECT itemCode FROM item order by itemCode DESC limit 1";
                
                Statement st= null;
                Statement sta;
                String iCode="";
        
        
            sta = conn.createStatement();
            ResultSet res = sta.executeQuery(early);
            
            while(res.next()){
                iCode =res.getString("itemCode");
        }
           
           int code = Integer.parseInt(iCode);
           code++;
           iCode=String.valueOf(code);
           i_id.setText(iCode);

        }catch(Exception e){
            System.out.println(e);
        }
            }    
           
        }else
            JOptionPane.showMessageDialog(null, "Some of the Required Fields are Empty!","Error",2);
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        notify_label = new javax.swing.JLabel();
        lgDate = new javax.swing.JLabel();
        stringDay = new javax.swing.JLabel();
        lgTime = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        notifi_table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        to1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        to2 = new javax.swing.JTextField();
        itemname = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        item_table_L = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        item_table_C = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        item_table_S = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        i_name = new javax.swing.JTextField();
        i_price = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        i_id = new javax.swing.JLabel();
        i_cat = new javax.swing.JComboBox();
        i_rlevel = new javax.swing.JTextField();
        i_qty = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        add_button = new javax.swing.JButton();
        t_update = new javax.swing.JButton();
        t_delete = new javax.swing.JButton();
        clearAll = new javax.swing.JButton();
        addEntry = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("Inventory Management System");

        notify_label.setBackground(new java.awt.Color(0, 255, 0));
        notify_label.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        notify_label.setText("jLabel3");
        notify_label.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                notify_labelMouseMoved(evt);
            }
        });
        notify_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notify_labelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                notify_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                notify_labelMouseExited(evt);
            }
        });

        lgDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lgDate.setText("jLabel4");

        stringDay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        stringDay.setText("Date");

        lgTime.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(notify_label, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton7)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stringDay, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lgDate, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lgTime, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
            .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(notify_label, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stringDay, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lgDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lgTime))
                        .addGap(18, 18, 18)))
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Items to be reordered");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_12images.jpg"))); // NOI18N
        jButton3.setText("Reorder");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(626, 626, 626)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(296, Short.MAX_VALUE))
        );

        notifi_table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        notifi_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Item Name", "Category", "Stock", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(notifi_table);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1232, Short.MAX_VALUE))
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Notifications", jPanel12);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Item Name");

        jButton6.setBackground(new java.awt.Color(153, 255, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Search_png.png"))); // NOI18N
        jButton6.setText("Search");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_previous_256.png"))); // NOI18N
        jButton16.setText("Reset");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton29.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_reports.png"))); // NOI18N
        jButton29.setText("General Report");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Price Range");

        to1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        to1.setText("0");
        to1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                to1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                to1KeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("to");

        to2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        to2.setText("1000");
        to2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                to2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                to2KeyReleased(evt);
            }
        });

        itemname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        itemname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemnameKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel12))
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(to1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(to2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(itemname))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton16)
                        .addGap(57, 57, 57)
                        .addComponent(jButton6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton29)
                        .addGap(336, 336, 336))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(itemname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton16)
                    .addComponent(jLabel14)
                    .addComponent(to1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(to2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit_png.png"))); // NOI18N
        jButton1.setText("Edit");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_garbage_delete.png"))); // NOI18N
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Table View", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        item_table_L.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        item_table_L.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item Code", "Item Name", "Stock", "Unit Price", "Re Order Level"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        item_table_L.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                item_table_LMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(item_table_L);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1504, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Lubricants", jPanel5);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Table View", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        item_table_C.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        item_table_C.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item Code", "Item Name", "Stock", "Unit Price", "Re Order Level"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        item_table_C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                item_table_CMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(item_table_C);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1504, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Chemicals", jPanel9);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Table View", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        item_table_S.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        item_table_S.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item Code", "Item Name", "Stock", "Unit Price", "Re Order Level"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        item_table_S.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                item_table_SMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item_table_SMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(item_table_S);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1504, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Spare Parts", jPanel4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTabbedPane2)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(333, 333, 333)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(439, 439, 439)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Inventory Details", jPanel2);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Record Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Item ID");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Category");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Item Name");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("Unit Price");

        i_name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        i_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i_nameActionPerformed(evt);
            }
        });

        i_price.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        i_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                i_priceKeyReleased(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("Item Qty");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setText("Re-Order-Level");

        i_id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        i_cat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        i_cat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select>", "Lubricants", "Chemicals" }));

        i_rlevel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        i_rlevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                i_rlevelKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                i_rlevelKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                i_rlevelKeyTyped(evt);
            }
        });

        i_qty.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        i_qty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                i_qtyMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                i_qtyMouseReleased(evt);
            }
        });
        i_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                i_qtyKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(34, 34, 34)
                        .addComponent(i_price))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(i_name, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(i_id, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(i_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(i_rlevel)
                    .addComponent(i_qty, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                .addGap(495, 495, 495))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(i_id, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(i_cat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(i_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(26, 26, 26)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(i_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(46, 46, 46))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(i_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(i_rlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        add_button.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        add_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_add_big.png"))); // NOI18N
        add_button.setText("Add");
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        t_update.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        t_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_update.png"))); // NOI18N
        t_update.setText("Update");
        t_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_updateActionPerformed(evt);
            }
        });

        t_delete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        t_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_garbage_delete.png"))); // NOI18N
        t_delete.setText("Delete");
        t_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_deleteActionPerformed(evt);
            }
        });

        clearAll.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_previous_256.png"))); // NOI18N
        clearAll.setText("Clear");
        clearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllActionPerformed(evt);
            }
        });

        addEntry.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        addEntry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_add_big.png"))); // NOI18N
        addEntry.setText("Add New Entry");
        addEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEntryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118)
                        .addComponent(clearAll)
                        .addGap(111, 111, 111)
                        .addComponent(t_delete)
                        .addGap(109, 109, 109)
                        .addComponent(t_update))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(365, 365, 365)
                        .addComponent(addEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearAll, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_update, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(319, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(242, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Record Details", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       String i_name = itemname.getText();
        String to_1 = to1.getText();
        String to_2 = to2.getText();
        int selectedIndex = jTabbedPane2.getSelectedIndex();
        
        try{

            if(selectedIndex==0){

                if(i_name.equals("All")){

                    String sql = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel FROM item WHERE category='Lubricants' AND (Item_Price between '"+to_1 +"' and '"+ to_2+"')" ;
           
               pst = conn.prepareStatement(sql);
           
                    rs = pst.executeQuery();
                    item_table_L.setModel(DbUtils.resultSetToTableModel(rs));

                }else{

                    String sql = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel FROM item WHERE itemName LIKE'%"+i_name +"%' AND category='Lubricants' AND (unitPrice between '"+to_1 +"' and '"+ to_2+"')" ;
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    item_table_L.setModel(DbUtils.resultSetToTableModel(rs));

               }}
            else if(selectedIndex==1){
            
                 if(i_name.equals("All")){

                    String sql = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel FROM item WHERE category='Chemicals' AND (Item_Price between '"+to_1 +"' and '"+ to_2+"')" ;
           
               pst = conn.prepareStatement(sql);
           
                    rs = pst.executeQuery();
                    item_table_C.setModel(DbUtils.resultSetToTableModel(rs));

                }else{

                    String sql = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel FROM item WHERE itemName LIKE'%"+i_name +"%' AND category='Chemicals' AND (unitPrice between '"+to_1 +"' and '"+ to_2+"')" ;
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    item_table_C.setModel(DbUtils.resultSetToTableModel(rs));

               }
                
            
            }else if(selectedIndex==2){
            
                    if(i_name.equals("All")){

                    String sql = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel FROM item WHERE category='Spare Parts' AND (Item_Price between '"+to_1 +"' and '"+ to_2+"')" ;
           
               pst = conn.prepareStatement(sql);
           
                    rs = pst.executeQuery();
                    item_table_S.setModel(DbUtils.resultSetToTableModel(rs));

                }else{

                    String sql = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel FROM item WHERE itemName LIKE'%"+i_name +"%' AND category='Spare Parts' AND (unitPrice between '"+to_1 +"' and '"+ to_2+"')" ;
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    item_table_S.setModel(DbUtils.resultSetToTableModel(rs));

               }
            
            
            
            
            }
            
            
        }catch(SQLException ex) {
               Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
           }
            

            

      
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
       String sql = "SELECT itemCode,itemName,stock,unitPrice,reOrderLevel FROM item";

        
            
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            item_tableload();
        } catch (SQLException ex) {
            Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed

            
          try {
            
            String report = "C:\\Users\\Kavinga\\Voltage new\\src\\reports\\report1.jrxml";
            JasperReport jp = JasperCompileManager.compileReport(report);
            JasperPrint jd = JasperFillManager.fillReport(jp, null,conn);
            JasperViewer.viewReport(jd,false);
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }//GEN-LAST:event_jButton29ActionPerformed

    private void i_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_i_nameActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed

        
        String categoryid = i_cat.getSelectedItem().toString();
        String itemname = i_name.getText();
        String unit = i_price.getText();
        String stk = i_qty.getText();
        String lvl = i_rlevel.getText();
        
        addItem(categoryid, itemname, unit,stk , lvl);
        


    }//GEN-LAST:event_add_buttonActionPerformed

    
    private void updateItem(int itemCode,String categoryid,String itemname,float unitPrice,int stock,int level) {
    
        
        String earlier_qty = "SELECT stock FROM item WHERE itemCode='"+itemCode+"'";
        Statement sta;
        String e_qty="";
        int new_qty1;
        String new_qty="";
        
        try {
            sta=conn.createStatement();
            ResultSet res=sta.executeQuery(earlier_qty);
            
            while(res.next()){
            
                e_qty = res.getString("stock");
            }
            new_qty1=Integer.parseInt(e_qty)+stock;
            new_qty = String.valueOf(stock);
            
            String sql = "UPDATE item set stock='"+new_qty+"',unitPrice='"+unitPrice+"' ,reOrderLevel='"+level+"' WHERE itemCode='"+itemCode+"'";
           // early_qty.setText("earlier stock amount :"+e_qty);
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(i_cat, "item updated successfully!");
            clear();
            i_qty.setEnabled(true);
            i_name.setEnabled(true);
            i_name.setEnabled(true);
            i_cat.setEnabled(true);
            i_cat.setSelectedItem("<select>");
            
            
            
            
            
            
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        item_tableload();
        
    }
    
    
    
    private void t_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_updateActionPerformed
      //  int x=JOptionPane.showConfirmDialog(null, "Do you really want to update this field? ");
        int response=JOptionPane.showConfirmDialog(ims.this,"Do you want to Update a Record ?","Updating Record",JOptionPane.YES_NO_OPTION);
        
        if( response==JOptionPane.YES_OPTION){
        int itemCode = Integer.parseInt(i_id.getText());
        String categoryid = i_cat.getSelectedItem().toString();
        String itemname = i_name.getText();
        float unitPrice = Float.parseFloat(i_price.getText());
        int stock = Integer.parseInt(i_qty.getText());
        int level = Integer.parseInt(i_rlevel.getText());
        
        
        updateItem(itemCode,categoryid,itemname,unitPrice,stock,level);
        
     
              
        }
        
    }//GEN-LAST:event_t_updateActionPerformed

    private void deleteItem(String itemid){
    
           String sql = "DELETE FROM item where itemCode = '"+itemid+"'";
           
           try {
               pst = conn.prepareStatement(sql);
               pst.execute();
                item_tableload();
                JOptionPane.showMessageDialog(ims.this, "Item deleted successfully!");
           } catch (SQLException ex) {
               Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
           }
                
                 i_id.setText(null);
                 i_cat.setSelectedItem(null);
                 i_name.setText(null);
                 i_price.setText(null);
                 i_qty.setText(null);
                 i_rlevel.setText(null);
                 jTabbedPane1.setSelectedIndex(1);
    
    
    }
    private void t_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_deleteActionPerformed
       int x = JOptionPane.showConfirmDialog(null, "Do you want to delete this? ");
        String itemid="";
        if(x==0){
            itemid = i_id.getText();
            deleteItem(itemid);
            
        }
        
        
        
         Statement sta = null;
        String chkid;
        
            
            String sql2="SELECT * FROM notification";
               
             try {
                sta=conn.createStatement();
                ResultSet res=sta.executeQuery(sql2);
                
                while(res.next()){
            
                chkid = res.getString("itemcode");
                
                if(chkid.equals(itemid)){
                    
                    String deletenoti="DELETE FROM notification where itemCode ='"+chkid+"'";
                    pst=conn.prepareStatement(deletenoti);
                    pst.execute();
                    noti_tableload();
                    
                }
            }
                 
             } catch (SQLException ex) {
                 Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
             }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_t_deleteActionPerformed

    private void clear(){
    
       // i_id.setText(null);
        i_cat.setSelectedItem("<select>");
        i_name.setText(null);
        i_price.setText(null);
        i_qty.setText(null);
        i_rlevel.setText(null);
    
    
    }
    
    private void clearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllActionPerformed

           clear();

    }//GEN-LAST:event_clearAllActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        btnevent= true;
        int selectedIndex = jTabbedPane2.getSelectedIndex();
        
       int r=-2;
        
        if(selectedIndex==0){
        
                r = item_table_L.getSelectedRow();
                
                if(r==-1){
                   JOptionPane.showMessageDialog(ims.this,"Please Select a row from the table","Re-Check",2);
                }
                else{
                String itemCode= item_table_L.getValueAt(r, 0).toString();
                String itemName = item_table_L.getValueAt(r, 1).toString();
                String stock= item_table_L.getValueAt(r, 2).toString();
                String unitprice = item_table_L.getValueAt(r, 3).toString();
                String reorderlevel= item_table_L.getValueAt(r, 4).toString();
        

                i_id.setText(itemCode);
                i_cat.setSelectedItem("Lubricants");
                i_name.setText(itemName);
                i_price.setText(unitprice);
                i_qty.setText(stock);
                i_qty.setEnabled(false);
                t_delete.setEnabled(true);
               // early_qty.setText("Available Stock :"+stock);
                i_rlevel.setText(reorderlevel);
                }
        
        }else if(selectedIndex==1){
        
                r = item_table_C.getSelectedRow();
                if(r==-1){
                
                    JOptionPane.showMessageDialog(i_cat, "Please select an item!");
                }else{
                
                String itemCode= item_table_C.getValueAt(r, 0).toString();
                String itemName = item_table_C.getValueAt(r, 1).toString();
                String stock= item_table_C.getValueAt(r, 2).toString();
                String unitprice = item_table_C.getValueAt(r, 3).toString();
                String reorderlevel= item_table_C.getValueAt(r, 4).toString();
        

                i_id.setText(itemCode);
                i_cat.setSelectedItem("Chemicals");
                i_name.setText(itemName);
                i_price.setText(unitprice);
                i_qty.setText(stock);
                i_rlevel.setText(reorderlevel);
                t_delete.setEnabled(true);
                }
        
        }else if(selectedIndex==2){
        
                r = item_table_S.getSelectedRow();
                
                if(r==-1){
                
                        JOptionPane.showMessageDialog(i_cat, "Please select an item!");
                
                }else{
                
                String itemCode= item_table_C.getValueAt(r, 0).toString();
                String itemName = item_table_C.getValueAt(r, 1).toString();
                String stock= item_table_C.getValueAt(r, 2).toString();
                String unitprice = item_table_C.getValueAt(r, 3).toString();
                String reorderlevel= item_table_C.getValueAt(r, 4).toString();
        

                i_id.setText(itemCode);
                i_cat.setSelectedItem("Spare Parts");
                i_name.setText(itemName);
                i_price.setText(unitprice);
                i_qty.setText(stock);
                i_rlevel.setText(reorderlevel);
                }
                
        }
                            
       
        if(r!=-1){
        
        i_name.setEnabled(false);
        clearAll.setEnabled(false);
        i_cat.setEnabled(false);
        
        add_button.setVisible(false);
        t_update.setVisible(true);
        t_update.setEnabled(true);
        addEntry.setVisible(true);
        addEntry.setEnabled(true);
        jTabbedPane1.setSelectedIndex(2);
        
        
        
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    private void addEntry(){
    
         
         String early = "SELECT itemCode FROM item order by itemCode DESC limit 1";
                
                Statement st= null;
                Statement sta;
                String iCode="";
        
        try {
            sta = conn.createStatement();
            ResultSet res = sta.executeQuery(early);
            
            while(res.next()){
                iCode =res.getString("itemCode");
        }} catch (SQLException ex) {
            Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
        }
           
           int code = Integer.parseInt(iCode);
           code++;
           iCode=String.valueOf(code);
          //  System.out.println(iCode);
            
        
        
        
        
        
        i_id.setText(iCode);
        
        
        
        
        
        
        
        i_name.setEnabled(true);
        i_cat.setEnabled(true);
        
        
      //  i_id.setText(null);
        i_cat.setSelectedItem("<select>");
        i_qty.setEnabled(true);
        i_name.setText(null);
        i_price.setText(null);
        i_qty.setText(null);
        i_rlevel.setText(null);
        
        t_update.setEnabled(false);
        t_delete.setEnabled(false);
        addEntry.setVisible(false);
        add_button.setVisible(true);
        clearAll.setEnabled(true);
    
    }
    private void addEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEntryActionPerformed

       addEntry();
        
    }//GEN-LAST:event_addEntryActionPerformed

    
      public static ArrayList<ArrayList<Object>> FinalArray = new ArrayList<ArrayList<Object>>();
    
    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        
        
//         String early = "SELECT itemCode FROM item order by itemCode DESC limit 1";
//                
//                Statement st= null;
//                Statement sta;
//                String iCode="";
//        
//        try {
//            sta = conn.createStatement();
//            ResultSet res = sta.executeQuery(early);
//            
//            while(res.next()){
//                iCode =res.getString("itemCode");
//        }} catch (SQLException ex) {
//            Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
//        }
//           
//           int code = Integer.parseInt(iCode);
//           code++;
//           iCode=String.valueOf(code);
//          //  System.out.println(iCode);
//            
//        
//        
//        
//        
//        
//        i_id.setText(iCode);
//        i_cat.setSelectedItem("<select>");
////       // i_cat.setSelectedItem(null);
////        i_name.setText(null);
////        i_price.setText(null);
////        i_qty.setText(null);
////        i_rlevel.setText(null);
//        clearAll.setEnabled(true);
//         i_name.setEnabled(true);
//        i_cat.setEnabled(true);
//        t_update.setEnabled(false);
//             
//        addEntry.setEnabled(false);
//        add_button.setVisible(true);
//        t_delete.setEnabled(false);
//  
        
        
        
        
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
         int x = JOptionPane.showConfirmDialog(null, "Do you really want to delete this? ");
         
         String itemCode=" ";

        if(x==0){
            
             int selectedIndex = jTabbedPane2.getSelectedIndex();
        
        if(selectedIndex==0){
        
                int r = item_table_L.getSelectedRow();
                 itemCode= item_table_L.getValueAt(r, 0).toString();
               
            
        
        }else if(selectedIndex==1){
        
                int r = item_table_C.getSelectedRow();
                 itemCode= item_table_C.getValueAt(r, 0).toString();
               
        }else if(selectedIndex==2){
        
                int r = item_table_S.getSelectedRow();
                 itemCode= item_table_S.getValueAt(r, 0).toString();
                
        }
            
            
        
        
        
        
        
         
            
            
            
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        Statement sta = null;
        String chkid;
        
            
            String sql2="SELECT * FROM notification";
               
             try {
                sta=conn.createStatement();
                ResultSet res=sta.executeQuery(sql2);
                
                while(res.next()){
            
                chkid = res.getString("itemcode");
                
                if(chkid.equals(itemCode)){
                    
                    String deletenoti="DELETE FROM notification where itemCode ='"+chkid+"'";
                    pst=conn.prepareStatement(deletenoti);
                    pst.execute();
                    noti_tableload();
                    
                }
            }
                 
             } catch (SQLException ex) {
                 Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
             }
            
            

            String sql = "DELETE FROM item where itemCode = '"+itemCode+"'";
            
       
             try {
                 pst = conn.prepareStatement(sql);
                 pst.execute();
                 item_tableload();
             } catch (SQLException ex) {
                 Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
             }
             
           

        }
        
        
        
        
        
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        
        
                String unitPrice="";
                String reOrderLevel="";
        
                reOrderForm form2 = new reOrderForm();
                
              int r = notifi_table.getSelectedRow();
              
              
                
                if(r==-1){
                    JOptionPane.showMessageDialog(i_cat, "Please select an item! ");
                }
                else{
                    
                    if(notifi_table.getValueAt(r, 4).equals("Order Placed")){
                        JOptionPane.showMessageDialog(i_cat, "Order has already been placed! ");
                    }else{
                    
                String itemCode= notifi_table.getValueAt(r, 0).toString();
                String itemName = notifi_table.getValueAt(r, 1).toString();
                String category = notifi_table.getValueAt(r, 2).toString();
                String stock= notifi_table.getValueAt(r, 3).toString();
                //String unitprice = notifi_table.getValueAt(r, 3).toString();
                //String reorderlevel= notifi_table.getValueAt(r, 4).toString();
        
                    Statement cs1 = null;
             
                    String sql1 = "SELECT supplierName FROM supplier WHERE category='"+category+"'";
                  
        
                    try {
                            cs1 = conn.createStatement();
                            ResultSet rs1 = cs1.executeQuery(sql1);
                            while(rs1.next()){
                               form2.combo_sup.addItem(rs1.getString(1));
                            
                               
                        }      } catch (SQLException ex) {
                        Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                
                
                
                
                
                
                
                String sup_name = "SELECT supplierName FROM sup_item INNER JOIN supplier ON sup_item.itemCode='"+itemCode+"' and sup_item.supplierID=supplier.supplierID  ";
                
                Statement cs = null;
                    try {
                        cs=conn.createStatement();
                          ResultSet res = cs.executeQuery(sup_name);
               
                while(res.next()){
                
                    form2.combo_sup.addItem(res.getString(1));
                }
                
                    } catch (SQLException ex) {
                        Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
                    }
              
                
                String sql= "SELECT * FROM item where itemCode='"+itemCode+"'";
                
                
                
                
                
                Statement st= null;
                Statement sta;
               
        
        try {
            sta = conn.createStatement();
            ResultSet res = sta.executeQuery(sql);
            
            while(res.next()){
                unitPrice =res.getString("unitPrice");
                reOrderLevel = res.getString("reOrderLevel");
                                               
                
        }} catch (SQLException ex) {
            Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
        }
               
                
                
                
                
                form2.sup_item.setText(itemCode);
                form2.sup_stock.setText(stock);
                form2.sup_price.setText(unitPrice);
                form2.sup_cat.setText(category);
                form2.sup_level.setText(reOrderLevel);
                form2.sup_name.setText(itemName);
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
               // i_name.setText(itemName);
              //  i_price.setText(unitprice);
              //  i_qty.setText(null);
               // early_qty.setText("Available Stock :"+stock);
              //  i_rlevel.setText(reorderlevel);
 
 
                form2.setVisible(true);
                
 
                }        
        
        
        
        
        
                }
        
                
        
       
    }//GEN-LAST:event_jButton3ActionPerformed

    

    
    
    
    private void notify_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notify_labelMouseClicked
       
       
        notify_label.addMouseListener(new MouseAdapter(){
     
          
            public void mouseClicked(MouseEvent e) {
                jTabbedPane1.setSelectedIndex(0);
                
            
            }
        
        
        
        });
 
        
        
        
    }//GEN-LAST:event_notify_labelMouseClicked

  
    
    
    
    private void notify_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notify_labelMouseEntered
 notify_label.addMouseListener(new MouseAdapter(){
     
            @Override
            public void mouseEntered(MouseEvent e) {
                
            }
        
        
        
        });       
        
        
        
        
        
    }//GEN-LAST:event_notify_labelMouseEntered

    private void notify_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notify_labelMouseExited
         notify_label.addMouseListener(new MouseAdapter(){
     
          
            public void mouseExited(MouseEvent e) {
               
                
            
            }
        
        
        
        });     
    }//GEN-LAST:event_notify_labelMouseExited

    private void notify_labelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notify_labelMouseMoved
       notify_label.addMouseListener(new MouseAdapter(){
     
          
            public void mouseMoved(MouseEvent e) {
               
                
            
            }
        
        
        
        });    
    }//GEN-LAST:event_notify_labelMouseMoved

    private void itemnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemnameKeyPressed
     
    }//GEN-LAST:event_itemnameKeyPressed

    private void to1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_to1KeyPressed
   
    }//GEN-LAST:event_to1KeyPressed

    private void to2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_to2KeyPressed
      
    }//GEN-LAST:event_to2KeyPressed

    private void item_table_CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_table_CMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_item_table_CMouseClicked

    private void item_table_LMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_table_LMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_item_table_LMouseClicked

    private void item_table_SMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_table_SMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_item_table_SMouseEntered

    private void item_table_SMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_table_SMouseClicked

    }//GEN-LAST:event_item_table_SMouseClicked

    private void i_qtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_i_qtyMouseClicked
       
    }//GEN-LAST:event_i_qtyMouseClicked

    private void i_rlevelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i_rlevelKeyPressed
      
    }//GEN-LAST:event_i_rlevelKeyPressed

    private void i_rlevelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i_rlevelKeyTyped
        
    }//GEN-LAST:event_i_rlevelKeyTyped

    private void i_rlevelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i_rlevelKeyReleased
        validateInt(i_rlevel);
    }//GEN-LAST:event_i_rlevelKeyReleased

    private void i_qtyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_i_qtyMouseReleased
       
    }//GEN-LAST:event_i_qtyMouseReleased

    private void i_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i_qtyKeyReleased
        validateInt(i_qty);
    }//GEN-LAST:event_i_qtyKeyReleased

    private void i_priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i_priceKeyReleased
       validateDouble(i_price);
    }//GEN-LAST:event_i_priceKeyReleased

    private void to1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_to1KeyReleased
       validateInt(to1);
    }//GEN-LAST:event_to1KeyReleased

    private void to2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_to2KeyReleased
         validateInt(to2);
    }//GEN-LAST:event_to2KeyReleased

    
    private class handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            if(ae.getSource()==jButton1){
            
                btnevent = true;
            }
            
        }
    
        
        
        
    }
    
    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
      
        
        JTabbedPane tp = (JTabbedPane) evt.getSource();
        int index = tp.getSelectedIndex();
        
        if(index == 2){
            handler hnd = new handler();
            jButton1.addActionListener(hnd);
                if(btnevent==false){
            
            
            //JOptionPane.showMessageDialog(null, "yaay");
        
                String early = "SELECT itemCode FROM item order by itemCode DESC limit 1";
//                
//                Statement st= null;
                Statement sta;
                String iCode="";
//        
        try {
            sta = conn.createStatement();
            ResultSet res = sta.executeQuery(early);
            
            while(res.next()){
                iCode =res.getString("itemCode");
        }} catch (SQLException ex) {
            Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
        }
           
           int code = Integer.parseInt(iCode);
           code++;
           iCode=String.valueOf(code);
            System.out.println(iCode);
            
        
        
        
        
        
        i_id.setText(iCode);
        i_cat.setSelectedItem("<select>");
       // i_cat.setSelectedItem(null);
        i_name.setText(null);
        i_price.setText(null);
        i_qty.setText(null);
        i_rlevel.setText(null);
        clearAll.setEnabled(true);
         i_name.setEnabled(true);
        i_cat.setEnabled(true);
        t_update.setEnabled(false);
             
        addEntry.setEnabled(false);
        add_button.setVisible(true);
        t_delete.setEnabled(false);
        
        
        }
        
                btnevent=false;
        }
        
        
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
       
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        new Fdivision().setVisible(true);
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
            java.util.logging.Logger.getLogger(ims.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ims.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ims.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ims.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ims().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEntry;
    private javax.swing.JButton add_button;
    private javax.swing.JButton clearAll;
    private javax.swing.JComboBox i_cat;
    private javax.swing.JLabel i_id;
    private javax.swing.JTextField i_name;
    private javax.swing.JTextField i_price;
    private javax.swing.JTextField i_qty;
    private javax.swing.JTextField i_rlevel;
    private javax.swing.JTable item_table_C;
    private javax.swing.JTable item_table_L;
    private javax.swing.JTable item_table_S;
    private javax.swing.JTextField itemname;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lgDate;
    private javax.swing.JLabel lgTime;
    private javax.swing.JTable notifi_table;
    private javax.swing.JLabel notify_label;
    private javax.swing.JLabel stringDay;
    private javax.swing.JButton t_delete;
    private javax.swing.JButton t_update;
    private javax.swing.JTextField to1;
    private javax.swing.JTextField to2;
    // End of variables declaration//GEN-END:variables
}
