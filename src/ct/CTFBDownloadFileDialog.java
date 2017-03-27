
package ct;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import y.ylib.ylib;

/**
 *
 * @author Administrator
 */
public class CTFBDownloadFileDialog extends javax.swing.JDialog {

    CTModerator cfb;
String originalId,random;
    public CTFBDownloadFileDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.cfb=(CTModerator)parent;
        initComponents();
     init();
    }
      public void init(){
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=750;
        int h2=360;

        setSize(w2,h2);
        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
      }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    jLabel1 = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    jRadioButton2 = new javax.swing.JRadioButton();
    jRadioButton3 = new javax.swing.JRadioButton();
    jRadioButton4 = new javax.swing.JRadioButton();
    jRadioButton1 = new javax.swing.JRadioButton();
    jCheckBox1 = new javax.swing.JCheckBox();
    jCheckBox2 = new javax.swing.JCheckBox();
    jPanel2 = new javax.swing.JPanel();
    jLabel5 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    jLabel7 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ct/Bundle"); 
    setTitle(bundle.getString("CTFBDownloadFileDialog.title")); 
    addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        formKeyReleased(evt);
      }
    });
    getContentPane().setLayout(null);

    jLabel1.setText(bundle.getString("CTFBDownloadFileDialog.jLabel1.text")); 
    getContentPane().add(jLabel1);
    jLabel1.setBounds(10, 10, 200, 15);

    jPanel1.setBackground(new java.awt.Color(220, 220, 220));
    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 255, 255), 1, true), bundle.getString("CTFBDownloadFileDialog.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, null, new java.awt.Color(0, 51, 204))); 
    jPanel1.setLayout(null);

    buttonGroup1.add(jRadioButton2);
    jRadioButton2.setText(bundle.getString("CTFBDownloadFileDialog.jRadioButton2.text")); 
    jRadioButton2.setOpaque(false);
    jRadioButton2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jRadioButton2KeyReleased(evt);
      }
    });
    jPanel1.add(jRadioButton2);
    jRadioButton2.setBounds(20, 50, 190, 23);

    buttonGroup1.add(jRadioButton3);
    jRadioButton3.setText(bundle.getString("CTFBDownloadFileDialog.jRadioButton3.text")); 
    jRadioButton3.setOpaque(false);
    jRadioButton3.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jRadioButton3KeyReleased(evt);
      }
    });
    jPanel1.add(jRadioButton3);
    jRadioButton3.setBounds(20, 80, 190, 23);

    buttonGroup1.add(jRadioButton4);
    jRadioButton4.setText(bundle.getString("CTFBDownloadFileDialog.jRadioButton4.text")); 
    jRadioButton4.setOpaque(false);
    jRadioButton4.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jRadioButton4KeyReleased(evt);
      }
    });
    jPanel1.add(jRadioButton4);
    jRadioButton4.setBounds(20, 110, 200, 23);

    buttonGroup1.add(jRadioButton1);
    jRadioButton1.setSelected(true);
    jRadioButton1.setText(bundle.getString("CTFBDownloadFileDialog.jRadioButton1.text")); 
    jRadioButton1.setOpaque(false);
    jRadioButton1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jRadioButton1KeyReleased(evt);
      }
    });
    jPanel1.add(jRadioButton1);
    jRadioButton1.setBounds(20, 20, 180, 23);

    getContentPane().add(jPanel1);
    jPanel1.setBounds(490, 40, 230, 150);

    jCheckBox1.setText(bundle.getString("CTFBDownloadFileDialog.jCheckBox1.text")); 
    jCheckBox1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jCheckBox1KeyReleased(evt);
      }
    });
    getContentPane().add(jCheckBox1);
    jCheckBox1.setBounds(490, 200, 220, 23);

    jCheckBox2.setText(bundle.getString("CTFBDownloadFileDialog.jCheckBox2.text")); 
    jCheckBox2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jCheckBox2KeyReleased(evt);
      }
    });
    getContentPane().add(jCheckBox2);
    jCheckBox2.setBounds(510, 230, 210, 23);

    jPanel2.setBackground(new java.awt.Color(220, 220, 220));
    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 255, 255), 1, true), bundle.getString("CTFBDownloadFileDialog.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, null, new java.awt.Color(0, 0, 255))); 
    jPanel2.setLayout(null);

    jLabel5.setText(bundle.getString("CTFBDownloadFileDialog.jLabel5.text")); 
    jPanel2.add(jLabel5);
    jLabel5.setBounds(10, 60, 220, 15);

    jLabel4.setText(bundle.getString("CTFBDownloadFileDialog.jLabel4.text")); 
    jPanel2.add(jLabel4);
    jLabel4.setBounds(10, 40, 220, 15);

    jLabel3.setText(bundle.getString("CTFBDownloadFileDialog.jLabel3.text")); 
    jPanel2.add(jLabel3);
    jLabel3.setBounds(10, 20, 440, 15);

    getContentPane().add(jPanel2);
    jPanel2.setBounds(10, 40, 470, 90);

    jPanel3.setBackground(new java.awt.Color(220, 220, 220));
    jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 255, 255), 1, true), bundle.getString("CTFBDownloadFileDialog.jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, null, new java.awt.Color(0, 0, 204))); 
    jPanel3.setLayout(null);

    jLabel7.setText(bundle.getString("CTFBDownloadFileDialog.jLabel7.text")); 
    jPanel3.add(jLabel7);
    jLabel7.setBounds(10, 20, 440, 15);

    jLabel8.setText(bundle.getString("CTFBDownloadFileDialog.jLabel8.text")); 
    jPanel3.add(jLabel8);
    jLabel8.setBounds(10, 40, 210, 15);

    jLabel9.setText(bundle.getString("CTFBDownloadFileDialog.jLabel9.text")); 
    jPanel3.add(jLabel9);
    jLabel9.setBounds(10, 60, 220, 15);

    getContentPane().add(jPanel3);
    jPanel3.setBounds(10, 150, 470, 90);

    jButton1.setText(bundle.getString("CTFBDownloadFileDialog.jButton1.text")); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jButton1KeyReleased(evt);
      }
    });
    getContentPane().add(jButton1);
    jButton1.setBounds(250, 270, 90, 23);

    jButton2.setText(bundle.getString("CTFBDownloadFileDialog.jButton2.text")); 
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jButton2KeyReleased(evt);
      }
    });
    getContentPane().add(jButton2);
    jButton2.setBounds(370, 270, 90, 23);

    pack();
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    String result2="",secondPara="secondPara";
    if(jRadioButton1.isSelected()) result2="overwrite";
    else if(jRadioButton2.isSelected()) {
      result2="checkdate";
      secondPara="false";
      try{
      long oldTime=cfb.format2.parse(jLabel5.getText()).getTime();
      long newTime=cfb.format2.parse(jLabel9.getText()).getTime();
      if(newTime>oldTime) secondPara="true";
      } catch(ParseException e){
         e.printStackTrace();
      }
    } else if(jRadioButton3.isSelected()) {
      secondPara=getAnotherName();
      result2="rename";
    } else if(jRadioButton4.isSelected()) result2="keeporiginal";
    if(jCheckBox1.isSelected() && jCheckBox2.isSelected()){
      cfb.f_myDownloadMode=result2;
      cfb.f_myUploadMode=result2;
    } else if(jCheckBox1.isSelected()){
      cfb.f_myDownloadMode=result2;
    }
    String msg="performaction ct.CTModerator 1 f_checksavefilefeedback "+result2+" "+secondPara+" "+cfb.f_myDownloadMode+" "+random+" 0";

          cfb.w.sendToOne(msg, originalId);
          setVisible(false);
  }

  public String getAnotherName(){
  String oldName=jLabel3.getText();
  File file=new File(oldName);
  String name=file.getName();
  File dir=file.getParentFile();
  File files[]=dir.listFiles();
  Vector fv=new Vector();
  for(int i=0;i<files.length;i++){
    fv.add(files[i].getName());
  }
  int inx=1;
  String newName=name;
  while (true){
    int inx2=name.lastIndexOf(".");
    if(inx2>-1){
      newName=name.substring(0, inx2)+"("+inx+")"+name.substring(inx2);
    } else newName=name+"("+inx+")";
    if(!fv.contains(newName)) break;
    inx++;
  }
  return newName;
}
  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    cancel();
  }

  private void formKeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
      cancel();
    }
  }

  private void jRadioButton1KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jRadioButton2KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jRadioButton3KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jRadioButton4KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jCheckBox1KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jCheckBox2KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jButton1KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jButton2KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }
void cancel(){
        String msg="performaction ct.CTModerator 1 f_checksavefilefeedback cancel secondPara ask "+random+" 0";

          cfb.w.sendToOne(msg, originalId);
          setVisible(false);
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
            java.util.logging.Logger.getLogger(CTFBDownloadFileDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CTFBDownloadFileDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CTFBDownloadFileDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CTFBDownloadFileDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CTFBDownloadFileDialog dialog = new CTFBDownloadFileDialog(new javax.swing.JFrame(), true);
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
public void setInfo(String oldName,String oldSize,String oldTime,String newName,String newSize,String newTime,String originalId,String random){
  this.originalId=originalId;
  this.random=random;
  jLabel3.setText(oldName);
  jLabel4.setText(oldSize+" bytes");
  jLabel5.setText(cfb.format2.format(new Date(Long.parseLong(oldTime))));
  jLabel7.setText(newName);
  jLabel8.setText(newSize+" bytes");
  jLabel9.setText(cfb.format2.format(new Date(Long.parseLong(newTime))));
}

  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JCheckBox jCheckBox1;
  private javax.swing.JCheckBox jCheckBox2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JRadioButton jRadioButton1;
  private javax.swing.JRadioButton jRadioButton2;
  private javax.swing.JRadioButton jRadioButton3;
  private javax.swing.JRadioButton jRadioButton4;

}