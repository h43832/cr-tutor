package ct;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import infinity.common.*;
class CTCBListener extends Thread implements ClipboardOwner {
  Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

  String remoteCBValue="";
  CTModerator moderator;
  public CTCBListener(CTModerator moderator){
    this.moderator=moderator;
  }
  public void run() {
    try{
    Transferable trans = sysClip.getContents(this);
    regainOwnership(trans);
    } catch(IllegalStateException e){
     if(moderator.w.getHVar("a_test")!=null && moderator.w.getHVar("a_test").equalsIgnoreCase("true")) System.out.println("IllegalStateException in CTCBListener.run().");

  }

    while(true) {
      try{
       sleep(100000L);
      } catch(InterruptedException e){e.printStackTrace();}
    }
  }

  public void lostOwnership(Clipboard c, Transferable t) {
  try {
    this.sleep(200);
  } catch(Exception e) {

    e.printStackTrace();
  }
  try{
    Transferable contents = sysClip.getContents(this);
    processContents(contents);
    regainOwnership(contents);
  }catch(IllegalStateException e){
     if(moderator.w.getHVar("a_test")!=null && moderator.w.getHVar("a_test").equalsIgnoreCase("true")) System.out.println("IllegalStateException in CTCBListener.lostOwnership().");

  }catch(Exception e){
     if(moderator.w.getHVar("a_test")!=null && moderator.w.getHVar("a_test").equalsIgnoreCase("true")) System.out.println("Exception in CTCBListener.lostOwnership(). msg="+e.getMessage());
  }
}

  void processContents(Transferable t) {
    try{

      if(t.isDataFlavorSupported(DataFlavor.stringFlavor) && moderator!=null && moderator.w!=null){
        String s=(String)t.getTransferData(DataFlavor.stringFlavor),cmd="",
          id1=moderator.w.getAHVar("counterpartID1"),id2=moderator.w.getAHVar("counterpartID2");

        if(s.length()>0 && !s.equals(remoteCBValue)){
         if(id1!=null && id1.length()>0){

           cmd="performactionobject ct.CTModerator setclipboard2 ";
           Obj obj=new Obj(cmd,s.getBytes());

           moderator.w.ap.feedbackObj(11,1,"%comecmdcode%",id1,obj);
         }
         if(id2!=null && id2.length()>0 && !id2.equals(id1)){

           if(cmd.length()<1) cmd="performactionobject ct.CTModerator setclipboard2 ";
           Obj obj=new Obj(cmd,s.getBytes());

           moderator.w.ap.feedbackObj(11,1,"%comecmdcode%",id2,obj);

         }
        }

      }
    } catch(UnsupportedFlavorException e){e.printStackTrace();}
    catch(IOException e){e.printStackTrace();}
  }
  public void setContents(String s){

    try{
      remoteCBValue=s;
      StringSelection ss=new StringSelection(s);
      sysClip.setContents(ss, null);

    }catch(IllegalStateException e){}
  }
  void regainOwnership(Transferable t) {
    try{
      sysClip.setContents(t, this);
    }catch(IllegalStateException e){}
  }

}