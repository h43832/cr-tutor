
package ct;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class CTRegister2 extends javax.swing.JDialog implements Runnable {
  boolean isSleep=false;
  CTModerator moderator;
  Thread t=null;
  Vector waitV=new Vector();
  String prc="0",em="";
  public CTRegister2(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    this.moderator=(CTModerator)parent;
    initComponents();
    String pFile="apps"+File.separator+"cr-tutor"+File.separator+"images"+File.separator+"paypal.jpg";
    String cFile="apps"+File.separator+"cr-tutor"+File.separator+"images"+File.separator+"creditcard.jpg";
    pFile=moderator.w.fileSeparator(pFile);
    cFile=moderator.w.fileSeparator(cFile);
    jButton1.setIcon(new javax.swing.ImageIcon(pFile)); 
    jButton2.setIcon(new javax.swing.ImageIcon(cFile)); 
            int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        jButton1.setVisible(false);
        jButton2.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        int w2=750;
        int h2=550;

        setSize(w2,h2);

        setLocationRelativeTo(null);
        jLabel1.setText("");

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
        waitV.add("1");
        t=new Thread(this);
        t.start();
        this.setVisible(true);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jRadioButton1 = new javax.swing.JRadioButton();
    jRadioButton2 = new javax.swing.JRadioButton();
    jLabel6 = new javax.swing.JLabel();
    jTextField2 = new javax.swing.JTextField();
    jLabel7 = new javax.swing.JLabel();
    jTextField3 = new javax.swing.JTextField();
    jLabel9 = new javax.swing.JLabel();
    jTextField5 = new javax.swing.JTextField();
    jTextField6 = new javax.swing.JTextField();
    jLabel8 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    jButton3 = new javax.swing.JButton();
    jButton4 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ct/Bundle"); 
    setTitle(bundle.getString("CTRegister2.title")); 

    jPanel1.setLayout(null);

    jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+4));
    jLabel1.setForeground(new java.awt.Color(0, 0, 204));
    jLabel1.setText(bundle.getString("CTRegister2.jLabel1.text")); 
    jPanel1.add(jLabel1);
    jLabel1.setBounds(40, 280, 350, 30);

    jButton1.setFont(jButton1.getFont());
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel1.add(jButton1);
    jButton1.setBounds(290, 320, 250, 40);

    jButton2.setFont(jButton2.getFont());
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel1.add(jButton2);
    jButton2.setBounds(290, 400, 250, 40);

    jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getStyle() | java.awt.Font.BOLD, jLabel3.getFont().getSize()+4));
    jLabel3.setText(bundle.getString("CTRegister2.jLabel3.text")); 
    jPanel1.add(jLabel3);
    jLabel3.setBounds(40, 320, 230, 40);

    jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD, jLabel4.getFont().getSize()+4));
    jLabel4.setText(bundle.getString("CTRegister2.jLabel4.text")); 
    jPanel1.add(jLabel4);
    jLabel4.setBounds(40, 400, 230, 30);

    jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD, jLabel2.getFont().getSize()+4));
    jLabel2.setText(bundle.getString("CTRegister2.jLabel2.text")); 
    jPanel1.add(jLabel2);
    jLabel2.setBounds(40, 360, 50, 30);

    jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getStyle() | java.awt.Font.BOLD, jLabel5.getFont().getSize()+4));
    jLabel5.setText(bundle.getString("CTRegister2.jLabel5.text")); 
    jPanel1.add(jLabel5);
    jLabel5.setBounds(40, 50, 150, 40);

    jTextField1.setBackground(new java.awt.Color(255, 204, 255));
    jTextField1.setFont(jTextField1.getFont().deriveFont(jTextField1.getFont().getSize()+4f));
    jPanel1.add(jTextField1);
    jTextField1.setBounds(190, 50, 420, 40);

    buttonGroup1.add(jRadioButton1);
    jRadioButton1.setFont(jRadioButton1.getFont().deriveFont(jRadioButton1.getFont().getStyle() | java.awt.Font.BOLD, jRadioButton1.getFont().getSize()+4));
    jRadioButton1.setSelected(true);
    jRadioButton1.setText(bundle.getString("CTRegister2.jRadioButton1.text")); 
    jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButton1ActionPerformed(evt);
      }
    });
    jPanel1.add(jRadioButton1);
    jRadioButton1.setBounds(40, 110, 230, 30);

    buttonGroup1.add(jRadioButton2);
    jRadioButton2.setFont(jRadioButton2.getFont().deriveFont(jRadioButton2.getFont().getStyle() | java.awt.Font.BOLD, jRadioButton2.getFont().getSize()+4));
    jRadioButton2.setText(bundle.getString("CTRegister2.jRadioButton2.text")); 
    jRadioButton2.setActionCommand(bundle.getString("CTRegister2.jRadioButton2.actionCommand")); 
    jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButton2ActionPerformed(evt);
      }
    });
    jPanel1.add(jRadioButton2);
    jRadioButton2.setBounds(40, 180, 230, 30);

    jLabel6.setFont(jLabel6.getFont());
    jLabel6.setText(bundle.getString("CTRegister2.jLabel6.text")); 
    jPanel1.add(jLabel6);
    jLabel6.setBounds(100, 210, 90, 30);

    jTextField2.setFont(jTextField2.getFont());
    jPanel1.add(jTextField2);
    jTextField2.setBounds(200, 210, 220, 30);

    jLabel7.setFont(jLabel7.getFont());
    jLabel7.setText(bundle.getString("CTRegister2.jLabel7.text")); 
    jPanel1.add(jLabel7);
    jLabel7.setBounds(440, 210, 130, 30);
    jPanel1.add(jTextField3);
    jTextField3.setBounds(570, 210, 100, 30);

    jLabel9.setFont(jLabel9.getFont());
    jLabel9.setText(bundle.getString("CTRegister2.jLabel9.text")); 
    jPanel1.add(jLabel9);
    jLabel9.setBounds(100, 245, 90, 20);

    jTextField5.setFont(jTextField5.getFont());
    jPanel1.add(jTextField5);
    jTextField5.setBounds(200, 240, 280, 30);

    jTextField6.setFont(jTextField6.getFont());
    jPanel1.add(jTextField6);
    jTextField6.setBounds(200, 140, 280, 30);

    jLabel8.setFont(jLabel8.getFont());
    jLabel8.setText(bundle.getString("CTRegister2.jLabel8.text")); 
    jPanel1.add(jLabel8);
    jLabel8.setBounds(100, 140, 100, 30);

    jLabel10.setFont(jLabel10.getFont());
    jLabel10.setText(bundle.getString("CTRegister2.jLabel10.text")); 
    jPanel1.add(jLabel10);
    jLabel10.setBounds(40, 20, 240, 15);

    jLabel11.setFont(jLabel11.getFont());
    jLabel11.setText(bundle.getString("CTRegister2.jLabel11.text")); 
    jPanel1.add(jLabel11);
    jLabel11.setBounds(490, 145, 220, 20);

    jButton3.setFont(new java.awt.Font("新細明體", 1, 18)); 
    jButton3.setText(bundle.getString("CTRegister2.jButton3.text")); 
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton3ActionPerformed(evt);
      }
    });
    jPanel1.add(jButton3);
    jButton3.setBounds(190, 340, 170, 50);

    jButton4.setFont(new java.awt.Font("新細明體", 1, 18)); 
    jButton4.setText(bundle.getString("CTRegister2.jButton4.text")); 
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton4ActionPerformed(evt);
      }
    });
    jPanel1.add(jButton4);
    jButton4.setBounds(430, 340, 130, 50);

    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    pack();
  }
public void run(){
  while(true){
   while(waitV.size()>0){
     int type=Integer.parseInt((String)waitV.get(0));
     switch(type){
       case 1:
         String v[]=p_getVer();
         if(v!=null && v.length>13){
           prc=getAnotherPrice(20,v[13],1.0);
           if(prc.indexOf(".")>-1) prc=prc.substring(0,prc.indexOf("."));
           jLabel1.setText("註冊費用:  新台幣 "+prc+" 元 (含稅)");

           moderator.p_statuses[32]=prc;

         }
         break;
       case 2:
         break;
     }
      waitV.remove(0);
    }
    try{
      isSleep=true;
      t.sleep(1000L * 3600L * 24L * 365L);
    } catch(InterruptedException e){}
    isSleep=false;
  }
}

void setAction(String type){
    waitV.add(type);
  if(isSleep){
    t.interrupt();
  }
}
  String getAnotherPrice(int modN,String price,double interval){
    String rc="http://cloud-rain.com/web/getAnotherPrice.jsp?price="+price+"&modn="+modN+"&interval="+interval;
    rc=moderator.w.ap.urltooneline(rc);
    if(rc.indexOf("price:")>-1 && rc.indexOf(":price")>-1){
      price=rc.substring(rc.indexOf("price:")+6,rc.indexOf(":price"));
    }
    return price;
  }
private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  jTextField2.setBackground(new Color(255,255,255));
  jTextField3.setBackground(new Color(255,255,255));
  jTextField5.setBackground(new Color(255,255,255));
}

private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {
  jTextField2.setBackground(new Color(255,204,255));
  jTextField3.setBackground(new Color(255,204,255));
  jTextField5.setBackground(new Color(255,204,255));
}

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  if(p_chkInput()) {

    moderator.openURL.open("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=96RFVJPKTMT9W");
    setVisible(false);
    p_getRC(false);
  }

}

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
  if(p_chkInput()) {

    moderator.openURL.open("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=96RFVJPKTMT9W");
    setVisible(false);
    p_getRC(false);
  }
}

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
    setVisible(false);
  }

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
     if(p_chkInput()) {

    setVisible(false);
    p_getRC(true);
  }
  }
boolean p_chkInput(){
   em=jTextField1.getText().trim();
  if(em.length()<1) {JOptionPane.showMessageDialog(this, "email為必填項目。"); return false;}
  if(!moderator.p_isValidEmailAddress2(em)) {JOptionPane.showMessageDialog(this, "\""+em+"\" 非正確email格式!"); return false;}
  String addr="",uno="",title="";
  if(jRadioButton1.isSelected()){
    addr=jTextField6.getText().trim();
  } else if(jRadioButton2.isSelected()){
    addr=jTextField5.getText().trim();
    uno=jTextField3.getText().trim();
    title=jTextField2.getText().trim();
    if(addr.length()<1 || uno.length()<1 || title.length()<1) {JOptionPane.showMessageDialog(this, "三聯式發票, 公司統編, 公司抬頭, 及寄送地址為必填項目!"); return false;}
  }
  return true;
}

void p_getRC(boolean informCustomer){
    String addr="",iTitle="",iN="";
   if(jRadioButton1.isSelected()) addr=jTextField6.getText().trim();
   else addr=addr=jTextField5.getText().trim();
   iTitle=jTextField2.getText().trim();
   iN=jTextField3.getText().trim();
   addr=moderator.w.e16(addr);
   iTitle=moderator.w.e16(iTitle);
   iN=moderator.w.e16(iN);
   String rc="http://cloud-rain.com/web/getRC.jsp?a="+moderator.w.e16(System.getProperty("user.country"))+"&t=1&p="+
            moderator.w.e16(prc)+"&e="+moderator.w.e16(em)+"&ap="+moderator.w.e16("1")+"&ia="+addr+"&it="+iTitle+"&in="+iN+"&informc="+informCustomer;
    rc=moderator.w.ap.urltooneline(rc);
    if(rc.indexOf("rc:")>-1 && rc.indexOf(":rc")>-1){
      rc=rc.substring(rc.indexOf("rc:")+3,rc.indexOf(":rc"));
      if(rc.length()>0){
        moderator.p_statuses[44]=moderator.format4.format(new Date());
        moderator.p_statuses[46]="";
        moderator.p_statuses[47]="";
        try{
	  FileOutputStream out = new FileOutputStream ("apps"+File.separator+"cr-tutor"+File.separator+"lib"+File.separator+"dll"+File.separator+"rk.dll");
	  byte [] b=rc.getBytes();
	  out.write(b);
	  out.close();
      }catch(IOException e){e.printStackTrace();}
    }
    } else {}
    if(rc.indexOf("rt:")>-1 && rc.indexOf(":rt")>-1){
      rc=rc.substring(rc.indexOf("rt:")+3,rc.indexOf(":rt"));
      if(rc.length()>0) moderator.p_statuses[44]=rc;
    }
    if(informCustomer){
      JOptionPane.showMessageDialog(this, "付款資訊將以email傳送到您的email帳號 "+em+"\r\n謝謝您。");
    }
}
String [] p_getVer(){
  String v=moderator.w.ap.urltooneline("http://cloud-rain.com/web/version_crtutor.txt");

  if(v.indexOf(",")==-1) v=moderator.w.d642(v);

  String v2[]=null;
  if(v.length()>0 && v.indexOf(",")>-1) {
    v2=moderator.w.csvLineToArray(v);
    if(v2.length>1) moderator.p_statuses[31]=v2[1];
  } else {
      moderator.appendStatus("Warning: network disconnected.\r\n");
      v2=null;
  }

  return v2;
}
  /**
   * @param args the command line arguments
   */

  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JRadioButton jRadioButton1;
  private javax.swing.JRadioButton jRadioButton2;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JTextField jTextField3;
  private javax.swing.JTextField jTextField5;
  private javax.swing.JTextField jTextField6;

}