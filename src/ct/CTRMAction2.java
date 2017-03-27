package ct;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import infinity.common.server.*;
import infinity.client.*;
import infinity.server.*;
import infinity.common.*;
import infinity.common.action.*;
import java.io.*;
import java.util.*;
import java.text.*;

import y.ylib.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.datatransfer.*;

import com.sun.media.jai.codec.ImageEncoder;
 /**
   #.remote control
   #.
     [0]:
         [0]:getimg
         [0]:rm_mouse
         [0]:rm_key
         [0]:contsrn
         [0]:stopsrn

  */
public class CTRMAction2 implements GAction,Runnable{
  protected Thread t=null;
  private String sep=""+(char)0,originalId=""; 
  String stringx[]=null;
  Weber w;
  Net gs;
  Robot rbt;
  String previousToId= null;
  private String pid="";
  int mode0=0;

  boolean mousePressed=false,keyPressed=false;
  String msg;
  byte previousImg[]=null;
  SimpleDateFormat format2=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

  long sysstarttime=System.currentTimeMillis(),defaultsysstoptime=0,filelength=0,
      executiontimelimit=60L * 60L * 24L * 365L * 10L,

      copysize=0,localdelsize=0,localmovesize=0;
  ByteArrayOutputStream byteArrayStream=null;
  ImageEncoder enc=null;
  com.sun.media.jai.codec.JPEGEncodeParam param=null;

  /**
   * The status of the thread t. If run() performed, the value is true. The default value is false.
   */

  /**
   * The status of the thread t. If thread t is sleeping, it is true. The default value is false.
   */
  protected boolean isSleep=true;
  Vector waitItem=new Vector();
  public CTRMAction2(){
    try{
      rbt=new Robot();
    }catch(AWTException e){e.printStackTrace();}
    byteArrayStream = new ByteArrayOutputStream();

      param=new com.sun.media.jai.codec.JPEGEncodeParam();

      enc=ImageCodec.createImageEncoder("jpeg",byteArrayStream,param);

  }

  

  public static void alt(int event1, int event2, int event3, int event4) throws Exception {

    Robot bot = new Robot();
    bot.delay(50); 
        bot.keyPress(KeyEvent.VK_ALT);

            bot.keyPress(event1);
            bot.keyRelease(event1);

            bot.keyPress(event2);
            bot.keyRelease(event2);

            bot.keyPress(event3);
            bot.keyRelease(event3);

            bot.keyPress(event4);
            bot.keyRelease(event4);

        bot.keyRelease(KeyEvent.VK_ALT);

}
  public String getVersion(){return "1.0.1";}
    /** To get the version date of the action class. 
    @return The default return value is "undefined". 
   */
  public String getVersionDate(){return "2016.10.1";}
  

  /** To get the output format of the action. 
     @return 1, if the output is in html format. 0, if not in html format. The defautl return value is "0".
   */
  public String getHtmlOutput(){ return "0";}
  /** To get the action name. The action name is a brief description about this action.
      @return The default return value is "undefined". 
   */
  public String getActionName(){ return "undefined";}
  /** To get the menu title for hyperlink.
    @return The default return value is "undefined". 
   */
  public String getMenuTitle(){return "undefined";}
  /** To get the html title, this can be put as the headline of the html-result, 
    @return The default return value is "undefined". 
   */
  public String getHtmlTitle(){return "undefined";}
  /** To get the Style for the html result. The value format is "&lt;style&gt;.....&lt;/style&gt;".
     @return The deault return value is "". 
   */
  public String getStyle(){ return "";}
  /** To get the javascript for the html result. The value is the text between "&lt;script&gt;&lt;!--&quot; and &quot;--&gt;&lt;/script&gt;".
     @return The deault return value is "". 
   */
  public String getJScript(){ return "";}
  /** To get the html result. 
     @return The deault return value is "". 
   */
  public String getHtmlResult(){return "";}
  /** To get the action's password. 
    @return The default return value is "infinity".
  */

  

  

  

     public boolean commandObj(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread sot){
       return true;
   }
     public boolean commandObj2(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread2 sot){
       return true;
   }
       /**
   * The parameter definition of this is the same as command().
   * The difference between command() and msg() is that msg() can be called without authorized code.
   */
  public boolean msg(int mode0,int modeNow,String originalId,String scheduleItemId,Weber w,Net gs,String cmd,infinity.common.server.Connection c){return false;}
  /**
   * The parameter definition of this method is the same as commandObj().
   * The difference between commandObj() and msgObj() is that msgObj() can be called without authorized code.
   */
  public boolean msgObj(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread sot){return false;}
  public boolean msgObj2(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread2 sot){return false;}
  /**
 /**
   * This method usually called by the system's scheduler.
   * If the subclass is intended to be a thread, can use these codes:
   * t=new Thread(this);
   * t.start();
   * when override this method.
   * If this method wants to share Weber, Net,and Connection with the command() 
   * method, can use these codes:
   *          this.w=w; this.gs=gs; this.c=c;
   * when override this method.
   * @param mode A type of this command, produced by system.
             If a user calls this method directly, can set this to null.
   * @param commandid An id of this command, produced by system.
             If a user calls this method directly, can set this to null.
   * @param filler A string used by system.
             If a user calls this method directly, can set this to null.
   * @param w The client object, produced by system.
             If a user calls this method directly, can set this to null.
   * @param gs The server object, produced by system.
             If a user calls this method directly, can set this to null.
   * @param stringx An array that represents the parameters for this perform.
   * @param c The connection to the server, produced by system.
             If a user calls this method directly, can set this to null.
   */
  public boolean perform(int mode0,int modeNow,String originalId,String actionCode,Weber w,Net gs,String stringx[],infinity.common.server.Connection c){
    this.originalId=originalId;
    this.w=w;
    this.mode0=mode0;
    this.stringx=stringx;
    int result=1;
    this.gs=gs;

    if(t==null){  t=new Thread(this);  t.start(); }

    return true;
  }
  

  /**
   * This method usually called by using the client command.
   * If this method wants to share Weber, Net,and Connection with the perform() 
   * method, can use these codes:
   *          this.w=w; this.gs=gs; this.c=c;
   * when override this method.
   * @param mode A type of this command, produced by system.
   *         If a user calls this method directly, can set this to null.
   * @param commandid An id of this command, produced by system.
   *         If a user calls this method directly, can set this to null.
   * @param filler A string used by system.
   *         If a user calls this method directly, can set this to null.
   * @param w The client object, produced by system.
   *         If a user calls this method directly, can set this to null.
   * @param gs The server object, produced by system.
   *         If a user calls this method directly, can set this to null.
   * @param cmd The command string represents the parameters of the command,
   *      each parameter separated by space, for example, "john 232 good day".
   *      when override this method, use 
   *      StringTokenizer st=new StringTokenizer(cmd," "); to parse the command.
   *      if an parameter is empty or having space, use %empty% and %space% before StringTokenizer st=new StringTokenizer(cmd," "); statement.
   * @param c The connection to the server, produced by system. 
   *         If a user calls this method directly, can set this to null.
   */
  

  public boolean command(int mode0,int modeNow,String originalId,String actionCode,Weber w,Net gs,String cmd,infinity.common.server.Connection c){
    this.originalId=originalId;
    this.w=w;
    this.mode0=mode0;
    this.gs=gs;
    if(t==null){  t=new Thread(this);  t.start(); }
   StringTokenizer st=new StringTokenizer(cmd," ");
   int cnt=st.countTokens();
   stringx=new String[cnt];
   for(int i=0;i<cnt;i++){
       stringx[i]=st.nextToken();
       stringx[i]=w.replaceCommon(stringx[i]);
   }

   if(stringx[0].equalsIgnoreCase("getimg2")) {

       setAction(originalId,2,stringx[1],stringx[2],stringx[3],stringx[4]);

   }
   else if(stringx[0].equalsIgnoreCase("getimg")) {

     setAction(originalId,1,stringx[1],stringx[1],stringx[2],stringx[3]);
   }
   else if(stringx[0].equalsIgnoreCase("rm_mouse")) {
      int type= Integer.parseInt(stringx[1]);
      int id=Integer.parseInt(stringx[2]);
      int x=Integer.parseInt(stringx[3]);
      int y=Integer.parseInt(stringx[4]);
      int cnt2=Integer.parseInt(stringx[5]);
      int modifier=Integer.parseInt(stringx[6]);
      int btn=Integer.parseInt(stringx[7]);
      String currentCode=stringx[8];
      int mask=0;
      switch(btn){
          case 1:
             mask=InputEvent.BUTTON1_MASK;
             break;
          case 2:
             mask=InputEvent.BUTTON2_MASK;
             break;
          case 3:
             mask=InputEvent.BUTTON3_MASK;
             break;
      }
      rbt.mouseMove(x, y);
      if(type==1) {
          rbt.mousePress(mask);
          mousePressed=true;
      }
      else if(type==2) {
          rbt.mouseRelease(mask);
          mousePressed=false;

          setAction(originalId,1,"true",currentCode,"1","0");
      }
      else if(type==3) {
          if(mousePressed){

          } else rbt.mouseMove(x, y);
      }
   }
   else if(stringx[0].equalsIgnoreCase("rm_mouse2")) {
      int type= Integer.parseInt(stringx[1]);
      int id=Integer.parseInt(stringx[2]);
      int x=Integer.parseInt(stringx[3]);
      int y=Integer.parseInt(stringx[4]);
      int cnt2=Integer.parseInt(stringx[5]);
      int modifier=Integer.parseInt(stringx[6]);
      int btn=Integer.parseInt(stringx[7]);
      String currentCode=stringx[8];
      String toInform=stringx[9];
      int mask=0;
      switch(btn){
          case 1:
             mask=InputEvent.BUTTON1_MASK;
             break;
          case 2:
             mask=InputEvent.BUTTON2_MASK;
             break;
          case 3:
             mask=InputEvent.BUTTON3_MASK;
             break;
      }
      rbt.mouseMove(x, y);
      if(type==1) {
          rbt.mousePress(mask);
          mousePressed=true;
      }
      else if(type==2) {
          rbt.mouseRelease(mask);
          mousePressed=false;

          if(toInform.equals("0")) setAction(originalId,2,"true",currentCode,"1","0");
      }
      else if(type==3) {
          if(mousePressed){

          } else rbt.mouseMove(x, y);
      }
   }
   else if(stringx[0].equalsIgnoreCase("rm_mousewheel2")) {
      int noches= Integer.parseInt(stringx[1]);

      rbt.mouseWheel(noches);

   }
   else if(stringx[0].equalsIgnoreCase("rm_key")) {
      int type= Integer.parseInt(stringx[1]);
      int id=Integer.parseInt(stringx[2]);
      int code=Integer.parseInt(stringx[3]);
      int modifier=Integer.parseInt(stringx[4]);
      String currentCode=stringx[5];
      if(type==1) {
          rbt.keyPress(code);
          keyPressed=true;

      }
      else if(type==2) {
          rbt.keyRelease(code);
          keyPressed=false;

          setAction(originalId,1,"true",currentCode,"1","0");
      }
   }
   else if(stringx[0].equalsIgnoreCase("rm_key2")) {
      int type= Integer.parseInt(stringx[1]);
      int id=Integer.parseInt(stringx[2]);
      int code=Integer.parseInt(stringx[3]);
      int modifier=Integer.parseInt(stringx[4]);
      String currentCode=stringx[5];
      String toInform=stringx[6];
      try{
      if(type==1) {
        if(code==92 || code==520) rbt.keyPress(KeyEvent.VK_BACK_SLASH);
         else  rbt.keyPress(code);
          keyPressed=true;

      }
      else if(type==2) {
        if(code==92 || code==520) rbt.keyRelease(KeyEvent.VK_BACK_SLASH);
        else{
          rbt.keyRelease(code);
        }
          keyPressed=false;

          if(toInform.equals("0")) setAction(originalId,2,"true",currentCode,"1","0");
      }
      }catch(IllegalArgumentException e){
         System.out.println("IllegalArgumentException, type="+(type==1? "pressed":"released")+", key code="+code);
      }catch(Exception e){
         System.out.println("Exception, type="+(type==1? "pressed":"released")+", key code="+code);
      }
   }
   else if(stringx[0].equalsIgnoreCase("contsrn")) {

       long interval1=Long.parseLong(stringx[1]);
       boolean byRobot=Boolean.parseBoolean(stringx[2]);
       if(t==null) {
           perform(mode0,modeNow,originalId,actionCode,w,gs,stringx,c);
       }
       boolean runThread=true; 
       if(isSleep()) t.interrupt();
   }
   else if(stringx[0].equalsIgnoreCase("contsrn2")) {

       long interval2=Long.parseLong(stringx[1]);
       boolean byRobot=Boolean.parseBoolean(stringx[2]);
       if(t==null) {
           perform(mode0,modeNow,originalId,actionCode,w,gs,stringx,c);
       }
       boolean runThread=true; 
       if(isSleep()) t.interrupt();
   }
   else if(stringx[0].equalsIgnoreCase("stopsrn")) {

   }
   else if(stringx[0].equalsIgnoreCase("stopsrn2")) {

   }
   return true;
  }
  /**
   * By default, the content of this method is empty.
   * If the subclass is intended to be a thread class, it can override this method.
   * For example:
   * public void run(){
   *   runThread=true;
   *   while(runThread){do_something}
   * } 
   */

  public void run(){
    while(true){
      isSleep=false;
      while(waitItem.size()>0){
        ActionItem ai=(ActionItem)waitItem.get(0);   
        switch(ai.actionType){
          case 1:
              if(previousToId==null || !previousToId.equals(ai.originalId)) sendSrnImg(ai.originalId,ai.para,ai.currentCode,ai.div,ai.inx);
            break;
          case 2:
              if(previousToId==null || !previousToId.equals(ai.originalId)) sendSrnImg2(ai.originalId,ai.para,ai.currentCode,ai.div,ai.inx);
            break;
      }
      previousToId=ai.originalId;
      waitItem.remove(0);
      }
      previousToId=null;
      previousImg=null;
      try{
        isSleep=true;

        Thread.sleep(1000L*60L*60L*24L*365L);
      } catch(InterruptedException e){
        }
    }

  }
  BufferedImage scaleImage(BufferedImage img,int newW,int newH){
    BufferedImage newImage = new BufferedImage(newW,newH, BufferedImage.TYPE_INT_RGB);
    Graphics g = newImage.createGraphics();
    g.drawImage(img, 0, 0, newW, newH, null);
    g.dispose();
    return newImage;
  }
  public void gridChanged(Weber w,Net n,int from){}
  /**
   * To stop the running thread t.
   * Subclass don't have to override this method, this method can be called directly. 
   */
  public void killThread(){

  }
  /**
   * To check if the thread t is sleeping or not.
   * @return If the thread t is sleeping, return true.
   */
  public boolean isSleep(){
    return isSleep;
  }
  /**
   * To check if the thread is null or not.
   * @return If the thread is null, return true.
   */
  public boolean isNull(){
    return (t==null);
  }
  /**
   * To check if the thread t is alive or not.
   * @return If the thread t is alive, return true.
   */
  public boolean isAlive(){
    return t.isAlive();
  }
  /**
   * To get the status of this action class.
   * @return  The status of this action class, including the action name, version, 
   * version date, Thread t status, and so on.
   */
  public String getStatus(){
  	StringBuffer sb=new StringBuffer();
  	sb.append("Action name=").append(getActionName()).
  	  append(",version=").append(getVersion()).
  	  append(",version date=").append(getVersionDate()).
  	  append(",Menu title=").append(getMenuTitle()).
  	  append(",Html title=").append(getHtmlTitle()).
  	  append(",Thread=null? "+isNull()).
  	  append((isNull()? "":",Thread.isALive? "+isAlive())).
  	  append((isNull()? "":",isSleep="+isSleep()));
  	return sb.toString();
  }

  public void sendSrnImg(String toId,String byRB,String currentCode,String div,String inx3){

    byte imgB[]=null;
    if(previousImg==null){
    BufferedImage img=null;
    boolean byRobot=true;
    if(byRobot){
      Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
      int x=d.width;
      int y=d.height;
      java.awt.Rectangle rec=new java.awt.Rectangle(x,y);
      img=rbt.createScreenCapture(rec);
    } else {
        rbt.keyPress(KeyEvent.VK_PRINTSCREEN);
        rbt.delay(40);
        rbt.keyRelease(KeyEvent.VK_PRINTSCREEN);
        rbt.delay(40);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

        try{
          img = (BufferedImage)cb.getData(DataFlavor.imageFlavor);
        } catch(UnsupportedFlavorException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
     }
    

	try{

      enc.encode(img);
        }catch(IOException e){e.printStackTrace();}
	imgB=byteArrayStream.toByteArray();  
        previousImg=imgB;
    } else imgB=previousImg;

    

    long rnd=Math.round(Math.random()*100000D);
    int end=0;
    int bSize=imgB.length;

     int endb=0;
     int diff=0;

     int S=36414;
     int inx=0,inx2=0,from=0;
     StringBuffer backStr;
     if(bSize>0){
         

       for (int i = 0; i<bSize; i+=S){

         endb=((i+S)>bSize? bSize:(i+S));
         diff=endb-i;
         inx2=inx+diff;
         byte [] b2=new byte[diff];
         for(int j =inx,k=0;j<inx2;j++,k++){
           b2[k]=imgB[j];
         }
         from=inx;
         inx=inx2;
         if(inx==bSize) end=1;
         backStr=new StringBuffer("srnimg");

         backStr.append(sep).append(rnd).append(sep).append(end).append(sep).append(bSize).append(sep).append(from).append(sep).append((inx2)).append(sep).append(new String(Weber.e642(b2)));
         w.ap.feedback(mode0,0,"%comecmdcode%",toId,backStr.toString()+"\r\n");
         backStr.delete(0, backStr.length());
         }
        }

  }

  public void sendSrnImg2(String toId,String byRB,String currentCode,String div,String inx){

    byte imgB[]=null;
    if(previousImg==null){
    BufferedImage img=null;
    boolean byRobot=true;
    if(byRobot){
      Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
      int x=d.width;
      int y=d.height;
      java.awt.Rectangle rec=new java.awt.Rectangle(x,y);
      img=rbt.createScreenCapture(rec);
      int div2=Integer.parseInt(div);
      if(div2>1){
        int w=img.getWidth();
        int h=img.getHeight();
        w=Math.round(((float)w)/((float)div2));
        h=Math.round(((float)h)/((float)div2));
        img=scaleImage(img,w,h);
      }
    } else {
        rbt.keyPress(KeyEvent.VK_PRINTSCREEN);
        rbt.delay(40);
        rbt.keyRelease(KeyEvent.VK_PRINTSCREEN);
        rbt.delay(40);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

        try{
          img = (BufferedImage)cb.getData(DataFlavor.imageFlavor);
        } catch(UnsupportedFlavorException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
     }
    

    try{

      byteArrayStream.reset();
      enc.encode(img);

      }catch(IOException e){e.printStackTrace();}

      imgB=byteArrayStream.toByteArray();    
      previousImg=imgB;
    } else imgB=previousImg;

         StringBuffer backStr=new StringBuffer("performactionobject ct.CTModerator srn_srnimg2 "+currentCode+" "+div+" "+inx+" %empty% 0 0 0 0 ? ? ? 0");
         Obj obj=new Obj(backStr.toString(),imgB);

    w.ap.feedbackObj(mode0,0,"%comecmdcode%",toId,obj);

  }

  public void setAction(String originalId,int actionType,String para,String code,String div,String inx){
    waitItem.add(new ActionItem(originalId,actionType,para,code,div,inx));

    if(isSleep()) t.interrupt();
  }
  class ActionItem {
    String originalId;
    int actionType;
    String para;
    String currentCode;
    String div;
    String inx;
    public ActionItem(String originalId,int actionType,String para,String code,String div,String inx){
      this.originalId=originalId;
      this.actionType=actionType;
      this.para=para;
      this.currentCode=code;
      this.div=div;
      this.inx=inx;
    }
  }
  public String getHelp(){return "";}

public void onExit(int type){};
public void informVersion(int status,String version){}
}