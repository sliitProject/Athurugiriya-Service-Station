/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_management;

import static commonClasses.Validation.validateInt;
import static commonClasses.Validation.validateString;
import connections.DB_Connect;
import inventory.ims;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
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
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author D2
 */
public class EventManagement extends javax.swing.JFrame {

 Connection con=null;
 PreparedStatement pst=null;
 ResultSet rs=null;
 ResultSet rst=null;
 static int decide=0;
 String eid=null;
 String nxtEventName=null;
 
 public void extendFrame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        this.getContentPane().setPreferredSize(new Dimension(width,height));
        this.pack();
    }
 
    public EventManagement() {
      //  JFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
     //  JFrame.setVisible(true);
        initComponents();
        extendFrame();
        con=DB_Connect.getConnection();
        searchFromDate.setVisible(false);
        searchToDate.setVisible(false);
        btn_updateRedirect.setEnabled(false);
        jPanel7.setVisible(false);
        panel_descrip.setVisible(false);
        overviewTable_Load();
        allVehicleTable_Load();
        allVehiclesTable_Load();
        eventNotify();
        myThread th = new myThread();
        mythread1 t = new mythread1();
        t.start();
        th.start();
        if(eid==null)
        {
            btn_eUpdate.setEnabled(false);
            btn_eDelete.setEnabled(false);
        }
            
    }
    
    
    public class mythread1 extends Thread{
    
        public void run()
        {
        
            while(true)
            {

                
                setDate();


                try 
                {

                    this.sleep(500);
                } 
                catch (InterruptedException ex)
                {
                    Logger.getLogger(ims.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
        }
    
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

    
    
    
    
    public void overviewTable_Load(){
        String overviewQuery="SELECT event_id,event_name,from_date,to_date,event_address,vehicle_approved_emp,event_handler FROM event WHERE from_date >= CURDATE()";
        try
        {
                pst=con.prepareStatement(overviewQuery);
                rs=pst.executeQuery();
                overviewTable.setAutoCreateColumnsFromModel(false);
                overviewTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Loading OverView Table is Unsuccessful !"+ex);
        }
    }
    public void eventVehicleTable_Load(){
       String vid="-1";
       row=overviewTable.getSelectedRow();
       eid=overviewTable.getValueAt(row, 0).toString();
       String specificEventsVid="SELECT vehicle_id FROM evtable WHERE event_id='"+eid+"' ";
            try
            {
                pst=con.prepareStatement(specificEventsVid);
                rs=pst.executeQuery();
                while(rs.next())
                {
                    vid=vid+","+rs.getString("vehicle_id");

                }
                String specificVehiclesdes_query="SELECT chassis_no,vehicle_model,manufac_year,vehicle_colour,vehicle_status FROM event_vehicle WHERE vehicle_id IN ("+vid+") ";
                pst=con.prepareStatement(specificVehiclesdes_query);
                rst=pst.executeQuery();
                eVehicleTable.setAutoCreateColumnsFromModel(false);
                eVehicleTable.setModel(DbUtils.resultSetToTableModel(rst));
            
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Loading Data to Event Vehicle Table Error ! "+e);
            }
        
    }
    
    public void vehicleOverviewTable_Load(){
       String vid="-1";
       row=overviewTable.getSelectedRow();
       eid=overviewTable.getValueAt(row, 0).toString();
       String specificEventsVid="SELECT vehicle_id FROM evtable WHERE event_id='"+eid+"' ";
            try
            {
                pst=con.prepareStatement(specificEventsVid);
                rs=pst.executeQuery();
                while(rs.next())
                {
                    vid=vid+","+rs.getString("vehicle_id");

                }
                String specificVehiclesdes_query="SELECT chassis_no,vehicle_model,manufac_year,vehicle_colour,vehicle_status FROM event_vehicle WHERE vehicle_id IN ("+vid+") ";
                pst=con.prepareStatement(specificVehiclesdes_query);
                rst=pst.executeQuery();
                vehicleOverviewTable.setAutoCreateColumnsFromModel(false);
                vehicleOverviewTable.setModel(DbUtils.resultSetToTableModel(rst));
            
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Loading Data to Event Vehicle Table Error ! "+e);
            }
        
    }
    public void allVehicleTable_Load(){
       String vehicle_details="SELECT chassis_no,vehicle_model,manufac_year,vehicle_colour,vehicle_status FROM event_vehicle";
        try 
        {
                pst=con.prepareStatement(vehicle_details);
                rs=pst.executeQuery();
                allVehicleTable.setAutoCreateColumnsFromModel(false);
                allVehicleTable.setModel(DbUtils.resultSetToTableModel(rs));
                allVehiclesTable.setAutoCreateColumnsFromModel(false);
                allVehiclesTable.setModel(DbUtils.resultSetToTableModel(rs));
             
        } 
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Loading All Vehicles in Handle Vehicles is Unsuccessful !");
        }
      
        
    }
    
    public void allVehiclesTable_Load(){
       String vehicle_details="SELECT chassis_no,vehicle_model,manufac_year,vehicle_colour,vehicle_status FROM event_vehicle";
        try 
        {
                pst=con.prepareStatement(vehicle_details);
                rs=pst.executeQuery();
                allVehiclesTable.setAutoCreateColumnsFromModel(false);
                allVehiclesTable.setModel(DbUtils.resultSetToTableModel(rs));
             
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Loading All Vehicles in Handle Events is Unsuccessful !");
        }
      
        
    }
    
    private void panelDescrip_Load()
    {
        String eid=overviewTable.getValueAt(row, 0).toString();
        String eDescrip=null;
        String eDescripQuery="SELECT event_descrip FROM event WHERE event_id='"+eid+"' ";
        try
        {
            pst=con.prepareStatement(eDescripQuery);
            rs=pst.executeQuery();
            while(rs.next())
            {
                eDescrip=rs.getString("event_descrip");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Retrieveing Event Description is Unsuccessful ! "+e);
        }
        description.setText(eDescrip);
    }
    
    
    
    public void clearFields(){
          ename.setText("");
          sdate.setDate(new Date());
          edate.setDate(new Date());
          edes.setText("");
          eloc.setText("");
          vapprovedPerson.setText("");
          ehandler.setText("");
          newChassis.setText("");
          newVModel.setText("");
          newManuYear.setText("");
          newVColour.setText("");
          newVCondition.setText("");
          allVehiclesTable.clearSelection();
          eVehicleTable.clearSelection();
          DefaultTableModel model = (DefaultTableModel) eVehicleTable.getModel();
          model.setRowCount(0);
    }
    public void clearVehicleFields()
    {
        newChassis.setText("");
        newVModel.setText("");
        newManuYear.setText("");
        newVColour.setText("");
        newVCondition.setText("");
        allVehicleTable.clearSelection();
    }
    public long daysLeft=0;
    
    public class myThread extends Thread{
    
    
        public void run(){
            
            while(true)
            {
                int hello =eventNotify();
                String nextEVentQuery="SELECT event_name FROM event WHERE from_date >= CURDATE() ORDER BY from_date limit 1";
                try
                {
                    pst=con.prepareStatement(nextEVentQuery);
                    rs=pst.executeQuery();
                    while(rs.next())
                    {
                        nxtEventName=rs.getString("event_name");
                    }

                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, "Retrieving New Event is Unsuccessful !");
                }



                if(hello<=7)
                {
                    lbl_nxtEvent.setText("Next Event : "+nxtEventName);
                    lblnotifi.setText("Days Left : "+hello);
                }
                else
                {

                    lbl_nxtEvent.setVisible(false);
                    lblnotifi.setVisible(false);
                }


                try 
                {
                        this.sleep(1000);
                } 
                catch (InterruptedException ex) 
                {
                        Logger.getLogger(EventManagement.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
        }
    
    }
    
    
    
    public int eventNotify(){
    
        int days=0;
        String start=null;
        Date start1=new Date();
        String date="SELECT from_date FROM event WHERE from_date >= CURDATE() ORDER BY from_date limit 1";
       try
       {
            pst=con.prepareStatement(date);
            rs=pst.executeQuery();
            while(rs.next())
            {
                start1=rs.getDate("from_date");
            }


            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date today = Calendar.getInstance().getTime();
            String dateAsString = dateFormatter.format(today);
            long diff=0;

            diff = (start1.getTime() - today.getTime());
            daysLeft= (diff / (1000 * 60 * 60 * 24));


            String com1=dateAsString.toString();
            String com2=start1.toString();


            if(!com1.equals(com2))
            {

                daysLeft=daysLeft+1;            
            }
             Font font = new Font("Tahoma", Font.BOLD,12);
             Color colour=new Color(0,153,0);
            lbl_nxtEvent.setFont(font);
            if(daysLeft<3)
            {
                lblnotifi.setFont(font);
                lblnotifi.setForeground(Color.red);
            }
            else
            {
                lblnotifi.setFont(font);
                lblnotifi.setForeground(colour);
            }

       
       }
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, e);
       }
        
        
        
        
        return (int)daysLeft;
        
        
        
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        eventFrame = new javax.swing.JTabbedPane();
        eventOverview = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        searchName = new javax.swing.JTextField();
        viewAllEvents = new javax.swing.JButton();
        btn_search = new javax.swing.JButton();
        searchFromDate = new com.toedter.calendar.JDateChooser();
        searchToDate = new com.toedter.calendar.JDateChooser();
        lblnotifi = new javax.swing.JLabel();
        lbl_nxtEvent = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        byDate = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        overviewTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        vehicleOverviewTable = new javax.swing.JTable();
        panel_descrip = new javax.swing.JPanel();
        description = new javax.swing.JLabel();
        btn_updateRedirect = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        handleEvent = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ename = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        edes = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        sdate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        edate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        eloc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        vapprovedPerson = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        ehandler = new javax.swing.JTextField();
        addEvents = new javax.swing.JButton();
        btn_eUpdate = new javax.swing.JButton();
        btn_eDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        allVehiclesTable = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        vAddbtn = new javax.swing.JButton();
        deleteVehicle = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        eVehicleTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        newChassis = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        newVModel = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        newManuYear = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        newVColour = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        newVCondition = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        allVehicleTable = new javax.swing.JTable();
        addNewVehicle = new javax.swing.JButton();
        deleteVehicle1 = new javax.swing.JButton();
        updateNewVehicle = new javax.swing.JButton();
        clearVFields = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lgTime = new javax.swing.JLabel();
        lgDate = new javax.swing.JLabel();
        stringDay = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1084, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        eventFrame.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        searchName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNameActionPerformed(evt);
            }
        });
        jPanel4.add(searchName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 216, 30));

        viewAllEvents.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        viewAllEvents.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_quickview-icon.png"))); // NOI18N
        viewAllEvents.setText("View Upcoming Events");
        viewAllEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAllEventsActionPerformed(evt);
            }
        });
        jPanel4.add(viewAllEvents, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 270, -1));

        btn_search.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Search_png.png"))); // NOI18N
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel4.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, -1, -1));
        jPanel4.add(searchFromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 187, -1));
        jPanel4.add(searchToDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 189, -1));

        lblnotifi.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblnotifi.setForeground(new java.awt.Color(0, 153, 0));
        lblnotifi.setText("jLabel17");
        jPanel4.add(lblnotifi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 70, 127, 23));

        lbl_nxtEvent.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_nxtEvent.setText("jLabel1");
        jPanel4.add(lbl_nxtEvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 30, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_quickview-icon.png"))); // NOI18N
        jButton1.setText("View All Events");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, 270, -1));

        byDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        byDate.setText("By Date");
        byDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                byDateActionPerformed(evt);
            }
        });
        jPanel4.add(byDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 120, 20));

        overviewTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        overviewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Event ID", "Event Name", "Starting Date", "End Date", "Event Address", "Vehicle Appr.Emp", "Event Handler"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        overviewTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                overviewTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(overviewTable);
        if (overviewTable.getColumnModel().getColumnCount() > 0) {
            overviewTable.getColumnModel().getColumn(0).setPreferredWidth(11);
        }

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        vehicleOverviewTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        vehicleOverviewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "C.No", "V. Model", "M.Year", "V.Colour", "V.Condition"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(vehicleOverviewTable);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_descrip.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Event Description", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        description.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        description.setText("jLabel1");
        description.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout panel_descripLayout = new javax.swing.GroupLayout(panel_descrip);
        panel_descrip.setLayout(panel_descripLayout);
        panel_descripLayout.setHorizontalGroup(
            panel_descripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_descripLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(description, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_descripLayout.setVerticalGroup(
            panel_descripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_descripLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(description, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_updateRedirect.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_updateRedirect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_update.png"))); // NOI18N
        btn_updateRedirect.setText("Update Event");
        btn_updateRedirect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateRedirectActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_reports.png"))); // NOI18N
        jButton3.setText("Generate Report");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout eventOverviewLayout = new javax.swing.GroupLayout(eventOverview);
        eventOverview.setLayout(eventOverviewLayout);
        eventOverviewLayout.setHorizontalGroup(
            eventOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventOverviewLayout.createSequentialGroup()
                .addGroup(eventOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(eventOverviewLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1001, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(eventOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel_descrip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(eventOverviewLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btn_updateRedirect)
                                .addGap(43, 43, 43)
                                .addComponent(jButton3)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        eventOverviewLayout.setVerticalGroup(
            eventOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(eventOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(eventOverviewLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panel_descrip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(eventOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_updateRedirect, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(38, 38, 38))
        );

        eventFrame.addTab("Event Overview", eventOverview);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Event Name");

        ename.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Event Description");

        edes.setColumns(20);
        edes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edes.setRows(5);
        jScrollPane1.setViewportView(edes);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Starting Date");

        sdate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("End Date");

        edate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Event Location");

        eloc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eloc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elocActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Vehicle Approved Person ");

        vapprovedPerson.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        vapprovedPerson.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vapprovedPersonKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Event Handler");

        ehandler.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ehandler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ehandlerKeyPressed(evt);
            }
        });

        addEvents.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        addEvents.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_add_big.png"))); // NOI18N
        addEvents.setText("Add Events");
        addEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEventsActionPerformed(evt);
            }
        });

        btn_eUpdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_eUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_update.png"))); // NOI18N
        btn_eUpdate.setText("Update Event");
        btn_eUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eUpdateActionPerformed(evt);
            }
        });

        btn_eDelete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_eDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_garbage_delete.png"))); // NOI18N
        btn_eDelete.setText("Delete Event");
        btn_eDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eDeleteActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_previous_256.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "All Vehicles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        allVehiclesTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        allVehiclesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chassis No", "Vehicle Model", "Manufactured Year", "Vehicle Colour", "Vehicle Condition"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        allVehiclesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allVehiclesTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(allVehiclesTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selected Vehicles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        vAddbtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        vAddbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_add_big.png"))); // NOI18N
        vAddbtn.setText("Add Vehicles");
        vAddbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vAddbtnActionPerformed(evt);
            }
        });

        deleteVehicle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        deleteVehicle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_garbage_delete.png"))); // NOI18N
        deleteVehicle.setText("Delete Vehicles");
        deleteVehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteVehicleActionPerformed(evt);
            }
        });

        eVehicleTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eVehicleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chassis No", "Vehicle Model", "Manufactured Year", "Vehicle Colour", "Vehicle Condition"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        eVehicleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eVehicleTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(eVehicleTable);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 319, Short.MAX_VALUE)
                        .addComponent(vAddbtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteVehicle)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vAddbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout handleEventLayout = new javax.swing.GroupLayout(handleEvent);
        handleEvent.setLayout(handleEventLayout);
        handleEventLayout.setHorizontalGroup(
            handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handleEventLayout.createSequentialGroup()
                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handleEventLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(handleEventLayout.createSequentialGroup()
                                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8))
                                .addGap(107, 107, 107)
                                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1)
                                    .addComponent(eloc)))
                            .addGroup(handleEventLayout.createSequentialGroup()
                                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(141, 141, 141)
                                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(edate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ename, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(handleEventLayout.createSequentialGroup()
                                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(46, 46, 46)
                                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(vapprovedPerson, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                                    .addComponent(ehandler))))
                        .addGap(158, 158, 158))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, handleEventLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addEvents)
                        .addGap(29, 29, 29)
                        .addComponent(btn_eUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_eDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        handleEventLayout.setVerticalGroup(
            handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handleEventLayout.createSequentialGroup()
                .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(handleEventLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(ename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(sdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(edate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(handleEventLayout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jLabel5)
                                .addGap(54, 54, 54))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, handleEventLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(eloc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(vapprovedPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(ehandler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(handleEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(handleEventLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        eventFrame.addTab("Handle Events", handleEvent);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Vehicles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        newChassis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Chassis No");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Vehicle Model");

        newVModel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        newVModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newVModelActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Manufactured Year");

        newManuYear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        newManuYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newManuYearActionPerformed(evt);
            }
        });
        newManuYear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newManuYearKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Vehicle Colour");

        newVColour.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        newVCondition.setColumns(20);
        newVCondition.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        newVCondition.setRows(5);
        jScrollPane6.setViewportView(newVCondition);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Vehicle Condition");

        allVehicleTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        allVehicleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chassis No", "Vehicle Model", "Manufactured Year", "Vehicle Colour", "Vehicle Condition"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        allVehicleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allVehicleTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(allVehicleTable);

        addNewVehicle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        addNewVehicle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_add_big.png"))); // NOI18N
        addNewVehicle.setText("Add Vehicles");
        addNewVehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewVehicleActionPerformed(evt);
            }
        });

        deleteVehicle1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        deleteVehicle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_garbage_delete.png"))); // NOI18N
        deleteVehicle1.setText("Delete Vehicles");
        deleteVehicle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteVehicle1ActionPerformed(evt);
            }
        });

        updateNewVehicle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        updateNewVehicle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_update.png"))); // NOI18N
        updateNewVehicle.setText("Update Vehicles");
        updateNewVehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateNewVehicleActionPerformed(evt);
            }
        });

        clearVFields.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clearVFields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_previous_256.png"))); // NOI18N
        clearVFields.setText("Clear");
        clearVFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearVFieldsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel15)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel19)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(newVColour)
                            .addComponent(newVModel)
                            .addComponent(newChassis)
                            .addComponent(newManuYear)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addNewVehicle)
                        .addGap(18, 18, 18)
                        .addComponent(updateNewVehicle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deleteVehicle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clearVFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(465, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(newChassis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(newVModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(newManuYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(newVColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNewVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateNewVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteVehicle1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(clearVFields, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 371, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 164, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        eventFrame.addTab("Handle Vehicles", jPanel1);

        getContentPane().add(eventFrame, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("Event Management");

        lgTime.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lgTime.setText("jLabel4");

        lgDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lgDate.setText("jLabel4");

        stringDay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        stringDay.setText("Date");

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 931, Short.MAX_VALUE)
                        .addComponent(lgDate, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(stringDay, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lgTime, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jButton7)
                .addContainerGap())
            .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(104, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lgDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(stringDay, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lgTime))
                        .addGap(9, 9, 9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void elocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_elocActionPerformed

    private void addEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEventsActionPerformed
        String eName=ename.getText();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDate=sdf.format(sdate.getDate());
        String endDate=sdf.format(edate.getDate());
        String descrip=edes.getText();
        String eLocation=eloc.getText();
        String vApprovedPerson=vapprovedPerson.getText();
        String eHandler=ehandler.getText();
        
        addEvents(eName,startDate,endDate,descrip,eLocation,vApprovedPerson,eHandler);
       
        
    }//GEN-LAST:event_addEventsActionPerformed

    private void addEvents(String eName,String startDate,String endDate,String descrip,String eLocation,String vApprovedPerson,String eHandler)
        {
            int eid=0;
            int vid=0;
        if(!eName.equals("") && !eLocation.equals("") && !vApprovedPerson.equals("") && !eHandler.equals(""))
        {
             try
             {
           
                    String eventQuery = "INSERT INTO event(event_name,from_date,to_date,event_descrip,event_address,vehicle_approved_emp,event_handler)values ('"+eName+"','"+startDate+"','"+endDate+"','"+descrip+"','"+eLocation+"','"+vApprovedPerson +"','"+eHandler+"')";
                    pst=con.prepareStatement(eventQuery);
                    pst.execute();

                   String getLastRow="SELECT * FROM event order by event_id DESC limit 1";
                   pst=con.prepareStatement(getLastRow);
                   rs=pst.executeQuery();
                   while(rs.next())
                   {
                       eid=rs.getInt("event_id");
                   }

                   for(int a=0;a<eVehicleTable.getRowCount();a++)
                   {
                        String chassis=eVehicleTable.getValueAt(a, 0).toString();
                        String getVid_query="SELECT vehicle_id FROM event_vehicle WHERE chassis_no='"+chassis+"' ";
                        pst=con.prepareStatement(getVid_query);
                        rs=pst.executeQuery();
                        while(rs.next())
                        {
                            vid=rs.getInt("vehicle_id");
                        }
                        String eVehicle_query="INSERT INTO evtable(event_id,vehicle_id) values ('"+eid+"','"+vid+"')";
                        pst=con.prepareStatement(eVehicle_query);
                        pst.execute();
                    }
                 JOptionPane.showMessageDialog(null, "Event Added Successfully !");
            }
           catch(Exception ex)
           {
                  JOptionPane.showMessageDialog(null,"Adding New Event is Unsuccessful ! "+ex);
           }
           clearFields();
           overviewTable_Load();
           eventFrame.setSelectedIndex(0);
        }
        else
            JOptionPane.showMessageDialog(null,"Some of the Required Fields are Missing !");
    }        
     public static ArrayList<ArrayList<Object>> FinalArray = new ArrayList<>();
     
    private void vAddbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vAddbtnActionPerformed
        String findChassis=null;
        boolean check=false;
        row=allVehiclesTable.getSelectedRow();
         if(row != -1)
         {
            String chassis=allVehiclesTable.getValueAt(row, 0).toString();
            String vModel=allVehiclesTable.getValueAt(row, 1).toString();
            String manuYear=allVehiclesTable.getValueAt(row, 2).toString();
            String vColour=allVehiclesTable.getValueAt(row, 3).toString();
            String vCondition=allVehiclesTable.getValueAt(row, 4).toString();
            for(int a=0;a<eVehicleTable.getRowCount();a++)
            {
               findChassis=eVehicleTable.getValueAt(a,0).toString();
            
                if(findChassis.equals(chassis))
                {
                   check=true;
                }
            }
                if(check==false)
                {
                    DefaultTableModel model = (DefaultTableModel) eVehicleTable.getModel();
                     model.addRow(new Object[]{chassis, vModel,manuYear,vColour,vCondition});
                     allVehiclesTable.clearSelection();
                }
                else
                    JOptionPane.showMessageDialog(null, "This Vehicle Is Already Existed !");
         }
         else
              JOptionPane.showMessageDialog(null,"Please Select a Row First !");
         
                
                
    }//GEN-LAST:event_vAddbtnActionPerformed
    private void addVehicles(String chassisNo,String vmodel,String manufacYear,String vcolour,String vcondition)
    {
        if(!chassisNo.equals("") && !vmodel.equals("") && !manufacYear.equals("") && !vcolour.equals(""))
                {
                    if(decide==1)
                    {
                         row=overviewTable.getSelectedRow();
                         eid=overviewTable.getValueAt(row, 0).toString();
                         String update_add_vehicle_query="INSERT INTO event_vehicle(chassis_no,vehicle_model,manufac_year,vehicle_colour,vehicle_status,event_id) VALUES ('"+chassisNo+"','"+vmodel+"','"+manufacYear+"','"+vcolour+"','"+vcondition+"','"+eid+"')";
                         try
                         {
                              pst=con.prepareStatement(update_add_vehicle_query);
                              pst.execute();
                              eventVehicleTable_Load();
                         }
                         catch(Exception e)
                         {
                             JOptionPane.showMessageDialog(null, "Adding Vehicles when Updating is Unsuccessful !"+e);
                         }

                    }
                    else
                    {
                        DefaultTableModel model = (DefaultTableModel) allVehiclesTable.getModel();
                        model.addRow(new Object[]{chassisNo, vmodel,manufacYear,vcolour,vcondition});

                    }
                  newChassis.setText("");
                  newVModel.setText("");
                  newManuYear.setText("");
                  newVColour.setText("");
                  newVCondition.setText("");
                  vehicleOverviewTable_Load();
               }
               else
                  JOptionPane.showMessageDialog(null,"Some of the Required Fields are Empty!");
    }
    
    int row;
    private void allVehiclesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allVehiclesTableMouseClicked
        row=allVehiclesTable.getSelectedRow();
    }//GEN-LAST:event_allVehiclesTableMouseClicked
    
    private void deleteVehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteVehicleActionPerformed
        row=eVehicleTable.getSelectedRow();
        if(row != -1)
        {
            int choose=JOptionPane.showConfirmDialog(null, "Do You Really Want to Remove the Vehicle ?");
            if(choose==0)
              {
                   ((DefaultTableModel)eVehicleTable.getModel()).removeRow(row);
              }
            
        }
        else
            JOptionPane.showMessageDialog(null, "To Remove You Want to Select a Row First");
           
    }//GEN-LAST:event_deleteVehicleActionPerformed

    private void deleteVehicle()
    {
        int vid=0;
        String vidString=null;
        row=allVehicleTable.getSelectedRow();
         if(row != -1)
           {
              int choose=JOptionPane.showConfirmDialog(null, "Do You Really Want to DELETE ?");
              if(choose==0)
              {
                     
                    try
                    {
                        row=allVehicleTable.getSelectedRow();
                        String tempChassis=allVehicleTable.getValueAt(row, 0).toString();
                        String getVidQuery="SELECT vehicle_id FROM event_vehicle WHERE chassis_no='"+tempChassis+"'";
                        pst=con.prepareStatement(getVidQuery);
                        rs=pst.executeQuery();
                        while(rs.next())
                        {
                            vid=rs.getInt("vehicle_id");
                        }
                        vidString=String.valueOf(vid);

                      String deleteVehicleQuery="DELETE FROM event_vehicle WHERE vehicle_id ='"+vidString+"' ";
                      pst=con.prepareStatement(deleteVehicleQuery);
                      pst.execute();
                      allVehicleTable_Load();
                 
                   }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null, "Vehicle Deletion Error ! "+e);
                    }
                    
                    clearVehicleFields();
          }
         
         }
         else
             JOptionPane.showMessageDialog(null, "To Delete,Please Select a Row First");
    }
    
    private void vehicleUpdate()
    {
        row=allVehicleTable.getSelectedRow();
        if(row != -1)
           {
                int choose=JOptionPane.showConfirmDialog(null, "Do You Really Want to UPDATE ?");
                if(choose==0)
                {
                    String tempChassis=allVehiclesTable.getValueAt(row, 0).toString();
                    String getVidQuery="SELECT vehicle_id FROM event_vehicle WHERE chassis_no='"+tempChassis+"'";
                    int vid=0;
                    String vidString=null;
                    try
                    {
                        pst=con.prepareStatement(getVidQuery);
                        rs=pst.executeQuery();
                        while(rs.next())
                        {
                            vid=rs.getInt("vehicle_id");
                        }
                        vidString=String.valueOf(vid);
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null, "Retrieving Vehicle Id is Unsuccessful !"+e);
                    }
                    String chassis_no=newChassis.getText();
                    String v_model=newVModel.getText();
                    String manu_year=newManuYear.getText();
                    String v_colour=newVColour.getText();
                    String v_condition=newVCondition.getText();
                    String updateEventVehicleQuery="UPDATE event_vehicle SET chassis_no='"+chassis_no+"',vehicle_model='"+v_model+"',manufac_year='"+manu_year+"',vehicle_colour='"+v_colour+"',vehicle_status='"+v_condition+"' WHERE vehicle_id='"+vidString+"' ";
                    try{
                              pst=con.prepareStatement(updateEventVehicleQuery);
                              pst.execute();
                              allVehicleTable_Load();
                       }
                    catch(Exception e)
                    {
                            JOptionPane.showMessageDialog(null, "Updating Event Vehicle is Unsuccessful !"+e);
                    }


                      clearVehicleFields();
                }
          }
        else
             JOptionPane.showMessageDialog(null, "To Update,Please Select a Row First");
    }
            
     private void viewAllEvents()
     {
         String pastEventQuery="SELECT event_id,event_name,from_date,to_date,event_address,vehicle_approved_emp,event_handler FROM event WHERE from_date > CURDATE()";
         try
           {
                   pst=con.prepareStatement(pastEventQuery);
                  rs=pst.executeQuery();
                  overviewTable.setModel(DbUtils.resultSetToTableModel(rs));
                  
           }
           catch(Exception ex)
           {
              JOptionPane.showMessageDialog(null, "Retrieving Upcoming Events Unsuccessful !"+ex);
           }      
       
            
        
        DefaultTableModel model = (DefaultTableModel) vehicleOverviewTable.getModel();
        model.setRowCount(0);
        jPanel7.setVisible(false);
        panel_descrip.setVisible(false);
        btn_updateRedirect.setEnabled(false);
     }
    
    private void overviewTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_overviewTableMouseClicked
       
        btn_updateRedirect.setEnabled(true);
        vehicleOverviewTable_Load();
        jPanel7.setVisible(true);
        row=overviewTable.getSelectedRow();
        panelDescrip_Load();
        panel_descrip.setVisible(true);
        
        
        
    }//GEN-LAST:event_overviewTableMouseClicked

    
    private void search(String sName,String fromDate,String toDate)
    {
         if(byDate.isSelected()==true && !searchName.getText().equals(""))
        {
            String nameDateQuery="SELECT event_id,event_name,from_date,to_date,event_address,vehicle_approved_emp,event_handler FROM event WHERE event_name LIKE '%"+sName+"%' AND from_date>= '"+fromDate+"' AND to_date <= '"+toDate+"'";
            try 
            {
                    pst=con.prepareStatement(nameDateQuery);
                    rs=pst.executeQuery();
                    overviewTable.setModel(DbUtils.resultSetToTableModel(rs));

            } 
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Searching From Name & Start Date, Unsuccessful !"+e);
            }
        }
        
       else if(byDate.isSelected()==false && !searchName.getText().equals(""))
        {
            String nameQuery="SELECT event_id,event_name,from_date,to_date,event_address,vehicle_approved_emp,event_handler FROM event WHERE event_name LIKE '%"+sName+"%'";
            try 
            {
                    pst=con.prepareStatement(nameQuery);
                    rs=pst.executeQuery();
                    overviewTable.setModel(DbUtils.resultSetToTableModel(rs));
            } 
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Searching From Name, Unsuccessful ! "+e);
            }
        }
        
       else if(byDate.isSelected()==true && searchName.getText().equals(""))
        {
            String nameDateQuery="SELECT event_id,event_name,from_date,to_date,event_address,vehicle_approved_emp,event_handler FROM event WHERE from_date>= '"+fromDate+"' AND to_date <= '"+toDate+"'";
            try 
            {
                    pst=con.prepareStatement(nameDateQuery);
                    rs=pst.executeQuery();
                    overviewTable.setModel(DbUtils.resultSetToTableModel(rs));

            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, "Searching From Date, Unsuccessful !"+e);
            }
        }
       else if(byDate.isSelected()==false && searchName.getText().equals(""))
       {
           JOptionPane.showMessageDialog(null, "Please Search using Name or Date !");
       }
    }
    
    
    private void btn_eUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eUpdateActionPerformed
      
      eventUpdate();
       
    }//GEN-LAST:event_btn_eUpdateActionPerformed

    private void eventUpdate()
    {
        int vid=0;
        int availableVid=0;
        int choose=JOptionPane.showConfirmDialog(null, "Do You Really Want to Update Event Details ?");
        if(choose==0)
        {
            if(decide==1)
            {
              row=overviewTable.getSelectedRow();
              eid=overviewTable.getValueAt(row, 0).toString();
              String eName=ename.getText();
              SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
              String startDate=sdf.format(sdate.getDate());
              String endDate=sdf.format(edate.getDate());
              String descrip=edes.getText();
              String eLocation=eloc.getText();
              String vApprovedPerson=vapprovedPerson.getText();
              String eHandler=ehandler.getText();
              String updateQuery="UPDATE event SET event_name='"+eName+"',from_date='"+startDate+"',to_date='"+endDate+"',event_descrip='"+descrip+"',event_address='"+eLocation+"',vehicle_approved_emp='"+vApprovedPerson+"',event_handler='"+eHandler+"' WHERE event_id='"+eid+"'";
              if(!eName.equals("") && !eLocation.equals("") && !vApprovedPerson.equals("") && !eHandler.equals(""))
              {
                  try
                  {
                     pst=con.prepareStatement(updateQuery);
                     pst.execute();
                     String deletetoUpdate_query="DELETE FROM evtable WHERE event_id='"+eid+"' ";
                     pst=con.prepareStatement(deletetoUpdate_query);
                     pst.execute();
                     for(int a=0;a<eVehicleTable.getRowCount();a++)
                     {
                            String chassis=eVehicleTable.getValueAt(a, 0).toString();
                            String getVid_query="SELECT vehicle_id FROM event_vehicle WHERE chassis_no='"+chassis+"' ";
                            pst=con.prepareStatement(getVid_query);
                            rs=pst.executeQuery();
                            while(rs.next())
                            {
                                vid=rs.getInt("vehicle_id");
                            }
                            String eVehicle_query="INSERT INTO evtable(event_id,vehicle_id) values ('"+eid+"','"+vid+"')";
                            pst=con.prepareStatement(eVehicle_query);
                            pst.execute();
                     }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
                     JOptionPane.showMessageDialog(null, "Updating Successful !");
                     eventFrame.setSelectedIndex(0);
                     clearFields();
                     overviewTable_Load();
                     eid=null;
                     btn_eUpdate.setEnabled(false);
                     btn_eDelete.setEnabled(false);   
                     addEvents.setEnabled(true); 

                  } 
               catch (Exception e)
                {
                      JOptionPane.showMessageDialog(null, "Update Event Error !"+e);
                }
             }
             else
              {
                  JOptionPane.showMessageDialog(null,"Some of the Required Fields are Empty!");
              }
           }
              decide=0;
              addEvents.setEnabled(true);
              jPanel7.setVisible(false);
              panel_descrip.setVisible(false);
              btn_updateRedirect.setEnabled(false);
       }
    }
    
    private void btn_updateRedirectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateRedirectActionPerformed
        decide=1;
        updateRedirect();
        
    }//GEN-LAST:event_btn_updateRedirectActionPerformed

    private void updateRedirect()
    {
        row=overviewTable.getSelectedRow();
        if(row!=-1)
        {
            try
            {
             String endDate=overviewTable.getValueAt(row, 3).toString();
             SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
             Date d2=sdf.parse(endDate);
             Date date=new Date();
             if(date.before(d2))
             {
                btn_eUpdate.setEnabled(true);
                btn_eDelete.setEnabled(true);   
                addEvents.setEnabled(false);
                clearFields();
                eventFrame.setSelectedIndex(1);
                String eid=overviewTable.getValueAt(row, 0).toString();
                ename.setText(overviewTable.getValueAt(row, 1).toString());
                String startDate=overviewTable.getValueAt(row, 2).toString();
               // String endDate=overviewTable.getValueAt(row, 3).toString();
               // SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                Date d1=sdf.parse(startDate);
               // Date d2=sdf.parse(endDate);
                sdate.setDate(d1);
                edate.setDate(d2);
                edes.setText(description.getText());
                eloc.setText(overviewTable.getValueAt(row, 4).toString());
                vapprovedPerson.setText(overviewTable.getValueAt(row, 5).toString());
                ehandler.setText(overviewTable.getValueAt(row, 6).toString());
                eventVehicleTable_Load();
             }
             else
             {
                 JOptionPane.showMessageDialog(null, "This Event was already held, Can't Update !");
             }
             
            } 
            catch (ParseException ex) 
            {
                 JOptionPane.showMessageDialog(null, "Loading Data to Update is Unsuccessful !"+ex);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please Select a Row to Update.");
        }
    }
    
    private void btn_eDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eDeleteActionPerformed
      
        eventDelete();
    }//GEN-LAST:event_btn_eDeleteActionPerformed

    private void eventDelete()
    {
       int choose=JOptionPane.showConfirmDialog(null, "Do You Really Want to DELETE WHOLE EVENT ? ");
       if(choose==0)
       {
            row=overviewTable.getSelectedRow();
            eid=overviewTable.getValueAt(row, 0).toString();
            String deleteEventQuery="DELETE FROM event WHERE event_id='"+eid+"' ";
            String deleteEventVehicleQuery="DELETE FROM evtable WHERE event_id='"+eid+"' ";
            try
            {
                pst=con.prepareStatement(deleteEventVehicleQuery);
                pst.execute();
                pst=con.prepareStatement(deleteEventQuery);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Deleting Event Successful !");
                overviewTable_Load();
                eventFrame.setSelectedIndex(0);
                eid=null;
                   btn_eUpdate.setEnabled(false);
                   btn_eDelete.setEnabled(false);   
                  addEvents.setEnabled(true); 
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Deleting Event Unsuccessful ! "+e);
            }

              clearFields();
       }
    }
    
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
          clearFields();
          btn_eUpdate.setEnabled(false);
          btn_eDelete.setEnabled(false);
          addEvents.setEnabled(true); 
          overviewTable.clearSelection();
          jPanel7.setVisible(false);
          panel_descrip.setVisible(false);
          description.setText("");
          btn_updateRedirect.setEnabled(false);
          decide=0;
    }//GEN-LAST:event_btnClearActionPerformed

    private void loadAllEvents()
    {
        String allEventQuery="SELECT event_id,event_name,from_date,to_date,event_address,vehicle_approved_emp,event_handler FROM event";
        try
        {
            pst=con.prepareStatement(allEventQuery);
            rs=pst.executeQuery();
            overviewTable.setModel(DbUtils.resultSetToTableModel(rs));
                  
        }
        catch(Exception ex)
        {
           JOptionPane.showMessageDialog(null, "Retrieving All Events Unsuccessful !"+ex);
        }      
        DefaultTableModel model = (DefaultTableModel) vehicleOverviewTable.getModel();
        model.setRowCount(0);
    }
    
    private void vapprovedPersonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vapprovedPersonKeyPressed
       validateString(vapprovedPerson);
    }//GEN-LAST:event_vapprovedPersonKeyPressed

    private void ehandlerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ehandlerKeyPressed
          validateString(ehandler);
    }//GEN-LAST:event_ehandlerKeyPressed

    private void newVModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newVModelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newVModelActionPerformed

    private void newManuYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newManuYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newManuYearActionPerformed

    private void newManuYearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newManuYearKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_newManuYearKeyPressed

    private void allVehicleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allVehicleTableMouseClicked
       row=allVehicleTable.getSelectedRow();
       newChassis.setText(allVehicleTable.getValueAt(row, 0).toString());
       newVModel.setText(allVehicleTable.getValueAt(row, 1).toString());
       newManuYear.setText(allVehicleTable.getValueAt(row, 2).toString());
       newVColour.setText(allVehicleTable.getValueAt(row, 3).toString());
       newVCondition.setText(allVehicleTable.getValueAt(row, 4).toString());
    }//GEN-LAST:event_allVehicleTableMouseClicked

    private void addNewVehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewVehicleActionPerformed
       String chassis=newChassis.getText();
       String vModel=newVModel.getText();
       String manuYear=newManuYear.getText();
       String vColour=newVColour.getText();
       String vCondition=newVCondition.getText();
       String addNewVehicle_query="INSERT INTO event_vehicle(chassis_no,vehicle_model,manufac_year,vehicle_colour,vehicle_status)VALUES('"+chassis+"','"+vModel+"','"+manuYear+"','"+vColour+"','"+vCondition+"')";
       if(!chassis.equals("") && !vModel.equals("") && !manuYear.equals("") && !vColour.equals("") )
       { 
             try 
             {
             
                    pst=con.prepareStatement(addNewVehicle_query);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Vehicle Added Successfully !");
                    allVehicleTable_Load();
                    clearVehicleFields();
              }
             catch (Exception e) 
             {
                    JOptionPane.showMessageDialog(null, "Insering New Vehicles is UnSuccessful !");
             }
       }
       else
            JOptionPane.showMessageDialog(null, "Some of the Required Fieds are Missing !");
        
    }//GEN-LAST:event_addNewVehicleActionPerformed

    private void deleteVehicle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteVehicle1ActionPerformed
        deleteVehicle();
    }//GEN-LAST:event_deleteVehicle1ActionPerformed

    private void updateNewVehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateNewVehicleActionPerformed
       vehicleUpdate();
    }//GEN-LAST:event_updateNewVehicleActionPerformed

    private void eVehicleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eVehicleTableMouseClicked
        row=eVehicleTable.getSelectedRow();
        
    }//GEN-LAST:event_eVehicleTableMouseClicked

    private void clearVFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearVFieldsActionPerformed
      clearVehicleFields();
    }//GEN-LAST:event_clearVFieldsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadAllEvents();
        jPanel7.setVisible(false);
        panel_descrip.setVisible(false);
        btn_updateRedirect.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void byDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_byDateActionPerformed
        if(byDate.isSelected()==true)
        {
            searchFromDate.setVisible(true);
            searchToDate.setVisible(true);
        }
        else
        {
            searchFromDate.setVisible(false);
            searchToDate.setVisible(false);
        }
    }//GEN-LAST:event_byDateActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        String sName=searchName.getText();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fromDate=sdf.format(searchFromDate.getDate());
        String toDate=sdf.format(searchToDate.getDate());

        search(sName,fromDate,toDate);
    }//GEN-LAST:event_btn_searchActionPerformed

    private void viewAllEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAllEventsActionPerformed

        viewAllEvents();
    }//GEN-LAST:event_viewAllEventsActionPerformed

    private void searchNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchNameActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            
          try {
            
            String report = "C:\\Users\\chathura\\Documents\\NetBeansProjects\\voltage\\src\\reports\\event.jrxml";
            JasperReport jp = JasperCompileManager.compileReport(report);
            JasperPrint jd = JasperFillManager.fillReport(jp, null,con);
            JasperViewer.viewReport(jd,false);
            
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
            java.util.logging.Logger.getLogger(EventManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EventManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EventManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EventManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EventManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEvents;
    private javax.swing.JButton addNewVehicle;
    private javax.swing.JTable allVehicleTable;
    private javax.swing.JTable allVehiclesTable;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btn_eDelete;
    private javax.swing.JButton btn_eUpdate;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_updateRedirect;
    private javax.swing.JCheckBox byDate;
    private javax.swing.JButton clearVFields;
    private javax.swing.JButton deleteVehicle;
    private javax.swing.JButton deleteVehicle1;
    private javax.swing.JLabel description;
    private javax.swing.JTable eVehicleTable;
    private com.toedter.calendar.JDateChooser edate;
    private javax.swing.JTextArea edes;
    private javax.swing.JTextField ehandler;
    private javax.swing.JTextField eloc;
    private javax.swing.JTextField ename;
    private javax.swing.JTabbedPane eventFrame;
    private javax.swing.JPanel eventOverview;
    private javax.swing.JPanel handleEvent;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbl_nxtEvent;
    private javax.swing.JLabel lblnotifi;
    private javax.swing.JLabel lgDate;
    private javax.swing.JLabel lgTime;
    private javax.swing.JTextField newChassis;
    private javax.swing.JTextField newManuYear;
    private javax.swing.JTextField newVColour;
    private javax.swing.JTextArea newVCondition;
    private javax.swing.JTextField newVModel;
    private javax.swing.JTable overviewTable;
    private javax.swing.JPanel panel_descrip;
    private com.toedter.calendar.JDateChooser sdate;
    private com.toedter.calendar.JDateChooser searchFromDate;
    private javax.swing.JTextField searchName;
    private com.toedter.calendar.JDateChooser searchToDate;
    private javax.swing.JLabel stringDay;
    private javax.swing.JButton updateNewVehicle;
    private javax.swing.JButton vAddbtn;
    private javax.swing.JTextField vapprovedPerson;
    private javax.swing.JTable vehicleOverviewTable;
    private javax.swing.JButton viewAllEvents;
    // End of variables declaration//GEN-END:variables
}
