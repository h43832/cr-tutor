
package ct;

import java.util.*;
import java.io.*;
import javax.swing.*;
public class CTAutoUpdate extends Thread{
  boolean isSleep=false;
  CTModerator moderator;
  Vector waitV=new Vector();
  public CTAutoUpdate(CTModerator m){
    this.moderator=m;
  }
  public void run(){

    while(true){
      while(waitV.size()>0){
        String para[]=moderator.w.csvLineToArray((String)waitV.get(0));
        int type=Integer.parseInt(para[0]);
        String str1="",rc="";
        switch(type){
          case 1:

            if(moderator.tutorMode && moderator.p_chkActivate()) {
              rc="http://cloud-rain.com/web/activate.jsp?a="+moderator.w.e16(System.getProperty("user.country"))+"&rc="+moderator.p_rc+"&o="+
              moderator.w.e16(System.getProperty("os.name"))+"&i="+moderator.w.e16(moderator.w.getGNS(6))+"&m="+moderator.w.e16(moderator.w.getGNS(18))+
                    "&jv="+moderator.w.e16(moderator.w.getGNS(19));
              str1=moderator.w.ap.urltooneline(rc);

              if(str1.indexOf("at:")>-1 && str1.indexOf(":at")>-1){
                rc=str1.substring(str1.indexOf("at:")+3,str1.indexOf(":at")).trim();
                if(rc.length()==14){
                  moderator.p_statuses[5]=rc;
                }
              } 
              if(str1.indexOf("del:")>-1 && str1.indexOf(":del")>-1){
                rc=str1.substring(str1.indexOf("del:")+4,str1.indexOf(":del")).trim();
                if(rc.equalsIgnoreCase("y")){
                  moderator.p_statuses[5]="";
                  moderator.p_statuses[44]="";
                  moderator.p_rc="";
                  if(new File(moderator.p_rcFile).exists()) new File(moderator.p_rcFile).delete();
                }
              } 
              moderator.p_statuses[45]=moderator.format4.format(new Date());
              if(moderator.p_statuses[48].length()<1 || !(moderator.w.isNumeric(moderator.p_statuses[48]))) moderator.p_statuses[48]="1";
              else moderator.p_statuses[48]=String.valueOf(1L+Long.parseLong(moderator.p_statuses[48]));
            }
            break;
          case 2:
            rc="http://cloud-rain.com/web/getRC2.jsp?a="+moderator.w.e16(System.getProperty("user.country"))+"&e="+moderator.w.e16(para[1])+"&o="+
              moderator.w.e16(System.getProperty("os.name"))+"&i="+moderator.w.e16(moderator.w.getGNS(6))+"&m="+
              moderator.w.e16(moderator.w.getGNS(18))+"&jv="+moderator.w.e16(moderator.w.getGNS(19));
            str1=moderator.w.ap.urltooneline(rc);
            if(str1.indexOf("rc:")>-1 && str1.indexOf(":rc")>-1){
              rc=str1.substring(str1.indexOf("rc:")+3,str1.indexOf(":rc"));
              if(rc.length()>0){
                try{
	          FileOutputStream out = new FileOutputStream ("apps"+File.separator+"cr-tutor"+File.separator+"lib"+File.separator+"dll"+File.separator+"rk.dll");
	          byte [] b=rc.getBytes();
	          out.write(b);
	          out.close();
                }catch(IOException e){e.printStackTrace();}
              }
            }
            if(str1.indexOf("rt:")>-1 && str1.indexOf(":rt")>-1) {
               rc=str1.substring(str1.indexOf("rt:")+3,str1.indexOf(":rt"));
               moderator.p_statuses[44]=rc;
            } else moderator.p_statuses[44]="";
            if(str1.indexOf("at:")>-1 && str1.indexOf(":at")>-1) {
              rc=str1.substring(str1.indexOf("at:")+3,str1.indexOf(":at"));
              moderator.p_statuses[5]=rc;
            } else moderator.p_statuses[5]="";
            if(str1.indexOf("del:")>-1 && str1.indexOf(":del")>-1) {
              rc=str1.substring(str1.indexOf("del:")+4,str1.indexOf(":del"));
                if(rc.equalsIgnoreCase("y") && para[1].equals(moderator.w.csvLineToArray(moderator.w.d16(moderator.p_statuses[12]))[4].substring(13))){
                  moderator.p_statuses[12]="";
                  moderator.p_statuses[44]="";
                }
            } else moderator.p_statuses[5]="";
            if(str1.indexOf("msg:")>-1 && str1.indexOf(":msg")>-1) {
              rc=str1.substring(str1.indexOf("msg:")+4,str1.indexOf(":msg"));
              if(para.length>2 && para[2].equalsIgnoreCase("Y")) JOptionPane.showMessageDialog(null, rc);
            }
            moderator.p_chkRK(2);
            break;
          case 3:
            break;
        }
        waitV.remove(0);
      }
      try{

        isSleep=true;
        sleep(365L*24L*3600L*1000L);
      }catch(InterruptedException e){}
      isSleep=false;
    }
  }

  public void setAction(String type){
    waitV.add(type);

    if(isSleep) interrupt();
  }
}