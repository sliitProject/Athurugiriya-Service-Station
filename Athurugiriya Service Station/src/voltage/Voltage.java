/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package voltage;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.luna.LunaLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
import inventory.ims;
import inventory.ims.mythread1;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author chathura
 */
public class Voltage {

    
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
  
                UIManager.setLookAndFeel(new AluminiumLookAndFeel());
                
                //mythread1 t = new mythread1();
         
            
        new Login().setVisible(true);
}
            
            // Voltage vol = new Voltage();
            //  thread.setDaemon(true);
            
            
            //   new SwingBackgroupWorker().execute();
            
            //   java.awt.EventQueue.invokeLater(new Runnable() {
            //      public void run() {
            //         JFrame jFrame = new JFrame();
            //         jFrame.setSize(200, 200);
            //            jFrame.setVisible(true);
            //     }
            //    });
        
       
       
    }
    
