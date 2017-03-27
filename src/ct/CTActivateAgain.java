
package ct;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
public class CTActivateAgain extends javax.swing.JDialog {
 ResourceBundle bundle2;
  CTModerator moderator;
  String em="";
  public CTActivateAgain(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    bundle2 = java.util.ResourceBundle.getBundle("ct/Bundle");
    this.moderator=(CTModerator)parent;
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=700;
        int h2=250;

        setSize(w2,h2);

        setLocationRelativeTo(null);

  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ct/Bundle"); 
        jLabel1.setText(bundle.getString("CTActivateAgain.jLabel1.text")); 
        getContentPane().add(jLabel1);
        jLabel1.setBounds(39, 18, 530, 15);

        jLabel2.setText(bundle.getString("CTActivateAgain.jLabel2.text")); 
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 40, 640, 15);

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+6f));
        jLabel3.setText(bundle.getString("CTActivateAgain.jLabel3.text")); 
        getContentPane().add(jLabel3);
        jLabel3.setBounds(40, 85, 140, 30);

        jTextField1.setBackground(new java.awt.Color(255, 204, 255));
        jTextField1.setFont(jTextField1.getFont().deriveFont(jTextField1.getFont().getSize()+6f));
        getContentPane().add(jTextField1);
        jTextField1.setBounds(190, 90, 380, 30);

        jButton1.setFont(jButton1.getFont().deriveFont(jButton1.getFont().getSize()+6f));
        jButton1.setText(bundle.getString("CTActivateAgain.jButton1.text")); 
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(240, 163, 290, 30);

        pack();
    }

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  em=jTextField1.getText().trim();
  if(em.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("CTActivateAgain.xy.msg1")); return;}
  if(!moderator.p_isValidEmailAddress2(em)) {JOptionPane.showMessageDialog(this, "\""+em+"\" "+bundle2.getString("CTActivateAgain.xy.msg2")); return;}

    moderator.p_toActivateAgain2(em+",Y");
    setVisible(false);

}

void p_getRC2_old(){
   String rc="http://cloud-rain.com/web/getRC2.jsp?a="+moderator.w.e16(System.getProperty("user.country"))+"&e="+moderator.w.e16(em)+"&o="+
            moderator.w.e16(System.getProperty("os.name"))+"&i="+moderator.w.e16(moderator.w.getGNS(6))+"&m="+
           moderator.w.e16(moderator.w.getGNS(18))+"&jv="+moderator.w.e16(moderator.w.getGNS(19));
   String str1=moderator.w.ap.urltooneline(rc);
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
    if(str1.indexOf("msg:")>-1 && str1.indexOf(":msg")>-1) {
             rc=str1.substring(str1.indexOf("msg:")+4,str1.indexOf(":msg"));
             JOptionPane.showMessageDialog(this, rc);
    }
}
  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {

        

    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(CTActivateAgain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(CTActivateAgain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(CTActivateAgain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(CTActivateAgain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        CTActivateAgain dialog = new CTActivateAgain(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {

          @Override
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;

}