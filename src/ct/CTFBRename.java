
package ct;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class CTFBRename extends javax.swing.JDialog {

    CTModerator moderator;
    int action=0,from=0;
    String dir="",oldName="";
    public CTFBRename(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.moderator=(CTModerator) parent;
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=635;
        int h2=80;

        setSize(w2,h2);
        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
    }

public void setValue(String dir,String name,int action,int from){
    jTextField1.setText(name);
    this.action=action;
    this.from=from;
    this.dir=dir;
    this.oldName=name;
       switch(action){
            case 1:
                setTitle(moderator.bundle2.getString("CTModerator.xy.msg23"));
                jLabel1.setText(moderator.bundle2.getString("CTModerator.xy.msg24"));
                break;
            case 2:
                setTitle(moderator.bundle2.getString("CTModerator.xy.msg25"));
                jLabel1.setText(moderator.bundle2.getString("CTModerator.xy.msg26"));
                break;
            case 3:
                setTitle(moderator.bundle2.getString("CTModerator.xy.msg27"));
                jLabel1.setText(moderator.bundle2.getString("CTModerator.xy.msg28")+dir+(dir.endsWith(File.separator)? "":File.separator));
                jTextField1.setText(name);
                break;
            case 4:
                setTitle(moderator.bundle2.getString("CTModerator.xy.msg29"));
                jLabel1.setText(moderator.bundle2.getString("CTModerator.xy.msg30")+dir+(dir.endsWith(moderator.f_remoteFileSeparator)? "":moderator.f_remoteFileSeparator));
                jTextField1.setText(name);
                break;
            case 5:
                setTitle(moderator.bundle2.getString("CTModerator.xy.msg31"));
                jLabel1.setText(moderator.bundle2.getString("CTModerator.xy.msg32"));

                break;
            case 6:
                setTitle(moderator.bundle2.getString("CTModerator.xy.msg33"));
                jLabel1.setText(moderator.bundle2.getString("CTModerator.xy.msg34"));

                break;
        }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    btnOK = new javax.swing.JButton();
    btnCancel = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ct/Bundle"); 
    setTitle(bundle.getString("CTFBRename.title")); 
    addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        formKeyReleased(evt);
      }
    });
    getContentPane().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel1.setText(bundle.getString("CTFBRename.jLabel1.text")); 
    jLabel1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jLabel1KeyReleased(evt);
      }
    });
    getContentPane().add(jLabel1);

    jTextField1.setMinimumSize(new java.awt.Dimension(200, 21));
    jTextField1.setPreferredSize(new java.awt.Dimension(200, 25));
    jTextField1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jTextField1ActionPerformed(evt);
      }
    });
    jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jTextField1KeyReleased(evt);
      }
    });
    getContentPane().add(jTextField1);

    btnOK.setText(bundle.getString("CTFBRename.btnOK.text")); 
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnOKActionPerformed(evt);
      }
    });
    btnOK.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        btnOKKeyReleased(evt);
      }
    });
    getContentPane().add(btnOK);

    btnCancel.setText(bundle.getString("CTFBRename.btnCancel.text")); 
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCancelActionPerformed(evt);
      }
    });
    btnCancel.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        btnCancelKeyReleased(evt);
      }
    });
    getContentPane().add(btnCancel);

    pack();
  }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        doIt();
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        cancel();
    }
void cancel(){
  setVisible(false);
}
  private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {
        doIt();
  }

  private void formKeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void btnOKKeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void btnCancelKeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }

  private void jLabel1KeyReleased(java.awt.event.KeyEvent evt) {
    if(evt.getKeyCode()==27){
       cancel();
    }
  }
public void doIt(){
            String cmd="";
            String newName=jTextField1.getText().trim();
            switch(action){
            case 1:
                if(newName.length()>0 && !newName.equals(oldName)){
                  if(new File(dir+(dir.endsWith(File.separator)? "":File.separator)+oldName).renameTo(new File(dir+(dir.endsWith(File.separator)? "":File.separator)+newName))) {

                    System.out.println("before call moderator.f_updateTable1()-1");
                    moderator.f_updateTable1(dir);

                  }
                  else {
                    JOptionPane.showMessageDialog(this, "Failed to rename "+dir+(dir.endsWith(File.separator)? "":File.separator)+oldName+" to "+newName);
                    moderator.jTextArea3.append("Local failed to rename "+dir+(dir.endsWith(File.separator)? "":File.separator)+oldName+" to "+newName+"\r\n");

                  }
                } else JOptionPane.showMessageDialog(this, "Cannot rename to same file name.");
                break;
            case 2:
                if(newName.length()>0 && !newName.equals(oldName)){
                  moderator.f_currentRandom=""+Math.random();

                 cmd="performcommand ct.CTModerator f_renamefile "+moderator.w.e642(dir)+" "+moderator.f_currentRandom+" "+moderator.w.e642(oldName)+" "+moderator.w.e642(newName)+" 0 0 0 0  0 0 0 0 0 ";
                 moderator.w.sendToOne(cmd, moderator.f_rdb.infinityNodeId);
                } else JOptionPane.showMessageDialog(this, "Cannot rename to same directory name.");
                break;
            case 3:
               if(newName.length()>0){

                  if(new File(dir+(dir.endsWith(File.separator)? "":File.separator)+newName).mkdir()) {

                  }
                  else {
                    JOptionPane.showMessageDialog(this, "Failed to create directory \""+dir+(dir.endsWith(File.separator)? "":File.separator)+newName+"\".");
                    moderator.jTextArea3.append("Failed to create directory \""+dir+(dir.endsWith(File.separator)? "":File.separator)+newName+"\".\r\n");

                  }

                } else JOptionPane.showMessageDialog(this, "New Name can not be empty!");
                break;
            case 4:
                if(newName.length()>0){
                  moderator.f_currentRandom=""+Math.random();

                 cmd="performcommand ct.CTModerator f_insertdir "+moderator.w.e642(dir)+" "+moderator.f_currentRandom+" "+moderator.w.e642(newName)+" "+from+" 0 0 0 0 0  0 0 0 0 0 ";
                 moderator.w.sendToOne(cmd, moderator.f_rdb.infinityNodeId);
                } else JOptionPane.showMessageDialog(this, "Please input new directory name.");
                break;
            case 5:
                if(newName.length()>0 && !newName.equals(oldName)){
                  if(new File(dir+(dir.endsWith(File.separator)? "":File.separator)+oldName).renameTo(new File(dir+(dir.endsWith(File.separator)? "":File.separator)+newName))) {

                    System.out.println("before call moderator.f_updateTable1()-2");
                    moderator.f_updateTable1(dir);

                  }
                  else {
                    JOptionPane.showMessageDialog(this, "Failed to rename "+dir+(dir.endsWith(File.separator)? "":File.separator)+oldName+" to "+newName);
                    moderator.jTextArea3.append("Local failed to rename "+dir+(dir.endsWith(File.separator)? "":File.separator)+oldName+" to "+newName+"\r\n");

                  }
                } else JOptionPane.showMessageDialog(this, "Cannot rename to same file name.");
                break;
            case 6:
                if(newName.length()>0 && !newName.equals(oldName)){
                  moderator.f_currentRandom=""+Math.random();

                 cmd="performcommand ct.CTModerator f_renamedir "+moderator.w.e642(dir)+" "+moderator.f_currentRandom+" "+moderator.w.e642(oldName)+" "+from+" "+moderator.w.e642(newName)+" 0 0 0 0  0 0 0 0 0 ";
                 moderator.w.sendToOne(cmd, moderator.f_rdb.infinityNodeId);
                } else JOptionPane.showMessageDialog(this, "Cannot rename to same directory name.");
                break;
        }
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
            java.util.logging.Logger.getLogger(CTFBRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CTFBRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CTFBRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CTFBRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CTFBRename dialog = new CTFBRename(new javax.swing.JFrame(), true);
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

  private javax.swing.JButton btnCancel;
  private javax.swing.JButton btnOK;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JTextField jTextField1;

}