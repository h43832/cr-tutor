
package ct;

import java.io.File;
import java.util.Calendar;
import y.ylib.ylib;

/**
 *
 * @author peter
 */
public class CTFBDownloadThread extends Thread{
   boolean runThread=false,isSleep=true,reload=false;
  CTModerator moderator;
  public void init(CTModerator moderator){
    this.moderator=moderator;
  }
  public void run(){
    runThread=true;

    moderator.f_currentStatus=0;

    boolean firstTime=true;
    StringBuffer sendSB=new StringBuffer();
    while(runThread){
      firstTime=true;
      moderator.f_currentStatus=1;
      try{
          isSleep=true;
          sleep(moderator.waitTime);
      }catch(InterruptedException e){
        }
       isSleep=false;
      while(moderator.f_downloadActions.size()>0){
          moderator.f_currentStatus=2;
          String action=(String)moderator.f_downloadActions.get(0);
          String[] strs=ylib.csvlinetoarray(action);
          String originalId2=strs[0];

          String cmd=strs[1],str10,str11,str12,str13;
          moderator.f_downloadFileVector.removeAllElements();

          long fromtime=0,totime=0;
          String stringx[]=new String[strs.length-2];
          for(int i=0;i<stringx.length;i++) stringx[i]=strs[i+2];
          if(stringx.length<11 || stringx[10].length()<1 || stringx[10].equals("0") || stringx[10].length()==0) str10="0000-00-00";
	  else str10=stringx[10];
          if(stringx.length<12 || stringx[11].length()<1 ||  stringx[11].equals("0") || stringx[11].length()==0) str11="00:00:00";
	  else str11=stringx[11];
          if(stringx.length<13 || stringx[12].length()<1 ||  stringx[12].equals("0") || stringx[12].length()==0) str12="9999-12-31";
	  else str12=stringx[12];
          if(stringx.length<14 || stringx[13].length()<1 ||  stringx[13].equals("0") || stringx[13].length()==0) str13="23:59:59";
	  else str13=stringx[13];
          try{
              fromtime=moderator.format2.parse(str10+" "+str11).getTime();
            } catch(java.text.ParseException e){
              String msg="error LocalAction.perform().7 in parsing \""+str10+" "+str11+"\" -ParseException:"+e.getMessage()+"\r\n";

              System.out.println(msg);
        	fromtime=0;
              }
            try{
              totime=moderator.format2.parse(str12+" "+str13).getTime();
            } catch(java.text.ParseException e){
              String  msg="error LocalAction.perform().8 in parsing \""+str12+" "+str13+"\" -ParseException:"+e.getMessage()+"\r\n";

              System.out.println(msg);
        	totime=System.currentTimeMillis();
              }
          Calendar start=Calendar.getInstance();
          Calendar end=Calendar.getInstance();
          start.setTimeInMillis(fromtime); 
          end.setTimeInMillis(totime);
          String ddir=moderator.w.d642(stringx[8]);
         if(cmd.equals("getfile")){

            String sDirOrFile=moderator.w.d642(stringx[6]),sdir="";
            sDirOrFile=moderator.w.replaceC(sDirOrFile);

            if(sDirOrFile.lastIndexOf(File.separator)==sDirOrFile.length()-1) {
              if(new File(sDirOrFile).getParent()!=null){
                sDirOrFile=sDirOrFile.substring(0, sDirOrFile.length()-1);
              }
            }
            if(new File(sDirOrFile).getParent()!=null){
                sdir=new File(sDirOrFile).getParent();
              } else sdir=sDirOrFile;
            if(!sdir.endsWith(File.separator)) sdir=sdir+File.separator;

            String subdirmode=stringx[14];
            if(ddir.lastIndexOf(File.separator)!=ddir.length()-1) ddir=ddir+moderator.f_remoteFileSeparator;

            String writemode=stringx[15];
            if(firstTime){
              moderator.f_currentRemoteWriteMode=writemode;
              firstTime=false;
            }
            String fn="",dfn="";
            File f=new File(sDirOrFile),f2;
            if(f.exists()){
              if(f.isDirectory()){
                      moderator.f_getDownloadFileVectors(sdir,"*",fromtime,totime,subdirmode,ddir,sDirOrFile,writemode);
                   } else {
                         long filetime=f.lastModified();
                         if(filetime>=fromtime && filetime<=totime){
                            moderator.f_getDownloadFileVector(sdir,f,ddir,f.getName(),f.getParent(),writemode);
                          }
                         sDirOrFile=f.getParent();
                       }

            }

               while(moderator.f_downloadFileVector.size()>0){
                  long onevar=0;
                  if(moderator.f_downloadFileVector.size()<2 && moderator.f_downloadActions.size()<2) onevar=moderator.w.addOneVar(onevar,1);
                   moderator.f_currentStatus=3;
                   moderator.f_chkExistResult=false;
                   String fdata[]=ylib.csvlinetoarray((String) moderator.f_downloadFileVector.get(0));
                   String lasttime=fdata[1],filesize=fdata[2],ddir2=fdata[3],relative=fdata[4],dfilename2=fdata[5];

                   writemode=moderator.f_currentRemoteWriteMode;
                   String nsdir=nsdir=sdir+relative;
                   if(!writemode.equalsIgnoreCase("overwrite")){
                   sendSB.delete(0,sendSB.length());
                   int currentRandom2=(int)(Math.random()*10000000D);

                   sendSB.append("performaction ct.CTModerator 1 f_downloadcheckexist "+ylib.replace(fdata[0]," ","%space%")+" "+ylib.replace(ddir2," ","%space%")+" "+(relative.length()>0? ylib.replace(relative," ","%space%"):"%empty%")+" "+
                           ylib.replace(dfilename2," ","%space%")+" "+lasttime+" "+filesize+" "+writemode+" "+currentRandom2+" "+moderator.f_currentStatus);
                   moderator.w.ap.feedback(moderator.w.getMode0(originalId2),0,"%comecmdcode%",originalId2,sendSB.toString()+"\r\n"); 

                   try{
                     isSleep=true;
                     sleep(moderator.waitTime);
                   }catch(InterruptedException e){
                    }
                   isSleep=false;

                   if(moderator.f_chkExistResult){
                   if(writemode.equalsIgnoreCase("ask")){
                   moderator.f_downloadChkSaveResult="skip";
                   sendSB.delete(0,sendSB.length());
                   currentRandom2=(int)(Math.random()*10000000D);
                   sendSB.append("performaction ct.CTModerator 1 f_checksavefile "+ylib.replace(fdata[0]," ","%space%")+" "+ylib.replace(ddir2," ","%space%")+" "+(relative.length()>0? ylib.replace(relative," ","%space%"):"%empty%")+" "+
                           ylib.replace(dfilename2," ","%space%")+" "+lasttime+" "+filesize+" "+writemode+" "+currentRandom2+" "+moderator.f_currentStatus+" 0");
                   moderator.w.ap.feedback(moderator.w.getMode0(originalId2),0,"%comecmdcode%",originalId2,sendSB.toString()+"\r\n"); 

                   try{
                     isSleep=true;
                      sleep(moderator.waitTime);
                   }catch(InterruptedException e){
                    }
                   isSleep=false;
                   currentRandom2=4;

                     if(moderator.f_downloadChkSaveResult.equals("overwrite")){
                       File f3=new File(fdata[0]);
                       String  cmd2="performcommand ct.CTModerator f_beginsendfile "+moderator.w.e642(sDirOrFile)+" "+moderator.w.e642(f3.getName())+" "+moderator.w.e642(ddir)+" "+moderator.w.e642(dfilename2)+" "+moderator.w.e642(""+f3.length())+" 0 0 0 0  0 0 0 0 0 ";
                       moderator.w.ap.feedback(moderator.w.getMode0(originalId2),0,"%comecmdcode%",originalId2,cmd2+"\r\n"); 

                       moderator.f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,originalId2,originalId2,onevar);
                       cmd2="performcommand ct.CTModerator f_endsendfile "+moderator.w.e642(sDirOrFile)+" "+moderator.w.e642(f3.getName())+" "+moderator.w.e642(ddir)+" "+moderator.w.e642(dfilename2)+" "+moderator.w.e642(""+f3.length())+" 0 0 0 0  0 0 0 0 0 ";
                       moderator.w.ap.feedback(moderator.w.getMode0(originalId2),0,"%comecmdcode%",originalId2,cmd2+"\r\n"); 

                     } if(moderator.f_downloadChkSaveResult.equals("checkdate")){
                       if(Boolean.parseBoolean(moderator.f_downloadChkSaveSecondPara)){
                         File f3=new File(fdata[0]);
                         String  cmd2="performcommand ct.CTModerator f_beginsendfile "+moderator.w.e642(sDirOrFile)+" "+moderator.w.e642(f3.getName())+" "+moderator.w.e642(ddir)+" "+moderator.w.e642(dfilename2)+" "+moderator.w.e642(""+f3.length())+" 0 0 0 0  0 0 0 0 0 ";
                         moderator.w.ap.feedback(moderator.w.getMode0(originalId2),0,"%comecmdcode%",originalId2,cmd2+"\r\n"); 

                         moderator.f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,originalId2,originalId2,onevar);
                         cmd2="performcommand ct.CTModerator f_endsendfile "+moderator.w.e642(sDirOrFile)+" "+moderator.w.e642(f3.getName())+" "+moderator.w.e642(ddir)+" "+moderator.w.e642(dfilename2)+" "+moderator.w.e642(""+f3.length())+" 0 0 0 0  0 0 0 0 0 ";
                         moderator.w.ap.feedback(moderator.w.getMode0(originalId2),0,"%comecmdcode%",originalId2,cmd2+"\r\n"); 

                       }
                     } if(moderator.f_downloadChkSaveResult.equals("rename")){
                       File f3=new File(fdata[0]);
                       String  cmd2="performcommand ct.CTModerator f_beginsendfile "+moderator.w.e642(sDirOrFile)+" "+moderator.w.e642(f3.getName())+" "+moderator.w.e642(ddir)+" "+moderator.w.e642(dfilename2)+" "+moderator.w.e642(""+f3.length())+" 0 0 0 0  0 0 0 0 0 ";
                       moderator.w.ap.feedback(moderator.w.getMode0(originalId2),0,"%comecmdcode%",originalId2,cmd2+"\r\n"); 

                       moderator.f_sendFile3(sdir,f3,ddir,moderator.f_downloadChkSaveSecondPara,nsdir,writemode,originalId2,originalId2,onevar);
                       cmd2="performcommand ct.CTModerator f_endsendfile "+moderator.w.e642(sDirOrFile)+" "+moderator.w.e642(f3.getName())+" "+moderator.w.e642(ddir)+" "+moderator.w.e642(dfilename2)+" "+moderator.w.e642(""+f3.length())+" 0 0 0 0  0 0 0 0 0 ";
                       moderator.w.ap.feedback(moderator.w.getMode0(originalId2),0,"%comecmdcode%",originalId2,cmd2+"\r\n"); 

                     } else if(moderator.f_downloadChkSaveResult.equals("cancel")){
                        moderator.f_downloadFileVector.clear();
                        moderator.f_downloadActions.clear();
                     }

                   } else if(writemode.equalsIgnoreCase("checkdate")){
                      if(Boolean.parseBoolean(moderator.f_downloadChkExistSecondPara)){
                        File f3=new File(fdata[0]);
                        moderator.f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,originalId2,originalId2,onevar);
                      }
                   } else if(writemode.equalsIgnoreCase("rename")){
                       File f3=new File(fdata[0]);
                       moderator.f_sendFile3(sdir,f3,ddir,moderator.f_downloadChkExistSecondPara,nsdir,writemode,originalId2,originalId2,onevar);
                     } 
                   }else{
                     File f3=new File(fdata[0]);
                     moderator.f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,originalId2,originalId2,onevar);
                   }
                   } else {
                     File f3=new File(fdata[0]);
                     moderator.f_sendFile3(sdir,f3,ddir,dfilename2,nsdir,writemode,originalId2,originalId2,onevar);
                   }
                 if(moderator.f_downloadFileVector.size()>0) moderator.f_downloadFileVector.remove(0);
               }

          }
          if(moderator.f_downloadActions.size()>0) moderator.f_downloadActions.remove(0);
          if(moderator.f_downloadActions.size()<1 && !moderator.f_downloadChkSaveResult.equalsIgnoreCase("cancel")){

           }
          }

    } 
    moderator.f_currentStatus=6;
  }
  public boolean isSleep(){
    return isSleep;
  }
}