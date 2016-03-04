/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package voltage;

import com.sun.glass.events.KeyEvent;
import connections.DB_Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author chathura
 */
public class Login extends javax.swing.JFrame {

    
    
    Connection con=null;
    PreparedStatement pst=null;
    ResultSet rs = null;
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        con=DB_Connect.getConnection();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        login_pass = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        username = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setText("VOLTAGE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jLabel1)
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Username: ");

        jLabel3.setText("Password: ");

        login_pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                login_passKeyPressed(evt);
            }
        });

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        username.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(login_pass, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(username))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(login_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        String division = choose_division.getSelectedItem().toString();
//        String logpass = login_pass.getText();
//        
//        System.out.println(logpass);
//        
//        if (division.equals("Finance Division") && logpass.equals("diyath"))
//        {
//          new Fdivision().setVisible(true);
//          this.dispose();
//        }
//        else if (division.equals("Service Division") && logpass.equals("chathura"))
//        {
//            new Sdivision().setVisible(true);
//            this.dispose();
//        }
//        else if (division.equals("Customer Relations") && logpass.equals("nipuni"))
//        {
//            new Crelations().setVisible(true);
//            this.dispose();
//        }
//        else
//        {
//            JOptionPane.showMessageDialog(this, "Invalid Password");
//        }
//        
        
        
        
        
        
        
        
         String password;
         password = new String(login_pass.getPassword());
        // String uname = choose_division.getSelectedItem().toString();
         // password = String.valueOf(jPasswordField1.getPassword());
        String u_type = "SELECT department from employee WHERE Username = '"+username.getText()+"'";
        
       /* try {
            pst = con.prepareStatement(u_type);
            rs = pst.executeQuery();
            
            while(rs.next()){
                type = rs.getString("Type_ID");
            }
            //String type = pst.setString(WIDTH, u_type)
           
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        String sql1 = "SELECT * FROM employee where username=? and password=?";
       
        try {
            
            pst = con.prepareStatement(u_type);
            rs = pst.executeQuery();
            String type = null;
            while(rs.next()){
                type = rs.getString("department");
            }
            
            
             
            pst = con.prepareStatement(sql1);
            pst.setString(1, username.getText());
            pst.setString(2,password );
            rs=pst.executeQuery();
            
            if(rs.next()){
                
                
                if(type.equals("Financial Division")){
                    //System.out.println(type);
                   
                    
                    Fdivision dev = new Fdivision();
                    dev.setVisible(true);
                    this.dispose();
                 
                    
               }else if(type.equals("Service Division")){
                  
                    Sdivision dev =  new Sdivision();
                    dev.setVisible(true);
                    this.dispose();
                } else if(type.equals("Customer Relations")){
                    
                    Crelations dev = new Crelations();
                    dev.setVisible(true);
                    this.dispose();
                }
               
           }else{
                JOptionPane.showMessageDialog(null,"Invlaid user name or password");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void login_passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_login_passKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        
               String password;
         password = new String(login_pass.getPassword());
         // password = String.valueOf(jPasswordField1.getPassword());
        String u_type = "SELECT department from employee WHERE Username = '"+username.getText()+"'";
        
       /* try {
            pst = con.prepareStatement(u_type);
            rs = pst.executeQuery();
            
            while(rs.next()){
                type = rs.getString("Type_ID");
            }
            //String type = pst.setString(WIDTH, u_type)
           
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        String sql1 = "SELECT * FROM employee where username=? and password=?";
       
        try {
            
             pst = con.prepareStatement(u_type);
            rs = pst.executeQuery();
            String type = null;
            while(rs.next()){
                type = rs.getString("department");
            }
            
            
             
            pst = con.prepareStatement(sql1);
            pst.setString(1, username.getText());
            pst.setString(2,password );
            rs=pst.executeQuery();
            
            if(rs.next()){
                
                
                 if(type.equals("Financial Division")){
                    //System.out.println(type);
                   
                    
                    Fdivision dev = new Fdivision();
                    dev.setVisible(true);
                    this.dispose();
                 
                    
               }else if(type.equals("Service Division")){
                  
                    Sdivision dev =  new Sdivision();
                    dev.setVisible(true);
                    this.dispose();
                } else if(type.equals("Customer Relations")){
                    
                    Crelations dev = new Crelations();
                    dev.setVisible(true);
                    this.dispose();
                }
            
                
            }else{
                JOptionPane.showMessageDialog(null,"Invlaid user name or password");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        
        
        }
        
    }                                        

    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {                                           
        
//        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//        
//               String password;
//         password = new String(jPasswordField1.getPassword());
//         // password = String.valueOf(jPasswordField1.getPassword());
//        String u_type = "SELECT User_Type_Name from user WHERE Username = '"+jTextField1.getText()+"'";
//        
//       /* try {
//            pst = con.prepareStatement(u_type);
//            rs = pst.executeQuery();
//            
//            while(rs.next()){
//                type = rs.getString("Type_ID");
//            }
//            //String type = pst.setString(WIDTH, u_type)
//           
//        } catch (SQLException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        }*/
//        String sql1 = "SELECT * FROM user where Username=? and password=?";
//       
//        try {
//            
//             pst = con.prepareStatement(u_type);
//            rs = pst.executeQuery();
//            String type = null;
//            while(rs.next()){
//                type = rs.getString("User_Type_Name");
//            }
//            
//            
//             
//            pst = con.prepareStatement(sql1);
//            pst.setString(1, jTextField1.getText());
//            pst.setString(2,password );
//            rs=pst.executeQuery();
//            
//            if(rs.next()){
//                
//                
//                if(type.equals("Salesmen")){
//                    //System.out.println(type);
//                    id=1;
//                    count=-1;
//                    bar.setValue(0);
//                    bar.setStringPainted(true);
//                    tme = new Timer(TWO_SECOND,new TimerListener());
//                    activate();
//                    
//                    //SalesMen smen = new SalesMen();
//                   // smen.setVisible(true);
//                   // this.dispose();
//                 
//                    
//               }else if(type.equals("Sales Manage")){
//                   id=2;
//                   count=-1;
//                    bar.setValue(0);
//                    bar.setStringPainted(true);
//                    tme = new Timer(TWO_SECOND,new TimerListener());
//                    activate();
//                    //SalesManager smgr = new SalesManager();
//                   // smgr.setVisible(true);
//                   // this.dispose();
//                } else if(type.equals("Inventory Manager")){
//                    id=3;
//                    count=-1;
//                    bar.setValue(0);
//                    bar.setStringPainted(true);
//                    tme = new Timer(TWO_SECOND,new TimerListener());
//                    activate();
//                    //InventoryManager imgr = new InventoryManager();
//                   // imgr.setVisible(true);
//                    //this.dispose();
//                }else if(type.equals("Administrator")){
//                    id=4;
//                    count=-1;
//                    bar.setValue(0);
//                    bar.setStringPainted(true);
//                    tme = new Timer(TWO_SECOND,new TimerListener());
//                    activate();
//                    //Administrator admin = new Administrator();
//                   // admin.setVisible(true);
//                    //this.dispose();
//                
//                }
//               
//            
//                
//            }else{
//                JOptionPane.showMessageDialog(null,"Invlaid user name and password");
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        }
//        
//        
//        
//        }
    }//GEN-LAST:event_login_passKeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField login_pass;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}