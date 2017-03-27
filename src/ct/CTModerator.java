
package ct;

import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFDecodeParam;
import static ct.CTFBDirBrowser.getDirBrowser;
import infinity.client.*;
import infinity.common.action.*;
import infinity.common.*;
import infinity.common.server.Connection;
import infinity.server.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.*;
import static java.lang.Thread.sleep;
import javax.swing.*;
import java.util.*;
import java.text.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import y.ylib.OneVar;
import y.ylib.ZDIS;
import y.ylib.ylib;

public class CTModerator extends javax.swing.JFrame implements Runnable,GAction{
  static public String version="2.17.0012",newversion=version,versionDate="2017-03-27 21:00:00";
  public Weber w;
  public Net gs;
  public String allNodesName="",myNodeName="",logFileHead="log\\logCrTutor-";

  CTSlidesDialg sld_dialog;
  Thread t;
  Rectangle thisRect=new Rectangle(800,600);
  int thisState=JFrame.MAXIMIZED_BOTH,lastFrameW=0,lastFrameH=0,lastFileInx=-1;
  public ResourceBundle bundle2 = java.util.ResourceBundle.getBundle("ct/Bundle");

  ByteArrayOutputStream byteArrayStream=null;
  ImageEncoder enc=null;
  com.sun.media.jai.codec.JPEGEncodeParam param=null;

 boolean runThread=false,isAlive=false,isSleep=false,reload=false,tutorMode=false,
             presentationMode=false,existSaveFile=false;
 SimpleDateFormat format1 = new SimpleDateFormat ("yyyy/MM/dd HH:mm"),format2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
         format3=new SimpleDateFormat("HH:mm"),format4=new SimpleDateFormat("yyyyMMddHHmmss"),format5 = new SimpleDateFormat("HH:mm:ss");

 long timeout=3000,oldAuOne=0,auOne_asAMember=0,auOne_all=0,statusOne=0;
 CTAbout p_about;
 CTAutoUpdate  p_au;

 public CTRegister p_register;
 public CTRegister2 p_register2;
 public CTActivateAgain aa;
 CTCBListener cbListener;
 String restartStr="",sk_currentId="",sr_currentId="",sld_currentId="",f_currentId="",msg_currentId="",apId="1005",
         sld_opened_file_id="";
 TreeMap nameIdMap=new TreeMap(),slides=new TreeMap();
 public y.ylib.OpenURL openURL=new y.ylib.OpenURL();
 String lattestRemoteTutorId="",lattestRemoteTutorName="",p_statuses[]=new String[50];
 public String p_statusFile=System.getProperty("user.home")+File.separator+"cr-tutor.txt",p_rc="",
         p_rcFile="apps"+File.separator+"cr-tutor"+File.separator+"lib"+File.separator+"dll"+File.separator+"rk.dll";
 Properties moderator_props=new Properties();

 String propsFile="apps"+File.separator+"cr-tutor"+File.separator+"node_ctslide_properties.txt",sketchLogDir="apps"+File.separator+"cr-tutor"+File.separator+"log"+File.separator+"sketch",
         sldLogDir="apps"+File.separator+"cr-tutor"+File.separator+"log"+File.separator+"slide",screenLogDir="apps"+File.separator+"cr-tutor"+File.separator+"log"+File.separator+"screen",
     msgLogDir="apps"+File.separator+"cr-tutor"+File.separator+"log"+File.separator+"message",fileLogDir="apps"+File.separator+"cr-tutor"+File.separator+"log"+File.separator+"file_transfer",
         statusLogDir="apps"+File.separator+"cr-tutor"+File.separator+"log"+File.separator+"status";
 CTSlideThread slideThread=new CTSlideThread();
 CTScreenThread screenThread=new CTScreenThread();
 Srn_Thread2 screenThread2=new Srn_Thread2();
 CTSketchThread sketchThread=new CTSketchThread();
 CTMsgThread msgThread=new CTMsgThread();
 CTFBDownloadThread f_downloadThread=new CTFBDownloadThread();

 public int showIndex=11,showType=2,currentTbl1SortIndex=-1,currentTbl2SortIndex=-1;
 private String sep=""+(char)0;
long waitTime=365L*24L*60L*60L*1000L;

  String sld_listfile="apps"+File.separator+"cr-tutor"+File.separator+"slides.txt";
  boolean sld_skipSlideInxChanged=false,currentTbl1SortAsc=false,currentTbl2SortAsc=false;
   JMenuItem sld_itemUp;
  JMenuItem sld_itemDown;
  JMenuItem sld_itemFirst;
  JMenuItem sld_itemLast;
  JMenu sld_menuGo = new JMenu("Go");
  JMenuItem sld_item1;
  JMenu sld_menuScreen;
  JMenuItem sld_itemBlack;
  JMenuItem sld_itemWhite;
  JMenu sld_menuPointer;
  JMenuItem sld_itemArrow;
  JMenuItem sld_itemLaser;
  JMenuItem sld_itemPen;
  JMenu sld_menuColor;
  JMenuItem sld_itemClear;
  JMenuItem sld_itemEnd;
  JPopupMenu sld_popupMenu = new JPopupMenu("Popup");
  TreeMap sld_imgs=new TreeMap(),sld_dataVs=new TreeMap();
  int sld_currentInx=0;
  Vector sld_dataV=new Vector(),sld_laserDataV=new Vector();
  boolean sld_reload=false,sld_oldIsVisible=false;
   Image sld_img;

  ImageIcon sld_icon;
  String sld_dirForBad="",sld_inputNo="";
  String sld_currentFN="",sld_currentDir="";
 long sld_oldAuOne=0;
  CTSldImgPanel sld_imgPanel;

 DefaultStyledDocument msg_styleDoc=new DefaultStyledDocument();
 boolean msg_needChk=false;

 public int msg_maxMainLogLength=100000,file_maxMainLogLength=100000,status_maxMainLogLength=100000;
   Vector msg_displayV=new Vector(),f_saveActions=new Vector(),srn_actions=new Vector();
 boolean msg_showMember=false,msg_showTime=false;
 public String msg_lastUpper="";
 boolean msg_gIsFinal=false,hasNewVersion=false;
  TreeMap innerMembers=new TreeMap(),
          outerMembers=new TreeMap(),
          innerMemberItems=new TreeMap(),
          outerMemberItems=new TreeMap(),
          elseMemberItems=new TreeMap();
          ;

     String srn_currentName;
   Object srn_copyObj;
   String srn_currentId="",srn_currentCode="";
   public long srn_intervalMS=3000L;
   javax.swing.ButtonGroup srn_bGroup1=new javax.swing.ButtonGroup(),srn_bGroup2=new javax.swing.ButtonGroup();
   CTScreenPanel2 sPanel;
   public TreeMap srn_imgMap=new TreeMap();
   String srn_startStr="Start",srn_stopStr="Stop";

   int srn_viewTutorMode=0,oldControlAu=2;
   boolean srn_oldIsVisible=false,srn_oldPanelIsVisible=false,srn_oldFixToSrn=true,srn_oldHasSrnPanel=false;
   String srn_oldSelectedItem="";
   public int srn_div=1,srn_ttl=1,srn_viewCount=0;
   boolean srn_hasNewValue=false;
   public boolean srn_imgByInterval=false,srn_imgByRobot=true,srn_fixToSrn=true;
   KeyboardFocusManager srn_kfm;
   public boolean srn_toInform=false, srn_updating=false;

   byte[] srn_imgB={};

   long lastGetDir_time=0;
   F_UploadThread f_uThread=new F_UploadThread();
   String jTextPane1Text="";
  F_DeletedByRemote_Thread f_RDThread=new F_DeletedByRemote_Thread();
  F_DeletedByLocal_Thread f_LDThread=new F_DeletedByLocal_Thread();
  CTFBRemoteDirNode f_lastGetRemoteNode,
                 f_currentRemoteNode;
  CTFBRemoteDirBrowser f_rdb;
  CTFBDownloadFileDialog f_downloadDialog;
  CTFBUploadFileDialog f_uploadDialog;

  CTFBDirNode f_currentLocalNode;
  CTFBDirBrowser f_browser;
  int f_sX = -1, f_sY = -1,f_oldH4=0,f_oldH3=0,f_oldH6=0,f_oldH5,f_oldH2=0,f_oldW1=0,f_oldW2=0,f_ssX=0,f_ssY=0;
  int f_curX = -1, f_curY = -1,f_ccurX=0,f_ccurY=0,f_waitCnt=0,f_failedCnt=0,f_okCnt=0;
  public String f_myDownloadMode="ask",f_myUploadMode="ask";

  TreePath f_currentSelectedPath1[]=null,f_currentSelectedPath2[]=null;
  boolean f_dragging = false,f_skipComboBox1ItemStateChanged=false,f_skipComboBox2ItemStateChanged=false,relayout=false;
  String f_currentName="",f_currentRandom="",f_dirType="<Directory>",f_downloadChkExistSecondPara="",
          f_uploadChkExistSecondPara="",f_uploadChkExistThirdPara="",f_uploadChkExistFourthPara="",f_uploadChkExistFifthPara="",f_uploadChkExistSixthPara="",
          f_downloadChkSaveSecondPara="", f_uploadChkSaveResult="",
         f_logDir="apps"+File.separator+"cr-tutor"+File.separator+"log",f_remoteFileSeparator="\\",
         f_current_local_absolutePath="",f_lastGetRemoteDir="",
          f_currentRemoteWriteMode="ask",f_current_remote_absolutePath="";
  int f_w1=0,f_w2=0,f_h1=0,f_h2=0,f_h3=0,f_h4=0,f_h5=0,f_h6=0;
  public int f_local_orderby=1,f_remote_orderby=1;
  public boolean f_local_asc=true,f_remote_asc=true;
  CTFBRename f_rename2;
  JMenuItem f_itemUpload1 = new JMenuItem(bundle2.getString("CTModerator.xy.msg15"));
  JMenuItem f_itemAddDir1 = new JMenuItem(bundle2.getString("CTModerator.xy.msg16"));
  JMenuItem f_itemRename1 = new JMenuItem(bundle2.getString("CTModerator.xy.msg17"));
  JMenuItem f_itemDelete1 = new JMenuItem(bundle2.getString("CTModerator.xy.msg18"));
  JPopupMenu f_popupMenu1 = new JPopupMenu("Popup");
  JMenuItem f_itemDownload2 = new JMenuItem(bundle2.getString("CTModerator.xy.msg19"));
  JMenuItem f_itemAddDir2 = new JMenuItem(bundle2.getString("CTModerator.xy.msg20"));
  JMenuItem f_itemDelete2 = new JMenuItem(bundle2.getString("CTModerator.xy.msg21"));
  JMenuItem f_itemRename2 = new JMenuItem(bundle2.getString("CTModerator.xy.msg22"));
  JPopupMenu f_popupMenu2 = new JPopupMenu("Popup");

    int f_currentStatus=0,f_feedbackStatus=0,f_feedbackRandom=0,f_fileVectorLimit=200000;
    Vector f_downloadActions=new Vector(),f_uploadActions=new Vector(),f_deleteActions=new Vector(), f_downloadFileVector=new Vector();
    String f_downloadChkSaveResult="";
    boolean f_chkExistResult=false,f_uploadChkExistResult=false;

   Vector sk_dataV=new Vector();
   public CTSketchPanel2 skPanel2;
  /** Creates new form CTModerator */
  public CTModerator() {
    initComponents();
    init();
    if(!new File("log").exists()) new File("log").mkdirs();
 slideThread.init(this);
 screenThread.init(this);
 sketchThread.init(this);
 msgThread.init(this);
 f_downloadThread.init(this);
    slideThread.start();
    screenThread.start();
    screenThread2.start();
    sketchThread.start();
    msgThread.start();
    f_downloadThread.start();
    f_uThread.start();
    f_RDThread.start();
    f_LDThread.start();

    sld_menuScreen = new JMenu(bundle2.getString("CTSlideBoard.xy.msg6"));
    sld_menuPointer = new JMenu(bundle2.getString("CTSlideBoard.xy.msg9"));
    sld_itemUp= new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg3"));
    sld_itemDown=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg2"));
    sld_itemFirst= new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg4"));
    sld_itemLast=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg5"));
    sld_item1=new JMenuItem("page 1");
    sld_menuGo.add(sld_item1);
    sld_itemBlack=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg7"));
    sld_itemWhite=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg8"));
    sld_menuScreen.add(sld_itemBlack);
    sld_menuScreen.add(sld_itemWhite);
    sld_itemArrow=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg15"));
    sld_itemLaser=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg13"));
    sld_itemPen=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg14"));

    sld_itemClear=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg12"));
    sld_itemEnd=new JMenuItem(bundle2.getString("CTSlideBoard.xy.msg10"));
    sld_itemUp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(w.checkOneVar(auOne_asAMember, 2) ||  tutorMode) sld_gotoSlide(-1,true);
      }
    });
    sld_itemDown.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(w.checkOneVar(auOne_asAMember, 2) ||  tutorMode) sld_gotoSlide(-2,true);
      }
    });
    sld_itemFirst.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(w.checkOneVar(auOne_asAMember, 2) ||  tutorMode) sld_gotoSlide(0,true);
      }
    });
    sld_itemLast.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(w.checkOneVar(auOne_asAMember, 2) ||  tutorMode) sld_gotoSlide(-3,true);
      }
    });
    sld_itemBlack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(w.checkOneVar(auOne_asAMember, 2) ||  tutorMode) sld_gotoSlide(-4,true);
      }
    });
    sld_itemWhite.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(w.checkOneVar(auOne_asAMember, 2) ||  tutorMode) sld_gotoSlide(-5,true);
      }
    });
    sld_itemArrow.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          auOne_asAMember=w.removeOneVar(auOne_asAMember, 11);
          auOne_asAMember=w.removeOneVar(auOne_asAMember, 12);
      }
    });
    sld_itemLaser.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          auOne_asAMember=w.addOneVar(auOne_asAMember, 11);
          auOne_asAMember=w.removeOneVar(auOne_asAMember, 12);
      }
    });
    sld_itemPen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          auOne_asAMember=w.addOneVar(auOne_asAMember, 12);
          auOne_asAMember=w.removeOneVar(auOne_asAMember, 11);
      }
    });
    sld_itemClear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(w.checkOneVar(auOne_asAMember, 2) ||  tutorMode) sld_gotoSlide(-6,true);
      }
    });
    sld_itemEnd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          auOne_asAMember=w.removeOneVar(auOne_asAMember,11);
          auOne_asAMember=w.removeOneVar(auOne_asAMember,12);
         if(w.checkOneVar(auOne_asAMember, 2) ||  tutorMode) sld_gotoSlide(-7,true);
      }
    });
    sld_menuPointer.add(sld_itemArrow);
    sld_menuPointer.add(sld_itemLaser);
    sld_menuPointer.add(sld_itemPen);
    sld_menuPointer.add(sld_itemClear);

    sld_popupMenu.add(sld_itemDown);
    sld_popupMenu.add(sld_itemUp);
    sld_popupMenu.add(sld_itemFirst);
    sld_popupMenu.add(sld_itemLast);

    sld_popupMenu.add(sld_menuScreen);
    sld_popupMenu.add(sld_menuPointer);
    sld_popupMenu.add(sld_itemEnd);
    sld_imgPanel=new CTSldImgPanel(this);
    slidePanel.add(sld_imgPanel, java.awt.BorderLayout.CENTER);

      srn_startStr=bundle2.getString("CTScreenBoard.xy.msg1");
        srn_stopStr=bundle2.getString("CTScreenBoard.xy.msg2");

        allNodesName=bundle2.getString("CTScreenBoard.xy.msg3");

        btn_srnStart.setText(srn_startStr);

        srn_kfm= KeyboardFocusManager.getCurrentKeyboardFocusManager();
        srn_kfm.addKeyEventPostProcessor(new KeyEventPostProcessor() {
            public boolean postProcessKeyEvent(KeyEvent evt) {
                if (evt.getID() == KeyEvent.KEY_PRESSED) {
                     srn_keyPress(evt);
                } else if  (evt.getID() == KeyEvent.KEY_RELEASED){
                     srn_keyRelease(evt);
                    }
                return true;
            }
        });

    skPanel2=new CTSketchPanel2(this);
    sketchPanel.add(skPanel2, java.awt.BorderLayout.CENTER);

  }
public void init(){

        allNodesName=bundle2.getString("CTModerator.xy.msg6");
        myNodeName=bundle2.getString("CTModerator.xy.msg7");

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
        cbListener = new CTCBListener(this);
        cbListener.start();
        byteArrayStream = new ByteArrayOutputStream();

       if(!(new File(sldLogDir)).exists())  (new File(sldLogDir)).mkdirs();

        (new CTMsgBlinker2(this)).start();
        if(!(new File(msgLogDir)).exists())  (new File(msgLogDir)).mkdirs();

        cbb_moderatorSldList.removeAllItems();
     cbb_moderatorSkList.removeAllItems();
     cbb_moderatorMsgList.removeAllItems();
     cbb_moderatorSrnList.removeAllItems();
     cbb_moderatorFList.removeAllItems();
     cbb_moderatorPermissionList.removeAllItems();
     cbb_moderatorElseList.removeAllItems();
     cbb_moderatorStatusList.removeAllItems();
     cbb_sldMList.removeAllItems();
     cbb_skMList.removeAllItems();
     cbb_msgMList.removeAllItems();
     cbb_srnMList.removeAllItems();
     cbb_fMlist.removeAllItems();
     cbb_sldMList.addItem("");
     cbb_msgMList.addItem("");
     cbb_fMlist.addItem("");
     cbb_skMList.addItem("");
     String tmpName=makeListItem(allNodesName,"0");
     cbb_moderatorSldList.addItem(tmpName);
     cbb_moderatorSkList.addItem(tmpName);
     cbb_moderatorMsgList.addItem(tmpName);
     cbb_moderatorSrnList.addItem(tmpName);
     cbb_moderatorFList.addItem(tmpName);
     cbb_moderatorPermissionList.addItem(tmpName);
     cbb_moderatorElseList.addItem(tmpName);
     cbb_moderatorStatusList.addItem(tmpName);
     cbb_sldMList.addItem(tmpName);
     cbb_skMList.addItem(tmpName);
     cbb_msgMList.addItem(tmpName);
     cbb_srnMList.addItem(tmpName);
     tmpName=makeListItem(myNodeName,"temp");

        sld_currentId=(String)nameIdMap.get((String)cbb_sldMList.getSelectedItem());
        msg_currentId=(String)nameIdMap.get((String)cbb_msgMList.getSelectedItem());
        srn_currentId=(String)nameIdMap.get((String)cbb_srnMList.getSelectedItem());
        sk_currentId=(String)nameIdMap.get((String)cbb_skMList.getSelectedItem());

        if(!(new File(screenLogDir)).exists())  (new File(screenLogDir)).mkdirs();
        btn_srnACD.setVisible(false);

        if(!(new File(fileLogDir)).exists())  (new File(fileLogDir)).mkdirs();
        f_skipComboBox1ItemStateChanged=true;
        cbb_fList1.removeAllItems();
        f_skipComboBox1ItemStateChanged=false;
        f_skipComboBox2ItemStateChanged=true;

        cbb_fList2.removeAllItems();
        f_skipComboBox2ItemStateChanged=false;
        JScrollPane scrollpane =  new JScrollPane(getDirBrowser("Locale Computer",this));
        f_panel7.add(scrollpane,BorderLayout.CENTER);
        f_rdb=CTFBRemoteDirBrowser.getRemoteDirBrowser("Remote Computer",this);

        JScrollPane scrollpane2 =  new JScrollPane(f_rdb);
        jPanel31.add(scrollpane2,BorderLayout.CENTER);
        f_currentId="";
   f_popupMenu1.add(f_itemUpload1);
   f_popupMenu1.addSeparator();
   f_popupMenu1.add(f_itemAddDir1);
   f_popupMenu1.addSeparator();
   f_popupMenu1.add(f_itemDelete1);
   f_popupMenu1.add(f_itemRename1);
    f_itemUpload1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        int rowindex[] = f_table1.getSelectedRows();
          TreeMap uploadTM=new TreeMap();
          for(int i=0;i<rowindex.length;i++){

            String name=(String)f_table1.getModel().getValueAt(rowindex[i], 0);
            uploadTM.put(CTModerator.this.f_current_local_absolutePath+File.separator+name, "2");
          }
           f_currentRandom=""+Math.random();

           f_upload(uploadTM);
      }
    });
    f_itemAddDir1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int rowindex = f_table1.getSelectedRow();

        String name=(String)f_table1.getModel().getValueAt(rowindex, 0);
        if(f_rename2==null) f_rename2=new CTFBRename(CTModerator.this,true);
        f_rename2.setValue(CTModerator.this.f_current_local_absolutePath+File.separator+name, "NewFolder", 3,2);
        f_rename2.setVisible(true);

      }
    });
    f_itemDelete1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int rowindex[] = f_table1.getSelectedRows();
        int an=JOptionPane.showConfirmDialog(null, bundle2.getString("CTModerator.xy.msg40"),bundle2.getString("CTModerator.xy.msg41") , JOptionPane.YES_NO_CANCEL_OPTION);
        if(an==JOptionPane.YES_OPTION){

          for(int i=0;i<rowindex.length;i++){

            String name=(String)f_table1.getModel().getValueAt(rowindex[i], 0);

            f_LDThread.addLDAction(CTModerator.this.f_current_local_absolutePath+File.separator+name);
          }
          f_skipComboBox1ItemStateChanged=true;
          String absPath=(String)cbb_fList1.getSelectedItem();
          f_skipComboBox1ItemStateChanged=false;

          f_showLocalDir(absPath,f_local_orderby,f_local_asc);
        }

      }
    });
    f_itemRename1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        

        int rowindex = f_table1.getSelectedRow();

        String name=(String)f_table1.getModel().getValueAt(rowindex, 0);
        if(f_rename2==null) f_rename2=new CTFBRename(CTModerator.this,true);
        f_rename2.setValue(CTModerator.this.f_current_local_absolutePath, name, 1,2);
        f_rename2.setVisible(true);
      }
    });
   f_popupMenu2.add(f_itemDownload2);
   f_popupMenu2.addSeparator();
   f_popupMenu2.add(f_itemAddDir2);
   f_popupMenu2.addSeparator();
   f_popupMenu2.add(f_itemDelete2);
   f_popupMenu2.add(f_itemRename2);
   f_itemDownload2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        int rowindex[] = f_table2.getSelectedRows();
          TreeMap downloadTM=new TreeMap();
          for(int i=0;i<rowindex.length;i++){

            String name=(String)f_table2.getModel().getValueAt(rowindex[i], 0);
            downloadTM.put(CTModerator.this.f_current_remote_absolutePath+CTModerator.this.f_remoteFileSeparator+name, "2");
          }
           f_currentRandom=""+Math.random();

           f_download(downloadTM);
      }
    });
   f_itemAddDir2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int rowindex = f_table2.getSelectedRow();

        String name=(String)f_table2.getModel().getValueAt(rowindex, 0);
        if(f_rename2==null) f_rename2=new CTFBRename(CTModerator.this,true);
        f_rename2.setValue(CTModerator.this.f_current_remote_absolutePath+CTModerator.this.f_remoteFileSeparator+name, "NewFolder", 4,2);
        f_rename2.setVisible(true);

      }
    });
    f_itemDelete2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int rowindex[] = f_table2.getSelectedRows();
        int an=JOptionPane.showConfirmDialog(null, bundle2.getString("CTModerator.xy.msg40"),bundle2.getString("CTModerator.xy.msg41"), JOptionPane.YES_NO_CANCEL_OPTION);
        if(an==JOptionPane.YES_OPTION){
          TreeMap deleteTM=new TreeMap();
          for(int i=0;i<rowindex.length;i++){

            String name=(String)f_table2.getModel().getValueAt(rowindex[i], 0);
            deleteTM.put(CTModerator.this.f_current_remote_absolutePath+CTModerator.this.f_remoteFileSeparator+name, "2");
          }
          if(deleteTM.size()>0) f_deleteRemote(deleteTM);
        }
      }
    });
   f_itemRename2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int rowindex = f_table2.getSelectedRow();

        String name=(String)f_table2.getModel().getValueAt(rowindex, 0);
        if(f_rename2==null) f_rename2=new CTFBRename(CTModerator.this,true);
        f_rename2.setValue(CTModerator.this.f_current_remote_absolutePath, name, 2,2);
        f_rename2.setVisible(true);

      }
    });
     f_table1.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent e) {
        int col =f_table1.columnAtPoint(e.getPoint());
        f_sortLocalTable(col);
      }
    });
     f_table2.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent e) {
        int col =f_table2.columnAtPoint(e.getPoint());
        f_sortRemoteTable(col);
      }
    });

   if(!(new File(sketchLogDir)).exists())  (new File(sketchLogDir)).mkdirs();

      if(!(new File(statusLogDir)).exists())  (new File(statusLogDir)).mkdirs();

}
private void f_sortLocalTable(int col){
  System.out.println("begin of f_sortLocalTable(int col)");
  if(col!=currentTbl1SortIndex) {currentTbl1SortIndex=col; currentTbl1SortAsc=true;}
  else currentTbl1SortAsc=(!currentTbl1SortAsc);
  int rcount=f_table1.getModel().getRowCount();
  if(rcount>1){
     String data[][]=new String[rcount][f_table1.getModel().getColumnCount()];
     for(int i=0;i<rcount;i++)  {
       for(int j=0;j<data[i].length;j++){
         data[i][j]=(String)f_table1.getModel().getValueAt(i, j);
       }
     }
     data=f_sortStrArr(data,col,currentTbl1SortAsc);
     for(int i=0;i<data.length;i++){
       for(int j=0;j<data[i].length;j++){
         f_table1.getModel().setValueAt(data[i][j], i, j);
       }
     }
  }
  System.out.println("end of f_sortLocalTable(int col)");
}
String [][] f_sortStrArr(String array[][],int col, boolean asc){
        String small;
        for(int k = 0; k < array.length; k++)  {

                small = array[k][col];
                int smallrow=k;
                long smallLong=getFileSize(array[k][3]);
                int smallLength=array[k][3].length();
                for(int i = k+1; i < array.length; i++)  {
                  switch(col){
                    case 0:
                      if(asc){
                        if(array[i][3].length()==0 && smallLength>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                        else if(array[i][3].length()>0 && smallLength==0) {}
                        else if(array[i][col].compareToIgnoreCase(small)<0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                      } else {
                          if(array[i][3].length()==0 && smallLength>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                          else if(array[i][3].length()>0 && smallLength==0) {}
                          else  if(array[i][col].compareToIgnoreCase(small)>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                        }
                      break;
                    case 1:
                      if(asc){
                        if(array[i][3].length()==0 && smallLength>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                          else if(array[i][3].length()>0 && smallLength==0) {}
                          else if(array[i][col].compareToIgnoreCase(small)<0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                      } else {
                          if(array[i][3].length()==0 && smallLength>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                          else if(array[i][3].length()>0 && smallLength==0) {}
                          else  if(array[i][col].compareToIgnoreCase(small)>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                        }
                      break;
                    case 2:
                      if(asc){
                          if(array[i][3].length()==0 && smallLength>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                          else if(array[i][3].length()>0 && smallLength==0) {}
                          else if(array[i][col].compareToIgnoreCase(small)<0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                      } else {
                          if(array[i][3].length()==0 && smallLength>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                          else if(array[i][3].length()>0 && smallLength==0) {}
                          else if(array[i][col].compareToIgnoreCase(small)>0) {smallrow=i; small=array[i][col]; smallLength=array[i][3].length();}
                        }
                      break;
                    case 3:
                      if(asc){
                          if(array[i][3].length()==0 && smallLength>0) {smallrow=i; small=array[i][col]; smallLong=getFileSize(array[i][col]); smallLength=array[i][3].length();}
                          else if(array[i][3].length()>0 && smallLength==0) {}
                          else if(array[i][3].length()==0 && smallLength==0) {}
                          else {
                             int kinx=small.indexOf("KB"),iinx=array[i][3].indexOf("KB");
                             if(kinx>-1 && iinx==-1) {smallrow=i; small=array[i][col]; smallLong=getFileSize(array[i][col]); smallLength=array[i][3].length();}
                             else if(kinx==-1 && iinx>-1){}
                             else {
                               long isize=getFileSize(array[i][3]);

                               if(isize<smallLong) {smallrow=i; small=array[i][col]; smallLong=getFileSize(array[i][col]); smallLength=array[i][3].length();}
                             }
                          }
                      } else {
                          if(array[i][3].length()==0 && smallLength>0) {smallrow=i; small=array[i][col]; smallLong=getFileSize(array[i][col]); smallLength=array[i][3].length();}
                          else if(array[i][3].length()>0 && smallLength==0) {}
                          else if(array[i][3].length()==0 && smallLength==0) {}
                          else {
                             int kinx=small.indexOf("KB"),iinx=array[i][3].indexOf("KB");
                             if(kinx==-1 && iinx>-1) {smallrow=i; small=array[i][col]; smallLong=getFileSize(array[i][col]); smallLength=array[i][3].length();}
                             else if(kinx>-1 && iinx==-1){}
                             else {
                               long isize=getFileSize(array[i][3]);

                               if(isize>smallLong) {smallrow=i; small=array[i][col]; smallLong=getFileSize(array[i][col]); smallLength=array[i][3].length();}
                             }
                          }
                        }
                      break;
                  }
                }
                String smallArr[]=array[smallrow];
            array[smallrow] = array[k];
            array[k] = smallArr;
        }
        return array;
}
private long getFileSize(String si){
  if(si.length()>0){
    String lStr="";
    int inx=si.indexOf(" ");
    int inxK=si.indexOf(" KB");
    lStr=si.substring(0, inx);
    if(inxK>-1) lStr=lStr+"024";
    return Long.parseLong(lStr);
  } else return 0;
}
private void f_sortRemoteTable(int col){
  if(col!=currentTbl2SortIndex) {currentTbl2SortIndex=col; currentTbl2SortAsc=true;}
  else currentTbl2SortAsc=(!currentTbl2SortAsc);
  int rcount=f_table2.getModel().getRowCount();
  if(rcount>1){
     String data[][]=new String[rcount][f_table2.getModel().getColumnCount()];
     for(int i=0;i<rcount;i++)  {
       for(int j=0;j<data[i].length;j++){
         data[i][j]=(String)f_table2.getModel().getValueAt(i, j);
       }
     }
     data=f_sortStrArr(data,col,currentTbl2SortAsc);
     for(int i=0;i<data.length;i++){
       for(int j=0;j<data[i].length;j++){
         f_table2.getModel().setValueAt(data[i][j], i, j);
       }
     }
  }
}

  public boolean f_deleteDir(File dir) {
    if (dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = f_deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
    return dir.delete(); 
}
String getHtmlMsg(String msg){
  int width=200;
  if(msg.length()>80) width=300;
  else if(msg.length()>160) width=400;
  else if(msg.length()>300) width=500;
  else if(msg.length()>500) width=600;
  else if(msg.length()>1500) width=800;
  return "<html><body><p style='width: "+width+"px;'>"+msg+"</p></body></html>";
}
public void f_updateTable1(String dir){
        if(dir!=null && dir.length()>0){
            File f=new File(dir);
            if(f.exists()){
            if(f.isDirectory()){
                dir=f.getAbsolutePath();
                f_current_local_absolutePath=dir;

                f_showLocalDir(dir,f_local_orderby,f_local_asc);
            } else {
                dir=f.getParentFile().getAbsolutePath();
                f_current_local_absolutePath=dir;

                f_showLocalDir(dir,f_local_orderby,f_local_asc);
            }
              boolean found=false;
              for(int j=0;j<cbb_fList1.getItemCount();j++){
                    String tmp=(String)cbb_fList1.getItemAt(j);
                    if(tmp.equals(dir)) {found=true; break;}
              }
              f_skipComboBox1ItemStateChanged=true;
              if(!found) {
                cbb_fList1.addItem(dir);
              }
              cbb_fList1.setSelectedItem(dir);
              f_skipComboBox1ItemStateChanged=false;
            } else {
                JOptionPane.showMessageDialog(this,getHtmlMsg("\""+dir+"\" not exists."));
            }
        }
 }
public void f_updateTabTitle(int tabId){
  switch(tabId){
    case 0:
      f_tabbedPane1.setTitleAt(0, bundle2.getString("CTModerator.f_panel18.TabConstraints.tabTitle")+(f_waitCnt>0? "("+f_waitCnt+")":""));
      break;
    case 1:
      f_tabbedPane1.setTitleAt(1, bundle2.getString("CTModerator.jPanel6.TabConstraints.tabTitle")+(f_failedCnt>0? "("+f_failedCnt+")":""));
      break;
    case 2:
      f_tabbedPane1.setTitleAt(2, bundle2.getString("CTModerator.jPanel33.TabConstraints.tabTitle")+(f_okCnt>0? "("+f_okCnt+")":""));
      break;
  }
}
public void f_updateAllTabTitle(){
  f_updateTabTitle(0);
  f_updateTabTitle(1);
  f_updateTabTitle(2);
}

 CTFBRemoteDirNode f_getNodeFromAbsPath(String absPath){
    TreePath tp=f_getTPFromAbsPath(absPath);
    CTFBRemoteDirNode node=(CTFBRemoteDirNode)((DefaultMutableTreeNode)tp.getLastPathComponent());
    return node;
}
TreePath f_getTPFromAbsPath(String absPath){

  String splitter = File.separator.replace("\\","\\\\");
  String arr[]=absPath.split(splitter);
    Object o[]=new Object[arr.length];
    for(int i=0;i<o.length;i++) o[i]=arr[i];
    return new TreePath(o);
}
private TreePath f_getTreePathFromNode1(CTFBDirNode node) {
    TreeNode[] nodes = ((DefaultTreeModel)f_browser.getModel()).getPathToRoot(node);
    if(nodes!=null) return (new TreePath(nodes));
    else return null;
}
private TreePath f_getTreePathFromNode2(CTFBRemoteDirNode node) {
    TreeNode[] nodes = ((DefaultTreeModel)f_rdb.getModel()).getPathToRoot(node);
    if(nodes!=null) return (new TreePath(nodes));
    else return null;
}

  public void f_addDownloadAction(String action){
      f_downloadActions.add(action);
      if(f_downloadThread.isSleep()) f_downloadThread.interrupt();
  }

  public void f_getDownloadFileVector(String sdir,File f,String ddir, String dfilename,String nsdir,String writemode){
     String relative="";
     if(sdir.length()<nsdir.length()) relative=nsdir.substring(sdir.length());
     f_downloadFileVector.add(ylib.tocsv(f.getAbsolutePath())+","+f.lastModified()+","+f.length()+","+ylib.tocsv(ddir)+","+ylib.tocsv(relative)+","+ylib.tocsv(dfilename));
     if(f_downloadFileVector.size()>=f_fileVectorLimit){

     }
  }

 public void f_getDownloadFileVectors(String sdir,String key2,long fromtime,long totime,String subdirmode,String ddir, String nsdir,String writemode){
               if(sdir.lastIndexOf(File.separator)!=sdir.length()-1) sdir=sdir+File.separator;
               if(nsdir.lastIndexOf(File.separator)!=nsdir.length()-1) nsdir=nsdir+File.separator;
               if(ddir.lastIndexOf(File.separator)!=ddir.length()-1) ddir=ddir+File.separator;
               File f=new File(nsdir);
                                          File[] contents1=f.listFiles();
                                          Arrays.sort(contents1);
                                          for(int x1=0;x1<contents1.length;x1++) {
                                            if(ylib.chkfn(key2,contents1[x1].getName()) && contents1[x1].isFile()) {
                                            	f_getDownloadFileVector(sdir,contents1[x1],ddir,contents1[x1].getName(),nsdir,writemode);
                                            }
                                          }
                                          for(int x1=0;x1<contents1.length;x1++) {
                                            if(contents1[x1].isDirectory()) {
                                              f_getDownloadFileVectors(sdir,key2,fromtime,totime,subdirmode,ddir,contents1[x1].getAbsolutePath(),writemode);
                                            }
                                          }
  }
  

  public void f_sendFile2(String sdir,File f,String ddir, String dfilename,String nsdir,String writemode,String originalId2,String originalId){
      boolean yesOk=false,sameOrigi=(originalId2.equals(originalId));
      System.out.println("f_sendFile2(),sdir="+sdir+",f="+f.getAbsolutePath()+",ddir="+ddir+",dfilename="+dfilename+",nsdir="+nsdir+",writemode="+writemode);

      String cmdCode=(sameOrigi ? "%comecmdcode%":"%cmdcode%");
       try{
                                            	  FileInputStream in = new FileInputStream(f);
                                            	  BufferedInputStream d= new BufferedInputStream(in);
                                                  String append2="false"; 
                                                  int inx=0,accum=0;
                                                  String relative="";
                                                  if(sdir.length()<nsdir.length()) relative=nsdir.substring(sdir.length());
                                                  int filesize=in.available();

                                                  int endb=0;
                                                  int diff=0;

                                                  int S=36414;
                                                  StringBuffer fileonly;
                                                  if(filesize>0){
                                                    for (int i = 0; i<filesize; i+=S){

                                                      endb=((i+S)>filesize? filesize:(i+S));
                                                      diff=endb-i;
                                                      byte [] b2=new byte[diff];
                                                      d.read(b2,0,diff);
                                                      accum+=b2.length;

                                                     fileonly=new StringBuffer("performaction ct.CTModerator 1 f_savefile "+ylib.replace(ddir+relative+dfilename," ","%space%")+
                                                              " "+f.lastModified()+" "+append2+" "+writemode+

                                                              " "+(new String(Weber.en(b2))).toString()+" "+inx+" "+accum+" "+filesize+" "+sameOrigi+" "+ylib.replace(sdir+(sdir.endsWith(File.separator)? "":File.separator)+relative+dfilename," ","%space%")+" 0");

                                                      if(sameOrigi) w.ap.feedback(w.getMode0(originalId),0,cmdCode,originalId,fileonly.toString()); 
                                                      else w.sendToOne(fileonly.toString(), originalId);
                                                      append2="true";
                                                      if(inx %10 ==0 && !sameOrigi) f_appendFileWaitLog(sdir+(sdir.endsWith(File.separator)? "":File.separator)+relative+f.getName()+" -->> "+ddir+(ddir.endsWith(File.separator)? "":File.separator)+relative+dfilename+" ("+f.length()+", "+((double)Math.round(((double)accum)/((double)filesize)*10000.0))/100.0+"%)\r\n");
                                                      inx++;
                                                    }

                                                  }
                                                  in.close();
                                                } catch (FileNotFoundException e){e.printStackTrace();}
                                                  catch (IOException e){e.printStackTrace();}
  }
  synchronized void f_saveFile2(String stringx[],int mode0,String originalId){
     File f=new File(stringx[1]),fbak=new File(stringx[1]+".crtemp.bak");
     long filetime=Long.parseLong(stringx[2]);
     boolean append2=Boolean.parseBoolean(stringx[3]);
     String writemode=stringx[4];
     int inx=Integer.parseInt(stringx[6]);
     int accum=Integer.parseInt(stringx[7]);
     int filesize=Integer.parseInt(stringx[8]);
     String sameOrigi=stringx[9];
     String sfile=stringx[10];

     System.out.println("in f_savefile:lastInx="+lastFileInx+",inx="+stringx[6]+",accum="+stringx[7]+",filesize="+filesize);
     if(inx==0 || inx==lastFileInx+1){
       if(inx==0){
         if(f.exists()) {existSaveFile=true;}
         else existSaveFile=false;
       } 
       lastFileInx=inx;
        try{

          if(!append2 && !existSaveFile){
              File f2=f.getParentFile();
              if(!f2.exists()) f2.mkdirs();
          }
            FileOutputStream out = new FileOutputStream ( (existSaveFile? fbak:f) , append2);

            byte [] b=Weber.de(stringx[5].toCharArray());
            out.write(b);
            out.close();
            if(inx %10 ==0 && sameOrigi.equalsIgnoreCase("true")) f_appendFileWaitLog(stringx[1]+" <<-- "+sfile+" ("+filesize+", "+((double)Math.round(((double)accum)/((double)filesize)*10000.0))/100.0+"%)\r\n");
            if(accum==filesize){
              if(sameOrigi.equalsIgnoreCase("true")){
               f_appendFileWaitLog(stringx[1]+" <<-- "+sfile+" ("+filesize+", "+((double)Math.round(((double)accum)/((double)filesize)*10000.0))/100.0+"%)\r\n");
               jTextArea1.append(format5.format(new Date())+" "+stringx[1]+" <<-- "+sfile+" ("+filesize+")\r\n");
              } else {
                String  cmd2="performcommand ct.CTModerator f_aftersaveupload_ok "+w.e642(sfile+" -->> "+stringx[1]+" ("+filesize+")")+" 0 0 0 0 0  0 0 0 0 0 ";
                w.ap.feedback(mode0,0,"%comecmdcode%",originalId,cmd2); 
                }
              if(existSaveFile){
                if(f.delete()) fbak.renameTo(f);
              }
             lastFileInx=-1;
             existSaveFile=false;
            }
        } catch (FileNotFoundException e){
          e.printStackTrace();
        } catch (IOException e){
              e.printStackTrace(); 
               String msg="message: savefile ioexception: "+e.getMessage()+"\r\n";
                w.ap.feedback(mode0,0,"%comecmdcode%",originalId,"sys_mes"+sep+msg);
          }
     }
     else if(accum==filesize){
       if(sameOrigi.equalsIgnoreCase("true")) jTextArea3.append(format5.format(new Date())+" "+stringx[1]+" <<-- "+sfile+" ("+filesize+")\r\n");
       else {
         String  cmd2="performcommand ct.CTModerator f_aftersaveupload_failed "+w.e642(sfile+" -->> "+stringx[1]+" ("+filesize+")")+" 0 0 0 0 0  0 0 0 0 0 ";
         w.ap.feedback(mode0,0,"%comecmdcode%",originalId,cmd2); 
       }
       (existSaveFile? fbak:f).delete();
       lastFileInx=-1;
       existSaveFile=false;
     }
  }
  

  public void f_sendFile3(String sdir,File f,String ddir, String dfilename,String nsdir,String writemode,String originalId2,String originalId,long onevar){
      boolean yesOk=false,sameOrigi=(originalId2.equals(originalId));

      String cmdCode=(sameOrigi ? "%comecmdcode%":"%cmdcode%");

       try{
                                            	  FileInputStream in = new FileInputStream(f);
                                            	  BufferedInputStream d= new BufferedInputStream(in);
                                                  String append2="false"; 
                                                  int inx=0,accum=0;
                                                  String relative="";
                                                  if(sdir.length()<nsdir.length()) relative=nsdir.substring(sdir.length());
                                                  int filesize=in.available();

                                                  int endb=0;
                                                  int diff=0;
                                                  int S=4194304;
                                                  StringBuffer fileonly;
                                                  if(filesize>0){
                                                   if(!sameOrigi)  f_appendFileWaitLog(sdir+(sdir.endsWith(File.separator)? "":File.separator)+relative+f.getName()+" -->> "+ddir+(ddir.endsWith(File.separator)? "":File.separator)+ylib.replace(ylib.replace(relative,"/",this.f_remoteFileSeparator),"\\",this.f_remoteFileSeparator)+dfilename+" ("+f.length()+")\r\n");
                                                    for (int i = 0; i<filesize; i+=S){

                                                      endb=((i+S)>filesize? filesize:(i+S));
                                                      diff=endb-i;
                                                      byte [] b2=new byte[diff];
                                                      d.read(b2,0,diff);
                                                      accum+=b2.length;

                                                     fileonly=new StringBuffer("performactionobject2 ct.CTModerator f_savefile2 "+ylib.replace(ddir+ylib.replace(ylib.replace(relative,"/",this.f_remoteFileSeparator),"\\",this.f_remoteFileSeparator)+dfilename," ","%space%")+
                                                              " "+f.lastModified()+" "+append2+" "+writemode+

                                                              " "+inx+" "+accum+" "+filesize+" "+sameOrigi+" "+ylib.replace(sdir+(sdir.endsWith(File.separator)? "":File.separator)+relative+dfilename," ","%space%")+" "+onevar+" 0");

        Obj obj=new Obj(fileonly.toString(),b2);
                   if(sameOrigi) w.ap.feedbackObj2(w.getMode0(originalId),0,cmdCode,originalId,obj); 
                        else w.sendObj2ToOne(obj, originalId);
                   

                                                      append2="true";

                                                      if(!sameOrigi) f_appendFileWaitLog(sdir+(sdir.endsWith(File.separator)? "":File.separator)+relative+f.getName()+" -->> "+ddir+(ddir.endsWith(File.separator)? "":File.separator)+relative+dfilename+" ("+f.length()+", "+((double)Math.round(((double)accum)/((double)filesize)*10000.0))/100.0+"%)\r\n");
                                                      inx++;
                                                    }

                                                  }
                                                  in.close();
                                                } catch (FileNotFoundException e){e.printStackTrace();}
                                                  catch (IOException e){e.printStackTrace();}
  }

  synchronized void f_saveFile3(String stringx[],Object obj,int mode0,String originalId){
    byte data[]=(byte[])obj;
     File f=new File(stringx[1]),fbak=new File(stringx[1]+".crtemp.bak");
     long filetime=Long.parseLong(stringx[2]);
     boolean append2=Boolean.parseBoolean(stringx[3]);
     String writemode=stringx[4];
     int inx=Integer.parseInt(stringx[5]);
     int accum=Integer.parseInt(stringx[6]);
     int filesize=Integer.parseInt(stringx[7]);
     String sameOrigi=stringx[8];
     String sfile=stringx[9];
     long onevar=Long.parseLong(stringx[10]);
     if(inx==0 && sameOrigi.equalsIgnoreCase("true")) {
                 String msg=stringx[1]+" <<-- "+sfile+" ("+filesize+")\r\n";
              f_appendFileWaitLog(msg);
       }

     if(inx==0 || inx==lastFileInx+1){
       if(inx==0){
         if(f.exists()) {existSaveFile=true;}
         else existSaveFile=false;
       } 
       lastFileInx=inx;
        try{

          if(!append2 && !existSaveFile){
              File f2=f.getParentFile();
              if(!f2.exists()) f2.mkdirs();
          }
            FileOutputStream out = new FileOutputStream ( (existSaveFile? fbak:f) , append2);

            out.write(data);
            out.close();

            String msg=stringx[1]+" <<-- "+sfile+" ("+filesize+", "+((double)Math.round(((double)accum)/((double)filesize)*10000.0))/100.0+"%)\r\n";
            if(sameOrigi.equalsIgnoreCase("true")) f_appendFileWaitLog(msg);
            if(accum==filesize){
              if(existSaveFile){
                if(f.delete()) fbak.renameTo(f);
              }
              if(sameOrigi.equalsIgnoreCase("true")){

               f_appendFileOKLog(format5.format(new Date())+" "+stringx[1]+" <<-- "+sfile+" ("+filesize+")\r\n");
               f_appendFileWaitLog("");
               f_okCnt++;
               if(f_waitCnt>0) f_waitCnt--;
               f_updateAllTabTitle();
               String msg2=format2.format(new Date())+" : "+sfile+" -->> "+stringx[1]+" ("+filesize+") to "+w.getGNS(27);
               String cmd2="performmessage ct.CTModerator status "+w.e642(msg2)+" 0";

                w.sendToOne(cmd2, originalId);
               if(w.checkOneVar(onevar, 1)){
                  String absPath=(String)cbb_fList1.getSelectedItem();

                  f_showLocalDir(absPath,f_local_orderby,f_local_asc);
               }
              } else {
                String  cmd2="performcommand ct.CTModerator f_aftersaveupload_ok "+w.e642(sfile+" -->> "+stringx[1]+" ("+filesize+")")+" 0 0 0 0 0  0 0 0 0 0 ";
                w.ap.feedback(mode0,0,"%comecmdcode%",originalId,cmd2); 
                appendStatus(format2.format(new Date())+" : "+stringx[1]+" <<-- "+sfile+" ("+filesize+") from "+getNameFromId(originalId)+"\r\n");
                }
             lastFileInx=-1;
             existSaveFile=false;
            }
        } catch (FileNotFoundException e){
          e.printStackTrace();
              if(sameOrigi.equalsIgnoreCase("true")){
               jTextArea3.append(format5.format(new Date())+" "+stringx[1]+" <<-- "+sfile+" ("+filesize+") (File not found Exception: "+e.getMessage()+")\r\n");
               if(w.checkOneVar(onevar, 1)){
                  String absPath=(String)cbb_fList1.getSelectedItem();

                  f_showLocalDir(absPath,f_local_orderby,f_local_asc);
               }
               f_appendFileWaitLog("");
               f_failedCnt++;
               if(f_waitCnt>0) f_waitCnt--;
               f_updateAllTabTitle();
              } else {
                String  cmd2="performcommand ct.CTModerator f_aftersaveupload_failed "+w.e642(sfile+" -->> "+stringx[1]+" ("+filesize+")")+" 0 0 0 0 0  0 0 0 0 0 ";
                w.ap.feedback(mode0,0,"%comecmdcode%",originalId,cmd2); 
                }

        } catch (IOException e){
              e.printStackTrace(); 
              if(sameOrigi.equalsIgnoreCase("true")){
               jTextArea3.append(format5.format(new Date())+" "+stringx[1]+" <<-- "+sfile+" ("+filesize+") (IOException: "+e.getMessage()+")\r\n");
               f_appendFileWaitLog("");
               f_failedCnt++;
               if(f_waitCnt>0) f_waitCnt--;
               f_updateAllTabTitle();
              } else {
                String  cmd2="performcommand ct.CTModerator f_aftersaveupload_failed "+w.e642(sfile+" -->> "+stringx[1]+" ("+filesize+")")+" 0 0 0 0 0  0 0 0 0 0 ";
                w.ap.feedback(mode0,0,"%comecmdcode%",originalId,cmd2); 
                }

          }
     }
     else if(accum==filesize){
       if(sameOrigi.equalsIgnoreCase("true")) {
         jTextArea3.append(format5.format(new Date())+" "+stringx[1]+" <<-- "+sfile+" ("+filesize+") (Transmission erron)\r\n");
         f_appendFileWaitLog("");
         f_failedCnt++;
               if(f_waitCnt>0) f_waitCnt--;
               f_updateAllTabTitle();
       }
       else {
         String  cmd2="performcommand ct.CTModerator f_aftersaveupload_failed "+w.e642(sfile+" -->> "+stringx[1]+" ("+filesize+") (Transmission erron)")+" 0 0 0 0 0  0 0 0 0 0 ";
         w.ap.feedback(mode0,0,"%comecmdcode%",originalId,cmd2); 
       }
       (existSaveFile? fbak:f).delete();
       lastFileInx=-1;
       existSaveFile=false;
     }
  }
  public String getNewName(File file){
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

  public void f_setDownloadChkSaveResult(String writemode,String secondPara,String myDownloadMode){
      this.f_downloadChkSaveResult=writemode;
      this.f_downloadChkSaveSecondPara=secondPara;
      if(myDownloadMode.length()>0) this.f_currentRemoteWriteMode=myDownloadMode;

      if(f_downloadThread!=null && f_downloadThread.isSleep()) f_downloadThread.interrupt();
  }
  public void f_setUploadChkSaveResult(String writemode,String myUploadMode){
      this.f_uploadChkSaveResult=writemode;

      if(myUploadMode.length()>0) this.f_myUploadMode=myUploadMode;
  }
  public void f_setDownloadChkExistResult(boolean r,String secondPara){
      this.f_chkExistResult=r;
      this.f_downloadChkExistSecondPara=secondPara;
      if(f_downloadThread!=null && f_downloadThread.isSleep()) f_downloadThread.interrupt();
  }

  public void f_setUploadChkExistResult(boolean r,String secondPara,String thirdPara,String fourthPara,String fifthPara,String sixthPara){
      this.f_uploadChkExistResult=r;
      this.f_uploadChkExistSecondPara=secondPara;
      this.f_uploadChkExistThirdPara=thirdPara;
      this.f_uploadChkExistFourthPara=fourthPara;
      this.f_uploadChkExistFifthPara=fifthPara;
      this.f_uploadChkExistSixthPara=sixthPara;
      if(f_uThread!=null && f_uThread.f_isSleep2) f_uThread.interrupt();
  }
  public void f_showLocalDir(String dir,int orderby,boolean asc){

     int rcount=f_table1.getModel().getRowCount();
     for(int i=rcount-1;i>-1;i--)  {if(f_table1.getModel().getRowCount()>i) ((DefaultTableModel)f_table1.getModel()).removeRow(i);}
     int inx=0,listInx=0;
     File dirF=new File(dir);
     if(currentTbl1SortIndex>-1 && f_current_local_absolutePath.equalsIgnoreCase(dirF.getAbsolutePath())){
      TreeMap tm=new TreeMap();
     if(dirF!=null){
         f_current_local_absolutePath=dirF.getAbsolutePath();
         File fList[]=dirF.listFiles();
         if(fList!=null){
         for(inx=0;inx<fList.length;inx++){
             if(fList[inx].isDirectory()){
               tm.put(fList[inx].getName(), ylib.tocsv(fList[inx].getName())+","+f_dirType+","+format1.format(new Date(fList[inx].lastModified()))+",");
             } else {
               String name=fList[inx].getName();
               int dotInx=name.lastIndexOf(".");
               String temp=(fList[inx].length()>1024L? (fList[inx].length()/1024)+ " KB":fList[inx].length()+ " byte");
               tm.put(fList[inx].getName(), ylib.tocsv(name)+","+(dotInx>-1? name.substring(dotInx+1):"")+","+format1.format(new Date(fList[inx].lastModified()))+","+temp);
             }
         }
     if(tm.size()>0){
     Iterator it=tm.values().iterator();
     String data[][]=new String[tm.size()][4];
     for(;it.hasNext();){
       data[listInx]=ylib.csvlinetoarray((String)it.next());
       listInx++;
     }
     data=this.f_sortStrArr(data, currentTbl1SortIndex, currentTbl1SortAsc);
     listInx=0;
     for(int i=0;i<data.length;i++){
       if(f_table1.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table1.getModel()).addRow(new Object[f_table1.getModel().getColumnCount()]);
       for(int j=0;j<4;j++) f_table1.getModel().setValueAt(data[i][j], listInx, j);
       listInx++;
     }
     }
     }
     }
     } else {
       currentTbl1SortIndex=-1;
     if(dirF!=null){
         f_current_local_absolutePath=dirF.getAbsolutePath();
         File fList[]=dirF.listFiles();
         if(fList!=null){
         for(inx=0;inx<fList.length;inx++){
             if(fList[inx].isDirectory()){
                if(f_table1.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table1.getModel()).addRow(new Object[f_table1.getModel().getColumnCount()]);
                f_table1.getModel().setValueAt(fList[inx].getName(), listInx, 0);
                f_table1.getModel().setValueAt(f_dirType, listInx, 1);
                if(fList[inx].lastModified()>0) f_table1.getModel().setValueAt(format1.format(new Date(fList[inx].lastModified())), listInx, 2);
                f_table1.getModel().setValueAt("", listInx, 3);
                listInx++;
             }
         }
         for(inx=0;inx<fList.length;inx++){
             if(!fList[inx].isDirectory()){
                if(f_table1.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table1.getModel()).addRow(new Object[f_table1.getModel().getColumnCount()]);
                String name=fList[inx].getName();
                int dotInx=name.lastIndexOf(".");
                f_table1.getModel().setValueAt(name, listInx, 0);
                f_table1.getModel().setValueAt((dotInx>-1? name.substring(dotInx+1):""), listInx, 1);
                f_table1.getModel().setValueAt(format1.format(new Date(fList[inx].lastModified())), listInx, 2);
                String temp=(fList[inx].length()>1024L? (fList[inx].length()/1024)+ " KB":fList[inx].length()+ " byte");
                f_table1.getModel().setValueAt(temp, listInx, 3);
                listInx++;
             }
         }
         }
     }
     }

 }

 public synchronized void f_showRemoteDir(CTFBRemoteDirNode node,int orderby,boolean asc){

     int rcount=f_table2.getModel().getRowCount();
     for(int i=rcount-1;i>-1;i--)  {if(f_table2.getModel().getRowCount()>i) ((DefaultTableModel)f_table2.getModel()).removeRow(i);}
     int inx=0,listInx=0;
     if(currentTbl2SortIndex>-1 && f_current_remote_absolutePath.equalsIgnoreCase(node.getFile().getAbsolutePath())){
      TreeMap tm=new TreeMap();
     CTFBRemoteFile file0=node.getFile();
     Enumeration en=node.children();
     if(en.hasMoreElements()){
         for(inx=0;en.hasMoreElements();inx++){
             CTFBRemoteFile file=((CTFBRemoteDirNode)en.nextElement()).file;
             if(file.isDirectory()){
               tm.put(file.getName(), ylib.tocsv(file.getName())+",<Directory>,"+format1.format(new Date(file.lastModified()))+",");
             } else { }
         }
     }
     if(file0.tmFile!=null){
             TreeMap tmF2=(TreeMap)file0.tmFile.clone();
             f_current_remote_absolutePath=file0.getAbsolutePath();
             Iterator it=tmF2.keySet().iterator();
             for(;it.hasNext();){
               CTFBRemoteFile file=(CTFBRemoteFile)tmF2.get((String)it.next());
               if(file.isFile()){
               String name=file.getName();
               int dotInx=name.lastIndexOf(".");
               String temp=(file.length()>1024L? (file.length()/1024)+ " KB":file.length()+ " byte");
               tm.put(file.getName(), ylib.tocsv(name)+","+(dotInx>-1? name.substring(dotInx+1):"")+","+format1.format(new Date(file.lastModified()))+","+temp);
             }
             }
         }
        if(tm.size()>0){
     Iterator it=tm.values().iterator();
     String data[][]=new String[tm.size()][4];
     for(;it.hasNext();){
       data[listInx]=ylib.csvlinetoarray((String)it.next());
       listInx++;
     }
     data=this.f_sortStrArr(data, currentTbl2SortIndex, currentTbl2SortAsc);
     listInx=0;
     for(int i=0;i<data.length;i++){
       if(f_table2.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table2.getModel()).addRow(new Object[f_table2.getModel().getColumnCount()]);
       for(int j=0;j<4;j++) f_table2.getModel().setValueAt(data[i][j], listInx, j);
       listInx++;
     }
     }

     } else {
       currentTbl2SortIndex=-1;
     CTFBRemoteFile file0=node.getFile();
     Enumeration en=node.children();
     if(en.hasMoreElements()){

         for(inx=0;en.hasMoreElements();inx++){
             CTFBRemoteFile file=((CTFBRemoteDirNode)en.nextElement()).file;
             if(file.isDirectory()){
                if(f_table2.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table2.getModel()).addRow(new Object[f_table2.getModel().getColumnCount()]);
                f_table2.getModel().setValueAt(file.getName(), listInx, 0);
                f_table2.getModel().setValueAt("<Directory>", listInx, 1);
                if(file.lastModified()>0) f_table2.getModel().setValueAt(format1.format(new Date(file.lastModified())), listInx, 2);
                f_table2.getModel().setValueAt("", listInx, 3);
                listInx++;
             }
         }
         

     }
         if(file0.tmFile!=null){
             TreeMap tmF2=(TreeMap)file0.tmFile.clone();
             f_current_remote_absolutePath=file0.getAbsolutePath();
             Iterator it=tmF2.keySet().iterator();
             for(;it.hasNext();){
               CTFBRemoteFile file=(CTFBRemoteFile)tmF2.get((String)it.next());
               if(file.isFile()){
                if(f_table2.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table2.getModel()).addRow(new Object[f_table2.getModel().getColumnCount()]);
                String name=file.getName();
                int dotInx=name.lastIndexOf(".");
                f_table2.getModel().setValueAt(name, listInx, 0);
                f_table2.getModel().setValueAt((dotInx>-1? name.substring(dotInx+1):""), listInx, 1);
                f_table2.getModel().setValueAt(format1.format(new Date(file.lastModified())), listInx, 2);
                f_table2.getModel().setValueAt((file.length()>1024L? (file.length()/1024)+ " KB":file.length()+ " byte"), listInx, 3);
                listInx++;
             }
             }
         }
         

    }
 }

 public void f_showRemoteDir(CTFBRemoteFile file0,String dir,int orderby,boolean asc){

     int rcount=f_table2.getModel().getRowCount();
     for(int i=rcount-1;i>-1;i--)  {if(f_table2.getModel().getRowCount()>i) ((DefaultTableModel)f_table2.getModel()).removeRow(i);}
     int inx=0,listInx=0;
      if(file0.tmFile!=null){
            boolean foundInCbbList2=false;
            for(int j=0;j<cbb_fList2.getItemCount();j++){
                    String tmp=(String)cbb_fList2.getItemAt(j);
                    if(tmp.equals(dir)) {foundInCbbList2=true; break;}
                  }

            f_skipComboBox2ItemStateChanged=true;
            if(!foundInCbbList2) {
              cbb_fList2.addItem(dir);
            }
            cbb_fList2.setSelectedItem(dir);
            f_skipComboBox2ItemStateChanged=false;
             f_current_remote_absolutePath=dir;
            if(currentTbl2SortIndex>-1 && f_current_remote_absolutePath.equalsIgnoreCase(file0.getAbsolutePath())){
      TreeMap tm=new TreeMap();
     if(file0.tmFile!=null){
             TreeMap tmF2=(TreeMap)file0.tmFile.clone();
             f_current_remote_absolutePath=file0.getAbsolutePath();
             Iterator it=tmF2.keySet().iterator();
             for(;it.hasNext();){
               CTFBRemoteFile file=(CTFBRemoteFile)tmF2.get((String)it.next());
               if(file.isDirectory()){
               tm.put(file.getName(), ylib.tocsv(file.getName())+",<Directory>,"+format1.format(new Date(file.lastModified()))+",");
             } else if(file.isFile()){
               String name=file.getName();
               int dotInx=name.lastIndexOf(".");
               String temp=(file.length()>1024L? (file.length()/1024)+ " KB":file.length()+ " byte");
               tm.put(file.getName(), ylib.tocsv(name)+","+(dotInx>-1? name.substring(dotInx+1):"")+","+format1.format(new Date(file.lastModified()))+","+temp);
             }
             }
         }
        if(tm.size()>0){
     Iterator it=tm.values().iterator();
     String data[][]=new String[tm.size()][4];
     for(;it.hasNext();){
       data[listInx]=ylib.csvlinetoarray((String)it.next());
       listInx++;
     }
     data=this.f_sortStrArr(data, currentTbl2SortIndex, currentTbl2SortAsc);
     listInx=0;
     for(int i=0;i<data.length;i++){
       if(f_table2.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table2.getModel()).addRow(new Object[f_table2.getModel().getColumnCount()]);
       for(int j=0;j<4;j++) f_table2.getModel().setValueAt(data[i][j], listInx, j);
       listInx++;
     }
     }
     } else {
              currentTbl2SortIndex=-1;
             Iterator it=file0.tmFile.keySet().iterator();
             for(;it.hasNext();){
               CTFBRemoteFile file=(CTFBRemoteFile)file0.tmFile.get((String)it.next());
               if(file.isDirectory()){
                if(f_table2.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table2.getModel()).addRow(new Object[f_table2.getModel().getColumnCount()]);
                String name=file.getName();
                if(f_table2.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table2.getModel()).addRow(new Object[f_table2.getModel().getColumnCount()]);
                f_table2.getModel().setValueAt(name, listInx, 0);
                f_table2.getModel().setValueAt("<Directory>", listInx, 1);
                if(file.lastModified()>0) f_table2.getModel().setValueAt(format1.format(new Date(file.lastModified())), listInx, 2);
                f_table2.getModel().setValueAt("", listInx, 3);
                listInx++;
             }
             }

             it=file0.tmFile.keySet().iterator();
             for(;it.hasNext();){
               CTFBRemoteFile file=(CTFBRemoteFile)file0.tmFile.get((String)it.next());
               if(file.isFile()){
                if(f_table2.getModel().getRowCount()<listInx+1) ((DefaultTableModel)f_table2.getModel()).addRow(new Object[f_table2.getModel().getColumnCount()]);
                String name=file.getName();
                int dotInx=name.lastIndexOf(".");
                f_table2.getModel().setValueAt(name, listInx, 0);
                f_table2.getModel().setValueAt((dotInx>-1? name.substring(dotInx+1):""), listInx, 1);
                f_table2.getModel().setValueAt(format1.format(new Date(file.lastModified())), listInx, 2);
                f_table2.getModel().setValueAt((file.length()>1024L? (file.length()/1024)+ " KB":file.length()+ " byte"), listInx, 3);
                listInx++;
             }
             }
            }
         }

         

 }

  public void f_deleteLocal(TreeMap deleteTM){
        Iterator it=deleteTM.keySet().iterator();
        for(;it.hasNext();){
          String key=(String)it.next();
          String from=(String)deleteTM.get(key);
           f_currentRandom=""+Math.random();

           String cmd="performcommand ct.CTModerator f_delete "+w.e642(new File(key).getParentFile().getAbsolutePath())+" "+f_currentRandom+" "+w.e642(new File((String)deleteTM.firstKey()).getName())+" "+from+" 0 0 0 0 0  0 0 0 0 0 ";
           w.sendToOne(cmd, f_rdb.infinityNodeId);
        }
      }
      public void f_deleteRemote(TreeMap deleteTM){
        Iterator it=deleteTM.keySet().iterator();
        for(;it.hasNext();){

          String absolu=(String)it.next();
          if(absolu.endsWith(f_remoteFileSeparator)) absolu=absolu.substring(0,absolu.length()-1);
        int inx=absolu.lastIndexOf(f_remoteFileSeparator);
        String parent=(inx>-1? absolu.substring(0, inx):"");
        String name=absolu.substring(inx+1);
          String from=(String)deleteTM.get(absolu);
           f_currentRandom=""+Math.random();

           String cmd="performcommand ct.CTModerator f_delete "+w.e642(parent)+" "+f_currentRandom+" "+w.e642(name)+" "+from+" 0 0 0 0 0  0 0 0 0 0 ";
           w.sendToOne(cmd, f_rdb.infinityNodeId);
        }
      }

public void f_download(TreeMap downloadTM){
  Iterator it=downloadTM.keySet().iterator();
  for(;it.hasNext();){
    String fileabspath=(String)it.next();

        if(fileabspath.endsWith(f_remoteFileSeparator)) fileabspath=fileabspath.substring(0,fileabspath.length()-1);
        int inx=fileabspath.lastIndexOf(f_remoteFileSeparator);
        String dir=(inx>-1? fileabspath.substring(0, inx):"");
        String name=fileabspath.substring(inx+1);

  String from=(String)downloadTM.get(fileabspath);
  if(dir==null) dir=name;
         f_currentRandom=""+Math.random();
           String cmd="performaction ct.CTModerator 1 f_getfile 0 0 0 0 0 "+w.e642(fileabspath)+" * "+w.e642(f_current_local_absolutePath)+" * 0 0 0 0 includesubdir "+f_myDownloadMode+" ? 0 "+f_currentRandom+" "+from+" 0 0 0 0  0 0 0 0 0 ";
           w.sendToOne(cmd, f_rdb.infinityNodeId);
  }
}
public boolean f_isRoot(String absolutepath){

      boolean rtn=false;
      if(absolutepath.equals("/")) rtn=true;
      else if(absolutepath.endsWith(":\\")) rtn=true;
      return rtn;
}
public void f_upload(TreeMap uploadTM){
  Iterator it=uploadTM.keySet().iterator();
  f_waitCnt=uploadTM.size();
  f_updateTabTitle(0);
  for(;it.hasNext();){
    String key=(String)it.next();
         f_currentRandom=""+Math.random();
         String from=(String)uploadTM.get(key);
         f_uThread.f_addUploadAction(f_rdb.infinityNodeId+",sendfile,"+key+","+from);

  }
}
 @Override
 public void doLayout() {
   super.doLayout(); 
   int width=this.getContentPane().getWidth();
   int height=this.getContentPane().getHeight();
   if(f_h2==0 || (lastFrameW !=width || lastFrameH!=height) || relayout){

     lastFrameW=width;
     lastFrameH=height;
      width=filePanel.getWidth();
      height=filePanel.getHeight();
       f_h1=f_panel6.getHeight();
       f_h2=(int)((double)height * 0.35);
       f_h5=f_h2;
       f_h3=(int)((double)height * 0.35);
       f_h6=f_h3;
       f_h4=height-f_h1-f_h2-f_h3-3-3;
       f_w1=(int)((double)(width-3)*0.5);
       f_w2=(int)((double)(width-3)*0.5);
       f_w2=width-3-f_w1;
   

   f_panel1.setBounds(0,0,f_w1, f_h1+f_h2);
   f_panel2.setBounds(f_w1+3,0,f_w2, f_h1+f_h5);
   f_panel3.setBounds(0,f_h1+f_h2+3,f_w1, f_h3);
   f_panel4.setBounds(f_w1+3,f_h1+f_h5+3,f_w2, f_h6);
   f_panel5.setBounds(0,f_h1+f_h2+f_h3+3+3,width,f_h4);
   f_panel10.setBounds(f_w1+3,f_h1+f_h5,f_w2, 3);
   f_panel11.setBounds(0,f_h1+f_h2+3+f_h3,width, 3);
   f_panel12.setBounds(f_w1,0,3, f_h1+f_h2+f_h3);
   f_panel13.setBounds(0,f_h1+f_h2,f_w1, 3);

   f_panel1.validate();
   f_panel2.validate();
   f_panel3.validate();
   f_panel4.validate();
   f_panel5.validate();
   f_panel10.validate();
   f_panel11.validate();
   f_panel12.validate();
   f_panel13.validate();
   }
   relayout=false;

 }

 public void msg_setBlink(boolean onoff){
  if(msg_needChk){msg_textPane.setCaretPosition(msg_styleDoc.getLength()); msg_needChk=false;}
}
  public boolean perform(int mode0,int modeNow,String originalId,String scheduleItemId,Weber w,Net gs,String stringx[],infinity.common.server.Connection c){

    this.w=w;
    this.gs=gs;
    if(t==null){

    if(stringx!=null && stringx.length>0 && stringx[0].equalsIgnoreCase("tutor")) tutorMode=true; else tutorMode=false;

    p_about=new CTAbout(this); 
    String fn="file:apps"+File.separator+"cr-tutor"+File.separator+"doc"+File.separator+bundle2.getString("CTModerator.xy.msg9");
    fn=w.fileSeparator(fn);
    p_about.setPage1(fn);
    fn="file:apps"+File.separator+"cr-tutor"+File.separator+"doc"+File.separator+bundle2.getString("CTModerator.xy.msg8");
    fn=w.fileSeparator(fn);
    p_about.setPage2(fn);
    updateTitle();
    if(tutorMode){

      w.setAHVar("moderatorId",w.getGNS(1));

    } else {
      jLabel36.setVisible(false);
      cbb_moderatorStatusList.setVisible(false);
      jButton1.setVisible(false);
      jButton41.setVisible(false);
      jMenuItem4.setVisible(false);
      jMenuItem10.setVisible(false);
      p_statusFile=System.getProperty("user.home")+File.separator+"cr-member-"+w.e16(w.getGNS(27))+".txt";
    }
    readProperties();
    if(w.getHVar("a_test")==null || !w.getHVar("a_test").equalsIgnoreCase("true")){

      jMenuItem6.setVisible(false);
      jMenuItem9.setVisible(false);
      jMenu2.setVisible(false);

      jPanel17.setBackground(new java.awt.Color(240,240,240));
    }
    nameIdMap.put(myNodeName,w.getGNS(1));
    if(tutorMode) {
      p_chkInit();
      p_chkRK(2);
    }

    t=new Thread(this);
    t.start();
    if(p_chkShowAbout()) {
      p_about.setVisible(true);
      if(p_statuses[6].length()<1) p_statuses[6]=format4.format(new Date());
      p_statuses[7]=format4.format(new Date());
      p_statuses[42]="";
      p_statuses[43]="";
      if(p_statuses[8].length()<1 || !w.isNumeric(p_statuses[8])) p_statuses[8]="1";
      else p_statuses[8]=String.valueOf(Long.parseLong(p_statuses[8])+1L);
    }
    p_toActivate();

      sld_readSlideList();
      if(moderator_props.getProperty("sld_file_id")!=null && moderator_props.getProperty("sld_file_id").length()>0 && 
              !moderator_props.getProperty("sld_file_id").toLowerCase().startsWith("http://") && !moderator_props.getProperty("sld_file_id").toLowerCase().startsWith("https://")) moderator_props.setProperty("sld_file_id", w.fileSeparator(moderator_props.getProperty("sld_file_id")));
    if(moderator_props.getProperty("sld_dir")!=null && moderator_props.getProperty("sld_dir").length()>0) moderator_props.setProperty("sld_dir", w.fileSeparator(moderator_props.getProperty("sld_dir")));
    if(moderator_props.getProperty("sld_file_id")==null) moderator_props.setProperty("sld_file_id","");
    if(moderator_props.getProperty("sld_dir")==null) moderator_props.setProperty("sld_dir","");
    if(moderator_props.getProperty("sld_zipfile_pw")==null) moderator_props.setProperty("sld_zipfile_pw","");
    if(moderator_props.getProperty("sld_pwencode")==null) moderator_props.setProperty("sld_pwencode","N");
    if(moderator_props.getProperty("sld_inxdex")==null) moderator_props.setProperty("sld_index","0");
    if(moderator_props.getProperty("sld_lastshowtime")==null) moderator_props.setProperty("sld_lastshowtime",format2.format(new Date()));
    if(moderator_props.getProperty("sld_imagefile_filter")==null) moderator_props.setProperty("sld_imagefile_filter","*.*");

    if(moderator_props.getProperty("sld_file_id").length()<1) {
      if(moderator_props.getProperty("sld_dir").length()>0) moderator_props.setProperty("sld_file_id", moderator_props.getProperty("sld_dir"));
    }
    if(moderator_props.getProperty("sld_file_id").length()>0) {
      String[] sldArr;
      if(slides.containsKey(moderator_props.getProperty("sld_file_id"))){
        String sldString=(String)slides.get(moderator_props.getProperty("sld_file_id"));
        sldArr=ylib.csvlinetoarray(sldString);
        moderator_props.setProperty("sld_dir",sldArr[1]);
        moderator_props.setProperty("sld_imagefile_filter",sldArr[2]);
        moderator_props.setProperty("sld_zipfile_pw",sldArr[3]);
        moderator_props.setProperty("sld_pwencode",sldArr[4]);
        moderator_props.setProperty("sld_lastshowtime",sldArr[5]);
        moderator_props.setProperty("sld_index",sldArr[6]);
      } else {
        sldArr=new String[7];
      sldArr[0]=moderator_props.getProperty("sld_file_id");
      sldArr[1]=moderator_props.getProperty("sld_dir");
      sldArr[2]=moderator_props.getProperty("sld_imagefile_filter");
      sldArr[3]=moderator_props.getProperty("sld_zipfile_pw");
      sldArr[4]=moderator_props.getProperty("sld_pwencode");
      sldArr[5]=moderator_props.getProperty("sld_lastshowtime");
      sldArr[6]=moderator_props.getProperty("sld_index");
      slides.put(sldArr[0], ylib.arrayToCsvLine(sldArr));
    }
    }
    sld_updateSlideList(true);
    int inx=0;
    if(moderator_props.getProperty("sld_index")!=null && w.isNumeric(moderator_props.getProperty("sld_index"))) inx=Integer.parseInt(moderator_props.getProperty("sld_index"));
    if(moderator_props.getProperty("sld_file_id")!=null && moderator_props.getProperty("sld_file_id").length()>0) sld_readImages(inx,w.getGNS(1));

    elseMemberItems.put("0","0,0,,,"+allNodesName+",,,,,,,,,,,,,,,,");
    elseMemberItems.put(w.getGNS(1),w.getGNS(1)+",0,,,"+myNodeName+",,,,,,,,,,,,,,,,");
    jComboBox2.setSelectedItem(moderator_props.getProperty("msg_bg_color")!=null && w.isNumeric(moderator_props.getProperty("msg_bg_color")) ? new Color(Integer.parseInt(moderator_props.getProperty("msg_bg_color"))):Color.white);
    jComboBox7.setSelectedItem(moderator_props.getProperty("msg_font_color")!=null && w.isNumeric(moderator_props.getProperty("msg_font_color")) ? new Color(Integer.parseInt(moderator_props.getProperty("msg_font_color"))):Color.black);
    msg_textPane.setBackground((Color)jComboBox2.getSelectedItem());

      sPanel=new CTScreenPanel2(this,w);

        srn_scrollPane1.setViewportView(sPanel);
        srn_scrollPane1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        srn_scrollPane1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        w.startUpd("http://cloud-rain.com/web/crtutor_version.txt", apId, version,10);

    jComboBox3.setSelectedItem(moderator_props.getProperty("sk_bg_color")!=null && w.isNumeric(moderator_props.getProperty("sk_bg_color")) ? new Color(Integer.parseInt(moderator_props.getProperty("sk_bg_color"))):Color.white);
    jComboBox1.setSelectedItem(moderator_props.getProperty("sk_line_color")!=null && w.isNumeric(moderator_props.getProperty("sk_line_color")) ? new Color(Integer.parseInt(moderator_props.getProperty("sk_line_color"))):Color.black);
    jComboBox4.setSelectedItem(moderator_props.getProperty("sk_linewidth")!=null && w.isNumeric(moderator_props.getProperty("sk_linewidth")) ? ""+Integer.parseInt(moderator_props.getProperty("sk_linewidth")):"1");

    if(tutorMode){
      setVisible(true);
    } else {
      jTabbedPane1.remove(moderatorPanel);
    if(!w.checkOneVar(auOne_asAMember,21)) jTabbedPane1.remove(slidePanel);
    if(!w.checkOneVar(auOne_asAMember,22)) jTabbedPane1.remove(msgPanel);
    if(!w.checkOneVar(auOne_asAMember,23)) jTabbedPane1.remove(screenPanel);
    if(!w.checkOneVar(auOne_asAMember,24)) jTabbedPane1.remove(filePanel);
    if(!w.checkOneVar(auOne_asAMember,25)) jTabbedPane1.remove(sketchPanel);
    if(!w.checkOneVar(auOne_asAMember,20))  this.setVisible(false);
    else setVisible(true);

    if(w.checkOneVar(auOne_asAMember, 2) || w.checkOneVar(auOne_asAMember, 14)){

           sld_upperPanel1.setVisible(true);
           if(w.checkOneVar(auOne_asAMember, 2)){
             jLabel9.setVisible(true);
             cbb_sldMList.setVisible(true);
           } else{
             jLabel9.setVisible(false);
             cbb_sldMList.setVisible(false);
           }
           } else sld_upperPanel1.setVisible(false);
     if(w.checkOneVar(auOne_asAMember, 4) || w.checkOneVar(auOne_asAMember, 17)){

           if(w.checkOneVar(auOne_asAMember, 4)) msg_lowerPanel.setVisible(true);
           else msg_lowerPanel.setVisible(false);
         } else msg_lowerPanel.setVisible(false);
      if(w.checkOneVar(auOne_asAMember, 6) || w.checkOneVar(auOne_asAMember, 7)){

           srn_upperPanel.setVisible(true);
          } else srn_upperPanel.setVisible(false);
       if(w.checkOneVar(auOne_asAMember, 3) || w.checkOneVar(auOne_asAMember, 16)){

           sk_upperPanel.setVisible(true);
           if(w.checkOneVar(auOne_asAMember, 3)){
             jLabel8.setVisible(true);
             cbb_skMList.setVisible(true);
           } else {
             jLabel8.setVisible(false);
             cbb_skMList.setVisible(false);
           }
         } else sk_upperPanel.setVisible(false);
    }
    if(!w.checkOneVar(auOne_asAMember,29)){
        rb_srnMonitor.setVisible(false);
        rb_srnControlAndImage.setVisible(false);
        jLabel29.setVisible(false);
        jPasswordField1.setVisible(false);
    }

    String sel=moderator_props.getProperty("selected");
    if(sel.equalsIgnoreCase("slidepanel") && containPanel(slidePanel)) jTabbedPane1.setSelectedComponent(slidePanel);
    else if(sel.equalsIgnoreCase("msgpanel") && containPanel(msgPanel)) jTabbedPane1.setSelectedComponent(msgPanel);
    else if(sel.equalsIgnoreCase("screenpanel") && containPanel(screenPanel)) jTabbedPane1.setSelectedComponent(screenPanel);
    else if(sel.equalsIgnoreCase("filepanel") && containPanel(filePanel)) jTabbedPane1.setSelectedComponent(filePanel);
    else if(sel.equalsIgnoreCase("sketchpanel") && containPanel(sketchPanel)) jTabbedPane1.setSelectedComponent(sketchPanel);
    else if(sel.equalsIgnoreCase("statuspanel")) jTabbedPane1.setSelectedComponent(statusPanel);
    else if(tutorMode && sel.equalsIgnoreCase("moderatorpanel") ) jTabbedPane1.setSelectedComponent(moderatorPanel);    
    msg_checkBox1.setSelected(w.checkOneVar(auOne_asAMember, 26));
    f_checkBox1.setSelected(w.checkOneVar(auOne_asAMember, 27));
    status_checkBox1.setSelected(w.checkOneVar(auOne_asAMember, 28));
    jCheckBox14.setSelected(w.checkOneVar(auOne_asAMember, 30));
    jCheckBox13.setSelected(w.checkOneVar(auOne_asAMember, 31));
    }

   if(stringx[0].equalsIgnoreCase("f_savefile")) {

     f_saveActions.add(new CTAction(originalId,mode0,1,stringx,null,null));

     if(isSleep) t.interrupt();

        return true;
   }
   

      if(stringx[0].equalsIgnoreCase("f_checksavefile")) {
          File oldFile=new File(stringx[2]+stringx[3]+stringx[4]);
          long newTime=Long.parseLong(stringx[5]);
          long newSize=Integer.parseInt(stringx[6]);
          String writemode=stringx[7];
          String random=stringx[8];
          String scode=stringx[9];
          String result2="skip";
          if(oldFile.exists()){
            long oldTime=oldFile.lastModified();
            long oldSize=oldFile.length();
          if(f_downloadDialog==null){
            f_downloadDialog=new CTFBDownloadFileDialog(this,true);
          }
          f_downloadDialog.setInfo(oldFile.getAbsolutePath(),""+oldSize,""+oldTime,stringx[1],""+newSize,""+newTime,originalId,random);
          f_downloadDialog.setVisible(true);
          }
          return true;
   }

      if(stringx[0].equalsIgnoreCase("f_checksavefilefeedback")) {
        String writemode=stringx[1];
        String secondPara=stringx[2];
        String myDownloadMode=stringx[3];
        int random=Integer.parseInt(stringx[4]);

           f_setDownloadChkSaveResult(writemode,secondPara,myDownloadMode);

        return true;
   }
      

      if(stringx[0].equalsIgnoreCase("f_downloadcheckexist")) {
          File f=new File(stringx[2]+stringx[3]+stringx[4]);
          long lasttime=Long.parseLong(stringx[5]);
          int filesize=Integer.parseInt(stringx[6]);
          String writemode=stringx[7];
          String random=stringx[8];
          String scode=stringx[9];
          String secondPara="secondPara";
          String result2="false";
          if(f.exists()) {
            result2="true";
            if(writemode.equalsIgnoreCase("checkdate")){
              long oldTime=f.lastModified();
              if(lasttime>oldTime) secondPara="true";
              else secondPara="false";
            } else if(writemode.equalsIgnoreCase("rename")){
              secondPara=getNewName(f);
            }
          }

          String msg="performaction ct.CTModerator 1 f_downloadcheckexistfeedback "+result2+" "+secondPara+" "+writemode+" "+random;

          w.sendToOne(msg, originalId);
          return true;
   }
      if(stringx[0].equalsIgnoreCase("f_downloadcheckexistfeedback")) {
        boolean resu=Boolean.parseBoolean(stringx[1]);
        String secondPara=stringx[2];
        int random=Integer.parseInt(stringx[4]);

           f_setDownloadChkExistResult(resu,secondPara);

        return true;
   }
      

      if(stringx[0].equalsIgnoreCase("f_uploadcheckexist")) {

          File f=new File(stringx[2]+stringx[3]+stringx[4]);
          long lasttime=Long.parseLong(stringx[5]);
          int filesize=Integer.parseInt(stringx[6]);
          String writemode=stringx[7];
          String random=stringx[8];
          String scode=stringx[9];
          String secondPara="secondPara";
          String thirdPara="thirdPara";
          String fourthPara="fourthPara";
          String fifthPara="fifthPara";
          String sixthPara="sixPara";
          String result2="false";
          if(f.exists()) {
            result2="true";
            fourthPara=f.getAbsolutePath();
            fifthPara=""+f.length();
            sixthPara=""+f.lastModified();
            if(writemode.equalsIgnoreCase("ask")){
              long oldTime=f.lastModified();
              if(lasttime>oldTime) secondPara="true";
              else secondPara="false";
              thirdPara=getNewName(f);
            }else if(writemode.equalsIgnoreCase("checkdate")){
              long oldTime=f.lastModified();
              if(lasttime>oldTime) secondPara="true";
              else secondPara="false";
            } else if(writemode.equalsIgnoreCase("rename")){
              thirdPara=getNewName(f);
            }
          }
          String msg="performaction ct.CTModerator 1 f_uploadcheckexistfeedback "+result2+" "+secondPara+" "+ylib.replace(thirdPara," ","%space%")+" "+ylib.replace(fourthPara," ","%space%")+" "+fifthPara+" "+sixthPara+" "+writemode+" "+random+" 0";

          w.sendToOne(msg, originalId);
          return true;
   }
      if(stringx[0].equalsIgnoreCase("f_uploadcheckexistfeedback")) {

        boolean resu=Boolean.parseBoolean(stringx[1]);
        int random=Integer.parseInt(stringx[8]);

           f_setUploadChkExistResult(resu,stringx[2],stringx[3],stringx[4],stringx[5],stringx[6]);

        return true;
   }
   if(stringx[0].equals("f_getfile")){
       f_addDownloadAction(originalId+",getfile,"+ylib.arrayToCsvLine(stringx));
       return true;
   }

    return true;
  }
  void updateTitle(){
    if(tutorMode){
      setTitle("cr-Tutor "+version+"      Tutor: "+w.getGNS(27)+"      in Group: "+w.getGNS(11)+(w.getGNS(38).length()>0? "      connect to: "+w.getGNS(8):"")+(gs!=null? "      number of client: "+gs.connectionCount():"")+(hasNewVersion? "      ("+bundle2.getString("CTModerator.xy.msg14")+")("+newversion+")":""));
    } else {
      setTitle("cr-Tutor "+version+"      "+bundle2.getString("CTModerator.xy.msg13")+": "+w.getGNS(27)+"      in Group: "+w.getGNS(11)+(w.getGNS(38).length()>0? "      connect to: "+w.getGNS(8):"")+(gs!=null? "      number of client: "+gs.connectionCount():"")+(hasNewVersion? "      ("+bundle2.getString("CTModerator.xy.msg14")+")("+newversion+")":""));
        }
  }

  void p_chkInit(){
      if(p_statuses[44].length()<5){
          if(new File(p_rcFile).exists()) new File(p_rcFile).delete();
      } else if(p_statuses[44].length()>5 && p_statuses[12].length()>5 && !(new File(p_rcFile).exists())){
          String em=w.csvLineToArray(w.d16(p_statuses[12]))[4].substring(13);
          p_toActivateAgain2(em);
      }
  }
  public boolean command(int mode0,int modeNow,String originalId,String scheduleItemId,Weber w,Net gs,String cmd,infinity.common.server.Connection c){
     StringTokenizer st=new StringTokenizer(cmd," ");
     String command=st.nextToken();
     if(command.equals("restart")){
       restart();
                return true;
     }
     if(command.equals("shutdown")){
      w.ap.onExit(113);
      System.exit(0);
        return true;
     }
     if(command.equals("checkversion")){

       w.upd.setActionType(2, originalId);
       return true;
     }
     if(command.equals("setclipboard")){
      cbListener.setContents(w.d642(st.nextToken()));
        return true;
     }
     if(command.equals("closemoderator")){
        if(!tutorMode) {
          thisRect=this.getBounds();
          thisState=this.getExtendedState();
          setVisible(false);
        }
        return true;
     }
     if(command.equals("openmoderator")){
        this.setVisible(true);
        this.setExtendedState(thisState);
        if(thisState!=JFrame.MAXIMIZED_BOTH) this.setBounds(thisRect);
        return true;
     }

      if(command.equals("getau")){
        long rtn=auOne_asAMember;
       if(tutorMode) {
         rtn=w.addOneVar(rtn, 3);
         rtn=w.addOneVar(rtn, 16);
         rtn=w.addOneVar(rtn, 4);
         rtn=w.addOneVar(rtn, 17);
         rtn=w.addOneVar(rtn, 6);
         rtn=w.addOneVar(rtn, 7);
         rtn=w.addOneVar(rtn, 13);
         rtn=w.addOneVar(rtn, 15);
         rtn=w.addOneVar(rtn, 2);
         rtn=w.addOneVar(rtn, 14);
         rtn=w.addOneVar(rtn, 5);
        }
       String cmd2="";
       cmd2="performcommand ct.CTModerator feedbackau "+rtn+" 0";

       w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
        setRemoteTutor(originalId);
       return true;
     }
     if(command.equals("feedbackau")){
       long au=Long.parseLong(st.nextToken());
       cbSlideAu1.setSelected(w.checkOneVar(au, 14));
       cbSlideAu2.setSelected(w.checkOneVar(au, 2));
       cbSketchAu1.setSelected(w.checkOneVar(au,16));
       cbSketchAu2.setSelected(w.checkOneVar(au, 3));
       cbMsgAu1.setSelected(w.checkOneVar(au, 17));
       cbMsgAu2.setSelected(w.checkOneVar(au, 4));
       cbSrnAu1.setSelected(w.checkOneVar(au, 7));
       cbSrnAu2.setSelected(w.checkOneVar(au, 6));
       cbFileAu1.setSelected(w.checkOneVar(au,15));
       cbFileAu2.setSelected(w.checkOneVar(au,13));
       cbOpenWebAu.setSelected(w.checkOneVar(au, 5));
       return true;
     }
     if(command.equals("plusau")){
       if(!tutorMode && st.hasMoreTokens()) {
         long one=Long.parseLong(st.nextToken());

         int iv[]=OneVar.getAll(one);
         auOne_asAMember=OneVar.combine(auOne_asAMember,iv);
        setRemoteTutor(originalId);
        }
       return true;
     }
     if(command.equals("setau")){
       if(!tutorMode && st.hasMoreTokens()) {
         auOne_asAMember=Long.parseLong(st.nextToken());
         if(w.checkOneVar(auOne_asAMember, 3) || w.checkOneVar(auOne_asAMember, 16)){
           showPanel(sketchPanel);
           sk_upperPanel.setVisible(true);
           if(w.checkOneVar(auOne_asAMember, 3)){
             jLabel8.setVisible(true);
             cbb_skMList.setVisible(true);
           } else {
             jLabel8.setVisible(false);
             cbb_skMList.setVisible(false);
           }
         } else sk_upperPanel.setVisible(false);
         if(w.checkOneVar(auOne_asAMember, 4) || w.checkOneVar(auOne_asAMember, 17)){
           showPanel(msgPanel);
           if(w.checkOneVar(auOne_asAMember, 4)) msg_lowerPanel.setVisible(true);
           else msg_lowerPanel.setVisible(false);
         } else msg_lowerPanel.setVisible(false);
         if(w.checkOneVar(auOne_asAMember, 6) || w.checkOneVar(auOne_asAMember, 7)){
           showPanel(screenPanel);
           srn_upperPanel.setVisible(true);
         } else srn_upperPanel.setVisible(false);
         if(w.checkOneVar(auOne_asAMember, 13)  || w.checkOneVar(auOne_asAMember, 15)){
           showPanel(filePanel);
         } else if(containPanel(filePanel)) jTabbedPane1.remove(filePanel);
         if(w.checkOneVar(auOne_asAMember, 2) || w.checkOneVar(auOne_asAMember, 14)){
           showPanel(slidePanel);
           sld_upperPanel1.setVisible(true);
           if(w.checkOneVar(auOne_asAMember, 2)){
             jLabel9.setVisible(true);
             cbb_sldMList.setVisible(true);
           } else{
             jLabel9.setVisible(false);
             cbb_sldMList.setVisible(false);
           }
         } else sld_upperPanel1.setVisible(false);
        setRemoteTutor(originalId);
        }
       return true;
     }
     if(command.equals("plusau")){
       if(!tutorMode && st.hasMoreTokens()) {
         long one=Long.parseLong(st.nextToken());

         int iv[]=OneVar.getAll(one);
         auOne_asAMember=OneVar.combine(auOne_asAMember,iv);
        setRemoteTutor(originalId);
        }
       return true;
     }
     if(command.equals("minusau")){
       if(!tutorMode && st.hasMoreTokens()) {
         long one=Long.parseLong(st.nextToken());
         int iv[]=OneVar.getAll(one);
         for(int i=0;i<iv.length;i++) auOne_asAMember=w.removeOneVar(auOne_asAMember, iv[i]);

        setRemoteTutor(originalId);
        }
       return true;
     }
    if(command.equals("getstatus")){
       setRemoteTutor(originalId);
       String cmd2="performmessage ct.CTModerator status "+w.e642(w.getGNS(27)+" status: "+getStatus());
       w.sendToOne(cmd2,originalId);
       return true;
     }
    if(command.equals("setallpanelinvisible")){

      if(!tutorMode){

        jTabbedPane1.removeAll();
        jTabbedPane1.addTab(bundle2.getString("CTModerator.statusPanel.TabConstraints.tabTitle"), statusPanel);
      }
        setRemoteTutor(originalId);
       return true;
     }
    if(command.equals("setallpanelvisible")){

      if(!tutorMode){
       showPanel(null);
      }
       setRemoteTutor(originalId);
       return true;
     }

     if(command.equals("sld_setpanel")){
       if(!tutorMode && st.hasMoreTokens()) {
        String show=st.nextToken();
        if(st.hasMoreTokens()){
         String focus=st.nextToken();
           if(st.hasMoreTokens()){
             String au1=st.nextToken();
             if(au1.equals("1")){
               auOne_asAMember=w.addOneVar(auOne_asAMember, 14);
             } else auOne_asAMember=w.removeOneVar(auOne_asAMember, 14);
           if(st.hasMoreTokens()){
             String au2=st.nextToken();
             if(au2.equals("1")){
               auOne_asAMember=w.addOneVar(auOne_asAMember, 2);
             } else auOne_asAMember=w.removeOneVar(auOne_asAMember, 2);
            if(show.equals("1")){
             showPanel(slidePanel);
             if(focus.equals("1"))  jTabbedPane1.setSelectedComponent(slidePanel);
            } else if(!tutorMode && containPanel(slidePanel)){
                jTabbedPane1.remove(slidePanel);
              }
           if(w.checkOneVar(auOne_asAMember, 2) || w.checkOneVar(auOne_asAMember, 14)){

           sld_upperPanel1.setVisible(true);
           if(w.checkOneVar(auOne_asAMember, 2)){
             jLabel9.setVisible(true);
             cbb_sldMList.setVisible(true);
           } else{
             jLabel9.setVisible(false);
             cbb_sldMList.setVisible(false);
           }
           } else sld_upperPanel1.setVisible(false);
           }
             }
             }
           }
       return true;
     }
     

     if(command.equals("sld_clear")){

       sld_imgPanel.setBG(Color.white);
         setRemoteTutor(originalId);
          return true;
     }
     if(command.equals("sld_close")){
         sld_close();
         setRemoteTutor(originalId);
          return true;
     }
     

     if(command.equals("sld_reload")){
       boolean ch=false;
       if(st.hasMoreTokens()) {
         String arr[]=ylib.csvlinetoarray(w.d642(st.nextToken()));
         String file_id,dir="",filter="",pw="",pwencode="";
         file_id=arr[0];
         if(arr.length>1) dir=arr[1];
         if(arr.length>2) filter=arr[2];
         if(arr.length>3) pw=arr[3];
         if(arr.length>4) pwencode=arr[4];
       if(st.hasMoreTokens()) {
         int inx=Integer.parseInt(st.nextToken());
          if(file_id.toLowerCase().startsWith("http") && file_id.indexOf(":")>-1){}
          else {
            file_id=ylib.replace(file_id, "\\", File.separator);
            file_id=ylib.replace(file_id, "/", File.separator);
            dir=ylib.replace(dir, "\\", File.separator);
            dir=ylib.replace(dir, "/", File.separator);
          }
          if(!file_id.equalsIgnoreCase(moderator_props.getProperty("sld_file_id"))) {moderator_props.put("sld_file_id",file_id); ch=true;}

          if(!pw.equals(moderator_props.getProperty("sld_zipfile_pw")))  {moderator_props.put("sld_zipfile_pw",pw); ch=true;}
          if(!pwencode.equals(moderator_props.getProperty("sld_pwencode")))  {moderator_props.put("sld_pwencode",pwencode); ch=true;}
          moderator_props.put("sld_lastshowtime", format2.format(new Date()));
          if(ch) { sld_setSlideDir(file_id, filter); sld_readImages(inx,originalId);}
          else if (!sld_opened_file_id.equals(moderator_props.get("sld_file_id"))) sld_readImages(inx,originalId);
           else sld_gotoSlide(inx,false);
        }
       }
          setRemoteTutor(originalId);
          return true;
     }
     if(command.equals("sld_syn")){
       boolean ch=false;
        if(st.hasMoreTokens()) {
         String arr[]=ylib.csvlinetoarray(w.d642(st.nextToken()));
         String file_id,dir="",filter="",pw="",pwencode="";
         file_id=arr[0];
         if(arr.length>1) dir=arr[1];
         if(arr.length>2) filter=arr[2];
         if(arr.length>3) pw=arr[3];
         if(arr.length>4) pwencode=arr[4];
         if(file_id.toLowerCase().startsWith("http") && file_id.indexOf(":")>-1){}
          else {
            file_id=ylib.replace(file_id, "\\", File.separator);
            file_id=ylib.replace(file_id, "/", File.separator);
            dir=ylib.replace(dir, "\\", File.separator);
            dir=ylib.replace(dir, "/", File.separator);
          }
       if(st.hasMoreTokens()) {
         int inx=Integer.parseInt(st.nextToken());
         if(!file_id.equalsIgnoreCase(moderator_props.getProperty("sld_file_id"))) {moderator_props.put("sld_file_id",file_id); ch=true;}

          if(!pw.equals(moderator_props.getProperty("sld_zipfile_pw")))  {moderator_props.put("sld_zipfile_pw",pw); ch=true;}
          if(!pwencode.equals(moderator_props.getProperty("sld_pwencode")))  {moderator_props.put("sld_pwencode",pwencode); ch=true;}
          moderator_props.put("sld_lastshowtime", format2.format(new Date()));
          if(ch) { sld_setSlideDir(file_id, filter); sld_readImages(inx,originalId);}
          else if (!sld_opened_file_id.equals(moderator_props.get("sld_file_id"))) sld_readImages(inx,originalId);
                 else sld_gotoSlide(inx,false);
         }
       }

          return true;
     }
     if(command.equals("sld_laser")){
       if(st.hasMoreTokens()) {
         String currFN=st.nextToken();
         if(currFN.equals(sld_currentFN) && st.hasMoreTokens()) {
           int x=Integer.parseInt(st.nextToken());
           if(st.hasMoreTokens()) {
             int y=Integer.parseInt(st.nextToken());
             if(st.hasMoreTokens()) {
               int size=Integer.parseInt(st.nextToken());
               if(st.hasMoreTokens()) {
                 showPanel(slidePanel);
                 jTabbedPane1.setSelectedComponent(slidePanel);
                 int color=Integer.parseInt(st.nextToken());
                 sld_laserDataV.clear();
                 sld_laserDataV.add(new CTSketchData(1,x,y,0,size,color));
                 sld_imgPanel.setLaserDataV(sld_laserDataV);
             }
           }
          }
         }
        }

          return true;
     }
     if(command.equals("sld_laseroff")){
       if(st.hasMoreTokens()) {
         String currFN=st.nextToken();
         showPanel(slidePanel);
         jTabbedPane1.setSelectedComponent(slidePanel);
         if(currFN.equals(sld_currentFN)){
                 sld_laserDataV.clear();
                 sld_imgPanel.setLaserDataV(null);
         }
        }

          return true;
     }
     if(command.equals("sld_writing")){
       if(st.hasMoreTokens()) {
         String currFN=st.nextToken();
         if(currFN.equals(sld_currentFN) && st.hasMoreTokens()) {
           int type=Integer.parseInt(st.nextToken());
           if(st.hasMoreTokens()) {
             int x=Integer.parseInt(st.nextToken());
             if(st.hasMoreTokens()) {
               int y=Integer.parseInt(st.nextToken());
               if(st.hasMoreTokens()) {
                 int size=Integer.parseInt(st.nextToken());
                 if(st.hasMoreTokens()) {
                   showPanel(slidePanel);
                   jTabbedPane1.setSelectedComponent(slidePanel);
                   int color=Integer.parseInt(st.nextToken());
                   sld_dataV=(Vector)sld_dataVs.get(sld_currentFN);
                   sld_dataV.add(new CTSketchData(type,x,y,0,size,color));
                   sld_dataVs.put(sld_currentFN, sld_dataV);
                   sld_imgPanel.setDataV(sld_dataV);
                 }
             }
           }
          }
         }
        }

          return true;
     }
     if(command.equals("sld_starttobeaslidepresenter")){
       if(!tutorMode){
         sld_oldIsVisible=isVisible();
         sld_oldAuOne=auOne_asAMember;

         auOne_asAMember=w.addOneVar(auOne_asAMember,2);

         if(!this.isVisible()) this.setVisible(true);
         this.toFront();
         showPanel(slidePanel);
         jTabbedPane1.setSelectedComponent(slidePanel);
         sld_upperPanel1.setVisible(true);
         setRemoteTutor(originalId);
        }
       return true;
     }
     if(command.equals("sld_stopbeingaslidepresenter")){
       if(!tutorMode) {

           auOne_asAMember=w.removeOneVar(auOne_asAMember, 2);
           sld_upperPanel1.setVisible(false);

         setRemoteTutor(originalId);
        }
       return true;
     }

     if(command.equals("msg_setpanel")){
       if(!tutorMode && st.hasMoreTokens()) {
         String show=st.nextToken();
           if(st.hasMoreTokens()){
             String focus=st.nextToken();
           if(st.hasMoreTokens()){
               String au1=st.nextToken();
           if(st.hasMoreTokens()){
               String au2=st.nextToken();
               if(au1.equals("1")) auOne_asAMember=w.addOneVar(auOne_asAMember,17);
               else auOne_asAMember=w.removeOneVar(auOne_asAMember,17);
               if(au2.equals("1")) auOne_asAMember=w.addOneVar(auOne_asAMember,4);
               else auOne_asAMember=w.removeOneVar(auOne_asAMember,4);
             if(show.equals("1")){
             showPanel(msgPanel);
             if(focus.equals("1")) jTabbedPane1.setSelectedComponent(msgPanel);
         } else if(!tutorMode && containPanel(msgPanel)){
             jTabbedPane1.remove(msgPanel);
         }
         if(w.checkOneVar(auOne_asAMember, 4) || w.checkOneVar(auOne_asAMember, 17)){

           if(w.checkOneVar(auOne_asAMember, 4)) msg_lowerPanel.setVisible(true);
           else msg_lowerPanel.setVisible(false);
         } else msg_lowerPanel.setVisible(false);
             }
           }
         }
         }
       return true;
     }
     

       if(command.equals("msg_clear")){

         showPanel(msgPanel);
         jTabbedPane1.setSelectedComponent(msgPanel);
        msg_clear();
        setRemoteTutor(originalId);
       return true;
     }
     if(command.equals("msg_setMsgOption")){
     if(st.hasMoreTokens()) {
       st.nextToken();
       if(st.hasMoreTokens()) {
         st.nextToken();
         if(st.hasMoreTokens()) {
           msg_showMember=Boolean.parseBoolean(st.nextToken());
           if(st.hasMoreTokens()) {
             msg_showTime=Boolean.parseBoolean(st.nextToken());
           }
         }
        setRemoteTutor(originalId);
       }
     }
       return true;
     }
     if(command.equals("msg_openurl")){
     if(st.hasMoreTokens()) {
       String webAddr=w.d642(st.nextToken());
       String nname="";
       if(st.hasMoreTokens()) nname=st.nextToken();
       msg_textPaneAppend((msg_showTime? format3.format(new Date())+" ":"")+(msg_showMember? nname+": ":"") +webAddr+"\r\n");

          openURL.open(webAddr);
        setRemoteTutor(originalId);
     	}
       return true;
     }
     if(command.equals("msg_geturlfile")){
     if(st.hasMoreTokens()) {
       String webAddr=w.d642(st.nextToken());
       if(st.hasMoreTokens()){
         String file=w.d642(st.nextToken());
         file=w.fileSeparator(file);
         String nname="";
         if(st.hasMoreTokens()) nname=st.nextToken();

         String cmd2="";
          if(w.ap.urlfiletodisk(webAddr,file)){
              cmd2="performmessage ct.CTModerator statusmsg "+w.e642(w.getGNS(27)+" get "+webAddr+" to "+file+" successfully.");
          } else{
            cmd2="performmessage ct.CTModerator statusmsg "+w.e642(w.getGNS(27)+" get "+webAddr+" to "+file+" failed.");
          }
          w.sendToOne(cmd2,originalId);
          if(!originalId.equals(w.getGNS(1))) w.sendToOne(cmd2,w.getGNS(1));
          setRemoteTutor(originalId);
     	 }
       }
       return true;
     }

     if(command.equals("srn_setpanel")){
       if(!tutorMode && st.hasMoreTokens()) {
         String show=st.nextToken();
           if(st.hasMoreTokens()){
             String focus=st.nextToken();
           if(st.hasMoreTokens()){
             String au1=st.nextToken();
           if(st.hasMoreTokens()){
             String au2=st.nextToken();
              if(au1.equals("1")){
               auOne_asAMember=w.addOneVar(auOne_asAMember, 7);
             } else auOne_asAMember=w.removeOneVar(auOne_asAMember, 7);
              if(au2.equals("1")){
               auOne_asAMember=w.addOneVar(auOne_asAMember, 6);
             } else auOne_asAMember=w.removeOneVar(auOne_asAMember, 6);
             if(show.equals("1")){
               showPanel(screenPanel);
             if(focus.equals("1")) jTabbedPane1.setSelectedComponent(screenPanel);
            } else if(!tutorMode && containPanel(screenPanel)) jTabbedPane1.remove(screenPanel);
          if(w.checkOneVar(auOne_asAMember, 6) || w.checkOneVar(auOne_asAMember, 7)){

           srn_upperPanel.setVisible(true);
          } else srn_upperPanel.setVisible(false);
       }
       }
       }
       }
       return true;
     }
     if(command.equals("srn_clear")){
         sPanel.clear();
        setRemoteTutor(originalId);
       return true;
     }
     if(command.equals("srn_setboard")){
     if(!tutorMode && st.hasMoreTokens()) {
     	String showcontrolpanel=st.nextToken();
     	if(st.hasMoreTokens()){
          String fixtosn=st.nextToken();
          if(showcontrolpanel.equals("1")) {if(!srn_upperPanel.isVisible()) srn_upperPanel.setVisible(true);}
          else {if(srn_upperPanel.isVisible()) srn_upperPanel.setVisible(false);}
          if(fixtosn.equals("1")) {
            srn_fixToSrn=true;
            cb_srnFix.setSelected(true);
            jLabel2.requestFocusInWindow();
          }
          else {
            srn_fixToSrn=false;
            cb_srnFix.setSelected(false);
            jLabel1.requestFocusInWindow();
          }
          setRemoteTutor(originalId);
     	}
      }
      return true;
     }
     if(command.equals("srn_startviewtutorscreen")){
       if( srn_viewTutorMode==0) {
         oldAuOne=auOne_asAMember;
         srn_oldIsVisible=isVisible();
         srn_oldHasSrnPanel=containPanel(screenPanel);
         srn_oldSelectedItem=(String)cbb_srnMList.getSelectedItem();
         srn_oldPanelIsVisible=srn_upperPanel.isVisible();
         srn_oldFixToSrn=srn_fixToSrn;
         oldControlAu=0;
         showPanel(screenPanel);
         jTabbedPane1.setSelectedComponent(screenPanel);

         if(rb_srnMonitor.isSelected()) oldControlAu=2;
         else  oldControlAu=3;

         auOne_asAMember=w.addOneVar(auOne_asAMember,6);
         auOne_asAMember=w.addOneVar(auOne_asAMember,9);
         srn_viewTutorMode=1;
         rb_srnMonitor.setSelected(true);
         srn_upperPanel.setVisible(false);

         srn_fixToSrn=false;
         cb_srnFix.setSelected(srn_fixToSrn);

         setRemoteTutor(originalId);
         cbb_srnMList.setSelectedItem(lattestRemoteTutorName);
         btn_srnStart.setText(srn_startStr);
         srn_pressButton();
        }
       return true;
     }
     if(command.equals("srn_stopviewtutorscreen")){
       if(((String)cbb_srnMList.getSelectedItem()).equals(lattestRemoteTutorName)) {
         btn_srnStart.setText(srn_stopStr);
         srn_pressButton();
         if(srn_viewTutorMode==1){
           auOne_asAMember=oldAuOne;
           cbb_srnMList.setSelectedItem(srn_oldSelectedItem);
           srn_upperPanel.setVisible(srn_oldPanelIsVisible);
           srn_fixToSrn=srn_oldFixToSrn;
           cb_srnFix.setSelected(srn_fixToSrn);

           if(oldControlAu==2) rb_srnMonitor.setSelected(true);
           else rb_srnControlAndImage.setSelected(true);
           if(!srn_oldHasSrnPanel) jTabbedPane1.remove(screenPanel);
           this.setVisible(srn_oldIsVisible);
           srn_viewTutorMode=0;
         }
         setRemoteTutor(originalId);
        }
       return true;
     }

     if(command.equals("f_setpanel")){
       if(!tutorMode && st.hasMoreTokens()) {
         String show=st.nextToken();
         if(st.hasMoreTokens()){
             String focus=st.nextToken();
         if(st.hasMoreTokens()){
           String au1=st.nextToken();
         if(st.hasMoreTokens()){
           String au2=st.nextToken();
           if(au1.equals("1")) auOne_asAMember=w.addOneVar(auOne_asAMember,15);
           else auOne_asAMember=w.removeOneVar(auOne_asAMember,15);
           if(au2.equals("1")) auOne_asAMember=w.addOneVar(auOne_asAMember,13);
           else auOne_asAMember=w.removeOneVar(auOne_asAMember,13);
         if(show.equals("1")){
           showPanel(filePanel);
             if(focus.equals("1")) jTabbedPane1.setSelectedComponent(filePanel);
         } else if(!tutorMode && containPanel(filePanel)) jTabbedPane1.remove(filePanel);
         if(w.checkOneVar(auOne_asAMember, 13)  || w.checkOneVar(auOne_asAMember, 15)){

         } else if(containPanel(filePanel)) jTabbedPane1.remove(filePanel);
             }
             }
         }
       }
       return true;
     }
      if(command.equals("f_getroot")){
      if(st.hasMoreTokens()) {
       	String random=st.nextToken();
	File roots[]  = File.listRoots();
        Vector rs=new Vector();
	for (int i = 0 ; i < roots.length ; i++) {
	    File   root = roots[i];
	    String path = root.getAbsolutePath();
            rs.add(path);
	}
       String cmd2="";
       cmd2="performcommand ct.CTModerator f_root "+random+" "+File.separator;
       Enumeration en=rs.elements();
       for(;en.hasMoreElements();){
           cmd2=cmd2+" "+(String)en.nextElement();
       }

       w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
       return true;
      }
       return false;
     }
     if(command.equals("f_root")){
       if(st.hasMoreTokens()) {
       	String random=st.nextToken();
       if(f_currentRandom.equals(random) && st.hasMoreTokens()) {
         f_remoteFileSeparator=st.nextToken();

         if(f_rdb.root.file.tmFile==null) f_rdb.root.file.tmFile=new TreeMap();
         DefaultTreeModel model = (DefaultTreeModel)f_rdb.getModel();

           DefaultMutableTreeNode root=(DefaultMutableTreeNode)model.getRoot();
           root.removeAllChildren();
         while(st.hasMoreTokens()){

             String absolutePath=st.nextToken();

             CTFBRemoteDirNode newNode= new CTFBRemoteDirNode(f_rdb,absolutePath,this);
             f_rdb.root.file.tmFile.put(absolutePath, newNode.getFile());

             model.insertNodeInto(newNode, root, model.getChildCount(root));

         }

         model.reload(root);
         return true;
       }
       }
       return false;
     }
     if(command.equals("f_getdir")){
       if(st.hasMoreTokens()) {
       	String dir=st.nextToken();
        dir=w.d642(dir);
       if(st.hasMoreTokens()) {
       	String random=st.nextToken();
	File files[]  = new File(dir).listFiles();
        Vector rs=new Vector();
	for (int i = 0 ; i < files.length ; i++) {
	    File  f2 = files[i];
            if(f2.isDirectory()){
	    String path = f2.getName();
            rs.add(path);
            }
	}
       String cmd2="";
       cmd2="performcommand ct.CTModerator f_dir "+w.e642(dir)+" "+random;
       Enumeration en=rs.elements();
       for(;en.hasMoreElements();){
           cmd2=cmd2+" "+w.e642((String)en.nextElement());
       }

       w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
       }
       }
       return true;
     }
     if(command.equals("f_aftersaveupload_ok")){
       if(st.hasMoreTokens()) {
       	String data=st.nextToken();
        data=w.d642(data);

        f_appendFileOKLog(format5.format(new Date())+" "+data+"\r\n");
        f_okCnt++;
        f_appendFileWaitLog("");
        if(f_waitCnt>0) f_waitCnt--;
        f_updateAllTabTitle();
        String absPath=this.f_current_remote_absolutePath;
        if(!f_lastGetRemoteDir.equals(absPath) || lastGetDir_time+1000L <System.currentTimeMillis()){
              String cmd2="";
              f_currentRandom=""+Math.random();

              cmd2="performcommand ct.CTModerator f_getdirandfile "+w.e642(absPath)+" "+f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
              w.sendToOne(cmd2, f_rdb.infinityNodeId);
              f_lastGetRemoteDir=absPath;
              f_lastGetRemoteNode=null;
              lastGetDir_time=System.currentTimeMillis();
            }
       }
       return true;
     }
     if(command.equals("f_aftersaveupload_failed")){
       if(st.hasMoreTokens()) {
       	String data=st.nextToken();
        data=w.d642(data);
        jTextArea3.append(format5.format(new Date())+" "+data+"\r\n");
        f_appendFileWaitLog("");
        f_failedCnt++;
        if(f_waitCnt>0) f_waitCnt--;
        f_updateAllTabTitle();
       }
       return true;
     }
     if(command.equals("f_dir")){

       return true;
     }
     if(command.equals("f_getdirandfile")){
       if(st.hasMoreTokens()) {
       	String dir=st.nextToken();
        dir=w.d642(dir);
       if(st.hasMoreTokens()) {
       	String random=st.nextToken();
        File f=new File(dir);
        if(!f.exists()) {
          String cmd2="performmessage ct.CTModerator msg "+w.e642("Remote directory or file \""+dir+"\" not exists.");
          w.sendToOne(cmd2, originalId);
            return false;
        }
        if(!f.isDirectory()) f=f.getParentFile();
        dir=f.getAbsolutePath();
	File files[]  = new File(dir).listFiles();
        Vector rs=new Vector();
        if(files!=null){
	for (int i = 0 ; i < files.length ; i++) {
	    File  f2 = files[i];
            if(f2.isDirectory()){
            rs.add(f2.getName()+sep+f2.lastModified());
            } else {
                rs.add(f2.getName()+sep+f2.lastModified()+sep+f2.length());
            }
	}
        }
       String cmd2="";
       cmd2="performcommand ct.CTModerator f_dirandfile "+w.e642(dir)+" "+random;
       Enumeration en=rs.elements();
       for(;en.hasMoreElements();){
           cmd2=cmd2+" "+w.e642((String)en.nextElement());
       }

       w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
       }
       }
       return true;
     }
     if(command.equals("f_renamefile")){
       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String name=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String newname=w.d642(st.nextToken());
             File f=new File(dir+(dir.endsWith(File.separator)? "":File.separator)+name);

             if(f.renameTo(new File(dir+(dir.endsWith(File.separator)? "":File.separator)+newname))){
               String  cmd2="performcommand ct.CTModerator f_afterrenamefile "+w.e642(dir)+" "+random+" "+w.e642(name)+" "+w.e642(newname)+" 0 0 0 0  0 0 0 0 0 ";

               w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
             } else {
                String cmd2="performmessage ct.CTModerator msg "+w.e642("Failed to rename file from \""+dir+(dir.endsWith(File.separator)? "":File.separator)+name+"\" to \""+dir+(dir.endsWith(File.separator)? "":File.separator)+newname+"\".");
                w.sendToOne(cmd2, originalId);
                return false;
             }
           }
         }
         }
       }
         return true;
     }
     if(command.equals("f_afterrenamefile")){

       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String name=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String newname=w.d642(st.nextToken());
             String absPath=(String)cbb_fList2.getSelectedItem();

        if(absPath!=null && absPath.length()>0){
         f_current_remote_absolutePath=absPath;
         if(!f_lastGetRemoteDir.equals(absPath) || lastGetDir_time+1000L <System.currentTimeMillis()){
         f_currentRandom=""+Math.random();
         f_lastGetRemoteNode=null;

         String cmd2="performcommand ct.CTModerator f_getdirandfile "+w.e642(absPath)+" "+f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
         f_lastGetRemoteDir=absPath;
         lastGetDir_time=System.currentTimeMillis();
         w.sendToOne(cmd2, f_rdb.infinityNodeId);
         }
      }
           }
         }
         }
       }
         return true;
     }
     if(command.equals("f_afterdelete")){

       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
       if(st.hasMoreTokens()) {
         String from=st.nextToken();
              String absPath=(String)cbb_fList2.getSelectedItem();

               if(absPath!=null && absPath.length()>0){
                f_current_remote_absolutePath=absPath;
                f_currentRandom=""+Math.random();
                f_lastGetRemoteNode=null;

                f_appendFileOKLog("Remote delete "+dir+" successfully.\r\n");

                String cmd2="performcommand ct.CTModerator f_getdirandfile "+w.e642((from.equals("2")? absPath:dir))+" "+f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
                f_lastGetRemoteDir=absPath;
                w.sendToOne(cmd2, f_rdb.infinityNodeId);
              }
         }
       }
         return true;
     }
     if(command.equals("f_delete_ok")){

       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String name=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String from=st.nextToken();
           if(st.hasMoreTokens()){
            String data=w.d642(st.nextToken()); 

         }
           }
         }
       }
       }
         return true;
     }
     if(command.equals("f_beginsendfile")){

       if(st.hasMoreTokens()) {
         String sdir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String fn=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String ddir=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String fn2=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             long len=Long.parseLong(w.d642(st.nextToken()));
             f_appendFileWaitLog(ddir+(ddir.endsWith(File.separator)? "":File.separator)+fn2+" <<-- "+sdir+(sdir.endsWith(File.separator)? "":File.separator)+fn+" ("+len+")\r\n");
         }
       }
       }
         }
       }
         return true;
     }
     if(command.equals("f_endsendfile")){

       if(st.hasMoreTokens()) {
         String sdir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String fn=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String ddir=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String fn2=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             long len=Long.parseLong(w.d642(st.nextToken()));
             f_appendFileWaitLog("");
             f_appendFileOKLog(format5.format(new Date())+" "+ddir+(ddir.endsWith(File.separator)? "":File.separator)+fn2+" <<-- "+sdir+(sdir.endsWith(File.separator)? "":File.separator)+fn+" ("+len+")\r\n");
         }
       }
       }
         }
       }
         return true;
     }
     if(command.equals("f_afterdownload")){

       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
           if(st.hasMoreTokens()){
             String from=st.nextToken();
                String absPath=(String)cbb_fList1.getSelectedItem();

                

                f_showLocalDir(absPath,f_local_orderby,f_local_asc);

                f_waitCnt=0;
                f_updateTabTitle(0);
         }
       }
       }
         return true;
     }
     if(command.equals("f_renamedir")){
       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String name=w.d642(st.nextToken());
         if(st.hasMoreTokens()){
           String from=st.nextToken();
           if(st.hasMoreTokens()){
             String newname=w.d642(st.nextToken());
             File f=new File(dir+(dir.endsWith(File.separator)? "":File.separator)+name);
             if(f.renameTo(new File(dir+(dir.endsWith(File.separator)? "":File.separator)+newname))){
               String  cmd2="performcommand ct.CTModerator f_afterrenamedir "+w.e642(dir)+" "+random+" "+w.e642(name)+" "+from+" "+w.e642(newname)+" 0 0 0 0  0 0 0 0 0 ";

               w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
             } else {
                String cmd2="performmessage ct.CTModerator msg "+w.e642("Failed to rename directory from \""+dir+(dir.endsWith(File.separator)? "":File.separator)+name+"\" to \""+dir+(dir.endsWith(File.separator)? "":File.separator)+newname+"\".");
                w.sendToOne(cmd2, originalId);
                return false;
             }
           }
         }
         }
         }
       }
         return true;
     }
     if(command.equals("f_insertdir")){
       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String newdir=w.d642(st.nextToken());
         if(st.hasMoreTokens()){
           String from=st.nextToken();
             File f=new File(dir+(dir.endsWith(File.separator)? "":File.separator)+newdir);
             if(f.mkdir()){
               String  cmd2="performcommand ct.CTModerator f_afterinsertdir "+w.e642(dir)+" "+random+" "+w.e642(newdir)+" "+from+" 0 0 0 0 0  0 0 0 0 0 ";

               w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
               appendStatus(format2.format(new Date())+" : create folder \""+f.getAbsolutePath()+"\" by "+getNameFromId(originalId)+"\r\n");
             } else {
                String cmd2="performmessage ct.CTModerator msg "+w.e642("Failed to create directory \""+dir+(dir.endsWith(File.separator)? "":File.separator)+newdir+"\".");
                w.sendToOne(cmd2, originalId);
                return false;
             }
           }
         }
         }
       }
         return true;
     }
     if(command.equals("f_afterrenamedir")){

       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String name=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String from=st.nextToken();
           if(st.hasMoreTokens()){
             String newname=w.d642(st.nextToken());
             String absPath=dir+(dir.endsWith(File.separator)? "":File.separator)+newname;
             f_skipComboBox2ItemStateChanged=true;

             cbb_fList2.setSelectedItem(absPath);
             f_skipComboBox2ItemStateChanged=false;

             CTFBRemoteDirNode node = (CTFBRemoteDirNode)f_rdb.getLastSelectedPathComponent();
             CTFBRemoteFile rf=node.file;

        if(absPath!=null && absPath.length()>0){
         f_current_remote_absolutePath=absPath;
         f_currentRandom=""+Math.random();
         f_lastGetRemoteNode=null;

         String cmd2="performcommand ct.CTModerator f_getdirandfile "+w.e642(absPath)+" "+f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
         f_lastGetRemoteDir=absPath;
         w.sendToOne(cmd2, f_rdb.infinityNodeId);
      }
           }
           }
         }
         }
       }
         return true;
     }
     if(command.equals("f_makedir")){
       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String name=w.d642(st.nextToken());
         if(st.hasMoreTokens()){
           String from=st.nextToken();
             File f=new File(dir+(dir.endsWith(File.separator)? "":File.separator)+name);
             if(f.mkdir()){
               String  cmd2="performcommand ct.CTModerator f_aftermakedir "+w.e642(dir)+" "+random+" "+w.e642(name)+" "+from+" 0 0 0 0  0 0 0 0 0 ";

               w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
             } else {
                String cmd2="performmessage ct.CTModerator msg "+w.e642("Failed to insert new directory \""+name+"\" under directory \""+dir+"\".");
                w.sendToOne(cmd2, originalId);
                return false;
             }

           }
         }
         }
       }
         return true;
     }
     if(command.equals("f_delfile")){
       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
             String name=st.nextToken();
             File f=new File(dir+(dir.endsWith(File.separator)? "":File.separator)+name);
             if(f.delete()){
               String  cmd2="performcommand ct.CTModerator f_afterdelfile "+w.e642(dir)+" "+random+" "+w.e642(name)+" 0 0 0 0  0 0 0 0 0 ";

               w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
             } else {
                String cmd2="performmessage ct.CTModerator msg "+w.e642("Failed to delete file \""+dir+(dir.endsWith(File.separator)? "":File.separator)+name+"\".");
                w.sendToOne(cmd2, originalId);
                return false;
             }
         }
         }
       }
         return true;
     }
     if(command.equals("f_deldir")){
       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String name=w.d642(st.nextToken());
         if(st.hasMoreTokens()){
           String from=st.nextToken();
             File f=new File(dir+(dir.endsWith(File.separator)? "":File.separator)+name);
             if(f_deleteDir(f)){
               String  cmd2="performcommand ct.CTModerator f_afterdeldir "+w.e642(dir)+" "+random+" "+w.e642(name)+" "+from+" 0 0 0 0  0 0 0 0 0 ";

               w.ap.feedback(w.getMode0(originalId),0,"%comecmdcode%",originalId,cmd2); 
             } else {
                String cmd2="performmessage ct.CTModerator msg "+w.e642("Failed to delete directory \""+dir+(dir.endsWith(File.separator)? "":File.separator)+name+"\".");
                w.sendToOne(cmd2, originalId);
                return false;
             }
           }
         }
         }
       }
         return true;
     }
     if(command.equals("f_delete")){
       f_RDThread.addRDAction(new CTAction(originalId,mode0,2,null,null,cmd));
         return true;
     }
     if(command.equals("f_dirandfile")){

       if(st.hasMoreTokens()) {
       	String dir=st.nextToken();
        dir=w.d642(dir);
        f_skipComboBox2ItemStateChanged=true;

        cbb_fList2.setSelectedItem(dir);
        f_skipComboBox2ItemStateChanged=false;
        f_current_remote_absolutePath=dir;

       if(st.hasMoreTokens()) {
       	String random=st.nextToken();

       if(f_currentRandom.equals(random)){

        if(f_lastGetRemoteNode!=null){

          f_currentRemoteNode.removeAllChildren();
          if(dir.equals(f_currentRemoteNode.file.getAbsolutePath())){
           if(f_currentRemoteNode.file.tmFile==null) f_currentRemoteNode.file.tmFile=new TreeMap();
           else f_currentRemoteNode.file.tmFile.clear();
          }
        } else {
          f_currentRemoteNode =f_rdb.getNodeFromNodeAndAbsPath(f_currentRemoteNode,dir);

          CTFBRemoteFile file0=null;
          if(f_currentRemoteNode!=null){

          if(dir.equals(f_currentRemoteNode.file.getAbsolutePath())){
           if(f_currentRemoteNode.file.tmFile==null) f_currentRemoteNode.file.tmFile=new TreeMap();
           else f_currentRemoteNode.file.tmFile.clear();
          }
          }
        }
        if(st.hasMoreTokens()) {

       if(f_lastGetRemoteNode!=null){

       if(dir.equals(f_currentRemoteNode.file.getAbsolutePath())){
           DefaultTreeModel model = (DefaultTreeModel)f_rdb.getModel();

         while(st.hasMoreTokens()){

             StringTokenizer st2=new StringTokenizer(w.d642(st.nextToken()),sep);
             if(st2.hasMoreTokens()){
             String name=st2.nextToken();
             if(st2.hasMoreTokens()){
               String modified=st2.nextToken();
             if(st2.hasMoreTokens()){
               String len=st2.nextToken();
             String absolutePath=dir+(dir.endsWith(f_remoteFileSeparator)? "":f_remoteFileSeparator)+name;

             CTFBRemoteFile file=new CTFBRemoteFile(name,Long.parseLong(modified),Long.parseLong(len));
             f_currentRemoteNode.file.tmFile.put(name, file);

             } else {
             String absolutePath=dir+(dir.endsWith(f_remoteFileSeparator)? "":f_remoteFileSeparator)+name;

             CTFBRemoteDirNode newNode= new CTFBRemoteDirNode(f_rdb,name,this);
             f_currentRemoteNode.file.tmFile.put(name, newNode.getFile());

             model.insertNodeInto(newNode, f_currentRemoteNode, model.getChildCount(f_currentRemoteNode));
             }
             }
             }
         }

         f_showRemoteDir(f_currentRemoteNode,f_remote_orderby,f_remote_asc);
        }
       } else {

          CTFBRemoteFile file0=null;

             CTFBRemoteDirNode tmpnode=f_rdb.getNodeFromNodeAndAbsPath2(f_rdb.root,dir);
             file0=new CTFBRemoteFile(tmpnode,0,new File(dir).getName());

         file0.tmFile=new TreeMap();

         while(st.hasMoreTokens()){

             StringTokenizer st2=new StringTokenizer(w.d642(st.nextToken()),sep);
             if(st2.hasMoreTokens()){
             String name=st2.nextToken();
             if(st2.hasMoreTokens()){
               String modified=st2.nextToken();
             if(st2.hasMoreTokens()){
               String len=st2.nextToken();
             String absolutePath=dir+(dir.endsWith(f_remoteFileSeparator)? "":f_remoteFileSeparator)+name;

             CTFBRemoteFile file=new CTFBRemoteFile(name,Long.parseLong(modified),Long.parseLong(len));
             file0.tmFile.put(name, file);

             } else {
             String absolutePath=dir+(dir.endsWith(f_remoteFileSeparator)? "":f_remoteFileSeparator)+name;

             CTFBRemoteFile file=new CTFBRemoteFile(null,Long.parseLong(modified),name);
             file0.tmFile.put(name, file);
             }
             }
             }
         }

          f_showRemoteDir(file0,dir,f_remote_orderby,f_remote_asc);
         }
       } else {
           if(f_lastGetRemoteNode!=null){

            f_showRemoteDir(f_currentRemoteNode,f_remote_orderby,f_remote_asc);
           } else {

               CTFBRemoteFile file0=new CTFBRemoteFile("",0,0);
               file0.tmFile=new TreeMap();

               f_showRemoteDir(file0,dir,f_remote_orderby,f_remote_asc);
           }
           }
         return true;
       }
     }
     }
     }

     if(command.equals("sk_setpanel")){
       if(!tutorMode && st.hasMoreTokens()) {
         String show=st.nextToken();
           if(st.hasMoreTokens()){
             String focus=st.nextToken();
           if(st.hasMoreTokens()){
             String au1=st.nextToken();
           if(st.hasMoreTokens()){
             String au2=st.nextToken();
             if(au1.equals("1")) auOne_asAMember=w.addOneVar(auOne_asAMember, 16);
             else auOne_asAMember=w.removeOneVar(auOne_asAMember, 16);
             if(au2.equals("1")) auOne_asAMember=w.addOneVar(auOne_asAMember, 3);
             else auOne_asAMember=w.removeOneVar(auOne_asAMember, 3);
         if(show.equals("1")){
           showPanel(sketchPanel);
             if(focus.equals("1")) jTabbedPane1.setSelectedComponent(sketchPanel);
             }else if(!tutorMode && containPanel(sketchPanel)) jTabbedPane1.remove(sketchPanel);
         if(w.checkOneVar(auOne_asAMember, 3) || w.checkOneVar(auOne_asAMember, 16)){

           sk_upperPanel.setVisible(true);
           if(w.checkOneVar(auOne_asAMember, 3)){
             jLabel8.setVisible(true);
             cbb_skMList.setVisible(true);
           } else {
             jLabel8.setVisible(false);
             cbb_skMList.setVisible(false);
           }
         } else sk_upperPanel.setVisible(false);
         }
       }
         }
       }
       return true;
     }
     if(command.equals("sk_clear")){

       showPanel(sketchPanel);
       jTabbedPane1.setSelectedComponent(sketchPanel);
       sk_dataV.clear();
       skPanel2.setData(sk_dataV);
       setRemoteTutor(originalId);
       return true;
     }
     if(command.equals("sk_draw")){
     int x,y,mode2;
     if(st.hasMoreTokens()) {
     	x=Integer.parseInt(st.nextToken()); 
     	if(st.hasMoreTokens()){
     	  y=Integer.parseInt(st.nextToken());
     	  if(st.hasMoreTokens()){
            showPanel(sketchPanel);
            jTabbedPane1.setSelectedComponent(sketchPanel);
     	    mode2=Integer.parseInt(st.nextToken());

     	    switch(mode2){
     	      case 1:

                sk_dataV.add(new CTSketchData(1,x,y,0,1,0));
                skPanel2.setData(sk_dataV);

     	        break;
     	      case 2:

                sk_dataV.add(new CTSketchData(2,x,y,0,1,0));
                skPanel2.setData(sk_dataV);

     	        break;
     	    }
     	  }
     	}
     }
       return true;
     }
     if(command.equals("sk_draw2")){
     if(st.hasMoreTokens()) {
       String arr[]=ylib.csvlinetoarray(st.nextToken());
       int values[]=new int[arr.length];
       for(int i=0;i<arr.length;i++) values[i]=Integer.parseInt(arr[i]);
     showPanel(sketchPanel);
     jTabbedPane1.setSelectedComponent(sketchPanel);
                sk_dataV.add(new CTSketchData(values));
                skPanel2.setData(sk_dataV);
     }
       return true;
     }

	 return true;
  }
    public void setId(String id2,String name){
      String oldId=srn_currentId;
      if(!oldId.equals(id2)){
        this.srn_currentId=id2;
        this.srn_currentName=name;
        this.setTitle("screen view of "+id2+" (name="+name+")");
        if(btn_srnStart.getText().equals(srn_startStr) && (rb_srnMonitor.isSelected() || rb_srnControlAndImage.isSelected())){
           String cmd="performcommand ct.CTRMAction2 stopsrn null null null null null null null null null 0 0 0 0 ? ? ? 0";
               String to=(String) cbb_srnMList.getSelectedItem();
    if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));

    else w.sendToOne(cmd, (String)nameIdMap.get(to));
           cmd="performcommand ct.CTRMAction2 contsrn null null null null null null null null null 0 0 0 0 ? ? ? 0";
            to=(String) cbb_srnMList.getSelectedItem();
    if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));

    else w.sendToOne(cmd, (String)nameIdMap.get(to));
        } else {
             srn_refresh();
        }

      }
    }

void srn_refresh(){
  String to=(String) cbb_srnMList.getSelectedItem();
  if((w.checkOneVar(auOne_asAMember, 7) && !to.equals(lattestRemoteTutorName))  || tutorMode || ((w.checkOneVar(auOne_asAMember, 6) && to.equals(lattestRemoteTutorName) ))){ 
   srn_setEnv();
   srn_issueRequest();
  }
}
void srn_pressButton(){
  String cmd="unknown";
  String  to=(String) cbb_srnMList.getSelectedItem();
  if(srn_imgByInterval){
    if(btn_srnStart.getText().equals(srn_startStr) && (rb_srnMonitor.isSelected() || rb_srnControlAndImage.isSelected())){
      if((w.checkOneVar(auOne_asAMember, 7) && !to.equals(lattestRemoteTutorName)) || tutorMode || (w.checkOneVar(auOne_asAMember, 6) && to.equals(lattestRemoteTutorName))){ 
        cmd="performcommand ct.CTRMAction2 contsrn2 "+srn_intervalMS+" "+srn_imgByRobot+" null null null null null null null 0 0 0 0 ? ? ? 0";
        srn_toInform=false;
        btn_srnStart.setText(srn_stopStr);
        if(to.equals(allNodesName))  {
            w.sendToGroupExceptMyself(cmd, w.getGNS(11));
            srn_inform(1);
        }

        else {
            w.sendToOne(cmd, (String)nameIdMap.get(to));
            w.setAHVar("counterpartID2", (String)nameIdMap.get(to));
            srn_inform(1);
        }

        jLabel2.requestFocusInWindow();
      }
    } else  if(btn_srnStart.getText().equals(srn_stopStr)){

        srn_toInform=false;
        srn_inform(2);
        btn_srnStart.setText(srn_startStr);
    }
 }  else {
      if(btn_srnStart.getText().equals(srn_startStr)) {
        if((w.checkOneVar(auOne_asAMember, 7) && !to.equals(lattestRemoteTutorName)) || tutorMode || (w.checkOneVar(auOne_asAMember, 6) && to.equals(lattestRemoteTutorName))){ 
          srn_setEnv();
          srn_issueRequest();
          if(!to.equals(allNodesName)) w.setAHVar("counterpartID2", (String)nameIdMap.get(to));
          srn_inform(1);
          if(rb_srnMonitor.isSelected() || rb_srnControlAndImage.isSelected()) srn_toInform=true;
          btn_srnStart.setText(srn_stopStr);

          jLabel2.requestFocusInWindow();
        }
      } else  if(btn_srnStart.getText().equals(srn_stopStr)){

        srn_toInform=false;
        w.setAHVar("counterpartID2", "");
        srn_inform(2);
        btn_srnStart.setText(srn_startStr);
     }
  }
}

void srn_setEnv(){

    srn_updating=true;
    srn_currentCode=String.valueOf((int)(Math.random()*1000000.0));
    String selName=(String)cbb_srnMList.getSelectedItem();
    srn_ttl=cbb_srnMList.getItemCount()-1;
    srn_currentId=(String)nameIdMap.get(selName);
    if(allNodesName.equals(selName)){
      int div1=(int)Math.sqrt((double)srn_ttl);
      int div2=div1+1;
      if((div1*div1)<srn_ttl) srn_div=div2; else srn_div=div1;
      srn_viewCount=srn_ttl;
    } else {srn_div=1; srn_viewCount=1;}
    int inx=0;
    if(srn_viewCount>1){
      for(Iterator it=srn_imgMap.keySet().iterator();it.hasNext() && inx<srn_viewCount;){
        CTImgClass ic=(CTImgClass) srn_imgMap.get(inx);
        if(ic!=null){
        ic.inx=inx;
        ic.img=null;
        ic.nodeName=(String)cbb_srnMList.getItemAt(inx+1);
        ic.nodeId=(String)nameIdMap.get(ic.nodeName);
        ic.randomCode=srn_currentCode;
        } else {
          String name=(String)cbb_srnMList.getItemAt(inx+1);
          ic=new CTImgClass(inx,null,(String)nameIdMap.get(name),name,srn_currentCode);
        }
        srn_imgMap.put(inx,ic);
        inx++;
      }
      if(inx<srn_viewCount){
        for(; inx<srn_viewCount;){
          String name=(String)cbb_srnMList.getItemAt(inx+1);
          CTImgClass ic=new CTImgClass(inx,null,(String)nameIdMap.get(name),name,srn_currentCode);
          srn_imgMap.put(inx,ic);
          inx++;
        }
      }
    } else if(srn_viewCount==1){
        int selInx=cbb_srnMList.getSelectedIndex();
        if(selInx==0) selInx=1;
        for(Iterator it=srn_imgMap.keySet().iterator();it.hasNext() && inx<srn_viewCount;){
          CTImgClass ic=(CTImgClass) srn_imgMap.get(inx);
          ic.inx=0;
          ic.img=null;
          ic.nodeName=(String)cbb_srnMList.getItemAt(selInx);
          ic.nodeId=(String)nameIdMap.get(ic.nodeName);
          ic.randomCode=srn_currentCode;
          srn_imgMap.put(inx,ic);
          inx++;
        }
        if(inx<srn_viewCount){
          for(; inx<srn_viewCount;){
            String name=(String)cbb_srnMList.getItemAt(selInx);
            CTImgClass ic=new CTImgClass(0,null,(String)nameIdMap.get(name),name,srn_currentCode);
            srn_imgMap.put(inx,ic);
            inx++;
          }
        }
      }
    srn_updating=false;

}

void srn_issueRequest(){
    String  to=(String) cbb_srnMList.getSelectedItem();
    if(to.equals(allNodesName))  {
      for(int i=0;i<srn_viewCount;i++){
        CTImgClass ic=(CTImgClass) srn_imgMap.get(i);
        String cmd="performcommand ct.CTRMAction2 getimg2 "+srn_imgByRobot+" "+srn_currentCode+" "+srn_div+" "+i+" null null null null null 0 0 0 0 ? ? ? 0";

        w.sendToOne(cmd, ic.nodeId);
      }
    }

    else {
      String cmd="performcommand ct.CTRMAction2 getimg2 "+srn_imgByRobot+" "+srn_currentCode+" 1 0 null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(cmd, (String)nameIdMap.get(to));
    }
}

void srn_inform(int type){
    String  to=(String) cbb_srnMList.getSelectedItem();
    String cmd="";
    switch(type){
        case 1:
          cmd="performmessage ct.CTModerator statusmsg "+w.e642(w.getGNS(27)+" %CTScreenBoard.xy.msg5%");
          break;
        case 2:
          cmd="performmessage ct.CTModerator statusmsg "+w.e642(w.getGNS(27)+" %CTScreenBoard.xy.msg6%");
          break;
        case 3:
          cmd="performmessage ct.CTModerator statusmsg "+w.e642(w.getGNS(27)+" %CTScreenBoard.xy.msg7%");
          break;

    }
    if(to.equals(allNodesName))  {
      for(int i=0;i<srn_viewCount;i++){
        CTImgClass ic=(CTImgClass) srn_imgMap.get(i);

        w.sendToOne(cmd, ic.nodeId);
      }
    }

    else {
      w.sendToOne(cmd, (String)nameIdMap.get(to));
    }
}

int getInx(int div,int x,int y){
  int inx=0;
  if(div>1){
    int w=sPanel.getWidth();
    int h=sPanel.getHeight();
    double w2=((double)w)/((double)div);
    double h2=((double)h)/((double)div);
    int x2=(int)(((double)x)/w2);
    int y2=(int)(((double)y)/h2);
    inx=y2 * div + x2;
  }
  return inx;
}

private void srn_keyPress(java.awt.event.KeyEvent evt){
  String to=(String)cbb_srnMList.getSelectedItem();
  if(btn_srnStart.getText().equals(srn_stopStr) && srn_div==1 && rb_srnControlAndImage.isSelected() && ((w.checkOneVar(auOne_asAMember, 7) && !to.equals(lattestRemoteTutorName)) || tutorMode || (w.checkOneVar(auOne_asAMember, 6) && to.equals(lattestRemoteTutorName)))){
      int type=1;
      int id=evt.getID();
      int code=evt.getKeyCode();
      int modifier=evt.getModifiers();
      String cmd0="performcommand ct.CTRMAction2 rm_key2 "+type+" "+id+" "+code+" "+modifier+" "+srn_currentCode+" "+(srn_toInform? "1":"0")+" 0 0 0 0 ? ? ? 0";
      w.sendToOne(cmd0, srn_currentId);

  }
}
private void srn_keyRelease(java.awt.event.KeyEvent evt){
  String to=(String)cbb_srnMList.getSelectedItem();
  if(btn_srnStart.getText().equals(srn_stopStr) && srn_div==1 && rb_srnControlAndImage.isSelected() && ((w.checkOneVar(auOne_asAMember, 7) && !to.equals(lattestRemoteTutorName)) || tutorMode || (w.checkOneVar(auOne_asAMember, 6) && to.equals(lattestRemoteTutorName)))){
      int type=2;
      int id=evt.getID();
      int code=evt.getKeyCode();
      int modifier=evt.getModifiers();
      String cmd0="performcommand ct.CTRMAction2 rm_key2 "+type+" "+id+" "+code+" "+modifier+" "+srn_currentCode+" "+(srn_toInform? "1":"0")+" 0 0 0 0 ? ? ? 0";

      w.sendToOne(cmd0, srn_currentId);
  }

}

  boolean containPanel(JPanel panel){
    boolean rtn=false;
    for(int i=0;i<jTabbedPane1.getComponentCount();i++){

      if(jTabbedPane1.getComponentAt(i).equals((Component)panel)) return true;

    }
    return rtn;
  }

  void showPanel(Component comp){
    if(!isVisible()) {
             setVisible(true);
             this.setExtendedState(thisState);
             if(thisState!=JFrame.MAXIMIZED_BOTH) this.setBounds(thisRect);
    }
    if(comp==null){
      int inx=0;
      if(!containPanel(slidePanel)) {jTabbedPane1.insertTab(bundle2.getString("CTModerator.slidePanel.TabConstraints.tabTitle"), null, slidePanel, "", inx); inx++;}
      if(!containPanel(msgPanel)) {jTabbedPane1.insertTab(bundle2.getString("CTModerator.msgPanel.TabConstraints.tabTitle"), null, msgPanel, "", inx); inx++;}
      if(!containPanel(screenPanel)) {jTabbedPane1.insertTab(bundle2.getString("CTModerator.screenPanel.TabConstraints.tabTitle"), null, screenPanel, "", inx); inx++;}
      if(!containPanel(filePanel) && w.checkOneVar(auOne_asAMember, 13)) {jTabbedPane1.insertTab(bundle2.getString("CTModerator.filePanel.TabConstraints.tabTitle"), null, filePanel, "", inx); inx++;}
      if(!containPanel(sketchPanel)) {jTabbedPane1.insertTab(bundle2.getString("CTModerator.sketchPanel.TabConstraints.tabTitle"), null, sketchPanel, "", inx); inx++;}

      

    } else if(comp.equals((Component)slidePanel)){
           if(!containPanel(slidePanel)){
             jTabbedPane1.insertTab(bundle2.getString("CTModerator.slidePanel.TabConstraints.tabTitle"), null, slidePanel, "", 0);
           }

       } else if(comp.equals((Component)msgPanel)){
           if(!containPanel(msgPanel)){
             int inx=0;
             if(containPanel(slidePanel)) inx++;
             jTabbedPane1.insertTab(bundle2.getString("CTModerator.msgPanel.TabConstraints.tabTitle"), null, msgPanel, "", inx);
           }

       } else if(comp.equals((Component)screenPanel)){
           if(!containPanel(screenPanel)){
             int inx=0;
             if(containPanel(slidePanel)) inx++;
             if(containPanel(msgPanel)) inx++;
             jTabbedPane1.insertTab(bundle2.getString("CTModerator.screenPanel.TabConstraints.tabTitle"), null, screenPanel, "", inx);
           }

       } else if(comp.equals((Component)filePanel) && w.checkOneVar(auOne_asAMember, 13)){
           if(!containPanel(filePanel)){
             int inx=0;
             if(containPanel(slidePanel)) inx++;
             if(containPanel(msgPanel)) inx++;
             if(containPanel(screenPanel)) inx++;
             jTabbedPane1.insertTab(bundle2.getString("CTModerator.filePanel.TabConstraints.tabTitle"), null, filePanel, "", inx);
           }
       } else if(comp.equals((Component)sketchPanel)){
           if(!containPanel(sketchPanel)){
             int inx=0;
             if(containPanel(slidePanel)) inx++;
             if(containPanel(msgPanel)) inx++;
             if(containPanel(screenPanel)) inx++;
             if(containPanel(filePanel)) inx++;
             jTabbedPane1.insertTab(bundle2.getString("CTModerator.sketchPanel.TabConstraints.tabTitle"), null, sketchPanel, "", inx);
           }

       }
  }
  public void msg_textPaneAppend(String temp2){
       msg_textPaneAppend(temp2,null,0);

  }

  public void msg_textPaneAppend(String temp2,Color col,int fontSize){

     msg_displayV.add(new DisplayData(temp2,col,fontSize));
      Runnable  runnable = new Runnable() {
        public void run(){
         if(msg_textPane.getText().length()>msg_maxMainLogLength) {

           if(msg_checkBox1.isSelected()) msg_saveLog(msg_textPane.getText().trim()+"\r\n");
             try{

               msg_styleDoc.remove(0, msg_styleDoc.getLength());

             } catch(BadLocationException e){
                e.printStackTrace();
              }

         }
         DisplayData dData=(DisplayData)msg_displayV.get(0);
            try   {   
                SimpleAttributeSet   attrSet   =   new   SimpleAttributeSet();
                if(dData.fontColor!=null) StyleConstants.setForeground(attrSet, dData.fontColor);   
                if(dData.fontSize>0) StyleConstants.setFontSize(attrSet,   dData.fontSize);  
                msg_styleDoc.insertString(msg_styleDoc.getLength(), dData.data,  attrSet);

            }   catch   (BadLocationException   e)   {   
               System.out.println("BadLocationException:   "   +   e);   
                }   

              msg_displayV.remove(0);
              msg_needChk=true;
            }
        };
        SwingUtilities.invokeLater(runnable);
  }
   class MyCellRenderer extends JButton implements ListCellRenderer {  
     public MyCellRenderer() {  
         setOpaque(true); 

     }
     boolean b=false;
    @Override
    public void setBackground(Color bg) {

         if(!b)  {
             return;
         }

        super.setBackground(bg);
    }
     public Component getListCellRendererComponent(  
         JList list,  
         Object value,  
         int index,  

         boolean isSelected,  
         boolean cellHasFocus)  {  

         b=true;
         setText(" ");           
         setBackground((Color)value);        
         b=false;
         return this;  
     }  
}
   public class DisplayData{
   String data;
   int fontSize;
   Color fontColor;
   public DisplayData(String data,Color fontColor,int fontSize){
     this.data=data;
     this.fontColor=fontColor;
     this.fontSize=fontSize;
   }
 }
 class  Srn_Thread2 extends Thread{
   boolean sleep=false;
   public void run(){
     while(true){
     while(srn_actions.size()>0){
       Object obj=srn_actions.get(0);
       CTAction action=(CTAction)obj;
        srn_imgB=(byte[])action.obj;

        try{
          InputStream in = new ByteArrayInputStream(srn_imgB);
	  BufferedImage img = ImageIO.read(in);

        int inx=Integer.parseInt(action.stringx[3]);

        CTImgClass ic=(CTImgClass)srn_imgMap.get(inx);
        ic.img=img;
        ic.randomCode=action.stringx[1];
        srn_imgMap.put(inx,ic);
        if(srn_div==1) {
          if(cb_srnFix.isSelected()){}
          sPanel.setImages(srn_imgMap);
        }
        else {
          srn_hasNewValue=true;
          if(srn_toInform){

            for(int i=0;i<srn_viewCount;i++){
               CTImgClass ic2=(CTImgClass) srn_imgMap.get(i);
               if(ic2!=null && ic2.nodeId.equals(action.originalId)){
                String contCmd="performcommand ct.CTRMAction2 getimg2 "+srn_imgByRobot+" "+srn_currentCode+" "+srn_div+" "+i+" null null null null null 0 0 0 0 ? ? ? 0";

                w.sendToOne(contCmd, action.originalId);
               }
            }

          }
        }
        } catch(IOException e){e.printStackTrace();};
       srn_actions.remove(obj);
     }
     try{
       sleep=true;
       this.sleep(31536L);
     }catch(InterruptedException e){}
     sleep=false;
     }
   }
   public boolean isSleep(){
     return sleep;
   }
 }
 class F_UploadThread extends Thread{
    boolean f_isSleep2=false,runUploadThread=false;
    int currentStatus2=0;
    long waitTime2=365L*24L*60L*60L*1000L;
    Vector f_uploadActions=new Vector(), uploadFileVector=new Vector();
    String feedbackResult2="";
    StringBuffer sendSB2=new StringBuffer();
    public void run(){
    runUploadThread=true;

    currentStatus2=0;

    while(runUploadThread){

      currentStatus2=1;
      try{
          f_isSleep2=true;
          sleep(waitTime2);
      }catch(InterruptedException e){
        }
       f_isSleep2=false;
      while(f_uploadActions.size()>0){
          currentStatus2=2;
          String action=(String)f_uploadActions.get(0);
          String[] strs=ylib.csvlinetoarray(action);
          String originalId2=strs[0];
          String cmd=strs[1],str10,str11,str12,str13;
          uploadFileVector.removeAllElements();
          

          String ddir=f_current_remote_absolutePath;
          String from=strs[3];
         if(cmd.equals("sendfile")){

            String sDirOrFile=strs[2],sdir="";
            sDirOrFile=w.replaceC(sDirOrFile);

            if(sDirOrFile.lastIndexOf(File.separator)==sDirOrFile.length()-1) {
              if(new File(sDirOrFile).getParent()!=null){
                sDirOrFile=sDirOrFile.substring(0, sDirOrFile.length()-1);
              }
            }
            if(new File(sDirOrFile).getParent()!=null){
              sdir=new File(sDirOrFile).getParent();
            } else sdir=sDirOrFile;
            if(!sdir.endsWith(File.separator)) sdir=sdir+File.separator;

            String subdirmode="includesubdir";
            if(ddir.lastIndexOf(File.separator)!=ddir.length()-1) ddir=ddir+CTModerator.this.f_remoteFileSeparator;
            String writemode=f_myUploadMode;
            

            String fn="",dfn="";
            File f=new File(sDirOrFile),f2;
            if(f.exists()){
              if(f.isDirectory()){
                      getUploadFileVectors(sdir,"*",0,0,subdirmode,ddir,sDirOrFile,writemode);
                   } else {

                            getUploadFileVector(sdir,f,ddir,f.getName(),sdir,writemode);
                            sDirOrFile=f.getParent();

                       }

            }

               while(uploadFileVector.size()>0){
                  long onevar=0;
                   currentStatus2=3;
                   f_uploadChkExistResult=false;
                   String fdata[]=ylib.csvlinetoarray((String) uploadFileVector.get(0));
                   String lasttime=fdata[1],filesize=fdata[2],ddir2=fdata[3],relative=fdata[4],dfilename2=fdata[5];

                   String nsdir=sdir+relative;
                   writemode=f_myUploadMode;
                   if(!writemode.equalsIgnoreCase("overwrite")){
                   sendSB2.delete(0,sendSB2.length());
                   int currentRandom2=(int)(Math.random()*10000000D);
                   sendSB2.append("performaction ct.CTModerator 1 f_uploadcheckexist "+ylib.replace(fdata[0]," ","%space%")+" "+ylib.replace(ddir2," ","%space%")+" "+(relative.length()>0? ylib.replace(ylib.replace(ylib.replace(relative," ","%space%"),"/",CTModerator.this.f_remoteFileSeparator),"\\",CTModerator.this.f_remoteFileSeparator):"%empty%")+" "+
                           ylib.replace(dfilename2," ","%space%")+" "+lasttime+" "+filesize+" "+writemode+" "+currentRandom2+" "+f_currentStatus);

                    w.sendToOne(sendSB2.toString(),originalId2);
                   try{
                     f_isSleep2=true;
                      sleep(waitTime2);
                   }catch(InterruptedException e){
                    }
                   f_isSleep2=false;
                   if(f_uploadChkExistResult){
                   if(writemode.equalsIgnoreCase("ask")){

                     

          String result2="skip";

            String oldTime=f_uploadChkExistSixthPara;
            String oldSize=f_uploadChkExistFifthPara;
          if(f_uploadDialog==null){
            f_uploadDialog=new CTFBUploadFileDialog(CTModerator.this,true);
          }

          f_uploadDialog.setInfo(f_uploadChkExistFourthPara,oldSize,oldTime,fdata[0],filesize,lasttime,originalId2,"");
          f_uploadDialog.setVisible(true);

                   currentRandom2=4;

                     if(f_uploadChkSaveResult.equals("overwrite")){
                       File f3=new File(fdata[0]);
                       f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,w.getGNS(1),originalId2,onevar);
                     } if(f_uploadChkSaveResult.equals("checkdate")){
                       if(Boolean.parseBoolean(f_uploadChkExistSecondPara)){
                         File f3=new File(fdata[0]);
                         f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,w.getGNS(1),originalId2,onevar);
                       }
                     } if(f_uploadChkSaveResult.equals("rename")){
                       File f3=new File(fdata[0]);
                       f_sendFile3(sdir,f3,ddir,f_uploadChkExistThirdPara,nsdir,writemode,w.getGNS(1),originalId2,onevar);
                     } else if(f_uploadChkSaveResult.equals("cancel")){
                        uploadFileVector.clear();
                        f_uploadActions.clear();
                     }

                   } else if(writemode.equalsIgnoreCase("checkdate")){
                      if(Boolean.parseBoolean(f_uploadChkExistSecondPara)){
                        File f3=new File(fdata[0]);
                        f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,w.getGNS(1),originalId2,onevar);
                      }
                   } else if(writemode.equalsIgnoreCase("rename")){
                       File f3=new File(fdata[0]);
                       f_sendFile3(sdir,f3,ddir,f_uploadChkExistThirdPara,nsdir,writemode,w.getGNS(1),originalId2,onevar);
                     } 
                   }else{
                     File f3=new File(fdata[0]);
                     f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,w.getGNS(1),originalId2,onevar);
                   }
                   } else {
                     File f3=new File(fdata[0]);
                     f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,w.getGNS(1),originalId2,onevar);
                   }
                 if(uploadFileVector.size()>0) uploadFileVector.remove(0);
               }

          }
          if(f_uploadActions.size()>0) f_uploadActions.remove(0);
          if(f_uploadActions.size()<1 && !f_uploadChkSaveResult.equalsIgnoreCase("cancel")){
        String absPath=(String)cbb_fList2.getSelectedItem();

      if(absPath!=null && absPath.length()>0){
        if(!f_lastGetRemoteDir.equals(absPath) || lastGetDir_time+1000L <System.currentTimeMillis()){
         f_current_remote_absolutePath=absPath;
         f_currentRandom=""+Math.random();

         String cmd2="performcommand ct.CTModerator f_getdirandfile "+w.e642(absPath)+" "+f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
         f_lastGetRemoteDir=absPath;
         f_lastGetRemoteNode=null;
         lastGetDir_time=System.currentTimeMillis();
         w.sendToOne(cmd2, originalId2);
        }
      }
           }
          }
      f_waitCnt=0;
      f_updateTabTitle(0);
    } 
    f_currentStatus=6;
    }

  public void f_addUploadAction(String action){
      f_uploadActions.add(action);
      if(f_isSleep2) interrupt();
  }
  public void getUploadFileVector(String sdir,File f,String ddir, String dfilename,String nsdir,String writemode){
     String relative="";
     if(sdir.length()<nsdir.length()) relative=nsdir.substring(sdir.length());
     uploadFileVector.add(ylib.tocsv(f.getAbsolutePath())+","+f.lastModified()+","+f.length()+","+ylib.tocsv(ddir)+","+ylib.tocsv(relative)+","+ylib.tocsv(dfilename));
     if(uploadFileVector.size()>=f_fileVectorLimit){

     }
  }

 public void getUploadFileVectors(String sdir,String key2,long fromtime,long totime,String subdirmode,String ddir, String nsdir,String writemode){
               if(sdir.lastIndexOf(File.separator)!=sdir.length()-1) sdir=sdir+File.separator;
               if(nsdir.lastIndexOf(File.separator)!=nsdir.length()-1) nsdir=nsdir+File.separator;
               if(ddir.lastIndexOf(File.separator)!=ddir.length()-1) ddir=ddir+File.separator;
               File f=new File(nsdir);
                                          File[] contents1=f.listFiles();
                                          Arrays.sort(contents1);
                                          for(int x1=0;x1<contents1.length;x1++) {
                                            if(ylib.chkfn(key2,contents1[x1].getName()) && contents1[x1].isFile()) {
                                            	getUploadFileVector(sdir,contents1[x1],ddir,contents1[x1].getName(),nsdir,writemode);
                                            }
                                          }
                                          for(int x1=0;x1<contents1.length;x1++) {
                                            if(contents1[x1].isDirectory()) {
                                              getUploadFileVectors(sdir,key2,fromtime,totime,subdirmode,ddir,contents1[x1].getAbsolutePath(),writemode);
                                            }
                                          }
  }
  }
  class F_DeletedByRemote_Thread extends Thread{
    boolean f_isSleep3=false,runRDThread=false;
    int currentStatus3=0;
    long waitTime3=365L*24L*60L*60L*1000L;
    Vector f_RDActions=new Vector();

    String dir="_",from="0";
    public void run(){
    runRDThread=true;

    currentStatus3=0;

    while(runRDThread){

      currentStatus3=1;
      try{
          f_isSleep3=true;
          sleep(waitTime3);
      }catch(InterruptedException e){
        }
       f_isSleep3=false;
      while(f_RDActions.size()>0){
        CTAction action=(CTAction)f_RDActions.get(0);
        StringTokenizer st=new StringTokenizer(action.data," ");
        if(st.hasMoreTokens()) {
         String cmd=st.nextToken();
         if(st.hasMoreTokens()){
         dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
          String name=w.d642(st.nextToken());
         if(st.hasMoreTokens()){
           from=st.nextToken();
             File f=new File(dir+(dir.endsWith(File.separator)? "":File.separator)+name);
             if(f_deleteDir(f)){
               String  cmd2="performcommand ct.CTModerator f_delete_ok "+w.e642(dir)+" "+random+" "+w.e642(name)+" "+from+" "+w.e642("Delete \""+dir+(dir.endsWith(File.separator)? "":File.separator)+name+"\" successfully.")+" 0 0 0 0  0 0 0 0 0 ";

               w.ap.feedback(w.getMode0(action.originalId),0,"%comecmdcode%",action.originalId,cmd2); 
               appendStatus(format2.format(new Date())+" : delete "+f.getAbsolutePath()+" by "+getNameFromId(action.originalId)+"\r\n");
             } else {
                String cmd2="performmessage ct.CTModerator f_delete_failed "+w.e642(dir)+" "+random+" "+w.e642(name)+" "+from+" "+w.e642("Failed to delete \""+dir+(dir.endsWith(File.separator)? "":File.separator)+name+"\".")+" 0";

                w.ap.feedback(w.getMode0(action.originalId),0,"%comecmdcode%",action.originalId,cmd2); 
             }
         }
         }
         }
         }
       }
        if(f_RDActions.size() >0)f_RDActions.remove(0);
          if(f_RDActions.size()<1){

         String cmd2="performcommand ct.CTModerator f_afterdelete "+w.e642(dir)+" "+from+" 0 0 0 0  0 0 0 0 0 ";
         w.ap.feedback(w.getMode0(action.originalId), 0, "%comecmdcode%",action.originalId,cmd2);
         }
      }
    } 
    f_currentStatus=6;
    }

  public void addRDAction(CTAction action){
      f_RDActions.add(action);
      if(f_isSleep3) interrupt();
  }
  

  }
  class F_DeletedByLocal_Thread extends Thread{
    boolean f_isSleep4=false,runLDThread=false;
    int currentStatus4=0;
    long waitTime3=365L*24L*60L*60L*1000L;
    Vector LDActions=new Vector(), LDFileVector=new Vector();
    String feedbackResult4="";
    StringBuffer sendSB3=new StringBuffer();
    public void run(){
    runLDThread=true;

    currentStatus4=0;

    while(runLDThread){

      currentStatus4=1;
       f_isSleep4=false;
      while(LDActions.size()>0){
          currentStatus4=2;
          String action=(String)LDActions.get(0);
            File f=new File(action);
            if(f.exists()){
              if(!f_deleteDir(f)){
                JOptionPane.showMessageDialog(null, bundle2.getString("CTModerator.xy.msg46")+" "+f.getAbsolutePath());
                jTextArea3.append(bundle2.getString("CTModerator.xy.msg46")+" "+f.getAbsolutePath()+"\r\n");

                LDActions.clear();
              }else {

              }
            }
          if(LDActions.size()>0) {
            LDActions.remove(0);

          }
          if(LDActions.size()<1){
                String absPath=(String)cbb_fList1.getSelectedItem();

                f_showLocalDir(absPath,f_local_orderby,f_local_asc);

           }
          }
        try{
          f_isSleep4=true;
          sleep(waitTime3);
      }catch(InterruptedException e){
        }

    } 
    f_currentStatus=6;
    }

  public void addLDAction(String action){
      LDActions.add(action);
      if(f_isSleep4) interrupt();
  }
  

  }
public void msg_saveLog(String str1){
  String file="log_"+format4.format(new Date()).subSequence(0,8)+".txt";
  try{
	  FileOutputStream out = new FileOutputStream (msgLogDir+(msgLogDir.endsWith(File.separator)? "":File.separator)+file,true);
	  byte [] b=str1.getBytes();
	  out.write(b);
	  out.close();
      }catch(IOException e){e.printStackTrace();}
}
public void f_saveLog(String str1){
  String file="log_"+format4.format(new Date()).subSequence(0,8)+".txt";
  try{
	  FileOutputStream out = new FileOutputStream (fileLogDir+(fileLogDir.endsWith(File.separator)? "":File.separator)+file,true);
	  byte [] b=str1.getBytes();
	  out.write(b);
	  out.close();
      }catch(IOException e){e.printStackTrace();}
}
public void status_saveLog(String str1){
  String file="log_"+format4.format(new Date()).subSequence(0,8)+".txt";
  try{
	  FileOutputStream out = new FileOutputStream (statusLogDir+(statusLogDir.endsWith(File.separator)? "":File.separator)+file,true);
	  byte [] b=str1.getBytes();
	  out.write(b);
	  out.close();
      }catch(IOException e){e.printStackTrace();}
}
void msg_clear(){
  if(msg_checkBox1.isSelected())  msg_saveLog(msg_textPane.getText().trim()+"\r\n");
   try{

   msg_styleDoc.remove(0, msg_textPane.getDocument().getLength());
 } catch(BadLocationException e){
   e.printStackTrace();
 }
}
void msg_clear_to(){
      String cmd="performcommand ct.CTModerator msg_clear";
      if(tutorMode || w.checkOneVar(auOne_asAMember,4)){
  String to=(String) cbb_moderatorMsgList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
      }
}
public void msg_sendMsg(String s){
  if (s.length()>0 && (w.checkOneVar(auOne_asAMember, 4)  || tutorMode)){
    String cmd="performmessage ct.CTModerator msg_msg "+w.e642(s)+" "+auOne_asAMember+" "+w.getGNS(27);
    String to=(String) cbb_msgMList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOne(cmd, (String)nameIdMap.get(to));
  }
}
  public void run(){

    runThread=true;
    while(runThread){
      isSleep=false;
      while(f_saveActions.size()>0){

        Object o=f_saveActions.get(0);
        CTAction act=(CTAction)o;
        switch(act.actionType){
          case 1:
            f_saveFile2(act.stringx,act.mode0,act.originalId);
            break;
          case 2:
            f_saveFile3(act.stringx,act.obj,act.mode0,act.originalId);
            break;
        }
        f_saveActions.remove(o);

      }
      try{
        isSleep=true;
        t.sleep(365L*24L*60L*60L*1000L);
      } catch(InterruptedException e){

      	}
    }
  }
  /** 
     Note that InternetAddress.validate() considers user@[10.9.8.7] , bla@bla, and user@localhost as valid email addresses - which they are according to 
     the RFC. Though, depending on the use case (web form), you might want to treat them as invalid.
   */
  

 public boolean p_isValidEmailAddress2(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }

 public boolean p_isValidRK(String rc){
   if(rc==null || rc.length()<10) return false;
   rc=w.d16(rc);
   String r[]=w.csvLineToArray(rc);
   if(r.length<7) return false;
   if(r[0].length()<1 || !w.isNumeric(r[0]) || Integer.parseInt(r[0])<1) return false;
   int ver=Integer.parseInt(r[0]);
   switch(ver){
     case 1:
      if(r[1].length()<1 || !w.isNumeric(r[1]) || Integer.parseInt(r[1])<1) return false;
      if(r[2].length()<1 || !w.isNumeric(r[2]) || Integer.parseInt(r[2])<1) return false;
      if(r[3].length()<1 || !w.isNumeric(r[3]) || Integer.parseInt(r[3])<1) return false;
      if(r[4].length()<1 || !w.isNumeric(r[4]) || Integer.parseInt(r[4])<0) return false;
      if(r[6].length()<1 || !w.isNumeric(r[6])) return false;
      long sn=Long.parseLong(r[1]);
      long apcode=Long.parseLong(r[2]);
      long cnt=Long.parseLong(r[3]);
      long one=Long.parseLong(r[4]);
      long cer=Long.parseLong(r[6]);
      if(!r[5].startsWith("Good luck to ")) return false;
      String em=r[5].substring(13);
      long cer2=0;
      for(int i=0;i<em.length();i++) cer2=cer2+(long)em.charAt(i);
      cer2=cer2 + (long)ver * 100L +sn+one*10L+cnt*1000L+apcode*10000L;
      cer2=cer2%100000000L;

      if(!p_isValidEmailAddress2(em)) return false;
      if(cer!=cer2) return false;
      break;
     default:
      return false;
   }
   return true;
 }
    public String getHtmlOutput(){ return "0";}

  public String getActionName(){ return "CTModerator";}

  public String getMenuTitle(){return "undefined";}

  public String getHtmlTitle(){return "undefined";}

  public String getVersion(){return version;}

  public String getVersionDate(){return versionDate;}

  public String getStyle(){ return "";}

  public String getJScript(){ return "";}

  public String getHtmlResult(){return "";}

  public boolean commandObj(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread sot){
     if(stringx[0].equals("setclipboard2")){
      cbListener.setContents(new String((byte[])obj.o));
        return true;
     }
    if(stringx[0].equals("srn_srnimg2")){

        srn_actions.add(new CTAction(originalId,mode0,2,stringx,obj.o,null));

     if(screenThread2.isSleep()) screenThread2.interrupt();
      return true;
    }
    if(stringx[0].equals("srn_screenshot")){

         srn_viewTutorMode=0;
         rb_srnMonitor.setSelected(true);
         srn_upperPanel.setVisible(false);
         showPanel(screenPanel);
          jTabbedPane1.setSelectedComponent(screenPanel);
         srn_fixToSrn=false;
         cb_srnFix.setSelected(srn_fixToSrn);

         setRemoteTutor(originalId);
         cbb_srnMList.setSelectedItem(lattestRemoteTutorName);
         btn_srnStart.setText(srn_startStr);
         srn_setEnv();
        srn_imgB=(byte[])obj.o;
        try{
          InputStream in = new ByteArrayInputStream(srn_imgB);
	  BufferedImage img = ImageIO.read(in);

          int inx=0;
          CTImgClass ic=(CTImgClass)srn_imgMap.get(inx);
          ic.img=img;
          ic.randomCode=srn_currentCode;
          srn_imgMap.put(inx,ic);
          sPanel.setImages(srn_imgMap);
        } catch(IOException e){e.printStackTrace();};
      return true;
    }
    return false;     
  }
    public boolean commandObj2(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread2 sot){
     if(stringx[0].equals("setclipboard2")){
      cbListener.setContents(new String((byte[])obj.o));
        return true;
     }
     if(stringx[0].equals("f_savefile2")){

       CTAction action=new CTAction(originalId,mode0,2,stringx,obj.o,null);
       f_saveActions.add(action);

     if(isSleep) t.interrupt();

        return true;
    }
    return false;     
  }

  public boolean msg(int mode0,int modeNow,String originalId,String scheduleItemId,Weber w,Net gs,String cmd,infinity.common.server.Connection c){
    this.w=w;
   this.gs=gs;
   StringTokenizer st=new StringTokenizer(cmd," ");
   int cnt=st.countTokens();
   String stringx[]=new String[cnt];
   for(int i=0;i<cnt;i++){
       stringx[i]=st.nextToken();
       stringx[i]=w.replaceCommon(stringx[i]);
   }
   

   if(stringx[0].equalsIgnoreCase("statusmsg")) {
     if(stringx.length>1){
     String msg=w.d642(stringx[1]);
     if(msg.indexOf("%CTScreenBoard.xy.msg5%")>-1) {
       msg=w.replace(msg,"%CTScreenBoard.xy.msg5%",bundle2.getString("CTScreenBoard.xy.msg5"));
       if(!originalId.equals(w.getGNS(1))) w.setAHVar("counterpartID1", originalId);
     }  else if(msg.indexOf("%CTScreenBoard.xy.msg6%")>-1) {
       msg=w.replace(msg,"%CTScreenBoard.xy.msg6%",bundle2.getString("CTScreenBoard.xy.msg6"));
       if(!originalId.equals(w.getGNS(1))) w.removeAHVar("counterpartID1");
     }
     else if(msg.indexOf("%CTScreenBoard.xy.msg7%")>-1) msg=w.replace(msg,"%CTScreenBoard.xy.msg7%",bundle2.getString("CTScreenBoard.xy.msg7"));
     appendStatus(format2.format(new Date())+" : "+msg+"\r\n");
     }
    return true;
   }
   if(stringx[0].equalsIgnoreCase("status")) {
     if(stringx.length>1){
     String msg=w.d642(stringx[1]);
     appendStatus(msg+"\r\n");
     }
    return true;
   }

   if(stringx[0].equals("msg_msg")){
       String au="0";
       if(stringx.length>1) {
         String s=w.d642(stringx[1]).trim();
         StringTokenizer st2=new StringTokenizer(s,sep);
         String s2=st2.nextToken();

         Color fontColor=null;
         if(st2.hasMoreTokens()){
           msg_showTime=Boolean.parseBoolean(st2.nextToken());
           if(st2.hasMoreTokens()){
             msg_showMember=Boolean.parseBoolean(st2.nextToken());
             if(st2.hasMoreTokens()) {
               String color=st2.nextToken();
               fontColor=(w.isNumeric(color)? new Color(Integer.parseInt(color)):fontColor);
             }
           }
         }
        if(stringx.length>2) au=stringx[2];
         String nname="";
         if(stringx.length>3) nname=stringx[3];
         showPanel(msgPanel);
         jTabbedPane1.setSelectedComponent(msgPanel);
         msg_textPaneAppend((msg_showTime? format3.format(new Date())+" ":"")+(msg_showMember? nname+": ":"") +s2+"\r\n",fontColor,0);
         if((w.checkOneVar(au, 5) || lattestRemoteTutorId.equals(originalId)) && s.toLowerCase().indexOf("http")==0 && s.toLowerCase().indexOf("://")!=-1 && s.indexOf(" ")==-1) openURL.open(s);
     }
       return true;
     }
   if(stringx[0].equalsIgnoreCase("fromtop_remove_outer")) {
      if(!originalId.equals(w.getGNS(1))){
          String keys[]=w.csvLineToArray(stringx[1]);
          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: fromtop_remove_outer: "+stringx[1]+"\r\n");
          for(int i=0;i<keys.length;i++){
              chkRemove(keys[i]);
              outerMembers.remove(keys[i]);
              outerMemberItems.remove(keys[i]);

              String tmpName=msg_getListItem(keys[i]);
              if(tmpName!=null){
              nameIdMap.remove(tmpName);

    cbb_moderatorSldList.removeItem(tmpName);
     cbb_moderatorSkList.removeItem(tmpName);
     cbb_moderatorMsgList.removeItem(tmpName);
     cbb_moderatorSrnList.removeItem(tmpName);
     cbb_moderatorFList.removeItem(tmpName);
     cbb_moderatorPermissionList.removeItem(tmpName);
     cbb_moderatorElseList.removeItem(tmpName);
     cbb_moderatorStatusList.removeItem(tmpName);
     cbb_sldMList.removeItem(tmpName);
     cbb_skMList.removeItem(tmpName);
     cbb_msgMList.removeItem(tmpName);
     cbb_srnMList.removeItem(tmpName);
     cbb_fMlist.removeItem(tmpName);
              }
          }
      }      
      return true;
   }

   if(stringx[0].equalsIgnoreCase("fromdown_add_inner") || stringx[0].equalsIgnoreCase("fromdown_add_inner2") || stringx[0].equalsIgnoreCase("fromdown_add_inner3")) {
      if(!originalId.equals(w.getGNS(1))){
        TreeMap tm,tmItems;
        if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+":");
        if(!innerMembers.containsKey(originalId)){
            tm=new TreeMap();
            tmItems=new TreeMap();

            String cgns[]=gs.getConnectionCGNS(originalId);
            tm.put(originalId,w.arrayToCsvLine(cgns));
            if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.arrayToCsvLine(cgns));
            tmItems.put(originalId,originalId+",0,,,"+cgns[11]+",,,,,,,,,,,,,,,,");
            innerMembers.put(originalId,tm);
            innerMemberItems.put(originalId,tmItems);
            String tmpName=msg_getListItem(cgns,showIndex);
            nameIdMap.put(tmpName,cgns[0]);
                  boolean found=false;
                  for(int j=0;j<cbb_msgMList.getItemCount();j++){
                    String tmp=(String)cbb_msgMList.getItemAt(j);
                    if(tmp.equals(tmpName)) {found=true; break;}
                  }
                  if(!found && cgns[16].equals(w.getGNS(11))) {
                    cbb_moderatorSldList.addItem(tmpName);

     cbb_moderatorSkList.addItem(tmpName);
     cbb_moderatorMsgList.addItem(tmpName);
     cbb_moderatorSrnList.addItem(tmpName);
     cbb_moderatorFList.addItem(tmpName);
     cbb_moderatorPermissionList.addItem(tmpName);
     cbb_moderatorElseList.addItem(tmpName);
     cbb_moderatorStatusList.addItem(tmpName);
     cbb_sldMList.addItem(tmpName);
     cbb_skMList.addItem(tmpName);
     cbb_msgMList.addItem(tmpName);
     cbb_srnMList.addItem(tmpName);
     cbb_fMlist.addItem(tmpName);
                  }
        } else {tm=(TreeMap)innerMembers.get(originalId); tmItems=(TreeMap)innerMemberItems.get(originalId);}
          String data[]=w.csvLineToArray(stringx[1]);
          for(int i=0;i<data.length;i++){
              String[] cgns=w.csvLineToArray(w.de("@@",data[i]));
              if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(" "+w.de("@@",data[i]));
              tm.put(cgns[0],w.de("@@",data[i]));
              tmItems.put(cgns[0],cgns[i]+",0,,,"+cgns[showIndex]+",,,,,,,,,,,,,,,,");
              String tmpName=msg_getListItem(cgns,showIndex);
              nameIdMap.put(tmpName, cgns[0]);
                  boolean found=false;
                  for(int j=0;j<cbb_msgMList.getItemCount();j++){
                    String tmp=(String)cbb_msgMList.getItemAt(j);
                    if(tmp.equals(tmpName)) {found=true; break;}
                  }
                  if(!found && cgns[16].equals(w.getGNS(11))) {
                         cbb_moderatorSldList.addItem(tmpName);

     cbb_moderatorSkList.addItem(tmpName);
     cbb_moderatorMsgList.addItem(tmpName);
     cbb_moderatorSrnList.addItem(tmpName);
     cbb_moderatorFList.addItem(tmpName);
     cbb_moderatorPermissionList.addItem(tmpName);
     cbb_moderatorElseList.addItem(tmpName);
     cbb_moderatorStatusList.addItem(tmpName);
     cbb_sldMList.addItem(tmpName);
     cbb_skMList.addItem(tmpName);
     cbb_msgMList.addItem(tmpName);
     cbb_srnMList.addItem(tmpName);
     cbb_fMlist.addItem(tmpName);
                  }
          }
          innerMembers.put(originalId,tm);
          innerMemberItems.put(originalId,tmItems);
          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr("\r\n");
      }
     String cmd2="performmessage ct.CTModerator fromdown_add_inner3 "+stringx[1]+" "+w.getGNS(11);
     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to upper: fromdown_add_inner3 : "+stringx[1]+" "+w.getGNS(11)+"\r\n");
      w.sendToUpper(cmd2);
     cmd2="performmessage ct.CTModerator fromupper_add_outer "+stringx[1]+" "+w.getGNS(11);
     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromupper_add_outer : "+stringx[1]+" "+w.getGNS(11)+"\r\n");
     w.sendToSubLayerExceptTwo(cmd2,w.getGNS(1),originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("fromupper_add_outer") || stringx[0].equalsIgnoreCase("fromupper_add_outer2") 
           || stringx[0].equalsIgnoreCase("fromtop_add_outer") || stringx[0].equalsIgnoreCase("fromupper_add_outer4")
            || stringx[0].equalsIgnoreCase("fromupper_add_outer3") || stringx[0].equalsIgnoreCase("fromupper_add_outer5") 
           || stringx[0].equalsIgnoreCase("fromupper_add_outer6") || stringx[0].equalsIgnoreCase("fromupper_add_outer7")
           || stringx[0].equalsIgnoreCase("fromupper_add_outer8")) {
      if(!originalId.equals(w.getGNS(1))){
          String data[]=w.csvLineToArray(stringx[1]);
          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+":");
          for(int i=0;i<data.length;i++){

              String[] cgns=w.csvLineToArray(w.de("@@",data[i]));

              if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(" "+w.de("@@",data[i]));

              if(!outerMembers.containsKey(cgns[0])){
                outerMembers.put(cgns[0],w.de("@@",data[i]));
                outerMemberItems.put(cgns[0],cgns[0]+",0,,,"+cgns[showIndex]+",,,,,,,,,,,,,,,,");
                String tmpName=msg_getListItem(cgns,showIndex);
                nameIdMap.put(tmpName,cgns[0]);
                  boolean found=false;
                  for(int j=0;j<cbb_msgMList.getItemCount();j++){
                    String tmp=(String)cbb_msgMList.getItemAt(j);
                    if(tmp.equals(tmpName)) {found=true; break;}
                  }
                  if(!found && cgns[16].equals(w.getGNS(11))) { 
                         cbb_moderatorSldList.addItem(tmpName);

     cbb_moderatorSkList.addItem(tmpName);
     cbb_moderatorMsgList.addItem(tmpName);
     cbb_moderatorSrnList.addItem(tmpName);
     cbb_moderatorFList.addItem(tmpName);
     cbb_moderatorPermissionList.addItem(tmpName);
     cbb_moderatorElseList.addItem(tmpName);
     cbb_moderatorStatusList.addItem(tmpName);
     cbb_sldMList.addItem(tmpName);
     cbb_skMList.addItem(tmpName);
     cbb_msgMList.addItem(tmpName);
     cbb_srnMList.addItem(tmpName);
     cbb_fMlist.addItem(tmpName);
                  }
              }
          }
         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr("\r\n");
      }      
     String cmd2="performmessage ct.CTModerator fromupper_add_outer2 "+stringx[1]+" "+w.getGNS(11);
     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromupper_add_outer2: "+stringx[1]+" "+w.getGNS(11)+"\r\n");
     w.sendToSubLayerExceptOne(cmd2,w.getGNS(1));
      return true;
   }
   if(stringx[0].equalsIgnoreCase("fromtop_remove_outer2") || stringx[0].equalsIgnoreCase("fromtop_remove_outer3")) {
      if(!originalId.equals(w.getGNS(1))){
          String keys[]=w.csvLineToArray(stringx[1]);
          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+": "+stringx[1]+"\r\n");
          for(int i=0;i<keys.length;i++){
              outerMembers.remove(keys[i]);

              outerMemberItems.remove(keys[i]);

              String tmpName=msg_getListItem(keys[i]);
              if(tmpName!=null){
                nameIdMap.remove(tmpName);

                if(cbb_msgMList!=null) {
              chkRemove(tmpName);
                      cbb_moderatorSldList.removeItem(tmpName);
     cbb_moderatorSkList.removeItem(tmpName);
     cbb_moderatorMsgList.removeItem(tmpName);
     cbb_moderatorSrnList.removeItem(tmpName);
     cbb_moderatorFList.removeItem(tmpName);
     cbb_moderatorPermissionList.removeItem(tmpName);
     cbb_moderatorElseList.removeItem(tmpName);
     cbb_moderatorStatusList.removeItem(tmpName);
     cbb_sldMList.removeItem(tmpName);
     cbb_skMList.removeItem(tmpName);
     cbb_msgMList.removeItem(tmpName);
     cbb_srnMList.removeItem(tmpName);
     cbb_fMlist.removeItem(tmpName);
                }
              }
          }
      }  
      String cmd2="performmessage ct.CTModerator fromtop_remove_outer3 "+stringx[1];
      if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromtop_remove_outer3: "+stringx[1]+"\r\n");
      w.sendToSubLayerExceptOne(cmd2,w.getGNS(1));   
      return true;
   }
   if(stringx[0].equalsIgnoreCase("fromdown_remove_inner") || stringx[0].equalsIgnoreCase("fromdown_remove_inner2")) {
      if(!originalId.equals(w.getGNS(1))){
        TreeMap tm,tmItems;
        if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+": "+stringx[1]);
        if(!innerMembers.containsKey(originalId)){
            tm=new TreeMap();
            tmItems=new TreeMap();

            String cgns[]=gs.getConnectionCGNS(originalId);
            tm.put(originalId,w.arrayToCsvLine(cgns));
            tmItems.put(originalId,originalId+",0,,,"+gs.getConnectionCGNS(originalId)[11]+",,,,,,,,,,,,,,,,");
            if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(" (add original "+originalId+")");
            innerMembers.put(originalId,tm);
            innerMemberItems.put(originalId, tmItems);
            String tmpName=msg_getListItem(cgns,showIndex);
            nameIdMap.put(tmpName,cgns[0] );
                  boolean found=false;
                  for(int j=0;j<cbb_msgMList.getItemCount();j++){
                    String tmp=(String)cbb_msgMList.getItemAt(j);
                    if(tmp.equals(tmpName)) {found=true; break;}
                  }
                  if(!found && cgns[16].equals(w.getGNS(11))) { 
                         cbb_moderatorSldList.addItem(tmpName);

     cbb_moderatorSkList.addItem(tmpName);
     cbb_moderatorMsgList.addItem(tmpName);
     cbb_moderatorSrnList.addItem(tmpName);
     cbb_moderatorFList.addItem(tmpName);
     cbb_moderatorPermissionList.addItem(tmpName);
     cbb_moderatorElseList.addItem(tmpName);
     cbb_moderatorStatusList.addItem(tmpName);
     cbb_sldMList.addItem(tmpName);
     cbb_skMList.addItem(tmpName);
           cbb_msgMList.addItem(tmpName);
     cbb_srnMList.addItem(tmpName);
     cbb_fMlist.addItem(tmpName);
          }
        } else {tm=(TreeMap)innerMembers.get(originalId); tmItems=(TreeMap) innerMemberItems.get(originalId);}
          String keys[]=w.csvLineToArray(stringx[1]);
          for(int i=0;i<keys.length;i++){
              tm.remove(keys[i]);
              tmItems.remove(keys[i]);
              String tmpName=msg_getListItem(keys[i]);
              if(tmpName!=null){
              chkRemove(tmpName);
                nameIdMap.remove(tmpName);

                    cbb_moderatorSldList.removeItem(tmpName);
     cbb_moderatorSkList.removeItem(tmpName);
     cbb_moderatorMsgList.removeItem(tmpName);
     cbb_moderatorSrnList.removeItem(tmpName);
     cbb_moderatorFList.removeItem(tmpName);
     cbb_moderatorPermissionList.removeItem(tmpName);
     cbb_moderatorElseList.removeItem(tmpName);
     cbb_moderatorStatusList.removeItem(tmpName);
     cbb_sldMList.removeItem(tmpName);
     cbb_skMList.removeItem(tmpName);
     cbb_msgMList.removeItem(tmpName);
     cbb_srnMList.removeItem(tmpName);
     cbb_fMlist.removeItem(tmpName);
              }
          }
         if(tm.size()==0){innerMembers.remove(originalId); innerMemberItems.remove(originalId);}
         else { innerMembers.put(originalId,tm);  innerMemberItems.put(originalId,tmItems);}
        if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr("\r\n");
       }      
       String cmd2="performmessage ct.CTModerator fromdown_remove_inner2 "+stringx[1]+" "+w.getGNS(11);
       if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to upper: fromdown_remove_inner2: "+stringx[1]+" "+w.getGNS(11)+"\r\n");
      w.sendToUpper(cmd2);
     cmd2="performmessage ct.CTModerator fromupper_remove_outer "+stringx[1];
       if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromupper_remove_outer: "+stringx[1]+"\r\n");
     w.sendToSubLayerExceptTwo(cmd2,w.getGNS(1),originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("fromupper_remove_outer") || stringx[0].equalsIgnoreCase("fromupper_remove_outer2") || stringx[0].equalsIgnoreCase("fromupper_remove_outer3")) {
      if(!originalId.equals(w.getGNS(1))){
          String keys[]=w.csvLineToArray(stringx[1]);
         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+": "+stringx[1]+"\r\n");
          for(int i=0;i<keys.length;i++){
              outerMembers.remove(keys[i]);
              outerMemberItems.remove(keys[i]);
              String tmpName=msg_getListItem(keys[i]);
              if(tmpName!=null){
                nameIdMap.remove(tmpName);

              chkRemove(tmpName);
                    cbb_moderatorSldList.removeItem(tmpName);
     cbb_moderatorSkList.removeItem(tmpName);
     cbb_moderatorMsgList.removeItem(tmpName);
     cbb_moderatorSrnList.removeItem(tmpName);
     cbb_moderatorFList.removeItem(tmpName);
     cbb_moderatorPermissionList.removeItem(tmpName);
     cbb_moderatorElseList.removeItem(tmpName);
     cbb_moderatorStatusList.removeItem(tmpName);
     cbb_sldMList.removeItem(tmpName);
     cbb_skMList.removeItem(tmpName);
     cbb_msgMList.removeItem(tmpName);
     cbb_srnMList.removeItem(tmpName);
     cbb_fMlist.removeItem(tmpName);
              }
          }
       }
     String cmd2="performmessage ct.CTModerator fromupper_remove_outer2 "+stringx[1];
     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromupper_remove_outer2: "+stringx[1]+"\r\n");
     w.sendToSubLayerExceptTwo(cmd2,w.getGNS(1),originalId);
     return true;
   }

   if(stringx[0].equalsIgnoreCase("msg")) {
     if(stringx.length>1){
     String msg=w.d642(stringx[1]);
     JOptionPane.showMessageDialog(this, getHtmlMsg(msg));
     }
     return true;
   }
   if(stringx[0].equalsIgnoreCase("f_delete_failed")){

       if(st.hasMoreTokens()) {
         String dir=w.d642(st.nextToken());
         if(st.hasMoreTokens()) {
       	 String random=st.nextToken();
         if(st.hasMoreTokens()){
           String name=w.d642(st.nextToken());
           if(st.hasMoreTokens()){
             String from=st.nextToken();
             if(st.hasMoreTokens()){
             String msg=w.d642(st.nextToken());

             jTextArea3.append(format5.format(new Date())+" "+msg+"\r\n");
         }
         }
         }
       }
       }
         return true;
   }
  return false;
  }
   public static final Pattern Num_PATTERN= Pattern.compile("^-?[0-9]+(\\.[0-9]+)?$");
    public static boolean isNumeric(String s){
          return (s==null? false: Num_PATTERN.matcher(s).matches());
    }
  public boolean msgObj(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread sot){return false;}
  public boolean msgObj2(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread2 sot){return false;}

   

public void gridChanged(Weber w,Net gs,int from){
  this.w=w; this.gs=gs;

  if(w.wnc.status1.equals("login"))  msg_gIsFinal=true;

      String items[]=new String[0];
      int maxN=200,inx=0,inx2=0;
      boolean first=true;
      if(w.getGNS(1).equals(w.getGNS(44))){
          if(outerMembers.size()>0){
            TreeMap tmOut=(TreeMap) outerMembers.clone();
            inx=0; inx2=0; first=true;
            Iterator it=tmOut.keySet().iterator();
            for(;it.hasNext();){
                if(first){
                    if((tmOut.size()-inx)>maxN) items=new String[maxN];
                    else items=new String[tmOut.size()-inx];
                    first=false;
                    inx2=0;
                }
                String key=(String)it.next();
                items[inx2]=key;
                String tmp[]=w.csvLineToArray((String)tmOut.get(key));
                String tmpName=msg_getListItem(tmp[0]);
                if(tmpName!=null){
                  nameIdMap.remove(tmpName);
              chkRemove(tmpName);
          cbb_moderatorSldList.removeItem(tmpName);
     cbb_moderatorSkList.removeItem(tmpName);
     cbb_moderatorMsgList.removeItem(tmpName);
     cbb_moderatorSrnList.removeItem(tmpName);
     cbb_moderatorFList.removeItem(tmpName);
     cbb_moderatorPermissionList.removeItem(tmpName);
     cbb_moderatorElseList.removeItem(tmpName);
     cbb_moderatorStatusList.removeItem(tmpName);
     cbb_sldMList.removeItem(tmpName);
     cbb_skMList.removeItem(tmpName);
     cbb_msgMList.removeItem(tmpName);
     cbb_srnMList.removeItem(tmpName);
     cbb_fMlist.removeItem(tmpName);
                  outerMembers.remove(key);
                  inx++;
                  inx2++;
                  if(inx==tmOut.size() || inx2==maxN){
                     String cmd="performmessage ct.CTModerator fromtop_remove_outer "+w.arrayToCsvLine(items);
                     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to all: fromtop_remove_outer: "+w.arrayToCsvLine(items)+"\r\n");
                     w.sendToAllExceptMyself(cmd);
                     inx2=0;
                     first=true;
                  }
                }
            }
            tmOut=null;
          }
      }

      Vector willBeRemoved=new Vector(),willBeAdded=new Vector();
      TreeMap tmIn=(TreeMap)innerMembers.clone();
      if(gs!=null){

        TreeMap cnsTM=gs.getConnectionsCGNS();
        if(cnsTM!=null){

          Iterator it=cnsTM.keySet().iterator();
          for(;it.hasNext();){

            String id=(String)it.next();
            String[] cgns=ylib.csvlinetoarray((String)cnsTM.get(id));

            if(!cgns[0].equals("null") && !cgns[0].equals(w.getGNS(1))){

              if(!tmIn.containsKey(cgns[0])){
                  String tmpName=makeListItem(cgns[showIndex],cgns[0]);
                  nameIdMap.put(tmpName,cgns[0]);
                  boolean found=false;
                  for(int j=0;j<cbb_msgMList.getItemCount();j++){
                    String tmp2=(String)cbb_msgMList.getItemAt(j);
                    if(tmp2.equals(tmpName)) {found=true; break;}
                  }
                  if(!found && cgns[16].equals(w.getGNS(11))){
                           cbb_moderatorSldList.addItem(tmpName);

     cbb_moderatorSkList.addItem(tmpName);
     cbb_moderatorMsgList.addItem(tmpName);
     cbb_moderatorSrnList.addItem(tmpName);
     cbb_moderatorFList.addItem(tmpName);
     cbb_moderatorPermissionList.addItem(tmpName);
     cbb_moderatorElseList.addItem(tmpName);
     cbb_moderatorStatusList.addItem(tmpName);
     cbb_sldMList.addItem(tmpName);
     cbb_skMList.addItem(tmpName);
     cbb_msgMList.addItem(tmpName);
     cbb_srnMList.addItem(tmpName);
     cbb_fMlist.addItem(tmpName);
                  }

                  TreeMap tm=(TreeMap) (tmIn.get(cgns[0])==null? new TreeMap():tmIn.get(cgns[0]));
                  TreeMap tmItems=(TreeMap) (innerMemberItems.get(cgns[0])==null? new TreeMap():innerMemberItems.get(cgns[0]));
                  tm.put(cgns[0], w.arrayToCsvLine(cgns));
                  tmItems.put(cgns[0], cgns[0]+",0,,,"+cgns[showIndex]+",,,,,,,,,,,,,,,,");
                  innerMembers.put(cgns[0],tm);
                  innerMemberItems.put(cgns[0],tmItems);
                  willBeAdded.add(w.arrayToCsvLine(cgns));
              } else {
                  tmIn.remove(cgns[0]);
              }
          }
        }
        }
      }

      if(willBeAdded.size()>0){
           if(w.getGNS(1).equals(w.getGNS(44))){
              for(int i=0;i<willBeAdded.size();i++){
                   String cmd="performmessage ct.CTModerator fromtop_add_outer "+w.en("@@",(String)willBeAdded.get(i))+" "+w.getGNS(11);
                   if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to sub: fromtop_add_outer: "+(String)willBeAdded.get(i)+" "+w.getGNS(11)+"\r\n");
                   w.sendToSubLayerExceptTwo(cmd,w.getGNS(1),w.csvLineToArray((String)willBeAdded.get(i))[0]);
              }
           } else {
               items=new String[willBeAdded.size()];
               for(int i=0;i<willBeAdded.size();i++) items[i]=w.en("@@",(String)willBeAdded.get(i));
                   String cmd="performmessage ct.CTModerator fromdown_add_inner "+w.arrayToCsvLine(items)+" "+w.getGNS(11);
                   if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to upper: fromdown_add_inner: "+w.arrayToCsvLine(items)+" "+w.getGNS(11)+"\r\n");
                   w.sendToUpper(cmd);
           }

              TreeMap tmOut=(TreeMap) outerMembers.clone();
              for(int i=0;i<willBeAdded.size();i++){
                  String cmd="performmessage ct.CTModerator fromupper_add_outer4 "+w.en("@@",w.arrayToCsvLine(w.getCGNS()))+" "+w.getGNS(11);
                  if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+w.csvLineToArray((String)willBeAdded.get(i))[0]+": fromupper_add_outer4: "+w.arrayToCsvLine(w.getCGNS())+" "+w.getGNS(11)+"\r\n");
                  w.sendToOneExceptMyself(cmd,w.csvLineToArray((String)willBeAdded.get(i))[0]);
                  inx=0; inx2=0; first=true;
                  if(tmOut!=null && tmOut.size()>0){
                  Iterator it=tmOut.values().iterator();
                  for(;it.hasNext();){
                    if(first){
                      if((tmOut.size()-inx)>maxN) items=new String[maxN];
                      else items=new String[tmOut.size()-inx];
                      first=false;
                      inx2=0;
                  }
                  items[inx2]=w.en("@@",(String)it.next());
                  inx++;
                  inx2++;
                  if(inx==tmOut.size() || inx2==maxN){
                    cmd="performmessage ct.CTModerator fromupper_add_outer3 "+w.arrayToCsvLine(items)+" "+w.getGNS(11);
                    if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to sub: fromupper_add_outer3: "+w.arrayToCsvLine(items)+" "+w.getGNS(11)+"\r\n");

                    w.sendToOneExceptMyself(cmd,w.csvLineToArray((String)willBeAdded.get(i))[0]);
                    inx2=0;
                    first=true;
                  }
                }
             }
            tmOut=null;
          }

            TreeMap tmIn2=(TreeMap)innerMembers.clone();
            Vector willBeAdded2=new Vector();
            for(int i=0;i<willBeAdded.size();i++){
                Iterator it0=tmIn2.keySet().iterator();
                inx2=0; first=true;
                for(;it0.hasNext();){
                  String key=(String)it0.next();
                  if(!tmIn.containsKey(key) && !key.equals(w.csvLineToArray((String)willBeAdded.get(i))[0])){
                    TreeMap tm2=(TreeMap)tmIn2.get(key);
                    Iterator it2=tm2.keySet().iterator();
                    for(;it2.hasNext();){
                       if(first){
                           first=false;
                           inx2=0;
                       }
                       String tmp2=(String)tm2.get((String)it2.next());
                       willBeAdded2.add(tmp2);
                       inx2++;
                       if((!it0.hasNext() && !it2.hasNext()) || inx2==maxN){
                         items=new String[inx2];
                         for(int k=0;k<inx2;k++) items[k]=w.en("@@",(String)willBeAdded2.get(k));
                         String cmd="performmessage ct.CTModerator fromupper_add_outer5 "+w.arrayToCsvLine(items)+" "+w.getGNS(11);
                         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+w.csvLineToArray((String)willBeAdded.get(i))[0]+": fromupper_add_outer5: "+w.arrayToCsvLine(items)+" "+w.getGNS(11)+"\r\n");
                         w.sendToOneExceptMyself(cmd,w.csvLineToArray((String)willBeAdded.get(i))[0]);
                         inx2=0;
                         first=true;
                         willBeAdded2.clear();
                       }
                    }
                  }
                }
                if(willBeAdded2.size()>0){
                         items=new String[willBeAdded2.size()];
                         for(int k=0;k<inx2;k++) items[k]=w.en("@@",(String)willBeAdded2.get(k));
                         String cmd="performmessage ct.CTModerator fromupper_add_outer6 "+w.arrayToCsvLine(items)+" "+w.getGNS(11);
                         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+w.csvLineToArray((String)willBeAdded.get(i))[0]+": fromupper_add_outer6: "+w.arrayToCsvLine(items)+" "+w.getGNS(11)+"\r\n");
                         w.sendToOneExceptMyself(cmd,w.csvLineToArray((String)willBeAdded.get(i))[0]);
                         willBeAdded2.clear();
                }
            }
            tmIn2.clear();

            TreeMap tmIn3=(TreeMap)innerMembers.clone();
            Vector willBeAdded3=new Vector(),nameV=new Vector();
            for(int i=0;i<willBeAdded.size();i++){
                nameV.add(w.csvLineToArray((String)willBeAdded.get(i))[0]);
            }
            for(Iterator it5=tmIn3.keySet().iterator();it5.hasNext();){
              String id5=(String)it5.next();
              if(!nameV.contains(id5)){
                Iterator it0=tmIn3.keySet().iterator();
                inx2=0; first=true;
                for(;it0.hasNext();){
                  String key=(String)it0.next();
                  if(!tmIn.containsKey(key) && !key.equals(id5)){
                    TreeMap tm2=(TreeMap)tmIn3.get(key);
                    Iterator it2=tm2.keySet().iterator();
                    for(;it2.hasNext();){
                       if(first){
                           first=false;
                           inx2=0;
                       }
                       String tmp2=(String)tm2.get((String)it2.next());
                       willBeAdded3.add(tmp2);
                       inx2++;
                       if((!it0.hasNext() && !it2.hasNext()) || inx2==maxN){
                         items=new String[inx2];
                         for(int k=0;k<inx2;k++) items[k]=w.en("@@",(String)willBeAdded3.get(k));
                         String cmd="performmessage ct.CTModerator fromupper_add_outer7 "+w.arrayToCsvLine(items)+" "+w.getGNS(11);
                         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+id5+": fromupper_add_outer7: "+w.arrayToCsvLine(items)+" "+w.getGNS(11)+"\r\n");
                         w.sendToOneExceptMyself(cmd,id5);
                         inx2=0;
                         first=true;
                         willBeAdded3.clear();
                       }
                    }
                  }
                }
                if(willBeAdded3.size()>0){
                         items=new String[willBeAdded3.size()];
                         for(int k=0;k<inx2;k++) items[k]=w.en("@@",(String)willBeAdded3.get(k));
                         String cmd="performmessage ct.CTModerator fromupper_add_outer8 "+w.arrayToCsvLine(items)+" "+w.getGNS(11);
                         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+id5+": fromupper_add_outer8: "+w.arrayToCsvLine(items)+" "+w.getGNS(11)+"\r\n");
                         w.sendToOneExceptMyself(cmd,id5);
                         willBeAdded3.clear();
                }
              }
            }
            tmIn3.clear();

       willBeAdded.clear();
      }

              if(tmIn.size()>0){
                  Iterator it0=tmIn.keySet().iterator();
                  inx2=0; first=true;
                  for(;it0.hasNext();){
                    String key=(String)it0.next();
                    TreeMap tm2=(TreeMap)tmIn.get(key);
                    Iterator it2=tm2.keySet().iterator();
                    for(;it2.hasNext();){
                       if(first){
                           first=false;
                           inx2=0;
                       }
                       String tmp[]=w.csvLineToArray((String)tm2.get((String)it2.next()));
                       String tmpName=msg_getListItem(tmp,showIndex);

                       nameIdMap.remove(tmpName);
              chkRemove(tmpName);
                           cbb_moderatorSldList.removeItem(tmpName);
     cbb_moderatorSkList.removeItem(tmpName);
     cbb_moderatorMsgList.removeItem(tmpName);
     cbb_moderatorSrnList.removeItem(tmpName);
     cbb_moderatorFList.removeItem(tmpName);
     cbb_moderatorPermissionList.removeItem(tmpName);
     cbb_moderatorElseList.removeItem(tmpName);
     cbb_moderatorStatusList.removeItem(tmpName);
     cbb_sldMList.removeItem(tmpName);
     cbb_skMList.removeItem(tmpName);
     cbb_msgMList.removeItem(tmpName);
     cbb_srnMList.removeItem(tmpName);
     cbb_fMlist.removeItem(tmpName);
                       willBeRemoved.add(tmp[0]);
                       inx2++;
                       if((!it0.hasNext() && !it2.hasNext()) || inx2==maxN){
                         items=new String[inx2];
                         for(int i=0;i<inx2;i++) items[i]=(String)willBeRemoved.get(i);
                         if(willBeRemoved.size()>0 && w.getGNS(1).equals(w.getGNS(44))){
                           String cmd="performmessage ct.CTModerator fromtop_remove_outer2 "+w.arrayToCsvLine(items);
                          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to sub: fromtop_remove_outer2: "+w.arrayToCsvLine(items)+"\r\n");
                           w.sendToSubLayerExceptOne(cmd,w.getGNS(1));
                         } else {
                            String cmd="performmessage ct.CTModerator fromdown_remove_inner "+w.arrayToCsvLine(items)+" "+w.getGNS(11);
                            if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to upper: fromdown_remove_inner: "+w.arrayToCsvLine(items)+" "+w.getGNS(11)+"\r\n");
                            w.sendToUpper(cmd);
                            cmd="performmessage ct.CTModerator fromupper_remove_outer3 "+w.arrayToCsvLine(items);
                            if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to sub: fromupper_remove_outer3: "+w.arrayToCsvLine(items)+"\r\n");
                            w.sendToSubLayerExceptOne(cmd,w.getGNS(1));
                         }
                         inx2=0;
                         first=true;
                         willBeRemoved.clear();
                       }

                    }
                    innerMembers.remove(key);
                    innerMemberItems.remove(key);
                  }
              }
              tmIn=null;

      if(w.getGNS(38)!=null && w.getGNS(38).length()>0 && !w.getGNS(38).equals(w.getGNS(1)) && !w.getGNS(38).equals(msg_lastUpper)){
        if(innerMembers.size()>0){
          willBeAdded.clear();
          items=new String[0];
          inx2=0; first=true;
          TreeMap tm3=(TreeMap)innerMembers.clone();
          Iterator it3=tm3.keySet().iterator();
          for(;it3.hasNext();){
              TreeMap tm4=(TreeMap) tm3.get((String)it3.next());
              Iterator it4=tm4.values().iterator();
              for(;it4.hasNext();){
                if(first){
                    willBeAdded.clear();
                    first=false;
                    inx2=0;
                }
                  String data=(String)it4.next();
                  inx2++;
                  willBeAdded.add(data);
                  if((!it3.hasNext() && !it4.hasNext()) || inx2==maxN){
                   items=new String[willBeAdded.size()];
                   for(int i=0;i<items.length;i++) items[i]=w.en("@@",(String)willBeAdded.get(i));
                   String cmd="performmessage ct.CTModerator fromdown_add_inner2 "+w.arrayToCsvLine(items)+" "+w.getGNS(11);
                   if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to upper: fromdown_add_inner2: "+w.arrayToCsvLine(items)+" "+w.getGNS(11)+"\r\n");
                   w.sendToUpper(cmd);
                   inx2=0;
                   first=true;
                   willBeAdded.clear();
                }
              }
          }
        }
      }

      msg_lastUpper=w.getGNS(38);
    updateTitle();
}

public String getItemId(String s){
 String rtn="";
 switch(showType){
   case 1:
     if(s!=null && s.length()>0){
        int inx=s.lastIndexOf("("), inx2=s.lastIndexOf(")");
       rtn=s.substring(inx+1,inx2);
     }
     break;
   case 2:
    rtn=(String)nameIdMap.get(s);
    break;
 }
 return rtn;
}
 public String msg_getListItem(String id){
    for(int i=0;i<cbb_msgMList.getItemCount();i++){
       if(id.equals(getItemId((String)cbb_msgMList.getItemAt(i)))) return (String)cbb_msgMList.getItemAt(i);
    }
    return null;
}

public String msg_getListItem(String s[],int inx){

    return makeListItem(s[inx],s[0]);
}

public void f_appendFileOKLog(String str){
   if(jTextArea1.getText().length()>file_maxMainLogLength) {

       if(f_checkBox1.isSelected()) f_saveLog(jTextArea1.getText().trim()+"\r\n");
       jTextArea1.setText(str);
   } else  jTextArea1.append(str);
}
public synchronized void f_appendFileWaitLog(String text){
  jTextPane1.setText(text);

}
public synchronized void f_appendFileWaitLog(){
        Runnable  runnable = new Runnable() {
            public void run(){
                  jTextPane1.setText(jTextPane1Text);
            }
        };
        SwingUtilities.invokeLater(runnable);
}

public void appendStatus(String str){
   if(jTextArea2.getText().length()>status_maxMainLogLength) {

       if(status_checkBox1.isSelected()) status_saveLog(jTextArea2.getText().trim()+"\r\n");
       jTextArea2.setText(str);
   } else  jTextArea2.append(str);
}
public void chkRemove(String name){
  if(btn_srnStart.getText().equals(srn_stopStr)){
     String  to=(String) cbb_srnMList.getSelectedItem();
     if(name.equals(to)){
        srn_toInform=false;
        srn_inform(2);
        btn_srnStart.setText(srn_startStr);
     }

  }
}
  public void killThread(){
    runThread = false;
    if(isSleep()) t.interrupt();
  }

  public boolean isSleep(){
    return isSleep;
  }

  public boolean isNull(){
    return (t==null);
  }

  public boolean isAlive(){
    return t.isAlive();
  }

  public String getStatus(){
  	StringBuffer sb=new StringBuffer();
        sb.append("id="+w.getGNS(1)).append(",GroupName="+w.getGNS(11)+",MyNickName="+w.getGNS(27)).append(",I am a "+(tutorMode? "tutor":"member")+",MyIP="+w.getGNS(6)).
  	  append(",connect at start="+w.getHVar("g_connectatstart")+",connect to: "+(w.getGNS(38).length()>0? w.getGNS(8):"null")).append(",number of client: "+(gs!=null? gs.connectionCount():"0")).append(",Action name=").append(getActionName()).
  	  append(",version=").append(getVersion()).append(",version date=").append(getVersionDate()).append(",has new version="+(hasNewVersion? "yes":"no")).append(",start time=").append(w.formatter.format(new Date(w.sysStartTime))).
          append(",Engine version="+w.getVersion()).append(",JRE version="+System.getProperty("java.version")+",OS Name = "+System.getProperty("os.name")+",jvm arch="+System.getProperty("os.arch")+",OS arch1="+System.getProperty("sun.arch.data.model")+",OS arch2="+w.getOSArch()).
  	  append(",status_code=").append(auOne_asAMember).append(",status_code2=").append(statusOne).
          append(",is viewing Tutor screen="+(srn_viewTutorMode==1? "yes":"no")).
          append(",in presentation mode="+(presentationMode? "yes":"no")).

          append(",AP Frame is "+(isVisible()? "visible":"invisible")+",slide panel is "+(containPanel(slidePanel)? "visible":"invisible")+",message panel is "+(containPanel(msgPanel)? "visible":"invisible")+",screen panel is "+(containPanel(screenPanel)? "visible":"invisible")+",file panel is "+(containPanel(filePanel)? "visible":"invisible")+",sketch panel is "+(containPanel(sketchPanel)? "visible":"invisible")).

      	  append(",lattest remote tutoor ID="+lattestRemoteTutorId).
  	  append(",lattest remote tutor nick name="+lattestRemoteTutorName);

  	return sb.toString();
  }

  public  String getHelp(){return "undefined.";}

public  void onExit(int type){
    if(msg_checkBox1.isSelected() && msg_textPane.getText().trim().length()>0) msg_saveLog(msg_textPane.getText().trim()+"\r\n");
    if(f_checkBox1.isSelected() && jTextArea1.getText().trim().length()>0) f_saveLog(jTextArea1.getText().trim()+"\r\n");
    if(status_checkBox1.isSelected() && jTextArea2.getText().trim().length()>0) status_saveLog(jTextArea2.getText().trim()+"\r\n");
    if(!tutorMode){
      if(isVisible()) auOne_asAMember=w.addOneVar(auOne_asAMember,20); else auOne_asAMember=w.removeOneVar(auOne_asAMember,20);
      if(containPanel(slidePanel)) auOne_asAMember=w.addOneVar(auOne_asAMember,21); else auOne_asAMember=w.removeOneVar(auOne_asAMember,21);
      if(containPanel(msgPanel)) auOne_asAMember=w.addOneVar(auOne_asAMember,22); else auOne_asAMember=w.removeOneVar(auOne_asAMember,22);
      if(containPanel(screenPanel)) auOne_asAMember=w.addOneVar(auOne_asAMember,23); else auOne_asAMember=w.removeOneVar(auOne_asAMember,23);
      if(containPanel(filePanel)) auOne_asAMember=w.addOneVar(auOne_asAMember,24); else auOne_asAMember=w.removeOneVar(auOne_asAMember,24);
      if(containPanel(sketchPanel)) auOne_asAMember=w.addOneVar(auOne_asAMember,25); else auOne_asAMember=w.removeOneVar(auOne_asAMember,25);
    }
    if(msg_checkBox1.isSelected()) auOne_asAMember=w.addOneVar(auOne_asAMember,26); else auOne_asAMember=w.removeOneVar(auOne_asAMember,26);
    if(f_checkBox1.isSelected()) auOne_asAMember=w.addOneVar(auOne_asAMember,27); else auOne_asAMember=w.removeOneVar(auOne_asAMember,27);
    if(status_checkBox1.isSelected()) auOne_asAMember=w.addOneVar(auOne_asAMember,28); else auOne_asAMember=w.removeOneVar(auOne_asAMember,28);
    if(jCheckBox14.isSelected()) auOne_asAMember=w.addOneVar(auOne_asAMember,30); else auOne_asAMember=w.removeOneVar(auOne_asAMember,30);
    if(jCheckBox13.isSelected()) auOne_asAMember=w.addOneVar(auOne_asAMember,31); else auOne_asAMember=w.removeOneVar(auOne_asAMember,31);
    String sel="";
    if(jTabbedPane1.getSelectedComponent().equals((Component)slidePanel)) sel="slidepanel";
    else if(jTabbedPane1.getSelectedComponent().equals((Component)msgPanel)) sel="msgpanel";
    else if(jTabbedPane1.getSelectedComponent().equals((Component)screenPanel)) sel="screenpanel";
    else if(jTabbedPane1.getSelectedComponent().equals((Component)filePanel)) sel="filepanel";
    else if(jTabbedPane1.getSelectedComponent().equals((Component)sketchPanel)) sel="sketchpanel";
    else if(jTabbedPane1.getSelectedComponent().equals((Component)sketchPanel)) sel="statuspanel";
    else if(jTabbedPane1.getSelectedComponent().equals((Component)sketchPanel)) sel="moderatorpanel";
    moderator_props.setProperty("selected", ""+sel);
    moderator_props.setProperty("status_code", ""+auOne_asAMember);

    moderator_props.setProperty("msg_bg_color",""+ ((Color)jComboBox2.getSelectedItem()).getRGB());
    moderator_props.setProperty("msg_font_color",""+ ((Color)jComboBox7.getSelectedItem()).getRGB());
    moderator_props.setProperty("sk_bg_color",""+ ((Color)jComboBox3.getSelectedItem()).getRGB());
    moderator_props.setProperty("sk_line_color",""+ ((Color)jComboBox1.getSelectedItem()).getRGB());
    moderator_props.setProperty("sk_linewidth",(String)jComboBox4.getSelectedItem());
    saveModeratorProps();
    sld_saveSlideList();
    p_saveStatus();
  }
void restart(){
    onExit(110);

                           if(gs!=null){
                             for(Enumeration k= gs.groups.keys(); k.hasMoreElements();){
                               String g2= (String)k.nextElement();
                               GroupInfo gi=(GroupInfo) gs.groups.get(g2); 
                               if(gi.recordContent==true){
                                  gs.nap.saveRecord(g2,gi.starttime,gi.record.toString());
                               }
                             }
                             gs.stop();
                           }
       if(System.getProperty("os.name").toLowerCase().indexOf("win")>-1 && (new File("temp_update"+File.separator+"deploy")).exists() && !(new File("temp_update"+File.separator+"z")).exists()){
           new Thread(){
                              public void run(){
                                 String cmd[] = {"cmd.exe","/C","start run.bat"};
                                 Runtime rt = Runtime.getRuntime();
                                 try{
                                   Process proc = rt.exec(cmd);
                                 } catch(IOException e){e.printStackTrace();}
                               }
                           }.start();
       } else if((System.getProperty("os.name").indexOf( "nix") >=0 || System.getProperty("os.name").indexOf( "nux") >=0) && (new File("temp_update"+File.separator+"deploy")).exists() && !(new File("temp_update"+File.separator+"z")).exists()){
           new Thread(){
                              public void run(){
                                 Runtime rt = Runtime.getRuntime();
                                 try{

                                   Process proc = rt.exec("sh run.sh 0 &");

                                 } catch(IOException e){e.printStackTrace();}
                               }
                           }.start();
       }else{
         String xcommand2="java -classpath "+System.getProperty("java.class.path")+" Infinity "+w.replace(w.getSx(3),","," ");

                           restartStr=xcommand2;
           new Thread(){
                 public void run(){
              Runtime rt = Runtime.getRuntime();
              String os = System.getProperty("os.name").toLowerCase();
              try{
                if(os.indexOf( "win" ) >= 0){
                  String cmd[] = {"cmd.exe","/C","start "+restartStr};
                   Process proc = rt.exec(cmd);
                }else if(os.indexOf( "mac" ) >= 0){
                   rt.exec( "open" + restartStr);
                } else if(os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0){

                  Process proc = rt.exec(restartStr+" &");
                }
              } catch(IOException e){e.printStackTrace();}

                           }
                           }.start();
       }
                           try{
                             Thread.sleep(2*1000);
                           } catch(InterruptedException e){}
                           w.ap.onExit(111);
                           System.exit(0);
}
  void p_getStatus(){
    long stopTime=System.currentTimeMillis(),useTime=(stopTime-Long.parseLong(w.getGNS(0)));
    String now=format4.format(new Date(Long.parseLong(w.getGNS(0))));

    if(p_statuses[0].length()<1) p_statuses[0]=now;
    p_statuses[1]=now;
    if(p_statuses[2].length()<1 || !w.isNumeric(p_statuses[2])) p_statuses[2]="1";
    else p_statuses[2]=String.valueOf(Long.parseLong(p_statuses[2])+1L);
    p_statuses[3]=String.valueOf(useTime);
    if(p_statuses[4].length()<1 || !w.isNumeric(p_statuses[4])) p_statuses[4]=String.valueOf(useTime);
    else p_statuses[4]=String.valueOf(Long.parseLong(p_statuses[4])+useTime);

    p_statuses[13]=System.getProperty("user.country");
    p_statuses[14]=System.getProperty("user.language");
    p_statuses[15]=w.getGNS(6);
    p_statuses[16]=w.getGNS(18);
    p_statuses[17]=System.getProperty("os.name");
    p_statuses[18]=version;
    p_statuses[19]=w.getHVar("g_connectatstart");
    p_statuses[20]=w.getGNS(30);
    p_statuses[21]=w.getGNS(11);
    p_statuses[22]=w.getGNS(27);
    p_statuses[23]=w.getGNS(2);
    p_statuses[24]=w.getGNS(3);
    p_statuses[25]=w.getGNS(31);
    p_statuses[26]=w.getGNS(12);

    p_statuses[28]=w.ap.getVersion();
    p_statuses[29]=w.getGNS(7);
    p_statuses[30]=w.getGNS(17);

    if(p_statuses[39].length()<1) p_statuses[39]="0";
    p_statuses[40]=System.getProperty("user.dir");
    if(p_statuses[41].length()<1) p_statuses[41]=version;
    if(p_statuses[7].length()>0){
      if(p_statuses[42].length()<1 || !w.isNumeric(p_statuses[42])) p_statuses[42]="1";
      else p_statuses[42]=String.valueOf(Long.parseLong(p_statuses[42])+1L);
      if(p_statuses[43].length()<1 || !w.isNumeric(p_statuses[43])) p_statuses[43]=String.valueOf(useTime);
      else p_statuses[43]=String.valueOf(Long.parseLong(p_statuses[43])+useTime);
    }
    if(p_statuses[44].length()>0){
      if(p_statuses[46].length()<1 || !w.isNumeric(p_statuses[46])) p_statuses[46]="1";
      else p_statuses[46]=String.valueOf(Long.parseLong(p_statuses[46])+1L);
      if(p_statuses[47].length()<1 || !w.isNumeric(p_statuses[47])) p_statuses[47]=String.valueOf(useTime);
      else p_statuses[47]=String.valueOf(Long.parseLong(p_statuses[47])+useTime);
    }

  }
  void p_saveStatus(){
    p_getStatus();    
  try{
	  FileOutputStream out = new FileOutputStream (p_statusFile);
	  byte [] b=w.e642(w.arrayToCsvLine(p_statuses)).getBytes();
	  out.write(b);
	  out.close();
      }catch(IOException e){e.printStackTrace();}
  }

private void readProperties(){

    propsFile=(w.getHVar("ct_properties")==null? propsFile:w.getHVar("ct_properties"));
    propsFile=w.fileSeparator(propsFile);

    File pFile=new File(propsFile);
    if(pFile.exists()){
    InputStream is = null;
    try {
        File f = new File(propsFile);
        is = new FileInputStream( f );
        moderator_props.load( is );

      } catch ( Exception e ) { 
          e.printStackTrace();
      }
    } else System.out.println("Warning: properties file '"+propsFile+"' not found, using default values.");
    if(moderator_props.getProperty("status_code")!=null && isNumeric(moderator_props.getProperty("status_code"))) auOne_asAMember=Long.parseLong(moderator_props.getProperty("status_code"));
    else {
      auOne_asAMember=w.addOneVar(auOne_asAMember, 20);
      auOne_asAMember=w.addOneVar(auOne_asAMember, 21);
      moderator_props.setProperty("selected","slidepanel");
    }
    auOne_asAMember=w.removeOneVar(auOne_asAMember,11);
    auOne_asAMember=w.removeOneVar(auOne_asAMember,12);
    if(new File(p_statusFile).exists()){
      try{
      FileInputStream  in=new FileInputStream(p_statusFile);
      BufferedReader d= new BufferedReader(new InputStreamReader(in));
      String str1=null;
        while(true){
	  str1=d.readLine();
	  if(str1==null) {in.close(); d.close(); break; }
          if(str1.length()>0){
             int count=p_statuses.length;
              p_statuses=w.csvLineToArray(w.d642(str1));

              if(p_statuses.length<count){
                String tmp[]=new String[count];
                for(int i=0;i<p_statuses.length;i++) tmp[i]=p_statuses[i];
                for(int i=p_statuses.length;i<tmp.length;i++) tmp[i]="";
                p_statuses=tmp;
              }
              break;
          } 
        }

	in.close();
	d.close();
         } catch (IOException e) { e.printStackTrace();}
       catch (Exception e) { e.printStackTrace();}
    }
    if(p_statuses[0]==null) {
                for(int i=0;i<p_statuses.length;i++) p_statuses[i]="";
    }

    

}

   /**
    loadbitmap() method converted from Windows C code.
    Reads only uncompressed 24- and 8-bit images.  Tested with
    images saved using Microsoft Paint in Windows 95.  If the image
    is not a 24- or 8-bit image, the program refuses to even try.
    I guess one could include 4-bit images by masking the byte
    by first 1100 and then 0011.  I am not really 
    interested in such images.  If a compressed image is attempted,
    the routine will probably fail by generating an IOException.
    Look for variable ncompression to be different from 0 to indicate
    compression is present.
    Arguments:
        sdir and sfile are the result of the FileDialog()
        getDirectory() and getFile() methods.
    Returns:
        Image Object, be sure to check for (Image)null !!!!
    */
    public Image sld_bmap (String sdir, String sfile) {
    Image image=null;

    try
        {
          FileInputStream fs=new FileInputStream(sdir+(sdir.endsWith(File.separator)? "":File.separator)+sfile);
        int bflen=14;  
        byte bf[]=new byte[bflen];
        fs.read(bf,0,bflen);
        int bilen=40; 
        byte bi[]=new byte[bilen];
        fs.read(bi,0,bilen);

        int nsize = (((int)bf[5]&0xff)<<24) 
        | (((int)bf[4]&0xff)<<16)
        | (((int)bf[3]&0xff)<<8)
        | (int)bf[2]&0xff;

        int nbisize = (((int)bi[3]&0xff)<<24)
        | (((int)bi[2]&0xff)<<16)
        | (((int)bi[1]&0xff)<<8)
        | (int)bi[0]&0xff;

        int nwidth = (((int)bi[7]&0xff)<<24)
        | (((int)bi[6]&0xff)<<16)
        | (((int)bi[5]&0xff)<<8)
        | (int)bi[4]&0xff;

        int nheight = (((int)bi[11]&0xff)<<24)
        | (((int)bi[10]&0xff)<<16)
        | (((int)bi[9]&0xff)<<8)
        | (int)bi[8]&0xff;

        int nplanes = (((int)bi[13]&0xff)<<8) | (int)bi[12]&0xff;

        int nbitcount = (((int)bi[15]&0xff)<<8) | (int)bi[14]&0xff;

        int ncompression = (((int)bi[19])<<24)
        | (((int)bi[18])<<16)
        | (((int)bi[17])<<8)
        | (int)bi[16];

        int nsizeimage = (((int)bi[23]&0xff)<<24)
        | (((int)bi[22]&0xff)<<16)
        | (((int)bi[21]&0xff)<<8)
        | (int)bi[20]&0xff;

        int nxpm = (((int)bi[27]&0xff)<<24)
        | (((int)bi[26]&0xff)<<16)
        | (((int)bi[25]&0xff)<<8)
        | (int)bi[24]&0xff;

        int nypm = (((int)bi[31]&0xff)<<24)
        | (((int)bi[30]&0xff)<<16)
        | (((int)bi[29]&0xff)<<8)
        | (int)bi[28]&0xff;

        int nclrused = (((int)bi[35]&0xff)<<24)
        | (((int)bi[34]&0xff)<<16)
        | (((int)bi[33]&0xff)<<8)
        | (int)bi[32]&0xff;

        int nclrimp = (((int)bi[39]&0xff)<<24)
        | (((int)bi[38]&0xff)<<16)
        | (((int)bi[37]&0xff)<<8)
        | (int)bi[36]&0xff;

        if (nbitcount==24)
        {

        int npad = (nsizeimage / nheight) - nwidth * 3;
        int ndata[] = new int [nheight * nwidth];
        byte brgb[] = new byte [( nwidth + npad) * 3 * nheight];
        fs.read (brgb, 0, (nwidth + npad) * 3 * nheight);
        int nindex = 0;
        for (int j = 0; j < nheight; j++)
            {
            for (int i = 0; i < nwidth; i++)
            {
            ndata [nwidth * (nheight - j - 1) + i] =
                (255&0xff)<<24
                | (((int)brgb[nindex+2]&0xff)<<16)
                | (((int)brgb[nindex+1]&0xff)<<8)
                | (int)brgb[nindex]&0xff;

            nindex += 3;
            }
            nindex += npad;
            }
        image = createImage ( new MemoryImageSource (nwidth, nheight, ndata, 0, nwidth));
        }
        else if (nbitcount == 8) {

        int nNumColors = 0;
        if (nclrused > 0)
            {
            nNumColors = nclrused;
            }
        else
            {
            nNumColors = (1&0xff)<<nbitcount;
            }

        if (nsizeimage == 0)
            {
            nsizeimage = ((((nwidth*nbitcount)+31) & ~31 ) >> 3);
            nsizeimage *= nheight;

            }

        int  npalette[] = new int [nNumColors];
        byte bpalette[] = new byte [nNumColors*4];
        fs.read (bpalette, 0, nNumColors*4);
        int nindex8 = 0;
        for (int n = 0; n < nNumColors; n++)
            {
            npalette[n] = (255&0xff)<<24
            | (((int)bpalette[nindex8+2]&0xff)<<16)
            | (((int)bpalette[nindex8+1]&0xff)<<8)
            | (int)bpalette[nindex8]&0xff;

            nindex8 += 4;
            }

        int npad8 = (nsizeimage / nheight) - nwidth;

        int  ndata8[] = new int [nwidth*nheight];
        byte bdata[] = new byte [(nwidth+npad8)*nheight];
        fs.read (bdata, 0, (nwidth+npad8)*nheight);
        nindex8 = 0;
        for (int j8 = 0; j8 < nheight; j8++)
            {
            for (int i8 = 0; i8 < nwidth; i8++)
            {
            ndata8 [nwidth*(nheight-j8-1)+i8] =
                npalette [((int)bdata[nindex8]&0xff)];
            nindex8++;
            }
            nindex8 += npad8;
            }
        image = createImage
            ( new MemoryImageSource (nwidth, nheight,
                         ndata8, 0, nwidth));
        }
        else
        {

        image = (Image)null;
        }
        fs.close();
        return image;
        }
    catch (Exception e) {
          e.printStackTrace();

        }
    return image;
    }
    public Image sld_bytesToBmap (byte[] bytes) {
    Image image=null;

    try
        {

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        int bflen=14;  
        byte bf[]=new byte[bflen];
        bis.read(bf,0,bflen);
        int bilen=40; 
        byte bi[]=new byte[bilen];
        bis.read(bi,0,bilen);

        int nsize = (((int)bf[5]&0xff)<<24) 
        | (((int)bf[4]&0xff)<<16)
        | (((int)bf[3]&0xff)<<8)
        | (int)bf[2]&0xff;

        int nbisize = (((int)bi[3]&0xff)<<24)
        | (((int)bi[2]&0xff)<<16)
        | (((int)bi[1]&0xff)<<8)
        | (int)bi[0]&0xff;

        int nwidth = (((int)bi[7]&0xff)<<24)
        | (((int)bi[6]&0xff)<<16)
        | (((int)bi[5]&0xff)<<8)
        | (int)bi[4]&0xff;

        int nheight = (((int)bi[11]&0xff)<<24)
        | (((int)bi[10]&0xff)<<16)
        | (((int)bi[9]&0xff)<<8)
        | (int)bi[8]&0xff;

        int nplanes = (((int)bi[13]&0xff)<<8) | (int)bi[12]&0xff;

        int nbitcount = (((int)bi[15]&0xff)<<8) | (int)bi[14]&0xff;

        int ncompression = (((int)bi[19])<<24)
        | (((int)bi[18])<<16)
        | (((int)bi[17])<<8)
        | (int)bi[16];

        int nsizeimage = (((int)bi[23]&0xff)<<24)
        | (((int)bi[22]&0xff)<<16)
        | (((int)bi[21]&0xff)<<8)
        | (int)bi[20]&0xff;

        int nxpm = (((int)bi[27]&0xff)<<24)
        | (((int)bi[26]&0xff)<<16)
        | (((int)bi[25]&0xff)<<8)
        | (int)bi[24]&0xff;

        int nypm = (((int)bi[31]&0xff)<<24)
        | (((int)bi[30]&0xff)<<16)
        | (((int)bi[29]&0xff)<<8)
        | (int)bi[28]&0xff;

        int nclrused = (((int)bi[35]&0xff)<<24)
        | (((int)bi[34]&0xff)<<16)
        | (((int)bi[33]&0xff)<<8)
        | (int)bi[32]&0xff;

        int nclrimp = (((int)bi[39]&0xff)<<24)
        | (((int)bi[38]&0xff)<<16)
        | (((int)bi[37]&0xff)<<8)
        | (int)bi[36]&0xff;

        if (nbitcount==24)
        {

        int npad = (nsizeimage / nheight) - nwidth * 3;
        int ndata[] = new int [nheight * nwidth];
        byte brgb[] = new byte [( nwidth + npad) * 3 * nheight];
        bis.read (brgb, 0, (nwidth + npad) * 3 * nheight);
        int nindex = 0;
        for (int j = 0; j < nheight; j++)
            {
            for (int i = 0; i < nwidth; i++)
            {
            ndata [nwidth * (nheight - j - 1) + i] =
                (255&0xff)<<24
                | (((int)brgb[nindex+2]&0xff)<<16)
                | (((int)brgb[nindex+1]&0xff)<<8)
                | (int)brgb[nindex]&0xff;

            nindex += 3;
            }
            nindex += npad;
            }
        image = createImage ( new MemoryImageSource (nwidth, nheight, ndata, 0, nwidth));
        }
        else if (nbitcount == 8) {

        int nNumColors = 0;
        if (nclrused > 0)
            {
            nNumColors = nclrused;
            }
        else
            {
            nNumColors = (1&0xff)<<nbitcount;
            }

        if (nsizeimage == 0)
            {
            nsizeimage = ((((nwidth*nbitcount)+31) & ~31 ) >> 3);
            nsizeimage *= nheight;

            }

        int  npalette[] = new int [nNumColors];
        byte bpalette[] = new byte [nNumColors*4];
        bis.read (bpalette, 0, nNumColors*4);
        int nindex8 = 0;
        for (int n = 0; n < nNumColors; n++)
            {
            npalette[n] = (255&0xff)<<24
            | (((int)bpalette[nindex8+2]&0xff)<<16)
            | (((int)bpalette[nindex8+1]&0xff)<<8)
            | (int)bpalette[nindex8]&0xff;

            nindex8 += 4;
            }

        int npad8 = (nsizeimage / nheight) - nwidth;

        int  ndata8[] = new int [nwidth*nheight];
        byte bdata[] = new byte [(nwidth+npad8)*nheight];
        bis.read (bdata, 0, (nwidth+npad8)*nheight);
        nindex8 = 0;
        for (int j8 = 0; j8 < nheight; j8++)
            {
            for (int i8 = 0; i8 < nwidth; i8++)
            {
            ndata8 [nwidth*(nheight-j8-1)+i8] =
                npalette [((int)bdata[nindex8]&0xff)];
            nindex8++;
            }
            nindex8 += npad8;
            }
        image = createImage
            ( new MemoryImageSource (nwidth, nheight,
                         ndata8, 0, nwidth));
        }
        else
        {

        image = (Image)null;
        }
        bis.close();
        return image;
        }
    catch (Exception e) {
          e.printStackTrace();

        }
    return image;
    }

void sld_readSlideList(){
    if(sld_listfile!=null && sld_listfile.length()>0){
       File f=new File(sld_listfile);
       if(f.exists() && f.isFile()){
         StringBuilder sb=new StringBuilder();
         try{
           FileInputStream in=new FileInputStream(sld_listfile);
           BufferedReader d= new BufferedReader(new InputStreamReader(in));
           while(true){
	     String str1=d.readLine();
	     if(str1==null) {in.close(); d.close(); break; }

             if(str1.length()>0) {
               String arr[]=ylib.csvlinetoarray(str1);
               if(arr[0].length()>0) {
                 if(arr[0].toLowerCase().startsWith("http") && arr[0].indexOf(":")>-1){}
                 else{
                   arr[0]=ylib.replace(arr[0],"/",File.separator);
                   arr[0]=ylib.replace(arr[0],"\\",File.separator);
                   arr[1]=ylib.replace(arr[0],"/",File.separator);
                   arr[1]=ylib.replace(arr[0],"\\",File.separator);
                   str1=ylib.arrayToCsvLine(arr);
                 }
                 slides.put(arr[0],str1);
               }
             }
           }
	   in.close();
	   d.close();
           }catch(FileNotFoundException e){
               e.printStackTrace();
           }
    catch(IOException e){

        System.out.println(" Error in reading "+sld_listfile+" file.");
        e.printStackTrace();
    }
}
    }
}
 void sld_saveSlideList(){
      if(sld_listfile!=null && sld_listfile.length()>0){
        StringBuffer sb=new StringBuffer("");
        boolean first=true;
        if(slides.size()>0){
          Iterator it=slides.keySet().iterator();
          for(;it.hasNext();){
            String key=(String)it.next();
            if(slides.get(key)!=null){
             sb.append((first? "":"\r\n")+(String)slides.get(key));
             first=false;
            } else System.out.println("Warning : slides key '"+key+"' no slide data in slides.");
          }
        }
	try{
	  FileOutputStream out = new FileOutputStream (sld_listfile);
	  byte [] b=sb.toString().getBytes();
	  out.write(b);
	  out.close();
     }catch(IOException e){e.printStackTrace();}
    }
}
 void sld_updateSlideList(boolean firsttime){
   String sel=null;
   if(firsttime){
     if(moderator_props.getProperty("sld_file_id")!=null && moderator_props.getProperty("sld_file_id").length()>0) sel=moderator_props.getProperty("sld_file_id");
   } else {
       if(cbb_slide.getSelectedItem()!=null) sel=(String)cbb_slide.getSelectedItem();
   }
   cbb_slide.removeAllItems();
   cbb_slide.addItem("");
   if(slides.size()>0){
     Iterator it=slides.keySet().iterator();
     for(;it.hasNext();){
       cbb_slide.addItem((String)it.next());
     }
   }
   if(sel!=null && sel.length()>0) {
     boolean found=false;
     for(int i=0;i<cbb_slide.getItemCount();i++){
       if(((String)cbb_slide.getItemAt(i)).equals(sel)) {found=true; break;}
     }
     if(found) cbb_slide.setSelectedItem(sel);
     else cbb_slide.setSelectedItem("");
   }
 }
private void saveModeratorProps(){

  moderator_props.remove("sld_zipfile_pw");
  moderator_props.remove("sld_pwencode");
  moderator_props.remove("sld_imagefile_filter");
  moderator_props.remove("sld_lastshowtime");
  moderator_props.remove("sld_index");
  moderator_props.remove("sld_dir");
  try{

    FileOutputStream out = new FileOutputStream(propsFile );
        moderator_props.store(out,"");
        out.close();
  }catch(IOException e){
    e.printStackTrace();
  }
}
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    buttonGroup6 = new javax.swing.ButtonGroup();
    buttonGroup7 = new javax.swing.ButtonGroup();
    buttonGroup2 = new javax.swing.ButtonGroup();
    buttonGroup3 = new javax.swing.ButtonGroup();
    buttonGroup8 = new javax.swing.ButtonGroup();
    jTabbedPane1 = new javax.swing.JTabbedPane();
    slidePanel = new javax.swing.JPanel();
    sld_upperPanel1 = new javax.swing.JPanel();
    sld_upperPanel2 = new javax.swing.JPanel();
    jButton39 = new javax.swing.JButton();
    btn_openSld = new javax.swing.JButton();
    cbb_slide = new javax.swing.JComboBox();
    jLabel12 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    cbb_sldMList = new javax.swing.JComboBox();
    jPanel52 = new javax.swing.JPanel();
    jLabel13 = new javax.swing.JLabel();
    cbb_slideInx = new javax.swing.JComboBox();
    jLabel20 = new javax.swing.JLabel();
    jButton38 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jButton5 = new javax.swing.JButton();
    msgPanel = new javax.swing.JPanel();
    msg_TabbedPane = new javax.swing.JTabbedPane();
    msg_panel1 = new javax.swing.JPanel();
    msg_scrollPane = new javax.swing.JScrollPane();
    msg_textPane = new javax.swing.JTextPane(msg_styleDoc);
    msg_upperPanel = new javax.swing.JPanel();
    msg_btnSaveAs = new javax.swing.JButton();
    msg_btnClear = new javax.swing.JButton();
    msg_btnPlus = new javax.swing.JButton();
    msg_btnMinus = new javax.swing.JButton();
    jLabel31 = new javax.swing.JLabel();
    Color[] colors={Color.white,Color.red,Color.blue,Color.green,Color.BLACK,Color.CYAN,Color.DARK_GRAY,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
    jComboBox2 = new javax.swing.JComboBox();
    msg_checkBox1 = new javax.swing.JCheckBox();
    msg_panel2 = new javax.swing.JPanel();
    msg_scrollPane2 = new javax.swing.JScrollPane();
    msg_textArea1 = new javax.swing.JTextArea();
    msg_draft_upperPanel = new javax.swing.JPanel();
    msg_button10 = new javax.swing.JButton();
    msg_button9 = new javax.swing.JButton();
    msg_button8 = new javax.swing.JButton();
    msg_button7 = new javax.swing.JButton();
    msg_lowerPanel = new javax.swing.JPanel();
    jPanel51 = new javax.swing.JPanel();
    msg_textField1 = new javax.swing.JTextField();
    msg_panel7 = new javax.swing.JPanel();
    msg_label1 = new javax.swing.JLabel();
    jPanel53 = new javax.swing.JPanel();
    msg_panel5 = new javax.swing.JPanel();
    jCheckBox14 = new javax.swing.JCheckBox();
    jCheckBox13 = new javax.swing.JCheckBox();
    jLabel27 = new javax.swing.JLabel();
    Color[] colors2={Color.white,Color.red,Color.blue,Color.green,Color.BLACK,Color.CYAN,Color.DARK_GRAY,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
    jComboBox7 = new javax.swing.JComboBox();
    msg_btn_sendMsg = new javax.swing.JButton();
    msg_btnSendDraft = new javax.swing.JButton();
    msg_btnSendClear = new javax.swing.JButton();
    msg_label2 = new javax.swing.JLabel();
    cbb_msgMList = new javax.swing.JComboBox();
    screenPanel = new javax.swing.JPanel();
    srn_upperPanel = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    cbb_srnMList = new javax.swing.JComboBox();
    btn_srnStart = new javax.swing.JButton();
    rb_srnMonitor = new javax.swing.JRadioButton();
    rb_srnControlAndImage = new javax.swing.JRadioButton();
    btn_srnRefresh = new javax.swing.JButton();
    btn_srnACD = new javax.swing.JButton();
    jButton11 = new javax.swing.JButton();
    cb_srnFix = new javax.swing.JCheckBox();
    srn_scrollPane1 = new javax.swing.JScrollPane();
    filePanel = new javax.swing.JPanel();
    f_panel1 = new javax.swing.JPanel();
    f_panel6 = new javax.swing.JPanel();
    f_panel20 = new javax.swing.JPanel();
    btn_fResetLayout1 = new javax.swing.JButton();
    btn_fRoot1 = new javax.swing.JButton();
    f_checkBox1 = new javax.swing.JCheckBox();
    f_panel21 = new javax.swing.JPanel();
    f_label1 = new javax.swing.JLabel();
    cbb_fList1 = new javax.swing.JComboBox();
    f_panel7 = new javax.swing.JPanel();
    f_panel2 = new javax.swing.JPanel();
    jPanel29 = new javax.swing.JPanel();
    f_panel22 = new javax.swing.JPanel();
    btn_fResetLayout2 = new javax.swing.JButton();
    btn_fRoot2 = new javax.swing.JButton();
    f_panel23 = new javax.swing.JPanel();
    f_label2 = new javax.swing.JLabel();
    cbb_fMlist = new javax.swing.JComboBox();
    cbb_fList2 = new javax.swing.JComboBox();
    jPanel31 = new javax.swing.JPanel();
    f_panel3 = new javax.swing.JPanel();
    f_scrollPane6 = new javax.swing.JScrollPane();
    f_table1 = new javax.swing.JTable();
    f_panel4 = new javax.swing.JPanel();
    f_scrollPane7 = new javax.swing.JScrollPane();
    f_table2 = new javax.swing.JTable();
    f_panel5 = new javax.swing.JPanel();
    f_tabbedPane1 = new javax.swing.JTabbedPane();
    f_panel18 = new javax.swing.JPanel();
    jScrollPane4 = new javax.swing.JScrollPane();
    jTextPane1 = new javax.swing.JTextPane();
    jPanel6 = new javax.swing.JPanel();
    jScrollPane3 = new javax.swing.JScrollPane();
    jTextArea3 = new javax.swing.JTextArea();
    jPanel33 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    f_panel10 = new javax.swing.JPanel();
    f_panel11 = new javax.swing.JPanel();
    f_panel12 = new javax.swing.JPanel();
    f_panel13 = new javax.swing.JPanel();
    sketchPanel = new javax.swing.JPanel();
    sk_upperPanel = new javax.swing.JPanel();
    jPanel55 = new javax.swing.JPanel();
    jButton4 = new javax.swing.JButton();
    jLabel37 = new javax.swing.JLabel();
    Color[] colors4={Color.white,Color.red,Color.blue,Color.green,Color.BLACK,Color.CYAN,Color.DARK_GRAY,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
    jComboBox3 = new javax.swing.JComboBox();
    jLabel16 = new javax.swing.JLabel();
    Color[] colors3={Color.white,Color.red,Color.blue,Color.green,Color.BLACK,Color.CYAN,Color.DARK_GRAY,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
    jComboBox1 = new javax.swing.JComboBox();
    jLabel40 = new javax.swing.JLabel();
    jComboBox4 = new javax.swing.JComboBox();
    jLabel48 = new javax.swing.JLabel();
    jPanel54 = new javax.swing.JPanel();
    jLabel8 = new javax.swing.JLabel();
    cbb_skMList = new javax.swing.JComboBox();
    clear = new javax.swing.JButton();
    moderatorPanel = new javax.swing.JPanel();
    jTabbedPane2 = new javax.swing.JTabbedPane();
    jPanel1 = new javax.swing.JPanel();
    cbb_moderatorSldList = new javax.swing.JComboBox();
    jLabel23 = new javax.swing.JLabel();
    jPanel13 = new javax.swing.JPanel();
    jPanel36 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jCheckBox1 = new javax.swing.JCheckBox();
    jCheckBox2 = new javax.swing.JCheckBox();
    jButton7 = new javax.swing.JButton();
    jButton14 = new javax.swing.JButton();
    jPanel42 = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    jCheckBox5 = new javax.swing.JCheckBox();
    jCheckBox8 = new javax.swing.JCheckBox();
    jPanel12 = new javax.swing.JPanel();
    jPanel50 = new javax.swing.JPanel();
    jLabel7 = new javax.swing.JLabel();
    jTextField2 = new javax.swing.JTextField();
    jButton9 = new javax.swing.JButton();
    jPanel14 = new javax.swing.JPanel();
    jLabel29 = new javax.swing.JLabel();
    jPasswordField1 = new javax.swing.JPasswordField();
    jLabel30 = new javax.swing.JLabel();
    jTextField7 = new javax.swing.JTextField();
    btnReload = new javax.swing.JButton();
    jPanel24 = new javax.swing.JPanel();
    jPanel15 = new javax.swing.JPanel();
    jLabel45 = new javax.swing.JLabel();
    jButton30 = new javax.swing.JButton();
    jButton29 = new javax.swing.JButton();
    jPanel3 = new javax.swing.JPanel();
    jLabel25 = new javax.swing.JLabel();
    cbb_moderatorMsgList = new javax.swing.JComboBox();
    jPanel18 = new javax.swing.JPanel();
    jPanel34 = new javax.swing.JPanel();
    jLabel38 = new javax.swing.JLabel();
    jCheckBox6 = new javax.swing.JCheckBox();
    jCheckBox7 = new javax.swing.JCheckBox();
    jButton18 = new javax.swing.JButton();
    jButton20 = new javax.swing.JButton();
    jPanel45 = new javax.swing.JPanel();
    jLabel4 = new javax.swing.JLabel();
    jCheckBox9 = new javax.swing.JCheckBox();
    jCheckBox10 = new javax.swing.JCheckBox();
    jPanel19 = new javax.swing.JPanel();
    jPanel7 = new javax.swing.JPanel();
    jLabel32 = new javax.swing.JLabel();
    cbb_moderatorSrnList = new javax.swing.JComboBox();
    jButton6 = new javax.swing.JButton();
    jButton19 = new javax.swing.JButton();
    jCheckBox19 = new javax.swing.JCheckBox();
    jCheckBox20 = new javax.swing.JCheckBox();
    jButton25 = new javax.swing.JButton();
    jPanel20 = new javax.swing.JPanel();
    jPanel32 = new javax.swing.JPanel();
    jLabel39 = new javax.swing.JLabel();
    jCheckBox15 = new javax.swing.JCheckBox();
    jCheckBox16 = new javax.swing.JCheckBox();
    jButton24 = new javax.swing.JButton();
    jButton26 = new javax.swing.JButton();
    jPanel46 = new javax.swing.JPanel();
    jLabel6 = new javax.swing.JLabel();
    jCheckBox11 = new javax.swing.JCheckBox();
    jCheckBox12 = new javax.swing.JCheckBox();
    jLabel43 = new javax.swing.JLabel();
    jButton34 = new javax.swing.JButton();
    jPanel21 = new javax.swing.JPanel();
    jLabel41 = new javax.swing.JLabel();
    jPanel22 = new javax.swing.JPanel();
    jPanel26 = new javax.swing.JPanel();
    cbb_moderatorFList = new javax.swing.JComboBox();
    jLabel46 = new javax.swing.JLabel();
    jPanel27 = new javax.swing.JPanel();
    jPanel30 = new javax.swing.JPanel();
    jLabel47 = new javax.swing.JLabel();
    jCheckBox22 = new javax.swing.JCheckBox();
    jCheckBox23 = new javax.swing.JCheckBox();
    jButton35 = new javax.swing.JButton();
    jPanel47 = new javax.swing.JPanel();
    jLabel10 = new javax.swing.JLabel();
    jCheckBox17 = new javax.swing.JCheckBox();
    jCheckBox18 = new javax.swing.JCheckBox();
    jPanel49 = new javax.swing.JPanel();
    jPanel2 = new javax.swing.JPanel();
    jLabel24 = new javax.swing.JLabel();
    cbb_moderatorSkList = new javax.swing.JComboBox();
    jPanel16 = new javax.swing.JPanel();
    jPanel28 = new javax.swing.JPanel();
    jLabel5 = new javax.swing.JLabel();
    jCheckBox3 = new javax.swing.JCheckBox();
    jCheckBox4 = new javax.swing.JCheckBox();
    jButton8 = new javax.swing.JButton();
    jButton13 = new javax.swing.JButton();
    jPanel48 = new javax.swing.JPanel();
    jLabel11 = new javax.swing.JLabel();
    jCheckBox21 = new javax.swing.JCheckBox();
    jCheckBox24 = new javax.swing.JCheckBox();
    jPanel17 = new javax.swing.JPanel();
    jPanel5 = new javax.swing.JPanel();
    jLabel26 = new javax.swing.JLabel();
    cbb_moderatorPermissionList = new javax.swing.JComboBox();
    jPanel23 = new javax.swing.JPanel();
    jPanel38 = new javax.swing.JPanel();
    jLabel17 = new javax.swing.JLabel();
    cbSlideAu1 = new javax.swing.JCheckBox();
    cbSlideAu2 = new javax.swing.JCheckBox();
    jPanel39 = new javax.swing.JPanel();
    jLabel14 = new javax.swing.JLabel();
    cbSketchAu1 = new javax.swing.JCheckBox();
    cbSketchAu2 = new javax.swing.JCheckBox();
    jPanel40 = new javax.swing.JPanel();
    jLabel15 = new javax.swing.JLabel();
    cbMsgAu1 = new javax.swing.JCheckBox();
    cbMsgAu2 = new javax.swing.JCheckBox();
    jPanel41 = new javax.swing.JPanel();
    jLabel33 = new javax.swing.JLabel();
    cbSrnAu1 = new javax.swing.JCheckBox();
    cbSrnAu2 = new javax.swing.JCheckBox();
    jPanel43 = new javax.swing.JPanel();
    jLabel18 = new javax.swing.JLabel();
    cbOpenWebAu = new javax.swing.JCheckBox();
    jButton10 = new javax.swing.JButton();
    jPanel44 = new javax.swing.JPanel();
    jLabel28 = new javax.swing.JLabel();
    cbFileAu1 = new javax.swing.JCheckBox();
    cbFileAu2 = new javax.swing.JCheckBox();
    jPanel25 = new javax.swing.JPanel();
    jPanel8 = new javax.swing.JPanel();
    jLabel44 = new javax.swing.JLabel();
    jButton40 = new javax.swing.JButton();
    jButton21 = new javax.swing.JButton();
    jPanel4 = new javax.swing.JPanel();
    jLabel19 = new javax.swing.JLabel();
    jTextField5 = new javax.swing.JTextField();
    jButton12 = new javax.swing.JButton();
    jLabel22 = new javax.swing.JLabel();
    cbb_moderatorElseList = new javax.swing.JComboBox();
    jLabel34 = new javax.swing.JLabel();
    jTextField8 = new javax.swing.JTextField();
    jPanel9 = new javax.swing.JPanel();
    jButton27 = new javax.swing.JButton();
    jLabel35 = new javax.swing.JLabel();
    jTextField9 = new javax.swing.JTextField();
    jPanel10 = new javax.swing.JPanel();
    jLabel42 = new javax.swing.JLabel();
    jPanel11 = new javax.swing.JPanel();
    jPanel35 = new javax.swing.JPanel();
    jButton28 = new javax.swing.JButton();
    jButton36 = new javax.swing.JButton();
    jPanel37 = new javax.swing.JPanel();
    jButton32 = new javax.swing.JButton();
    jButton33 = new javax.swing.JButton();
    jButton15 = new javax.swing.JButton();
    jButton16 = new javax.swing.JButton();
    statusPanel = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTextArea2 = new javax.swing.JTextArea();
    jTextField4 = new javax.swing.JTextField();
    status_upperPanel = new javax.swing.JPanel();
    jLabel36 = new javax.swing.JLabel();
    cbb_moderatorStatusList = new javax.swing.JComboBox();
    jButton1 = new javax.swing.JButton();
    jButton41 = new javax.swing.JButton();
    jButton42 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    jButton31 = new javax.swing.JButton();
    jButton17 = new javax.swing.JButton();
    status_checkBox1 = new javax.swing.JCheckBox();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem5 = new javax.swing.JMenuItem();
    jMenuItem11 = new javax.swing.JMenuItem();
    jMenuItem12 = new javax.swing.JMenuItem();
    jMenuItem13 = new javax.swing.JMenuItem();
    jMenuItem14 = new javax.swing.JMenuItem();
    jMenuItem15 = new javax.swing.JMenuItem();
    jMenuItem16 = new javax.swing.JMenuItem();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();
    jMenuItem3 = new javax.swing.JMenuItem();
    jMenu3 = new javax.swing.JMenu();
    jMenuItem8 = new javax.swing.JMenuItem();
    jMenuItem7 = new javax.swing.JMenuItem();
    jMenuItem6 = new javax.swing.JMenuItem();
    jMenuItem4 = new javax.swing.JMenuItem();
    jMenuItem10 = new javax.swing.JMenuItem();
    jMenuItem9 = new javax.swing.JMenuItem();
    jMenuItem2 = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ct/Bundle"); 
    setTitle(bundle.getString("CTModerator.title")); 
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });
    addWindowStateListener(new java.awt.event.WindowStateListener() {
      public void windowStateChanged(java.awt.event.WindowEvent evt) {
        formWindowStateChanged(evt);
      }
    });

    jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        jTabbedPane1StateChanged(evt);
      }
    });

    slidePanel.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        slidePanelMouseClicked(evt);
      }
      public void mousePressed(java.awt.event.MouseEvent evt) {
        slidePanelMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        slidePanelMouseReleased(evt);
      }
    });
    slidePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(java.awt.event.MouseEvent evt) {
        slidePanelMouseDragged(evt);
      }
    });
    slidePanel.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        slidePanelKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        slidePanelKeyReleased(evt);
      }
    });
    slidePanel.setLayout(new java.awt.BorderLayout());

    sld_upperPanel1.setLayout(new java.awt.GridLayout(2, 1));

    sld_upperPanel2.setBackground(new java.awt.Color(102, 102, 102));
    sld_upperPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jButton39.setText(bundle.getString("CTModerator.jButton39.text")); 
    jButton39.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton39ActionPerformed(evt);
      }
    });
    sld_upperPanel2.add(jButton39);

    btn_openSld.setText(bundle.getString("CTModerator.btn_openSld.text")); 
    btn_openSld.setActionCommand(bundle.getString("CTModerator.btn_openSld.actionCommand")); 
    btn_openSld.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btn_openSldActionPerformed(evt);
      }
    });
    sld_upperPanel2.add(btn_openSld);

    cbb_slide.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    sld_upperPanel2.add(cbb_slide);

    jLabel12.setText(bundle.getString("CTModerator.jLabel12.text")); 
    sld_upperPanel2.add(jLabel12);

    jLabel9.setForeground(new java.awt.Color(255, 255, 255));
    jLabel9.setText(bundle.getString("CTModerator.jLabel9.text")); 
    sld_upperPanel2.add(jLabel9);

    cbb_sldMList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    sld_upperPanel2.add(cbb_sldMList);

    sld_upperPanel1.add(sld_upperPanel2);

    jPanel52.setBackground(new java.awt.Color(102, 102, 102));
    jPanel52.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel13.setForeground(new java.awt.Color(255, 255, 255));
    jLabel13.setText(bundle.getString("CTModerator.jLabel13.text")); 
    jPanel52.add(jLabel13);

    cbb_slideInx.setEditable(true);
    cbb_slideInx.setPreferredSize(new java.awt.Dimension(60, 21));
    cbb_slideInx.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        cbb_slideInxItemStateChanged(evt);
      }
    });
    cbb_slideInx.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbb_slideInxActionPerformed(evt);
      }
    });
    jPanel52.add(cbb_slideInx);

    jLabel20.setForeground(new java.awt.Color(255, 255, 255));
    jLabel20.setText(bundle.getString("CTModerator.jLabel20.text")); 
    jPanel52.add(jLabel20);

    jButton38.setText(bundle.getString("CTModerator.jButton38.text")); 
    jButton38.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton38ActionPerformed(evt);
      }
    });
    jPanel52.add(jButton38);

    jButton2.setText(bundle.getString("CTModerator.jButton2.text")); 
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel52.add(jButton2);

    jButton5.setText(bundle.getString("CTModerator.jButton5.text")); 
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton5ActionPerformed(evt);
      }
    });
    jPanel52.add(jButton5);

    sld_upperPanel1.add(jPanel52);

    slidePanel.add(sld_upperPanel1, java.awt.BorderLayout.NORTH);

    jTabbedPane1.addTab(bundle.getString("CTModerator.slidePanel.TabConstraints.tabTitle"), slidePanel); 

    msgPanel.setLayout(new java.awt.BorderLayout());

    msg_panel1.setLayout(new java.awt.BorderLayout());

    msg_scrollPane.setViewportView(msg_textPane);

    msg_panel1.add(msg_scrollPane, java.awt.BorderLayout.CENTER);

    msg_upperPanel.setBackground(new java.awt.Color(102, 102, 102));
    msg_upperPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    msg_btnSaveAs.setText(bundle.getString("CTModerator.msg_btnSaveAs.text")); 
    msg_btnSaveAs.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_btnSaveAsActionPerformed(evt);
      }
    });
    msg_upperPanel.add(msg_btnSaveAs);

    msg_btnClear.setText(bundle.getString("CTModerator.msg_btnClear.text")); 
    msg_btnClear.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_btnClearActionPerformed(evt);
      }
    });
    msg_upperPanel.add(msg_btnClear);

    msg_btnPlus.setText(bundle.getString("CTModerator.msg_btnPlus.text")); 
    msg_btnPlus.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_btnPlusActionPerformed(evt);
      }
    });
    msg_upperPanel.add(msg_btnPlus);

    msg_btnMinus.setText(bundle.getString("CTModerator.msg_btnMinus.text")); 
    msg_btnMinus.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_btnMinusActionPerformed(evt);
      }
    });
    msg_upperPanel.add(msg_btnMinus);

    jLabel31.setForeground(new java.awt.Color(255, 255, 255));
    jLabel31.setText(bundle.getString("CTModerator.jLabel31.text")); 
    msg_upperPanel.add(jLabel31);

    jComboBox2.setModel((new javax.swing.DefaultComboBoxModel(colors)));
    jComboBox2.setRenderer(new MyCellRenderer());
    jComboBox2.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        jComboBox2ItemStateChanged(evt);
      }
    });
    msg_upperPanel.add(jComboBox2);

    msg_checkBox1.setForeground(new java.awt.Color(255, 255, 255));
    msg_checkBox1.setText(bundle.getString("CTModerator.msg_checkBox1.text")); 
    msg_checkBox1.setOpaque(false);
    msg_upperPanel.add(msg_checkBox1);

    msg_panel1.add(msg_upperPanel, java.awt.BorderLayout.PAGE_START);

    msg_TabbedPane.addTab(bundle.getString("CTModerator.msg_panel1.TabConstraints.tabTitle"), msg_panel1); 

    msg_panel2.setLayout(new java.awt.BorderLayout());

    msg_textArea1.setColumns(20);
    msg_textArea1.setLineWrap(true);
    msg_textArea1.setRows(5);
    msg_scrollPane2.setViewportView(msg_textArea1);

    msg_panel2.add(msg_scrollPane2, java.awt.BorderLayout.CENTER);

    msg_draft_upperPanel.setBackground(new java.awt.Color(102, 102, 102));
    msg_draft_upperPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    msg_button10.setText(bundle.getString("CTModerator.msg_button10.text")); 
    msg_button10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_button10ActionPerformed(evt);
      }
    });
    msg_draft_upperPanel.add(msg_button10);

    msg_button9.setText(bundle.getString("CTModerator.msg_button9.text")); 
    msg_button9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_button9ActionPerformed(evt);
      }
    });
    msg_draft_upperPanel.add(msg_button9);

    msg_button8.setText(bundle.getString("CTModerator.msg_button8.text")); 
    msg_button8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_button8ActionPerformed(evt);
      }
    });
    msg_draft_upperPanel.add(msg_button8);

    msg_button7.setText(bundle.getString("CTModerator.msg_button7.text")); 
    msg_button7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_button7ActionPerformed(evt);
      }
    });
    msg_draft_upperPanel.add(msg_button7);

    msg_panel2.add(msg_draft_upperPanel, java.awt.BorderLayout.PAGE_START);

    msg_TabbedPane.addTab(bundle.getString("CTModerator.msg_panel2.TabConstraints.tabTitle"), msg_panel2); 

    msgPanel.add(msg_TabbedPane, java.awt.BorderLayout.CENTER);

    msg_lowerPanel.setLayout(new java.awt.GridLayout(2, 1));

    jPanel51.setLayout(new java.awt.BorderLayout());

    msg_textField1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_textField1ActionPerformed(evt);
      }
    });
    jPanel51.add(msg_textField1, java.awt.BorderLayout.CENTER);

    msg_panel7.setBackground(new java.awt.Color(102, 102, 102));

    msg_label1.setForeground(new java.awt.Color(255, 255, 255));
    msg_label1.setText(bundle.getString("CTModerator.msg_label1.text")); 
    msg_panel7.add(msg_label1);

    jPanel51.add(msg_panel7, java.awt.BorderLayout.WEST);

    msg_lowerPanel.add(jPanel51);

    jPanel53.setLayout(new java.awt.BorderLayout());

    msg_panel5.setBackground(new java.awt.Color(102, 102, 102));

    jCheckBox14.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox14.setText(bundle.getString("CTModerator.jCheckBox14.text")); 
    jCheckBox14.setOpaque(false);
    msg_panel5.add(jCheckBox14);

    jCheckBox13.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox13.setText(bundle.getString("CTModerator.jCheckBox13.text")); 
    jCheckBox13.setOpaque(false);
    msg_panel5.add(jCheckBox13);

    jLabel27.setForeground(new java.awt.Color(255, 255, 255));
    jLabel27.setText(bundle.getString("CTModerator.jLabel27.text")); 
    msg_panel5.add(jLabel27);

    jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(colors2));
    jComboBox7.setRenderer(new MyCellRenderer());
    msg_panel5.add(jComboBox7);

    msg_btn_sendMsg.setText(bundle.getString("CTModerator.msg_btn_sendMsg.text")); 
    msg_btn_sendMsg.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_btn_sendMsgActionPerformed(evt);
      }
    });
    msg_panel5.add(msg_btn_sendMsg);

    msg_btnSendDraft.setText(bundle.getString("CTModerator.msg_btnSendDraft.text")); 
    msg_btnSendDraft.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_btnSendDraftActionPerformed(evt);
      }
    });
    msg_panel5.add(msg_btnSendDraft);

    msg_btnSendClear.setText(bundle.getString("CTModerator.msg_btnSendClear.text")); 
    msg_btnSendClear.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        msg_btnSendClearActionPerformed(evt);
      }
    });
    msg_panel5.add(msg_btnSendClear);

    msg_label2.setForeground(new java.awt.Color(255, 255, 255));
    msg_label2.setText(bundle.getString("CTModerator.msg_label2.text")); 
    msg_panel5.add(msg_label2);

    cbb_msgMList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    cbb_msgMList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbb_msgMListActionPerformed(evt);
      }
    });
    msg_panel5.add(cbb_msgMList);

    jPanel53.add(msg_panel5, java.awt.BorderLayout.EAST);

    msg_lowerPanel.add(jPanel53);

    msgPanel.add(msg_lowerPanel, java.awt.BorderLayout.SOUTH);

    jTabbedPane1.addTab(bundle.getString("CTModerator.msgPanel.TabConstraints.tabTitle"), msgPanel); 

    screenPanel.setLayout(new java.awt.BorderLayout());

    srn_upperPanel.setBackground(new java.awt.Color(102, 102, 102));
    srn_upperPanel.setPreferredSize(new java.awt.Dimension(849, 33));
    srn_upperPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText(bundle.getString("CTModerator.jLabel2.text")); 
    srn_upperPanel.add(jLabel2);

    cbb_srnMList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All" }));
    cbb_srnMList.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        cbb_srnMListItemStateChanged(evt);
      }
    });
    cbb_srnMList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbb_srnMListActionPerformed(evt);
      }
    });
    srn_upperPanel.add(cbb_srnMList);

    btn_srnStart.setText(bundle.getString("CTModerator.btn_srnStart.text")); 
    btn_srnStart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btn_srnStartActionPerformed(evt);
      }
    });
    srn_upperPanel.add(btn_srnStart);

    rb_srnMonitor.setForeground(new java.awt.Color(255, 255, 255));
    rb_srnMonitor.setText(bundle.getString("CTModerator.rb_srnMonitor.text")); 
    rb_srnMonitor.setOpaque(false);
    rb_srnMonitor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        rb_srnMonitorActionPerformed(evt);
      }
    });
    srn_upperPanel.add(rb_srnMonitor);

    rb_srnControlAndImage.setForeground(new java.awt.Color(255, 255, 255));
    rb_srnControlAndImage.setSelected(true);
    rb_srnControlAndImage.setText(bundle.getString("CTModerator.rb_srnControlAndImage.text")); 
    rb_srnControlAndImage.setOpaque(false);
    rb_srnControlAndImage.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        rb_srnControlAndImageActionPerformed(evt);
      }
    });
    srn_upperPanel.add(rb_srnControlAndImage);

    btn_srnRefresh.setText(bundle.getString("CTModerator.btn_srnRefresh.text")); 
    btn_srnRefresh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btn_srnRefreshActionPerformed(evt);
      }
    });
    srn_upperPanel.add(btn_srnRefresh);

    btn_srnACD.setText(bundle.getString("CTModerator.btn_srnACD.text")); 
    btn_srnACD.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btn_srnACDActionPerformed(evt);
      }
    });
    srn_upperPanel.add(btn_srnACD);

    jButton11.setText(bundle.getString("CTModerator.jButton11.text")); 
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton11ActionPerformed(evt);
      }
    });
    srn_upperPanel.add(jButton11);

    cb_srnFix.setForeground(new java.awt.Color(255, 255, 255));
    cb_srnFix.setSelected(true);
    cb_srnFix.setText(bundle.getString("CTModerator.cb_srnFix.text")); 
    cb_srnFix.setOpaque(false);
    cb_srnFix.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cb_srnFixActionPerformed(evt);
      }
    });
    srn_upperPanel.add(cb_srnFix);

    screenPanel.add(srn_upperPanel, java.awt.BorderLayout.NORTH);
    screenPanel.add(srn_scrollPane1, java.awt.BorderLayout.CENTER);

    jTabbedPane1.addTab(bundle.getString("CTModerator.screenPanel.TabConstraints.tabTitle"), screenPanel); 

    filePanel.setLayout(null);

    f_panel1.setBackground(new java.awt.Color(204, 255, 255));
    f_panel1.setLayout(new java.awt.BorderLayout());

    f_panel6.setLayout(new java.awt.GridLayout(2, 1));

    f_panel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    btn_fResetLayout1.setText(bundle.getString("CTModerator.btn_fResetLayout1.text")); 
    btn_fResetLayout1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btn_fResetLayout1ActionPerformed(evt);
      }
    });
    f_panel20.add(btn_fResetLayout1);

    btn_fRoot1.setText(bundle.getString("CTModerator.btn_fRoot1.text")); 
    f_panel20.add(btn_fRoot1);

    f_checkBox1.setText(bundle.getString("CTModerator.f_checkBox1.text")); 
    f_panel20.add(f_checkBox1);

    f_panel6.add(f_panel20);

    f_panel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    f_label1.setText(bundle.getString("CTModerator.f_label1.text")); 
    f_panel21.add(f_label1);

    cbb_fList1.setEditable(true);
    cbb_fList1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    cbb_fList1.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        cbb_fList1ItemStateChanged(evt);
      }
    });
    cbb_fList1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbb_fList1ActionPerformed(evt);
      }
    });
    f_panel21.add(cbb_fList1);

    f_panel6.add(f_panel21);

    f_panel1.add(f_panel6, java.awt.BorderLayout.NORTH);

    f_panel7.setBackground(new java.awt.Color(204, 204, 255));
    f_panel7.setLayout(new java.awt.BorderLayout());
    f_panel1.add(f_panel7, java.awt.BorderLayout.CENTER);

    filePanel.add(f_panel1);
    f_panel1.setBounds(20, 10, 310, 210);

    f_panel2.setBackground(new java.awt.Color(204, 255, 255));
    f_panel2.setLayout(new java.awt.BorderLayout());

    jPanel29.setLayout(new java.awt.GridLayout(2, 0));

    f_panel22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    btn_fResetLayout2.setText(bundle.getString("CTModerator.btn_fResetLayout2.text")); 
    btn_fResetLayout2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btn_fResetLayout2ActionPerformed(evt);
      }
    });
    f_panel22.add(btn_fResetLayout2);

    btn_fRoot2.setText(bundle.getString("CTModerator.btn_fRoot2.text")); 
    f_panel22.add(btn_fRoot2);

    jPanel29.add(f_panel22);

    f_panel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    f_label2.setText(bundle.getString("CTModerator.f_label2.text")); 
    f_panel23.add(f_label2);

    cbb_fMlist.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    cbb_fMlist.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbb_fMlistActionPerformed(evt);
      }
    });
    f_panel23.add(cbb_fMlist);

    cbb_fList2.setEditable(true);
    cbb_fList2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    cbb_fList2.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        cbb_fList2ItemStateChanged(evt);
      }
    });
    cbb_fList2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbb_fList2ActionPerformed(evt);
      }
    });
    f_panel23.add(cbb_fList2);

    jPanel29.add(f_panel23);

    f_panel2.add(jPanel29, java.awt.BorderLayout.NORTH);

    jPanel31.setBackground(new java.awt.Color(204, 204, 255));
    jPanel31.setLayout(new java.awt.BorderLayout());
    f_panel2.add(jPanel31, java.awt.BorderLayout.CENTER);

    filePanel.add(f_panel2);
    f_panel2.setBounds(360, 10, 300, 210);

    f_panel3.setBackground(new java.awt.Color(204, 255, 255));
    f_panel3.setLayout(new java.awt.BorderLayout());

    f_table1.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Name", "Type", "Modified Time", "Size"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    f_table1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        f_table1MouseClicked(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        f_table1MouseReleased(evt);
      }
    });
    f_scrollPane6.setViewportView(f_table1);

    f_panel3.add(f_scrollPane6, java.awt.BorderLayout.CENTER);

    filePanel.add(f_panel3);
    f_panel3.setBounds(20, 250, 160, 50);

    f_panel4.setBackground(new java.awt.Color(204, 255, 255));
    f_panel4.setLayout(new java.awt.BorderLayout());

    f_table2.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Name", "Type", "Modified Time", "Size"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    f_table2.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        f_table2MouseClicked(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        f_table2MouseReleased(evt);
      }
    });
    f_scrollPane7.setViewportView(f_table2);

    f_panel4.add(f_scrollPane7, java.awt.BorderLayout.CENTER);

    filePanel.add(f_panel4);
    f_panel4.setBounds(220, 250, 160, 50);

    f_panel5.setBackground(new java.awt.Color(204, 204, 255));
    f_panel5.setLayout(new java.awt.BorderLayout());

    f_tabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

    f_panel18.setLayout(new java.awt.BorderLayout());

    jScrollPane4.setViewportView(jTextPane1);

    f_panel18.add(jScrollPane4, java.awt.BorderLayout.CENTER);

    f_tabbedPane1.addTab(bundle.getString("CTModerator.f_panel18.TabConstraints.tabTitle"), f_panel18); 

    jPanel6.setLayout(new java.awt.BorderLayout());

    jTextArea3.setColumns(20);
    jTextArea3.setRows(5);
    jScrollPane3.setViewportView(jTextArea3);

    jPanel6.add(jScrollPane3, java.awt.BorderLayout.CENTER);

    f_tabbedPane1.addTab(bundle.getString("CTModerator.jPanel6.TabConstraints.tabTitle"), jPanel6); 

    jPanel33.setLayout(new java.awt.BorderLayout());

    jTextArea1.setColumns(20);
    jTextArea1.setRows(5);
    jScrollPane1.setViewportView(jTextArea1);

    jPanel33.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    f_tabbedPane1.addTab(bundle.getString("CTModerator.jPanel33.TabConstraints.tabTitle"), jPanel33); 

    f_panel5.add(f_tabbedPane1, java.awt.BorderLayout.CENTER);

    filePanel.add(f_panel5);
    f_panel5.setBounds(17, 330, 490, 60);

    f_panel10.setBackground(new java.awt.Color(255, 204, 255));
    f_panel10.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        f_panel10MouseEntered(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        f_panel10MouseExited(evt);
      }
      public void mousePressed(java.awt.event.MouseEvent evt) {
        f_panel10MousePressed(evt);
      }
    });
    f_panel10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(java.awt.event.MouseEvent evt) {
        f_panel10MouseDragged(evt);
      }
      public void mouseMoved(java.awt.event.MouseEvent evt) {
        f_panel10MouseMoved(evt);
      }
    });

    javax.swing.GroupLayout f_panel10Layout = new javax.swing.GroupLayout(f_panel10);
    f_panel10.setLayout(f_panel10Layout);
    f_panel10Layout.setHorizontalGroup(
      f_panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 140, Short.MAX_VALUE)
    );
    f_panel10Layout.setVerticalGroup(
      f_panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 10, Short.MAX_VALUE)
    );

    filePanel.add(f_panel10);
    f_panel10.setBounds(320, 230, 140, 10);

    f_panel11.setBackground(new java.awt.Color(255, 204, 255));
    f_panel11.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        f_panel11MouseEntered(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        f_panel11MouseExited(evt);
      }
      public void mousePressed(java.awt.event.MouseEvent evt) {
        f_panel11MousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        f_panel11MouseReleased(evt);
      }
    });
    f_panel11.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(java.awt.event.MouseEvent evt) {
        f_panel11MouseDragged(evt);
      }
      public void mouseMoved(java.awt.event.MouseEvent evt) {
        f_panel11MouseMoved(evt);
      }
    });

    javax.swing.GroupLayout f_panel11Layout = new javax.swing.GroupLayout(f_panel11);
    f_panel11.setLayout(f_panel11Layout);
    f_panel11Layout.setHorizontalGroup(
      f_panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 370, Short.MAX_VALUE)
    );
    f_panel11Layout.setVerticalGroup(
      f_panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 10, Short.MAX_VALUE)
    );

    filePanel.add(f_panel11);
    f_panel11.setBounds(70, 310, 370, 10);

    f_panel12.setBackground(new java.awt.Color(255, 204, 255));
    f_panel12.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        f_panel12MouseEntered(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        f_panel12MouseExited(evt);
      }
      public void mousePressed(java.awt.event.MouseEvent evt) {
        f_panel12MousePressed(evt);
      }
    });
    f_panel12.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(java.awt.event.MouseEvent evt) {
        f_panel12MouseDragged(evt);
      }
      public void mouseMoved(java.awt.event.MouseEvent evt) {
        f_panel12MouseMoved(evt);
      }
    });

    javax.swing.GroupLayout f_panel12Layout = new javax.swing.GroupLayout(f_panel12);
    f_panel12.setLayout(f_panel12Layout);
    f_panel12Layout.setHorizontalGroup(
      f_panel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 10, Short.MAX_VALUE)
    );
    f_panel12Layout.setVerticalGroup(
      f_panel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 190, Short.MAX_VALUE)
    );

    filePanel.add(f_panel12);
    f_panel12.setBounds(700, 150, 10, 190);

    f_panel13.setBackground(new java.awt.Color(255, 204, 255));
    f_panel13.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        f_panel13MouseEntered(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        f_panel13MouseExited(evt);
      }
      public void mousePressed(java.awt.event.MouseEvent evt) {
        f_panel13MousePressed(evt);
      }
    });
    f_panel13.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(java.awt.event.MouseEvent evt) {
        f_panel13MouseDragged(evt);
      }
      public void mouseMoved(java.awt.event.MouseEvent evt) {
        f_panel13MouseMoved(evt);
      }
    });

    javax.swing.GroupLayout f_panel13Layout = new javax.swing.GroupLayout(f_panel13);
    f_panel13.setLayout(f_panel13Layout);
    f_panel13Layout.setHorizontalGroup(
      f_panel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 120, Short.MAX_VALUE)
    );
    f_panel13Layout.setVerticalGroup(
      f_panel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 10, Short.MAX_VALUE)
    );

    filePanel.add(f_panel13);
    f_panel13.setBounds(40, 230, 120, 10);

    jTabbedPane1.addTab(bundle.getString("CTModerator.filePanel.TabConstraints.tabTitle"), filePanel); 

    sketchPanel.setLayout(new java.awt.BorderLayout());

    sk_upperPanel.setBackground(new java.awt.Color(102, 102, 102));
    sk_upperPanel.setLayout(new java.awt.GridLayout(1, 2));

    jPanel55.setBackground(new java.awt.Color(102, 102, 102));
    jPanel55.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jButton4.setText(bundle.getString("CTModerator.jButton4.text")); 
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton4ActionPerformed(evt);
      }
    });
    jPanel55.add(jButton4);

    jLabel37.setForeground(new java.awt.Color(255, 255, 255));
    jLabel37.setText(bundle.getString("CTModerator.jLabel37.text")); 
    jPanel55.add(jLabel37);

    jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(colors4));
    jComboBox3.setRenderer(new MyCellRenderer());
    jComboBox3.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        jComboBox3ItemStateChanged(evt);
      }
    });
    jPanel55.add(jComboBox3);

    jLabel16.setForeground(new java.awt.Color(255, 255, 255));
    jLabel16.setText(bundle.getString("CTModerator.jLabel16.text")); 
    jPanel55.add(jLabel16);

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(colors3));
    jComboBox1.setRenderer(new MyCellRenderer());
    jPanel55.add(jComboBox1);

    jLabel40.setForeground(new java.awt.Color(255, 255, 255));
    jLabel40.setText(bundle.getString("CTModerator.jLabel40.text")); 
    jPanel55.add(jLabel40);

    jComboBox4.setEditable(true);
    jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
    jComboBox4.setPreferredSize(new java.awt.Dimension(48, 21));
    jPanel55.add(jComboBox4);

    jLabel48.setForeground(new java.awt.Color(255, 255, 255));
    jLabel48.setText(bundle.getString("CTModerator.jLabel48.text")); 
    jPanel55.add(jLabel48);

    sk_upperPanel.add(jPanel55);

    jPanel54.setBackground(new java.awt.Color(102, 102, 102));
    jPanel54.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    jLabel8.setForeground(new java.awt.Color(255, 255, 255));
    jLabel8.setText(bundle.getString("CTModerator.jLabel8.text")); 
    jPanel54.add(jLabel8);

    cbb_skMList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbb_skMListActionPerformed(evt);
      }
    });
    jPanel54.add(cbb_skMList);

    clear.setText(bundle.getString("CTModerator.clear.text")); 
    clear.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearActionPerformed(evt);
      }
    });
    jPanel54.add(clear);

    sk_upperPanel.add(jPanel54);

    sketchPanel.add(sk_upperPanel, java.awt.BorderLayout.NORTH);

    jTabbedPane1.addTab(bundle.getString("CTModerator.sketchPanel.TabConstraints.tabTitle"), sketchPanel); 

    moderatorPanel.setLayout(new java.awt.BorderLayout());

    jPanel1.setLayout(null);

    cbb_moderatorSldList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbb_moderatorSldListActionPerformed(evt);
      }
    });
    jPanel1.add(cbb_moderatorSldList);
    cbb_moderatorSldList.setBounds(70, 20, 120, 21);

    jLabel23.setText(bundle.getString("CTModerator.jLabel23.text")); 
    jPanel1.add(jLabel23);
    jLabel23.setBounds(20, 20, 50, 20);

    jPanel13.setBackground(new java.awt.Color(0, 51, 204));
    jPanel13.setLayout(null);

    jPanel36.setOpaque(false);
    jPanel36.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel1.setForeground(new java.awt.Color(255, 255, 255));
    jLabel1.setText(bundle.getString("CTModerator.jLabel1.text")); 
    jPanel36.add(jLabel1);
    jLabel1.getAccessibleContext().setAccessibleName(bundle.getString("CTModerator.jLabel1.AccessibleContext.accessibleName")); 

    jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox1.setText(bundle.getString("CTModerator.jCheckBox1.text")); 
    jCheckBox1.setOpaque(false);
    jPanel36.add(jCheckBox1);

    jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox2.setText(bundle.getString("CTModerator.jCheckBox2.text")); 
    jCheckBox2.setOpaque(false);
    jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox2ActionPerformed(evt);
      }
    });
    jPanel36.add(jCheckBox2);

    jButton7.setText(bundle.getString("CTModerator.jButton7.text")); 
    jButton7.setActionCommand(bundle.getString("CTModerator.jButton7.actionCommand")); 
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton7ActionPerformed(evt);
      }
    });
    jPanel36.add(jButton7);

    jButton14.setText(bundle.getString("CTModerator.jButton14.text")); 
    jButton14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton14ActionPerformed(evt);
      }
    });
    jPanel36.add(jButton14);

    jPanel13.add(jPanel36);
    jPanel36.setBounds(10, 50, 820, 40);

    jPanel42.setOpaque(false);
    jPanel42.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setText(bundle.getString("CTModerator.jLabel3.text")); 
    jPanel42.add(jLabel3);

    jCheckBox5.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox5.setText(bundle.getString("CTModerator.jCheckBox5.text")); 
    jCheckBox5.setOpaque(false);
    jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox5ActionPerformed(evt);
      }
    });
    jPanel42.add(jCheckBox5);

    jCheckBox8.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox8.setText(bundle.getString("CTModerator.jCheckBox8.text_1")); 
    jCheckBox8.setOpaque(false);
    jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox8ActionPerformed(evt);
      }
    });
    jPanel42.add(jCheckBox8);

    jPanel13.add(jPanel42);
    jPanel42.setBounds(10, 10, 820, 40);

    jPanel1.add(jPanel13);
    jPanel13.setBounds(20, 70, 880, 100);

    jPanel12.setBackground(new java.awt.Color(255, 255, 204));
    jPanel12.setLayout(null);

    jPanel50.setOpaque(false);
    jPanel50.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel7.setText(bundle.getString("CTModerator.jLabel7.text")); 
    jPanel50.add(jLabel7);

    jTextField2.setToolTipText(bundle.getString("CTModerator.jTextField2.toolTipText")); 
    jTextField2.setPreferredSize(new java.awt.Dimension(606, 21));
    jPanel50.add(jTextField2);

    jButton9.setText(bundle.getString("CTModerator.jButton9.text")); 
    jButton9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton9ActionPerformed(evt);
      }
    });
    jPanel50.add(jButton9);

    jPanel12.add(jPanel50);
    jPanel50.setBounds(10, 10, 810, 40);

    jPanel14.setOpaque(false);
    jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel29.setText(bundle.getString("CTModerator.jLabel29.text")); 
    jPanel14.add(jLabel29);

    jPasswordField1.setText(bundle.getString("CTModerator.jPasswordField1.text")); 
    jPasswordField1.setToolTipText(bundle.getString("CTModerator.jPasswordField1.toolTipText")); 
    jPasswordField1.setPreferredSize(new java.awt.Dimension(106, 25));
    jPanel14.add(jPasswordField1);

    jLabel30.setText(bundle.getString("CTModerator.jLabel30.text")); 
    jPanel14.add(jLabel30);

    jTextField7.setToolTipText(bundle.getString("CTModerator.jTextField7.toolTipText")); 
    jTextField7.setPreferredSize(new java.awt.Dimension(26, 25));
    jPanel14.add(jTextField7);

    btnReload.setText(bundle.getString("CTModerator.btnReload.text")); 
    btnReload.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnReloadActionPerformed(evt);
      }
    });
    jPanel14.add(btnReload);

    jPanel12.add(jPanel14);
    jPanel14.setBounds(470, 50, 370, 40);

    jPanel1.add(jPanel12);
    jPanel12.setBounds(20, 170, 880, 100);

    jPanel24.setBackground(new java.awt.Color(204, 204, 255));
    jPanel24.setLayout(null);

    jPanel15.setOpaque(false);
    jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel45.setText(bundle.getString("CTModerator.jLabel45.text")); 
    jPanel15.add(jLabel45);

    jButton30.setText(bundle.getString("CTModerator.jButton30.text")); 
    jButton30.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton30ActionPerformed(evt);
      }
    });
    jPanel15.add(jButton30);

    jButton29.setText(bundle.getString("CTModerator.jButton29.text")); 
    jButton29.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton29ActionPerformed(evt);
      }
    });
    jPanel15.add(jButton29);

    jPanel24.add(jPanel15);
    jPanel15.setBounds(10, 20, 840, 40);

    jPanel1.add(jPanel24);
    jPanel24.setBounds(20, 270, 880, 80);

    jTabbedPane2.addTab(bundle.getString("CTModerator.jPanel1.TabConstraints.tabTitle"), jPanel1); 

    jPanel3.setLayout(null);

    jLabel25.setText(bundle.getString("CTModerator.jLabel25.text")); 
    jPanel3.add(jLabel25);
    jLabel25.setBounds(30, 30, 50, 20);

    jPanel3.add(cbb_moderatorMsgList);
    cbb_moderatorMsgList.setBounds(90, 30, 170, 21);

    jPanel18.setBackground(new java.awt.Color(0, 51, 204));
    jPanel18.setLayout(null);

    jPanel34.setOpaque(false);
    jPanel34.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel38.setForeground(new java.awt.Color(255, 255, 255));
    jLabel38.setText(bundle.getString("CTModerator.jLabel38.text")); 
    jPanel34.add(jLabel38);

    jCheckBox6.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox6.setText(bundle.getString("CTModerator.jCheckBox6.text")); 
    jCheckBox6.setOpaque(false);
    jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox6ActionPerformed(evt);
      }
    });
    jPanel34.add(jCheckBox6);

    jCheckBox7.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox7.setText(bundle.getString("CTModerator.jCheckBox7.text")); 
    jCheckBox7.setOpaque(false);
    jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox7ActionPerformed(evt);
      }
    });
    jPanel34.add(jCheckBox7);

    jButton18.setText(bundle.getString("CTModerator.jButton18.text")); 
    jButton18.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton18ActionPerformed(evt);
      }
    });
    jPanel34.add(jButton18);

    jButton20.setText(bundle.getString("CTModerator.jButton20.text")); 
    jButton20.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton20ActionPerformed(evt);
      }
    });
    jPanel34.add(jButton20);

    jPanel18.add(jPanel34);
    jPanel34.setBounds(10, 50, 790, 40);

    jPanel45.setOpaque(false);
    jPanel45.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
    jLabel4.setText(bundle.getString("CTModerator.jLabel4.text")); 
    jPanel45.add(jLabel4);

    jCheckBox9.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox9.setText(bundle.getString("CTModerator.jCheckBox9.text")); 
    jCheckBox9.setOpaque(false);
    jCheckBox9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox9ActionPerformed(evt);
      }
    });
    jPanel45.add(jCheckBox9);

    jCheckBox10.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox10.setText(bundle.getString("CTModerator.jCheckBox10.text")); 
    jCheckBox10.setOpaque(false);
    jCheckBox10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox10ActionPerformed(evt);
      }
    });
    jPanel45.add(jCheckBox10);

    jPanel18.add(jPanel45);
    jPanel45.setBounds(10, 10, 780, 40);

    jPanel3.add(jPanel18);
    jPanel18.setBounds(20, 60, 870, 100);

    jPanel19.setBackground(new java.awt.Color(255, 255, 204));
    jPanel3.add(jPanel19);
    jPanel19.setBounds(20, 160, 870, 120);

    jTabbedPane2.addTab(bundle.getString("CTModerator.jPanel3.TabConstraints.tabTitle"), jPanel3); 

    jPanel7.setOpaque(false);
    jPanel7.setLayout(null);

    jLabel32.setText(bundle.getString("CTModerator.jLabel32.text")); 
    jPanel7.add(jLabel32);
    jLabel32.setBounds(30, 20, 50, 20);

    jPanel7.add(cbb_moderatorSrnList);
    cbb_moderatorSrnList.setBounds(90, 20, 170, 21);

    jButton6.setForeground(new java.awt.Color(0, 51, 255));
    jButton6.setText(bundle.getString("CTModerator.jButton6.text")); 
    jButton6.setOpaque(false);
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton6ActionPerformed(evt);
      }
    });
    jPanel7.add(jButton6);
    jButton6.setBounds(140, 280, 310, 23);

    jButton19.setForeground(new java.awt.Color(0, 51, 255));
    jButton19.setText(bundle.getString("CTModerator.jButton19.text")); 
    jButton19.setOpaque(false);
    jButton19.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton19ActionPerformed(evt);
      }
    });
    jPanel7.add(jButton19);
    jButton19.setBounds(470, 280, 90, 23);

    jCheckBox19.setSelected(true);
    jCheckBox19.setText(bundle.getString("CTModerator.jCheckBox19.text")); 
    jCheckBox19.setOpaque(false);
    jPanel7.add(jCheckBox19);
    jCheckBox19.setBounds(50, 210, 200, 23);

    jCheckBox20.setSelected(true);
    jCheckBox20.setText(bundle.getString("CTModerator.jCheckBox20.text")); 
    jCheckBox20.setOpaque(false);
    jPanel7.add(jCheckBox20);
    jCheckBox20.setBounds(260, 210, 230, 23);

    jButton25.setText(bundle.getString("CTModerator.jButton25.text")); 
    jButton25.setActionCommand(bundle.getString("CTModerator.jButton25.actionCommand")); 
    jButton25.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton25ActionPerformed(evt);
      }
    });
    jPanel7.add(jButton25);
    jButton25.setBounds(500, 210, 90, 23);

    jPanel20.setBackground(new java.awt.Color(0, 51, 204));
    jPanel20.setLayout(null);

    jPanel32.setOpaque(false);
    jPanel32.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel39.setForeground(new java.awt.Color(255, 255, 255));
    jLabel39.setText(bundle.getString("CTModerator.jLabel39.text")); 
    jPanel32.add(jLabel39);

    jCheckBox15.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox15.setText(bundle.getString("CTModerator.jCheckBox15.text")); 
    jCheckBox15.setOpaque(false);
    jPanel32.add(jCheckBox15);

    jCheckBox16.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox16.setText(bundle.getString("CTModerator.jCheckBox16.text")); 
    jCheckBox16.setOpaque(false);
    jCheckBox16.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox16ActionPerformed(evt);
      }
    });
    jPanel32.add(jCheckBox16);

    jButton24.setText(bundle.getString("CTModerator.jButton24.text")); 
    jButton24.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton24ActionPerformed(evt);
      }
    });
    jPanel32.add(jButton24);

    jButton26.setText(bundle.getString("CTModerator.jButton26.text")); 
    jButton26.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton26ActionPerformed(evt);
      }
    });
    jPanel32.add(jButton26);

    jPanel20.add(jPanel32);
    jPanel32.setBounds(20, 60, 790, 40);

    jPanel46.setOpaque(false);
    jPanel46.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel6.setForeground(new java.awt.Color(255, 255, 255));
    jLabel6.setText(bundle.getString("CTModerator.jLabel6.text")); 
    jPanel46.add(jLabel6);

    jCheckBox11.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox11.setText(bundle.getString("CTModerator.jCheckBox11.text_1")); 
    jCheckBox11.setOpaque(false);
    jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox11ActionPerformed(evt);
      }
    });
    jPanel46.add(jCheckBox11);

    jCheckBox12.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox12.setText(bundle.getString("CTModerator.jCheckBox12.text")); 
    jCheckBox12.setOpaque(false);
    jCheckBox12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox12ActionPerformed(evt);
      }
    });
    jPanel46.add(jCheckBox12);

    jPanel20.add(jPanel46);
    jPanel46.setBounds(20, 10, 1010, 40);

    jPanel7.add(jPanel20);
    jPanel20.setBounds(20, 70, 1050, 110);

    jLabel43.setText(bundle.getString("CTModerator.jLabel43.text")); 
    jPanel7.add(jLabel43);
    jLabel43.setBounds(40, 260, 160, 15);

    jButton34.setText(bundle.getString("CTModerator.jButton34.text")); 
    jButton34.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton34ActionPerformed(evt);
      }
    });
    jPanel7.add(jButton34);
    jButton34.setBounds(140, 320, 310, 23);

    jPanel21.setBackground(new java.awt.Color(255, 204, 204));
    jPanel7.add(jPanel21);
    jPanel21.setBounds(20, 250, 1050, 100);

    jLabel41.setText(bundle.getString("CTModerator.jLabel41.text")); 
    jPanel7.add(jLabel41);
    jLabel41.setBounds(40, 190, 210, 15);

    jPanel22.setBackground(new java.awt.Color(255, 255, 204));
    jPanel7.add(jPanel22);
    jPanel22.setBounds(20, 180, 1050, 70);

    jTabbedPane2.addTab(bundle.getString("CTModerator.jPanel7.TabConstraints.tabTitle"), jPanel7); 

    jPanel26.setLayout(null);

    jPanel26.add(cbb_moderatorFList);
    cbb_moderatorFList.setBounds(90, 20, 170, 21);

    jLabel46.setText(bundle.getString("CTModerator.jLabel46.text")); 
    jPanel26.add(jLabel46);
    jLabel46.setBounds(30, 20, 50, 20);

    jPanel27.setBackground(new java.awt.Color(0, 51, 204));
    jPanel27.setLayout(null);

    jPanel30.setOpaque(false);
    jPanel30.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel47.setForeground(new java.awt.Color(255, 255, 255));
    jLabel47.setText(bundle.getString("CTModerator.jLabel47.text")); 
    jPanel30.add(jLabel47);

    jCheckBox22.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox22.setText(bundle.getString("CTModerator.jCheckBox22.text")); 
    jCheckBox22.setOpaque(false);
    jCheckBox22.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox22ActionPerformed(evt);
      }
    });
    jPanel30.add(jCheckBox22);

    jCheckBox23.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox23.setText(bundle.getString("CTModerator.jCheckBox23.text")); 
    jCheckBox23.setOpaque(false);
    jCheckBox23.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox23ActionPerformed(evt);
      }
    });
    jPanel30.add(jCheckBox23);

    jButton35.setText(bundle.getString("CTModerator.jButton35.text")); 
    jButton35.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton35ActionPerformed(evt);
      }
    });
    jPanel30.add(jButton35);

    jPanel27.add(jPanel30);
    jPanel30.setBounds(20, 54, 800, 40);

    jPanel47.setOpaque(false);
    jPanel47.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel10.setForeground(new java.awt.Color(255, 255, 255));
    jLabel10.setText(bundle.getString("CTModerator.jLabel10.text")); 
    jPanel47.add(jLabel10);

    jCheckBox17.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox17.setText(bundle.getString("CTModerator.jCheckBox17.text")); 
    jCheckBox17.setOpaque(false);
    jCheckBox17.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox17ActionPerformed(evt);
      }
    });
    jPanel47.add(jCheckBox17);

    jCheckBox18.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox18.setText(bundle.getString("CTModerator.jCheckBox18.text")); 
    jCheckBox18.setOpaque(false);
    jCheckBox18.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox18ActionPerformed(evt);
      }
    });
    jPanel47.add(jCheckBox18);

    jPanel27.add(jPanel47);
    jPanel47.setBounds(20, 10, 830, 40);

    jPanel26.add(jPanel27);
    jPanel27.setBounds(10, 60, 890, 100);

    jPanel49.setBackground(new java.awt.Color(255, 255, 204));
    jPanel26.add(jPanel49);
    jPanel49.setBounds(10, 160, 890, 120);

    jTabbedPane2.addTab(bundle.getString("CTModerator.jPanel26.TabConstraints.tabTitle"), jPanel26); 

    jPanel2.setLayout(null);

    jLabel24.setText(bundle.getString("CTModerator.jLabel24.text")); 
    jPanel2.add(jLabel24);
    jLabel24.setBounds(30, 20, 80, 20);

    jPanel2.add(cbb_moderatorSkList);
    cbb_moderatorSkList.setBounds(110, 20, 170, 21);

    jPanel16.setBackground(new java.awt.Color(0, 51, 204));
    jPanel16.setLayout(null);

    jPanel28.setOpaque(false);
    jPanel28.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel5.setForeground(new java.awt.Color(255, 255, 255));
    jLabel5.setText(bundle.getString("CTModerator.jLabel5.text")); 
    jPanel28.add(jLabel5);

    jCheckBox3.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox3.setText(bundle.getString("CTModerator.jCheckBox3.text")); 
    jCheckBox3.setOpaque(false);
    jPanel28.add(jCheckBox3);

    jCheckBox4.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox4.setText(bundle.getString("CTModerator.jCheckBox4.text")); 
    jCheckBox4.setOpaque(false);
    jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox4ActionPerformed(evt);
      }
    });
    jPanel28.add(jCheckBox4);

    jButton8.setText(bundle.getString("CTModerator.jButton8.text")); 
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton8ActionPerformed(evt);
      }
    });
    jPanel28.add(jButton8);

    jButton13.setText(bundle.getString("CTModerator.jButton13.text")); 
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton13ActionPerformed(evt);
      }
    });
    jPanel28.add(jButton13);

    jPanel16.add(jPanel28);
    jPanel28.setBounds(10, 50, 820, 40);

    jPanel48.setOpaque(false);
    jPanel48.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel11.setForeground(new java.awt.Color(255, 255, 255));
    jLabel11.setText(bundle.getString("CTModerator.jLabel11.text")); 
    jPanel48.add(jLabel11);

    jCheckBox21.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox21.setText(bundle.getString("CTModerator.jCheckBox21.text_1")); 
    jCheckBox21.setOpaque(false);
    jCheckBox21.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox21ActionPerformed(evt);
      }
    });
    jPanel48.add(jCheckBox21);

    jCheckBox24.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox24.setText(bundle.getString("CTModerator.jCheckBox24.text")); 
    jCheckBox24.setOpaque(false);
    jCheckBox24.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox24ActionPerformed(evt);
      }
    });
    jPanel48.add(jCheckBox24);

    jPanel16.add(jPanel48);
    jPanel48.setBounds(10, 10, 780, 40);

    jPanel2.add(jPanel16);
    jPanel16.setBounds(10, 50, 890, 100);

    jPanel17.setBackground(new java.awt.Color(255, 255, 204));
    jPanel2.add(jPanel17);
    jPanel17.setBounds(10, 150, 890, 130);

    jTabbedPane2.addTab(bundle.getString("CTModerator.jPanel2.TabConstraints.tabTitle"), jPanel2); 

    jPanel5.setLayout(null);

    jLabel26.setText(bundle.getString("CTModerator.jLabel26.text")); 
    jPanel5.add(jLabel26);
    jLabel26.setBounds(30, 20, 70, 20);

    cbb_moderatorPermissionList.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        cbb_moderatorPermissionListItemStateChanged(evt);
      }
    });
    jPanel5.add(cbb_moderatorPermissionList);
    cbb_moderatorPermissionList.setBounds(110, 20, 160, 21);

    jPanel23.setBackground(new java.awt.Color(204, 204, 255));
    jPanel23.setLayout(null);

    jPanel38.setOpaque(false);
    jPanel38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel17.setText(bundle.getString("CTModerator.jLabel17.text")); 
    jPanel38.add(jLabel17);

    cbSlideAu1.setText(bundle.getString("CTModerator.cbSlideAu1.text")); 
    cbSlideAu1.setOpaque(false);
    cbSlideAu1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbSlideAu1ActionPerformed(evt);
      }
    });
    jPanel38.add(cbSlideAu1);

    cbSlideAu2.setText(bundle.getString("CTModerator.jCheckBox21.text")); 
    cbSlideAu2.setOpaque(false);
    cbSlideAu2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbSlideAu2ActionPerformed(evt);
      }
    });
    jPanel38.add(cbSlideAu2);

    jPanel23.add(jPanel38);
    jPanel38.setBounds(10, 20, 810, 40);

    jPanel39.setOpaque(false);
    jPanel39.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel14.setText(bundle.getString("CTModerator.jLabel14.text")); 
    jPanel39.add(jLabel14);

    cbSketchAu1.setText(bundle.getString("CTModerator.cbSketchAu1.text")); 
    cbSketchAu1.setOpaque(false);
    cbSketchAu1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbSketchAu1ActionPerformed(evt);
      }
    });
    jPanel39.add(cbSketchAu1);

    cbSketchAu2.setText(bundle.getString("CTModerator.cbSketchAu2.text")); 
    cbSketchAu2.setToolTipText(bundle.getString("CTModerator.cbSketchAu2.toolTipText")); 
    cbSketchAu2.setOpaque(false);
    cbSketchAu2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbSketchAu2ActionPerformed(evt);
      }
    });
    jPanel39.add(cbSketchAu2);

    jPanel23.add(jPanel39);
    jPanel39.setBounds(10, 60, 780, 30);

    jPanel40.setOpaque(false);
    jPanel40.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel15.setText(bundle.getString("CTModerator.jLabel15.text")); 
    jPanel40.add(jLabel15);

    cbMsgAu1.setText(bundle.getString("CTModerator.cbMsgAu1.text")); 
    cbMsgAu1.setOpaque(false);
    jPanel40.add(cbMsgAu1);

    cbMsgAu2.setText(bundle.getString("CTModerator.jCheckBox11.text")); 
    cbMsgAu2.setOpaque(false);
    jPanel40.add(cbMsgAu2);

    jPanel23.add(jPanel40);
    jPanel40.setBounds(10, 97, 780, 33);

    jPanel41.setOpaque(false);
    jPanel41.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel33.setText(bundle.getString("CTModerator.jLabel33.text")); 
    jPanel41.add(jLabel33);

    cbSrnAu1.setText(bundle.getString("CTModerator.cbSrnAu1.text")); 
    cbSrnAu1.setOpaque(false);
    jPanel41.add(cbSrnAu1);

    cbSrnAu2.setText(bundle.getString("CTModerator.cbSrnAu2.text")); 
    cbSrnAu2.setOpaque(false);
    jPanel41.add(cbSrnAu2);

    jPanel23.add(jPanel41);
    jPanel41.setBounds(10, 140, 800, 40);

    jPanel43.setOpaque(false);
    jPanel43.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel18.setText(bundle.getString("CTModerator.jLabel18.text")); 
    jPanel43.add(jLabel18);

    cbOpenWebAu.setText(bundle.getString("CTModerator.cbOpenWebAu.text")); 
    cbOpenWebAu.setOpaque(false);
    jPanel43.add(cbOpenWebAu);

    jPanel23.add(jPanel43);
    jPanel43.setBounds(10, 220, 510, 40);

    jButton10.setText(bundle.getString("CTModerator.jButton10.text")); 
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton10ActionPerformed(evt);
      }
    });
    jPanel23.add(jButton10);
    jButton10.setBounds(540, 220, 90, 30);

    jPanel44.setOpaque(false);
    jPanel44.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel28.setText(bundle.getString("CTModerator.jLabel28.text")); 
    jPanel44.add(jLabel28);

    cbFileAu1.setText(bundle.getString("CTModerator.cbFileAu1.text")); 
    cbFileAu1.setOpaque(false);
    cbFileAu1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbFileAu1ActionPerformed(evt);
      }
    });
    jPanel44.add(cbFileAu1);

    cbFileAu2.setText(bundle.getString("CTModerator.cbFileAu2.text")); 
    cbFileAu2.setOpaque(false);
    cbFileAu2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbFileAu2ActionPerformed(evt);
      }
    });
    jPanel44.add(cbFileAu2);

    jPanel23.add(jPanel44);
    jPanel44.setBounds(10, 180, 790, 40);

    jPanel5.add(jPanel23);
    jPanel23.setBounds(20, 60, 870, 280);

    jPanel25.setBackground(new java.awt.Color(204, 255, 204));
    jPanel25.setLayout(null);

    jPanel8.setOpaque(false);
    jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel44.setText(bundle.getString("CTModerator.jLabel44.text")); 
    jPanel8.add(jLabel44);

    jButton40.setText(bundle.getString("CTModerator.jButton40.text")); 
    jButton40.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton40ActionPerformed(evt);
      }
    });
    jPanel8.add(jButton40);

    jButton21.setText(bundle.getString("CTModerator.jButton21.text")); 
    jButton21.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton21ActionPerformed(evt);
      }
    });
    jPanel8.add(jButton21);

    jPanel25.add(jPanel8);
    jPanel8.setBounds(10, 10, 790, 40);

    jPanel5.add(jPanel25);
    jPanel25.setBounds(20, 340, 870, 60);

    jTabbedPane2.addTab(bundle.getString("CTModerator.jPanel5.TabConstraints.tabTitle"), jPanel5); 

    jPanel4.setLayout(null);

    jLabel19.setText(bundle.getString("CTModerator.jLabel19.text")); 
    jPanel4.add(jLabel19);
    jLabel19.setBounds(30, 90, 140, 20);
    jPanel4.add(jTextField5);
    jTextField5.setBounds(170, 90, 410, 21);

    jButton12.setText(bundle.getString("CTModerator.jButton12.text")); 
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton12ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton12);
    jButton12.setBounds(590, 90, 80, 23);

    jLabel22.setText(bundle.getString("CTModerator.jLabel22.text")); 
    jPanel4.add(jLabel22);
    jLabel22.setBounds(20, 30, 60, 20);

    jPanel4.add(cbb_moderatorElseList);
    cbb_moderatorElseList.setBounds(80, 30, 170, 21);

    jLabel34.setText(bundle.getString("CTModerator.jLabel34.text")); 
    jPanel4.add(jLabel34);
    jLabel34.setBounds(30, 140, 140, 20);
    jPanel4.add(jTextField8);
    jTextField8.setBounds(170, 140, 360, 21);

    jPanel9.setBackground(new java.awt.Color(204, 255, 255));
    jPanel4.add(jPanel9);
    jPanel9.setBounds(20, 70, 1140, 60);

    jButton27.setText(bundle.getString("CTModerator.jButton27.text")); 
    jButton27.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton27ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton27);
    jButton27.setBounds(590, 170, 80, 23);

    jLabel35.setText(bundle.getString("CTModerator.jLabel35.text")); 
    jPanel4.add(jLabel35);
    jLabel35.setBounds(110, 170, 100, 20);
    jPanel4.add(jTextField9);
    jTextField9.setBounds(220, 170, 360, 21);

    jPanel10.setBackground(new java.awt.Color(255, 204, 204));
    jPanel4.add(jPanel10);
    jPanel10.setBounds(20, 130, 1140, 80);

    jLabel42.setText(bundle.getString("CTModerator.jLabel42.text")); 
    jPanel4.add(jLabel42);
    jLabel42.setBounds(30, 220, 160, 15);

    jPanel11.setBackground(new java.awt.Color(255, 255, 204));
    jPanel11.setLayout(null);

    jPanel35.setOpaque(false);
    jPanel35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jButton28.setText(bundle.getString("CTModerator.jButton28.text")); 
    jButton28.setToolTipText(bundle.getString("CTModerator.jButton28.toolTipText")); 
    jButton28.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton28ActionPerformed(evt);
      }
    });
    jPanel35.add(jButton28);

    jButton36.setText(bundle.getString("CTModerator.jButton36.text")); 
    jButton36.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton36ActionPerformed(evt);
      }
    });
    jPanel35.add(jButton36);

    jPanel11.add(jPanel35);
    jPanel35.setBounds(10, 30, 620, 40);

    jPanel37.setOpaque(false);
    jPanel37.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jButton32.setText(bundle.getString("CTModerator.jButton32.text")); 
    jButton32.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton32ActionPerformed(evt);
      }
    });
    jPanel37.add(jButton32);

    jButton33.setText(bundle.getString("CTModerator.jButton33.text")); 
    jButton33.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton33ActionPerformed(evt);
      }
    });
    jPanel37.add(jButton33);

    jButton15.setText(bundle.getString("CTModerator.jButton15.text")); 
    jButton15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton15ActionPerformed(evt);
      }
    });
    jPanel37.add(jButton15);

    jButton16.setText(bundle.getString("CTModerator.jButton16.text")); 
    jButton16.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton16ActionPerformed(evt);
      }
    });
    jPanel37.add(jButton16);

    jPanel11.add(jPanel37);
    jPanel37.setBounds(10, 70, 970, 40);

    jPanel4.add(jPanel11);
    jPanel11.setBounds(20, 210, 1140, 130);

    jTabbedPane2.addTab(bundle.getString("CTModerator.jPanel4.TabConstraints.tabTitle"), jPanel4); 

    moderatorPanel.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

    jTabbedPane1.addTab(bundle.getString("CTModerator.moderatorPanel.TabConstraints.tabTitle"), moderatorPanel); 

    statusPanel.setLayout(new java.awt.BorderLayout());

    jTextArea2.setColumns(20);
    jTextArea2.setLineWrap(true);
    jTextArea2.setRows(5);
    jScrollPane2.setViewportView(jTextArea2);

    statusPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);
    statusPanel.add(jTextField4, java.awt.BorderLayout.PAGE_END);

    status_upperPanel.setBackground(new java.awt.Color(102, 102, 102));
    status_upperPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel36.setForeground(new java.awt.Color(255, 255, 255));
    jLabel36.setText(bundle.getString("CTModerator.jLabel36.text")); 
    status_upperPanel.add(jLabel36);

    status_upperPanel.add(cbb_moderatorStatusList);

    jButton1.setText(bundle.getString("CTModerator.jButton1.text")); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    status_upperPanel.add(jButton1);

    jButton41.setText(bundle.getString("CTModerator.jButton41.text")); 
    jButton41.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton41ActionPerformed(evt);
      }
    });
    status_upperPanel.add(jButton41);

    jButton42.setText(bundle.getString("CTModerator.jButton42.text")); 
    jButton42.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton42ActionPerformed(evt);
      }
    });
    status_upperPanel.add(jButton42);

    jButton3.setText(bundle.getString("CTModerator.jButton3.text")); 
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton3ActionPerformed(evt);
      }
    });
    status_upperPanel.add(jButton3);

    jButton31.setText(bundle.getString("CTModerator.jButton31.text")); 
    jButton31.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton31ActionPerformed(evt);
      }
    });
    status_upperPanel.add(jButton31);

    jButton17.setText(bundle.getString("CTModerator.jButton17.text")); 
    jButton17.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton17ActionPerformed(evt);
      }
    });
    status_upperPanel.add(jButton17);

    status_checkBox1.setForeground(new java.awt.Color(255, 255, 255));
    status_checkBox1.setText(bundle.getString("CTModerator.status_checkBox1.text")); 
    status_checkBox1.setOpaque(false);
    status_upperPanel.add(status_checkBox1);

    statusPanel.add(status_upperPanel, java.awt.BorderLayout.NORTH);

    jTabbedPane1.addTab(bundle.getString("CTModerator.statusPanel.TabConstraints.tabTitle"), statusPanel); 

    getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

    jMenu1.setText(bundle.getString("CTModerator.jMenu1.text")); 
    jMenu1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenu1ActionPerformed(evt);
      }
    });

    jMenuItem5.setText(bundle.getString("CTModerator.jMenuItem5.text")); 
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem5ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem5);

    jMenuItem11.setText(bundle.getString("CTModerator.jMenuItem11.text")); 
    jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem11ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem11);

    jMenuItem12.setText(bundle.getString("CTModerator.jMenuItem12.text")); 
    jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem12ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem12);

    jMenuItem13.setText(bundle.getString("CTModerator.jMenuItem13.text")); 
    jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem13ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem13);

    jMenuItem14.setText(bundle.getString("CTModerator.jMenuItem14.text")); 
    jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem14ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem14);

    jMenuItem15.setText(bundle.getString("CTModerator.jMenuItem15.text")); 
    jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem15ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem15);

    jMenuItem16.setText(bundle.getString("CTModerator.jMenuItem16.text")); 
    jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem16ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem16);

    jMenuItem1.setText(bundle.getString("CTModerator.jMenuItem1.text")); 
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu2.setText(bundle.getString("CTModerator.jMenu2.text")); 

    jMenuItem3.setText(bundle.getString("CTModerator.jMenuItem3.text")); 
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem3ActionPerformed(evt);
      }
    });
    jMenu2.add(jMenuItem3);

    jMenuBar1.add(jMenu2);

    jMenu3.setText(bundle.getString("CTModerator.jMenu3.text")); 
    jMenu3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenu3ActionPerformed(evt);
      }
    });

    jMenuItem8.setText(bundle.getString("CTModerator.jMenuItem8.text")); 
    jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem8ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem8);

    jMenuItem7.setText(bundle.getString("CTModerator.jMenuItem7.text")); 
    jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem7ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem7);

    jMenuItem6.setText(bundle.getString("CTModerator.jMenuItem6.text")); 
    jMenu3.add(jMenuItem6);

    jMenuItem4.setText(bundle.getString("CTModerator.jMenuItem4.text")); 
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem4ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem4);

    jMenuItem10.setText(bundle.getString("CTModerator.jMenuItem10.text")); 
    jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem10ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem10);

    jMenuItem9.setText(bundle.getString("CTModerator.jMenuItem9.text")); 
    jMenu3.add(jMenuItem9);

    jMenuItem2.setText(bundle.getString("CTModerator.jMenuItem2.text")); 
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem2);

    jMenuBar1.add(jMenu3);

    setJMenuBar(jMenuBar1);

    pack();
  }

private void formWindowClosing(java.awt.event.WindowEvent evt) {
  closing();
}
void closing(){
    w.ap.onExit(112);

  System.exit(0);
}
private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator sld_setpanel "+(jCheckBox1.isSelected()? "1":"0")+" "+(jCheckBox2.isSelected()? "1":"0")+" "+(jCheckBox5.isSelected()? "1":"0")+" "+(jCheckBox8.isSelected()? "1":"0");
    String to=(String) cbb_moderatorSldList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator sk_setpanel "+(jCheckBox3.isSelected()? "1":"0")+" "+(jCheckBox4.isSelected()? "1":"0")+" "+(jCheckBox21.isSelected()? "1":"0")+" "+(jCheckBox24.isSelected()? "1":"0");
    String to=(String) cbb_moderatorSkList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator msg_setpanel "+(jCheckBox6.isSelected()? "1":"0")+" "+(jCheckBox7.isSelected()? "1":"0")+" "+(jCheckBox9.isSelected()? "1":"0")+" "+(jCheckBox10.isSelected()? "1":"0");
    String to=(String) cbb_moderatorMsgList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));

}

private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator restart ";
        String to=(String) cbb_moderatorElseList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOne(cmd,  (String)nameIdMap.get(to));
}

private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator shutdown ";
        String to=(String) cbb_moderatorElseList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOne(cmd, (String)nameIdMap.get(to));
}
boolean sld_chkFn(String filter,String filename){
  boolean rtn=false;
  if(filter.equals("*") || filter.equals(".") || filter.equals("*.*") || filter.length()<1){
    filter="*.jpg";
    if(ylib.chkfn(filter,filename)) return true;
    filter="*.jpe";
    if(ylib.chkfn(filter,filename)) return true;
    filter="*.jpeg";
    if(ylib.chkfn(filter,filename)) return true;
    filter="*.gif";
    if(ylib.chkfn(filter,filename)) return true;
    filter="*.bmp";
    if(ylib.chkfn(filter,filename)) return true;
    filter="*.png";
    if(ylib.chkfn(filter,filename)) return true;
  } else if(filter.indexOf(".")<01){
    if(ylib.chkfn(filter+".jpg",filename)) return true;
    if(ylib.chkfn(filter+".jpe",filename)) return true;
    if(ylib.chkfn(filter+".jpeg",filename)) return true;
    if(ylib.chkfn(filter+".gif",filename)) return true;
    if(ylib.chkfn(filter+".bmp",filename)) return true;
    if(ylib.chkfn(filter+".png",filename)) return true;
  } else if(filter.endsWith(".")){
    if(ylib.chkfn(filter+"jpg",filename)) return true;
    if(ylib.chkfn(filter+"jpe",filename)) return true;
    if(ylib.chkfn(filter+"jpeg",filename)) return true;
    if(ylib.chkfn(filter+"gif",filename)) return true;
    if(ylib.chkfn(filter+"bmp",filename)) return true;
    if(ylib.chkfn(filter+"png",filename)) return true;
  } else return ylib.chkfn(filter,filename);
  return rtn;
}
void sld_close(){
       moderator_props.put("sld_file_id","");
       moderator_props.put("sld_dir","");
       moderator_props.put("sld_imagefile_filter","");
       moderator_props.put("sld_zipfile_pw","");
       moderator_props.put("sld_pwencode","");
       moderator_props.put("sld_lastshowtime",format2.format(new Date()));
       moderator_props.put("sld_index","0");
       sld_imgPanel.setImg(null);
       sld_imgPanel.setBG(Color.gray);
       sld_imgs.clear();
}

public void sld_gotoSlide(int inx,boolean syn){
   showPanel(slidePanel);
   jTabbedPane1.setSelectedComponent(slidePanel);
    switch(inx){
         case -1:
           if(sld_currentInx>0) inx=sld_currentInx-1;
           else inx=sld_imgs.size()-1;
           sld_setSlideInx(inx+1);
           break;
         case -2:
           if(sld_currentInx<(sld_imgs.size()-1)) inx=sld_currentInx+1;
           else inx=0;
           sld_setSlideInx(inx+1);
           break;
         case -3:
           inx=sld_imgs.size()-1;
           sld_setSlideInx(inx+1);
           break;
         case -4:
           sld_imgPanel.setBG(Color.black);
           break;
         case -5:
           sld_imgPanel.setBG(Color.white);
           break;
         case -6:
           if(sld_currentFN!=null && sld_currentFN.length()>0){
             sld_dataV.clear();
             sld_dataVs.put(sld_currentFN,sld_dataV);
             sld_imgPanel.setDataV(sld_dataV);
           }
           break;
         case -7:

           sld_close();
           sld_setSlideInx(0,0);
           break;
         default:
           if(inx>(sld_imgs.size()-1)) inx=sld_imgs.size()-1;
           sld_setSlideInx(inx+1);
           break;
        }
       int inx2=0;
       if(inx>-1){
         sld_imgPanel.resetBG();
         for(Iterator it=sld_imgs.keySet().iterator();it.hasNext();){
           String fn=(String)it.next();
           if(inx==inx2){
             sld_img=(Image)sld_imgs.get(fn);
             sld_dataV=(Vector)sld_dataVs.get(fn);
             sld_imgPanel.setImg2(sld_img,sld_dataV,sld_laserDataV);
             sld_currentInx=inx;
             sld_currentFN=fn;
             moderator_props.setProperty("sld_index", ""+inx);
             break;
           }
           inx2++;
         }
       }
       if(syn){
         

         String cmd="performcommand ct.CTModerator sld_syn "+w.e642((String)slides.get(moderator_props.getProperty("sld_file_id").trim()))+" "+inx;        

           String to=(String) cbb_sldMList.getSelectedItem();
         if(tutorMode || w.checkOneVar(auOne_asAMember, 2)){

         if(to==null && to.length()<1) return;
         else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
         else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
         else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));

       } 

       }
}

void sld_setSlideInx(int ttl, int sel){
  sld_skipSlideInxChanged=true;
  cbb_slideInx.removeAllItems();
  if(ttl>0){
  for(int i=0;i<ttl;i++)  cbb_slideInx.addItem(""+(i+1));
  cbb_slideInx.setSelectedItem("1");
  if(sel!=1) cbb_slideInx.setSelectedItem(""+sel);
  jLabel20.setText("/"+ttl+" "+bundle2.getString("CTModerator.jLabel20.text"));
  } else{
    jLabel20.setText(bundle2.getString("CTModerator.jLabel20.text"));
  }
  sld_skipSlideInxChanged=false;
}

void sld_setSlideInx(int sel){
  sld_skipSlideInxChanged=true;
  cbb_slideInx.setSelectedItem(""+sel);
  sld_skipSlideInxChanged=false;
}
byte[] sld_inputStreamToBytes(InputStream is){
  ByteArrayOutputStream buffer = new ByteArrayOutputStream();

  int nRead;
  byte[] data = new byte[16384];
  try{
    while ((nRead = is.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }
    buffer.flush();
  } catch (IOException e){e.printStackTrace();}
  return buffer.toByteArray();
}

/**
* 轉換byte數組為Image
* @param bytes
* @return Image
*/
public static Image sld_bytesToImage(byte[] bytes) {
Image image = Toolkit.getDefaultToolkit().createImage(bytes);
try {
MediaTracker mt = new MediaTracker(new Label());
mt.addImage(image, 0);
mt.waitForAll();
} catch (InterruptedException e) {
e.printStackTrace();
}
return image;
}

void sld_readImages(int inx,String originalId){
  boolean found=false;
  int errCode=0;
  if(moderator_props.getProperty("sld_file_id")!=null && moderator_props.getProperty("sld_file_id").length()>0) {
  if(moderator_props.getProperty("sld_dir")==null || moderator_props.getProperty("sld_dir").length()<1) moderator_props.put("sld_dir", System.getProperty("user.dir"));
  if(moderator_props.getProperty("sld_imagefile_filter")==null || moderator_props.getProperty("sld_imagefile_filter").length()<1)  moderator_props.put("sld_imagefile_filter","*");
  sld_currentDir=moderator_props.getProperty("sld_dir");
  String file_id=moderator_props.getProperty("sld_file_id"),fn2="";
  sld_dataV.clear(); sld_laserDataV.clear();
  if(moderator_props.getProperty("sld_file_id")!=null && (moderator_props.getProperty("sld_file_id").toLowerCase().indexOf("http://")==0 ||
       moderator_props.getProperty("sld_file_id").toLowerCase().indexOf("https://")==0)){
      sld_currentDir="apps"+File.separator+"cr-tutor"+File.separator+"slides"+File.separator+System.currentTimeMillis();
      new File(sld_currentDir).mkdirs();
      int lastInx=file_id.lastIndexOf("/");
      if(lastInx>-1 && lastInx<file_id.length()){
        fn2=sld_currentDir+File.separator+file_id.substring(lastInx+1);
      }
      if(w.ap.urlfiletodisk(moderator_props.getProperty("sld_file_id"), fn2)){
      if(file_id.toLowerCase().indexOf(".zip")==file_id.length()-4){

        if(moderator_props.getProperty("sld_zipfile_pw")==null || moderator_props.getProperty("sld_zipfile_pw").length()<1){
          sld_unzipContent(inx,fn2);
          found=true;

        } else {
            int cnt2=0;
            try{

              FileInputStream fis = new FileInputStream(fn2);

              String pw=(moderator_props.getProperty("sld_pwencode").equalsIgnoreCase("Y")? w.d642(moderator_props.getProperty("sld_zipfile_pw")):moderator_props.getProperty("sld_zipfile_pw"));
              ZDIS zdis = new ZDIS(fis, pw);

              ZipInputStream zis = new ZipInputStream(zdis);

              ZipEntry ze;
              sld_imgs.clear();
              sld_dataVs.clear();

              while ((ze = zis.getNextEntry()) != null) {

                  String fn3=ze.getName();
                  if(sld_chkFn(moderator_props.getProperty("sld_imagefile_filter"),fn3)){

                    Image img=(fn3.toLowerCase().endsWith(".bmp")? sld_bytesToBmap(sld_inputStreamToBytes(zis)):sld_bytesToImage(sld_inputStreamToBytes(zis)));
                     sld_imgs.put(fn3,img);
                     sld_dataVs.put(fn3,new Vector());

                     cnt2++;
                  }
                zis.closeEntry();
              }
              zis.close();
              found=true;

            } catch(FileNotFoundException e){e.printStackTrace();}
              catch(IOException e){e.printStackTrace();}
            }
      } else {
        String fn5=new File(fn2).getName();
                  if(sld_chkFn(moderator_props.getProperty("sld_imagefile_filter"),fn5)){
                     sld_imgs.clear();

                     sld_imgs.put(fn5,(fn2.toLowerCase().endsWith(".bmp")? sld_bmap(new File(fn2).getParent(),fn5):new ImageIcon(fn2).getImage()));
                     sld_dataVs.put(fn5,new Vector());
                     found=true;

                  }
      }
     }
  } else {
    File datalog=new File(file_id);

    if(datalog.exists()){
      if(datalog.isFile()){
        if(file_id.toLowerCase().indexOf(".zip")==file_id.length()-4){

        if(moderator_props.getProperty("sld_zipfile_pw")==null || moderator_props.getProperty("sld_zipfile_pw").length()<1){
          sld_unzipContent(inx,datalog.getAbsolutePath());
          found=true;

        } else {
            int cnt2=0;
            try{

              FileInputStream fis = new FileInputStream(datalog);

              String pw=(moderator_props.getProperty("sld_pwencode").equalsIgnoreCase("Y")? w.d642(moderator_props.getProperty("sld_zipfile_pw")):moderator_props.getProperty("sld_zipfile_pw"));

              ZDIS zdis = new ZDIS(fis, pw);

              ZipInputStream zis = new ZipInputStream(zdis);

              ZipEntry ze;
              sld_imgs.clear();
              sld_dataVs.clear();

              while ((ze = zis.getNextEntry()) != null) {

                  String fn=ze.getName();

                  if(sld_chkFn(moderator_props.getProperty("sld_imagefile_filter"),fn)){

                     Image img=(fn.toLowerCase().endsWith(".bmp")? sld_bytesToBmap(sld_inputStreamToBytes(zis)):sld_bytesToImage(sld_inputStreamToBytes(zis)));
                     sld_imgs.put(fn,img);
                     sld_dataVs.put(fn,new Vector());

                     cnt2++;
                  }
                zis.closeEntry();
              }
              zis.close();
              found=true;

            } catch(FileNotFoundException e){e.printStackTrace();}
              catch(IOException e){e.printStackTrace();}
            }
        } else {
            String fn4=new File(file_id).getName();
                  if(sld_chkFn(moderator_props.getProperty("sld_imagefile_filter"),fn4)){

                     sld_imgs.clear();

                     sld_imgs.put(fn4,(file_id.toLowerCase().endsWith(".bmp")? sld_bmap(new File(file_id).getParent(),fn4):(file_id.toLowerCase().endsWith(".tif") || file_id.toLowerCase().endsWith(".tiff")? sld_readTiff(file_id):new ImageIcon(file_id).getImage())));
                     sld_dataVs.clear();
                     sld_dataVs.put(fn4,new Vector());
                     found=true;

                  }
        }
      } else {
          int cnt=0;
          File[] contents1=datalog.listFiles();

          sld_imgs.clear();
          sld_dataVs.clear();
          if(contents1!=null && contents1.length>0){
             for(int i=0;i<contents1.length;i++){
               if(contents1[i].isFile()){
                  String fn=contents1[i].getName();
                  if(sld_chkFn(moderator_props.getProperty("sld_imagefile_filter"),fn)){

                     sld_imgs.put(fn,(fn.toLowerCase().endsWith(".bmp")? sld_bmap(contents1[i].getParent(),fn):(fn.toLowerCase().endsWith(".tif") || fn.toLowerCase().endsWith(".tiff")? sld_readTiff(contents1[i].getAbsolutePath()):new ImageIcon(contents1[i].getAbsolutePath()).getImage())));
                     sld_dataVs.put(fn,new Vector());

                     cnt++;
                  }
               }

             }
             found=true;

         }
       }

    }
    else errCode=1;
  }
  }
  if(found){
    sld_opened_file_id=moderator_props.getProperty("sld_file_id");
    sld_gotoSlide(inx,false);
    sld_skipSlideInxChanged=true;
    sld_setSlideInx(sld_imgs.size(),inx+1);
    sld_skipSlideInxChanged=false;
  } else {
    appendStatus(format2.format(new Date())+" : Slide file \""+moderator_props.getProperty("sld_file_id")+"\" not found.(err code="+errCode+")\r\n");
    if(!originalId.equals(w.getGNS(1))){
       String cmd2="performmessage ct.CTModerator status "+w.e642(w.getGNS(27)+" : Slide file \""+moderator_props.getProperty("sld_file_id")+"\" not found.(err ="+errCode+")");
       w.sendToOne(cmd2,originalId);
    }
  }
}
Image sld_readTiff(String fileName){

        Iterator readers = javax.imageio.ImageIO.getImageReadersBySuffix("tiff");
        if (readers.hasNext()) {
            File fi = new File(fileName);
          try{
            ImageInputStream iis = javax.imageio.ImageIO.createImageInputStream(fi);
            TIFFDecodeParam param = null;
            ImageDecoder dec = ImageCodec.createImageDecoder("tiff", fi, param);
            int pageCount = dec.getNumPages();
            ImageReader _imageReader = (ImageReader) (readers.next());
            if (_imageReader != null) {
                _imageReader.setInput(iis, true);
                int count = 1;
                for (int i = 0; i < pageCount; i++) {
                    BufferedImage bufferedImage = _imageReader.read(i);
                    BufferedImage img2 = new BufferedImage(
                            bufferedImage.getWidth(),
                            bufferedImage.getHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        for (int x = 0; x < bufferedImage.getWidth(); x++) {
                            img2.setRGB(x, y, bufferedImage.getRGB(x, y));
                        }
                    }
                  return img2.getScaledInstance(-1, -1, 0);
                }
            }
          }catch(IOException e){
            e.printStackTrace();
          }
        }
        return null;
}
    

  public boolean sld_unzipContent(int inx, String infile){
     File fin=new File(infile);
	 File f3=null;
	 int thisFileN=0, cnt3=0;
  	 try{
  	   if(fin.exists()){
  	    if(fin.length()>0){

            ZipFile zip = new ZipFile(infile);
            sld_imgs.clear();
            sld_dataVs.clear();
            Enumeration zipEnum = zip.entries();
             while (zipEnum.hasMoreElements()){
                ZipEntry item = (ZipEntry) zipEnum.nextElement();

                if (item.isDirectory()){

                }

                else {

                    InputStream is = zip.getInputStream(item);

                  String fn=item.getName();
                  if(sld_chkFn(moderator_props.getProperty("sld_imagefile_filter"),fn)){

                    Image img=(fn.toLowerCase().endsWith(".bmp")? sld_bytesToBmap(sld_inputStreamToBytes(is)):sld_bytesToImage(sld_inputStreamToBytes(is)));
                     sld_imgs.put(fn,img);

                     cnt3++;
                  }
                }
            }
            zip.close();
           } else{
               System.out.println("Warning: The size of zipfile "+infile+" is zero.\r\n");
             }
           } else{
               System.out.println("Warning: zipfile "+infile+" does not exist.\r\n");
             }
        } catch(ZipException e){
	   System.out.println("ZipToDir error: ZipException, infile="+infile+", exception: message: "+e.getMessage()+"\r\n");

			if(sld_dirForBad.length()<1) sld_dirForBad=fin.getParent()+"_bad";

			File f2 =new File(sld_dirForBad);
            if(!f2.exists()){f2.mkdirs();}
			f2=new File(sld_dirForBad+File.separator+fin.getName());
			  if(copyFile(fin,f2)) fin.delete();
			  System.out.println("ZipToDir warning: move "+infile+" to "+f2.getAbsolutePath()+" successfully.\r\n");
            e.printStackTrace();
		}catch (Exception e) {
            System.out.println("ZipToDir error: Exception,infile="+infile+", exception: message: "+e.getMessage()+"\r\n");

            e.printStackTrace();
            return false;
          }

        return true;
  }

public String makeListItem(String name,String id){
  String rtn=name+" ("+id+")";
  if(showType==2){
    rtn=name;
    int count=0;
    Vector sNo=new Vector();
    for(Iterator it=nameIdMap.keySet().iterator();it.hasNext();){
      String key=(String)it.next();
      String id2=(String)nameIdMap.get(key);
      if(id.equals(id2)) {rtn=key; count=0; break;}
      if(key.equals(name)) count++;
      else {
        int inx=key.indexOf("-");
        if(inx>-1 && key.substring(0,inx).equals(name)) {
          count++;
          sNo.add(key.substring(inx+1));
        }
      }
    }
    if(sNo.size()>0){
      if(sNo.size()==count) rtn=rtn;
      else {
        for(int i=0;i<sNo.size();i++){
          i++;
          if(!sNo.contains(String.valueOf(i))) {rtn=rtn+"-"+i; break;}
        }
      }
    } else if(count>0) rtn=rtn+"-"+(count+1);
  }

    return rtn;
}
  private boolean copyFile(File f1, File f2) {
    try{

        long len = f1.length();
        FileInputStream fis = new FileInputStream(f1);
        FileOutputStream fos = new FileOutputStream(f2);
        byte[] bytes = new byte[1024*4];
        int count = fis.read(bytes);
        while(count>0){
          fos.write(bytes,0,count);
          count = fis.read(bytes);
        }
        fis.close();
        fos.close();
        f2.setLastModified( f1.lastModified() );
        return true;

    }  catch(IOException e){

        return false;
	  }

  }
private void sendAu(){
  long tempOne=0;
  if(cbSlideAu1.isSelected()) tempOne=w.addOneVar(tempOne, 14);
  else tempOne=w.removeOneVar(tempOne, 14);
  if(cbSlideAu2.isSelected()) tempOne=w.addOneVar(tempOne, 2);
  else tempOne=w.removeOneVar(tempOne, 2);
  if(cbSketchAu1.isSelected()) tempOne=w.addOneVar(tempOne, 16);
  else tempOne=w.removeOneVar(tempOne, 16);
  if(cbSketchAu2.isSelected()) tempOne=w.addOneVar(tempOne, 3);
  else tempOne=w.removeOneVar(tempOne, 3);
  if(cbMsgAu1.isSelected()) tempOne=w.addOneVar(tempOne, 17);
  else tempOne=w.removeOneVar(tempOne, 17);
  if(cbMsgAu2.isSelected()) tempOne=w.addOneVar(tempOne, 4);
  else tempOne=w.removeOneVar(tempOne, 4);
  if(cbSrnAu1.isSelected()) tempOne=w.addOneVar(tempOne, 7);
  else tempOne=w.removeOneVar(tempOne, 7);
  if(cbSrnAu2.isSelected()) tempOne=w.addOneVar(tempOne, 6);
  else tempOne=w.removeOneVar(tempOne, 6);
  if(cbFileAu1.isSelected()) tempOne=w.addOneVar(tempOne, 15);
  else tempOne=w.removeOneVar(tempOne, 15);
  if(cbFileAu2.isSelected()) tempOne=w.addOneVar(tempOne, 13);
  else tempOne=w.removeOneVar(tempOne, 13);
  if(cbOpenWebAu.isSelected()) tempOne=w.addOneVar(tempOne, 5);
  else tempOne=w.removeOneVar(tempOne, 5);

    String cmd="performcommand ct.CTModerator setau "+tempOne;
    String to=(String) cbb_moderatorPermissionList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}
private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {
  sendAu();
}

private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {
   cbFileAu1.setSelected(false);
   cbFileAu2.setSelected(false);
   cbSketchAu1.setSelected(false);
   cbSketchAu2.setSelected(false);
   cbMsgAu1.setSelected(false);
   cbMsgAu2.setSelected(false);
   cbOpenWebAu.setSelected(false);
   cbSrnAu2.setSelected(false);
   cbSrnAu1.setSelected(false);
   cbSlideAu2.setSelected(false);
   cbSlideAu1.setSelected(false);
   sendAu();
}

private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
  String file_id=jTextField2.getText().trim();  
  if(file_id!=null && file_id.length()>0){
  String filter="";
  String pw=new String(jPasswordField1.getPassword());
  String inxStr=jTextField7.getText().trim(); 
  int inx=(inxStr.trim().length()>0 && isNumeric(inxStr)? Integer.parseInt(inxStr)-1:0);
  if(inx<0) inx=0;
    moderator_props.setProperty("sld_file_id", file_id);
  if(slides.get(file_id)!=null){
    String sldArr[]=ylib.csvlinetoarray((String)slides.get(file_id));
    moderator_props.setProperty("sld_dir", sldArr[1]);
    moderator_props.setProperty("sld_imagefile_filter", (sldArr[2].length()>0? sldArr[2]:"*"));
    moderator_props.setProperty("sld_zipfile_pw", (pw.length()>0? pw:sldArr[3]));
    moderator_props.setProperty("sld_pwencode", (sldArr[4].length()>0? sldArr[4]:"N"));
    moderator_props.setProperty("sld_index", ""+inx);
  } else{
    moderator_props.setProperty("sld_zipfile_pw", pw);
    moderator_props.setProperty("sld_pwencode", "N");
    moderator_props.setProperty("sld_index", ""+inx);
    moderator_props.setProperty("sld_imagefile_filter", "*");
  } 
  sld_setSlideDir(file_id,moderator_props.getProperty("sld_imagefile_filter"));
  if(tutorMode || w.checkOneVar(auOne_asAMember,2)){
    

    String cmd="performcommand ct.CTModerator sld_reload "+w.e642((String)slides.get(file_id))+" "+moderator_props.getProperty("sld_index");
    String to=(String) cbb_moderatorSldList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
  }
  sld_readImages(inx,w.getGNS(1));
  }
}

void sld_setSlideDir(String file_id,String filter){
  if(file_id==null) file_id="";
  file_id=w.fileSeparator(file_id);  
  String dir=file_id;
  if(!file_id.toLowerCase().trim().startsWith("http")){
    String userDir=System.getProperty("user.dir");
  dir=w.fileSeparator(dir);
  int lastInx=dir.lastIndexOf(File.separator);
  int dnx=dir.indexOf(".");
  if(dir.length()==0 || dir.equals(".") || dir.equals("*") || dir.equals("*.*")) {
    dir=userDir;
    if(filter.length()<1) filter="*";
  } else  if(lastInx==-1 && dnx>0 && !dir.toLowerCase().endsWith(".zip")){
    if(filter.length()<1) filter=dir;
    dir=userDir;
  } else if(dir.toLowerCase().endsWith(".zip")){
    dir=dir.substring(0, lastInx);
     if(filter.length()<1) filter="*";
    } else if(dnx==-1){
       if(dir.length()==(lastInx+1)) dir=dir.substring(0, lastInx);
       if(filter.length()<1) filter="*";
    } else {
       filter="*.*";
       dir=dir.substring(0, lastInx);
       if(filter.equals("*.*") || filter.equals(".")) filter="*";
    }

   if(dir.equals(userDir)) dir="";
   else if(dir.indexOf(userDir)==0) dir=dir.substring(userDir.length());
   moderator_props.setProperty("sld_dir", dir);
   moderator_props.setProperty("sld_imagefile_filter", filter);

   String str=(String)slides.get(file_id);
   if(str!=null){
     String arr[]=ylib.csvlinetoarray(str);
     arr[1]=dir;
     arr[2]=filter;
     slides.put(file_id,ylib.arrayToCsvLine(arr));
   } else {
     String arr[]=new String[7];
     arr[0]=file_id;
     arr[1]=dir;
     arr[2]=filter;
     arr[3]="";
     arr[4]="N";
     arr[5]="";
     arr[6]="0";
     slides.put(file_id,ylib.arrayToCsvLine(arr));
     }
  }
}
String getNameFromId(String id){
    Iterator it=nameIdMap.keySet().iterator();
    for(;it.hasNext();){
      String key=(String)it.next();
      if(((String)nameIdMap.get(key)).equals(id)) return key; 
    }
    return null;
}
void setRemoteTutor(String id){
  if(!lattestRemoteTutorId.equals(id)){
  lattestRemoteTutorId=id;
  if(id.length()>0 && (lattestRemoteTutorName.length()<1 || !id.equals((String)nameIdMap.get(lattestRemoteTutorName)))){
    lattestRemoteTutorName=getNameFromId(id);
  }
  }
}

    public void sk_sendCmd(int values []){
    String cmd="performcommand ct.CTModerator sk_draw2 "+ylib.arrayToCsvLine(values);
    String  to=(String) cbb_skMList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOne(cmd, (String)nameIdMap.get(to));

  }
private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator sld_clear";
    String to=(String) cbb_sldMList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {
    String url=jTextField5.getText().trim();
    if(url.length()>0){
      if(url.toLowerCase().indexOf("http")!=0 && url.toLowerCase().indexOf("file")!=0 && url.toLowerCase().indexOf("://")==-1) url="http://"+url;
      String cmd="performcommand ct.CTModerator msg_openurl "+w.e642(url)+" "+w.getGNS(27);
      String to=(String) cbb_moderatorElseList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
    } else JOptionPane.showMessageDialog(this, "Please input url address.\nFor example: http://www.google.com/abc.htm");
}

private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator srn_setpanel "+(jCheckBox15.isSelected()? "1":"0")+" "+(jCheckBox16.isSelected()? "1":"0")+" "+(jCheckBox11.isSelected()? "1":"0")+" "+(jCheckBox12.isSelected()? "1":"0");
    String to=(String) cbb_moderatorSrnList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator sk_clear";
    String to=(String) cbb_moderatorSkList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {
 msg_clear_to();
}

private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {
    String url=jTextField8.getText().trim();
    String file=jTextField9.getText().trim();
    if(url.length()>0 && file.length()>0){
      if(url.toLowerCase().indexOf("http")!=0 && url.toLowerCase().indexOf("file")!=0 && url.toLowerCase().indexOf("://")==-1) url="http://"+url;
      String cmd="performcommand ct.CTModerator msg_geturlfile "+w.e642(url)+" "+w.e642(file)+" "+w.getGNS(27);
      String to=(String) cbb_moderatorElseList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
    } else {
      if(url.length()==0 ) JOptionPane.showMessageDialog(this, "Please input url address.\nFor example: http://www.google.com/abc.pdf");
      else if(file.length()==0 ) JOptionPane.showMessageDialog(this, "Please input local file name.\nFor example: pub"+File.separator+"xyz.pdf");
    }
}

private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator srn_clear";
    String to=(String) cbb_moderatorSrnList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator srn_startviewtutorscreen";
    String to=(String) cbb_moderatorSrnList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator srn_stopviewtutorscreen";
    String to=(String) cbb_moderatorSrnList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator setallpanelinvisible ";
    String to=(String) cbb_moderatorStatusList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd,w.getGNS(11));
    else if(to.equals(myNodeName)) {}
    else w.sendToOneInGroup(cmd, w.getGNS(11),(String)nameIdMap.get(to));
}

private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {
  int showcontrolpanel=1,fixtosrn=1;
  if(jCheckBox19.isSelected()) showcontrolpanel=1; else showcontrolpanel=0;
  if(jCheckBox20.isSelected()) fixtosrn=1; else fixtosrn=0;
  String cmd="performcommand ct.CTModerator srn_setboard "+showcontrolpanel+" "+fixtosrn;
  String to=(String) cbb_moderatorSrnList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator sld_starttobeaslidepresenter";
    String to=(String) cbb_moderatorSldList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator sld_stopbeingaslidepresenter";
    String to=(String) cbb_moderatorSldList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
}

private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {
  jTextArea2.setText("");
}

private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {
 closing();
}

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
  if(!p_about.isVisible()) p_about.setVisible(true);
  if(p_rc.length()>0){
    p_about.jTabbedPane1.remove(p_about.jPanel2);
  }
}

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
 closing();
}

private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {
  openURL.open("https://groups.google.com/d/forum/cr-tutor");
}

private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
  p_toRegister();   
}

void p_resetRegisterTime(){

  p_statuses[46]="";
  p_statuses[47]="";
  p_statuses[7]=format4.format(new Date());
  p_statuses[42]="";
  p_statuses[43]="";
}

boolean p_chkActivate(){
  boolean activate=false;
  long now=System.currentTimeMillis(),firstP=2592000000L,
          secondP=2592000000L;

  if(tutorMode && p_rc.length()>0 && p_statuses[5].length()<5){
    if(p_statuses[44].length()>0){
      long rTime=Long.parseLong(p_statuses[44]);
      if(p_statuses[45].length()>0){
          long aTime=Long.parseLong(p_statuses[45]);
          if((now-aTime)>secondP) activate=true;
      } else {
          if((now-rTime)<firstP) activate=true;
        }
    } else {
        if(p_statuses[45].length()>0){
          long aTime=Long.parseLong(p_statuses[45]);
          if((now-aTime)>secondP) activate=true;
        } else activate=true;
    }
  } 

  return activate;
}
void p_toActivate(){
  if(p_au==null){
    p_au=new CTAutoUpdate(this);
    p_au.start();
  }

  p_au.setAction("1");
}
void p_toActivateAgain(){

  if(aa==null) aa=new CTActivateAgain(this,true);
  aa.setVisible(true);

}
void p_toActivateAgain2(String email){
  if(p_au==null){
    p_au=new CTAutoUpdate(this);
    p_au.start();
  }
  p_au.setAction("2,"+email);
}
public void p_toRegister(){

  if(!bundle2.getString("CTModerator.xy.msg11").equalsIgnoreCase(w.d16("hehh"))){ 
    if(p_register==null){
      p_register=new CTRegister(this,true);
    } else {p_register.setVisible(true);}    
  } else {
    if(p_register2==null){
      p_register2=new CTRegister2(this,true);
    } else {p_register2.setAction("1"); p_register2.setVisible(true); }   

  }
  if(p_chkRK(2)) p_resetRegisterTime();
}

boolean p_chkShowAbout(){
  long t_p=2592000000L,
       t_t=324000000L,
       a_p=604800000L,
       a_t=75600000L;
  int t_c=150,
      a_c=35;
  if(!tutorMode || (p_rc.length()>0 && p_statuses[5].length()>0)) return false;
  long now=System.currentTimeMillis(),firstTime=0L,lastTimeA=0L;
  boolean alert=false;
  if(p_statuses[7].length()<5){
    if(p_statuses[44].length()>0){
      try{
      firstTime=format4.parse(p_statuses[44]).getTime();
      long tp=now-firstTime;

      if(tp>t_p) alert=true;
    } catch(ParseException e){e.printStackTrace();}
    if(!alert && p_statuses[46].length()>0){
      long tc=Long.parseLong(p_statuses[46]);
      if(tc>t_c) alert=true;
    }
    if(!alert && p_statuses[47].length()>0){
      long tt=Long.parseLong(p_statuses[47]);
      if(tt>t_t) alert=true;
    }
    } else {
    if(p_statuses[0].length()>0){
      try{
      firstTime=format4.parse(p_statuses[0]).getTime();
      long tp=now-firstTime;

      if(tp>t_p) alert=true;
    } catch(ParseException e){

      System.out.println("ParseException in CTModerator.p_chkShowAbout(), p_statuses[0]=\""+p_statuses[0]+"\"");
      p_statuses[0]=format4.format(new Date());
    }
    if(!alert && p_statuses[2].length()>0){
      long tc=Long.parseLong(p_statuses[2]);
      if(tc>t_c) alert=true;
    }
    if(!alert && p_statuses[4].length()>0){
      long tt=Long.parseLong(p_statuses[4]);
      if(tt>t_t) alert=true;
    }
    }
    }
  }
   else{
     try{
       lastTimeA=format4.parse(p_statuses[7]).getTime();
       long ap=now-lastTimeA;

       if(ap>a_p) alert=true;
     } catch(ParseException e){e.printStackTrace();}
    if(!alert && p_statuses[42].length()>0){
      long ac=Long.parseLong(p_statuses[42]);
      if(ac>a_c) alert=true;
    }
    if(!alert && p_statuses[43].length()>0){
      long at=Long.parseLong(p_statuses[43]);
      if(at>a_t) alert=true;
    }
  }
  return alert;
}

boolean p_chkRK(int type){
  CTActivateAgain js=new CTActivateAgain(this,true);
  boolean rtn=false;
    if(new File(p_rcFile).exists()){
      try{
      FileInputStream  in=new FileInputStream(p_rcFile);
      BufferedReader d= new BufferedReader(new InputStreamReader(in));
        while(true){
	  String str1=d.readLine();
	  if(str1==null) {in.close(); d.close(); break; }
          if(str1.length()>0){
            p_rc=str1;
            if(p_isValidRK(p_rc)) {
              p_statuses[12]=p_rc;

            }
            else {
              p_rc=""; 
              p_statuses[12]="";
              appendStatus("Info: registration key is invalid.\r\n");
            }
          }
        }
	in.close();
	d.close();
    } catch (IOException e) { e.printStackTrace();}
   }
    if(p_rc.length()>0){
      jMenuItem4.setVisible(false);
      jMenuItem10.setVisible(false);
      if(type==1 || type==3) JOptionPane.showMessageDialog(this, "Info: Check registration key successfully.");
      rtn=true;
    } else {if(type==1 || type==4) JOptionPane.showMessageDialog(this, "Info: Check registration key failed."); rtn=false;}
    return rtn;
}

private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {

         String webAddr="http://www.cloud-rain.com/web/cr-tutor/doc/"+bundle2.getString("CTModerator.xy.msg10");
         if(webAddr.indexOf("http")==-1){
             webAddr=webAddr.substring(5);
             webAddr=webAddr.replace('/', File.separatorChar);
             webAddr=(new File(webAddr)).getAbsolutePath();
             webAddr="file:///"+webAddr.replace(File.separatorChar,'/');
         }

         openURL.open(webAddr);
}

private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
  CTOptions options=new CTOptions(this,true);
}

private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {

}

private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {
 p_toActivateAgain();
}

private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {

}

private void jCheckBox16ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox16.isSelected()) jCheckBox15.setSelected(true);
}

private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {
  String cmd="performcommand ct.CTModerator closemoderator ";
  String to=(String) cbb_moderatorElseList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName)) w.sendToGroupExceptMyself(cmd, w.getGNS(11));
  else if(to.equals(myNodeName)) {}
  else w.sendToOneInGroupExceptMyself(cmd, w.getGNS(11),(String)nameIdMap.get(to));
}

private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {
  String cmd="performcommand ct.CTModerator openmoderator ";
  String to=(String) cbb_moderatorElseList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName)) w.sendToGroupExceptMyself(cmd, w.getGNS(11));
  else if(to.equals(myNodeName)) {}
  else w.sendToOneInGroupExceptMyself(cmd, w.getGNS(11),(String)nameIdMap.get(to));
}

  private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {

    thisRect=this.getBounds();
    thisState=this.getExtendedState();
    this.setVisible(false);
   try{
       Thread.sleep(1500L);
   }catch(InterruptedException e){}
   srn_sendSrnImg2();
   try{
       Thread.sleep(500L);
   }catch(InterruptedException e){}
   this.setVisible(true);
   this.setExtendedState(thisState);
   if(thisState!=JFrame.MAXIMIZED_BOTH) this.setBounds(thisRect);
  }

    private void jCheckBox23ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox23.isSelected()) {jCheckBox22.setSelected(true); jCheckBox17.setSelected(true);}
    }

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator f_setpanel "+(jCheckBox22.isSelected()? "1":"0")+" "+(jCheckBox23.isSelected()? "1":"0")+" "+(jCheckBox17.isSelected()? "1":"0")+" "+(jCheckBox18.isSelected()? "1":"0");
    String to=(String) cbb_moderatorFList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
    }

  private void cbb_skMListActionPerformed(java.awt.event.ActionEvent evt) {
    if(cbb_skMList.getSelectedItem()!=null)   sk_currentId=(String)nameIdMap.get((String)cbb_skMList.getSelectedItem());
  }

  private void clearActionPerformed(java.awt.event.ActionEvent evt) {
    sk_dataV.clear();
    skPanel2.setData(sk_dataV);
    if(!(w.checkOneVar(auOne_asAMember, 3) ||  tutorMode)) return;
    String cmd="performcommand ct.CTModerator sk_clear";
    String  to=(String) cbb_skMList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOne(cmd, (String)nameIdMap.get(to));
  }

  private void slidePanelKeyReleased(java.awt.event.KeyEvent evt) {

  }

  private void slidePanelMouseClicked(java.awt.event.MouseEvent evt) {

  }

  private void slidePanelMousePressed(java.awt.event.MouseEvent evt) {

  }

  private void slidePanelMouseReleased(java.awt.event.MouseEvent evt) {

  }

  private void slidePanelMouseDragged(java.awt.event.MouseEvent evt) {

  }

  private void msg_btnSaveAsActionPerformed(java.awt.event.ActionEvent evt) {
    String file="datalog.txt";
    String str1=msg_textPane.getText().trim();
    if(str1.length()<1){JOptionPane.showMessageDialog(this, "No message data to be saved!"); return;}
    JFileChooser chooser = new JFileChooser(file);
    chooser.setDialogTitle("save message data");
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if(file.length()>0){
      chooser.setSelectedFile(new File(new File(file).getName()));
    }
    int returnVal = chooser.showDialog(this,"Confirm");
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      file=chooser.getSelectedFile().getAbsolutePath();
      File f2=new File(file);
      boolean save=false;
      if(f2.exists()){
        int result = JOptionPane.showConfirmDialog(this, "Overwrite old file ?", "Confirm Overwrite", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) save=true; else save=false;
      } else save=true;
      if(save){
        try{
          FileOutputStream out = new FileOutputStream (file);
          byte [] b=str1.getBytes();
          out.write(b);
          out.close();
        }catch(IOException e){e.printStackTrace();}
      }
    }
  }

  private void msg_btnClearActionPerformed(java.awt.event.ActionEvent evt) {

    msg_clear();
  }

  private void msg_btnPlusActionPerformed(java.awt.event.ActionEvent evt) {
    int si=msg_textPane.getFont().getSize();
    java.awt.Font f1=new java.awt.Font(msg_textPane.getFont().getName(),msg_textPane.getFont().getStyle(),si+1);
    msg_textPane.setFont(f1);
  }

  private void msg_btnMinusActionPerformed(java.awt.event.ActionEvent evt) {
    int si=msg_textPane.getFont().getSize();
    if(si>2){
      java.awt.Font f1=new java.awt.Font(msg_textPane.getFont().getName(),msg_textPane.getFont().getStyle(),si-1);
      msg_textPane.setFont(f1);
    }
  }

  private void msg_button10ActionPerformed(java.awt.event.ActionEvent evt) {
    String file="draft.txt";
    JFileChooser chooser = new JFileChooser(file);
    chooser.setDialogTitle("save draft");
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if(file.length()>0){
      chooser.setSelectedFile(new File(new File(file).getName()));
    }
    int returnVal = chooser.showDialog(this,bundle2.getString("CTModerator.xy.msg43"));
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      file=chooser.getSelectedFile().getAbsolutePath();
      File f2=new File(file);
      boolean save=false;
      if(f2.exists()){
        int result = JOptionPane.showConfirmDialog(this, bundle2.getString("CTModerator.xy.msg44"),bundle2.getString("CTModerator.xy.msg45"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) save=true; else save=false;
      } else save=true;
      if(save){

        try{
          FileOutputStream out = new FileOutputStream (file,false);
          String content=msg_textArea1.getText().trim();
          content = content.replaceAll("(?!\\r)\\n", "\r\n");
          byte [] b=content.getBytes();
          out.write(b);
          out.close();
        }catch(IOException e){e.printStackTrace();}
      }
    }
  }

  private void msg_button9ActionPerformed(java.awt.event.ActionEvent evt) {
    msg_textArea1.setText("");
  }

  private void msg_button8ActionPerformed(java.awt.event.ActionEvent evt) {
    int si=msg_textArea1.getFont().getSize();
    java.awt.Font f1=new java.awt.Font(msg_textArea1.getFont().getName(),msg_textArea1.getFont().getStyle(),si+1);
    msg_textArea1.setFont(f1);
  }

  private void msg_button7ActionPerformed(java.awt.event.ActionEvent evt) {
    int si=msg_textArea1.getFont().getSize();
    if(si>2){
      java.awt.Font f1=new java.awt.Font(msg_textArea1.getFont().getName(),msg_textArea1.getFont().getStyle(),si-1);
      msg_textArea1.setFont(f1);
    }
  }

  private void msg_textField1ActionPerformed(java.awt.event.ActionEvent evt) {
    String s=msg_textField1.getText();
    msg_showTime=jCheckBox14.isSelected();
    msg_showMember=jCheckBox13.isSelected();
    if (s.length()>0 && (w.checkOneVar(auOne_asAMember, 4)  || tutorMode)){
         msg_textPaneAppend((msg_showTime? format3.format(new Date())+" ":"")+(msg_showMember? w.getGNS(27)+": ":"") +s+"\r\n",(Color)jComboBox7.getSelectedItem(),0);
         if((w.checkOneVar(auOne_asAMember, 5) || tutorMode) && s.toLowerCase().indexOf("http")==0 && s.toLowerCase().indexOf("://")!=-1 && s.indexOf(" ")==-1) openURL.open(s);

       msg_sendMsg(s+sep+jCheckBox14.isSelected()+sep+jCheckBox13.isSelected()+sep+((Color)jComboBox7.getSelectedItem()).getRGB());
       msg_textField1.setText("");
    }
  }

  private void msg_btn_sendMsgActionPerformed(java.awt.event.ActionEvent evt) {
    String s=msg_textField1.getText();
    msg_showTime=jCheckBox14.isSelected();
    msg_showMember=jCheckBox13.isSelected();
    if (s.length()>0 && (w.checkOneVar(auOne_asAMember, 4)  || tutorMode)){
         msg_textPaneAppend((msg_showTime? format3.format(new Date())+" ":"")+(msg_showMember? w.getGNS(27)+": ":"") +s+"\r\n",(Color)jComboBox7.getSelectedItem(),0);
         if((w.checkOneVar(auOne_asAMember, 5) || tutorMode) && s.toLowerCase().indexOf("http")==0 && s.toLowerCase().indexOf("://")!=-1 && s.indexOf(" ")==-1) openURL.open(s);
       msg_sendMsg(s+sep+jCheckBox14.isSelected()+sep+jCheckBox13.isSelected()+sep+((Color)jComboBox7.getSelectedItem()).getRGB());
       msg_textField1.setText("");
    }
  }

  private void msg_btnSendDraftActionPerformed(java.awt.event.ActionEvent evt) {
    String s=msg_textArea1.getText();
    msg_showTime=jCheckBox14.isSelected();
    msg_showMember=jCheckBox13.isSelected();
    if (s.length()>0 && (w.checkOneVar(auOne_asAMember, 4)  || tutorMode)){
         msg_textPaneAppend((msg_showTime? format3.format(new Date())+" ":"")+(msg_showMember? w.getGNS(27)+": ":"") +s+"\r\n",(Color)jComboBox7.getSelectedItem(),0);
         if((w.checkOneVar(auOne_asAMember, 5) || tutorMode) && s.toLowerCase().indexOf("http")==0 && s.toLowerCase().indexOf("://")!=-1 && s.indexOf(" ")==-1) openURL.open(s);

      msg_sendMsg(s+sep+jCheckBox14.isSelected()+sep+jCheckBox13.isSelected()+sep+((Color)jComboBox7.getSelectedItem()).getRGB());

    }
  }

  private void cbb_msgMListActionPerformed(java.awt.event.ActionEvent evt) {
    if(cbb_msgMList.getSelectedItem()!=null)  msg_currentId=(String)nameIdMap.get((String)cbb_msgMList.getSelectedItem());
  }

  private void cbb_srnMListActionPerformed(java.awt.event.ActionEvent evt) {
    if(cbb_srnMList.getSelectedItem()!=null)   {
      srn_currentName=(String)cbb_srnMList.getSelectedItem();
      srn_currentId=(String)nameIdMap.get(srn_currentName);

    if(btn_srnStart.getText().equals(srn_stopStr)){
      srn_setEnv();
      srn_issueRequest();
      srn_inform(1);
    }
    }
  }

  private void btn_srnStartActionPerformed(java.awt.event.ActionEvent evt) {
    srn_pressButton();
  }

  private void rb_srnMonitorActionPerformed(java.awt.event.ActionEvent evt) {
    if(btn_srnStart.getText().equals(srn_stopStr)) {
      if(!srn_toInform) {
        srn_setEnv();
        srn_issueRequest();
        srn_toInform=true;
      }
    }
    jLabel2.requestFocusInWindow();
  }

  private void rb_srnControlAndImageActionPerformed(java.awt.event.ActionEvent evt) {
    if(btn_srnStart.getText().equals(srn_stopStr)) {
      if(!srn_toInform) {
        srn_setEnv();
        srn_issueRequest();
        srn_toInform=true;
      }
    }
    jLabel2.requestFocusInWindow();
  }

  private void btn_srnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
    srn_refresh();
    srn_inform(3);
  }

  private void btn_srnACDActionPerformed(java.awt.event.ActionEvent evt) {
    if(rb_srnControlAndImage.isSelected()){

      int type=1;
      int id=402;
      int code=127;
      int modifier=0;
      String cmd="performcommand ct.CTRMAction2 rm_key2 "+type+" "+id+" "+code+" "+modifier+" "+srn_currentCode+" %empty% 0 0 0 0 ? ? ? 0";
      String  to=(String) cbb_srnMList.getSelectedItem();
      if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));

      else w.sendToOneInGroup(cmd,w.getGNS(11), (String)nameIdMap.get(to));

      type=2;
      id=evt.getID();
      code=127;
      modifier=0;
      cmd="performcommand ct.CTRMAction2 rm_key2 "+type+" "+id+" "+code+" "+modifier+" "+srn_currentCode+" %empty% 0 0 0 0 ? ? ? 0";
      to=(String) cbb_srnMList.getSelectedItem();
      if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));

      else w.sendToOneInGroup(cmd,w.getGNS(11), (String)nameIdMap.get(to));
    }
  }

  private void cb_srnFixActionPerformed(java.awt.event.ActionEvent evt) {
    if(cb_srnFix.isSelected()) srn_fixToSrn=true; else srn_fixToSrn=false;

    jLabel2.requestFocusInWindow();
  }

  private void btn_fResetLayout1ActionPerformed(java.awt.event.ActionEvent evt) {
    f_h2=0;
    doLayout();
  }

  private void cbb_fList1ItemStateChanged(java.awt.event.ItemEvent evt) {

    if(evt.getStateChange()==evt.SELECTED && !f_skipComboBox1ItemStateChanged){
      String dir=(String)cbb_fList1.getSelectedItem();

      f_updateTable1(dir);
    }
  }

  private void cbb_fList1ActionPerformed(java.awt.event.ActionEvent evt) {

  }

  private void btn_fResetLayout2ActionPerformed(java.awt.event.ActionEvent evt) {
    f_h2=0;
    doLayout();
  }

  private void cbb_fMlistActionPerformed(java.awt.event.ActionEvent evt) {
    if(cbb_fMlist.getSelectedItem()!=null && ((String)cbb_fMlist.getSelectedItem()).length()>0)   {
      f_currentName=(String)cbb_fMlist.getSelectedItem();
      f_currentId=(String)nameIdMap.get(f_currentName);

    String  to=(String) cbb_fMlist.getSelectedItem();
    f_rdb.root.setUserObject(to);
    String cmd="";
    f_currentRandom=""+Math.random();
    cmd="performcommand ct.CTModerator f_getroot "+f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
    f_rdb.infinityNodeId=(String)nameIdMap.get(to);
    w.sendToOne(cmd,f_rdb.infinityNodeId );
    jTextArea1.append(format5.format(new Date())+" "+f_currentName+"@remote\r\n");
    } else if(f_rdb!=null && f_rdb.root!=null){
      DefaultTreeModel model = (DefaultTreeModel)f_rdb.getModel();

      DefaultMutableTreeNode root=(DefaultMutableTreeNode)model.getRoot();
      root.removeAllChildren();
      

      model.reload(root);
      int rcount=f_table2.getModel().getRowCount();
      for(int i=rcount-1;i>-1;i--) {if(f_table2.getModel().getRowCount()>i) ((DefaultTableModel)f_table2.getModel()).removeRow(i);}
      f_skipComboBox2ItemStateChanged=true;

      cbb_fList2.removeAllItems();
      f_skipComboBox2ItemStateChanged=false;
    }
  }

  private void cbb_fList2ItemStateChanged(java.awt.event.ItemEvent evt) {
    if(evt.getStateChange()==evt.SELECTED & !f_skipComboBox2ItemStateChanged){

      String absPath=(String)cbb_fList2.getSelectedItem();
      if(absPath!=null && absPath.length()>0){
        f_current_remote_absolutePath=absPath;
        f_currentRandom=""+Math.random();

        if(!f_lastGetRemoteDir.equals(absPath) || lastGetDir_time+1000L <System.currentTimeMillis()){

        String cmd="performcommand ct.CTModerator f_getdirandfile "+w.e642(absPath)+" "+f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
        w.sendToOne(cmd, f_rdb.infinityNodeId);
        f_lastGetRemoteDir=absPath;
        f_lastGetRemoteNode=null;
        lastGetDir_time=System.currentTimeMillis();
        }
      }
    }
  }

  private void cbb_fList2ActionPerformed(java.awt.event.ActionEvent evt) {
    

  }

  private void f_table1MouseReleased(java.awt.event.MouseEvent evt) {
    if(SwingUtilities.isRightMouseButton(evt)){
      int selRows[]=f_table1.getSelectedRows();
      int r = f_table1.rowAtPoint(evt.getPoint());
      if (r >= 0 && r < f_table1.getModel().getRowCount()){
        boolean selected=false;
        if(selRows.length>0){
          for(int i=0;i<selRows.length;i++){
            if(selRows[i]==r){selected=true; break;}
          }
        }
        if(!selected){
          f_table1.setRowSelectionInterval(r, r);
        }
      } else {
        f_table1.clearSelection();
      }
    }
    

    int rowindex = f_table1.getSelectedRow();

    if (rowindex < 0) return;

    if (SwingUtilities.isRightMouseButton(evt) && evt.getComponent() instanceof JTable ) {
      if (f_table1.getSelectedRows().length > 1) {
        f_itemAddDir1.setVisible(false);
        f_itemRename1.setVisible(false);
      } else {
        String type=(String)f_table1.getModel().getValueAt(rowindex, 1);
        if(type.equals(f_dirType)) f_itemAddDir1.setVisible(true); else f_itemAddDir1.setVisible(false);
        f_itemRename1.setVisible(true);
      }
      if(tutorMode || w.checkOneVar(auOne_asAMember, 13)) f_itemUpload1.setVisible(true); else  f_itemUpload1.setVisible(false);
      f_popupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
    if(evt.getClickCount()==2){
      String type=(String)f_table1.getModel().getValueAt(rowindex, 1);
      String name=(String)f_table1.getModel().getValueAt(rowindex, 0);
      if(type.equals(f_dirType)){
        String absPath=f_current_local_absolutePath+(f_current_local_absolutePath.charAt(f_current_local_absolutePath.length()-1)==File.separator.charAt(0)?"":File.separator)+name;
        CTFBDirNode node=f_browser.getNodeFromNodeAndFile(f_currentLocalNode,new File(absPath));

        if(node!=null){

            DefaultComboBoxModel model = (DefaultComboBoxModel)cbb_fList1.getModel();
            if(model.getIndexOf(absPath) == -1 ) {
              model.addElement(absPath);
            }
            f_skipComboBox1ItemStateChanged=true;
            cbb_fList1.setSelectedItem(absPath);
            f_skipComboBox1ItemStateChanged=false;
            f_current_local_absolutePath=absPath;
            f_currentLocalNode=node;
            f_showLocalDir(absPath, f_local_orderby, f_local_asc);

        }
      } else {

      }
    }
  }

  private void f_table2MouseReleased(java.awt.event.MouseEvent evt) {
    if(SwingUtilities.isRightMouseButton(evt)){
      int selRows[]=f_table2.getSelectedRows();
      int r = f_table2.rowAtPoint(evt.getPoint());
      if (r >= 0 && r < f_table2.getModel().getRowCount()){
        boolean selected=false;
        if(selRows.length>0){
          for(int i=0;i<selRows.length;i++){
            if(selRows[i]==r){selected=true; break;}
          }
        }
        if(!selected){
          f_table2.setRowSelectionInterval(r, r);
        }
      } else {
        f_table2.clearSelection();
      }
    }
    

    int rowindex = f_table2.getSelectedRow();

    if (rowindex < 0) return;
    if (SwingUtilities.isRightMouseButton(evt) && evt.getComponent() instanceof JTable ) {
      if (f_table2.getSelectedRows().length > 1) {
        f_itemAddDir2.setVisible(false);
        f_itemRename2.setVisible(false);
      } else {
         String type=(String)f_table2.getModel().getValueAt(rowindex, 1);
        if(type.equals(f_dirType) && (tutorMode || w.checkOneVar(auOne_asAMember, 13))) f_itemAddDir2.setVisible(true); else f_itemAddDir2.setVisible(false);
        if(tutorMode || w.checkOneVar(auOne_asAMember, 13)) f_itemRename2.setVisible(true);
      }
      if(tutorMode || w.checkOneVar(auOne_asAMember, 13)) f_itemDelete2.setVisible(true); else  f_itemDelete2.setVisible(false);
      f_popupMenu2.show(evt.getComponent(), evt.getX(), evt.getY());
    }
    if(evt.getClickCount()==2){
      String type=(String)f_table2.getModel().getValueAt(rowindex, 1);
      String name=(String)f_table2.getModel().getValueAt(rowindex, 0);
      if(type.equals(f_dirType)){
        String absPath=f_current_remote_absolutePath+(f_current_remote_absolutePath.endsWith(f_remoteFileSeparator)? "":(f_current_remote_absolutePath.length()>0? f_remoteFileSeparator:""))+name;
        CTFBRemoteDirNode node=f_rdb.getNodeFromNodeAndAbsPath(f_currentRemoteNode,absPath);

        f_rdb.setSelectionPath(f_getTreePathFromNode2(node));

            f_current_remote_absolutePath=absPath;

            if(!f_lastGetRemoteDir.equals(absPath) || lastGetDir_time+1000L <System.currentTimeMillis()){
              String cmd="";
              f_currentRandom=""+Math.random();

              cmd="performcommand ct.CTModerator f_getdirandfile "+w.e642(absPath)+" "+f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
              w.sendToOne(cmd, f_rdb.infinityNodeId);
              f_lastGetRemoteDir=absPath;
              f_lastGetRemoteNode=null;
              lastGetDir_time=System.currentTimeMillis();
            }

      } else {

      }
    }
  }

  private void f_panel10MouseEntered(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR | Cursor.N_RESIZE_CURSOR));
  }

  private void f_panel10MouseExited(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  private void f_panel10MousePressed(java.awt.event.MouseEvent evt) {
    int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    f_ssX=mouseX;
    f_ssY=mouseY;

    f_oldW1=f_w1; f_oldW2=f_w2; f_oldH4=f_h4;
    f_oldH3=f_h3; f_oldH2=f_h2; f_oldH6=f_h6; f_oldH5=f_h5;
    f_dragging = true;
  }

  private void f_panel10MouseDragged(java.awt.event.MouseEvent evt) {
    int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    int dif=mouseY-f_ssY;
    int height=this.getContentPane().getHeight();
    f_h6=f_oldH6-dif;
    if(f_h6<3) {f_h6=3; dif=f_oldH6-f_h6;}
    else if(f_h6 >f_oldH6+f_oldH5-3){f_h6=f_oldH6+f_oldH5-3; dif=f_oldH6-f_h6;}
    f_h5=f_oldH6+f_oldH5-f_h6;

    if (f_dragging) {

      relayout=true;
      doLayout();
    }
  }

  private void f_panel11MouseEntered(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR | Cursor.N_RESIZE_CURSOR));
  }

  private void f_panel11MouseExited(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  private void f_panel11MousePressed(java.awt.event.MouseEvent evt) {
    int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    f_ssX=mouseX;
    f_ssY=mouseY;

    f_oldH4=f_h4;
    f_oldH3=f_h3; f_oldH2=f_h2; f_oldH6=f_h6; f_oldH5=f_h5;
    f_dragging = true;

  }

  private void f_panel11MouseReleased(java.awt.event.MouseEvent evt) {
    f_dragging = false;
  }

  private void f_panel11MouseDragged(java.awt.event.MouseEvent evt) {
    int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    int dif=mouseY-f_ssY;
    int height=this.getContentPane().getHeight();
    f_h4=f_oldH4-dif;
    if(f_h4<30) {f_h4=30; dif=f_oldH4-f_h4;}
    else if(f_h4+f_h1+6+8>height){f_h4=height-f_h1-14; dif=f_oldH4-f_h4;}
    f_h3=f_oldH3-(f_h4-f_oldH4);
    if(f_h3<2) {
      f_h3=2;
      f_h2=f_oldH2-((f_h4-f_oldH4)+(f_h3-f_oldH3));
    }
    f_h6=f_oldH6-(f_h4-f_oldH4);
    if(f_h6<2) {
      f_h6=2;
      f_h5=f_oldH5-((f_h4-f_oldH4)+(f_h6-f_oldH6));
    }

    if (f_dragging) {

      relayout=true;
      doLayout();
    }
  }

  private void f_panel12MouseEntered(java.awt.event.MouseEvent evt) {
    this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR | Cursor.W_RESIZE_CURSOR));
  }

  private void f_panel12MouseExited(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  private void f_panel12MousePressed(java.awt.event.MouseEvent evt) {
    int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    f_ssX=mouseX;
    f_ssY=mouseY;

    f_oldW1=f_w1; f_oldW2=f_w2; f_oldH4=f_h4;
    f_oldH3=f_h3; f_oldH2=f_h2; f_oldH6=f_h6; f_oldH5=f_h5;
    f_dragging = true;
  }

  private void f_panel12MouseDragged(java.awt.event.MouseEvent evt) {
    int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    int dif=mouseX-f_ssX;
    int width=this.getContentPane().getWidth();
    f_w1=f_oldW1+dif;
    if(f_w1<3) {f_w1=3; dif=f_w1-f_oldW1;}
    else if(f_w1>width-6){f_w1=width-6; dif=f_w1-f_oldW1;}
    f_w2=width-3-f_w1;

    Rectangle rc1=cbb_fList1.getBounds();
    Rectangle rc2=cbb_fList2.getBounds();

    cbb_fList1.setPreferredSize(new Dimension(f_w1-f_label1.getWidth()-15, rc1.height));
    cbb_fList2.setPreferredSize(new Dimension(f_w2-f_label2.getWidth()-15-1, rc2.height));

    if (f_dragging) {

      relayout=true;
      doLayout();
    }
  }

  private void f_panel13MouseEntered(java.awt.event.MouseEvent evt) {
  this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR | Cursor.N_RESIZE_CURSOR));
  }

  private void f_panel13MouseExited(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  private void f_panel13MousePressed(java.awt.event.MouseEvent evt) {
    int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    f_ssX=mouseX;
    f_ssY=mouseY;

    f_oldW1=f_w1; f_oldW2=f_w2; f_oldH4=f_h4;
    f_oldH3=f_h3; f_oldH2=f_h2; f_oldH6=f_h6; f_oldH5=f_h5;
    f_dragging = true;
  }

  private void f_panel13MouseDragged(java.awt.event.MouseEvent evt) {
    int mouseX = MouseInfo.getPointerInfo().getLocation().x;
    int mouseY = MouseInfo.getPointerInfo().getLocation().y;
    int dif=mouseY-f_ssY;
    int height=this.getContentPane().getHeight();
    f_h3=f_oldH3-dif;
    if(f_h3<3) {f_h3=3; dif=f_oldH3-f_h3;}
    else if(f_h3 >f_oldH3+f_oldH2-3){f_h3=f_oldH3+f_oldH2-3; dif=f_oldH3-f_h3;}
    f_h2=f_oldH3+f_oldH2-f_h3;

    if (f_dragging) {

      relayout=true;
      doLayout();
    }
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    String to=((String) cbb_moderatorStatusList.getSelectedItem()).trim();

    String cmd="performcommand ct.CTModerator getstatus ";
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd,w.getGNS(11));
    else if(to.equals(myNodeName)) {}
    else w.sendToOne(cmd,(String)nameIdMap.get(to));
  }

  private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {

    if(((Component)filePanel).equals(jTabbedPane1.getSelectedComponent())) {
     f_h2=0;

     doLayout();
    }
  }

  private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator setallpanelvisible ";
    String to=(String) cbb_moderatorStatusList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd,w.getGNS(11));
    else if(to.equals(myNodeName)) {}
    else w.sendToOneInGroup(cmd, w.getGNS(11),(String)nameIdMap.get(to));
  }

  private void cbb_moderatorSldListActionPerformed(java.awt.event.ActionEvent evt) {

  }

  private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {

    sld_imgPanel.setBG(Color.white);
    if(tutorMode || w.checkOneVar(auOne_asAMember,2)){
      String cmd="performcommand ct.CTModerator sld_clear";
    String to=(String) cbb_sldMList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
    }
  }

  private void slidePanelKeyPressed(java.awt.event.KeyEvent evt) {

  }

  private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox2.isSelected()) jCheckBox1.setSelected(true);
  }

  private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox7.isSelected()) jCheckBox6.setSelected(true);
  }

  private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox4.isSelected()) jCheckBox3.setSelected(true);
  }

  private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {
   cbFileAu1.setSelected(true);
   cbFileAu2.setSelected(true);
   cbSketchAu1.setSelected(true);
   cbSketchAu2.setSelected(true);
   cbMsgAu1.setSelected(true);
   cbMsgAu2.setSelected(true);
   cbOpenWebAu.setSelected(true);
   cbSrnAu2.setSelected(true);
   cbSrnAu1.setSelected(true);
   cbSlideAu1.setSelected(true);
   cbSlideAu2.setSelected(true);

   sendAu();
  }

  private void formWindowStateChanged(java.awt.event.WindowEvent evt) {

   if ((evt.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED){

   }

   else if ((evt.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH){
    f_h2=0;

    doLayout();

   } else {
    f_h2=0;

    doLayout();

   }

  }

  private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {
    String cmd="performcommand ct.CTModerator checkversion ";
        String to=(String) cbb_moderatorElseList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOne(cmd,  (String)nameIdMap.get(to));
  }

  private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {
    appendStatus("My Status: "+this.getStatus()+"\r\n");
  }

  private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {
   if(sld_dialog==null) sld_dialog=new CTSlidesDialg(this,true);
   sld_dialog.tempSlides=(TreeMap)slides.clone();
   sld_dialog.updateList();
   sld_dialog.setVisible(true);
  }

  private void btn_openSldActionPerformed(java.awt.event.ActionEvent evt) {
  String file_id=(String)cbb_slide.getSelectedItem();
    sld_openSlide(file_id);
  }
public void sld_openSlide(String file_id){

  if(file_id!=null && file_id.length()>0){
    String sldArr[]=ylib.csvlinetoarray((String)slides.get(file_id));

    moderator_props.setProperty("sld_file_id", file_id);
    if(sldArr.length>1) moderator_props.setProperty("sld_dir", sldArr[1]);
    if(sldArr.length>2) moderator_props.setProperty("sld_imagefile_filter", (sldArr[2].length()>0? sldArr[2]:"*"));
    if(sldArr.length>3) moderator_props.setProperty("sld_zipfile_pw", sldArr[3]);
    if(sldArr.length>4) moderator_props.setProperty("sld_pwencode", (sldArr[4].length()>0? sldArr[4]:"N"));
    moderator_props.setProperty("sld_index", "0");
  } else {JOptionPane.showMessageDialog(this,"No slide selected."); return;}
  sld_setSlideDir(file_id,moderator_props.getProperty("sld_imagefile_filter"));
  if(tutorMode || w.checkOneVar(auOne_asAMember,2)){
    

    String cmd="performcommand ct.CTModerator sld_reload "+w.e642((String)slides.get(file_id))+" 0";
    String to=(String) cbb_sldMList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
  }
  sld_readImages(0,w.getGNS(1));
}
  private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
  String file=jTextField2.getText();
  JFileChooser chooser = new JFileChooser(file);
  chooser.setDialogTitle(bundle2.getString("CTModerator.xy.msg12"));
  chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
  if(file.length()>0){
    chooser.setSelectedFile(new File(new File(file).getName()));
  }
  int returnVal = chooser.showDialog(this,"OK");
  if(returnVal == JFileChooser.APPROVE_OPTION) {
      file=chooser.getSelectedFile().getAbsolutePath();
     jTextField2.setText(file);
     jTextField7.setText("1");
     jPasswordField1.setText("");

  }
  }

  private void cbSlideAu2ActionPerformed(java.awt.event.ActionEvent evt) {
    if(cbSlideAu2.isSelected()) cbSlideAu1.setSelected(true);
  }

  private void cbSlideAu1ActionPerformed(java.awt.event.ActionEvent evt) {
      if(!cbSlideAu1.isSelected()) cbSlideAu2.setSelected(false);

  }

  private void cbSketchAu2ActionPerformed(java.awt.event.ActionEvent evt) {
    if(cbSketchAu2.isSelected()) cbSketchAu1.setSelected(true);

  }

  private void cbSketchAu1ActionPerformed(java.awt.event.ActionEvent evt) {
      if(!cbSketchAu1.isSelected()) cbSketchAu2.setSelected(true);

  }

  private void cbFileAu2ActionPerformed(java.awt.event.ActionEvent evt) {
      if(cbFileAu2.isSelected()) cbFileAu1.setSelected(true);

  }

  private void cbFileAu1ActionPerformed(java.awt.event.ActionEvent evt) {
       if(!cbFileAu1.isSelected()) cbFileAu2.setSelected(true);
  }

  private void jCheckBox12ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox12.isSelected()){jCheckBox11.setSelected(true); jCheckBox15.setSelected(true);}
    else if(!jCheckBox11.isSelected() && !jCheckBox12.isSelected()) jCheckBox15.setSelected(false);
  }

  private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox11.isSelected()) jCheckBox15.setSelected(false);
    else if(!jCheckBox11.isSelected()) jCheckBox12.setSelected(false);
    else if(!jCheckBox11.isSelected() && !jCheckBox12.isSelected()) jCheckBox15.setSelected(false);
  }

  private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {
   if(jCheckBox8.isSelected()) {jCheckBox5.setSelected(true); jCheckBox1.setSelected(true);}
  }

  private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox5.isSelected()) jCheckBox1.setSelected(true);
    else if(!jCheckBox5.isSelected()) jCheckBox8.setSelected(false);
  }

  private void jCheckBox18ActionPerformed(java.awt.event.ActionEvent evt) {
if(jCheckBox18.isSelected()) {jCheckBox17.setSelected(true); jCheckBox22.setSelected(true);}
else if(!jCheckBox17.isSelected() && !jCheckBox18.isSelected())  {jCheckBox22.setSelected(false); jCheckBox23.setSelected(false);}
  }

  private void jCheckBox17ActionPerformed(java.awt.event.ActionEvent evt) {
 if(!jCheckBox17.isSelected()) {jCheckBox18.setSelected(false); jCheckBox22.setSelected(false);}
 else jCheckBox22.setSelected(true);
  }

  private void jCheckBox24ActionPerformed(java.awt.event.ActionEvent evt) {
  if(jCheckBox24.isSelected()) {jCheckBox21.setSelected(true); jCheckBox3.setSelected(true);}
  }

  private void jCheckBox21ActionPerformed(java.awt.event.ActionEvent evt) {
   if(jCheckBox21.isSelected()) jCheckBox3.setSelected(true);
   else if(!jCheckBox21.isSelected()) jCheckBox24.setSelected(false);
  }

  private void jCheckBox22ActionPerformed(java.awt.event.ActionEvent evt) {
   if(jCheckBox22.isSelected()) jCheckBox17.setSelected(true);
  }

  private void jCheckBox9ActionPerformed(java.awt.event.ActionEvent evt) {
   if(jCheckBox9.isSelected()) jCheckBox6.setSelected(true);
  }

  private void jCheckBox10ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jCheckBox10.isSelected()) jCheckBox6.setSelected(true);
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

    sld_close();
    if(tutorMode || w.checkOneVar(auOne_asAMember,2)){
    String cmd="performcommand ct.CTModerator sld_close";
    String to=(String) cbb_sldMList.getSelectedItem();
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
    }
  }

  private void msg_btnSendClearActionPerformed(java.awt.event.ActionEvent evt) {
    msg_clear();
    msg_clear_to();
  }

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
    String filename=sketchLogDir+(sketchLogDir.endsWith(File.separator)? "":File.separator)+format4.format(new Date())+".jpg";
    skPanel2.saveImage(filename);
  }

  private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {
    sPanel.saveImage(screenLogDir+(screenLogDir.endsWith(File.separator)? "":File.separator)+format4.format(new Date())+".jpg");
  }

  private void cbb_slideInxActionPerformed(java.awt.event.ActionEvent evt) {

    sld_skipSlideInxChanged=true;
    String sel=(String)cbb_slideInx.getSelectedItem();
    if(sel!=null && isNumeric(sel) && (w.checkOneVar(auOne_asAMember, 2) ||  tutorMode)) {
      int sel2=Integer.parseInt(sel);
      if(sel2>sld_imgs.size()) sel2=sld_imgs.size();
      sld_gotoSlide(Integer.parseInt(sel)-1,true);
    }
    sld_skipSlideInxChanged=false;
  }

  private void cbb_slideInxItemStateChanged(java.awt.event.ItemEvent evt) {
    if(evt.getStateChange()==evt.SELECTED && !sld_skipSlideInxChanged){
      String sel=(String)cbb_slideInx.getSelectedItem();
    if(sel!=null && isNumeric(sel) && (w.checkOneVar(auOne_asAMember, 2) ||  tutorMode)) sld_gotoSlide(Integer.parseInt(sel)-1,true);
    }
  }

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

    w.upd.setActionType(1, version);
  }

  private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
    String filename=sldLogDir+(sldLogDir.endsWith(File.separator)? "":File.separator)+format4.format(new Date())+".jpg";
    sld_imgPanel.saveImage(filename);
  }

  private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {
    String file="statuslog.txt";
    String str1=jTextArea2.getText().trim();
    if(str1.length()<1){JOptionPane.showMessageDialog(this, "No log data to be saved!"); return;}
    JFileChooser chooser = new JFileChooser(file);
    chooser.setDialogTitle("save status log data");
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if(file.length()>0){
      chooser.setSelectedFile(new File(new File(file).getName()));
    }
    int returnVal = chooser.showDialog(this,"Confirm");
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      file=chooser.getSelectedFile().getAbsolutePath();
      File f2=new File(file);
      boolean save=false;
      if(f2.exists()){
        int result = JOptionPane.showConfirmDialog(this, "Overwrite old file ?", "Confirm Overwrite", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) save=true; else save=false;
      } else save=true;
      if(save){
        try{
          FileOutputStream out = new FileOutputStream (file);
          byte [] b=str1.getBytes();
          out.write(b);
          out.close();
        }catch(IOException e){e.printStackTrace();}
      }
    }
  }

  private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {
    File file = new File (sldLogDir);
    if(!file.exists()) file.mkdir();

      CTDT.open(file);

  }

  private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {
    File file = new File (msgLogDir);
    if(!file.exists()) file.mkdir();

      CTDT.open(file);

  }

  private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {
    File file = new File (screenLogDir);
    if(!file.exists()) file.mkdir();

      CTDT.open(file);

  }

  private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {
    File file = new File (fileLogDir);
    if(!file.exists()) file.mkdir();

      CTDT.open(file);

  }

  private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {
       File file = new File (sketchLogDir);
    if(!file.exists()) file.mkdir();

      CTDT.open(file);

  }

  private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {
       File file = new File (statusLogDir);
    if(!file.exists()) file.mkdir();

      CTDT.open(file);

  }

  private void f_panel12MouseMoved(java.awt.event.MouseEvent evt) {
    this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR | Cursor.W_RESIZE_CURSOR));
  }

  private void f_panel10MouseMoved(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR | Cursor.N_RESIZE_CURSOR));
  }

  private void f_panel13MouseMoved(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR | Cursor.N_RESIZE_CURSOR));
  }

  private void f_panel11MouseMoved(java.awt.event.MouseEvent evt) {
 this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR | Cursor.N_RESIZE_CURSOR));
  }

  private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {
    if(evt.getStateChange()==evt.SELECTED){
      msg_textPane.setBackground((Color)jComboBox2.getSelectedItem());
    }
  }

  private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {
    if(evt.getStateChange()==evt.SELECTED){
      int values[]=new int[]{0,0,0,0,0,((Color)jComboBox3.getSelectedItem()).getRGB(),0};
        sk_dataV.add(new CTSketchData(values));
        sk_sendCmd(values);
         skPanel2.setData(sk_dataV);
    }
  }

  private void cbb_srnMListItemStateChanged(java.awt.event.ItemEvent evt) {
    if(evt.getStateChange()==evt.SELECTED && jTabbedPane1.getSelectedComponent().equals(screenPanel)){
       jLabel2.requestFocusInWindow();
    }
  }

  private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {
    restart();
  }

  private void cbb_moderatorPermissionListItemStateChanged(java.awt.event.ItemEvent evt) {
        if(evt.getStateChange()==evt.SELECTED){
          String to=(String)cbb_moderatorPermissionList.getSelectedItem();
          if(to.equals(this.allNodesName)){
               cbFileAu1.setSelected(false);
   cbFileAu2.setSelected(false);
   cbSketchAu1.setSelected(false);
   cbSketchAu2.setSelected(false);
   cbMsgAu1.setSelected(false);
   cbMsgAu2.setSelected(false);
   cbOpenWebAu.setSelected(false);
   cbSrnAu2.setSelected(false);
   cbSrnAu1.setSelected(false);
   cbSlideAu2.setSelected(false);
   cbSlideAu1.setSelected(false);
          } else {
               String cmd="performcommand ct.CTModerator getau 0";
    if(to==null && to.length()<1) return;
    else if(to.equals(allNodesName))  w.sendToGroupExceptMyself(cmd, w.getGNS(11));
    else if(to.equals(myNodeName)) w.sendToOne(cmd, w.getGNS(1));
    else w.sendToOneInGroup(cmd, w.getGNS(11), (String)nameIdMap.get(to));
          }
        }
  }

  private void f_table2MouseClicked(java.awt.event.MouseEvent evt) {
    if(evt.getClickCount()==2){
       int rowindex= f_table2.getSelectedRow();
          TreeMap downloadTM=new TreeMap();

            String name=(String)f_table2.getModel().getValueAt(rowindex, 0);
            downloadTM.put(CTModerator.this.f_current_remote_absolutePath+CTModerator.this.f_remoteFileSeparator+name, "2");
           f_currentRandom=""+Math.random();

           f_download(downloadTM);
    }
  }

  private void f_table1MouseClicked(java.awt.event.MouseEvent evt) {
    if(evt.getClickCount()==2){
        int rowindex= f_table1.getSelectedRow();
          TreeMap uploadTM=new TreeMap();

            String name=(String)f_table1.getModel().getValueAt(rowindex, 0);
            uploadTM.put(CTModerator.this.f_current_local_absolutePath+File.separator+name, "2");
           f_currentRandom=""+Math.random();
           f_upload(uploadTM);
    }
  }

  public void saveImg(Image image,String filename){
     try {

       if(filename.indexOf(".")>0){
        File outputfile = new File(filename);

        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ImageIO.write(img, "png", outputfile);

       } else {
           System.out.println("filename "+filename+" is not a valid image filename (no extension name).");
       }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }  
  public void log(String msg){
    w.log(msg,logFileHead,true);
  }

  public void srn_sendSrnImg2(){
    try{
      byte imgB[]=null;
        Robot rbt=new Robot();
        BufferedImage img=null;
        boolean byRobot=true;
        if(byRobot){
          Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
          int x=d.width;
          int y=d.height;
          java.awt.Rectangle rec=new java.awt.Rectangle(x,y);
          img=rbt.createScreenCapture(rec);
        }
        try{
          byteArrayStream.reset();
          enc.encode(img);

        }catch(IOException e){e.printStackTrace();}
        imgB=byteArrayStream.toByteArray();
        String backStr="performactionobject ct.CTModerator srn_screenshot %empty% 0 0 0 0 ? ? ? 0";
        Obj obj=new Obj(backStr,imgB);
        String to=(String) cbb_moderatorSrnList.getSelectedItem();
        if(to==null && to.length()<1) return;
        else if(to.equals(allNodesName))  w.sendObjToGroupExceptMyself(obj, w.getGNS(11));
        else if(to.equals(myNodeName)) {}
        else w.sendObjToOneInGroup(obj, w.getGNS(11), (String)nameIdMap.get(to));
    }catch(AWTException e){e.printStackTrace();}
  }

  public void informVersion(int status,String version){

    if(status==1 || status==3) {hasNewVersion=true; newversion=version;}
    if(status==3) {
      appendStatus(bundle2.getString("CTModerator.xy.msg35")+"\r\n");
    } else if(status==2) appendStatus(bundle2.getString("CTModerator.xy.msg37")+"\r\n");
    updateTitle();
  }

  

  private javax.swing.JButton btnReload;
  private javax.swing.JButton btn_fResetLayout1;
  private javax.swing.JButton btn_fResetLayout2;
  private javax.swing.JButton btn_fRoot1;
  private javax.swing.JButton btn_fRoot2;
  private javax.swing.JButton btn_openSld;
  private javax.swing.JButton btn_srnACD;
  private javax.swing.JButton btn_srnRefresh;
  public javax.swing.JButton btn_srnStart;
  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.ButtonGroup buttonGroup2;
  private javax.swing.ButtonGroup buttonGroup3;
  private javax.swing.ButtonGroup buttonGroup6;
  private javax.swing.ButtonGroup buttonGroup7;
  private javax.swing.ButtonGroup buttonGroup8;
  private javax.swing.JCheckBox cbFileAu1;
  private javax.swing.JCheckBox cbFileAu2;
  private javax.swing.JCheckBox cbMsgAu1;
  private javax.swing.JCheckBox cbMsgAu2;
  private javax.swing.JCheckBox cbOpenWebAu;
  private javax.swing.JCheckBox cbSketchAu1;
  private javax.swing.JCheckBox cbSketchAu2;
  private javax.swing.JCheckBox cbSlideAu1;
  private javax.swing.JCheckBox cbSlideAu2;
  private javax.swing.JCheckBox cbSrnAu1;
  private javax.swing.JCheckBox cbSrnAu2;
  public javax.swing.JCheckBox cb_srnFix;
  public javax.swing.JComboBox cbb_fList1;
  public javax.swing.JComboBox cbb_fList2;
  private javax.swing.JComboBox cbb_fMlist;
  private javax.swing.JComboBox cbb_moderatorElseList;
  private javax.swing.JComboBox cbb_moderatorFList;
  private javax.swing.JComboBox cbb_moderatorMsgList;
  private javax.swing.JComboBox cbb_moderatorPermissionList;
  private javax.swing.JComboBox cbb_moderatorSkList;
  private javax.swing.JComboBox cbb_moderatorSldList;
  private javax.swing.JComboBox cbb_moderatorSrnList;
  private javax.swing.JComboBox cbb_moderatorStatusList;
  private javax.swing.JComboBox cbb_msgMList;
  private javax.swing.JComboBox cbb_skMList;
  public javax.swing.JComboBox cbb_sldMList;
  protected javax.swing.JComboBox cbb_slide;
  private javax.swing.JComboBox cbb_slideInx;
  public javax.swing.JComboBox cbb_srnMList;
  private javax.swing.JButton clear;
  private javax.swing.JCheckBox f_checkBox1;
  private javax.swing.JLabel f_label1;
  private javax.swing.JLabel f_label2;
  private javax.swing.JPanel f_panel1;
  private javax.swing.JPanel f_panel10;
  private javax.swing.JPanel f_panel11;
  private javax.swing.JPanel f_panel12;
  private javax.swing.JPanel f_panel13;
  private javax.swing.JPanel f_panel18;
  private javax.swing.JPanel f_panel2;
  private javax.swing.JPanel f_panel20;
  private javax.swing.JPanel f_panel21;
  private javax.swing.JPanel f_panel22;
  private javax.swing.JPanel f_panel23;
  private javax.swing.JPanel f_panel3;
  private javax.swing.JPanel f_panel4;
  private javax.swing.JPanel f_panel5;
  private javax.swing.JPanel f_panel6;
  private javax.swing.JPanel f_panel7;
  private javax.swing.JScrollPane f_scrollPane6;
  private javax.swing.JScrollPane f_scrollPane7;
  private javax.swing.JTabbedPane f_tabbedPane1;
  private javax.swing.JTable f_table1;
  private javax.swing.JTable f_table2;
  private javax.swing.JPanel filePanel;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton10;
  private javax.swing.JButton jButton11;
  private javax.swing.JButton jButton12;
  private javax.swing.JButton jButton13;
  private javax.swing.JButton jButton14;
  private javax.swing.JButton jButton15;
  private javax.swing.JButton jButton16;
  private javax.swing.JButton jButton17;
  private javax.swing.JButton jButton18;
  private javax.swing.JButton jButton19;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton20;
  private javax.swing.JButton jButton21;
  private javax.swing.JButton jButton24;
  private javax.swing.JButton jButton25;
  private javax.swing.JButton jButton26;
  private javax.swing.JButton jButton27;
  private javax.swing.JButton jButton28;
  private javax.swing.JButton jButton29;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton30;
  private javax.swing.JButton jButton31;
  private javax.swing.JButton jButton32;
  private javax.swing.JButton jButton33;
  private javax.swing.JButton jButton34;
  private javax.swing.JButton jButton35;
  private javax.swing.JButton jButton36;
  private javax.swing.JButton jButton38;
  private javax.swing.JButton jButton39;
  private javax.swing.JButton jButton4;
  private javax.swing.JButton jButton40;
  private javax.swing.JButton jButton41;
  private javax.swing.JButton jButton42;
  private javax.swing.JButton jButton5;
  private javax.swing.JButton jButton6;
  private javax.swing.JButton jButton7;
  private javax.swing.JButton jButton8;
  private javax.swing.JButton jButton9;
  private javax.swing.JCheckBox jCheckBox1;
  private javax.swing.JCheckBox jCheckBox10;
  private javax.swing.JCheckBox jCheckBox11;
  private javax.swing.JCheckBox jCheckBox12;
  private javax.swing.JCheckBox jCheckBox13;
  private javax.swing.JCheckBox jCheckBox14;
  private javax.swing.JCheckBox jCheckBox15;
  private javax.swing.JCheckBox jCheckBox16;
  private javax.swing.JCheckBox jCheckBox17;
  private javax.swing.JCheckBox jCheckBox18;
  private javax.swing.JCheckBox jCheckBox19;
  private javax.swing.JCheckBox jCheckBox2;
  private javax.swing.JCheckBox jCheckBox20;
  private javax.swing.JCheckBox jCheckBox21;
  private javax.swing.JCheckBox jCheckBox22;
  private javax.swing.JCheckBox jCheckBox23;
  private javax.swing.JCheckBox jCheckBox24;
  private javax.swing.JCheckBox jCheckBox3;
  private javax.swing.JCheckBox jCheckBox4;
  private javax.swing.JCheckBox jCheckBox5;
  private javax.swing.JCheckBox jCheckBox6;
  private javax.swing.JCheckBox jCheckBox7;
  private javax.swing.JCheckBox jCheckBox8;
  private javax.swing.JCheckBox jCheckBox9;
  public javax.swing.JComboBox jComboBox1;
  private javax.swing.JComboBox jComboBox2;
  public javax.swing.JComboBox jComboBox3;
  public javax.swing.JComboBox jComboBox4;
  private javax.swing.JComboBox jComboBox7;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel16;
  private javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel18;
  private javax.swing.JLabel jLabel19;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel20;
  private javax.swing.JLabel jLabel22;
  private javax.swing.JLabel jLabel23;
  private javax.swing.JLabel jLabel24;
  private javax.swing.JLabel jLabel25;
  private javax.swing.JLabel jLabel26;
  private javax.swing.JLabel jLabel27;
  private javax.swing.JLabel jLabel28;
  private javax.swing.JLabel jLabel29;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel30;
  private javax.swing.JLabel jLabel31;
  private javax.swing.JLabel jLabel32;
  private javax.swing.JLabel jLabel33;
  private javax.swing.JLabel jLabel34;
  private javax.swing.JLabel jLabel35;
  private javax.swing.JLabel jLabel36;
  private javax.swing.JLabel jLabel37;
  private javax.swing.JLabel jLabel38;
  private javax.swing.JLabel jLabel39;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel40;
  private javax.swing.JLabel jLabel41;
  private javax.swing.JLabel jLabel42;
  private javax.swing.JLabel jLabel43;
  private javax.swing.JLabel jLabel44;
  private javax.swing.JLabel jLabel45;
  private javax.swing.JLabel jLabel46;
  private javax.swing.JLabel jLabel47;
  private javax.swing.JLabel jLabel48;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem10;
  private javax.swing.JMenuItem jMenuItem11;
  private javax.swing.JMenuItem jMenuItem12;
  private javax.swing.JMenuItem jMenuItem13;
  private javax.swing.JMenuItem jMenuItem14;
  private javax.swing.JMenuItem jMenuItem15;
  private javax.swing.JMenuItem jMenuItem16;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
  private javax.swing.JMenuItem jMenuItem4;
  private javax.swing.JMenuItem jMenuItem5;
  private javax.swing.JMenuItem jMenuItem6;
  private javax.swing.JMenuItem jMenuItem7;
  private javax.swing.JMenuItem jMenuItem8;
  private javax.swing.JMenuItem jMenuItem9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel10;
  private javax.swing.JPanel jPanel11;
  private javax.swing.JPanel jPanel12;
  private javax.swing.JPanel jPanel13;
  private javax.swing.JPanel jPanel14;
  private javax.swing.JPanel jPanel15;
  private javax.swing.JPanel jPanel16;
  private javax.swing.JPanel jPanel17;
  private javax.swing.JPanel jPanel18;
  private javax.swing.JPanel jPanel19;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel20;
  private javax.swing.JPanel jPanel21;
  private javax.swing.JPanel jPanel22;
  private javax.swing.JPanel jPanel23;
  private javax.swing.JPanel jPanel24;
  private javax.swing.JPanel jPanel25;
  private javax.swing.JPanel jPanel26;
  private javax.swing.JPanel jPanel27;
  private javax.swing.JPanel jPanel28;
  private javax.swing.JPanel jPanel29;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel30;
  private javax.swing.JPanel jPanel31;
  private javax.swing.JPanel jPanel32;
  private javax.swing.JPanel jPanel33;
  private javax.swing.JPanel jPanel34;
  private javax.swing.JPanel jPanel35;
  private javax.swing.JPanel jPanel36;
  private javax.swing.JPanel jPanel37;
  private javax.swing.JPanel jPanel38;
  private javax.swing.JPanel jPanel39;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel40;
  private javax.swing.JPanel jPanel41;
  private javax.swing.JPanel jPanel42;
  private javax.swing.JPanel jPanel43;
  private javax.swing.JPanel jPanel44;
  private javax.swing.JPanel jPanel45;
  private javax.swing.JPanel jPanel46;
  private javax.swing.JPanel jPanel47;
  private javax.swing.JPanel jPanel48;
  private javax.swing.JPanel jPanel49;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel50;
  private javax.swing.JPanel jPanel51;
  private javax.swing.JPanel jPanel52;
  private javax.swing.JPanel jPanel53;
  private javax.swing.JPanel jPanel54;
  private javax.swing.JPanel jPanel55;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel8;
  private javax.swing.JPanel jPanel9;
  private javax.swing.JPasswordField jPasswordField1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JScrollPane jScrollPane4;
  public javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTabbedPane jTabbedPane2;
  public javax.swing.JTextArea jTextArea1;
  private javax.swing.JTextArea jTextArea2;
  public javax.swing.JTextArea jTextArea3;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JTextField jTextField4;
  private javax.swing.JTextField jTextField5;
  private javax.swing.JTextField jTextField7;
  private javax.swing.JTextField jTextField8;
  private javax.swing.JTextField jTextField9;
  public javax.swing.JTextPane jTextPane1;
  private javax.swing.JPanel moderatorPanel;
  private javax.swing.JPanel msgPanel;
  private javax.swing.JTabbedPane msg_TabbedPane;
  private javax.swing.JButton msg_btnClear;
  private javax.swing.JButton msg_btnMinus;
  private javax.swing.JButton msg_btnPlus;
  private javax.swing.JButton msg_btnSaveAs;
  private javax.swing.JButton msg_btnSendClear;
  private javax.swing.JButton msg_btnSendDraft;
  private javax.swing.JButton msg_btn_sendMsg;
  private javax.swing.JButton msg_button10;
  private javax.swing.JButton msg_button7;
  private javax.swing.JButton msg_button8;
  private javax.swing.JButton msg_button9;
  private javax.swing.JCheckBox msg_checkBox1;
  private javax.swing.JPanel msg_draft_upperPanel;
  private javax.swing.JLabel msg_label1;
  private javax.swing.JLabel msg_label2;
  private javax.swing.JPanel msg_lowerPanel;
  private javax.swing.JPanel msg_panel1;
  private javax.swing.JPanel msg_panel2;
  private javax.swing.JPanel msg_panel5;
  private javax.swing.JPanel msg_panel7;
  private javax.swing.JScrollPane msg_scrollPane;
  private javax.swing.JScrollPane msg_scrollPane2;
  private javax.swing.JTextArea msg_textArea1;
  private javax.swing.JTextField msg_textField1;
  private javax.swing.JTextPane msg_textPane;
  private javax.swing.JPanel msg_upperPanel;
  public javax.swing.JRadioButton rb_srnControlAndImage;
  public javax.swing.JRadioButton rb_srnMonitor;
  public javax.swing.JPanel screenPanel;
  private javax.swing.JPanel sk_upperPanel;
  private javax.swing.JPanel sketchPanel;
  private javax.swing.JPanel sld_upperPanel1;
  private javax.swing.JPanel sld_upperPanel2;
  private javax.swing.JPanel slidePanel;
  private javax.swing.JScrollPane srn_scrollPane1;
  public javax.swing.JPanel srn_upperPanel;
  private javax.swing.JPanel statusPanel;
  private javax.swing.JCheckBox status_checkBox1;
  private javax.swing.JPanel status_upperPanel;

}