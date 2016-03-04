/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commonClasses;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author cmjd
 */
public class Validation {

    private static boolean checkInt(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean checkDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void validateInt(JTextField txt) {
        try {
            String s = txt.getText();
            while (!checkInt(s)) {
                String s1 = s.substring(0, s.length() - 1);
                s = s1;
                txt.setText(s1);
            }

        } catch (Exception e) {
        }
    }

    public static void validateInt(JTextField txt, int length) {
        try {
            String s = txt.getText();
            while (!checkInt(s)) {
                String s1 = s.substring(0, s.length() - 1);
                s = s1;
                txt.setText(s1);
            }
            if (s.length() > length) {
                String s1 = s.substring(0, length);
                txt.setText(s1);
            }
        } catch (Exception e) {
        }
    }

    public static void validateDouble(JTextField txt) {
        try {
            String s = txt.getText();
            while (!checkDouble(s)) {
                String s1 = s.substring(0, s.length() - 1);
                s = s1;
                txt.setText(s1);
            }

        } catch (Exception e) {
        }
    }

    private static boolean checkString(String s) {
        try {
            Long.parseLong(s);
            return false;
        } catch (Exception ex) {
            return true;
        }
    }

    public static void validateString(JTextField txt) {
        try {
            String s = txt.getText();

            while (!checkString(s.substring(s.length() - 1, s.length()))) {
                String s1 = s.substring(0, s.length() - 1);
                s = s1;
                txt.setText(s1);
            }

        } catch (Exception e) {
        }
    }


    public static void validateString(JTextField txt,int length) {
        try {
            String s = txt.getText();

            while (!checkString(s.substring(s.length() - 1, s.length()))) {
                String s1 = s.substring(0, s.length() - 1);
                s = s1;
                txt.setText(s1);
            }

            if (s.length() > length) {
                String s1 = s.substring(0, length);
                txt.setText(s1);
            }

        } catch (Exception e) {
        }
    }

    public static void validateString(JTextArea txt) {
        try {
            String s = txt.getText();

            while (!checkString(s.substring(s.length() - 1, s.length()))) {
                String s1 = s.substring(0, s.length() - 1);
                s = s1;
                txt.setText(s1);
            }

        } catch (Exception e) {
        } //To change body of generated methods, choose Tools | Templates.
    }
   
    public static boolean ValidateTime(String time,String status){
        
        int time1=Integer.parseInt(time);
        
        if(time1 >=9 && time1<12 ){
            if("AM".equals(status)){
                
                return true;
                
            }
            else{
                return false;
            }        
        }
        else if(time1==12){
            if("PM".equals(status)){
                return true;
            }
            else{
                return false;
            }
        }else if(time1>=1 && time1<= 5){
            if("PM".equals(status)){
                return true;     
            }
            else{
                return false;
            }
        }else{
            return false;
        }
        
        
    }
    
    public static boolean ValidateDateStringType(String date){
        Calendar cal=new GregorianCalendar();
        String month=String.valueOf(cal.get(Calendar.MONTH)+1);
        String year=String.valueOf(cal.get(Calendar.YEAR));
        String date1=String.valueOf(cal.get(Calendar.DATE));
        
        String CurrDate=year+"-"+month+"-"+date1;
        
        if(CurrDate.equals(date)){
           return true; 
        }else{
           return false;
        }
    }
    
//    public static boolean ValidateDateIntType(String date){
//        Calendar cal=new GregorianCalendar();
//        
//        int month=cal.get(Calendar.MONTH)+1;
//        int year=cal.get(Calendar.YEAR);
//        int date1=cal.get(Calendar.DATE);
//        
//        
//        
//    }
    
    public static boolean validateEmail(JTextField txt) {

        String email=txt.getText();
        
        int atCount = 0, dotCount = 0, atPos, dotPos;
        
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
                atPos = i;
            }

            if (email.charAt(i) == '.') {
                dotCount++;
                dotPos = i;
            }
        }

        if (atCount == 1 && dotCount > 0 && email.length() >= 5) {
            return true;
        } else {
            return false;
        }

    }
    
         public static boolean chk_num(String str){
            for (char c : str.toCharArray()){
                if (!Character.isDigit(c))
                return true; 
            }
                return false;
        }
     
   public static boolean val_nic(String nic){
            if(nic.length()!=10)
                return true;
            else if((nic.charAt(9)!= 'V') || nic.charAt(9)!='X')
                return true;
            else if(chk_num(nic.substring(0,9)))
                return true;
            else   
                return false;
     
} 

}